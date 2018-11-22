package servers;

import models.Project;
import models.Response;
import service.IdGenerator;
import service.ReliableUdpSend;
import models.request.Request;
import models.request.CreateMRecordRequest;
import models.request.CreateERecordRequest;
import models.request.EditRecordRequest;
import models.request.GetRecordCountsRequest;
import models.request.TransferRecordRequest;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ihar Volkau
 */
class Replica extends Thread implements Runnable {
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Server_logs\\"; // desktop
	private List<Request> queue;
	private static int expectedSeqNum = 1;

	private final String FE_IP = "";
	private final int FE_PORT = 3000;

	private final InetAddress REPLICA2_IP = InetAddress.getByName("");
	private final int REPLICA2_PORT = 2220;

	private final int REPLICA_ID = 2;

	// private final static String PATH =
	// "C:\\Users\\igor3\\eclipse-workspace\\Soen423\\src\\Server_logs\\";
	// //laptop
	// PORT IN USE: 2010, 2011, 2012, 2013,5555
	private DatagramSocket serverSocket;
	private CenterServer csCA;
	private CenterServer csUS;
	private CenterServer csUK;
	private IdGenerator idGen;

	public Replica() throws Exception {
		queue = new ArrayList<Request>();
		idGen = new IdGenerator(5555);
		serverSocket = new DatagramSocket(REPLICA2_PORT, REPLICA2_IP);
		this.csCA = new CenterServer("Canadian", PATH, REPLICA2_PORT + 1);
		this.csUS = new CenterServer("American", PATH, REPLICA2_PORT + 2);
		this.csUK = new CenterServer("English", PATH, REPLICA2_PORT + 3);
		idGen.start();
		preset(csCA, csUS, csUK);
	}

