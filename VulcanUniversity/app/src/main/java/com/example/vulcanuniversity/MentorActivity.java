package com.example.vulcanuniversity;

import android.content.Intent;
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

import com.example.vulcanuniversity.database.MentorEntity;
import com.example.vulcanuniversity.ui.MentorListAdapter;
import com.example.vulcanuniversity.viewmodel.MentorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MentorActivity extends AppCompatActivity {
    public static final int NEW_MENTOR_REQUEST_CODE = 39;
    public static final int EDIT_MENTOR_REQUEST_CODE = 49;

    private MentorViewModel mMentorViewModel;
    private int courseId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor);

        FloatingActionButton editMentor = findViewById(R.id.button_edit_mentor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        editMentor.setOnClickListener(v -> {
            Intent intentMentor = new Intent(MentorActivity.this, MentorDetailsActivity.class);
            intentMentor.putExtra(MentorDetailsActivity.EXTRA_COURSE, courseId);
            startActivityForResult(intentMentor, NEW_MENTOR_REQUEST_CODE);
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // check for point origin ...intent
        Intent intent = getIntent();
        courseId = intent.getIntExtra("courseId", -1);

        final MentorListAdapter adapter = new MentorListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mMentorViewModel = new ViewModelProvider(this).get(MentorViewModel.class);

        mMentorViewModel.getAllMentors().observe(this, mentors -> {
            // Update the cached copy of the words in the adapter.
            List<MentorEntity> AssignedMentors = new ArrayList<>();
            mentors.stream()
                    .filter(s->s.getCourseId() == getIntent().getIntExtra("courseId",-1))
                    .forEach(s->AssignedMentors.add(s));
            adapter.submitList(AssignedMentors);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mMentorViewModel.delete(adapter.getMentorAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MentorActivity.this, "Mentor deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);


        adapter.setOnItemClickListener(new MentorListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MentorEntity a) {
                Intent intent = new Intent(MentorActivity.this, MentorDetailsActivity.class);
                intent.putExtra(MentorDetailsActivity.EXTRA_ID, a.getMentorId());
                intent.putExtra(MentorDetailsActivity.EXTRA_NAME, a.getName());
                intent.putExtra(MentorDetailsActivity.EXTRA_PHONE_NUMBER, a.getPhoneNumber());
                intent.putExtra(MentorDetailsActivity.EXTRA_EMAIL, a.getEmail());
                intent.putExtra(MentorDetailsActivity.EXTRA_COURSE, a.getCourseId());
                startActivityForResult(intent, EDIT_MENTOR_REQUEST_CODE);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_MENTOR_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra(MentorDetailsActivity.EXTRA_NAME);
            String phoneNumber = data.getStringExtra(MentorDetailsActivity.EXTRA_PHONE_NUMBER);
            String email = data.getStringExtra(MentorDetailsActivity.EXTRA_EMAIL);
            int course = data.getIntExtra(MentorDetailsActivity.EXTRA_COURSE, -1);


            MentorEntity mentor = new MentorEntity(mMentorViewModel.lastId() + 1, name, phoneNumber, email, course);
            mMentorViewModel.insert(mentor);

            Toast.makeText(this, "Mentor Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_MENTOR_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(MentorDetailsActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Mentor can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(MentorDetailsActivity.EXTRA_NAME);
            String phoneNumber = data.getStringExtra(MentorDetailsActivity.EXTRA_PHONE_NUMBER);
            String email = data.getStringExtra(MentorDetailsActivity.EXTRA_EMAIL);
            int course = this.courseId;

            MentorEntity mentor = new MentorEntity(id, name, phoneNumber, email, course);
            mMentorViewModel.update(mentor);
            Toast.makeText(this, "Mentor updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mentor not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_mentor, menu);
        return true;
    }

    // student doesn't control mentors
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_mentors:
                mMentorViewModel.deleteAllMentors();
                Toast.makeText(this, "All mentors deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
