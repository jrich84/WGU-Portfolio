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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class AssessmentDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ID = "com.codinginflow.architectureexample.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.android.vulcanuniversity.EXTRA_TITLE";
    public static final String EXTRA_TYPE = "com.example.android.vulcanuniversity.EXTRA_TYPE";
    public static final String EXTRA_DUE_DATE = "com.example.android.vulcanuniversity.EXTRA_DUE_DATE";
    public static final String EXTRA_ALERTS_ENABLED = "com.example.android.vulcanuniversity.EXTRA_ALERTS_ENABLED";
    public static final String EXTRA_COURSE = "com.example.android.vulcanuniversity.EXTRA_COURSE";

    public static int ALARM_REQUEST_DUE_DATE_CODE=201;
    private DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);

    //    private EditText mEditAssessmentView;
    private EditText editAssessmentTitle;
    private int courseId;
    private EditText editAssessmentDueDate;
    private Spinner EditAssessmentType;

    protected Calendar dueCalendar = Calendar.getInstance(TimeZone.getDefault());

    // spinner list
    List<String> assessmentTypes = new ArrayList<String>();

    // switch
    Switch toggle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Spinner assessmentType = findViewById(R.id.type);

        //Switch
        toggle = (Switch) findViewById(R.id.alert_switch);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((R.drawable.ic_close_black_24dp));

        editAssessmentTitle = findViewById(R.id.title);
        editAssessmentDueDate = findViewById(R.id.dueDate);
        EditAssessmentType = findViewById(R.id.type);

        // Initialize assessment types
        assessmentTypes.add("Objective");
        assessmentTypes.add("Performance");

        //Getting the instance of Spinner and applying OnItemSelectedListener on it
        assessmentType.setOnItemSelectedListener(this);

        // spinner to select type of course
        ArrayAdapter types = new ArrayAdapter(this,android.R.layout.simple_spinner_item,assessmentTypes);
        types.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Setting the ArrayAdapter data on the Spinner
        assessmentType.setAdapter(types);

//        click event for date input that launched a Datepicker
        editAssessmentDueDate.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    DatePickerDialog startPicker = new DatePickerDialog(AssessmentDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            editAssessmentDueDate.setText((monthOfYear + 1) + "/"+ dayOfMonth +"/"+year);
                            dueCalendar = calendar;
                            dueCalendar.set(year, (monthOfYear), dayOfMonth);
                            editAssessmentDueDate.setText(format.format(dueCalendar));
                        }

                    }, dueCalendar.get(Calendar.YEAR), dueCalendar.get(Calendar.MONTH), dueCalendar.get(Calendar.DAY_OF_MONTH));
                    startPicker.show();
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

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Assessment");
            assessmentTypes.indexOf(intent.getStringExtra(EXTRA_TYPE));
            editAssessmentTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            editAssessmentDueDate.setText(intent.getStringExtra(EXTRA_DUE_DATE));
            toggle.setChecked(intent.getBooleanExtra(EXTRA_ALERTS_ENABLED, false));
            // have to find index of passed string to set selection, else index our of bounds
            EditAssessmentType.setSelection(assessmentTypes.indexOf(intent.getStringExtra(EXTRA_TYPE)));
        } else {
            setTitle("Add Assessment");
            this.courseId = intent.getIntExtra(EXTRA_COURSE, -1);
        }
    }

    private void assessmentAlerts() {
        // create alarms
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingDueDateIntent;
        String title = editAssessmentTitle.getText().toString();

        /*
         * Create intents for start and end dates
         * */
        Intent assessmentStartIntent = new Intent(this, MyBroadcastReceiver.class);

        /*
         * Intent Extras
         * */
        assessmentStartIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        assessmentStartIntent.putExtra("Message", "Assessment: " + title + " starts today!");

        /*
         * pending intents
         * */
        pendingDueDateIntent = PendingIntent.getBroadcast(this,ALARM_REQUEST_DUE_DATE_CODE++,assessmentStartIntent,0);

        /*
         * Set Alarms
         * */
        long starAlarmDate = dueCalendar.getTimeInMillis();


        alarmManager.set(AlarmManager.RTC_WAKEUP, starAlarmDate+7000, pendingDueDateIntent);
    }

    private void saveAssessment() {
        /* Get selected object and get CourseEntity Index*/

        String title = editAssessmentTitle.getText().toString();
        String dueDate = editAssessmentDueDate.getText().toString();
        String type = EditAssessmentType.getSelectedItem().toString();

        if (title.trim().isEmpty() || dueDate.trim().isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE, title);
        data.putExtra(EXTRA_COURSE, this.courseId);
        data.putExtra(EXTRA_DUE_DATE, dueCalendar.getTime());
        data.putExtra(EXTRA_TYPE, type);
        data.putExtra(EXTRA_ALERTS_ENABLED, toggle.isChecked());

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
        menuInflater.inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_assessment:
                assessmentAlerts();
                saveAssessment();
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