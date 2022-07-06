package com.fptu.android.project.model;

import java.util.Map;

public class ListFeedback {
    String uid;
    String rating;
    String commentContent;
    String pid;
    Map<String,Object> commonTime;

    public ListFeedback() {
    }

    public ListFeedback(String uid, String rating, String commentContent, String pid) {
        this.uid = uid;
        this.rating = rating;
        this.commentContent = commentContent;
        this.pid = pid;
    }

    public ListFeedback(String uid, String rating, String commentContent) {
        this.uid = uid;
        this.rating = rating;
        this.commentContent = commentContent;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
