package com.fptu.android.project.model;

import java.io.Serializable;

public class Order implements Serializable {
    String productName;
//    String productPrice;
    String currentTime;
    String currentDate;
    String totalQuantity;
    String documentId;
    int totalPrice;
    String address;

    public Order() {
    }

    public Order(String productName, String currentDate, String address) {
        this.productName = productName;
        this.currentDate = currentDate;
        this.address = address;
    }

    public Order(String productName, String currentTime, String currentDate, String totalQuantity, int totalPrice) {
        this.productName = productName;
//        this.productPrice = productPrice;
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "MyCart{" +
                "productName='" + productName + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", currentDate='" + currentDate + '\'' +
                ", totalQuantity='" + totalQuantity + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
