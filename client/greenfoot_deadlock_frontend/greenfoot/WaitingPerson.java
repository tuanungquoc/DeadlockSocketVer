import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.font.FontRenderContext;
/**
 * Write a description of class WaitingPerson here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class WaitingPerson extends Actor
{
    /**
     * Act - do whatever the WaitingPerson wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    
    public WaitingPerson(String username)
    {
        
        GreenfootImage image = getImage() ;
       
        image.scale( 150, 150 ) ;
        
        Font font = new Font("Serif", Font.PLAIN, 20);
        image.setFont(font);
        FontRenderContext frc = ((Graphics2D)image.getAwtImage().createGraphics()).getFontRenderContext();
        Rectangle2D boundsCond = font.getStringBounds(username, frc); 
        int condWidth = (int)boundsCond.getWidth();
        int strHeight = (int)boundsCond.getHeight();
        image.setColor(Color.yellow);
        image.drawString(username, 50, 75);
        
    }
    public void act() 
    {
        // Add your action code here.
    }    
}
