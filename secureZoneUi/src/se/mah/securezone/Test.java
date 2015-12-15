package se.mah.securezone;

import se.mah.securezone.client.SecureZoneClient;
import se.mah.securezone.event.SecurityEventDispatcher;

public class Test {
	public static void main(String[] args) {
		SecurityEventDispatcher dispatcher = new SecurityEventDispatcher();
		
		SecureZoneClient client0 = new SecureZoneClient(0, "127.0.0.1", 5000, dispatcher);
		SecureZoneClient client1 = new SecureZoneClient(1, "127.0.0.1", 5001, dispatcher);
		SecureZoneClient client2 = new SecureZoneClient(2, "127.0.0.1", 5002, dispatcher);
		
		new Thread(client0).start();
		new Thread(client1).start();
		new Thread(client2).start();
	}
}
