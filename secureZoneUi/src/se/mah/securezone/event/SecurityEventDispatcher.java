package se.mah.securezone.event;

import java.util.ArrayList;
import java.util.List;

public class SecurityEventDispatcher {
	private List<SecurityEventListener> listeners;
	
	public SecurityEventDispatcher() {
		this.listeners = new ArrayList<SecurityEventListener>();
	}
	
	public void addListener(SecurityEventListener listener) {
		listeners.add(listener);
	}
	
	public void dispatchSecurityEvent(SecurityEvent event) {
		for(SecurityEventListener listener : listeners) {
			if(listener.getId() != event.getSourceClientId()) {
				try {
					listener.onSecurityEvent(event);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
 