package Control;

public class DatabaseManager{
	private DatabaseConnection db;
	
	public DatabaseManager(DatabaseConnection db) {
		this.db = db;
	}
	
	public int createChatroom(String roomName, String pass) {
		return db.createChatroom(roomName, pass);
	}
	
	public void deleteChatroom(int id) {
		db.deleteChatroom(id);
	}
	
	public void giveRights(String username, String right) {
		int id = db.getUserId(username);
		db.updateUser(id, null, null, right, false);
	}
	
	public void banFromChatroom(String username, String roomName) {
		int uid = db.getUserId(username);
		int rid = db.getChatroomId(roomName);
		db.addToBlacklist(uid, rid);
	}
	
	public void unbanFromChatroom(String username, String roomName) {
		int uid = db.getUserId(username);
		int rid = db.getChatroomId(roomName);
		int id = db.getBlacklistId(uid, rid);
		db.deleteFromBlacklist(id);
	}
	
	public void suspendAccount(String username) {
		int id = db.getUserId(username);
		db.updateUser(id, null, null, null, true);
	}
}
