package ping;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class PingClient {

	private static DatagramSocket socket;

	public static void main(String[] args) throws Exception {

		String frase = "Testando ping UDP";

		InetAddress host = InetAddress.getByName("localhost");

		socket = new DatagramSocket(6789);

		byte[] buffer = new byte[1024];

		buffer = frase.getBytes();

		DatagramPacket response = new DatagramPacket(buffer, buffer.length, host, 6789);

		for (int i = 0; i < 10; i++) {
			socket.send(response);
		}

		System.out.println("sent");

	}

}
