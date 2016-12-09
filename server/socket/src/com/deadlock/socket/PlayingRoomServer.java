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
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

public class PlayingRoomServer extends WebSocketServer{

	public PlayingRoomServer(int port)throws UnknownHostException {
		super( new InetSocketAddress( port ) );
	}
	
	public PlayingRoomServer( InetSocketAddress address ) {
		super( address );
	}
	
	@Override
	public void onClose(WebSocket arg0, int arg1, String arg2, boolean arg3) {
		// TODO Auto-generated method stub
		System.out.println("someone disconnects!!");
		//checking if there is no connection, then will restart the game
		Collection<WebSocket> con = connections();
		//checking if we have 5 connection, then it will broadcast to client to start the game
		synchronized ( con ) {
			if(con.size() == 0){
				//restart the game
				String message = "{\"restart\":1}";
				ClientResource clientResource = new ClientResource( Utils.SERVICE_URL+ "/gumball" ); 
				clientResource.put(message, MediaType.APPLICATION_JSON);
			}
		}
	}

	@Override
	public void onError(WebSocket arg0, Exception arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMessage(WebSocket arg0, String message) {
		// TODO Auto-generated method stub
		Representation result = null;
		if(message.equals("invalid")){
			ClientResource clientResource = new ClientResource( Utils.SERVICE_URL+"/gumball" );
			result = clientResource.get();
		}else if(message.contains("config")){
			ClientResource clientResource = new ClientResource( Utils.SERVICE_URL+ "/config" ); 
			clientResource.put(message, MediaType.APPLICATION_JSON);
			ClientResource clientResource1 = new ClientResource( Utils.SERVICE_URL+"/gumball" );
			result = clientResource1.get();
            //make another call to broad cast the server
		}else{
			ClientResource clientResource = new ClientResource( Utils.SERVICE_URL+"/gumball" );
			result = clientResource.put(new JSONObject(message), MediaType.APPLICATION_JSON);
			//broadcast after making a move successfully
		}
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
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake arg1) {
		// TODO Auto-generated method stub
		this.sendToAll( "new connection: " + arg1.getResourceDescriptor() );
		System.out.println( conn.getRemoteSocketAddress().getAddress().getHostAddress() + " entered the room!" );
		Collection<WebSocket> con = connections();
		//checking if we have 5 connection, then it will broadcast to client to start the game
		ClientResource clientResource = new ClientResource( Utils.SERVICE_URL+"/gumball" );
		Representation result = clientResource.get();
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
