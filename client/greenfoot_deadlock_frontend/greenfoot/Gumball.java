import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import org.json.JSONObject ;
import org.restlet.resource.*;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;

/**
 * Write a description of class Gumball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Gumball extends Actor
{
    private Player owner;
    
    public Gumball()
    {
        GreenfootImage image = getImage() ;
        image.scale( 30, 30 ) ; 
    }
    
    
    public void setOwner(Player owner)
    {
        this.owner = owner;
    }
    
    public void act() 
    {
        int mouseX = 0, mouseY = 0 ;
        MouseInfo mouse = Greenfoot.getMouseInfo();  
        if(Greenfoot.mouseDragged(this)) {          
            mouseX=mouse.getX();  
            mouseY=mouse.getY();  
            setLocation(mouseX, mouseY);
            
        } 
        
        if(Greenfoot.mouseDragEnded(this)){
            Player ownerNeighbor = (Player)getOneIntersectingObject(Player.class);
            if(ownerNeighbor!=null &&  ownerNeighbor!= owner){
                HandType ownerHand = null; 
                JSONObject object = new JSONObject();
                ownerHand = owner.isObjectBelongTo(this);
                object.put("handType", ownerHand.getType());
                object.put("passingPlayer",((Player) owner).myPositionInGameRoom());
                object.put("passedPlayer",ownerNeighbor.myPositionInGameRoom());
                
                //Representation result= helloClientresource.put(object, MediaType.APPLICATION_JSON);
                
                try{
                    ((OrangeGameWorld)getWorld()).makeAMove(object.toString());                    
                }catch(Exception ex){}
            }
            else{
                 ((OrangeGameWorld)getWorld()).makeAMove("invalid"); 
            }
        }
        
    }
    
    public String toString(){
        return "Gumball";
    }
}
