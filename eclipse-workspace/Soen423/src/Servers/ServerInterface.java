package Servers;

import java.rmi.Remote;
import java.rmi.RemoteException;

import Models.Project;

public interface ServerInterface extends Remote {
	String createMRecord(String firstName, String lastName, String employeeID, String mailID, Project project, String location) throws RemoteException;
	String createERecord(String firstName, String lastName, int employeeID, String mailID, String projectId)throws RemoteException;
	String getRecordCounts()throws RemoteException;
	String editRecord(String recordID, String fieldName, String newValue)throws RemoteException;
	void printData(String mId, String taskSuccess)throws RemoteException;
}
