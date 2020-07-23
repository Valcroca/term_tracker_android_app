package com.example.valeriarocac196.Database;

import android.content.Context;
import android.os.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

@Database(entities = {TermEntity.class, CourseEntity.class, AssessmentEntity.class}, version = 4, exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class TrackerManagementDatabase extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();


    private static volatile TrackerManagementDatabase INSTANCE;

    static TrackerManagementDatabase getDatabase(final Context context) {
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
            TermEntity term= new TermEntity(1, "First Term", DateConverter.toDate("01/01/2020"), DateConverter.toDate("06/30/2020"), false);
            mTermDAO.insertTerm(term);
            term=new TermEntity(2, "Second Term", DateConverter.toDate("07/01/2020"), DateConverter.toDate("12/31/2020"), true);
            mTermDAO.insertTerm(term);
            term=new TermEntity(3, "Third Term", DateConverter.toDate("01/01/2021"), DateConverter.toDate("06/30/2021"), false);
            mTermDAO.insertTerm(term);
            //create courses
            CourseEntity course = new CourseEntity(1, 1, "First Course", null, null, "status", "Mable Nice", "801-333-6655", "mabel@gmail.com", "notes", null);
            mCourseDAO.insert(course);
            course=new CourseEntity(2, 1, "Second Course", null, null, "status", "Mable Nice", "801-333-6655", "mabel@gmail.com", "notes", null);
            mCourseDAO.insert(course);
            course=new CourseEntity(3, 2, "Third Course", null, null, "status", "Mable Nice", "801-333-6655", "mabel@gmail.com","notes", null);
            mCourseDAO.insert(course);
            course=new CourseEntity(4, 2, "Fourth Course", null, null, "status", "Mable Nice", "801-333-6655", "mabel@gmail.com", "notes", null);
            mCourseDAO.insert(course);
            //create assessments
            AssessmentEntity assessment = new AssessmentEntity(1,1,"Mid-term Test", "info", "One day prior", "status", null, null);
            mAssessmentDAO.insert(assessment);
            assessment= new AssessmentEntity(2,1,"Final Test", "info", "One day prior", "status", null, null);
            mAssessmentDAO.insert(assessment);
            assessment= new AssessmentEntity(3,2,"Final Test", "info", "One day prior", "status", null, null);
            mAssessmentDAO.insert(assessment);
            assessment= new AssessmentEntity(4,3,"Final Test", "info", "One day prior", "status", null, null);
            mAssessmentDAO.insert(assessment);
            assessment= new AssessmentEntity(5,4,"Final Test", "info", "One day prior", "status", null, null);
            mAssessmentDAO.insert(assessment);

            return null;
        }
    }
}
