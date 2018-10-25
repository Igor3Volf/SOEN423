package Servers;

import java.rmi.*;
import java.rmi.server.*;

import Client.ManagerInterface;
import CorbaModule.ManagerInterfaceHelper;
import Models.Project;
import UDP.UDP_CA;
import UDP.UDP_UK;
import UDP.UDP_US;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/**
 * 
 * @author Ihar Volkau
 */

public class ServerInitiatorCorba {
	private final static String PATH = "C:\\Users\\Igor3Volf\\git\\SOEN423\\eclipse-workspace\\Soen423\\Server_logs\\";		//desktop
	//private final static String PATH = "C:\\Users\\igor3\\eclipse-workspace\\Soen423\\src\\Server_logs\\"; 				//laptop

	public static void main(String args[]) throws Exception {
		try {
			
		    // create and initialize the ORB
	        ORB orb = ORB.init(args, null);
	        POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
	        rootpoa.the_POAManager().activate();	        
	        // create servant and register it with the ORB
	        CenterServer csCA = new CenterServer("Canadian", PATH,2001);
	        csCA.setORB(orb);	        
	        org.omg.CORBA.Object ref = rootpoa.servant_to_reference(csCA);
	        CorbaModule.ManagerInterface m = (CorbaModule.ManagerInterface) ManagerInterfaceHelper.narrow(ref);	  	        
	        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NamingService");
	        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
		    NameComponent path[] = ncRef.to_name("Canadian");
		    ncRef.bind(path, m);		    
	        System.out.println("CA Server is started!");
	
	        // create servant and register it with the ORB
	        CenterServer csUS = new CenterServer("American", PATH,2001);
	        csUS.setORB(orb);	        
	        org.omg.CORBA.Object ref2 = rootpoa.servant_to_reference(csUS);
	        CorbaModule.ManagerInterface m2 = (CorbaModule.ManagerInterface) ManagerInterfaceHelper.narrow(ref);	  	        
	        org.omg.CORBA.Object objRef2 = orb.resolve_initial_references("NamingService");
	        NamingContextExt ncRef2 = NamingContextExtHelper.narrow(objRef2);
		    NameComponent path2[] = ncRef2.to_name("Canadian");
		    ncRef2.bind(path2, m2);		    
	         
			
			System.out.println("US Server is started!");

		
			System.out.println("UK Server is started!");
		
			//preset(csCA, csUS, csUK);
			System.out.print("");
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
/*
	private static void preset(CenterServer ca, CenterServer us, CenterServer uk) throws RemoteException {
		CorbaModule.Project p1CA = new CorbaModule.Project("P0001", "Blenz Coffee", "Coffee Machine Optimization");
		CorbaModule.Project p2CA = new CorbaModule.Project("P0002", "Bluenotes", "Web Setup");
		CorbaModule.Project p3CA = new CorbaModule.Project("P0003", "Boeing Canada", "Network Setup");
		CorbaModule.Project p4CA = new CorbaModule.Project("P0004", "Bombardier Inc.", "Supplier Web Modification");
		String message;

		message = ca.createMRecord("Server Manager", "Vanessa", "Luke", "CA0001", "v.duk@gmail.com", p1CA, "CA");
		ca.printData("System Preset", message,"CA");

		message = ca.createMRecord("Server Manager","Trent", "Turner", "CA0002", "t.tur@gmail.com", p2CA, "CA");
		ca.printData("System Preset", message);

		message = ca.createMRecord("Server Manager","Luka", "Callahan", "CA0003", "l.cal@gmail.com", p3CA, "CA");
		ca.printData("System Preset", message);

		message = ca.createMRecord("Server Manager","Carmen", "Lalentine", "CA0004", "c.val@yahoo.com", p4CA, "CA");
		ca.printData("System Preset", message);

		message = ca.createERecord("Server Manager","Maliyah", "Garza", (short) 1, "m.gar@gmail.com", "P00001");
		ca.printData("System Preset", message);

		message = ca.createERecord("Server Manager","Jaliyah", "Lucas", (short) 2, "j.luc@gmail.com", "P00002");
		ca.printData("System Preset", message);

		message = ca.createERecord("Server Manager","Olivia", "Henson", (short) 3, "o.hen@gmail.com", "P00003");
		ca.printData("System Preset", message);

		message = ca.createERecord("Server Manager","Tyrese", "Sawyer", (short)4, "t.saw@hotmail.com", "P00004");
		ca.printData("System Preset", message);

		CorbaModule.Project p1US = new CorbaModule.Project("P0005", "Walmart", "Electro Cashier Setup");
		CorbaModule.Project p2US = new CorbaModule.Project("P0006", "Berkshire Hathaway", "Server Setup");
		CorbaModule.Project p3US = new CorbaModule.Project("P0007", "Apple Inc.", "New Server");
		CorbaModule.Project p4US = new CorbaModule.Project("P0008", "ExxonMobil", "Car Network");

		message = us.createMRecord("Server Manager","Giancarlo", "Pennington", "US0005", "g.pen@yahoo.com", p1US, "US");
		us.printData("System Preset", message);

		message = us.createMRecord("Server Manager","Keira", "Bennett", "US0006", "k.ben@gmail.com", p2US, "US");
		us.printData("System Preset", message);

		message = us.createMRecord("Server Manager","Barbara", "Wiggins", "US0007", "b.wig@gmail.com", p3US, "US");
		us.printData("System Preset", message);

		message = us.createMRecord("Server Manager","Gustavo", "Skinner", "US0008", "g.ski@yahoo.com", p4US, "US");
		us.printData("System Preset", message);

		message = us.createERecord("Server Manager","Garrett", "Khan", (short)5, "g.khan@gmail.com", "P00005");
		us.printData("System Preset", message);

		message = us.createERecord("Server Manager","Amanda", "Kim", (short)6, "a.kim@gmail.com", "P00006");
		us.printData("System Preset", message);

		message = us.createERecord("Server Manager","Athena", "Moody", (short)7, "a.moo@gmail.com", "P00007");
		us.printData("System Preset", message);

		message = us.createERecord("Server Manager","Adrien", "Owens", (short)8, "a.owe@hotmail.com", "P00008");
		us.printData("System Preset", message);

		CorbaModule.Project p1UK = new CorbaModule.Project("P0009", "Prudential", "Software Install");
		CorbaModule.Project p2UK = new CorbaModule.Project("P00010", "HSBC", "New Bank Server");
		CorbaModule.Project p3UK = new CorbaModule.Project("P00011", "Tesco", "Network Configuration");
		CorbaModule.Project p4UK = new CorbaModule.Project("P00012", "Aviva", "Website Setup");

		message = uk.createMRecord("Server Manager","Asa", "Woodard", "UK0009", "a.woo@gmail.com", p1UK, "UK");
		uk.printData("System Preset", message);

		message = uk.createMRecord("Server Manager","Cory", "Harvey", "UK0010", "c.har@gmail.com", p2UK, "UK");
		uk.printData("System Preset", message);

		message = uk.createMRecord("Server Manager","Marina", "Sanford", "UK0011", "m.san@gmail.com", p3UK, "UK");
		uk.printData("System Preset", message);

		message = uk.createMRecord("Server Manager","Lamar", "Fritz", "UK0012", "l.fri@gmail.com", p4UK, "UK");
		uk.printData("System Preset", message);

		message = uk.createERecord("Server Manager","Avery", "Castro", (short)9, "a.cas@gmail.com", "P00009");
		uk.printData("System Preset", message);

		message = uk.createERecord("Server Manager","Maxim", "House", (short)10, "m.hou@gmail.com", "P00010");
		uk.printData("System Preset", message);

		message = uk.createERecord("Server Manager","Rory", "Vang", (short)11, "r.yang@gmail.com", "P00011");
		uk.printData("System Preset", message);

		message = uk.createERecord("Server Manager","Reese", "Marquez", (short)12, "r.mar@hotmail.com", "P00012");
		uk.printData("System Preset", message);
		
		message =  ca.getRecordCounts("Server Manager");
		ca.printData("COUNT TEST", message);	
		

	}*/

} 