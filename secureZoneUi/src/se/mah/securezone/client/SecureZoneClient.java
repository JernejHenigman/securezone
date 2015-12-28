package se.mah.securezone.client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	private Socket socket;
	private ImageView imageView;
	
	public SecureZoneClient(int id, String ip, int port, 
			SecurityEventDispatcher securityEventDispatcher, ImageView imageView) {
		this.ip = ip;
		this.port = port;
		this.id = id;
		this.securityEventDispatcher = securityEventDispatcher;
		this.imageView = imageView;
		this.securityEventDispatcher.addListener(this); //FIXME this is kinda lame
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(ip, port);
			
			System.out.println("CONNECTED");
			DataInputStream dataInput = new DataInputStream(socket.getInputStream());
			while(true) {
				int totalBytes = dataInput.readInt();
				byte[] bytes = readBytes(dataInput, totalBytes);
				
				ByteArrayInputStream byteArrayStream = new ByteArrayInputStream(bytes);
				Image image = new Image(byteArrayStream);
				imageView.setImage(image);
				
				SecurityEvent event = new SecurityEvent(id, SecurityEvent.MOTION_TYPE, 1234);
				securityEventDispatcher.dispatchSecurityEvent(event);
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
