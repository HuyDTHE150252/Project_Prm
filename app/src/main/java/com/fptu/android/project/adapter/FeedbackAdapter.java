package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {

    private Context context;
    private List<Feedback> listFeedback;

    public FeedbackAdapter(Context context) {
        this.context = context;
    }

    public FeedbackAdapter(Context context, List<Feedback> listFeedback) {
        this.context = context;
        this.listFeedback = listFeedback;

    }

    public void setData(List<Feedback> list) {
        this.listFeedback = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_item, parent, false);
        return new FeedbackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Feedback feedback = listFeedback.get(position);
        if (feedback == null) {
            return;
        }
        holder.feedback_username.setText(feedback.getUser_name());
        holder.feedback_description.setText(feedback.getDescription());
        holder.feedback_rate.setRating(feedback.getRate());
        holder.feedback_date.setText(feedback.getFeedback_date());

    }


    @Override
    public int getItemCount() {
        if (listFeedback != null) {
            return listFeedback.size();
        }
        return 0;
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder {

        private TextView feedback_username;
        private TextView feedback_description;
        private RatingBar feedback_rate;
        private TextView feedback_date;


        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            feedback_username = itemView.findViewById(R.id.feedback_username);
            feedback_description = itemView.findViewById(R.id.feedback_description);
            feedback_rate = itemView.findViewById(R.id.feedback_rating);
            feedback_date = itemView.findViewById(R.id.feedback_date);

        }
    }
}
