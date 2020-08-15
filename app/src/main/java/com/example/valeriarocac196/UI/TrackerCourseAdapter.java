package com.example.valeriarocac196.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.R;

import java.util.List;

public class TrackerCourseAdapter extends RecyclerView.Adapter<TrackerCourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView coursesTotalItemView;
        private final TextView coursesPlanToTakeItemView;
        private final TextView coursesDroppedItemView;
        private final TextView coursesInProgressItemView;
        private final TextView coursesCompletedItemView;

        private CourseViewHolder(View itemView) {
            super(itemView);
            coursesTotalItemView = itemView.findViewById(R.id.totalCourses);
            coursesPlanToTakeItemView = itemView.findViewById(R.id.coursesPlanToTake);
            coursesDroppedItemView = itemView.findViewById(R.id.coursesDropped);
            coursesInProgressItemView = itemView.findViewById(R.id.coursesInProgress);
            coursesCompletedItemView = itemView.findViewById(R.id.coursesCompleted);
        }
    }

    private List<CourseEntity> mCourses;
    private int mCoursesPlanToTake;
    private int mCoursesDropped;
    private int mCoursesInProgress;
    private int mCoursesCompleted;
    private final LayoutInflater mInflater;
    private final Context context;

    public TrackerCourseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.tracker_course_list_item, parent, false);
        return new TrackerCourseAdapter.CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerCourseAdapter.CourseViewHolder holder, int position) {
//        if (mCourses != null) {
//            final CourseEntity current = mCourses.get(position);
//            holder.coursesTotalItemView.setText(String.valueOf(mCourses.size()));
//            holder.coursesPlanToTakeItemView.setText(mCoursesPlanToTake);
//            holder.coursesDroppedItemView.setText(mCoursesDropped);
//            holder.coursesInProgressItemView.setText(mCoursesInProgress);
//            holder.coursesCompletedItemView.setText(mCoursesCompleted);
//        } else {
//            // Covers the case of data not being ready yet.
//            holder.coursesTotalItemView.setText("No Courses");
//        }
    }

    public void setCourses(List<CourseEntity> courses) {
        mCourses = courses;
        setPlanToTake(mCourses);
        setDropped(mCourses);
        setInProgress(mCourses);
        setCompleted(mCourses);
        notifyDataSetChanged();
    }

    public int setPlanToTake(List<CourseEntity> courses) {
        mCoursesPlanToTake = 0;
        for(CourseEntity c : courses) {
            if (c.getStatus().equals("plan to take")) {
                mCoursesPlanToTake += 1;
            }
        }
        return mCoursesPlanToTake;
    }

    public int getCoursesPlanToTake(){
        return mCoursesPlanToTake;
    }

    public int setDropped(List<CourseEntity> courses) {
        mCoursesDropped = 0;
        for(CourseEntity c : courses) {
            if (c.getStatus().equals("dropped")) {
                mCoursesDropped += 1;
            }
        }
        return mCoursesDropped;
    }

    public int getCoursesDropped() {
        return mCoursesDropped;
    }

    public int setInProgress(List<CourseEntity> courses) {
        mCoursesInProgress = 0;
        for(CourseEntity c : courses) {
            if (c.getStatus().equals("in-progress")) {
                mCoursesInProgress += 1;
            }
        }
        return mCoursesInProgress;
    }

    public int getCoursesInProgress() {
        return mCoursesInProgress;
    }

    public int setCompleted(List<CourseEntity> courses) {
        mCoursesCompleted = 0;
        for(CourseEntity c : courses) {
            if (c.getStatus().equals("completed")) {
                mCoursesCompleted += 1;
            }
        }
        return mCoursesCompleted;
    }

    public int getCoursesCompleted() {
        return mCoursesCompleted;
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

}
