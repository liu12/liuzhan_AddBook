package com.example.addbook.call_log;

public class CallLogModule {
	private String callName;
	private String callPhone;
	private int callDuration;
	private String callDate;
	private String callType;
	public CallLogModule(String callName, String callPhone, int callDuration, String callDate, String callType) {
		super();
		this.callName = callName;
		this.callPhone = callPhone;
		this.callDuration = callDuration;
		this.callDate = callDate;
		this.callType = callType;
	}
	public CallLogModule() {
		super();
	}
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public String getCallPhone() {
		return callPhone;
	}
	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}
	public int getCallDuration() {
		return callDuration;
	}
	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}
	public String getCallDate() {
		return callDate;
	}
	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}
	public String getCallType() {
		return callType;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	
}
