package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Servers.ServerInterface;

public class UDP_UK implements Runnable {

	private DatagramSocket serverSocket;
	Registry r;
	ServerInterface server;
	
	public UDP_UK(int port) throws Exception {

		r = LocateRegistry.getRegistry(1099);
		server = (ServerInterface) r.lookup("localhost/UK");
		serverSocket = new DatagramSocket(port);		
	}

	@Override
	public void run() {
		try {
			 byte[] receiveData = new byte[1024];
	         byte[] sendData = new byte[1024];
			 while(true)
            {
               DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
               serverSocket.receive(receivePacket);                  
               sendData =("UK "+ server.getMapCount()).getBytes();
               InetAddress IPAddress = receivePacket.getAddress();
               int port = receivePacket.getPort();               
               DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
               serverSocket.send(sendPacket);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
}
