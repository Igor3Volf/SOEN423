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
import java.util.Timer;
import java.util.TimerTask;

import models.EmployeeRecord;
import models.ManagerRecord;
import models.request.Request;

public class Sequencer extends Thread implements Runnable {
	private final int SEQ_PORT = 4000;
	private final int FE_PORT = 3000;
	private final int REP_PORT = 2000;

	private final String[] REIPLICA_IPS = { "", "", "" };

	private final int[] REIPLICA_PORTS = { 2110, 2210, 2310 };
	private DatagramSocket socket;
	private Map<Long, DatagramPacket> buffer = new HashMap<Long, DatagramPacket>();
	private static long sequenceNumber = 0;

	private boolean replicaPort(int p) {
		if ((p >= REP_PORT) && (p < FE_PORT))
			return true;
		else
			return false;
	}

	private boolean frontEndPort(int p) {
		if ((p >= FE_PORT) && (p < SEQ_PORT))
			return true;
		else
			return false;
	}

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
		byte[] sendData = new byte[1024];
		InetAddress group;

		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				socket.receive(receivePacket);
				int port = receivePacket.getPort();

				if (replicaPort(port)) {
					System.out.println("This packet is from Replicas");

					receiveData = receivePacket.getData();
					InetAddress address = receivePacket.getAddress();
					ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));

					try {
						String msg = iStream.readObject().toString();
						String[] num = msg.split(" ");
						long key = Long.parseLong(num[1]);
						if (num[0].equals("NACK")) {
							DatagramPacket pack = buffer.get(key);
							ByteArrayOutputStream bStream = new ByteArrayOutputStream();
							ObjectOutput oo = new ObjectOutputStream(bStream);
							oo.writeObject(pack);
							sendData = bStream.toByteArray();
							DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
							socket.send(sendPacket);
						} else {
							buffer.remove(key);
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if (frontEndPort(port)) {
					System.out.println("This packet is from Front End");
					sequenceNumber++;
					buffer.put(sequenceNumber, receivePacket);

					InetAddress ipAdd = receivePacket.getAddress();
					receiveData = receivePacket.getData();
					ObjectInputStream iStream = new ObjectInputStream(new ByteArrayInputStream(receiveData));

					Request o;
					try {
						o = (Request) iStream.readObject();
						o.setSequenceNum(sequenceNumber);

						sendData = (new String("ACK " + sequenceNumber)).getBytes();
						DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAdd, port);
						socket.send(sendPacket);

						for (int i = 0; i < REIPLICA_PORTS.length; i++) {
							try {
								
								ReliableUDP rudp =new ReliableUDP(socket, REIPLICA_IPS[i], REIPLICA_PORTS[i],
										buffer.get(sequenceNumber));
								rudp.start();
								rudp.startTimeout();
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

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


class ReliableUDP extends Thread {

	private DatagramSocket serverSocket;
	private InetAddress ip;
	private int port;
	private DatagramPacket packet;

	public ReliableUDP(DatagramSocket serverSocket, String ip, int replica_port, DatagramPacket o) throws Exception {
		this.serverSocket = serverSocket;
		this.ip = InetAddress.getByName(ip);
		this.port = replica_port;
		this.packet = o;
	}

	public void startTimeout() {
		
		
	}

	@Override
	public void run() {
		DatagramPacket sendPacket = new DatagramPacket(packet.getData(), packet.getData().length, ip, port);
		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
}
