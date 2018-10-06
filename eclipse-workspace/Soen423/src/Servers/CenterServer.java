package Servers;

import Models.EmployeeRecord;
import Models.ManagerRecord;
import Models.Project;
import Repository.HashMapper;
import Repository.LogWriter;

import java.io.IOException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class CenterServer extends UnicastRemoteObject implements ServerInterface
{	
	private HashMapper map;
	private LogWriter logs;
	private int count=0;
	public CenterServer(String location, String path)throws RemoteException, IOException {
		String fileName = location+"_logs.txt";
		map = new HashMapper();	
		logs = new LogWriter(fileName, path);
		
	}
	@Override
	public synchronized String createMRecord(String firstName, String lastName, String employeeID, String mailID, Project project, String location) throws RemoteException {
		count++;
		String recordId="MR"+String.valueOf(count);
		ManagerRecord managerRec = new ManagerRecord(firstName, lastName, employeeID, mailID, recordId, location, project);
		map.put(lastName, managerRec);		
		return "The Manager Record is successfully added!";		
	}

	@Override
	public synchronized String createERecord(String firstName, String lastName, int employeeID, String mailID, String projectId)throws RemoteException {
		count++;
		String recordId="ER"+String.valueOf(count);
		EmployeeRecord empRec = new EmployeeRecord(firstName, lastName, employeeID, mailID, recordId, projectId);
		map.put(lastName, empRec);		
		return "The Employee Recored is successfully added!";
	}

	@Override
	public synchronized String getRecordCounts()throws RemoteException {
		// TODO Auto-generated method stub
		return String.valueOf(map.getCount());
	}

	@Override
	public synchronized String editRecord(String recordID, String fieldName, String newValue)throws RemoteException {
		// TODO Auto-generated method stub
		return map.edit(recordID, fieldName, newValue);
	}
	
	public synchronized void printData(String mId, String taskSuccess)throws RemoteException {
		// TODO Auto-generated method stub
			logs.writeLog(mId, taskSuccess);			
		
	}
}
