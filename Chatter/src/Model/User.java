package Model;

public class User {
	private int	   uId;
	private String username;
	private String password;
	private String rank;
	private boolean ban;
	
	public User(int id, String us, String pass, String rank, boolean ban) {
		this.uId = id;
		this.username = us;
		this.password = pass;
		this.rank = rank;
		this.ban = ban;
	}
	
	public int	getId() {
		return uId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public boolean isBan() {
		return ban;
	}

	public void setBan(boolean ban) {
		this.ban = ban;
	}
}
