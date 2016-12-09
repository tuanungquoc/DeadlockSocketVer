/**
 * Write a description of class WaitingRoom here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import javax.swing.*;
import org.json.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.ClientResource;
import org.restlet.representation.Representation ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;
import javax.swing.Timer;
import java.awt.event.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
public class WaitingRoom  extends BackGround
{
   
    String username = "";
    int numberOfPlayers = 0;
    Orientation orientation = new LineLayout();
    public ChatWindow chatWindow = new ChatWindow();
    WebSocketClient cc ;
   
   public WaitingRoom(String username)
   {
       //timer.start();
       this.username = username;
       addObject(new WithdrawButton(), 495,272);
       try{
            cc = new WebSocketClient( new URI( BackGround.SOCKET_WAITING_URL)) {
                
                    @Override
                    public void onMessage( String message ) {
                      //this will get the current number of user in the room
                      if(Utils.isJSONValid(message)){
                          JSONObject resp = new JSONObject(message);
                          updateWaitingRoom(resp);
                      }
                    }

                    @Override
                    public void onOpen( ServerHandshake handshake ) {
                      
                    }

                    @Override
                    public void onClose( int code, String reason, boolean remote ) {
                        
                    }

                    @Override
                    public void onError( Exception ex ) {
                        JFrame frame = new JFrame("Sign in");
                        JOptionPane.showMessageDialog(frame,ex.toString());
                    }
                };
                cc.connect();
                cc.send(username);
       } catch ( Exception ex ) {}
       
   }
   
   public String getUsername()
   {
       return username;
    }
    
    public void disconnectToServer()
    {
        cc.close();
    }
    public void updateWaitingRoom(JSONObject resp)
    {
        try{
                removeObjects(getObjects(WaitingPerson.class));
                numberOfPlayers = resp.getInt("numberPlayers");
                JSONArray listOfUsernames = resp.getJSONArray("users");
                if(numberOfPlayers < 5){
                     for(int i=0;i< numberOfPlayers;i++)
                     {
                         String name =(String) listOfUsernames.getJSONObject(i).get("username");
                         addObject(new WaitingPerson(name), orientation.getPositionXForPlayerAt(i), orientation.getPositionYForPlayerAt(i));
                     }
                 }else{
                     cc.close();
                     Greenfoot.setWorld(new OrangeGameWorld(username));
                 }
        }catch(Exception ex){System.out.println(ex);}
    }
    
   public void act() {
       
    }
    
    
}
