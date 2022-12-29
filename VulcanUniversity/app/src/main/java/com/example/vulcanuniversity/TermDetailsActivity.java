package com.example.vulcanuniversity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.vulcanuniversity.database.AppDatabase;
import com.example.vulcanuniversity.viewmodel.TermViewModel;

import java.text.ParseException;

public class TermDetailsActivity extends AppCompatActivity {

    public final DateFormat sdf = DateFormat.getDateInstance(DateFormat.LONG);
    public static final String EXTRA_ID = "com.codinginflow.architectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.android.vulcanuniversity.TITLE";
    public static final String EXTRA_START_DATE = "com.example.android.vulcanuniversity.START_DATE";
    public static final String EXTRA_END_DATE = "com.example.android.vulcanuniversity.END_DATE";
    public static final int COURSE_LIST_REQUEST_CODE = 475;
    private TermViewModel mTermViewModel;

    //    private EditText mEditTermView;
    private EditText editTermTitle,editTermStartDate,editTermEndDate;
    // pass course id to assessment activity
    private int termId;


    protected Calendar endCalendar = Calendar.getInstance(TimeZone.getDefault());
    protected Calendar startCalendar= Calendar.getInstance(TimeZone.getDefault());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((R.drawable.ic_close_black_24dp));

        editTermTitle = findViewById(R.id.title);
        editTermStartDate = findViewById(R.id.startDate);
        editTermEndDate = findViewById(R.id.endDate);
        Button courses = findViewById(R.id.courses_button);
        mTermViewModel = new ViewModelProvider(this).get(TermViewModel.class);

//        click event for date input that launched a Datepicker
        editTermStartDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog endPicker = new DatePickerDialog(TermDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editTermStartDate.setText((monthOfYear + 1) + "/"+ dayOfMonth +"/"+year);
                        startCalendar = calendar;

                        startCalendar.set(year, (monthOfYear), dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
                        System.out.println(format.format(startCalendar.getTime())+ " end");
                    }

                }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
                endPicker.show();
            }
        });


        editTermEndDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog endPicker = new DatePickerDialog(TermDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editTermEndDate.setText((monthOfYear + 1) + "/"+ dayOfMonth +"/"+year);
                        endCalendar = calendar;

                        endCalendar.set(year, (monthOfYear), dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
                        System.out.println(format.format(endCalendar.getTime())+ " end");
                    }

                }, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));
                endPicker.show();
            }
        });

        /*
         * Programatic implementation on fetching associated assessments
         * */
        courses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(TermDetailsActivity.this, CourseActivity.class);
                intent.putExtra("termId", termId);
//                System.out.println("\n"+termId+" TermId id");
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit term");
            this.termId = intent.getIntExtra(TermDetailsActivity.EXTRA_ID, -1);
            editTermTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editTermStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            editTermEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
        } else {
            setTitle("Add term");
        }
    }

    private void saveTerm() throws ParseException {
        String title = editTermTitle.getText().toString();
        String startDate = editTermStartDate.getText().toString();
        String endDate = editTermEndDate.getText().toString();

        if (title.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }


//        DateFormat sdf = DateFormat.getDateInstance(DateFormat.LONG);
//        String sDate = sdf.format(startCalendar.getTime());
//        String eDate = sdf.format(endCalendar.getTime());
//
//        Date newStartDate = sdf.parse(sDate);
//        Date newEndDate = sdf.parse(eDate);



        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_START_DATE, startCalendar.getTime());
        data.putExtra(EXTRA_END_DATE, endCalendar.getTime());

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    private void deleteTerm() {
        AppDatabase db = AppDatabase.getDatabase(this);
        int course = db.courseDao().termCount(termId);
        if(course > 0) {
            Toast.makeText(this, "Cannot delete Term with assigned Courses", Toast.LENGTH_SHORT).show();
        } else {
            db.termDao().deleteTermById(this.termId);
            Toast.makeText(this, "Term deleted", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_term_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_term:
                try {
                    saveTerm();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.delete_term:
                deleteTerm();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}