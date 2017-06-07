package ping;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class PingServer {

	private static final double LOSS_RATE = 0.3;
	private static final int AVERAGE_DELAY = 100;
	private static DatagramSocket socket;

	public static void main(String[] args) throws Exception {

		while (true) {
			int port = 6789;
			Random random = new Random();

			socket = new DatagramSocket(port);

			byte[] buffer = new byte[1024];

			DatagramPacket request = new DatagramPacket(buffer, buffer.length);

			socket.receive(request);

			printData(request);

			if (random.nextDouble() < LOSS_RATE) {
				System.out.println("Reply not sent");
				continue;
			}

			Thread.sleep((int) (random.nextDouble()) * 2 * AVERAGE_DELAY);
			
			InetAddress clienteHost = request.getAddress();
			int clientPort = request.getPort();
			byte[] buf = request.getData();
			DatagramPacket reply = new DatagramPacket(buf, buf.length, clienteHost, clientPort);
			socket.send(reply);
			
			System.out.println("Reply sent");

		}
	}

	private static void printData(DatagramPacket request) throws Exception {
		byte[] buf = request.getData();

		ByteArrayInputStream bais = new ByteArrayInputStream(buf);

		InputStreamReader isr = new InputStreamReader(bais);

		BufferedReader br = new BufferedReader(isr);

		String line = br.readLine();

		System.out.println("Received from " + request.getAddress().getHostAddress() + ":" + new String(line));

	}

}
