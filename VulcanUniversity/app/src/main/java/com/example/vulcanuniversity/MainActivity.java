package com.example.vulcanuniversity;


import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private TextView termsCompleted, coursesCompleted;

    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        checkPermission();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        // progress
//        termsCompleted = findViewById(R.id.term_progress);
//        coursesCompleted = findViewById(R.id.course_progress);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                // You don't have permission
                checkPermission();
            } else {
                // Do as per your logic
            }

        }

    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            }
        }
    }

    /*
    * Load Activity fragment selected from drawer menu into a new screen
    * */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent myIntent;
        switch (item.getItemId()) {
            case R.id.nav_ManageTerms:
                myIntent = new Intent(this, TermActivity.class);
                startActivity(myIntent);
                break;
// To do
//            case R.id.nav_ManageCourses:
//                myIntent = new Intent(this, CourseActivity.class);
//                myIntent.putExtra("course", "course");
//                startActivity(myIntent);
//                break;
//            case R.id.nav_ManageAssessments:
//                myIntent = new Intent(this, AssessmentActivity.class);
//                myIntent.putExtra("assessment", "assessment");
//                startActivity(myIntent);
//                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
    * algorithm:
    * Find # of distinct terms
    * Find # of courses in a term
    * Find # of complete courses
    * */

//    public void checkProgress() {
//        AppDatabase db = AppDatabase.getDatabase(this);
//        db.termDao().getCount();
//        // assign values
//        int courseLoad, completeCourses;
////        Map<String, Integer> distinctTerms = db.termDao()
////                .getAllTerms()
////                .getValue()
////                .stream()
////                .collect( Collectors.toMap(TermEntity::getTitle,
////                        null) );
//        System.out.println(db.progressDao().getAllTerms().getValue().size());
////        termsCompleted.setText("");
////        coursesCompleted.setText("");
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
