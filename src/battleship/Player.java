/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.Random;
import java.util.Stack;

/**
 *
 * @author sjanj
 */
public class Player 
{
    private String  name;
    private SeaGrid grid;
    private SeaGrid enemyGrid;
    private Ship[]  ships;
    
    private Stack< Coordinate > potentialGuesses;
    private Stack< Coordinate > hitStack;
    
    private int numSunkShips;
    
    private Coordinate tempHit;
    
    private boolean AI = false;
    
    public Player( String name )
    {
        this.name = name;
        grid      = new SeaGrid();
        enemyGrid = new SeaGrid();
        ships     = new Ship[ 5 ];
        
        potentialGuesses = new Stack<>();
        hitStack         = new Stack<>();
        
        ships[ 0 ] = new Ship( ShipKind.destroyer );
        ships[ 1 ] = new Ship( ShipKind.cruiser );
        ships[ 2 ] = new Ship( ShipKind.carrier );
        ships[ 3 ] = new Ship( ShipKind.submarine );
        ships[ 4 ] = new Ship( ShipKind.frigate );
    }
    
    public void arrangeShipsAuto()
    {
        grid.arrangeShipsAuto( ships );
    }
    
    public void arrangeShipsManually()
    {
        grid.arrangeShipsManual( ships );
    }
    
    public void attack( Player enemy, int x, int y )
    {
       if ( enemy.getGrid().getTile( x,  y ).getGuessed() == Tile.tileStatus.notGuessed )
       {
           if ( enemy.getGrid().getTile( x, y ).getOccupied() == true )
           {
               enemyGrid.getTile( x, y ).setGuessed( 1 );
               
               enemy.getGrid().getTile( x, y ).setGuessed( 1 );
               
               hitStack.push( new Coordinate( x, y ) );
            
               if ( potentialGuesses.isEmpty() )
               {
                   potentialGuesses.push( new Coordinate( x, y - 1 ) );
                   potentialGuesses.push( new Coordinate( x, y + 1 ) );
                   potentialGuesses.push( new Coordinate( x + 1, y ) );
                   potentialGuesses.push( new Coordinate( x - 1, y ) );
               }
               
               if ( hitStack.size() > 1 )
               {
                   tempHit = hitStack.pop();
                   
                   if ( tempHit.coordX == hitStack.peek().coordX
                       && tempHit.coordY < hitStack.peek().coordY )
                   {
                       potentialGuesses.push( new Coordinate( x, y + 1 ) );
                   }
                   
                   if ( tempHit.coordX == hitStack.peek().coordX
                       && tempHit.coordY > hitStack.peek().coordY )
                   {
                       potentialGuesses.push( new Coordinate( x, y - 1 ) );
                   }
                   
                   if ( tempHit.coordY == hitStack.peek().coordY
                       && tempHit.coordX > hitStack.peek().coordX )
                   {
                       potentialGuesses.push( new Coordinate( x + 1, y ) );
                   }
                   
                   if ( tempHit.coordY == hitStack.peek().coordY
                       && tempHit.coordX > hitStack.peek().coordX )
                   {
                       potentialGuesses.push( new Coordinate( x - 1 , y ) );
                   }
               }
           }
           
           else
           {
               enemyGrid.getTile( x, y ).setGuessed( 0 );
               
               enemy.getGrid().getTile( x, y ).setGuessed( 0 );
           }
       }
       
       else
       {
           if ( !AI )
           {
              
           }
           
           else
           {
               randomAttack( enemy );
           }
       }
    }
    
    public void randomAttack( Player enemy )
    {
        AI = true;
        
        int x;
        int y;
        
        if ( potentialGuesses.isEmpty() )
        {
            

            Random rand = new Random();

            x = rand.nextInt( 10 ) + 1;
            y = rand.nextInt( 10 ) + 1;

            attack( enemy, x, y );
            
//            System.out.println( "Computer attacked coordinates ( " + x + ", " 
//                + y + " )" );
        }
        
        else
        {
            
            boolean validCoord = false;
            
            Coordinate coord;
            
            do
            {
                // TODO: check if stack is empty
                coord = potentialGuesses.pop();
                
                if ( coord.coordX > 0 && coord.coordX < 11 &&
                     coord.coordY > 0 && coord.coordY < 11 )
                {
                    validCoord = true;
                }
            }
            while ( validCoord == false );
            
            x = coord.coordX;
            y = coord.coordY;
            
            attack( enemy, x, y );
    
        }
        
        System.out.println( "Computer attacked coordinates ( " + x + ", " 
                + y + " )" );
        
        
    }
    
    public SeaGrid getGrid()
    {
        return grid;
    }
    
    public String toString()
    {
        String str = "";
        
        str += "Player Grid" + "\n";
        str += grid.toString();
        str += "\n\n\n";
        str += "Enemy Grid" + "\n";
        str += enemyGrid.toString();
        
        return str;
    }
}
