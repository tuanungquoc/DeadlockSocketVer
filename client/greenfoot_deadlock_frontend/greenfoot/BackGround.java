import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;
import java.awt.Color;
import java.awt.Point;
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
/**
 * Write a description of class setBackGround here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BackGround extends World
{

    /**
     * Constructor for objects of class setBackGround.
     * 
     */
    
    public static final String SERVICE_URL = "http://169.44.10.31:8080";
    public static final String SOCKET_URL = "ws://169.44.118.61:8990";
    public static final String SOCKET_WAITING_URL = "ws://169.44.118.61:8991";
    public static final String SOCKET_CHATTING_URL = "ws://169.44.118.61:8992";
    private Point[] textBoxDisplaySizes = new Point[] { new Point(250, 100), new Point(140, 75), new Point(100, 50) };
    protected WebSocketClient chatConnect ;
    protected ButtonCtrl btnClick;
    protected TextBox txtB;
    public BackGround()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1000, 700, 1);  
         try{
            chatConnect = new WebSocketClient( new URI( BackGround.SOCKET_CHATTING_URL )) {
                
                    @Override
                    public void onMessage( String message ) {
                      //this will get the current number of user in the room
                     
                      txtB.clear();
                      txtB.setText(message);
                      
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
               
                
       } catch ( Exception ex ) {}
    }
    
    public void addChatFeature()
    {
        txtB = new TextBox(new Point(250, 100), "", new Font("Helvetica", Font.PLAIN, 15));
        txtB.setReadOnly(true);
        
        addObject(txtB, 854, 69);
        btnClick = new ButtonCtrl("Click chat", new Point(100, 23));
        addObject(btnClick, 851, 130);

    }
    
    public void hideChatFeature()
    {
        removeObject(txtB);
        removeObject(btnClick);
    }
    
    public void openChatFeature()
    {
       addChatFeature();
    }
    
}
