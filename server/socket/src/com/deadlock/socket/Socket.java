package com.deadlock.socket;
import java.net.UnknownHostException;

public class Socket {
	public static void main(String[]arg) throws UnknownHostException
	{
		PlayingRoomServer server1 = new PlayingRoomServer(8990);
		server1.start();
		WaitingRoomServer server2 = new WaitingRoomServer(8991);
		server2.start();
		ChatRoomServer server3 = new ChatRoomServer(8992);
		server3.start();
		
	}
}
