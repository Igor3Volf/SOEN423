package servers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import models.request.Request;
import service.ReliableUdpSend;

public class Sequencer extends Thread implements Runnable {

	private final String[] REPLICA_IPS = { "", "", "" };
	private final int[] REPLICA_PORTS = { 2110, 2210, 2310 };
	private DatagramSocket socket;
	private Map<Long, DatagramPacket> buffer = new HashMap<Long, DatagramPacket>();
	private static long sequenceNumber = 0;
	
	public Sequencer(int port) {
		try {
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] receiveData = new byte[1024];
 		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				socket.receive(receivePacket);
				sequenceNumber++;
				buffer.put(sequenceNumber, receivePacket);
				
				int port = receivePacket.getPort();
				InetAddress ipAdd = receivePacket.getAddress();		
				sendAck(ipAdd, port);
				
				receiveData = receivePacket.getData();
				ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));
				Request o;
				try {
					o = (Request) iStream.readObject();
					o.setSequenceNum(sequenceNumber);
					for (int i = 0; i < REPLICA_PORTS.length; i++) 
					{ 							
						(new ReliableUdpSend(REPLICA_IPS[i],REPLICA_PORTS[i],o)).start();
						
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
 			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
 		
	}
	private void sendAck(InetAddress ip, int p) 
	{
		byte[] bAck= (new String("ack")).getBytes();
		DatagramPacket sendPacket = new DatagramPacket(bAck, bAck.length, ip, p);

			try {
				socket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
}
