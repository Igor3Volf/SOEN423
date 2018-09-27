package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ManagerClientSide{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 try {
	         int RMIPort;         
	         String hostName;
	         InputStreamReader is = new InputStreamReader(System.in);
	         BufferedReader br = new BufferedReader(is);
	         System.out.println("Enter the RMIRegistry host namer:");
	         hostName = br.readLine();
	         System.out.println("Enter the RMIregistry port number:");
	         String portNum = br.readLine();
	         RMIPort = Integer.parseInt(portNum);
	         String registryURL = 
	            "rmi://" + hostName+ ":" + portNum;  
	         // find the remote object and cast it to an interface object
	         ManagerInterface h =
	           (ManagerInterface)Naming.lookup(registryURL);
	         System.out.println("Lookup completed " );
	         // invoke the remote method
	         String message = h.sayHello("Donald Duck");
	         System.out.println("HelloClient: " + message);
	      } // end try 
	      catch (Exception e) {
	         System.out.println("Exception in HelloClient: " + e);
	      } 	
	}

}
