import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import javax.swing.*;
import java.util.*;
import org.json.* ;
import org.restlet.ext.json.* ;
import org.restlet.resource.ClientResource;
import org.restlet.representation.Representation ;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * Write a description of class PlayerButton here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerButton extends Button
{
    
    public void action()
    {
        // a jframe here isn't strictly necessary, but it makes the example a little more real
        JFrame frame = new JFrame("Sign in");
        // prompt the user to enter their name
        //check if you can get connect to the room
        
        String username = JOptionPane.showInputDialog(frame, "Enter your unique username?");
        if(!username.equals("") && username!=null){
            try{
                ClientResource helloClientresource = new ClientResource( BackGround.SERVICE_URL + "/gumball" ); 
                JSONObject object = new JSONObject();
                object.put("username",username);
                Representation result = helloClientresource.post(object, MediaType.APPLICATION_JSON);
                String respString = result.getText();
                if(Utils.isJSONValid(respString)){
                    JSONObject respObject = new JSONObject(respString);
                    if(respObject.has("numberPlayers")){
                        if(respObject.getInt("numberPlayers") == 5)
                            Greenfoot.setWorld(new OrangeGameWorld(""));
                        else{
                            WaitingRoom room = new WaitingRoom(username);
                            Greenfoot.setWorld(room);
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame,respString);
                }
            }catch(Exception ex){}
        }
    }
}
