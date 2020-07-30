package com.example.valeriarocac196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.EditAssessmentActivity;
import com.example.valeriarocac196.EditCourseActivity;
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentNameItemView;
        private final TextView assessmentInfoItemView;
        private final TextView assessmentStatusItemView;
        private final TextView assessmentDueDateItemView;
        private final TextView assessmentAlertDateItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentNameItemView = itemView.findViewById(R.id.assessmentNameTextView);
            assessmentInfoItemView = itemView.findViewById(R.id.assessmentInfoTextView);
            assessmentStatusItemView = itemView.findViewById(R.id.assessmentStatusTextView);
            assessmentDueDateItemView = itemView.findViewById(R.id.assessmentDueDateTextView);
            assessmentAlertDateItemView = itemView.findViewById(R.id.assessmentAlertDateTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessments.get(position);
                    Intent intent = new Intent(context, EditAssessmentActivity.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("assessmentCourseId", current.getAssessmentCourseId());
                    intent.putExtra("assessmentName", current.getName());
                    intent.putExtra("assessmentInfo", current.getInfo());
                    intent.putExtra("assessmentStatus", current.getStatus());
                    intent.putExtra("assessmentDueDate", current.getDueDate());
                    intent.putExtra("assessmentAlertDate", current.getAlertDate());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<AssessmentEntity> mAssessments;
    private final LayoutInflater mInflater;
    private final Context context;

    public AssessmentAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            AssessmentEntity current = mAssessments.get(position);
            holder.assessmentNameItemView.setText(current.getName());
            holder.assessmentInfoItemView.setText("Info: " + current.getInfo());
            holder.assessmentStatusItemView.setText("Status: " + current.getStatus());
            holder.assessmentDueDateItemView.setText("Due Date: " + DateConverter.formatDateString(current.getDueDate().toString()));
            holder.assessmentAlertDateItemView.setText("Alarm Date: " + DateConverter.formatDateString(current.getAlertDate().toString()));
        } else {
            // Covers the case of data not being ready yet.
            holder.assessmentNameItemView.setText("No Assessments");

        }
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }

    public void setAssessments(List<AssessmentEntity> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
}
