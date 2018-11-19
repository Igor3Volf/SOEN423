package models.request;

public class CreateERecordRequest extends Request{
	private static final long serialVersionUID = 1L;
	
	private String managerId;
	private String firstName;
	private String lastName;
	private int employeeID;
	private String mailID;
	private String projectID;
	
	public CreateERecordRequest(String managerId, String firstName, String lastName,
			int employeeID, String mailID, String projectID) {
		super();
		this.managerId = managerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.employeeID = employeeID;
		this.mailID = mailID;
		this.projectID = projectID;
	}

	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getMailID() {
		return mailID;
	}

	public void setMailID(String mailID) {
		this.mailID = mailID;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}
	
}