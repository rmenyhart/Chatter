import java.io.DataInputStream;
import java.net.Socket;
import java.util.*;

public class Chatroom implements Observer{
	private int								chatroomId;
	private HashMap<String, ServerThread> 	conns;
	private List<String>					messages;
	
	public Chatroom(int id) {
		this.chatroomId = id;
		this.conns = new HashMap<String, ServerThread>();
		this.messages = new ArrayList<String>();
		System.out.println("\tChatroom created.");
	}
	
	public void addConnection(Socket socket, String username, DataInputStream in) {
		System.out.println("Connection added.");
		ServerThread th = new ServerThread(socket, username, chatroomId, in);
		th.addObserver(this);
		conns.put(username, th);
		Thread t = new Thread(th);
		t.start();
		return;
	}
	
	public int getChatroomId() {
		return chatroomId;
	}
	
	public void forwardMessage(Message msg) {
		Iterator<ServerThread> it = conns.values().iterator();
		ServerThread conn;
		while (it.hasNext()) {
			conn = it.next();
			conn.forwardMessage(msg);
		}
	}
	
	public void update(Observable th, Object msg) {
		Message m = (Message)msg;
		System.out.println("Chatroom " + chatroomId + " :  " + m.getMsg());
		messages.add(m.getUser() + ": " + m.getMsg() + "  (room " + m.getRoomId() + ")");
		forwardMessage(m);
	}
}
