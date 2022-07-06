package com.fptu.android.project.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.ProductDetailActivity;
import com.fptu.android.project.model.ListFeedback;
import com.fptu.android.project.model.Product;

import java.util.List;

public class ListFeedbackAdapter extends RecyclerView.Adapter<ListFeedbackAdapter.ProductViewHolder>{

    private Context context;
    private List<ListFeedback> listFeedbacks;

    public ListFeedbackAdapter(Context context) {
        this.context = context;
    }

    public ListFeedbackAdapter(Context context, List<ListFeedback> listFeedbacks) {
        this.context = context;
        this.listFeedbacks = listFeedbacks;
    }

    public void setData(List<ListFeedback> list) {
        this.listFeedbacks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListFeedbackAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_feedback_item, parent, false);
        return new ListFeedbackAdapter.ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFeedbackAdapter.ProductViewHolder holder, int position) {
       ListFeedback product = listFeedbacks.get(position);
        if(product == null){
            return;
        }

        holder.uName.setText(product.getUid());
        holder.txtRating.setText(product.getRating());
        holder.txtComment.setText(product.getCommentContent());
        holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                holder.txtRating.setText("Rate: "+ratingBar);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listFeedbacks.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {


        private TextView uName,txtComment,txtRating;
        RatingBar ratingBar;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRating=itemView.findViewById(R.id.txtStar);
           uName=itemView.findViewById(R.id.txtNameRating);
           txtComment=itemView.findViewById(R.id.txtComment);
           ratingBar=itemView.findViewById(R.id.ratingBar);
        }
    }
}
