package com.example.vulcanuniversity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vulcanuniversity.database.NoteEntity;
import com.example.vulcanuniversity.ui.NoteListAdapter;
import com.example.vulcanuniversity.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NoteActivity extends AppCompatActivity {
    public static final int NEW_NOTE_REQUEST_CODE = 3;
    public static final int EDIT_NOTE_REQUEST_CODE = 4;
    private NoteViewModel mNoteViewModel;
    private int courseId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        FloatingActionButton newNote = findViewById(R.id.button_new_note);


        newNote.setOnClickListener(v -> {
            Intent intent = new Intent(NoteActivity.this, NoteDetailsActivity.class);
            intent.putExtra(AssessmentDetailsActivity.EXTRA_COURSE,courseId);
            startActivityForResult(intent, NEW_NOTE_REQUEST_CODE);
        });



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // check for point origin ...intent
        Intent intent = getIntent();
        this.courseId = intent.getIntExtra("courseId",-1);



        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        final NoteListAdapter adapter = new NoteListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        mNoteViewModel.getAllNotes().observe(this, notes -> {

//            List<NoteEntity> filteredNotes = new ArrayList<>();
//            for(NoteEntity p:notes)
//                if(p.getTypeId() == getIntent().getIntExtra("courseId",-1))
//                    filteredNotes.add(p);
//            adapter.submitList(filteredNotes);
            List<NoteEntity> assignedNotes = new ArrayList<>();
            assignedNotes.clear();
            notes.stream()
                    .filter(s->s.getCourseId() == getIntent().getIntExtra("courseId",-1))
                    .forEach(s->assignedNotes.add(s));
            adapter.submitList(assignedNotes);
        });

        /*Delete on touch method*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(NoteActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new NoteListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NoteEntity n) {
                Intent intent = new Intent(NoteActivity.this, NoteDetailsActivity.class);
//                System.out.println(a.getId()+ " should be id of notes");
                intent.putExtra(NoteDetailsActivity.EXTRA_ID, n.getId());
                intent.putExtra(NoteDetailsActivity.EXTRA_TEXT, n.getText());
                intent.putExtra(NoteDetailsActivity.EXTRA_DATE, dateFormat(n.getDate()));
                intent.putExtra(NoteDetailsActivity.EXTRA_COURSE, n.getCourseId());
                startActivityForResult(intent, EDIT_NOTE_REQUEST_CODE);
            }
        });
    }

    private String dateFormat(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return format.format(date);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            String text = data.getStringExtra(NoteDetailsActivity.EXTRA_TEXT);
            Date date = (Date) data.getExtras().get(NoteDetailsActivity.EXTRA_DATE);
            int course = data.getIntExtra(AssessmentDetailsActivity.EXTRA_COURSE,-1);


            NoteEntity note = new NoteEntity(mNoteViewModel.lastId()+1,date,text, course);
            mNoteViewModel.insert(note);

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NoteDetailsActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String text = data.getStringExtra(NoteDetailsActivity.EXTRA_TEXT);
            Date date = (Date) data.getExtras().get(NoteDetailsActivity.EXTRA_DATE);
            int course = courseId;

            NoteEntity note = new NoteEntity(id,date,text, course);
            mNoteViewModel.update(note);
            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                mNoteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
