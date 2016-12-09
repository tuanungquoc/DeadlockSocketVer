import greenfoot.*; 
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.font.FontRenderContext;
public class Message extends Actor
{
    GreenfootImage gi;

    public Message()
    {
        gi = new GreenfootImage( 100, 50);
        setImage(gi);        
    }

    public void setText( String msg )
    {
       Font font = new Font("Serif", Font.PLAIN, 12);
        GreenfootImage g = getWorld().getBackground();
        g.setFont(font);
        String condString = "Partly cloudy and humid \n Test";
        FontRenderContext frc = ((Graphics2D)g.getAwtImage().createGraphics()).getFontRenderContext();
        Rectangle2D boundsCond = font.getStringBounds(condString, frc); 
        int condWidth = (int)boundsCond.getWidth();
        int strHeight = (int)boundsCond.getHeight();
        g.setColor(Color.red);
        g.drawString(condString, condWidth, strHeight);
    }

    public void act() 
    {
       setText("");
    }    
}
