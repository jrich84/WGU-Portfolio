package com.example.vulcanuniversity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ID = "com.example.android.vulcanuniversity.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.android.vulcanuniversity.TITLE";
    public static final String EXTRA_STATUS = "com.example.android.vulcanuniversity.STATUS";
    public static final String EXTRA_START_DATE = "com.example.android.vulcanuniversity.START_DATE";
    public static final String EXTRA_END_DATE = "com.example.android.vulcanuniversity.END_DATE";
    public static final String EXTRA_ALERT_ENABLED = "com.example.android.vulcanuniversity.ALERT_ENABLED ";
    public static final String EXTRA_TERM_ID= "com.example.android.vulcanuniversity.EXTRA_TERM_ID";

    public static final int ASSESSMENT_LIST_REQUEST_CODE = 247;
    public static final int NOTE_LIST_REQUEST_CODE = 258;
    public static final int MENTOR_LIST_REQUEST_CODE = 257;

    public static  int ALARM_REQUEST_START_DATE_CODE=111;
    public static  int ALARM_REQUEST_END_DATE_CODE=115;

    //    private EditText mEditCourseView;
    private EditText editCourseTitle;
    private Spinner editCourseStatus;
    private EditText editCourseStartDate;
    private EditText editCourseEndDate;
    // pass course id to assessment activity
    private int courseId, termId;

    private DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);

    // spinner
    private List<String> courseStatusTypes = new ArrayList<>();
    protected Calendar endCalendar = Calendar.getInstance(TimeZone.getDefault());
    protected Calendar startCalendar = Calendar.getInstance(TimeZone.getDefault());

    // switch
    Switch toggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Spinner courseStatusType = findViewById(R.id.status);
        // Initialize status types
        courseStatusTypes.add("in progress");
        courseStatusTypes.add("completed");
        courseStatusTypes.add("dropped");
        courseStatusTypes.add("plan to take");

        editCourseTitle = findViewById(R.id.title);
        editCourseStatus = findViewById(R.id.status);
        editCourseStartDate = findViewById(R.id.edit_course_startDate);
        editCourseEndDate = findViewById(R.id.edit_course_endDate);

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        courseStatusType.setOnItemSelectedListener(this);

        //Switch
        toggle = (Switch) findViewById(R.id.alert_switch);

        // spinner to select type of course
        ArrayAdapter status = new ArrayAdapter(this,android.R.layout.simple_spinner_item,courseStatusTypes);
        status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        courseStatusType.setAdapter(status);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((R.drawable.ic_close_black_24dp));

        Button assessments = findViewById(R.id.assessment_Button);
        Button notes = findViewById(R.id.note_Button);
        Button mentors = findViewById(R.id.mentor_Button);

        Intent intent = getIntent();
        this.termId = intent.getIntExtra(EXTRA_TERM_ID, -1);
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Course");
            this.courseId = intent.getIntExtra(CourseDetailsActivity.EXTRA_ID, -1);
            this.termId = intent.getIntExtra(CourseDetailsActivity.EXTRA_TERM_ID, -1);
            editCourseTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editCourseStartDate.setText(intent.getStringExtra(EXTRA_START_DATE));
            editCourseEndDate.setText(intent.getStringExtra(EXTRA_END_DATE));
            toggle.setChecked(intent.getBooleanExtra(EXTRA_ALERT_ENABLED, false));
        } else {
            setTitle("Add Course");
            this.courseId = intent.getIntExtra(CourseDetailsActivity.EXTRA_ID, -1);
        }

        //        click event for date input that launched a Datepicker
        editCourseStartDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog startPicker = new DatePickerDialog(CourseDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editCourseStartDate.setText((monthOfYear + 1) + "/"+ dayOfMonth +"/"+year);
                        startCalendar = calendar;
                        startCalendar.set(year, (monthOfYear), dayOfMonth);
                        editCourseStartDate.setText(format.format(startCalendar));

                    }

                }, startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH), startCalendar.get(Calendar.DAY_OF_MONTH));
                startPicker.show();
            }
        });

        editCourseEndDate.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog endPicker = new DatePickerDialog(CourseDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editCourseEndDate.setText((monthOfYear + 1) + "/"+ dayOfMonth +"/"+year);
                        endCalendar = calendar;
                        endCalendar.set(year, (monthOfYear), dayOfMonth);
                        editCourseEndDate.setText(format.format(endCalendar));
                    }

                }, endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH));
                endPicker.show();
            }
        });


        /*
         * Programatic implementation on fetching associated assessments
         * */
        assessments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(CourseDetailsActivity.this, AssessmentActivity.class);
                intent.putExtra("courseId", courseId);
                startActivityForResult(intent, ASSESSMENT_LIST_REQUEST_CODE);
            }
        });

        /*
         * Programatic implementation on fetching associated courses
         * */
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(CourseDetailsActivity.this, NoteActivity.class);
                intent.putExtra("courseId", courseId);
                startActivityForResult(intent, NOTE_LIST_REQUEST_CODE);
            }
        });


        /*
         * Programatic implementation on fetching associated mentors
         * */
        mentors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button click
                Intent intent = new Intent(CourseDetailsActivity.this, MentorActivity.class);
                intent.putExtra("courseId", courseId);
                startActivityForResult(intent, MENTOR_LIST_REQUEST_CODE);
            }
        });

        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // true if the switch is in the On position
            if(isChecked){
                Toast.makeText(this, "Alerts are Enabled", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Alerts are Disabled", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void courseAlerts() {
        // create alarms
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingStartIntent, pendingEndIntent;
        String title = editCourseTitle.getText().toString();

            /*
             * Create intents for start and end dates
             * */
            Intent courseStartIntent = new Intent(this, MyBroadcastReceiver.class);
            Intent courseEndIntent = new Intent(this, MyBroadcastReceiver.class);

            /*
             * Intent Extras
             * */
            courseStartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            courseStartIntent.putExtra("Message", "Course: " + title + " starts today!");

            courseEndIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            courseEndIntent.putExtra("Message", "Course: " + title + " ends today!");

            /*
             * pending intents
             * */
            pendingStartIntent = PendingIntent.getBroadcast(this,ALARM_REQUEST_START_DATE_CODE++,courseStartIntent,0);

            pendingEndIntent = PendingIntent.getBroadcast(this,ALARM_REQUEST_END_DATE_CODE++,courseEndIntent,0);

            /*
             * Set Alarms
             * */
            long starAlarmDate = startCalendar.getTimeInMillis();
            long endAlarmDate = endCalendar.getTimeInMillis();

            alarmManager.set(AlarmManager.RTC_WAKEUP, starAlarmDate -1000, pendingStartIntent);
            alarmManager.set(AlarmManager.RTC_WAKEUP, endAlarmDate -1000, pendingEndIntent);
    }

    private void saveCourse() {

        String title = editCourseTitle.getText().toString();
        String status = editCourseStatus.getSelectedItem().toString();
        String startDate = editCourseStartDate.getText().toString();
        String endDate = editCourseEndDate.getText().toString();
//        String startDate = startCalendar.getTime().toString();
//        String endDate = endCalendar.getTime().toString();

        if (title.trim().isEmpty() || status.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }
        //  testing
        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM d, yyyy 'at' h:mm a");
        System.out.println("\n Start"+format.format(startCalendar.getTime()));
        System.out.println("\n End"+format.format(endCalendar.getTime()));


        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_STATUS, status);
        data.putExtra(EXTRA_TERM_ID, termId);
        data.putExtra(EXTRA_ALERT_ENABLED, toggle.isChecked());
        data.putExtra(EXTRA_START_DATE, startCalendar.getTime());
        data.putExtra(EXTRA_END_DATE, endCalendar.getTime());

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_course_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_course:
                courseAlerts();
                saveCourse();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        parent.getItemAtPosition(pos);
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}