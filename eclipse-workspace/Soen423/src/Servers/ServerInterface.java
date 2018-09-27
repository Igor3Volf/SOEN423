package Servers;

import Models.Project;

public interface ServerInterface {
	String createMRecord(String firstName, String lastName, String employeeID, String mailID, Project project, String location);
	String createERecord(String firstName, String lastName, String employeeID, String mailID, Project project);
	String getRecordCounts();
	String editRecord(String recordID, String fieldName, String newValue);
}
