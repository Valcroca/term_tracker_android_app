package com.example.valeriarocac196.UI;

import android.content.ContentProvider;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.R;

import java.util.List;

public class TrackerTermAdapter extends RecyclerView.Adapter<TrackerTermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termsTotalItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termsTotalItemView = itemView.findViewById(R.id.totalTerms);
        }
    }

    private List<TermEntity> mTerms;
    private final LayoutInflater mInflater;
    private final Context context;

    public TrackerTermAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.tracker_term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerTermAdapter.TermViewHolder holder, int position) {
        if (mTerms != null) {
            holder.termsTotalItemView.setText(String.valueOf(mTerms.size()));
        } else {
            // Covers the case of data not being ready yet.
            holder.termsTotalItemView.setText("No Terms");
        }
    }

    public void setTerms(List<TermEntity> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mTerms != null)
            return mTerms.size();
        else return 0;
    }
}
