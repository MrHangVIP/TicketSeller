package com.zy.ticketseller.bean;

import java.io.Serializable;

/**
 * Created by Songzhihang on 2018/2/25.
 * 票务实体
 */
public class TicketItem implements Serializable{
    private int ticketId;
    private int bussinessId;
    private int typeId;
    private double price;
    private String title;
    private String biref;
    private String indexpicUrl;
    private String createTime;
    private String address;
    private String startTime;
    private long startTimeStmp;
    private String finishTime;
    private long finishTimeStmp;

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public int getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(int bussinessId) {
        this.bussinessId = bussinessId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBiref() {
        return biref;
    }

    public void setBiref(String biref) {
        this.biref = biref;
    }

    public String getIndexpicUrl() {
        return indexpicUrl;
    }

    public void setIndexpicUrl(String indexpicUrl) {
        this.indexpicUrl = indexpicUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public long getStartTimeStmp() {
        return startTimeStmp;
    }

    public void setStartTimeStmp(long startTimeStmp) {
        this.startTimeStmp = startTimeStmp;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public long getFinishTimeStmp() {
        return finishTimeStmp;
    }

    public void setFinishTimeStmp(long finishTimeStmp) {
        this.finishTimeStmp = finishTimeStmp;
    }
}
