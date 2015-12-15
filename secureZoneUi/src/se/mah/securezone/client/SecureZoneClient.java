package se.mah.securezone.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import se.mah.securezone.event.SecurityEvent;
import se.mah.securezone.event.SecurityEventDispatcher;
import se.mah.securezone.event.SecurityEventListener;

public class SecureZoneClient implements Runnable, SecurityEventListener {
	public static final int DETECTION_EVENT = 0;
	public static final int IMAGE_EVENT = 0;

	private int id;
	private String ip;
	private int port;
	private SecurityEventDispatcher securityEventDispatcher;
	Socket socket;
	
	public SecureZoneClient(int id, String ip, int port, SecurityEventDispatcher securityEventDispatcher) {
		this.ip = ip;
		this.port = port;
		this.id = id;
		this.securityEventDispatcher = securityEventDispatcher;
		this.securityEventDispatcher.addListener(this); //TODO this is kinda lame
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(ip, port);
			DataInputStream dataInput = new DataInputStream(socket.getInputStream());
			while(true) {
				int eventType = dataInput.readInt();
				if(DETECTION_EVENT == eventType) {
					int type = dataInput.readInt();
					long timestamp = dataInput.readLong();
					SecurityEvent event = new SecurityEvent(id, type, timestamp);
					System.out.println("DETECTION EVENT RECEIVED ");
					securityEventDispatcher.dispatchSecurityEvent(event);
				} 
				
				if(IMAGE_EVENT == eventType) {
					int size = dataInput.readInt();
					byte[] imageBytes = readBytes(dataInput, size);
//					TODO show the image or something
					System.out.println("IMAGE RECEIVED with size " + imageBytes.length);
				}
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		} finally {
			if(null != socket) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void onSecurityEvent(SecurityEvent event) {
		try {
			DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataOutputStream.writeInt(event.getType());
			dataOutputStream.writeLong(event.getTimestamp());
			dataOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	private byte[] readBytes(InputStream input, int totalBytes) throws IOException {
		System.out.println("TOTAL BYTES " + totalBytes);
		byte[] bytes = new byte[totalBytes];
		int totalBytesRead = 0;
		int bytesLeftToRead = totalBytes;
		
		while(totalBytesRead < totalBytes) {
			int bytesRead = input.read(bytes, totalBytesRead, bytesLeftToRead);
			totalBytesRead += bytesRead;
			bytesLeftToRead -= bytesRead;
		}
		return bytes;
	}
	
}
