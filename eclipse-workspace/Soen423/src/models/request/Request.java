package models.request;

import java.io.Serializable;

import models.Project;

public class Request implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private long sequenceNum;
	
	public Request() {
		super();
	}
	
	public Request(long sequenceNum) {
		super();
		this.sequenceNum = sequenceNum;
	}
	
	public long getSequenceNum() {
		return sequenceNum;
	}
	public void setSequenceNum(long sequenceNum) {
		this.sequenceNum = sequenceNum;
	}
}