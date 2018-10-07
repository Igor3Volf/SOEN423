package Servers;

import Models.EmployeeRecord;
import Models.ManagerRecord;
import Models.Project;
import Repository.HashMapper;
import Repository.LogWriter;
import UDP.UDP_CA;
import UDP.UDP_UK;
import UDP.UDP_US;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class CenterServer extends UnicastRemoteObject implements ServerInterface {
	private HashMapper map;
	private LogWriter logs;
	private static int count;
	private String location;

	public CenterServer(String location, String path) throws RemoteException, IOException {
		String fileName = location + "_logs.txt";
		map = new HashMapper();
		logs = new LogWriter(fileName, path);
		this.location = location;
		
	}

	@Override
	public String createMRecord(String firstName, String lastName, String managerID, String mailID,		
			Project project, String location) throws RemoteException {
		String message;
		count++;
		synchronized(map) {
			String recordId = "MR" + String.valueOf(count);
			ManagerRecord managerRec = new ManagerRecord(firstName, lastName, managerID, mailID, recordId, location,
					project);
			
			int check = map.getCount();
			
			map.put(lastName, managerRec);
			if(map.getCount()>check) {
				message= "The Manager Record is successfully added!";
			}else {
				message="The Manager Recod was not added";
			}			
			return message;
		}	
	}

	@Override
	public String createERecord(String firstName, String lastName, int employeeID, String mailID,
			String projectId) throws RemoteException {
		String message;
		count++;
		synchronized(map) {
			String recordId = "ER" + String.valueOf(count);
			EmployeeRecord empRec = new EmployeeRecord(firstName, lastName, employeeID, mailID, recordId, projectId);
			int check = map.getCount();
			map.put(lastName, empRec);
			
			if(map.getCount()>check) {
				message= "The Empoyee Record is successfully added!";
			}else {
				message="The Empoyee Recod was not added";
			}			
			return message;
		}
	}

	public String getMapCount() throws RemoteException {
		if (map.getCount() < 1) {
			return "Map is empty";
		} else {
			return String.valueOf(map.getCount());
		}
	}

	@Override
	/*
	 * 2001 = UDP CA 
	 * 2002 = UDP US 
	 * 2003 = UDP UK
	 */
	public String getRecordCounts() throws RemoteException {
		// TODO Auto-generated method stub\
		String caCount = "", usCount = "", ukCount = "";		
		
		if (location.equals("Canadian")) {
			caCount = "CA " + map.getCount();

			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[1024];
				sendData = "".getBytes();
				// Requesting US
				DatagramPacket sendPacketUS = new DatagramPacket(sendData, sendData.length, IPAddress, 2002);
				clientSocket.send(sendPacketUS);
				DatagramPacket receivePacketUS = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacketUS);
				String countUsServer = new String(receivePacketUS.getData(),receivePacketUS.getOffset(),receivePacketUS.getLength());
				usCount = countUsServer;

				// Requesting UK				
				DatagramPacket sendPacketUK = new DatagramPacket(sendData, sendData.length, IPAddress, 2003);
				clientSocket.send(sendPacketUK);
				DatagramPacket receivePacketUK = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacketUK);
				String countUKServer = new String(receivePacketUK.getData(),receivePacketUK.getOffset(),receivePacketUK.getLength());
				ukCount = countUKServer;

				clientSocket.close();

			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (location.equals("American")) {

			usCount = "US " + map.getCount();
			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[1024];
				sendData = "".getBytes();
				// Requesting CA
				DatagramPacket sendPacketCA = new DatagramPacket(sendData, sendData.length, IPAddress, 2001);
				clientSocket.send(sendPacketCA);
				DatagramPacket receivePacketCA = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacketCA);
				String countCAServer = new String(receivePacketCA.getData(),receivePacketCA.getOffset(),receivePacketCA.getLength());
				caCount = countCAServer;

				// Requesting UK
				DatagramPacket sendPacketUK = new DatagramPacket(sendData, sendData.length, IPAddress, 2003);
				clientSocket.send(sendPacketUK);
				DatagramPacket receivePacketUK = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacketUK);
				String countUKServer = new String(receivePacketUK.getData(),receivePacketUK.getOffset(),receivePacketUK.getLength());
				ukCount = countUKServer;

				clientSocket.close();

			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (location.equals("English")) 
		{
			ukCount = "UK " + map.getCount();
			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				byte[] sendData = new byte[1024];
				byte[] receiveData = new byte[1024];
				sendData = "".getBytes();
				// Requesting US
				DatagramPacket sendPacketUS = new DatagramPacket(sendData, sendData.length, IPAddress, 2002);
				clientSocket.send(sendPacketUS);
				DatagramPacket receivePacketUS = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacketUS);
				String countUsServer = new String(receivePacketUS.getData(),receivePacketUS.getOffset(),receivePacketUS.getLength());
				usCount = countUsServer;
				
				// Requesting CA
				DatagramPacket sendPacketCA = new DatagramPacket(sendData, sendData.length, IPAddress, 2001);
				clientSocket.send(sendPacketCA);
				DatagramPacket receivePacketCA = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacketCA);
				String countCAServer = new String(receivePacketCA.getData(),receivePacketCA.getOffset(),receivePacketCA.getLength());
				caCount = countCAServer;
				clientSocket.close();

			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			return "There is nothing yet.";
		}
		
		String totalCount = "Total Records on Servers: " + caCount+ " " + usCount+ " " + ukCount;
		return totalCount;
	}

	@Override
	public String editRecord(String recordID, String fieldName, String newValue) throws RemoteException {
		synchronized(map) {
			return map.edit(recordID, fieldName, newValue);
		}
	}

	public void printData(String mId, String taskSuccess) throws RemoteException {
		synchronized(logs) {
			logs.writeLog(mId, taskSuccess);			
		}
	}
}
