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

import com.example.vulcanuniversity.database.CourseEntity;
import com.example.vulcanuniversity.ui.CourseListAdapter;
import com.example.vulcanuniversity.viewmodel.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CourseActivity extends AppCompatActivity {


    public static final int NEW_COURSE_REQUEST_CODE = 20;
    public static final int EDIT_COURSE_REQUEST_CODE = 25;

    private CourseViewModel mCourseViewModel;
    private int termId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);


        FloatingActionButton editCourse = findViewById(R.id.button_edit_course);
        Toolbar toolbar = findViewById(R.id.toolbar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        editCourse.setOnClickListener(v -> {
            Intent intent = new Intent(CourseActivity.this, CourseDetailsActivity.class);
            intent.putExtra(CourseDetailsActivity.EXTRA_TERM_ID,termId);
            startActivityForResult(intent, NEW_COURSE_REQUEST_CODE);
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // check for point origin ...intent
        Intent intent = getIntent();
        termId = intent.getIntExtra("termId",-1);
//        final String courseFlag = intent.getStringExtra("course");
//        System.out.println(termFlag);
//        System.out.println(courseFlag);


        final CourseListAdapter adapter = new CourseListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        mCourseViewModel = new ViewModelProvider(this).get(CourseViewModel.class);

        mCourseViewModel.getAllCourses().observe(this, courses -> {
                List<CourseEntity> AssignedCourses = new ArrayList<>();
                courses.stream()
                    .filter(s->s.getTermId() == getIntent().getIntExtra("termId",-1))
                    .forEach(s->AssignedCourses.add(s));
                adapter.submitList(AssignedCourses);
        });

        /*Delete on touch method*/
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mCourseViewModel.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(CourseActivity.this, "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new CourseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CourseEntity c) {
                Intent intent = new Intent(CourseActivity.this, CourseDetailsActivity.class);
                intent.putExtra(CourseDetailsActivity.EXTRA_ID, c.getCourseId());
                intent.putExtra(CourseDetailsActivity.EXTRA_TITLE, c.getTitle());
                intent.putExtra(CourseDetailsActivity.EXTRA_STATUS, c.getStatus());
                intent.putExtra(CourseDetailsActivity.EXTRA_TERM_ID, c.getTermId());
                intent.putExtra(CourseDetailsActivity.EXTRA_ALERT_ENABLED, c.getAlertsEnabled());
                intent.putExtra(CourseDetailsActivity.EXTRA_END_DATE, dateFormat(c.getEndDate()));
                intent.putExtra(CourseDetailsActivity.EXTRA_START_DATE, dateFormat(c.getStartDate()));
                startActivityForResult(intent, EDIT_COURSE_REQUEST_CODE);
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
        System.out.println("\n Request Code: "+requestCode+" \n"+ "ResultCode: "+resultCode);
        if (requestCode == NEW_COURSE_REQUEST_CODE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(CourseDetailsActivity.EXTRA_TITLE);
            String status = data.getStringExtra(CourseDetailsActivity.EXTRA_STATUS);
            Date endDate = (Date) data.getExtras().get(CourseDetailsActivity.EXTRA_END_DATE);
            Date startDate = (Date) data.getExtras().get(CourseDetailsActivity.EXTRA_START_DATE);
            Boolean alertEnabled = data.getBooleanExtra(CourseDetailsActivity.EXTRA_ALERT_ENABLED, false);
            int termId = data.getIntExtra(CourseDetailsActivity.EXTRA_TERM_ID, -1);

            System.out.print("new course term id is "+ termId);

            CourseEntity Course = new CourseEntity(mCourseViewModel.getAllCourses().getValue().size()+1,title, status, startDate, endDate,alertEnabled, termId);
            mCourseViewModel.insert(Course);
            Toast.makeText(this, "Course Saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_COURSE_REQUEST_CODE && resultCode == RESULT_OK) {
            int id = data.getIntExtra(CourseDetailsActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = data.getStringExtra(CourseDetailsActivity.EXTRA_TITLE);
            String status = data.getStringExtra(CourseDetailsActivity.EXTRA_STATUS);
            Date endDate = (Date) data.getExtras().get(CourseDetailsActivity.EXTRA_END_DATE);
            Date startDate = (Date) data.getExtras().get(CourseDetailsActivity.EXTRA_START_DATE);
            Boolean alertEnabled = data.getBooleanExtra(CourseDetailsActivity.EXTRA_ALERT_ENABLED, false);
            int term = termId;

            CourseEntity Course = new CourseEntity(id,title, status, startDate, endDate,alertEnabled, term);
            mCourseViewModel.update(Course);
            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_course, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_courses:
                mCourseViewModel.deleteAllCourses();
                Toast.makeText(this, "All Courses deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
