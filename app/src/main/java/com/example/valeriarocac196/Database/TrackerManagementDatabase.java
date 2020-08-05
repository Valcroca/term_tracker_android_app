package com.example.valeriarocac196.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.valeriarocac196.DAO.AssessmentDAO;
import com.example.valeriarocac196.DAO.CourseDAO;
import com.example.valeriarocac196.DAO.TermDAO;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.Entities.TermEntity;

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 13, exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class TrackerManagementDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();


    private static volatile TrackerManagementDatabase INSTANCE;

    public static TrackerManagementDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrackerManagementDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TrackerManagementDatabase.class, "tracker_management_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more records, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CourseDAO mCourseDAO;
        private final TermDAO mTermDAO;
        private final AssessmentDAO mAssessmentDAO;

        PopulateDbAsync(TrackerManagementDatabase db) {
            mTermDAO = db.termDAO();
            mCourseDAO=db.courseDAO();
            mAssessmentDAO=db.assessmentDAO();

        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mTermDAO.deleteAllTerms();
            mCourseDAO.deleteAllCourses();
            mAssessmentDAO.deleteAllAssessments();

            //create terms
            TermEntity term1= new TermEntity(1,"First Term", DateConverter.toDate("01/01/2020"), DateConverter.toDate("06/30/2020"), false);
            mTermDAO.insertTerm(term1);
            TermEntity term2=new TermEntity(2,"Second Term", DateConverter.toDate("07/01/2020"), DateConverter.toDate("12/31/2020"), true);
            mTermDAO.insertTerm(term2);
            TermEntity term3=new TermEntity(3,"Third Term", DateConverter.toDate("01/01/2021"), DateConverter.toDate("06/30/2021"), false);
            mTermDAO.insertTerm(term3);
            //create courses
            CourseEntity course1 = new CourseEntity( 1, term1.getTermId(), "First Course", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"), "status", "Mable Nice", "801-333-6655", "mabel@gmail.com", "notes", DateConverter.toDate("01/01/2020"));
            mCourseDAO.insertCourse(course1);
            CourseEntity course2=new CourseEntity( 2, term1.getTermId(), "Second Course", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"), "status", "Mable Nice", "801-333-6655", "mabel@gmail.com", "notes", DateConverter.toDate("01/01/2020"));
            mCourseDAO.insertCourse(course2);
            CourseEntity course3 =new CourseEntity( 3, term2.getTermId(), "Third Course", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"), "status", "Mable Nice", "801-333-6655", "mabel@gmail.com","notes", DateConverter.toDate("01/01/2020"));
            mCourseDAO.insertCourse(course3);
            CourseEntity course4 =new CourseEntity( 4, term2.getTermId(), "Fourth Course", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"), "status", "Mable Nice", "801-333-6655", "mabel@gmail.com", "notes", DateConverter.toDate("01/01/2020"));
            mCourseDAO.insertCourse(course4);
            //create assessments
            AssessmentEntity assessment1 = new AssessmentEntity(1, course1.getCourseId(),"Mid-term Test", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"));
            mAssessmentDAO.insertAssessment(assessment1);
            AssessmentEntity assessment2 = new AssessmentEntity(2, course1.getCourseId(),"Final Test", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"));
            mAssessmentDAO.insertAssessment(assessment2);
            AssessmentEntity assessment3 = new AssessmentEntity(3, course2.getCourseId(),"Final Test", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"));
            mAssessmentDAO.insertAssessment(assessment3);
            AssessmentEntity assessment4 = new AssessmentEntity(4, course3.getCourseId(),"Final Test", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"));
            mAssessmentDAO.insertAssessment(assessment4);
            AssessmentEntity assessment5 = new AssessmentEntity(5, course4.getCourseId(),"Final Test", DateConverter.toDate("01/01/2020"), DateConverter.toDate("01/01/2020"));
            mAssessmentDAO.insertAssessment(assessment5);

            return null;
        }
    }
}
