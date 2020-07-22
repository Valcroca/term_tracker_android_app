package com.example.valeriarocac196.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.CourseActivity;
import com.example.valeriarocac196.CourseDetail;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.R;
import com.example.valeriarocac196.ViewModel.CourseViewModel;
import com.example.valeriarocac196.ViewModel.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;
        private final TextView courseItemView2;


        private CourseViewHolder(View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTextView);
            courseItemView2 = itemView.findViewById(R.id.courseTextView2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final CourseEntity current = mCourses.get(position);
                    Intent intent = new Intent(context, CourseDetail.class);
                    intent.putExtra("courseName", current.getName());
                    intent.putExtra("courseStart", current.getStartDate());
                    intent.putExtra("courseEnd", current.getEndDate());
                    intent.putExtra("position", position);
                    intent.putExtra("courseId", current.getCourseId());
                    intent.putExtra("termId", current.getCourseTermId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private final Context context;
    private List<CourseEntity> mCourses;

    public CourseAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context=context;
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
            holder.courseItemView.setText(current.getName());
            holder.courseItemView2.setText(Integer.toString(current.getCourseTermId()));
        } else {
            // Covers the case of data not being ready yet.
            holder.courseItemView.setText("No Courses");
            holder.courseItemView2.setText("No Courses");
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
