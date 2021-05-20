/*Maze Game Project that creates a maze using data pulled from an external file, and allows user to move the space using the arrow keys
By Madison Corder*/

import javafx.event.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.geometry.*;
import javafx.stage.*;
import java.util.*;
import java.io.*;
import javafx.scene.paint.Color;

public class MazeGame extends Application
{
   FlowPane maze = new FlowPane();
   Canvas mazeFill = new Canvas();
   GraphicsContext gc = mazeFill.getGraphicsContext2D();   
   //2D array of type MazeSlot
   MazeSlot [][] mazeArray = new MazeSlot[21][21];
   //number is either 0 or 1, startX and startY is keeping track of the active position, and ending position
   int number,startX, startY, endX;
   //to get the starting postion
   boolean active = true; 

   public void start(Stage stage)   
   {      
      maze.setAlignment(Pos.CENTER);
      mazeFill.setHeight(525);      
      mazeFill.setWidth(525);         
      //try/catch to scan in data from MazeData file
      try 
      {
         Scanner file = new Scanner(new File("MazeData.txt"));
         for (int j=0; j<21; j++)
         {
            for (int i=0; i<21; i++)
            {
               number = file.nextInt();
               //if number is 0 then the fill will be white
               if (number==0)
               {
                  gc.setFill(Color.WHITE);
                  //the first 0 will be true and so this overrides the fill from white to cyan and then sets the starting position
                  if (active == true)
                  {
                     gc.setFill(Color.CYAN);
                     startX = i;
                     startY = j;
                  }
                  //makes active false after the first 0
                  active = false;
                  //keeping track of the ending posistion only when j is 20 and number is 0
                  if (j==20)
                  {
                     endX = i;
                  }
               }
               //if number is 1 then fill will be white
               if (number==1)
               {
                  gc.setFill(Color.BLACK);
               }
               gc.fillRect(i*25,j*25, 25, 25);
               //contructor for every space, with 0 or 1 and their x and y positions
               mazeArray[i][j] = new MazeSlot(number); 
            }    
         } 
      }
      catch (FileNotFoundException fnfe)
      {
      }  
      //adds canvas to flowpane
      maze.getChildren().add(mazeFill);
      
      //keylistener
      maze.setOnKeyPressed(new KeyListener());
      
      Scene scene = new Scene(maze, 525, 525);      
      stage.setScene(scene);      
      stage.setTitle("The Maze Game");      
      stage.show();      
            
      maze.requestFocus();   
   }   
   public static void main(String[] args)   
   {      
      launch(args);   
   }
   
   public class KeyListener implements EventHandler<KeyEvent>
   {
      public void handle(KeyEvent event)
      {
         //active square moving
         //when up is pressed
         if (event.getCode() == KeyCode.UP)
         {
            //to keep the space and the check space in bounds
            if(startY-1>=0)
            {  
               //checking whether the space the user pressed is black or white, and only allowing action if next space is white
               if (mazeArray[startX][startY-1].getNum()==0)
               {
                  //sets current slot to white
                  gc.setFill(Color.WHITE);
                  gc.fillRect(25*startX,25*startY,25,25);
                  //up moves changes the Y in the negitive direction but makes sure it is inbounds
                  if (startY-1<0)
                     startY = 0;
                  else
                     startY--;
                  //sets new slot to cyan
                  gc.setFill(Color.CYAN);
                  gc.fillRect(25*startX,25*startY,25,25);
               }
            }
         }
         //when down is pressed
         if (event.getCode() == KeyCode.DOWN)
         {
            if (startY+1<21)
            {
               //check and see if it the number is 0 and if move is in bounds
               if (mazeArray[startX][startY+1].getNum()==0&&startY+1<21)
               {
                  gc.setFill(Color.WHITE);
                  gc.fillRect(25*startX,25*startY,25,25);
                  //down changes the y in the positive direction
                  startY++;
                  gc.setFill(Color.CYAN);
                  gc.fillRect(25*startX,25*startY,25,25);
                  //winning only on a down arrow
                  if (startX==endX&&startY==20)
                  {
                     System.out.println("YOU WIN!!");
                  }
               } 
            }       
         }
         //when left is pressed
         if (event.getCode() == KeyCode.LEFT)
         {
            if (startX-1>=0)
            {
               if (mazeArray[startX-1][startY].getNum()==0)
               {
                  gc.setFill(Color.WHITE);
                  gc.fillRect(25*startX,25*startY,25,25);
                  //left moves the slot in the negative x direction
                  startX--;
                  gc.setFill(Color.CYAN);
                  gc.fillRect(25*startX,25*startY,25,25);
               }  
            }
         }
         //when right is pressed
         if (event.getCode() == KeyCode.RIGHT)
         {
            if (startX+1<21)
            {
               if (mazeArray[startX+1][startY].getNum()==0)
               {
                  gc.setFill(Color.WHITE);
                  gc.fillRect(25*startX,25*startY,25,25);
                  //right moves the slot in the positive x direction
                  startX++;
                  gc.setFill(Color.CYAN);
                  gc.fillRect(25*startX,25*startY,25,25);
               }
            }
         }
      }
   }
}