package client;

import java.io.IOException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import repository.LogWriter;
import servers.CenterServerInterface;
import models.Project;

public class MultipleManagerClientSide {

	public static void main(String[] args) throws InterruptedException,
			IOException {
	

		(new ThreadManager("CA1111", "CA")).start();
		(new ThreadManager("CA1112", "CA")).start();
		(new ThreadManager("CA1113", "CA")).start();
		(new ThreadManager("CA1114", "CA")).start();
		(new ThreadManager("CA1115", "CA")).start();

		(new ThreadManager("US1116", "US")).start();
		(new ThreadManager("US1117", "US")).start();
		(new ThreadManager("US1118", "US")).start();
		(new ThreadManager("US1119", "US")).start();
		(new ThreadManager("US1120", "US")).start();		


		(new ThreadManager("UK1121", "UK")).start();
		(new ThreadManager("UK1122", "UK")).start();
		(new ThreadManager("UK1123", "UK")).start();
		(new ThreadManager("UK1124", "UK")).start();
		(new ThreadManager("UK1125", "UK")).start();	


		Thread.sleep(1500);

		(new ThreadManager("CA8888", "CA")).getCountFinal();		
		(new ThreadManager("US8888", "US")).getCountFinal();
		(new ThreadManager("UK8888", "UK")).getCountFinal();

	}

}

class ThreadManager extends Thread implements Runnable {

	static CenterServerInterface serverO;
	private static LogWriter log;
	// private final static String PATH =
	// "C:\\Users\\igor3\\eclipse-workspace\\Soen423\\src\\Client_logs\\";
	// //laptop
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Client_logs\\";
	private String userName;
	private String lastName = "LastNameThread";
	private String firstName = "FirstNameThread";
	private String managerId = "";
	private int employeeID = 8888;
	private String mailID = "MailThread";
	private String location;
	private String projectId = "P1111";
	private Project project = new Project("ProjectIdThread",
			"ClientNameThread", "ProjectNameThread");

	@Override
	public void run() {
		String taskSuccess;
		taskSuccess = serverO.createMRecord(userName, firstName, lastName,managerId, mailID, project, location);
		try {
			print(userName, taskSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	ThreadManager(String userName, String loc)
			throws IOException {

		this.userName = userName;
		this.managerId = userName;
		this.location = loc;

		connectToServer(loc);

	}

	public synchronized void print(String userName, String task)
			throws IOException {
		log = new LogWriter(userName + "_logs.txt", PATH);
		log.writeLog(userName, task, location);
	}

	public void getCountFinal() throws IOException {
		String message = serverO.getRecordCounts(userName);
		print(userName, message);
	}

	private void connectToServer(String location) {

		try {
			if (location.equals("CA")) {
				String name = "Canada";
				URL url = new URL("http://localhost:8080/CenterServer/" + name
						+ "?wsdl");
				QName qName = new QName("http://Servers/",
						"CenterServerService");
				Service service = Service.create(url, qName);
				serverO = service.getPort(CenterServerInterface.class);

			} else if (location.equals("US")) {
				String name = "America";
				URL url = new URL("http://localhost:8080/CenterServer/" + name
						+ "?wsdl");
				QName qName = new QName("http://Servers/",
						"CenterServerService");
				Service service = Service.create(url, qName);
				serverO = service.getPort(CenterServerInterface.class);				

			} else if (location.equals("UK")) {
				String name = "England";
				URL url = new URL("http://localhost:8080/CenterServer/" + name
						+ "?wsdl");
				QName qName = new QName("http://Servers/",
						"CenterServerService");
				Service service = Service.create(url, qName);
				serverO = service.getPort(CenterServerInterface.class);
			} else {
				System.out.println("Wrong Location.");
				System.exit(0);
			}

		} // end try
		catch (Exception e) {
			System.out.println("Exception in ManagerClientSide: " + e);
		}

	}
}
