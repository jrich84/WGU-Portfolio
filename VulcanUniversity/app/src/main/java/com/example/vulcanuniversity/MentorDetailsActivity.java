package com.example.vulcanuniversity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MentorDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String EXTRA_ID = "com.example.android.vulcanuniversity.EXTRA_ID";
    public static final String EXTRA_NAME = "com.example.android.vulcanuniversity.EXTRA_NAME";
    public static final String EXTRA_PHONE_NUMBER = "com.example.android.vulcanuniversity.EXTRA_PHONE_NUMBER";
    public static final String EXTRA_EMAIL = "com.example.android.vulcanuniversity.EXTRA_EMAIL";
    public static final String EXTRA_COURSE = "com.example.android.vulcanuniversity.EXTRA_COURSE";
    //    private EditText mEditMentorView;
    private EditText editMentorName;
    private EditText editMentorPhoneNumber;
    private EditText editMentorEmail;
    private int courseId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator((R.drawable.ic_close_black_24dp));

        editMentorName = findViewById(R.id.edit_name);
        editMentorPhoneNumber = findViewById(R.id.edit_phoneNumber);
        editMentorEmail = findViewById(R.id.edit_email);


        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Mentor");
            editMentorName.setText(intent.getStringExtra(EXTRA_NAME));
            editMentorPhoneNumber.setText(intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER));
            editMentorEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            this.courseId = intent.getIntExtra(MentorDetailsActivity.EXTRA_COURSE, -1);
        } else {
            setTitle("Add Mentor");
            this.courseId = intent.getIntExtra(MentorDetailsActivity.EXTRA_COURSE, -1);
        }
    }

    private void saveMentor() {

        String name = editMentorName.getText().toString();
        String phoneNumber = editMentorPhoneNumber.getText().toString();
        String email = editMentorEmail.getText().toString();

        if (name.trim().isEmpty() || phoneNumber.trim().isEmpty() || email.trim().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_PHONE_NUMBER, phoneNumber);
        data.putExtra(EXTRA_EMAIL, email);
        data.putExtra(EXTRA_COURSE, this.courseId);
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
        menuInflater.inflate(R.menu.menu_mentor_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_mentor:
                    saveMentor();
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
