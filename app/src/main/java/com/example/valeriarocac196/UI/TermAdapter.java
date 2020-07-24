package com.example.valeriarocac196.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.valeriarocac196.EditTermActivity;
import com.example.valeriarocac196.Database.DateConverter;
import com.example.valeriarocac196.Entities.TermEntity;
import com.example.valeriarocac196.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termTitleItemView;
        private final TextView termStartItemView;
        private final TextView termEndItemView;

        private TermViewHolder(View itemView) {
            super(itemView);
            termTitleItemView = itemView.findViewById(R.id.termTitleTextView);
            termStartItemView = itemView.findViewById(R.id.termStartTextView);
            termEndItemView = itemView.findViewById(R.id.termEndTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final TermEntity current = mTerms.get(position);
                    Intent intent = new Intent(context, EditTermActivity.class);
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("termTitle", current.getTitle());
                    intent.putExtra("termStart", DateConverter.formatDateString(current.getStartDate().toString()));
                    intent.putExtra("termEnd", DateConverter.formatDateString(current.getEndDate().toString()));
                    intent.putExtra("position",position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<TermEntity> mTerms;
    private final LayoutInflater mInflater;
    private final Context context;

    public TermAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TermViewHolder holder, int position) {

        if (mTerms != null) {
            final TermEntity current = mTerms.get(position);
            holder.termTitleItemView.setText(current.getTitle());
            holder.termStartItemView.setText("Start: " + DateConverter.formatDateString(current.getStartDate().toString()));
            holder.termEndItemView.setText("End: " + DateConverter.formatDateString(current.getEndDate().toString()));

        } else {
            // Covers the case of data not being ready yet.
            holder.termTitleItemView.setText("No Terms");
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
