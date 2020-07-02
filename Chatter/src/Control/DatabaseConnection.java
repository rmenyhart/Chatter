package Control;
import java.sql.*;
import java.util.*;
import Model.Chatroom;
import Model.User;

public class DatabaseConnection {
	private static Connection conn = null;
	private static final String USER = "root";
	private static final String PASS = "root";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/chatterdb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	public void connect() {
		try {
			if (conn == null)
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
		}
		catch(SQLException ex) {
			System.out.println("Couldn't connect to database.");
			ex.printStackTrace();
		}
		System.out.println("Succesfully connected to ChatterDB.");
	}
	
	private int getMaxId() {
		int id = -1;
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String query = "SELECT MAX(user_id) AS id FROM users";
				ResultSet rs = st.executeQuery(query);
				rs.next();
				id = rs.getInt("id");
				st.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot get next id.");
			}
		}
		return id;
	}
	
	public User readUser(int id) {
		User us = null;
		if (conn != null) {
			try {
				Statement readStm = conn.createStatement();
				String query = "SELECT * FROM users WHERE user_id =";
				query = query + Integer.toString(id);
				System.out.println(query);
				ResultSet rs = readStm.executeQuery(query);
				rs.next();
				String user = rs.getString("username");
				String pass = rs.getString("password");
				String rank = rs.getString("rank");
				boolean ban = rs.getBoolean("ban");
				us = new User(id, user, pass, rank, ban);
					
				System.out.println(id + " " + user + " " + pass + " " + rank + " " + ban);
				readStm.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot create read statement.");
				ex.printStackTrace();
			}
		}
		return us;
	}
	
	public int createUser(String username, String pass, String rank) {
		int newId = -1;
		if (conn != null) {
			try {
				newId = getMaxId() + 1;
				String query = "INSERT INTO users VALUES (";
				query = query + Integer.toString(newId) + ", ";
				query = query + "'" + username + "', '" + pass + "', '" + rank + "', " + "false)";
				Statement st = conn.createStatement();
				System.out.println(query);
				st.executeUpdate(query);
			}
			catch(SQLException ex) {
				System.out.println("Cannot create add statement.");
				ex.printStackTrace();
			}
		}
		return newId;
	}
	
	public void updateUser(int id, String username, String pass, String rank, boolean ban) {
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "UPDATE users SET ";
				if (username != null)
					query += "username = '" + username + "', ";
				if (pass != null)
					query += "password = '" + pass + "', ";
				if (rank != null)
					query += "users.rank = '" + rank + "', ";
				query += "ban = " + ban;
				query += " WHERE user_id = " + Integer.toString(id);
				System.out.println(query);
				stm.executeUpdate(query);
			}catch(SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void deleteUser(int id) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String query = "DELETE FROM users WHERE user_id = ";
				query += Integer.toString(id);
				st.executeUpdate(query);
				st.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot delete user.");
			}
		}
	}
	
	public int getUserId(String username) {
		int id = -1;
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "SELECT user_id FROM users WHERE username =";
				query = query + " '" + username + "' ";
				System.out.println(query);
				ResultSet rs = stm.executeQuery(query);
				if (rs.next())
					id = rs.getInt("user_id");
				stm.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot create read statement.");
				ex.printStackTrace();
			}
		}
		return id;
	}
	
	public String getUserName(int id) {
		String uname = "";
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "SELECT username FROM users WHERE user_id =";
				query = query + " '" + Integer.toString(id) + "' ";
				System.out.println(query);
				ResultSet rs = stm.executeQuery(query);
				if (rs.next())
					uname = rs.getString("username");
				stm.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot create read statement.");
				ex.printStackTrace();
			}
		}
		return uname;
	}
	
	private int getMaxChatroomId() {
		int id = -1;
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String query = "SELECT MAX(room_id) AS id FROM chatrooms";
				ResultSet rs = st.executeQuery(query);
				rs.next();
				id = rs.getInt("id");
				st.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot get next id.");
			}
		}
		return id;
	}
	
	
	public List<Chatroom> getChatroomList() {
		List<Chatroom> rooms = new ArrayList<Chatroom>();
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "SELECT * FROM chatrooms";
				ResultSet rs = stm.executeQuery(query);
				while (rs.next()) {
					int id = rs.getInt("room_id");
					String rName = rs.getString("name");
					String rPass = rs.getString("password");
					Chatroom room = new Chatroom(id, rName, rPass);
					rooms.add(room);
				}
				stm.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot read chatroom id.");
				ex.printStackTrace();
			}
		}
		return rooms;
	}
	
	public int getChatroomId(String roomName) {
		int id = -1;
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "SELECT room_id FROM chatrooms WHERE name =";
				query = query + " '" + roomName + "' ";
				System.out.println(query);
				ResultSet rs = stm.executeQuery(query);
				rs.next();
				id = rs.getInt("room_id");
				stm.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot read chatroom id.");
				ex.printStackTrace();
			}
		}
		return id;
	}
	
	public String getChatroomPassword(int id) {
		String pass = "";
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "SELECT password FROM chatrooms WHERE room_id = ";
				query = query + Integer.toString(id);
				System.out.println(query);
				ResultSet rs = stm.executeQuery(query);
				rs.next();
				pass = rs.getString("password");
				stm.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot read chatroom password.");
				ex.printStackTrace();
			}
		}
		return pass;
	}
	
	public void deleteChatroom(int id) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String query = "DELETE FROM chatrooms WHERE room_id = ";
				query += Integer.toString(id);
				st.executeUpdate(query);
				st.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot delete chatroom.");
			}
		}
	}
	
	public int createChatroom(String roomName, String pass) {
		int newId = -1;
		if (conn != null) {
			try {
				newId = getMaxChatroomId() + 1;
				String query = "INSERT INTO chatrooms VALUES (";
				query = query + Integer.toString(newId) + ", ";
				query = query + "'" + roomName + "', '" + pass + "')";
				Statement st = conn.createStatement();
				System.out.println(query);
				st.executeUpdate(query);
			}
			catch(SQLException ex) {
				System.out.println("Cannot create add statement.");
				ex.printStackTrace();
			}
		}
		return newId;
	}
	
	private int getMaxBlacklistId() {
		int id = -1;
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String query = "SELECT MAX(blacklist_id) AS id FROM blacklist";
				ResultSet rs = st.executeQuery(query);
				rs.next();
				id = rs.getInt("id");
				st.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot get next id.");
			}
		}
		return id;
	}
	
	
	public int addToBlacklist(int uid, int rid) {
		int newId = -1;
		if (conn != null) {
			try {
				newId = getMaxBlacklistId() + 1;
				String query = "INSERT INTO blacklist VALUES (";
				query = query + Integer.toString(newId) + ", ";
				query = query + Integer.toString(uid)+ ", " + Integer.toString(rid) + ")";
				Statement st = conn.createStatement();
				System.out.println(query);
				st.executeUpdate(query);
			}
			catch(SQLException ex) {
				System.out.println("Cannot create add statement.");
				ex.printStackTrace();
			}
		}
		return newId;
	}

	public void deleteFromBlacklist(int id) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String query = "DELETE FROM blacklist WHERE blacklist_id = ";
				query += Integer.toString(id);
				st.executeUpdate(query);
				st.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot delete from blacklist.");
			}
		}
	}

	public int getBlacklistId(int uid, int rid) {
		int id = -1;
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "SELECT blacklist_id FROM blacklist WHERE user_id = ";
				query = query + Integer.toString(uid) + " AND chatroom_id = " + Integer.toString(rid);
				System.out.println(query);
				ResultSet rs = stm.executeQuery(query);
				rs.next();
				id = rs.getInt("blacklist_id");
				stm.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot read blacklist id.");
				ex.printStackTrace();
			}
		}
		return id;
	}
	
	public void addMessage(int uid, int rid, String msg) {
		int mid = getMaxMsgId() + 1;
		if (conn != null) {
			try {
				Statement stm = conn.createStatement();
				String query = "INSERT INTO messages VALUES (";
				query = query + Integer.toString(mid) + ", ";
				query = query + Integer.toString(uid)+ ", " + Integer.toString(rid) + ", '"  + msg + "')";
				stm.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getMaxMsgId() {
		int id = -1;
		if (conn != null) {
			try {
				Statement stm;
				stm = conn.createStatement();
				String preq = "SELECT MAX(id) AS mid from messages";
				ResultSet rs = stm.executeQuery(preq);
				rs.next();
				id = rs.getInt("mid");
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return id;
	}
	
	public void deleteMessage(int mid) {
		if (conn != null) {
			try {
				Statement st = conn.createStatement();
				String query = "DELETE FROM messages WHERE id = ";
				query += Integer.toString(mid);
				st.executeUpdate(query);
				st.close();
			}
			catch(SQLException ex) {
				System.out.println("Cannot delete message from DB.");
			}
		}
	}
	
	public ArrayList<String> getMessages(int rid) {
		ArrayList<String> messages = new ArrayList<String>();
		try{
			Statement stm;
			stm = conn.createStatement();
			String query = "SELECT message, user_id FROM messages WHERE room_id = ";
			query = query + Integer.toString(rid);
			System.out.println(query);
			ResultSet rs = stm.executeQuery(query);
			while (rs.next()) {
				String msg = rs.getString("message");
				int uid = rs.getInt("user_id");
				String uname = getUserName(uid);
				String newMsg = uname + ":\t" + msg;
				messages.add(newMsg);
			}
			stm.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return messages;
	}
}
