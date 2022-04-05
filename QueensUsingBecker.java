//Name       : [redacted]
//Student #  : [redacted]
//Course     : INFO 16029 Problem Solving and Programming Logic
//Professor  : Peter Wheeler
//Date       : February 15th 2011
//Assignment : Solution for Eight Queen's Puzzle using becker robots


/* Algorithm and simple structure of the logic
 
   
   while queen!=0
     if position=89 queen--
     if queen=9 counter++, queen--
     if position is safe queen++
     if not position++
   
 */

//Although the algorithm looks simple, it was very powerful and took me 3 days to come up. 
//My initial algorithm was much more complex, and it created more problems than it solved.
//This logic algorithm turned out so dead-on that it practically wrote the program for me.
//My initial algorithm that wasn't working and only confusing me more looked like this.

/*
  
	While Queen!=9 {
	    While Row!=9 {
		While Column!=9 {
		   Check Position
		   If safe
			Place Queen
			Queen++
			If Queen=9 Counter++
		   Else 
			Column++
	        }
		Column=1
	 	Row++
	    }
	    Queen--
	}
	Counter++
*/
 

import java.util.Scanner;
import becker.robots.City;
import becker.robots.Direction;
import becker.robots.Robot;
import becker.robots.Thing;
import java.lang.String;


public class QueensUsingBecker
{
	
   final static int OFF=-1;  //we use -1 as a value for a queen that is picked up (not on the board)
   
   //Queen Safe or Not Safe finals
   final static boolean SAFE=true;
   final static boolean NOT_SAFE=false;
   
   //Position in check or not in check by a queen finals
   final static boolean CHECK=true;
   final static boolean NOT_CHECKED=false;


   //These are used for animation level variable
   final static int FULL=2;
   final static int PARTIAL=1;
   final static int DISABLED=0;

   //default animation level is full animation
   static int animation=FULL;
   
   
   public static void main (String[] args){
	   mainLoop();
   }
   
