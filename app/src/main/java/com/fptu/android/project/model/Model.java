package com.fptu.android.project.model;

public class Model {
    private  String pid, pimg, pprice, pname;
    private String image, status;

    public Model() {
    }

    public Model(String pimg, String pprice, String pname) {
        this.pimg = pimg;
        this.pprice = pprice;
        this.pname = pname;
    }

    public Model(String pid, String pimg, String pprice, String pname) {
        this.pid = pid;
        this.pimg = pimg;
        this.pprice = pprice;
        this.pname = pname;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPimg() {
        return pimg;
    }

    public void setPimg(String pimg) {
        this.pimg = pimg;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public String toString() {
        return "Model{" +
                "pid='" + pid + '\'' +
                ", pimg='" + pimg + '\'' +
                ", pprice='" + pprice + '\'' +
                ", pname='" + pname + '\'' +
                '}';
    }
}
