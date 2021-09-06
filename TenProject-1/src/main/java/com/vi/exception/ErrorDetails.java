package com.vi.exception;

import java.util.Date;

public class ErrorDetails {

	public String mesage;
	public Date timeStamp;
	public String details;
	
	public ErrorDetails(String mesage, Date timeStamp, String details) {
		super();
		this.mesage = mesage;
		this.timeStamp = timeStamp;
		this.details = details;
	}

	public String getMesage() {
		return mesage;
	}
	
	public void setMesage(String mesage) {
		this.mesage = mesage;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	
	
	
}
