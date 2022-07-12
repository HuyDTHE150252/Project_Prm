package com.fptu.android.project.model;

import java.io.Serializable;

public class Category implements Serializable {

    private String cate_name;
    private String url;
    private String type;

    public Category(){}

    public Category(String cate_name, String url) {
        this.cate_name = cate_name;
        this.url = url;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
