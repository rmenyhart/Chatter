package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import Control.Client;

public class LoginPage extends JPanel {
	Client 			client;
	JTextField		tUsername;
	JPasswordField	tPassword;
	
	public LoginPage(Client cl) {
		client = cl;
		JButton 		bLogin = new JButton("Login");
		JButton 		bRegister = new JButton("Register");
		
		bLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					client.connect(tUsername.getText(), tPassword.getText());
				}
			}
		);
		
		JLabel			lUsername = new JLabel("Username");
		JLabel			lPassword = new JLabel("Password");
		
		tUsername = new JTextField(30);
		tPassword = new JPasswordField(30);
		tPassword.setEchoChar('*');
		
		JPanel			pUsername = new JPanel();
		pUsername.add(lUsername);
		pUsername.add(tUsername);
		
		JPanel			pPassword = new JPanel();
		pPassword.add(lPassword);
		pPassword.add(tPassword);
		
		JPanel			buttons = new JPanel();
		buttons.add(bLogin);
		buttons.add(bRegister);
		
		JPanel			credentials = new JPanel();
		credentials.add(BorderLayout.NORTH, pUsername);
		credentials.add(BorderLayout.SOUTH, pPassword);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(BorderLayout.NORTH, credentials);
		this.add(BorderLayout.SOUTH, buttons);
	}
}
