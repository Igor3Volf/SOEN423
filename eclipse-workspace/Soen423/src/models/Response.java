package models;

import java.io.Serializable;

public class Response implements Serializable {
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getReplicID() {
		return replicaID;
	}

	public void setReplicID(int replicID) {
		this.replicaID = replicID;
	}

	public long getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Response(String message, int replicaID, long sequenceNumber) {
		super();
		this.message = message;
		this.replicaID = replicaID;
		this.sequenceNumber = sequenceNumber;
	}

	private String message;
	private int replicaID;
	private long sequenceNumber;
}
