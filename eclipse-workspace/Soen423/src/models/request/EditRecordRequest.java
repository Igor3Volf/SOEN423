package models.request;

public class EditRecordRequest extends Request{
	private static final long serialVersionUID = 1L;
	
	String managerID;
	String recordID; 
	String fieldName; 
	String newValue;
	
	public EditRecordRequest(String managerID, String recordID, String fieldName, String newValue) {
		super();
		this.managerID = managerID;
		this.recordID = recordID;
		this.fieldName = fieldName;
		this.newValue = newValue;
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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
}

