package Control;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import View.ChatPage;
import View.MainFrame;
import Model.User;


public class ServerHandler extends Thread{
	private DataInputStream in;
	private ChatPage		page;
	private User			user;
	private MainFrame		frame;
	private Boolean			exit;
	
	public ServerHandler(DataInputStream input, ChatPage p, User u, MainFrame f) {
		in = input;
		page = p;
		user = u;
		frame = f;
		exit = false;
	}
	
	public void exit() {
		exit = true;
	}
	
	public void run() {
		String msg = "";
		String username = "";
		while (!exit) {
			try {
				msg = in.readUTF();
				username = in.readUTF();
				if (msg.charAt(0) == '/') {
					String[] tokens = msg.split(" ");
					if (tokens[0].contentEquals("/kick")) {
						if (tokens[1].contentEquals(user.getUsername())) {
							frame.prevPage();
						}
					}
				}
				else {
					page.showMessage(username, msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
