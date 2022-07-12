package com.fptu.android.project.model;

import java.io.Serializable;

public class Order implements Serializable {

    String productName;
    String currentTime;
    String currentDate;
    String totalQuantity;
    String documentId;
    int totalPrice;
    String address;
    String paymentState;
    String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Order() {
    }
    public Order(String documentId) {
        this.documentId=documentId;
    }

    public Order(String currentTime, String currentDate, String documentId, String address,String orderStatus) {
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.documentId = documentId;
        this.address = address;
        this.orderStatus=orderStatus;
    }

    public Order(String productName, String totalQuantity, int totalPrice) {
        this.productName = productName;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
    }

    public Order(String productName, String currentTime, String currentDate, String totalQuantity, String documentId, int totalPrice, String address, String paymentState) {
        this.productName = productName;
        this.currentTime = currentTime;
        this.currentDate = currentDate;
        this.totalQuantity = totalQuantity;
        this.documentId = documentId;
        this.totalPrice = totalPrice;
        this.address = address;
        this.paymentState = paymentState;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
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
