package com.example.vulcanuniversity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vulcanuniversity.database.TermEntity;
import com.example.vulcanuniversity.ui.TermListAdapter;
import com.example.vulcanuniversity.viewmodel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.Locale;


public class TermActivity extends AppCompatActivity {
    public static final int NEW_TERM_REQUEST_CODE = 1;
    public static final int EDIT_TERM_REQUEST_CODE = 2;

    private TermViewModel mTermViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        FloatingActionButton editTerm = findViewById(R.id.button_edit_term);

        editTerm.setOnClickListener(v -> {
            Intent intent = new Intent(TermActivity.this, TermDetailsActivity.class);
            startActivityForResult(intent, NEW_TERM_REQUEST_CODE);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        final TermListAdapter adapter = new TermListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

        mTermViewModel.getAllTerms().observe(this, terms -> {
            // Update the cached copy in the adapter.
            adapter.submitList(terms);
        });

        adapter.setOnItemClickListener(new TermListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TermEntity term) {
                Intent intent = new Intent(TermActivity.this, TermDetailsActivity.class);
                intent.putExtra(TermDetailsActivity.EXTRA_ID, term.getId());
                intent.putExtra(TermDetailsActivity.EXTRA_TITLE, term.getTitle());
                intent.putExtra(TermDetailsActivity.EXTRA_END_DATE, dateFormat(term.getEndDate()));
                intent.putExtra(TermDetailsActivity.EXTRA_START_DATE, dateFormat(term.getStartDate()));
                startActivityForResult(intent, EDIT_TERM_REQUEST_CODE);
            }
        });
    }

    private String dateFormat(Date date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        return format.format(date).toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TERM_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(TermDetailsActivity.EXTRA_TITLE);
            Date endDate = (Date) data.getExtras().get(TermDetailsActivity.EXTRA_END_DATE);
            Date startDate = (Date) data.getExtras().get(TermDetailsActivity.EXTRA_START_DATE);


            TermEntity term = new TermEntity(mTermViewModel.getAllTerms().getValue().size()+1,title,startDate, endDate);
            mTermViewModel.insert(term);

            Toast.makeText(this, "Term Saved", Toast.LENGTH_SHORT).show();
            System.out.println("term saved");
        } else if (requestCode == EDIT_TERM_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(TermDetailsActivity.EXTRA_ID, -1);
            System.out.println("updated term id "+ id);
            if (id == -1) {
                Toast.makeText(this, "term can't be updated", Toast.LENGTH_SHORT).show();
                System.out.println("term  cant updated");
                return;
            }
            String title = data.getStringExtra(TermDetailsActivity.EXTRA_TITLE);
            Date endDate = (Date) data.getExtras().get(TermDetailsActivity.EXTRA_END_DATE);
            Date startDate = (Date) data.getExtras().get(TermDetailsActivity.EXTRA_START_DATE);

            TermEntity term = new TermEntity(id,title,startDate, endDate);
            mTermViewModel.update(term);

            Toast.makeText(this, "term updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Term not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_term, menu);
        return true;
    }
}
