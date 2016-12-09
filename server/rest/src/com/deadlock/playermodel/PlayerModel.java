package com.deadlock.playermodel;

import com.deadlock.controller.GameController;
import com.deadlock.objectholdmodel.ObjectHoldModel;

public class PlayerModel
{
    // 0 index will be left, 1 index will be right
	private String username;
    private ObjectHoldModel leftHandObj;
    private ObjectHoldModel rightHandObj;
    private GameController gameController;
    private ObjectHoldModel[] gumballs = new ObjectHoldModel[2];
    
   
    public PlayerModel(GameController gameController, String username)
    {
    	this.gameController = gameController;
    	this.username = username;
    }
    
    public String getUsername(){
    	return username;
    }
    
    //will return 0 if lefthand is available , return 1 if righthand available, retunr -1 if bothhands not avaialble
    public HandType whichHandAvailable()
    {
        if(leftHandObj == null)
            return HandType.LEFT;
        if(rightHandObj == null)
            return HandType.RIGHT;
        return null;
    }
    
    
    public void releaseObjFromHand(HandType hand)
    {
        switch(hand)
        {
            case LEFT:  leftHandObj = null;break;
            case RIGHT: rightHandObj = null;break;
        }
    }
    
    public void putObjectIntoHand(ObjectHoldModel object, HandType hand)
    {
        switch(hand)
        {
            case LEFT:  leftHandObj = object;break;
            case RIGHT: rightHandObj = object;break;
        }
    }
    
    public HandType isObjectBelongTo(ObjectHoldModel obj)
    {
        if(obj == leftHandObj)
            return HandType.LEFT;
        else if(obj == rightHandObj)
            return HandType.RIGHT;
        return null;
    }
    
    public ObjectHoldModel getGumball(HandType hand)
    {
    	switch(hand)
        {
            case LEFT:  return leftHandObj;
            case RIGHT: return rightHandObj;
        }
		return null;
    }
    
    public HandType passGumballToNeighboor(HandType hand, PlayerModel player)
    {
    	if(gameController.getOrientation().checkingNeighborHood(this, player))
    	{
    		 HandType neighborHand = player.whichHandAvailable();
             if(neighborHand!=null){
                 ObjectHoldModel object = this.getGumball(hand);
            	 player.putObjectIntoHand(object, neighborHand); 
                 this.releaseObjFromHand(hand);
                 object.setOwner(player);
             }
             return neighborHand;
    	}
    	return null;
    }
    
    public void display()
    {
    	System.out.print("(" + leftHandObj +"," + rightHandObj+")");
    }
    
}
