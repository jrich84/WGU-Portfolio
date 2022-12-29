package com.example.vulcanuniversity.database;

import android.content.Context;
import android.icu.util.Calendar;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class,NoteEntity.class,MentorEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    public static final String APPDATABASE = "AppDatabase.db";
    private static Calendar calendar;
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    private static final Object LOCK = new Object();

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract AssessmentDao assessmentDao();
    public abstract MentorDao mentorDao();
    public abstract NoteDao noteDao();

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null){
            synchronized (LOCK) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, APPDATABASE).addCallback(sRoomDatabaseCallback).build();
                            AppDatabase.class, APPDATABASE).addCallback(sRoomDatabaseCallback).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    public static Date setDate(int YYYY, int MM, int DD) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.set(Calendar.YEAR, YYYY);
        cal.set(Calendar.MONTH, MM -1);
        cal.set(Calendar.DAY_OF_MONTH, DD);
        return cal.getTime();
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.

                /*Make some Terms*/
                TermDao tdao = INSTANCE.termDao();
                tdao.deleteAllTerms();

                TermEntity term = new TermEntity(1,"Term 1", setDate(2012, 2,5),setDate(2012, 3,5));
                tdao.insert(term);

                term = new TermEntity(2,"Term 2", setDate(2012, 2,5),setDate(2012, 2,5));
                tdao.insert(term);

                /*Make some Courses*/
                CourseDao cdao = INSTANCE.courseDao();
                cdao.deleteAllCourses();

                CourseEntity course = new CourseEntity(1,"Course 1", "in progress",setDate(2012, 2,5),setDate(2012, 3,5),false,1);
                cdao.insert(course);

                course = new CourseEntity(2,"Course 2", "in progress",setDate(2012, 3,5),setDate(2012, 3,15), false,1);
                cdao.insert(course);

                /*Make some Assessments*/
                AssessmentDao adao = INSTANCE.assessmentDao();
                adao.deleteAllAssessments();

                AssessmentEntity assessment = new AssessmentEntity(1, "PA100", "Performance", false, setDate(2012, 3,5), 1);
                adao.insert(assessment);

                assessment = new AssessmentEntity(2, "OBJ100", "Objective", false, setDate(2012, 2,5), 2);
                adao.insert(assessment);

                /* Make some Notes*/
                NoteDao ndao = INSTANCE.noteDao();
                ndao.deleteAllNotes();

                NoteEntity note = new NoteEntity(1,setDate(2012, 2,5),"this is note text",1);
                ndao.insert(note);

                /* Make some Mentors*/
                MentorDao mdao = INSTANCE.mentorDao();
                mdao.deleteAllMentors();

                MentorEntity mentor;
                mentor = new MentorEntity(1,"MR. Spock","888-456-8954","spock@Enterprise.com",1);
                mdao.insert(mentor);

                mentor = new MentorEntity(2,"CPT Kirk","888-456-8955","JamesTKirk@Enterprise.com",2);
                mdao.insert(mentor);

                mentor = new MentorEntity(3,"Bones","888-456-8988","Bones@Enterprise.com",1);
                mdao.insert(mentor);

                mentor = new MentorEntity(4,"Scotty","888-456-8998","Scotty@Enterpris.com",1);
                mdao.insert(mentor);
            });
        }
    };

}
