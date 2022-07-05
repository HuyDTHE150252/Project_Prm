package com.fptu.android.project.model;

import java.util.List;

public class Shipper {
    private String uId;
    private String shipId;
    List<Order> order;
    private String endTime;
    private String status;

    public Shipper() {
    }

    @Override
    public String toString() {
        return "Shipper{" +
                "uId='" + uId + '\'' +
                ", shipId='" + shipId + '\'' +
                ", order=" + order +
                ", endTime='" + endTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getShipId() {
        return shipId;
    }

    public void setShipId(String shipId) {
        this.shipId = shipId;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Shipper(String uId, String shipId, List<Order> order, String endTime, String status) {
        this.uId = uId;
        this.shipId = shipId;
        this.order = order;
        this.endTime = endTime;
        this.status = status;
    }
}