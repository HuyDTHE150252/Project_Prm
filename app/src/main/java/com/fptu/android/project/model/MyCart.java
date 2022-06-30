package com.fptu.android.project.model;

public class MyCart {
    String productName;
//    String productPrice;
    String currentTime;
    String currentDate;
    String totalQuantity;
    int totalPrice;

    public MyCart() {
    }

    public MyCart(String productName, String currentTime, String currentDate, String totalQuantity, int totalPrice) {
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

//    public String getProductPrice() {
//        return productPrice;
//    }
//
//    public void setProductPrice(String productPrice) {
//        this.productPrice = productPrice;
//    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentDate() {
        return currentDate;
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