	private void sendAck(InetAddress ip, int p) {
		byte[] bAck = (new String("ack")).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(bAck, bAck.length, ip, p);

		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void sendNack(InetAddress ip, int p) {
		byte[] bAck = (new String("nack")).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(bAck, bAck.length, ip, p);

		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			byte[] receiveData = new byte[1024];
			while (true) {
				// getting data
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				int port = receivePacket.getPort();
				InetAddress ip = receivePacket.getAddress();

				receiveData = receivePacket.getData();
				ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));
				Object o = iStream.readObject();
				if (o == null || !(o instanceof Request)) {
					sendNack(ip, port);
				} else {
					Request r = (Request) o;
					queue.add(r);
					sendAck(ip, port);
					proccessRequest();
				}

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 * String createMRecord(String managerId, String firstName, String lastName,
	 * String employeeID, String mailID, Project project, String location);
	 * 
	 * String createERecord(String managerID, String firstName, String lastName,
	 * short employeeID, String mailID, String projectId); String
	 * 
	 * getRecordCounts(String managerID); String editRecord(String managerID, String
	 * recordID, String fieldName, String newValue); String String editRecord(String
	 * managerID, String recordID, String fieldName, String newValue); String
	 * transferRecord(String managerID, String recordID, String
	 * remoteCenterServerName);
	 */
	private void proccessRequest() {
		for (Request req : queue) {
			if (req.getSequenceNum() == expectedSeqNum) {
				doRequest(req);
				expectedSeqNum++;
				queue.remove(req);
			}
		}
	}

	private void doRequest(Request req) {

		String message;

		if (req instanceof CreateMRecordRequest) {
			CreateMRecordRequest o = (CreateMRecordRequest) req;
			CenterServer cs = getServerLocation(o.getManagerID());
			message = cs.createMRecord(o.getManagerID(), o.getFirstName(), o.getLastName(), o.getManagerID(),
					o.getMailID(), o.getProject(), o.getLocation());

		} else if (req instanceof CreateERecordRequest) {
			CreateERecordRequest o = (CreateERecordRequest) req;
			CenterServer cs = getServerLocation(o.getManagerID());
			message = cs.createERecord(o.getManagerID(), o.getFirstName(), o.getLastName(), o.getEmployeeID(),
					o.getMailID(), o.getProjectID());
		} else if (req instanceof GetRecordCountsRequest) {
			GetRecordCountsRequest o = (GetRecordCountsRequest) req;
			CenterServer cs = getServerLocation(o.getManagerId());

			message = cs.getRecordCounts(o.getManagerId());
		} else if (req instanceof EditRecordRequest) {
			EditRecordRequest o = (EditRecordRequest) req;
			CenterServer cs = getServerLocation(o.getManagerID());

			message = cs.editRecord(o.getManagerID(), o.getRecordID(), o.getFieldName(), o.getNewValue());

		} else if (req instanceof TransferRecordRequest) {
			TransferRecordRequest o = (TransferRecordRequest) req;
			CenterServer cs = getServerLocation(o.getManagerID());

			message = cs.transferRecord(o.getManagerID(), o.getRecordID(), o.getRemoteCenterServerName());
		} else {
			message = "";
		}
		Response res = new Response(message, REPLICA_ID, req.getSequenceNum());
		(new ReliableUdpSend(FE_IP, FE_PORT, res)).start();
	}

	private CenterServer getServerLocation(String managerId) {
		if (managerId.substring(0, 2).toUpperCase().equals("CA")) {
			return csCA;
		} else if (managerId.substring(0, 2).toUpperCase().equals("US")) {
			return csUS;
		} else if (managerId.substring(0, 2).toUpperCase().equals("UK")) {
			return csUK;

		} else {
			return null;
		}
	}

	private static void preset(CenterServer ca, CenterServer us, CenterServer uk) {
		System.out.println("-------------BEGINNING OF PRESET--------------");
		Project p1CA = new Project("P0001", "Blenz Coffee", "Coffee Machine Optimization");
		Project p2CA = new Project("P0002", "Bluenotes", "Web Setup");
		Project p3CA = new Project("P0003", "Boeing Canada", "Network Setup");
		Project p4CA = new Project("P0004", "Bombardier Inc.", "Supplier Web Modification");
		ca.createMRecord("Server Manager", "Vanessa", "Luke", "CA0001", "v.duk@gmail.com", p1CA, "CA");
		ca.createMRecord("Server Manager", "Trent", "Turner", "CA0002", "t.tur@gmail.com", p2CA, "CA");
		ca.createMRecord("Server Manager", "Luka", "Callahan", "CA0003", "l.cal@gmail.com", p3CA, "CA");
		ca.createMRecord("Server Manager", "Carmen", "Lalentine", "CA0004", "c.val@yahoo.com", p4CA, "CA");
		ca.createERecord("Server Manager", "Maliyah", "Garza", (short) 1, "m.gar@gmail.com", "P00001");
		ca.createERecord("Server Manager", "Jaliyah", "Lucas", (short) 2, "j.luc@gmail.com", "P00002");
		ca.createERecord("Server Manager", "Olivia", "Henson", (short) 3, "o.hen@gmail.com", "P00003");
		ca.createERecord("Server Manager", "Tyrese", "Sawyer", (short) 4, "t.saw@hotmail.com", "P00004");
		Project p1US = new Project("P0005", "Walmart", "Electro Cashier Setup");
		Project p2US = new Project("P0006", "Berkshire Hathaway", "Server Setup");
		Project p3US = new Project("P0007", "Apple Inc.", "New Server");
		Project p4US = new Project("P0008", "ExxonMobil", "Car Network");
		us.createMRecord("Server Manager", "Giancarlo", "Pennington", "US0005", "g.pen@yahoo.com", p1US, "US");
		us.createMRecord("Server Manager", "Keira", "Bennett", "US0006", "k.ben@gmail.com", p2US, "US");
		us.createMRecord("Server Manager", "Barbara", "Wiggins", "US0007", "b.wig@gmail.com", p3US, "US");
		us.createMRecord("Server Manager", "Gustavo", "Skinner", "US0008", "g.ski@yahoo.com", p4US, "US");
		us.createERecord("Server Manager", "Garrett", "Khan", (short) 5, "g.khan@gmail.com", "P00005");
		us.createERecord("Server Manager", "Amanda", "Kim", (short) 6, "a.kim@gmail.com", "P00006");
		us.createERecord("Server Manager", "Athena", "Moody", (short) 7, "a.moo@gmail.com", "P00007");
		us.createERecord("Server Manager", "Adrien", "Owens", (short) 8, "a.owe@hotmail.com", "P00008");
		Project p1UK = new Project("P0009", "Prudential", "Software Install");
		Project p2UK = new Project("P00010", "HSBC", "New Bank Server");
		Project p3UK = new Project("P00011", "Tesco", "Network Configuration");
		Project p4UK = new Project("P00012", "Aviva", "Website Setup");
		uk.createMRecord("Server Manager", "Asa", "Woodard", "UK0009", "a.woo@gmail.com", p1UK, "UK");
		uk.createMRecord("Server Manager", "Cory", "Harvey", "UK0010", "c.har@gmail.com", p2UK, "UK");
		uk.createMRecord("Server Manager", "Marina", "Sanford", "UK0011", "m.san@gmail.com", p3UK, "UK");
		uk.createMRecord("Server Manager", "Lamar", "Fritz", "UK0012", "l.fri@gmail.com", p4UK, "UK");
		uk.createERecord("Server Manager", "Avery", "Castro", (short) 9, "a.cas@gmail.com", "P00009");
		uk.createERecord("Server Manager", "Maxim", "House", (short) 10, "m.hou@gmail.com", "P00010");
		uk.createERecord("Server Manager", "Rory", "Vang", (short) 11, "r.yang@gmail.com", "P00011");
		uk.createERecord("Server Manager", "Reese", "Marquez", (short) 12, "r.mar@hotmail.com", "P00012");
		ca.getRecordCounts("Server Manager");
		us.getRecordCounts("Server Manager");
		uk.getRecordCounts("Server Manager");
		/*
		 * 
		 * testing transfer correct
		 * 
		 * ca.transferRecord("CASystem", "MR10003", "UK");
		 * ca.getRecordCounts("Server Manager"); uk.getRecordCounts("Server Manager");
		 * us.transferRecord("USSystem", "MR10003", "CA"); uk.transferRecord("UKSystem",
		 * "MR10003", "US"); us.transferRecord("US2System", "MR10003", "CA");
		 * ca.getRecordCounts("Server Manager");
		 * 
		 * 
		 * EmployeeRecord er = new EmployeeRecord("fnTest","lnTest",11111,"fail@mail.id"
		 * ,"ER10022","P00010"); ca.addToMap(er);
		 * ca.getRecordCounts("Fail Test Manager");
		 * 
		 * checking for duplicate in other server transfer correct
		 * ca.transferRecord("CASystem", "ER10022", "UK");
		 * ca.getRecordCounts("Fail Test Manager");
		 */
		System.out.println("-------------END OF PRESET------------");
	}
}

public class ReplicaManager extends Thread implements Runnable {
	
	private DatagramSocket socket;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				socket.receive(receivePacket);
				InetAddress address = receivePacket.getAddress();
				int port = receivePacket.getPort();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
				socket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}