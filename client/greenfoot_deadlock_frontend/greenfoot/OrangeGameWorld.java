import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import org.json.* ;
import javax.swing.*;
import org.restlet.ext.json.* ;
import org.restlet.resource.ClientResource;
import org.restlet.representation.Representation ;
import javax.swing.Timer;
import java.awt.event.*;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
/**
 * Write a description of class OrangeGameWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class OrangeGameWorld extends BackGround
{
    
    private Orientation orientation;
    GreenfootSound backgroundMusic = new GreenfootSound("../artwork/escape.mp3");
    WebSocketClient cc ;
    String username;
    TimerInfo counterClock;
    public OrangeGameWorld(String username)
    {
      super();
      this.username = username;
      this.addChatFeature();
      chatConnect.connect();
      //backgroundMusic.playLoop();
      counterClock = new TimerInfo(120,true);
      addObject(counterClock,150,100);
      try{
          cc = new WebSocketClient( new URI(BackGround.SOCKET_URL)) {
            
                @Override
                public void onMessage( String message ) {
                  //this will get the current number of user in the room
                  if(Utils.isJSONValid(message)){
                        JSONObject resp = new JSONObject(message);
                        String isWin = resp.getString("win");
                        if(isWin.equals("true")){
                            restartGame();
                        }else{
                            String config = resp.getString("orientation");
                            changeConfigUI(config);
                        }
                        redraw(resp);
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
                   // JFrame frame = new JFrame("Sign in");
                   // JOptionPane.showMessageDialog(frame,ex.toString());
                }
            };
          cc.connect();
        }catch(Exception ex){};
    }
    
    public void makeAMove(String jsonString){
        cc.send(jsonString);
    }
    
        
    
    public void redraw(JSONObject json)
    {
       removeObjects(getObjects(Player.class));
       removeObjects(getObjects(Gumball.class));
       JSONArray list = json.getJSONArray("players");
        for(int i = 0; i <list.length();i++)
        {
            JSONObject playerJson = list.getJSONObject(i);
            GumballType leftHand = choose(playerJson.getString("lefthand"));
            GumballType rightHand = choose(playerJson.getString("righthand"));
            Player player = new Player(leftHand,rightHand,i); 
            addObject(player, orientation.getPositionXForPlayerAt(i), 
                                        orientation.getPositionYForPlayerAt(i));
        }
    }

    private GumballType choose(String type)
    {
        switch(type)
        {
            case "YellowGumballModel": return GumballType.YELLOW;
            case "RedGumballModel": return GumballType.RED;
            case "GreenGumballModel": return GumballType.GREEN;
            case "BlueGumballModel": return GumballType.BLUE;
            case "PurpleGumballModel": return GumballType.PURPLE;
            case "null": return null;
        }
        return null;
    }
    
    public void restartGame()
    {
        removeObjects(getObjects(Actor.class));
        EndGameButton endGameBtn = new EndGameButton();
        addObject(endGameBtn, 536, 191);
        counterClock.stopTimer();
        cc.close();
        chatConnect.close();
    }
    
    public void changeConfigUI(String config){
        if(config.equals("CircularStrategy")){
                orientation = new CircularLayout();
                setBackground(new GreenfootImage("../artwork/darkspace.png"));
        }
        else{
            orientation = new LineLayout();
            setBackground(new GreenfootImage("../artwork/escaperoom.png"));
        }
    }
    

    public void act()
    {
        if (Greenfoot.isKeyDown ("c"))  {
            //send a put request to the server to update config
            ClientResource helloClientresource = new ClientResource( BackGround.SERVICE_URL+ "/config" ); 
            JSONObject object = new JSONObject();
            String config = "";
            if(orientation instanceof LineLayout){
                config = "CircularStrategy";
            }
            else if(orientation instanceof CircularLayout){
                config = "LineStrategy";        
            }
            object.put("config", config);
            makeAMove(object.toString());
            try{
                Thread.sleep(500);
            }catch(Exception ex){}
        }
      
        if(btnClick.wasClicked()){ 
            JFrame frame = new JFrame("Chat box");
            String msg = JOptionPane.showInputDialog(frame, "Enter your message?");
            chatConnect.send(username + ": " + msg);
        
        }
    }
}
