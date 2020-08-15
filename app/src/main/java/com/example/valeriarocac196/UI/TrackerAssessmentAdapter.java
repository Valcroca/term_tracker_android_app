package com.example.valeriarocac196.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.Entities.AssessmentEntity;
import com.example.valeriarocac196.Entities.CourseEntity;
import com.example.valeriarocac196.R;

import java.util.List;

public class TrackerAssessmentAdapter extends RecyclerView.Adapter<TrackerAssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentsTotalItemView;
        private final TextView assessmentsPlanToTakeItemView;
        private final TextView assessmentsFailedItemView;
        private final TextView assessmentsPassedItemView;

        private AssessmentViewHolder(View itemView) {
            super(itemView);
            assessmentsTotalItemView = itemView.findViewById(R.id.totalAssessments);
            assessmentsPlanToTakeItemView = itemView.findViewById(R.id.assessmentsPlanToTake);
            assessmentsFailedItemView = itemView.findViewById(R.id.assessmentsFailed);
            assessmentsPassedItemView = itemView.findViewById(R.id.assessmentsPassed);
        }
    }

    private List<AssessmentEntity> mAssessments;
    private int mAssessmentsPlanToTake;
    private int mAssessmentsFailed;
    private int mAssessmentsPassed;
    private final LayoutInflater mInflater;
    private final Context context;

    public TrackerAssessmentAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.tracker_assessment_list_item, parent, false);
        return new TrackerAssessmentAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {

    }

    public void setAssessments(List<AssessmentEntity> courses) {
        mAssessments = courses;
        setPlanToTake(mAssessments);
        setFailed(mAssessments);
        setInPassed(mAssessments);
        notifyDataSetChanged();
    }

    private int setInPassed(List<AssessmentEntity> mAssessments) {
        mAssessmentsPassed = 0;
        for(AssessmentEntity a : mAssessments) {
            if (a.getStatus().equals("passed")) {
                mAssessmentsPassed += 1;
            }
        }
        return mAssessmentsPassed;
    }

    public int getAssessmentsPassed() {
        return mAssessmentsPassed;
    }

    private int setFailed(List<AssessmentEntity> mAssessments) {
        mAssessmentsFailed = 0;
        for(AssessmentEntity a : mAssessments) {
            if (a.getStatus().equals("failed")) {
                mAssessmentsFailed += 1;
            }
        }
        return mAssessmentsFailed;
    }

    public int getAssessmentsFailed() {
        return mAssessmentsFailed;
    }

    private int setPlanToTake(List<AssessmentEntity> mAssessments) {
        mAssessmentsPlanToTake = 0;
        for(AssessmentEntity a : mAssessments) {
            if (a.getStatus().equals("plan to take")) {
                mAssessmentsPlanToTake += 1;
            }
        }
        return mAssessmentsPlanToTake;
    }

    public int getAssessmentsPlanToTake() {
        return mAssessmentsPlanToTake;
    }

    @Override
    public int getItemCount() {
        if (mAssessments != null)
            return mAssessments.size();
        else return 0;
    }
}
