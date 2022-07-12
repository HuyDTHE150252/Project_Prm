package com.fptu.android.project.model;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {

    private String feedback_id;
    private String user_name;
    private int product_id;
    private String description;
    private float rate;
    private String feedback_date;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Feedback() {
    }

    public Feedback(String user_name, String description, float rate, String feedback_date ){
        this.user_name = user_name;
        this.description = description;
        this.rate = rate;
        this.feedback_date = feedback_date;
    }

    public String getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(String feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(String feedback_date) {
        this.feedback_date = feedback_date;
    }
}
