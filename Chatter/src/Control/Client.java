package Control;
import java.net.*;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Model.Chatroom;
import Model.User;
import View.ChatPage;
import View.ChatroomSelector;
import View.LoginPage;
import View.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.*;

public class Client{
	private Socket 				socket	= null;
	private DataInputStream 	keyboardIn	= null;
	private DataInputStream		input = null;
	private DataOutputStream 	output = null;
	private ServerHandler		serverHandler;
	private String				address;
	private int					port;
	private static MainFrame	frame;
	private static Client		client;
	private ArrayList<Integer>	chatroomIds;
	private DatabaseConnection 	db;
	private int					userId;
	private int 				chatroomId;
	private User 				user;
	private DatabaseManager		actions;
	private ChatroomSelector	cs;
	private Boolean				loggedIn;
	
	public Client(String address, int port) {
		this.address = address;
		this.port = port;
		this.loggedIn = false;
		serverHandler = null;
	}
	
	private boolean login(String uName, String uPass) {
		db = new DatabaseConnection();
		db.connect();
		userId = db.getUserId(uName);
		user = null;
		if (userId != -1)
			user = db.readUser(userId);
		else {
			System.out.println("Invalid username.");
			return false;
		}
		return uName.contentEquals(user.getUsername()) && uPass.contentEquals(user.getPassword());
	}
	
	private void selectChatroom() {
		List<Chatroom> rooms = db.getChatroomList();
		
		String[] roomnames = new String[rooms.size()];
		chatroomIds = new ArrayList<Integer>();
		int i = 0;
		for (Chatroom r: rooms) {
			chatroomIds.add(r.getId());
			roomnames[i] = r.getRoomName();
			i++;
		}
		for (i = 0; i < rooms.size(); i++) {
			System.out.println(chatroomIds.get(i) + "." + roomnames[i]);
		}
		actions = new DatabaseManager(db);
		cs = new ChatroomSelector(roomnames, client, user.getRank());
		frame.add(cs);
		frame.nextPage();
	}
	
	public void sendMessage(String msg) {
		try {
			if (msg.charAt(0) == '/') {
				System.out.println("Command: ");
				System.out.println("Rank: " + user.getRank());
				if (user.getRank().contentEquals("mod") || user.getRank().contentEquals("admin")) {
					System.out.println(msg);
					output.writeUTF(msg);
				}
			}else {
				output.writeUTF(msg);
				db.addMessage(userId, chatroomId, msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void enterChatroom(int selectId) {
		try {
			chatroomId = chatroomIds.get(selectId);
			String pass = db.getChatroomPassword(chatroomId);
			String upass = "";
			if (!pass.contentEquals("")) {
				upass = cs.askForPassword();
			}
			if (!pass.contentEquals(upass)) {
				cs.wrongPassword();
				return;
			}
			System.out.println("Cid: " + chatroomId);
			socket = new Socket(address, port);
			System.out.println("Connected");
			keyboardIn = new DataInputStream(System.in);
			output = new DataOutputStream(socket.getOutputStream());
			output.writeInt(chatroomId);
			output.writeUTF(user.getUsername());
			
		}catch (UnknownHostException u) {
			System.out.println("Can't connect to host.");
			u.printStackTrace();
		}catch(IOException i) {
			System.out.println("Can't establish I/O stream.");
			i.printStackTrace();
		}
		
		ChatPage chatPage = new ChatPage(client);
		ArrayList<String> messages = db.getMessages(chatroomId);
		for (String msg : messages) {
			chatPage.printMessage(msg);
		}
		
		frame.add(chatPage);
		frame.nextPage();
		
		try {
			input = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Cannot establish input stream");
			e.printStackTrace();
		}
		
		//handles the inputs from the server
		serverHandler = new ServerHandler(input, chatPage, user, frame);
		serverHandler.start();
	}
	
	public void connect(String username, String password) {
		//Establishing connection
		if (!loggedIn) {
			if (login(username, password)) {
				loggedIn = true;
				selectChatroom();
			}
		}
		else {
			selectChatroom();
		}
	}
	
	public void newChatroom(String roomname, String pass) {
		int id = actions.createChatroom(roomname, pass);
		chatroomIds.add(id);
		cs.addToModel(roomname);
	}
	
	public void deleteChatroom(int id) {
		actions.deleteChatroom(chatroomIds.get(id));
		chatroomIds.remove(id);
		cs.removeFromModel(id);
	}
	
	public void exit() {
		if (serverHandler != null) {
			try {
				output.writeUTF("$exit");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serverHandler.stop();
			while (serverHandler.isAlive()) ;
		}
		//Closing the connection
		try {
			if (keyboardIn != null)
				keyboardIn.close();
			if (output != null)
				output.close();
			if (socket != null)
				socket.close();
			System.out.println("Conn closed");
		}catch(IOException i) {
			System.out.println("Error while closing connection");
		}
	}
	
	public static void main(String args[]) {
		client = new Client("127.0.0.1", 5000);
		LoginPage loginPage = new LoginPage(client);
		frame = new MainFrame(loginPage, client);
	}
}
