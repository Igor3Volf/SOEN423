package Client;

import java.rmi.Remote;

public interface ManagerInterface extends Remote{
	   public String sayHello(String name) 
			      throws java.rmi.RemoteException;

}