   //This method is just the User Interface that takes an input for N
   public static void mainLoop(){
	   
	   Scanner keyb = new Scanner(System.in);
	   
	   	   //This boolean holds the users intention to continue playing
	   boolean play = false;
	   
	   //This value holds the users input value that needs to be translated
	   int response;
	   
	   
	   do {
		   if (play==false) System.out.println("~~N Queen's Problem~~ ");
 
		   System.out.print("(Enter -5 to quit) Solve for N = ");
		   response = keyb.nextInt();
		   
		   
		   //So we use any response 0 or higher int as a value for N. 
		   //-1 to -3 are used to change some program configuration
		   
		   //If user enters 0 or higher, we translate that to their intention to play
		   if 		(response>=0)   	play=true;
		   
		   //-1 tells the program to change animation to partial, so we don't animate the robot checking each coordinate
		   else if       (response==-1) {
			   animation=PARTIAL;
			   System.out.println("Animation is now set to level 1. (Partial Animation. Faster results.)");
			   mainLoop();			   
		   }
		   
		   //-2 tells is the animation is full, this is the default. Robot is animated for each step in the algorithm
		   else if       (response==-2) {
			   animation=FULL;
			   System.out.println("Animation is now set to level 2. (Default level. Full animation. Slowest)");
			   mainLoop();
		   }
		   
		   //-3 tells is to not animate at all. This can be used for faster results. Especially useful for higher numbers
		   else if       (response==-3) {
			   animation=DISABLED;
			   System.out.println("Animation is now level 0 (Animation disabled. Fastest results)");
			   mainLoop();
		   } 
		   
		   //Any other number below -3 tells us the user wants to quit
		   else                     play=false;
		   
		   //If intention is to play, we startup the chessboard
		   if (play) solveForN(response);
	   }
	   
	   while (play); //we quit when intention changes 	   
   }
   public static void solveForN(int response){		
		
		int n=response; //this is used to set the board size, and number of queens			
	    int boardSize = (n*n); //this integer is used for calculations of positions		
		int currentPos=0;			//This used to be two variables that showed the current position row and column. Unifying made more sense.		
		int counter=0;		//This is the number of solutions found so far, obviously initialized at 0
		
	    //Set up the initial board of size n x n

		int boardDimension;
        if (n==0) boardDimension=8; //avoids an error when using 0 for n, a boardsize 0x0 can't be created
		else boardDimension=n;

        City board = new City(boardDimension,boardDimension);	    
		

		//Robot is created and placed
		Robot rob = new Robot(board, 0, 0, Direction.EAST);			    
		
		Thing[] Queen; //we make an array of size n that creates n number of "Things" on the board
		Queen = new Thing[n];

		//for (int x=0;x<n;x++) {				
		//	Queen[x]=new Thing(board,0,0);
		//	rob.pickThing();
		//}		

		

		int[] queenPos;   //This array stores the positions of each queen for checking
		queenPos=new int[n]; //declares the length of the array to the value of n
		
		//This loops places n queens and has the robot pick up thing n times, to set us up
		for (int x=0;x<n;x++) {
			queenPos[x]= OFF;
			Queen[x]=new Thing(board,0,0);
			rob.pickThing();
		}		
		
		int queenNumber = 0;		//This keeps track of which queen is being placed, as well as how many have already been placed	
		
		
		//If you look closely, this while loop is written directly from my algorithm. This piece of code does the actual work.
		//It may seem backwards but there goTo() calls were deliberately put at the top of each if statement, to ensure proper loop exit
		while (queenNumber>=0){	
			
			if ((queenNumber==0)&&(currentPos==boardSize)) queenNumber--;   //This is just ensuring the final loop exit.
			
			//If all queens have been used, we add 1 to the solution counter, pick the the last queen and find the next position.
			else if (queenNumber==n) {				
				counter++;
				System.out.println("Found "+counter);
				queenNumber--;

				currentPos=queenPos[queenNumber];
				if (animation>0)
				{
					goTo(currentPos,rob,n);
					rob.pickThing();				
				}
				queenPos[queenNumber]=OFF;
				currentPos++;				
			}
			
			
			//If we reach the end of the chess board, we pick the last queen and find the next position.
			else if (currentPos==boardSize) {
				queenNumber--;
				currentPos=queenPos[queenNumber];
				if (animation>0) {
					goTo(currentPos,rob,n);
					rob.pickThing();
				}
				queenPos[queenNumber]=OFF;
				currentPos++;				
			}
			
			
			//If the current position is safe, we place the queen, and start finding a position for the next queen
			else if (safe(currentPos,queenPos,n)) {				
				queenPos[queenNumber]=currentPos;
				if (animation>0) {
					goTo(currentPos,rob,n);
					rob.putThing();				
				}
				queenNumber++;				
				currentPos++;				
			}		
			
			
			//If none of the above apply, we haven't finished the board or queens, and the position isn't safe. We simply move on.
			else {
				currentPos++;											
			}

			if ((animation>1)&&(currentPos!=boardSize)) goTo(currentPos,rob,n);
		}
		
		//This is the last statement executed in the program. Returning the solution.
		System.out.println("There are "+counter+" solutions to for N = "+n+".");		
   	}
   
