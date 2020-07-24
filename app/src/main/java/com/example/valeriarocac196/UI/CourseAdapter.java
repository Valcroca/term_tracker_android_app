package com.example.valeriarocac196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.AddCourseActivity;
import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.EditCourseActivity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseNameItemView;
        private final TextView courseStartItemView;
        private final TextView courseEndItemView;


        private CourseViewHolder(View itemView) {
            super(itemView);
            courseNameItemView = itemView.findViewById(R.id.courseNameTextView);
            courseStartItemView = itemView.findViewById(R.id.courseStartTextView);
            courseEndItemView = itemView.findViewById(R.id.courseEndTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourses.get(position);
                    Intent intent = new Intent(context, EditCourseActivity.class);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("courseName", current.getName());
                    intent.putExtra("courseStart", current.getStartDate());
                    intent.putExtra("courseEnd", current.getEndDate());
                    intent.putExtra("position", position);
                    intent.putExtra("courseTermId", current.getCourseTermId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;

    public CourseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        if (mCourses != null) {
            CourseEntity current = mCourses.get(position);
            holder.courseNameItemView.setText(current.getName());
            holder.courseStartItemView.setText("Start: " + DateConverter.formatDateString(current.getStartDate().toString()));
            holder.courseEndItemView.setText("End: " + DateConverter.formatDateString(current.getEndDate().toString()));
        } else {
            // Covers the case of data not being ready yet.
            holder.courseNameItemView.setText("No Courses");

        }
    }

    @Override
    public int getItemCount() {
        if (mCourses != null)
            return mCourses.size();
        else return 0;
    }

    public void setCourses(List<CourseEntity> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
}
