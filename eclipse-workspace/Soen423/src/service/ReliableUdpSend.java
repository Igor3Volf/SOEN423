package service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ReliableUdpSend extends Thread {
	private Object obj;
	private String toIP;
	private int toPort;
	private static final int ACK_TIMEOUT = 1000;

	public ReliableUdpSend(String toIP, int toPort, Object obj) {
		super();
		this.toIP = toIP;
		this.toPort = toPort;
		this.obj = obj;
	}

	public void run() {
		byte[] data;
		if(!(obj instanceof String)) {
			ByteArrayOutputStream bStream = new ByteArrayOutputStream();
			try {
				ObjectOutput oo = new ObjectOutputStream(bStream);
				oo.writeObject(obj);
				oo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			data = bStream.toByteArray();
		}
		else {
			data = ((String) obj).getBytes();
		}
		
		DatagramSocket aSocket = null;
		String ack = null;
		try {
			aSocket = new DatagramSocket();
			
			InetAddress ahost = InetAddress.getByName(toIP);
			DatagramPacket request = new DatagramPacket(data, data.length, ahost, toPort);
			aSocket.send(request);
			aSocket.setSoTimeout(ACK_TIMEOUT);
			byte[] buffer = new byte[1000];
			DatagramPacket reply = new DatagramPacket(buffer,buffer.length);
			aSocket.receive(reply);
			ack = new String(reply.getData());
			ack = ack.trim();
			if(ack.toLowerCase().equals("ack"))
				System.out.println("Ack received after sending the following object\n" + obj.toString());
			else
				System.err.println("Something that is not an ack was received");
		} catch (SocketTimeoutException e) {
			System.err.println("Timeout occured and no ack received after sending the following object to IP " + toIP + ", Port " + toPort + "\n" + obj.toString());
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(aSocket!=null)
				aSocket.close();
		}
	}
}