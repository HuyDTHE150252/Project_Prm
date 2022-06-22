package com.fptu.android.project.model;

import java.io.Serializable;

public class Category implements Serializable {

    private int resourceImage;
    private String cate_name;

    public Category(int resourceImage, String cate_name) {
        this.resourceImage = resourceImage;
        this.cate_name = cate_name;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void setResourceImage(int resourceImage) {
        this.resourceImage = resourceImage;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "resourceImage=" + resourceImage +
                ", cate_name='" + cate_name + '\'' +
                '}';
    }
}
