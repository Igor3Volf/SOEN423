package UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Servers.ServerInterface;

public class UDP_CA implements Runnable {

	private DatagramSocket serverSocket;
	ServerInterface server;

	public UDP_CA(int port) throws Exception {

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
                sendData = ("CA "+server.getMapCount()).getBytes();
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
