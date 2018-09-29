package Servers;

import java.rmi.*;
import java.rmi.server.*;

import Models.Project;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.net.*;
import java.io.*;

/**
 * This class represents the object server for a distributed
 * object of class Hello, which implements the remote interface
 * HelloInterface.
 * @author M. L. Liu
 */

public class ServerInitiator  {
	
   public static void main(String args[]) throws AlreadyBoundException, IOException {    
	   	String loc="";
	try {					
		Registry register = LocateRegistry.createRegistry(1099);	
		
		CenterServer csCA = new CenterServer("Canadian");
		register.bind("localhost/CA", csCA);
		System.out.println("CA Server is started!");
		
		CenterServer csUS = new CenterServer("American");
		register.bind("localhost/US", csUS);		
		System.out.println("US Server is started!");

		CenterServer csUK = new CenterServer("English");
		register.bind("localhost/UK", csUK);
		System.out.println("UK Server is started!");
		
		preset(csCA,csUS,csUK);
		
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
   }
   private static void preset(CenterServer ca, CenterServer us, CenterServer uk) throws RemoteException
   {
	   Project p1CA = new Project("P0001","Blenz Coffee","Coffee Machine Optimization");
	   Project p2CA = new Project("P0002","Bluenotes","Web Setup");
	   Project p3CA = new Project("P0003","Boeing Canada","Network Setup");
	   Project p4CA = new Project("P0004","Bombardier Inc.","Supplier Web Modification");	
	   String message;
	   
	   message = ca.createMRecord("Vanessa", "Duke", "CA0001", "v.duk@gmail.com", p1CA, "CA");
	   ca.printData("System Preset",message);
		
	   message = ca.createMRecord("Trent", "Turner", "CA0001", "t.tur@gmail.com", p2CA, "CA");
	   ca.printData("System Preset",message);
		
	   message = ca.createMRecord("Luka", "Callahan", "CA0001", "l.cal@gmail.com", p3CA, "CA");
	   ca.printData("System Preset",message);
		
	   message = ca.createMRecord("Carmen", "Valentine", "CA0001", "c.val@yahoo.com", p4CA, "CA");
	   ca.printData("System Preset",message);
		
	   message = ca.createERecord("Maliyah", "Garza", 1, "m.gar@gmail.com", "P00001");
	   ca.printData("System Preset",message);
		
	   message = ca.createERecord("Jaliyah", "Lucas", 2, "j.luc@gmail.com", "P00002");
	   ca.printData("System Preset",message);
	
	   message = ca.createERecord("Olivia", "Henson", 3, "o.hen@gmail.com", "P00003");
	   ca.printData("System Preset",message);
	
	   message =  ca.createERecord("Tyrese", "Sawyer", 4, "t.saw@hotmail.com", "P00004");
	   ca.printData("System Preset",message);
	   
	   Project p1US = new Project("P0005","Walmart","Electro Cashier Setup");
	   Project p2US = new Project("P0006","Berkshire Hathaway","Server Setup");
	   Project p3US = new Project("P0007","Apple Inc.","New Server");
	   Project p4US = new Project("P0008","ExxonMobil","Car Network");	
	   
	   message =  us.createMRecord("Giancarlo", "Pennington", "US0005", "g.pen@yahoo.com", p1US, "US");
	   us.printData("System Preset", message);
		
	   message =  us.createMRecord("Keira", "Bennett", "US0006", "k.ben@gmail.com", p2US, "US");
	   us.printData("System Preset",message);
		
	   message = us.createMRecord("Barbara", "Wiggins", "US0007", "b.wig@gmail.com", p3US, "US");
	   us.printData("System Preset",message);
		
	   message = us.createMRecord("Gustavo", "Skinner", "US0008", "g.ski@yahoo.com", p4US, "US");
	   us.printData("System Preset",message);
		
	   message = us.createERecord("Garrett", "Khan", 5, "g.khan@gmail.com", "P00005");
	   us.printData("System Preset",message);
		
	   message = us.createERecord("Amanda", "Kim", 6, "a.kim@gmail.com", "P00006");
	   us.printData("System Preset",message);
	
	   message = us.createERecord("Athena", "Moody", 7, "a.moo@gmail.com", "P00007");
	   us.printData("System Preset",message);
	
	   message = us.createERecord("Adrien", "Owens", 8, "a.owe@hotmail.com", "P00008");
	   us.printData("System Preset",message);
	   
	   Project p1UK = new Project("P0009","Prudential","Software Install");
	   Project p2UK = new Project("P00010","HSBC","New Bank Server");
	   Project p3UK = new Project("P00011","Tesco","Network Configuration");
	   Project p4UK = new Project("P00012","Aviva","Website Setup");	
	   
	   message = uk.createMRecord("Asa", "Woodard", "UK0009", "a.woo@gmail.com", p1UK, "UK");
	   uk.printData("System Preset",message);
		
	   message = uk.createMRecord("Cory", "Harvey", "UK0010", "c.har@gmail.com", p2UK, "UK");
	   uk.printData("System Preset",message);
		
	   message = uk.createMRecord("Marina", "Sanford", "UK0011", "m.san@gmail.com", p3UK, "UK");
	   uk.printData("System Preset",message);
		
	   message = uk.createMRecord("Lamar", "Fritz", "UK0012", "l.fri@gmail.com", p4UK, "UK");
	   uk.printData("System Preset",message);
		
	   message = uk.createERecord("Avery", "Castro", 9, "a.cas@gmail.com", "P00009");
	   uk.printData("System Preset",message);
		
	   message =  uk.createERecord("Maxim", "House", 10, "m.hou@gmail.com", "P000010");
	   uk.printData("System Preset",message);
	
	   message = uk.createERecord("Rory", "Vang", 11, "r.yang@gmail.com", "P000011");
	   uk.printData("System Preset",message);
	
	   message =    uk.createERecord("Reese", "Marquez", 12, "r.mar@hotmail.com", "P000012");
	   uk.printData("System Preset",message);
	   
   }
} // end class
