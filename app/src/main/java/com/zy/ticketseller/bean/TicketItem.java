package com.zy.ticketseller.bean;

import java.io.Serializable;

public class TicketItem implements Serializable {

	private int ticketId;

	private int businessId;

	private int typeId;

	private String title;

	private String biref;

	private String address;

	private String indexpicurl;

	private String createTime;

	private String duration;

	private double price;

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

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIndexpicurl() {
		return indexpicurl;
	}

	public void setIndexpicurl(String indexpicurl) {
		this.indexpicurl = indexpicurl;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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
