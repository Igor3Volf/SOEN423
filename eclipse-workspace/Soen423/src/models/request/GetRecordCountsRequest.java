package models.request;

public class GetRecordCountsRequest extends Request{
	private static final long serialVersionUID = 1L;
	
	private String managerId;
	
	public GetRecordCountsRequest(String managerId) {
		super();
		this.managerId = managerId;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	
}