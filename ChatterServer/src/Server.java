import java.net.*;
import java.io.*;
import java.util.*;

public class Server extends Thread{
	private ServerSocket						server	= null;
	private Socket								socket = null;
	private HashMap<Integer, Chatroom> 			chatrooms;
	private int 								chatroomId = -1;
	
	
	public Server(int port) {
		chatrooms = new HashMap<Integer, Chatroom>();
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");
			int chatroomId = -1;
			String username = "";
			while (true) {
				try {
					System.out.println("Waiting for client.");
					socket = server.accept();
					System.out.println("Client accepted.");
					DataInputStream in = new DataInputStream (socket.getInputStream());
					chatroomId = in.readInt();
					username = in.readUTF();
					if (chatrooms.containsKey(chatroomId)) {
						System.out.println("In chatroom:  " + chatroomId);
						Chatroom thChat = chatrooms.get(chatroomId);
						thChat.addConnection(socket, username, in);
					}else {
						System.out.println("New chatroom: " + chatroomId);
						Chatroom thChat = new Chatroom(chatroomId);
						thChat.addConnection(socket, username, in);
						chatrooms.put(chatroomId, thChat);
					}
				}catch (IOException e) {
					System.out.println("I/O error: " + e);
				}
			}
		}catch(IOException i) {
			i.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		Server server = new Server(5000);
	}
}
