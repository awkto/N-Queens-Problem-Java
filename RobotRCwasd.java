import becker.robots.Direction;
import becker.robots.Robot;
import becker.robots.City;
import becker.robots.Thing;

//Extends the robot object
public class RobotRCwasd extends Robot{   
	
	public RobotRCwasd(City city, int i, int j, Direction direction, int k)
	{
	    super(city, i, j, direction, k);
	}
	
	public RobotRCwasd(City city, int i, int j, Direction direction)
	{
	    super(city, i, j, direction);
	}
	
	protected void keyTyped(char c)
	{
		//W tells the bot to go UP, and this algorithm figures out the # of turns needed to change direction and move UP 
	    if(c == 'w' || c == 'W')
	    {
	    	if (getDirection()==Direction.SOUTH){
	    		turnLeft();
	    		turnLeft();	
	    		if (frontIsClear()) move();
	    	}
	    	else if (getDirection()==Direction.EAST){
	    		turnLeft();
	    		if (frontIsClear()) move();
	    	}
    		else if (getDirection()==Direction.WEST){
    			turnLeft();
    			turnLeft();
    			turnLeft();
    			if (frontIsClear()) move();
    		}
    		else if (frontIsClear()) move();    		
	        return;
	    }
	    
	  //A tells the bot to go LEFT, and this algorithm figures out the # of turns needed to change direction and move LEFT
	    else if(c == 'a' || c == 'A')
	    {
	    	if (getDirection()==Direction.SOUTH){
	    		turnLeft();
	    		turnLeft();
	    		turnLeft();
	    		if (frontIsClear()) move();	    		
	    	}
	    	else if (getDirection()==Direction.NORTH){
	    		turnLeft();
	    		if (frontIsClear()) move();	    		
	    	}
    		else if (getDirection()==Direction.EAST){    		
    			turnLeft();
    			turnLeft();
    			if (frontIsClear()) move();
    		}
    		else if (frontIsClear()) move();
	        return;
	    }
	    
	  //S tells the bot to go DOWN, and this algorithm figures out the # of turns needed to change direction and move DOWN
	    else if(c == 's' || c == 'S')
	    {
	    	if (getDirection()==Direction.WEST){
	    		turnLeft();
	    		if (frontIsClear()) move();
	    	}
	    	else if (getDirection()==Direction.NORTH){
	    		turnLeft();
	    		turnLeft();
	    		if (frontIsClear()) move();
	    	}
    		else if (getDirection()==Direction.EAST){    		
    			turnLeft();
    			turnLeft();
    			turnLeft();
    			if (frontIsClear()) move();
    		}
    		else if (frontIsClear()) move();
	        return;
	    }
	    
	  //D tells the bot to go RIGHT, and this algorithm figures out the # of turns needed to change direction and move RIGHT
	    else if(c == 'd' || c == 'D')
	    {
	    	if (getDirection()==Direction.WEST){
	    		turnLeft();
	    		turnLeft();
	    		if (frontIsClear()) move();
	    	}
	    	else if (getDirection()==Direction.NORTH){
	    		turnLeft();
	    		turnLeft();
	    		turnLeft();
	    		if (frontIsClear()) move();
	    	}
    		else if (getDirection()==Direction.SOUTH){    		
    			turnLeft();
    			if (frontIsClear()) move();
    		}
    		else if (frontIsClear()) move();
	        return;
	    }
	    
	    //SPACE key tells the bot to pick a thing up if possible, otherwise put a thing down if possible
	    if(c == ' '){
	        if (canPickThing()) pickThing();
	        else if (countThingsInBackpack()>0) putThing();
	    }
	}
}