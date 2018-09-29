package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import Models.Project;
import Servers.ServerInterface;


public class ManagerClientSide{

	public static ServerInterface serverO;
	
	public static void main(String[] args)throws Exception 
	{
		// TODO Auto-generated method stub		 
		 Scanner keyboard = new Scanner(System.in);
		 login(keyboard);	 		 
	}
	
	private static void login(Scanner keyboard) 
	{
		 System.out.print("Hello User, Please enter your ID: (type 'exit' to stop)");			 
		 String input = keyboard.next();
		 String location = input.substring(0, 2); 
		 try {
			connectToServer(location);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		options(keyboard, input);
		
	}
	private static void options(Scanner keyboard, String userName) {
		 System.out.println("Chose one from following : (1,2,3,4)");
		 System.out.println("1. Create Manager Record.");
		 System.out.println("2. Create Employee Record.");
		 System.out.println("3. Get Record Count.");
		 System.out.println("4. Edit Record");
		 System.out.println("0. Exit");
		 int choice = keyboard.nextInt();
		 if(choice>0) {
			 switch(choice) {
			 	case 1:option1(keyboard, userName); break;
			 	case 2:option2(keyboard, userName); break;
			 	case 3:option3(keyboard, userName); break;
			 	case 4:option4(keyboard, userName); break;
			 	case 0:System.exit(0); break;
			 	default:System.out.println("Invalid Option"); break;
			 }
		 }		 
	}
	
	private static void option1 (Scanner k, String userName)
	{
		 Project p = new Project();
		 String firstName, lastName, managerId, mailId, location;
		 
		 System.out.println("Enter First Name");
		 firstName = k.next();
		 
		 System.out.println("Enter Last Name");
		 lastName = k.next();

		 System.out.println("Enter Manager ID");
		 managerId = k.next();

		 System.out.println("Enter Mail ID");
		 mailId = k.next();

		 System.out.println("Enter Project ID");		
		 p.setProjectId(k.next()) ;
		 
		 System.out.println("Enter Name of the Client");		
		 p.setClientName(k.next());
		 
		 System.out.println("Enter Name of the Project");		
		 p.setProjectName(k.next());
		 
		 System.out.println("Enter Location (CA,US,UK)");		
		 location = k.next();	
		 
		 try {
			String message = serverO.createMRecord(firstName, lastName, managerId, mailId, p, location);
			serverO.printData(userName, message);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	 

	}
	private static void option2 (Scanner k, String userName){
		 String firstName, lastName, employeeId, mailId, projectId;
		 
		 System.out.println("Enter First Name");
		 firstName = k.next();
		 
		 System.out.println("Enter Last Name");
		 lastName = k.next();

		 System.out.println("Enter Manager ID");
		 employeeId = k.next();

		 System.out.println("Enter Mail ID");
		 mailId = k.next();
		 
		 System.out.println("Enter Project ID");
		 projectId = k.next();
		 
		 try {
			String message = serverO.createERecord(firstName, lastName, Integer.parseInt(employeeId), mailId, projectId);
			serverO.printData(userName, message);
		 } catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		}
	private static void option3 (Scanner k, String userName){
		
		
	}
	private static void option4 (Scanner k, String userName){
		
		
	}
	
	 private static void connectToServer(String location) throws RemoteException 
	 {
		 try{
			 Registry r= LocateRegistry.getRegistry(1099);
			 if(location.equals("CA")) {
				 serverO=(ServerInterface) r.lookup("localhost/CA");	         
			 }
			 else if(location.equals("US")) {
				 serverO=(ServerInterface) r.lookup("localhost/US");	      
				 
			 }else if(location.equals("UK")) {
				 serverO=(ServerInterface) r.lookup("localhost/UK");	      				 
			 }
			 else {
				 System.out.println("Wrong Location.");
				 System.exit(0);
			 }
		   
	      } // end try 
	      catch (Exception e) {	    	  
	         System.out.println("Exception in ManagerClientSide: " + e);
	      } 
			 
	 }
 }


