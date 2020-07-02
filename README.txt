Chat Application:
	#MVC + Client-Server:
	Users need a username and password to enter the application(Stored in a DB).
	Three types of users:
		* User: can enter a chatroom, read and send messages, leave a chatroom
		* Moderator: + can remove messages with sensitive content, can ban users from chatroom
		* Administrator: + can create and delete chatrooms, give Mod and Admin rights to a user, can suspend accounts
	The messages of any user will go through a swear word filter.
	When creating a chatroom, the Admin can set:
		* Rules of the chatroom. (In plain text, can be viewed by anyone).
		* The swear word filter to on/off.
	

	Two types of chatrooms can be created:
		* Private -> needs a password to be accessed
		* Public

	#Observer Pattern:
	to distribute messages among all clients in the same chatroom