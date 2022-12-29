package com.example.vulcanuniversity.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {
    private TermDao mTermDao;
    private CourseDao mCourseDao;
    private AssessmentDao mAssessmentDao;
    private NoteDao mNoteDao;
    private MentorDao mMentorDao;

    private LiveData<List<TermEntity>> mAllTerms;
    private LiveData<List<CourseEntity>> mAllCourses, mAssociatedTerms;
    private LiveData<List<AssessmentEntity>> mAllAssessments,mAssociatedAssessments;
    private LiveData<List<NoteEntity>> mAllNotes, mAssociatedNotes;
    private LiveData<List<MentorEntity>> mAllMentors, mAssociatedMentors;
    private int courseId,noteId,termId;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        mTermDao = db.termDao();
        mCourseDao = db.courseDao();
        mNoteDao = db.noteDao();
        mAssessmentDao = db.assessmentDao();
        mMentorDao = db.mentorDao();

        mAllTerms = mTermDao.getAllTerms();
        mAllCourses = mCourseDao.getAllCourses();
        mAllAssessments = mAssessmentDao.getAllAssessments();
        mAllMentors = mMentorDao.getAllMentors();
        mAllNotes = mNoteDao.getAllNotes();

        mAssociatedTerms = mCourseDao.getAllAssociatedTerms(termId);
        mAssociatedNotes = mNoteDao.getAllAssociatedNotes(noteId);
        mAssociatedMentors = mMentorDao.getAllAssociatedMentors(courseId);
        mAssociatedAssessments=mAssessmentDao.getAllAssociatedAssessments(courseId);
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<TermEntity>> getAllTerms() {
        return mAllTerms;
    }
    public LiveData<List<CourseEntity>> getAllCourses() {
        return mAllCourses;
    }
    public LiveData<List<CourseEntity>> getAllAssociatedTerms(int termId) {

        return mAssociatedTerms;
    }
    public LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mAllAssessments;
    }
    public LiveData<List<AssessmentEntity>> getAllAssociatedAssessments(int courseId) {

        return mAssociatedAssessments;
    }
    public LiveData<List<MentorEntity>> getAllMentors() {
        return mAllMentors;
    }
    public LiveData<List<MentorEntity>> getAllAssociatedMentors(int courseId) {

        return mAssociatedMentors;
    }
    public LiveData<List<NoteEntity>> getAllNotes() {
        return mAllNotes;
    }
    public LiveData<List<NoteEntity>> getAllAssociatedNotes(int courseId) {
        return mAssociatedNotes;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.

    /*
        Terms
    */
    public void insert(TermEntity term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.insert(term);
        });
    }

    public void delete(TermEntity term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.deleteTerm(term);
        });
    }

    public void deleteTermById(int term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.deleteTermById(term);
        });
    }

    public void deleteAllTerms() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.deleteAllTerms();
        });
    }

    public void update(TermEntity term) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mTermDao.update(term);
        });
    }

    /*
        Courses
    */
    public void insert(CourseEntity c) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.insert(c);
        });
    }

    public void delete(CourseEntity c) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.deleteCourse(c);
        });
    }

    public void deleteAllCourses() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.deleteAllCourses();
        });
    }

    public void update(CourseEntity c) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mCourseDao.update(c);
        });
    }
    /*
        Assesments
    */
    public void insert(AssessmentEntity a) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.insert(a);
        });
    }

    public void delete(AssessmentEntity a) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.deleteAssessment(a);
        });
    }

    public void deleteAllAssessments() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.deleteAllAssessments();
        });
    }

    public void update(AssessmentEntity a) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAssessmentDao.update(a);
        });
    }
    /*
        Notes
    */
    public void insert(NoteEntity n) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.insert(n);
        });
    }
    public void delete(NoteEntity n) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.deleteNote(n);
        });
    }
    public void deleteAllNotes() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.deleteAllNotes();
        });
    }
    public void update(NoteEntity n) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mNoteDao.update(n);
        });
    }
    /*
    * Mentors
    */
    public void insert(MentorEntity m) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMentorDao.insert(m);
        });
    }
    public void delete(MentorEntity m) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMentorDao.deleteMentor(m);
        });
    }
    public void deleteAllMentors() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMentorDao.deleteAllMentors();
        });
    }
    public void update(MentorEntity m) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMentorDao.update(m);
        });
    }
}
