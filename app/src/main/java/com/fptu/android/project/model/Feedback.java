package com.fptu.android.project.model;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable {

    private int feedback_id;
    private String user_name;
    private int product_id;
    private String description;
    private float rate;
    private String feedback_date;

    public Feedback(String user_name,String description, float rate, String feedback_date ){
        this.user_name = user_name;
        this.description = description;
        this.rate = rate;
        this.feedback_date = feedback_date;
    }

    public Feedback(int feedback_id, String user_name, int product_id, String description, float rate, String feedback_date) {
        this.feedback_id = feedback_id;
        this.user_name = user_name;
        this.product_id = product_id;
        this.description = description;
        this.rate = rate;
        this.feedback_date = feedback_date;
    }

    public Feedback(String description, float rate, String feedback_date){
        this.description = description;
        this.rate = rate;
        this.feedback_date = feedback_date;
    }

    public int getFeedback_id() {
        return feedback_id;
    }

    public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_id) {
        this.user_name = user_id;
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
