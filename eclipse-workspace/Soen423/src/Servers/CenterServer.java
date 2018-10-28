package Servers;

import CorbaModule.ManagerInterfacePOA;
import Models.EmployeeRecord;
import Models.ManagerRecord;
import CorbaModule.Project;
import Repository.HashMapper;
import Repository.IdGenerator;
import Repository.LogWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.Semaphore;

import org.omg.CORBA.ORB;

class UDP_Server extends Thread implements Runnable {

	private DatagramSocket serverSocket;
	private CenterServer server;
	private String location;

	public UDP_Server(int port, CenterServer server, String location)
			throws Exception {
		this.server = server;
		serverSocket = new DatagramSocket(port);
		if (location.equals("Canadian")) {
			this.location = "CA";

		} else if (location.equals("American")) {
			this.location = "US";

		} else if (location.equals("English")) {
			this.location = "UK";
		} else {
			this.location = "Uknown";
		}
	}

	@Override
	public void run() {
		try {
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			String message;
			while (true) {

				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				serverSocket.receive(receivePacket);
				receiveData = receivePacket.getData();
				ObjectInputStream iStream = new ObjectInputStream(
						new ByteArrayInputStream(receiveData));
				Object o = iStream.readObject();

				if (o instanceof String) {
					message = o.toString();

					String words[] = message.split(" ");
					if (words[0].equals("Count")) {
						message = getCount();
						sendData = (location + " " + message).getBytes();

					} else if (words[0].equals("Check")) {
						message = checkRecord(words[1]);
						sendData = (message).getBytes();
					} else {
						message = "Wrong UDP Request";
						sendData = (message).getBytes();

					}
				} else if (o instanceof EmployeeRecord) {
					message = passEmp((EmployeeRecord) o);
					sendData = message.getBytes();

				} else if (o instanceof ManagerRecord) {
					message = passMan((ManagerRecord) o);
					sendData = message.getBytes();
				}

				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private  String passEmp(EmployeeRecord e) {
		return server.addToMap(e);
	}

	private  String passMan(ManagerRecord m) {
		return server.addToMap(m);
	}

	private  String checkRecord(String recordId) {
		if (server.checkIfExist(recordId)) {
			return "This Records Exists!";
		} else {
			return "This Records does not Exists!";
		}
	}

	private  String getCount() {
		return server.getMapCount();
	}

}

public class CenterServer extends ManagerInterfacePOA {
	private HashMapper map;
	private LogWriter logs;
	private String location;
	private String path;
	private String fileName;
	private ORB orb;
	private static Semaphore semaphore = new Semaphore(1);
	
	public CenterServer(String location, String path, int udpPort) throws IOException {
		this.fileName = location + "_logs.txt";
		this.path = path;
		map = new HashMapper();
		logs = new LogWriter(fileName, path);
		this.location = location;

		try {
			(new UDP_Server(udpPort, this, location)).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setORB(ORB orb_val) {
		orb = orb_val;
	}
	@SuppressWarnings("resource")
	private  int getUniqueId(){		
		
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		String id = "0";
		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName("localhost");
			// Requesting US
			DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, IPAddress, 5555);
			clientSocket.send(sendPacket);
			DatagramPacket receivePacketUS = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacketUS);
			
			id = new String(receivePacketUS.getData(),
					receivePacketUS.getOffset(),
					receivePacketUS.getLength());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return Integer.parseInt(id);
		
	}
	@Override
	public  String createMRecord(String managerId,
			String firstName, String lastName, String employeeID,
			String mailID, CorbaModule.Project project, String location) {
		String message;
		synchronized(CenterServer.class){
			int uId=getUniqueId();
			String recordId = "MR" + String.valueOf(uId);
			Project p = new Project(project.projectId, project.clientName,
					project.projectName);
			ManagerRecord managerRec = new ManagerRecord(firstName, lastName,
					employeeID, mailID, recordId, location, p);
	
			int check = map.getCount();
			map.put(lastName, managerRec);
			if (map.getCount() > check) {
				message = "The Manager Record is successfully added!";
			} else {
				message = "The Manager Recod was not added";
			}
			printData(managerId, message, this.location);
			
			return message;
		}
		
	}

	@Override
	public  String createERecord(String managerID,
			String firstName, String lastName, short employeeID, String mailID,
			String projectId) {
		String message;
		synchronized(CenterServer.class){
		int uId=getUniqueId();
		String recordId = "ER" + String.valueOf(uId);
		EmployeeRecord empRec = new EmployeeRecord(firstName, lastName,
				employeeID, mailID, recordId, projectId);
		int check = map.getCount();
		map.put(lastName, empRec);
		if (map.getCount() > check) {
			message = "The Empoyee Record is successfully added!";

		} else {
			message = "The Empoyee Recod was not added";
		}

		printData(managerID, message, this.location);		
		return message;
		}
	}

	@Override
	public  String getRecordCounts(String managerID) {
		String caCount = "", usCount = "", ukCount = "";

		String sendM = "Count";
		byte[] sendData = new byte[1024];
		sendData = sendM.getBytes();

		try {
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			ObjectOutput oo = new ObjectOutputStream(bStream);
			oo.writeObject(sendM);
			sendData = bStream.toByteArray();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] receiveData = new byte[1024];

		if (location.equals("Canadian")) {
			caCount = "CA " + map.getCount();

			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				// Requesting US
				DatagramPacket sendPacketUS = new DatagramPacket(sendData,
						sendData.length, IPAddress, 2002);
				clientSocket.send(sendPacketUS);
				DatagramPacket receivePacketUS = new DatagramPacket(
						receiveData, receiveData.length);
				clientSocket.receive(receivePacketUS);
				String countUsServer = new String(receivePacketUS.getData(),
						receivePacketUS.getOffset(),
						receivePacketUS.getLength());
				usCount = countUsServer;

				// Requesting UK
				DatagramPacket sendPacketUK = new DatagramPacket(sendData,
						sendData.length, IPAddress, 2003);
				clientSocket.send(sendPacketUK);
				DatagramPacket receivePacketUK = new DatagramPacket(
						receiveData, receiveData.length);
				clientSocket.receive(receivePacketUK);
				String countUKServer = new String(receivePacketUK.getData(),
						receivePacketUK.getOffset(),
						receivePacketUK.getLength());
				ukCount = countUKServer;

				clientSocket.close();

			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (location.equals("American")) {

			usCount = "US " + map.getCount();
			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				// Requesting CA
				DatagramPacket sendPacketCA = new DatagramPacket(sendData,
						sendData.length, IPAddress, 2001);
				clientSocket.send(sendPacketCA);
				DatagramPacket receivePacketCA = new DatagramPacket(
						receiveData, receiveData.length);
				clientSocket.receive(receivePacketCA);
				String countCAServer = new String(receivePacketCA.getData(),
						receivePacketCA.getOffset(),
						receivePacketCA.getLength());
				caCount = countCAServer;

				// Requesting UK
				DatagramPacket sendPacketUK = new DatagramPacket(sendData,
						sendData.length, IPAddress, 2003);
				clientSocket.send(sendPacketUK);
				DatagramPacket receivePacketUK = new DatagramPacket(
						receiveData, receiveData.length);
				clientSocket.receive(receivePacketUK);
				String countUKServer = new String(receivePacketUK.getData(),
						receivePacketUK.getOffset(),
						receivePacketUK.getLength());
				ukCount = countUKServer;

				clientSocket.close();

			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (location.equals("English")) {
			ukCount = "UK " + map.getCount();
			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				// Requesting US
				DatagramPacket sendPacketUS = new DatagramPacket(sendData,
						sendData.length, IPAddress, 2002);
				clientSocket.send(sendPacketUS);
				DatagramPacket receivePacketUS = new DatagramPacket(
						receiveData, receiveData.length);
				clientSocket.receive(receivePacketUS);
				String countUsServer = new String(receivePacketUS.getData(),
						receivePacketUS.getOffset(),
						receivePacketUS.getLength());
				usCount = countUsServer;

				// Requesting CA
				DatagramPacket sendPacketCA = new DatagramPacket(sendData,
						sendData.length, IPAddress, 2001);
				clientSocket.send(sendPacketCA);
				DatagramPacket receivePacketCA = new DatagramPacket(
						receiveData, receiveData.length);
				clientSocket.receive(receivePacketCA);
				String countCAServer = new String(receivePacketCA.getData(),
						receivePacketCA.getOffset(),
						receivePacketCA.getLength());
				caCount = countCAServer;
				clientSocket.close();

			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			return "There is nothing yet.";
		}
		String totalCount = "Total Records on Servers: " + caCount + " "
				+ usCount + " " + ukCount;
		printData(managerID, totalCount, this.location);
		map.printAll();
		System.out.println("-------------"+this.location+"---------------");

		return totalCount;
	}

	@Override
	public  String editRecord(String managerID, String recordID,
			String fieldName, String newValue) {
		String message = map.edit(recordID, fieldName, newValue);
		printData(managerID, message, this.location);
		return message;
		
	}

	private  void printData(String managerID, String task,
			String location) {

		logs.writeLog(managerID, task, this.location);

	}

	public  String getMapCount() {
		if (map.getCount() < 1)
			return "Map is empty";
		else
			return String.valueOf(map.getCount());

	}

	@Override
	public String transferRecord(String managerID,
			String recordID, String remoteCenterServerName) {
		
		String message = "";
		int port;
		switch (remoteCenterServerName) {
		case "CA":
			port = 2001;
			break;
		case "US":
			port = 2002;
			break;
		case "UK":
			port = 2003;
			break;
		default:
			port = 0;
			break;
		}
		if (checkIfExist(recordID)) {
			message = "The Record exist in " + this.location + " server.";
			printData(managerID, message, this.location);

			String sendM = "Check " + recordID;
			byte[] sendData = new byte[1024];
			try {
				ByteArrayOutputStream bStream = new ByteArrayOutputStream();
				ObjectOutput oo = new ObjectOutputStream(bStream);
				oo.writeObject(sendM);
				sendData = bStream.toByteArray();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			byte[] receiveData = new byte[1024];
			try {
				DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("localhost");
				DatagramPacket sendPacket = new DatagramPacket(sendData,
						sendData.length, IPAddress, port);
				clientSocket.send(sendPacket);
				DatagramPacket receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				clientSocket.receive(receivePacket);
				message = new String(receivePacket.getData(),
						receivePacket.getOffset(), receivePacket.getLength());
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (message.equals("This Records does not Exists!")) {
				message = recordID + " does not Exist in "
						+ remoteCenterServerName + " server";
				printData(managerID, message, location);
				try {
					ByteArrayOutputStream bStream = new ByteArrayOutputStream();
					ObjectOutput oo = new ObjectOutputStream(bStream);
					oo.writeObject(map.extract(recordID));
					oo.close();
					sendData = bStream.toByteArray();

					DatagramSocket clientSocket = new DatagramSocket();
					InetAddress IPAddress = InetAddress.getByName("localhost");
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, IPAddress, port);
					clientSocket.send(sendPacket);
					DatagramPacket receivePacket = new DatagramPacket(
							receiveData, receiveData.length);
					clientSocket.receive(receivePacket);
					message = new String(receivePacket.getData(),
							receivePacket.getOffset(),
							receivePacket.getLength());
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				map.delete(recordID);

			} else {
				message = recordID + " Already Exists in "
						+ remoteCenterServerName + " server";
				printData(managerID, message, location);
			}

		} else {
			message = "This Record does not exist " + this.location
					+ " server.";
		}
		printData(managerID, message, this.location);

		return message;
		}
	

	public boolean checkIfExist(String recordId) {
		return map.find(recordId);
	}

	public String addToMap(EmployeeRecord e) {
		map.put(e.getLastName(), e);
		return "Employee was Successfully Transfered.";
		
	}

	public String addToMap(ManagerRecord m) {
		map.put(m.getLastName(), m);
		return "Manager was Successfully Transfered.";	
	}
}
