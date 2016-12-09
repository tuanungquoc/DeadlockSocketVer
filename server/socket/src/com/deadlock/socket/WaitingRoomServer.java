package com.deadlock.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.NotYetConnectedException;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class WaitingRoomServer extends WebSocketServer{

	public WaitingRoomServer(int port)throws UnknownHostException {
		super( new InetSocketAddress( port ) );
	}
	
	public WaitingRoomServer( InetSocketAddress address ) {
		super( address );
	}
	
	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		System.out.println("someone disconnects!!");
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(WebSocket arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake arg1) {
		// TODO Auto-generated method stub
		this.sendToAll( "new connection: " + arg1.getResourceDescriptor() );
		System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
		//make a call to update number of user
		ClientResource helloClientresource = new ClientResource( Utils.SERVICE_URL+"/room" );
        Representation result = helloClientresource.get() ;
		Collection<WebSocket> con = connections();
		//checking if we have 5 connection, then it will broadcast to client to start the game
		synchronized ( con ) {
			for( WebSocket c : con ) {
				
				try {
					c.send(result.getText());
				} catch (NotYetConnectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//WebSocketClient client = new 
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
