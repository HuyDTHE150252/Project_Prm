package com.fptu.android.project.model;

import java.io.Serializable;

public class Category implements Serializable {

    private int cate_id;
    private String cate_name;
    private String url;

    public Category(){}

    public Category(int cate_id, String cate_name, String url) {
        this.cate_id = cate_id;
        this.cate_name = cate_name;
        this.url = url;
    }

    public int getCate_id() {
        return cate_id;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
