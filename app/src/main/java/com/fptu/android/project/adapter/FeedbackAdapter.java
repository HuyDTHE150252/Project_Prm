package com.fptu.android.project.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Feedback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFeedback(position);
            }
        });
    }

    private void deleteFeedback(int position) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();;
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Delete");
        alert.setMessage("Are you sure you want to delete?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                firestore.collection("Feedback")
                        .document(listFeedback.get(position).getFeedback_id())
                        .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    listFeedback.remove(listFeedback.get(position));
                                    notifyItemChanged(position);
                                    notifyDataSetChanged();

                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



                dialog.dismiss();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alert.show();
    }

    @Override
    public int getItemCount() {
        return listFeedback.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder {

        private TextView feedback_username;
        private TextView feedback_description;
        private RatingBar feedback_rate;
        private TextView feedback_date;
        private Button btnDelete;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            feedback_username = itemView.findViewById(R.id.feedback_username);
            feedback_description = itemView.findViewById(R.id.feedback_description);
            feedback_rate = itemView.findViewById(R.id.feedback_rating);
            feedback_date = itemView.findViewById(R.id.feedback_date);
            btnDelete=itemView.findViewById(R.id.btnDeleteFeedback);
        }
    }
}
