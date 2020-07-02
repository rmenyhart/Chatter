package Model;

public class Chatroom {
	private int 		id;
	private String 		roomName;
	private String		password;
	
	public Chatroom(int id, String name, String pass) {
		this.id = id;
		this.roomName = name;
		this.password = pass;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
