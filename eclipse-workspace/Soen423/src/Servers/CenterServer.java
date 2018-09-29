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
	private ArrayList<Object> rec;
	private String task;
	
	public CenterServer(String location)throws RemoteException, IOException {
		rec = new ArrayList<Object>();
		String fileName = location+"_logs.txt";
		map = new HashMapper();	
		logs = new LogWriter(fileName);
		
	}
	@Override
	public synchronized String createMRecord(String firstName, String lastName, String employeeID, String mailID, Project project, String location) throws RemoteException {
		task = "Create Manager Record";
		String recordId="MR";		
		ManagerRecord managerRec = new ManagerRecord(firstName, lastName, employeeID, mailID, recordId, location, project);
		rec.add(managerRec);
		map.put(lastName, rec);		
		rec.removeAll(rec);		
		return "The Manager Record is successfully added!";		
	}

	@Override
	public synchronized String createERecord(String firstName, String lastName, int employeeID, String mailID, String projectId)throws RemoteException {
		String recordId="ER";		
		EmployeeRecord empRec = new EmployeeRecord(firstName, lastName, employeeID, mailID, recordId, projectId);
		rec.add(empRec);
		map.put(lastName, rec);		
		rec.removeAll(rec);
		return "The Employee Recored is successfully added!";
	}

	@Override
	public String getRecordCounts()throws RemoteException {
		// TODO Auto-generated method stub
		
		return String.valueOf(map.getCount());
	}

	@Override
	public synchronized String editRecord(String recordID, String fieldName, String newValue)throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public synchronized void printData(String mId, String taskSuccess)throws RemoteException {
		// TODO Auto-generated method stub
		if(taskSuccess.equals("The Manager Record is successfully added!")||taskSuccess.equals("The Employee Recored is successfully added!")) {
			logs.writeLog(mId, taskSuccess);		
		}
		else {
			logs.writeLog(mId, "Task failed");
		}
		
	}
}