   //This method runs a loop checking the given position against all the queens already on the board for approachability (safety).
   	public static boolean safe(int currentPos, int[] queenPos, int n){
	   int loop=0;
	   while (loop!=n){
		   if (check(currentPos,queenPos[loop],n)) return NOT_SAFE;
		   loop++;
	   }
	   return SAFE; //True if the position on the board is safe. False if approachable.	   
   	}
   
    
   	//This method takes two queen positions on a chess board and checks if they are approachable
   	public static boolean check(int pos1, int pos2, int n){   
   		
   	   if (pos2==OFF) return NOT_CHECKED; //if the queen is off the board, we don't even calculate to check approachability
   	   
   	   //These first two statements simply check column and row safety
	   if (((int)(pos1/n))==((int)(pos2/n))) return CHECK;
	   if (((int)(pos1%n))==((int)(pos2%n))) return CHECK;
	   
	   
	   //These variables simplify the code for checking diagonal safety
	   int diagx, diagy;
	   
	   
	   //These two lines just separate the individual position integer into two seperate integers for column and row. For diagonal checking.
	   diagx=(pos1/n);
	   diagy=(pos1%n);
	   
	   
	   //The following four loops check the chess board diagonally, going in all 4 directions from the given position, for safety.
	   while ((diagx<(n-1))&&(diagy<(n-1))){
		   diagx++;
		   diagy++;
		   if (((diagx*n)+diagy)==pos2) return CHECK;   //We return true if the position is in 'check' (by another Queen)
	   }
	   
	   
	   //This two variables have to be reset after each diagonal check (each while loop in this method), before they can be used again.
	   diagx=(pos1/n);
	   diagy=(pos1%n);
	   	   
	   while ((diagx>0)&&(diagy>0)){
		   diagx--;
		   diagy--;
		   if (((diagx*n)+diagy)==pos2) return CHECK;		   
	   }
	   
	   
	   diagx=(pos1/n);
	   diagy=(pos1%n);
	   
	   while ((diagx<(n-1))&&(diagy>00)){
		   diagx++;
		   diagy--;
		   if (((diagx*n)+diagy)==pos2) return CHECK;		   
	   }

	   diagx=(pos1/n);
	   diagy=(pos1%n);
	   
	   while ((diagx>0)&&(diagy<(n-1))){
		   diagx--;
		   diagy++;
		   if (((diagx*n)+diagy)==pos2) return CHECK;		   
	   }		   
	   return NOT_CHECKED; //When all the checks fail and all the while loops exit, we return false because the position is NOT in 'check'
   }
   
   
   //This code I wrote in class before I started thinking about the algorithm. 
   //I knew I would need a method that makes the robot goto coordinates x and y automatically
   //This method is independent and can easily be copied and used in any other becker based code
   public static void getTo(int a, int b, Robot rob){
	   	while ((rob.getAvenue()!=a)||(rob.getStreet()!=b)){
		   while (rob.getAvenue()<a){
			   while ((rob.getDirection())!=Direction.EAST) {
				   rob.turnLeft();
			   }
			   rob.move();			   
		   }
		   while (rob.getAvenue()>a){
			   while ((rob.getDirection())!=Direction.WEST) {
				   rob.turnLeft();
			   }
			   rob.move();			   
		   }
		   while (rob.getStreet()<b){
			   while ((rob.getDirection())!=Direction.SOUTH) {
				   rob.turnLeft();
			   }
			   rob.move();			   
		   }
		   while (rob.getStreet()>b){
			   while ((rob.getDirection())!=Direction.NORTH) {
				   rob.turnLeft();
			   }
			   rob.move();			   
		   }
	   }
   }
   
   //This method adapts the previous method getTo, to work more seamlessly with this particular program. 
   //I started using a single integer for position, and it became tedious to keep separating an integer into two digits. This helps.
   //getTo takes 2 integers for position. goTo filters it to take only 1 integer. 
   //goTo acts as a screen between getTo and the rest of the code.
   public static void goTo(int currentPos, Robot rob, int n){
	   getTo(   (int)(currentPos%n),      (int)(currentPos/n),   rob      );
   }      
}


/* For completeness, this was my first attempt, using my first algorithm.  
 * It is garbage at this point, as the code has been replaced.
 * 
 * 
 * 
 * 
 * 
 * 
public boolean safetyCheck(int a, int b){
	   Thing location = new Thing (board,1,1);
	   
	   (queen3.equals(location)) {return false;}
	   
	   
	   		
	   else return false;
}
public int queenStreet(Thing queen){
	   int a=1,b=1;
	   Thing location = new Thing(board, a,b);
	   while (a<9){
		   while (b<9){
			   if (queen.equals(location)) {return a;}
			   else {b++; location=new Thing(board,a,b);}			   
		   }
		   a++;
		   location = new Thing(board,a,b);
	   }
	   return 0;
}
public int queenAvenue(Thing queen){
	   int a=1,b=1;
	   Thing location = new Thing(board, a,b);
	   while (a<9){
		   while (b<9){
			   if (queen.equals(location)) {return b;}
			   else {b++; location=new Thing(board,a,b);}			   
		   }
		   a++;
		   location = new Thing(board,a,b);
	   }
	   return 0;
}

*/

