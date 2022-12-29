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

import com.example.vulcanuniversity.database.AssessmentEntity;
import com.example.vulcanuniversity.ui.AssessmentListAdapter;
import com.example.vulcanuniversity.viewmodel.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AssessmentActivity extends AppCompatActivity {
    public static final int NEW_ASSESSMENT_REQUEST_CODE = 3;
    public static final int EDIT_ASSESSMENT_REQUEST_CODE = 4;

    private AssessmentViewModel mAssessmentViewModel;
    private int courseId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        FloatingActionButton editTerm = findViewById(R.id.button_edit_assessment);


        editTerm.setOnClickListener(v -> {
            Intent intent = new Intent(AssessmentActivity.this, AssessmentDetailsActivity.class);
            intent.putExtra(AssessmentDetailsActivity.EXTRA_COURSE,courseId);
            startActivityForResult(intent, NEW_ASSESSMENT_REQUEST_CODE);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // check for point origin ...intent
        Intent intent = getIntent();
        this.courseId = intent.getIntExtra("courseId",-1);
//        System.out.println(courseId);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        final AssessmentListAdapter adapter = new AssessmentListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mAssessmentViewModel = new ViewModelProvider(this).get(AssessmentViewModel.class);

        mAssessmentViewModel.getAllAssessments().observe(this, assessments -> {
            // Update the cached copy of the words in the adapter.
//
//            List<AssessmentEntity> filteredAssessments = new ArrayList<>();
//            for(AssessmentEntity p:assessments)
//                if(p.getCourseId() == getIntent().getIntExtra("courseId",-1))
//                    filteredAssessments.add(p);
//            adapter.submitList(filteredAssessments);
//            if(courseFlag == -1 && assessmentFlag.equals("assessment")){
//                adapter.submitList(assessments);
//                System.out.println("beep");
//            }
//            if(courseFlag != -1 && assessmentFlag == null){
//                List<AssessmentEntity> AssignedAssessments = new ArrayList<>();
//
//                assessments.stream()
//                        .filter(s->s.getCourseId() == getIntent().getIntExtra("courseId",-1))
//                        .forEach(s->AssignedAssessments.add(s));
//                adapter.submitList(AssignedAssessments);
//                System.out.println("honk");
//            }
            List<AssessmentEntity> AssignedAssessments = new ArrayList<>();
            AssignedAssessments.clear();
            assessments.stream()
                    .filter(s->s.getCourseId() == getIntent().getIntExtra("courseId",-1))
                    .forEach(s->AssignedAssessments.add(s));
            adapter.submitList(AssignedAssessments);
        });

        /*Delete on touch method*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mAssessmentViewModel.delete(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AssessmentActivity.this, "Assessment deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AssessmentListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(AssessmentEntity a) {
                Intent intent = new Intent(AssessmentActivity.this, AssessmentDetailsActivity.class);
                intent.putExtra(AssessmentDetailsActivity.EXTRA_ID, a.getId());
                intent.putExtra(AssessmentDetailsActivity.EXTRA_TITLE, a.getTitle());
                intent.putExtra(AssessmentDetailsActivity.EXTRA_COURSE, a.getCourseId());
                intent.putExtra(AssessmentDetailsActivity.EXTRA_DUE_DATE, dateFormat(a.getDueDate()));
                intent.putExtra(AssessmentDetailsActivity.EXTRA_TYPE, a.getType());
                intent.putExtra(AssessmentDetailsActivity.EXTRA_ALERTS_ENABLED, a.getAlertsEnabled());
                startActivityForResult(intent, EDIT_ASSESSMENT_REQUEST_CODE);
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
        if (requestCode == NEW_ASSESSMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AssessmentDetailsActivity.EXTRA_TITLE);
            String type = data.getStringExtra(AssessmentDetailsActivity.EXTRA_TYPE);
            Date dueDate = (Date) data.getExtras().get(AssessmentDetailsActivity.EXTRA_DUE_DATE);
            Boolean alertEnabled = data.getBooleanExtra(AssessmentDetailsActivity.EXTRA_ALERTS_ENABLED, false);
            int course = data.getIntExtra(AssessmentDetailsActivity.EXTRA_COURSE,-1);


            AssessmentEntity assessment = new AssessmentEntity(mAssessmentViewModel.lastId()+1,title,type, alertEnabled, dueDate, course);
            mAssessmentViewModel.insert(assessment);

            Toast.makeText(this, "Assessment Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_ASSESSMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AssessmentDetailsActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Assessment can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(AssessmentDetailsActivity.EXTRA_TITLE);
            String type = data.getStringExtra(AssessmentDetailsActivity.EXTRA_TYPE);
            Date dueDate = (Date) data.getExtras().get(AssessmentDetailsActivity.EXTRA_DUE_DATE);
            Boolean alertEnabled = data.getBooleanExtra(AssessmentDetailsActivity.EXTRA_ALERTS_ENABLED, false);
            int course = courseId;

            AssessmentEntity assessment = new AssessmentEntity(id,title,type, alertEnabled, dueDate, course);
            mAssessmentViewModel.update(assessment);
            Toast.makeText(this, "Assessment updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Assessment not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_assessment, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_assessments:
                mAssessmentViewModel.deleteAllAssessments();
                Toast.makeText(this, "All assessments deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
