package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Control.Client;

public class ChatPage extends JPanel{
	private static JTextArea taChat;
	
	public ChatPage(Client client) {
		taChat = new JTextArea(20, 50);
		taChat.setEditable(false);
		
		JTextField tMessage = new JTextField(50);
		
		JButton bSend = new JButton("Send");
		bSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				client.sendMessage(tMessage.getText());
			}
		}
		);
		
		this.add(taChat);
		this.add(tMessage);
		this.add(bSend);
	}
	
	public void showMessage(String user, String msg) {
		String txt = user + ":\t" + msg + "\n";
		taChat.setText(taChat.getText() + txt);
	}
	
	public void printMessage(String msg) {
		String txt = msg + "\n";
		taChat.setText(taChat.getText() + txt);
	}
}
