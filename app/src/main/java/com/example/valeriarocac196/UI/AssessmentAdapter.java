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
import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentNameItemView;
//        private final TextView assessmentStatusItemView;
        private final TextView assessmentStartDateItemView;
//        private final TextView assessmentAlertStartDateItemView;
        private final TextView assessmentDueDateItemView;
//        private final TextView assessmentAlertDueDateItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentNameItemView = itemView.findViewById(R.id.assessmentNameTextView);
//            assessmentStatusItemView = itemView.findViewById(R.id.assessmentStatusTextView);
            assessmentStartDateItemView = itemView.findViewById(R.id.assessmentStartDateTextView);
//            assessmentAlertStartDateItemView = itemView.findViewById(R.id.assessmentAlertStartDateTextVieStart);
            assessmentDueDateItemView = itemView.findViewById(R.id.assessmentDueDateTextView);
//            assessmentAlertDueDateItemView = itemView.findViewById(R.id.assessmentAlertDueDateTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final AssessmentEntity current = mAssessments.get(position);
                    Intent intent = new Intent(context, EditAssessmentActivity.class);
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("assessmentCourseId", current.getAssessmentCourseId());
                    intent.putExtra("assessmentName", current.getName());
                    intent.putExtra("assessmentStatus", current.getStatus());
                    intent.putExtra("assessmentStartDate", DateConverter.formatDateString(current.getStartDate().toString()));
                    intent.putExtra("assessmentAlertStartDate", DateConverter.formatDateString(current.getAlertStartDate().toString()));
                    intent.putExtra("assessmentDueDate", DateConverter.formatDateString(current.getDueDate().toString()));
                    intent.putExtra("assessmentAlertDueDate", DateConverter.formatDateString(current.getAlertDueDate().toString()));
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
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssessmentViewHolder holder, int position) {
        if (mAssessments != null) {
            AssessmentEntity current = mAssessments.get(position);
            holder.assessmentNameItemView.setText(current.getName());
//            holder.assessmentStatusItemView.setText(current.getStatus());//
            holder.assessmentStartDateItemView.setText("Start Date: " + DateConverter.formatDateString(current.getStartDate().toString()));
//            holder.assessmentAlertStartDateItemView.setText("Start Date Alarm: " + DateConverter.formatDateString(current.getAlertStartDate().toString()));
            holder.assessmentDueDateItemView.setText("Due Date: " + DateConverter.formatDateString(current.getDueDate().toString()));
//            holder.assessmentAlertDueDateItemView.setText("Due Date Alarm: " + DateConverter.formatDateString(current.getAlertDueDate().toString()));
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
