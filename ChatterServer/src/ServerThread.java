import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Observable implements Runnable {
	protected Socket 			socket;
	private DataInputStream 	in		= null;
	private DataOutputStream	out		= null;
	private String				username = "";
	private int					roomId = 0;
	
	public ServerThread(Socket clientSocket, String user, int id, DataInputStream input) {
		this.socket = clientSocket;
		this.username = user;
		this.roomId = id;
		this.in = input;
	}
	
	public void broadcastMessage(String user, String msg, int roomId) {
		Message m = new Message(msg, user, roomId);
		setChanged();
		notifyObservers(m);
	}
	
	public String getUsername() {
		return username;
	}

	public int getRoomId() {
		return roomId;
	}

	public void forwardMessage(Message msg) {
		try {
			System.out.println("Forwarding message to: " + username);
			String user = msg.getUser();
			String message = msg.getMsg();
			if (message.charAt(0) == '/') {
				String[] tokens = message.split(" ");
				if (tokens[0].contentEquals("/kick")) {
					if (tokens[1].contentEquals(username)) {
						out.writeUTF(message);
						out.writeUTF(user);
					}else {
						out.writeUTF("Kicked " + tokens[1] + "!");
						out.writeUTF(user);
					}
				}
			}
			else {
				out.writeUTF(message);
				out.writeUTF(user);
			}
		} catch (IOException e) {
			System.out.println("Error while forwarding message.");
			e.printStackTrace();
		}
	}
	
	public void run() {
		//establishing I/O stream
		try {
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("Cannot establish output stream.");
			e.printStackTrace();
		}
		
		//I/O operations
		String line = "";
		while (!line.contentEquals("$exit")) {
			try {
				line = in.readUTF();
				broadcastMessage(username, line, roomId);
			}catch (IOException i) {
				i.printStackTrace();
			}
		}
		
		//close connection
		try {
			in.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Error while closing connection.");
			e.printStackTrace();
		}
	}
}
