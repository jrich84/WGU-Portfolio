package com.example.vulcanuniversity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.ParseException;
import java.util.Date;

public class NoteDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_ID = "com.codinginflow.architectureexample.EXTRA_ID";
    public static final String EXTRA_TEXT = "com.example.android.vulcanuniversity.EXTRA_TITLE";
    public static final String EXTRA_DATE = "com.example.android.vulcanuniversity.EXTRA_DUE_DATE";
    public static final String EXTRA_COURSE = "com.example.android.vulcanuniversity.EXTRA_COURSE";

    private DateFormat format = DateFormat.getDateInstance(DateFormat.LONG);
    //    private EditText mEditNoteView;
    private EditText editNoteText;
    private EditText editNoteDate;
    private int courseId;

    protected Calendar dueCalendar = Calendar.getInstance(TimeZone.getDefault());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((R.drawable.ic_close_black_24dp));

        editNoteText = findViewById(R.id.note_text);
        editNoteDate = findViewById(R.id.note_date);

//        click event for date input that launched a Datepicker
        editNoteDate.setOnClickListener(new View.OnClickListener()  {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                    DatePickerDialog startPicker = new DatePickerDialog(NoteDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            editNoteDate.setText((monthOfYear + 1) + "/"+ dayOfMonth +"/"+year);
                            dueCalendar.set(year, (monthOfYear), dayOfMonth);
                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);
                            editNoteDate.setText(format.format(dueCalendar));
                        }

                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    startPicker.show();

                }
        });


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editNoteText.setText(intent.getStringExtra(EXTRA_TEXT));
            editNoteDate.setText(intent.getStringExtra(EXTRA_DATE));
            courseId = intent.getIntExtra(EXTRA_COURSE, -1);
        } else {
            setTitle("Add Note");
            courseId = intent.getIntExtra(EXTRA_COURSE, -1);
        }
    }

    private void saveNote() throws ParseException {

        String text = editNoteText.getText().toString();
        String date = editNoteDate.getText().toString();

        if (text.trim().isEmpty() || date.trim().isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }


        DateFormat sdf = DateFormat.getDateInstance(DateFormat.LONG);
        String targetdate = sdf.format(dueCalendar.getTime());

        Date newdate = sdf.parse(targetdate);



        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT, text);
        data.putExtra(EXTRA_DATE, newdate);
        data.putExtra(EXTRA_COURSE, this.courseId);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    // Share text to other applications, Ref developer.android.com
    private void shareNote() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, editNoteText.getText().toString());
        sendIntent.setType("text/plain");
        // list sharable apps
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_note_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                try {
                    saveNote();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.share_note:
                shareNote();
            break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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