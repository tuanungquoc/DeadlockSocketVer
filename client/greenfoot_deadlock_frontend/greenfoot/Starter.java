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
 
public class Starter extends BackGround
{
    private Point[] textBoxDisplaySizes = new Point[] { new Point(250, 100), new Point(140, 75), new Point(100, 50) };
    public Starter()
    {
        prepare();
        
    }
    
    public void prepare()
    {
        addObject(new PlayerButton(), 554, 537);
        addObject(new Logo(),556,357);
    }
    
    public void act() {
    }
    
}

