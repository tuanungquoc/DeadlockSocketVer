package com.deadlock.socket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.NotYetConnectedException;
import java.util.ArrayList;
import java.util.Collection;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class ChatRoomServer extends WebSocketServer{
	private ArrayList<String> history = new ArrayList<String>();
	public ChatRoomServer(int port)throws UnknownHostException {
		super( new InetSocketAddress( port ) );
	}
	
	public ChatRoomServer( InetSocketAddress address ) {
		super( address );
	}
	
	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		System.out.println("someone disconnects!!");
		Collection<WebSocket> con = connections();
		//checking if we have 5 connection, then it will broadcast to client to start the game
		synchronized ( con ) {
			if(con.size() == 0) history = new ArrayList<>();
		}
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(WebSocket arg0, String message) {
		// TODO Auto-generated method stub
		
		history.add(message);
		StringBuilder builder =new StringBuilder();
		for(String msg:history)
		{
			builder.append(msg + "\n");
		}
		Collection<WebSocket> con = connections();
		//checking if we have 5 connection, then it will broadcast to client to start the game
		synchronized ( con ) {
			for( WebSocket c : con ) {
				
				try {
					c.send(builder.toString());
				} catch (NotYetConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake arg1) {
		// TODO Auto-generated method stub
		this.sendToAll( "some one joins the room!!!" );
		System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
	}
	
	/**
	 * Sends <var>text</var> to all currently connected WebSocket clients.
	 * 
	 * @param text
	 *            The String to send across the network.
	 * @throws InterruptedException
	 *             When socket related I/O errors occur.
	 */
	public void sendToAll( String text ) {
		Collection<WebSocket> con = connections();
		synchronized ( con ) {
			for( WebSocket c : con ) {
				c.send( text );
			}
		}
	}

}
