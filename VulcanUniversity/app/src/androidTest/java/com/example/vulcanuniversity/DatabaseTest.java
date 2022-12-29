package com.example.vulcanuniversity;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
//    public static final String TAG = "Junit";
//    private DegreePlanDatabase mDb;
//    private Course mDao;
//
//    @Before
//    public void createDb() {
//        Context context = InstrumentationRegistry.getTargetContext();
//        mDb = Room.inMemoryDatabaseBuilder(context,
//            DegreePlanDatabase.class).build();
//
//        mDao = mDb.courseDAO();
//        Log.i(TAG, "CreateDb");
//    }
//
//    @After
//    public void closeDb() {
//        mDb.close();
//        Log.i(TAG, "closeDb");
//    }
//
//    @Test
//    public void createAndRetrieveNotes() {
//        mDao.insert(SampleData.getNotes());
//        int count = mDao.getCount();
//        Log.i(TAG, "createAndRetrieveNotes: count-" + count)
//        assertEquals(SampleData.getNotes.Size(), count);
//    }
//
//    @Test
//    public void compareString() {
//        mDao.insert(SampleData.getNotes());
//        CourseEntity original = SampleDate.GetNotes().get(0);
//        CourseEntity fromDb = mDao.getCourseById(1);
//        assertEquals(original.getText(), fromDb.getTitle());
//    }
}
