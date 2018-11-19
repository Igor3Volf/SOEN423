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
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import models.request.Request;

public class Sequencer extends Thread implements Runnable {
	private final int SEQ_PORT = 4000;
	private final int FE_PORT = 3000;
	private final int REP_PORT = 2000;
	private long timeout;
	private DatagramSocket socket;
	private Map<Integer, DatagramPacket> buffer = new HashMap<Integer, DatagramPacket>();
	Timer timer;				// for timeouts	

	private static int sequenceNumber = 0;

	@Override
	public void run() {
		// TODO Auto-generated method stub

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		InetAddress group;
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);
			try {
				socket.receive(receivePacket);
				int port = receivePacket.getPort();
				if ((port >= REP_PORT) && (port < SEQ_PORT)) {
					timeout = System.currentTimeMillis();
					System.out.println("This packet is from Replicas");

					receiveData = receivePacket.getData();
					InetAddress address = receivePacket.getAddress();
					ObjectInputStream iStream = new ObjectInputStream(
							new ByteArrayInputStream(receiveData));

					try {
						String msg = iStream.readObject().toString();
						if (msg.contains("NACK")) {
							String[] num = msg.split(" ");
							int key = Integer.parseInt(num[1]);
							DatagramPacket pack = buffer.get(key);
							ByteArrayOutputStream bStream = new ByteArrayOutputStream();
							ObjectOutput oo = new ObjectOutputStream(bStream);
							oo.writeObject(pack);
							sendData = bStream.toByteArray();
							DatagramPacket sendPacket = new DatagramPacket(
									sendData, sendData.length, address, port);
							socket.send(sendPacket);
							timeout = System.currentTimeMillis();
						} else {
							String[] num = msg.split(" ");
							int key = Integer.parseInt(num[1]);
							buffer.remove(key);
							timeout = System.currentTimeMillis();

						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ((port >= FE_PORT) && (port < SEQ_PORT)) {
					System.out.println("This packet is from Front End");
					sequenceNumber++;
					buffer.put(sequenceNumber, receivePacket);

					group = InetAddress.getByName("230.0.0.1");
					receiveData = receivePacket.getData();
					ObjectInputStream iStream = new ObjectInputStream(
							new ByteArrayInputStream(receiveData));

					Request o;
					try {
						o = (Request) iStream.readObject();
						o.setSequenceNum(sequenceNumber);

						ByteArrayOutputStream bStream = new ByteArrayOutputStream();
						ObjectOutput oo = new ObjectOutputStream(bStream);
						oo.writeObject(o);
						sendData = bStream.toByteArray();
						DatagramPacket sendPacket = new DatagramPacket(
								sendData, sendData.length, group, port);
						socket.send(sendPacket);

					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}	
	// to start or stop the timer
		private void setTimer(boolean isNewTimer){
			if (timer != null) 
				timer.cancel();
			if (isNewTimer){
				timer = new Timer();
				timer.schedule(taskTimout(),300);
			}
		}
		private TimerTask taskTimout()
		{
			
			return null;
			
		}
		
}
