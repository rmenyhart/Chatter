package View;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.JOptionPane;

import Control.Client;

public class ChatroomSelector extends JPanel {
	JList list;
	DefaultListModel model = new DefaultListModel();
	public ChatroomSelector(String[] rooms, Client client, String rank) {
		for (String room:rooms) {
			model.addElement(room);
		}
		list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(600, 400));
		
		JButton	bSelect = new JButton("Enter");
		bSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int cId;
				cId = list.getSelectedIndex();
				client.enterChatroom(cId);
			}
		}
		);
		
		if (rank.contentEquals("admin")) {
			JButton bNewChatroom = new JButton("New Chatroom");		
			bNewChatroom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String roomName = getRoomName();
					String pass = getPassword();
					client.newChatroom(roomName, pass);
				}
			}
			);
			this.add(bNewChatroom);
			
			JButton bDelChatroom = new JButton("Delete Chatroom");		
			bDelChatroom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int id = list.getSelectedIndex();
					if (id != -1)
						client.deleteChatroom(id);
				}
			}
			);
			this.add(bNewChatroom);
			this.add(bDelChatroom);
		}
		
		this.add(listScroller);
		this.add(bSelect);
	}
	
	private String getRoomName() {
		return JOptionPane.showInputDialog("Room name:");
	}
	
	private String getPassword() {
		return JOptionPane.showInputDialog("Password(optional):");
	}
	
	public String askForPassword() {
		return JOptionPane.showInputDialog("Password:");
	}
	
	public void wrongPassword() {
		JOptionPane.showMessageDialog(this, "Incorrect password");
	}
	
	public void addToModel(String roomname) {
		model.addElement(roomname);
	}
	
	public void removeFromModel(int ind) {
		model.remove(ind);
	}
}