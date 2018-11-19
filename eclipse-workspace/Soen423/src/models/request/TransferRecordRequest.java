package models.request;

public class TransferRecordRequest extends Request{
	private static final long serialVersionUID = 1L;
	
	String managerID; 
	String recordID; 
	String remoteCenterServerName;
	
	public TransferRecordRequest(String managerID, String recordID, String remoteCenterServerName) {
		super();
		this.managerID = managerID;
		this.recordID = recordID;
		this.remoteCenterServerName = remoteCenterServerName;
	}

	public String getManagerID() {
		return managerID;
	}

	public void setManagerID(String managerID) {
		this.managerID = managerID;
	}

	public String getRecordID() {
		return recordID;
	}

	public void setRecordID(String recordID) {
		this.recordID = recordID;
	}

	public String getRemoteCenterServerName() {
		return remoteCenterServerName;
	}

	public void setRemoteCenterServerName(String remoteCenterServerName) {
		this.remoteCenterServerName = remoteCenterServerName;
	}
	
}