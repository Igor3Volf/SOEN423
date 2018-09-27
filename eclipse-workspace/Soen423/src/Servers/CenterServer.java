package Servers;

import Models.Project;
import java.rmi.*;
import java.rmi.server.*;

public class CenterServer implements ServerInterface{

	String location;
	public CenterServer(String location) {
		this.location=location;
	}
	@Override
	public String createMRecord(String firstName, String lastName, String employeeID, String mailID, Project project,
			String location) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public String createERecord(String firstName, String lastName, String employeeID, String mailID, Project project) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRecordCounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editRecord(String recordID, String fieldName, String newValue) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String [] args) 
	{
			
	}

}
