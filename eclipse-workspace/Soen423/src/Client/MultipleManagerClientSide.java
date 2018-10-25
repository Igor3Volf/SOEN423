package Client;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Models.Project;
import Repository.LogWriter;
import Servers.ServerInterface;

public class MultipleManagerClientSide{

	public static void main(String[] args) throws InterruptedException, IOException {
		
		(new ThreadManager("CA1111","CA")).start();
		(new ThreadManager("CA1112","CA")).start();
		(new ThreadManager("CA1113","CA")).start();
		(new ThreadManager("CA1114","CA")).start();
		(new ThreadManager("CA1115","CA")).start();
		(new ThreadManager("CA1111","CA")).start();
		(new ThreadManager("CA1112","CA")).start();
		(new ThreadManager("CA1113","CA")).start();
		(new ThreadManager("CA1114","CA")).start();
		(new ThreadManager("CA1115","CA")).start();

		(new ThreadManager("US1116","US")).start();
		(new ThreadManager("US1117","US")).start();
		(new ThreadManager("US1118","US")).start();
		(new ThreadManager("US1119","US")).start();
		(new ThreadManager("US1120","US")).start();
		(new ThreadManager("US1116","US")).start();
		(new ThreadManager("US1117","US")).start();
		(new ThreadManager("US1118","US")).start();
		(new ThreadManager("US1119","US")).start();
		(new ThreadManager("US1120","US")).start();
		
		(new ThreadManager("UK1121","UK")).start();
		(new ThreadManager("UK1122","UK")).start();
		(new ThreadManager("UK1123","UK")).start();
		(new ThreadManager("UK1124","UK")).start();
		(new ThreadManager("UK1125","UK")).start();		
		(new ThreadManager("UK1121","UK")).start();
		(new ThreadManager("UK1122","UK")).start();
		(new ThreadManager("UK1123","UK")).start();
		(new ThreadManager("UK1124","UK")).start();
		(new ThreadManager("UK1125","UK")).start();

		
		
		Thread.sleep(1500);
		try {
			(new ThreadManager("CA8888","CA")).getCountFinal();
			(new ThreadManager("US8888","US")).getCountFinal();
			(new ThreadManager("UK8888","UK")).getCountFinal();


		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		
	}

}

class ThreadManager extends Thread implements Runnable  {

	private static ServerInterface serverO;
	private static LogWriter log;
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Client_logs\\";
	private String userName;
	private String lastName="LastNameThread";
	private String firstName="FirstNameThread";
	private String managerId="";
	private int employeeID=8888;
	private String mailID="MailThread";
	private String location;
	private String projectId="P1111";
	private Project project=new Project("ProjectIdThread","ClientNameThread","ProjectNameThread");
	
	@Override
	public void run() {		
		try {	
			String taskSuccess;						
			taskSuccess=serverO.createMRecord(firstName, lastName, managerId, mailID, project, location);
			serverO.printData(userName, taskSuccess);			
			print(userName, taskSuccess);	
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	ThreadManager(String userName,String loc) throws IOException{
		
			this.userName=userName;
			this.managerId = userName;
			this.location=loc;
			try {
				connectToServer(loc);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}	
	public synchronized void print(String userName, String task) throws IOException {			
			log = new LogWriter(userName + "_logs.txt", PATH);
			log.writeLog(userName, task);			
	}
	
	public void getCountFinal() throws IOException {
		String message = serverO.getRecordCounts();
		serverO.printData(userName, message);
		print(userName, message);
	}
	

	public void printAll() throws IOException {
	}
	
	
	private synchronized void connectToServer(String location) throws RemoteException {
		
		try {
			Registry r = LocateRegistry.getRegistry(1099);
			if (location.equals("CA")) {
				serverO = (ServerInterface) r.lookup("localhost/CA");
			} else if (location.equals("US")) {
				serverO = (ServerInterface) r.lookup("localhost/US");
			} else if (location.equals("UK")) {
				serverO = (ServerInterface) r.lookup("localhost/UK");
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
