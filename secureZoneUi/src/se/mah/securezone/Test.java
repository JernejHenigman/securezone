package se.mah.securezone;

import se.mah.securezone.client.SecureZoneClient;
import se.mah.securezone.event.SecurityEventDispatcher;

public class Test {
	public static void main(String[] args) {
		SecurityEventDispatcher dispatcher = new SecurityEventDispatcher();
		
		SecureZoneClient client0 = new SecureZoneClient(0, "192.168.20.252", 5000, dispatcher);
		
		new Thread(client0).start();
	}
}
