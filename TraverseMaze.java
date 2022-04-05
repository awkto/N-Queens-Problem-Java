

import becker.robots.City;
import becker.robots.Direction;
import becker.robots.Robot;
import becker.robots.Thing;
import java.awt.Color;
import becker.robots.Wall;
import becker.robots.MazeCity;
import java.lang.String;



//Maze Traversal Class
public class TraverseMaze {	

	public static void main(String[]args){
		initSizeAndPos();
	}
	
	public static void initSizeAndPos(){
		
		//Optional Further Development : User-input based values would be read in here if desired
		
		//Initial Maze size and Robot position values
		int mazeLength=18;
		int mazeHeight=12;
		int botPosX=0;
		int botPosY=0;
		
		//Invokes maze creation and starts up becker
		makeCity(mazeLength,mazeHeight,botPosX, botPosY);
		
	}
	
	public static void makeCity(int length, int height, int robX, int robY){		
		MazeCity maze=new MazeCity(height, length);
		
		//My original maze. This was replaced by becker's random maze generation.
		
		/*
		int walls[][]=//new int[14][21];
		{
			{3,2,2,2,2,2,2,2,3,2,2,2,2,2,2,2,3,2,2,2,1},
			{1,3,2,0,2,3,3,2,0,2,2,3,2,2,2,0,1,3,1,2,1},
			{1,1,2,2,2,0,1,1,3,0,1,1,2,2,2,2,1,1,1,1,1},
			{1,1,2,2,3,2,2,1,3,2,3,2,3,2,2,0,1,0,1,2,1},
			{1,3,0,1,2,1,1,2,1,1,1,1,1,1,3,1,1,3,3,1,1},
			{3,0,1,1,2,2,1,2,0,1,0,1,1,1,1,1,1,1,1,0,1},
			{1,3,1,1,3,2,3,2,0,3,2,0,1,1,1,1,0,3,2,1,1},
			{1,0,1,3,0,2,2,0,1,1,3,2,0,1,0,2,2,0,1,1,1},
			{3,0,2,1,2,2,3,2,1,1,1,3,2,2,2,2,1,3,2,0,1},
			{1,2,1,3,2,1,1,1,1,1,1,1,3,2,3,0,3,1,3,0,1},
			{3,0,1,1,1,1,1,1,1,1,1,0,1,1,0,1,0,1,3,2,1},
			{1,2,1,0,1,1,0,1,1,1,3,2,0,2,2,2,3,0,0,2,1},
			{1,2,2,2,1,2,2,0,2,1,2,2,2,2,2,0,1,2,2,2,0},
			{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0}
		};		
		makeMaze(walls,maze);
		*/
		//Increment minus one from each dimension variable and it becomes the end coordinates
		
		int endPosX=length-1;
		int endPosY=height-1;
		
		Color blue   = new Color(0,0,255);
		//Color green  = new Color(0,255,0);
		Color orange = new Color(255,127,0);
		
		//We have three bots : two are regular robots that is used in the traversal algorithm
		Robot 		autoBot  = 	new Robot 		(maze,robY,robX,Direction.EAST);	
		autoBot.setColor(blue);
		
		//And a controllable robot, created from a modified version of RobotRC that controls more intuitively
		RobotRCwasd freeBot  = 	new RobotRCwasd	(maze,robY,robX,Direction.EAST,2);
		freeBot.setColor(orange);
		
		//I placed two things one at start of maze, one at end, just for fun
		Thing thing1=new Thing(maze,0,0);		
		Thing thing2=new Thing(maze,height,length);
		
		//Sends autoBot to the traversal algorithm method
		traverse(autoBot,maze,endPosX,endPosY);	
		
		
	}
	
	//this method was used to make specific mazes, and can still be used. But right now its replaced with the random maze generator in becker
	/*
	public static void makeMaze(int[][] walls, City maze){
		int length1=walls.length;
		int length2=walls[1].length;
		System.out.print(length1);
		System.out.print(length2);
		int counter1=0;
		int counter2=0;
		int counter3=0;
		
		Wall borders[]=new Wall[(length1*length2*2)];
		
		while (counter1<length1){
			while (counter2<length2){
				
				if 		(walls[counter1][counter2]==1)  
					borders[counter3]=new Wall(maze,counter1,counter2,Direction.WEST);
				
				else if (walls[counter1][counter2]==2)  
					borders[counter3]=new Wall(maze,counter1,counter2,Direction.NORTH);
				
				else if (walls[counter1][counter2]==3) {
					borders[counter3]=new Wall(maze,counter1,counter2,Direction.WEST);
					counter3++;
					borders[counter3]=new Wall(maze,counter1,counter2,Direction.NORTH);
				}
				counter3++;								
				counter2++;
			}
			counter1++;
			counter2=0;
		}			
	}*/
	
	
	
	//This method traverses the robot through the maze using a simple LEFT-FORWARD-RIGHT-BACK algorithm
	public static void traverse(Robot rob, City maze, int endAve, int endStr){
		while ((rob.getAvenue()!=endAve)||(rob.getStreet()!=endStr)){ //the while condition is the EXIT position of the maze
			
			//If left is open, go LEFT
			if 		(safeLeft(rob,maze)) {				
				rob.move();
			}
			
			//If forward is open, go FORWARD
			else if (safeFront(rob,maze)) {
				rob.move();
			}
			
			//If right is open, go RIGHT
			else if (safeRight(rob,maze)){
				rob.move();
			}
			
			//Otherwise go BACK
			else {
				rob.turnLeft();
				rob.turnLeft();
				rob.turnLeft();
				rob.move();
			}			
		}
	}
	
	
	//Checks if robot can move LEFT
	public static boolean safeLeft(Robot rob, City maze){
		rob.turnLeft();
		return (rob.frontIsClear());
	}
	
	//Checks if robot can move FORWARD
	public static boolean safeFront(Robot rob, City maze){
		rob.turnLeft();
		rob.turnLeft();
		rob.turnLeft();
		return (rob.frontIsClear());		
	}
	
	
	//Checks if robot can move RIGHT
	public static boolean safeRight(Robot rob, City maze){
		rob.turnLeft();
		rob.turnLeft();
		rob.turnLeft();
		return (rob.frontIsClear());		
	}
	
}
