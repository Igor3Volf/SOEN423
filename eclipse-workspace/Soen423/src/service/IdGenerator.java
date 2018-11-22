package service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class IdGenerator extends Thread implements Runnable {

	private int id;
	private DatagramSocket uniqueIdSocket;
	public IdGenerator(){
		id=10000;

	}
	public IdGenerator(int port){
		id=10000;
		try {
			uniqueIdSocket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			byte[] receiveData = new byte[1024];
			byte[] sendData = new byte[1024];
			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				try {
					uniqueIdSocket.receive(receivePacket);
					sendData = new Integer(id).toString().getBytes();
					
					id++;
					InetAddress address = receivePacket.getAddress();
					int port = receivePacket.getPort();
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, address, port);
					uniqueIdSocket.send(sendPacket);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		
		
		
		
	}
}
