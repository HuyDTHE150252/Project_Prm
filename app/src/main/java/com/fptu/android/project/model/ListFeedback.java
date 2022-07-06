package com.fptu.android.project.model;

public class ListFeedback {
    String uid;
    String rating;
    String commentContent;

    public ListFeedback(String uid, String rating, String commentContent) {
        this.uid = uid;
        this.rating = rating;
        this.commentContent = commentContent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
