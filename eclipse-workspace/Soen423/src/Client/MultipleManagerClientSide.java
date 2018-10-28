package Client;

import java.io.IOException;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import CorbaModule.ManagerInterface;
import CorbaModule.ManagerInterfaceHelper;
import CorbaModule.Project;
import Repository.LogWriter;

public class MultipleManagerClientSide {

	public static void main(String[] args) throws InterruptedException,
			IOException, InvalidName {

		// create and initialize the ORB
		ORB orb = ORB.init(args, null);

		// get the root naming context
		org.omg.CORBA.Object objRef = orb
				.resolve_initial_references("NameService");
		// Use NamingContextExt instead of NamingContext. This is
		// part of the Interoperable naming Service.
		NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

		(new ThreadManager("CA1111", "CA", ncRef)).start();
		(new ThreadManager("CA1112", "CA", ncRef)).start();
		(new ThreadManager("CA1113", "CA", ncRef)).start();
		(new ThreadManager("CA1114", "CA", ncRef)).start();
		(new ThreadManager("CA1115", "CA", ncRef)).start();
		(new ThreadManager("CA1111", "CA", ncRef)).start();
		(new ThreadManager("CA1112", "CA", ncRef)).start();
		(new ThreadManager("CA1113", "CA", ncRef)).start();
		(new ThreadManager("CA1114", "CA", ncRef)).start();
		(new ThreadManager("CA1115", "CA", ncRef)).start();
		(new ThreadManager("CA1111", "CA", ncRef)).start();
		(new ThreadManager("CA1112", "CA", ncRef)).start();
		(new ThreadManager("CA1113", "CA", ncRef)).start();
		(new ThreadManager("CA1114", "CA", ncRef)).start();
		(new ThreadManager("CA1115", "CA", ncRef)).start();
		(new ThreadManager("CA1111", "CA", ncRef)).start();
		(new ThreadManager("CA1112", "CA", ncRef)).start();
		(new ThreadManager("CA1113", "CA", ncRef)).start();
		(new ThreadManager("CA1114", "CA", ncRef)).start();
		(new ThreadManager("CA1115", "CA", ncRef)).start();

		(new ThreadManager("US1116", "US", ncRef)).start();
		(new ThreadManager("US1117", "US", ncRef)).start();
		(new ThreadManager("US1118", "US", ncRef)).start();
		(new ThreadManager("US1119", "US", ncRef)).start();
		(new ThreadManager("US1120", "US", ncRef)).start();
		(new ThreadManager("US1116", "US", ncRef)).start();
		(new ThreadManager("US1117", "US", ncRef)).start();
		(new ThreadManager("US1118", "US", ncRef)).start();
		(new ThreadManager("US1119", "US", ncRef)).start();
		(new ThreadManager("US1120", "US", ncRef)).start();
		(new ThreadManager("US1116", "US", ncRef)).start();
		(new ThreadManager("US1117", "US", ncRef)).start();
		(new ThreadManager("US1118", "US", ncRef)).start();
		(new ThreadManager("US1119", "US", ncRef)).start();
		(new ThreadManager("US1120", "US", ncRef)).start();
		(new ThreadManager("US1116", "US", ncRef)).start();
		(new ThreadManager("US1117", "US", ncRef)).start();
		(new ThreadManager("US1118", "US", ncRef)).start();
		(new ThreadManager("US1119", "US", ncRef)).start();
		(new ThreadManager("US1120", "US", ncRef)).start();

		(new ThreadManager("UK1121", "UK", ncRef)).start();
		(new ThreadManager("UK1122", "UK", ncRef)).start();
		(new ThreadManager("UK1123", "UK", ncRef)).start();
		(new ThreadManager("UK1124", "UK", ncRef)).start();
		(new ThreadManager("UK1125", "UK", ncRef)).start();
		(new ThreadManager("UK1121", "UK", ncRef)).start();
		(new ThreadManager("UK1122", "UK", ncRef)).start();
		(new ThreadManager("UK1123", "UK", ncRef)).start();
		(new ThreadManager("UK1124", "UK", ncRef)).start();
		(new ThreadManager("UK1125", "UK", ncRef)).start();
		(new ThreadManager("UK1121", "UK", ncRef)).start();
		(new ThreadManager("UK1122", "UK", ncRef)).start();
		(new ThreadManager("UK1123", "UK", ncRef)).start();
		(new ThreadManager("UK1124", "UK", ncRef)).start();
		(new ThreadManager("UK1125", "UK", ncRef)).start();
		(new ThreadManager("UK1121", "UK", ncRef)).start();
		(new ThreadManager("UK1122", "UK", ncRef)).start();
		(new ThreadManager("UK1123", "UK", ncRef)).start();
		(new ThreadManager("UK1124", "UK", ncRef)).start();
		(new ThreadManager("UK1125", "UK", ncRef)).start();

		Thread.sleep(1500);

		(new ThreadManager("CA8888", "CA", ncRef)).getCountFinal();		
		(new ThreadManager("US8888", "US", ncRef)).getCountFinal();
		(new ThreadManager("UK8888", "UK", ncRef)).getCountFinal();

	}

}

class ThreadManager extends Thread implements Runnable {

	static ManagerInterface serverO;
	static NamingContextExt ncRef;
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

	ThreadManager(String userName, String loc, NamingContextExt ncRef)
			throws IOException {

		this.ncRef = ncRef;
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

	private synchronized void connectToServer(String location) {

		try {
			// resolve the Object Reference in Naming

			if (location.equals("CA")) {
				String name = "Canada";
				serverO = ManagerInterfaceHelper
						.narrow(ncRef.resolve_str(name));
			} else if (location.equals("US")) {
				String name = "America";
				serverO = ManagerInterfaceHelper
						.narrow(ncRef.resolve_str(name));

			} else if (location.equals("UK")) {
				String name = "England";
				serverO = ManagerInterfaceHelper
						.narrow(ncRef.resolve_str(name));				
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
