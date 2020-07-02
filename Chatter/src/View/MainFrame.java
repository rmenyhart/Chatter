package View;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Control.Client;

public class MainFrame extends JFrame{
	private Container contentPane;
	private CardLayout card = new CardLayout(40, 30);
	Component login;
	Client client;
	
	public MainFrame(JPanel login, Client cl) {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = this.getContentPane();
		contentPane.setLayout(card);
		contentPane.add("Login", login);
		this.client = cl;
		this.login = login;
		this.setSize(800, 600);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				client.exit();
			}
		});
	}
	
	public void addRoomSelectPage(JPanel p) {
		contentPane.add("Select chatroom", p);
		contentPane.revalidate();
		contentPane.repaint();
	}
	
	public void addChatPage(JPanel p) {
		contentPane.add("Chat", p);
	}
	
	public void nextPage() {
		card.next(contentPane);
	}
	
	public void prevPage() {
		card.previous(contentPane);
		card.previous(contentPane);
		for (Component comp : contentPane.getComponents()) {
			if (comp != login) {
				card.removeLayoutComponent(comp);
			}
		}
	}
}
