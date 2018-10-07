package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import Models.Project;
import Repository.LogWriter;
import Servers.ServerInterface;

public class ManagerClientSide {

	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */
	/* VALIDATION MUST BE DONE */

	private static ServerInterface serverO;
	private static LogWriter log;
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Client_logs\\";
	//private final static String PATH = "C:\\Users\\igor3\\eclipse-workspace\\Soen423\\src\\Server_logs\\";


	public static void main(String[] args) throws Exception {

		// TODO Auto-generated method stub
		Scanner keyboard = new Scanner(System.in);
		login(keyboard);
	}
	
	private static void login(Scanner keyboard) throws IOException {
		System.out.println("Hello User, Please enter your ID:[CA|UK|US]#### ");
		String input = keyboard.next();
		if(validUserId(input)) {
			log = new LogWriter(input + "_logs.txt", PATH);
			String location = input.substring(0, 2);
			try {
				connectToServer(location);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			options(keyboard, input);
		}
		else { login(keyboard);
		}
	}
	private static void options(Scanner keyboard, String userName) {
		System.out.println();
		System.out.println("Chose one from following :");
		System.out.println("1. Create Manager Record.");
		System.out.println("2. Create Employee Record.");
		System.out.println("3. Get Record Count.");
		System.out.println("4. Edit Record");
		System.out.println("0. Exit");
		int choice = keyboard.nextInt();
		if (choice > 0) {
			switch (choice) {
			case 1:
				option1(keyboard, userName);
				break;
			case 2:
				option2(keyboard, userName);
				break;
			case 3:
				option3(keyboard, userName);
				break;
			case 4:
				option4(keyboard, userName);
				break;
			case 0:
				System.exit(0);
				break;
			default:
				System.out.println("Invalid Option");
				break;
			}
		}
	}
	
	private static boolean validEmpId(String in) {
		if(in.length()==5) {
			return true;
		}
		else {
			return false;
		}
	}

	
	private static void option1(Scanner k, String userName) {
		Project p = new Project();
		String firstName, lastName, managerId, mailId, location;

		System.out.println();
		System.out.println("Enter First Name");
		firstName = k.next();
		if(isEmpty(firstName)||hasNum(firstName)) {
			option1(k, userName);
		}

		System.out.println("Enter Last Name");
		lastName = k.next();
		if(isEmpty(lastName)||hasNum(lastName)) {
			option1(k, userName);
		}

		System.out.println("Enter Manager ID");
		managerId = k.next();
		if(isEmpty(managerId)||!validUserId(managerId)||!allowedLocation(userName,managerId)) {
			option1(k, userName);
		}

		System.out.println("Enter Mail ID");
		mailId = k.next();
		if(isEmpty(mailId)) {
			option1(k, userName);
		}

		System.out.println("Enter Project ID");
		String projectId= k.next();		
		if(isEmpty(projectId)||!validProjectId(projectId)) {
			option1(k, userName);
		}
		p.setProjectId(projectId);
		
		System.out.println("Enter Name of the Client");
		String clientName= k.next();		
		if(isEmpty(clientName)||hasNum(clientName)) {
			option1(k, userName);
		}
		p.setClientName(clientName);
		
		System.out.println("Enter Name of the Project");
		String projectName= k.next();	
		if(isEmpty(projectName)) {
			option1(k, userName);
		}
		p.setProjectName(projectName);

		System.out.println("Enter Location (CA,US,UK)");
		location = k.next();
		if(isEmpty(location)||!allowedLocation(userName,location)) {
			option1(k, userName);
		}
		try {
			String message = serverO.createMRecord(firstName, lastName, managerId, mailId, p, location);
			serverO.printData(userName, message);
			log.writeLog(userName, message);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		options(k, userName);

	}	
	private static void option2(Scanner k, String userName) {
		String firstName, lastName, employeeId, mailId, projectId;

		System.out.println("Enter First Name");
		firstName = k.next();
		if(isEmpty(firstName)) {
			option2(k, userName);
		}
		
		System.out.println("Enter Last Name");
		lastName = k.next();
		if(isEmpty(lastName)) {
			option2(k, userName);
		}
		System.out.println("Enter Employee ID");
		employeeId = k.next();
		if(isEmpty(employeeId)||!validEmpId(employeeId)||!hasNum(employeeId)) {
			option2(k, userName);
		}
		System.out.println("Enter Mail ID");
		mailId = k.next();
		if(isEmpty(mailId)) {
			option2(k, userName);
		}
		System.out.println("Enter Project ID");
		projectId = k.next();
		if(isEmpty(projectId)||!validProjectId(projectId)) {
			option2(k, userName);
		}
		try {
			String message = serverO.createERecord(firstName, lastName, Integer.parseInt(employeeId), mailId,
					projectId);
			serverO.printData(userName, message);
			log.writeLog(userName, message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		options(k, userName);

	}

	private static void option3(Scanner k, String userName) 
	{
		String message;
		try {
			message = serverO.getRecordCounts();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			message = "Error: Could not get record count";
			e.printStackTrace();
		}
		log.writeLog(userName, message);
		options(k, userName);
	}

	private static void option4(Scanner k, String userName) {
		String recordId, fieldName, newValue;
		System.out.println();
		System.out.println("Enter Record ID");
		recordId = k.next();
		if(!validRecordId(recordId)) {
			option4(k, userName);
		}
		System.out.println("Enter Field Name(mailId, location, projectId, projectName, clientName)");
		fieldName = k.next();
		if(!validFieldName(fieldName)) {
			option4(k, userName);
		}
		System.out.println("Enter New Value");
		newValue = k.next();
		if(isEmpty(newValue)) {
			option4(k, userName);
		}
		try {
			String message = serverO.editRecord(recordId, fieldName, newValue);
			serverO.printData(userName, message);
			log.writeLog(userName, message);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		options(k, userName);
	}

	private static void connectToServer(String location) throws RemoteException {
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
	
	
	private static boolean validUserId(String in) {
		if(in.matches("(^CA(\\d{4})$)|(^UK(\\d{4})$)|(^US(\\d{4})$)")) 
			return true;
		
		else 
			return false;
	}
	private static boolean validFieldName(String in) {
		if(in.matches("(^mailId$)|(^location$)|(^projectId$)|(^projectName$)|(^clientName$)")) 
			return true;		
		else 
			return false;
	}
	private static boolean hasNum(String in) {
		if(in.matches(".*\\d+.*")) 
			return true;
		else 
			return false;
	}
	private static boolean isEmpty(String in) {
		if(in.isEmpty()||in.length()<2) 
			return true;
		else 
			return false;
	}
	private static boolean allowedLocation(String user, String in) {
		if(user.substring(0, 2).equals(in.substring(0, 2))) 
			return true;
		else 
			return false;
		
	}
	private static boolean validProjectId (String in) {

		if(in.matches("^P\\d{5}$")) 
			return true;
		
		else 
			return false;
	}
	private static boolean validRecordId(String in) {
		if(in.matches("(^MR(\\d{5})$)|(^ER(\\d{5})$)")) 
			return true;
		
		else 
			return false;
	}
}
