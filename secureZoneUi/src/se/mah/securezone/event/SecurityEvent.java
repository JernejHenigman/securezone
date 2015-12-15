package se.mah.securezone.event;

public class SecurityEvent {
	public static final int MOTION_TYPE = 0;
	public static final int AUDIO_TYPE = 1;
	
	private int type;
	private long timestamp;
	private int sourceClientId; 
	
	public SecurityEvent(int sourceClientId, int type, long timestamp) {
		this.type = type;
		this.timestamp = timestamp;
		this.sourceClientId = sourceClientId;
	}
	
	public int getType() {
		return type;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public int getSourceClientId() {
		return sourceClientId;
	}

	@Override
	public String toString() {
		return "SecurityEvent [type=" + type + ", timestamp=" + timestamp
				+ ", sourceClientId=" + sourceClientId + "]";
	}
}
