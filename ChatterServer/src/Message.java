public class Message {
	String 	msg;
	String 	user;
	int 	roomId;
	
	public Message(String m, String u, int rId) {
		this.msg = m;
		this.user = u;
		this.roomId = rId;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
