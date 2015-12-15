package se.mah.securezone.event;

public interface SecurityEventListener {
	public void onSecurityEvent(SecurityEvent event);
	public int getId();
}
