// **************************************************************************
// Daniel Wallace
// 
// java.seaGrid
//
// **************************************************************************

package battleship;

/**
 *
 * @author cim114
 */
import java.util.Random;
import java.util.Scanner;
public class SeaGrid 
{
    Scanner scan = new Scanner(System.in);
    private final int SIZE = 10;
    Tile [][] grid = new Tile[SIZE][SIZE]; // 10 x 10 grid
    
    // boolean for if ships are arranged automatically
    private boolean auto = false; 
    
    // Constructor
    // Creates a 10 X 10 seaGrid of tile objects
    public SeaGrid()
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                grid[i][j] = new Tile( j + 1, i + 1 );
            }
        }
    }
    
     /**  Dan Wallace
     * Mutator:  arrangeShipsManual()
     * @param ship // arranges all ships in the array
     * preconditions:  an array of ships has been created
     * postconditions:  arranges ships manually or automatically depending
     *                  on user's answer
     * Calls: manualOrientation() or arrangeShipsAuto() based on user's answer  
     */
    public void arrangeShipsManual(Ship[] ship)
    {
        String ans = "";
        do
        {
            System.out.println("Would you like to manually arrange your "
                    + "ships? Enter 'y' for yes and 'n' for no.");
            ans = scan.next();
        }
        while (!ans.toLowerCase().equals("y") && 
                !ans.toLowerCase().equals("n"));
        if (ans.equals("y"))
        {
            for (Ship boat : ship)
            {
                manualOrientation(boat);
            }
        }
        
        else if (ans.equals("n"))
        {
            arrangeShipsAuto(ship);
        }
    }
    
     /**  Dan Wallace
     * Mutator:  manualOrientation()
     * @param ship // reports its name
     * preconditions:  a ship object has been created
     * postconditions:  sets the orientation of the ship
     * Calls: manualCoordinate()
     */
    private void manualOrientation(Ship ship)
    {
        String ans = "";
        do
        {
            System.out.println("Enter the Orientation of the " 
                    + ship.getType() + ". Enter 'v' for vertical and"
                    + " 'h' for horizontal");
            ans = scan.next();
        }
        while (!ans.toLowerCase().equals("v") && 
            !ans.toLowerCase().equals("h"));
        if (ans.equals("v"))
        {
            ship.setDirection(Orientation.vertical);
        }   
        else
        {
            ship.setDirection(Orientation.horizontal);
        }
        manualCoordinate(ship); // user can now enter the starting coordinates
    }
    
     /**  Dan Wallace
     * Mutator:  manualCoordinate()
     * @param s // reports its orientation
     * preconditions:  user enters an orientation for the ship
     * postconditions:  if x and y values entered are valid, they are
     *                  passed to placedOnGrid() and setCoordinates()
     * Calls: desiredPlacement()
     */
    private void manualCoordinate(Ship s)
    {
        int x, y, size = s.getSize();
        Orientation orien;
        String ans= "";
        boolean valid; // true or false depending on validity of x and y
        do
        {
            // valid always set to true here so that it does not remain
            // false from the previous iteration of the loop
            valid = true;
            // gets x coordinate value from user
            System.out.println("Enter the the X-value of the ship's"
                + " starting coordinate");
            x = scan.nextInt();
            
            // subtracts 1 from x so array index is not out of bounds
            x -= 1;
            
            // gets y coordinate value from user
            System.out.println("Enter the the Y-value of the ship's"
                + " starting coordinate");
            y = scan.nextInt();
            
            // subtracts 1 from x so array index is not out of bounds
            y -= 1;
            
            orien = s.getDirection(); // gets orientation of boat
            
            // if vertical, y has to be within valid range of the ship
            // and x has to be between 0 and 9 inclusive
            if (orien == Orientation.vertical && !(y <= (9 - size + 1)
                && y >= 0 && x <= 9 && x >= 0))
            {
                // prints that ship placement is invalid
                System.out.println("Invalid Ship Placement. Please enter"
                    + " a valid starting location");
                valid = false;
            }
            
            // if horizontal, x has to be within valid range of the ship
            // and y has to be between 0 and 9 inclusive
            else if (orien == Orientation.horizontal && 
                !(x <= (9 - size + 1) && x >= 0 && y <= 9 && y >= 0))
            {
                // prints that ship placement is invalid
                System.out.println("Invalid Ship Placement. Please enter"
                    + " a valid starting location");
                valid = false;
            }
        }
        // keeps asking for new starting coordinates until valid coordinates
        // are given by user
        while(valid == false || !placedOnGrid(x, y, s));
        s.setCoordinates(orien, x, y);
        System.out.println(toString()); // prints current state of the grid
        desiredPlacement(x, y, s);
    }
    
    /**  Dan Wallace
     * Mutator:  manualCoordinate()
     * @param x // starting x coord
     * @param y // starting y coord
     * @param s // reports its size and orientation
     * preconditions:  a ship has been placed on the grid
     * postconditions:  clears previously entered ship if the current
     *                  placement is not desired
     * Calls: manualOrientation() if ship isn't in desired location
     */
    private void desiredPlacement(int x, int y, Ship s)
    {
        String ans = "";
        // asks user if the previously placed ship was placed as desired
        do
        {
        System.out.println("Is this the desired ship placement?"
            + " Enter 'y' for yes and 'n' for no.");
        ans = scan.next();
        }
        while (!ans.toLowerCase().equals("y") && 
                !ans.toLowerCase().equals("n"));
        
        // if ship placement is not desired, the ship is removed from the grid
        // and the user can reenter the orientation and starting coordinates
        if (ans.equals("n"))
        {
            clearShip(x, y, s); // clears ship
            
            // prints grid without previous ship
            System.out.println(toString());
            manualOrientation(s); // user can enter orientation and
            // coordinates of ship again
        }
    }
    
 /**  prof aw coded for team
  * Mutator:  arrangeShipsAuto
  * Description:  for each ship, determine size to find possible valid
  *               position range, then generate random # for row and col
  *               based on orientation;repeat placedOnGrid() until successful
  * preconditions:  Ships[] are constructed
  * postconditions: Ships[] are arranged onto the grid via random
  * Calls: placedOnGrid()
  * @param ships 
  */   
    public void arrangeShipsAuto(Ship[] ships)
    {
        // class knows when ships are arranged automatically
        // auto assigned true
        auto = true;
        Random rand = new Random(); // new random object
        // generates correct starting coordinates for all ships
        for(Ship boat: ships)
        {
            int tileX, tileY;// random x, y coordinate to place the ship
                    
            int howBig = boat.getSize();
            int range = SIZE - howBig + 1; // sets correct range of 
            // random numbers to be generated for this ship
            
            int oddEven = rand.nextInt(2); // generates random num
            // (either 0 or 1) and determines orientation based on this num
            Orientation way = (oddEven == 0) ? Orientation.horizontal : 
                    Orientation.vertical;
            boat.setDirection(way);
                
            do
            {
               // ship is horizontal 
              if (boat.getDirection() == Orientation.horizontal)   
              {
                tileX = rand.nextInt(range) ;// x coordinated limited to range
                tileY = rand.nextInt(SIZE);// can have any valid y coordinate
              }
              // ship is vertical
              else
              {
                tileX = rand.nextInt(SIZE);// can have any valid x coordinate
                tileY = rand.nextInt(range);// y coordinate limited to range
              }
            }
            // checks if tiles are available
            while (!placedOnGrid(tileX, tileY, boat)); 
            boat.setCoordinates(way, tileX, tileY);
            
        }
    }
    /**  prof aw coded for team
     * Mutator:  placedOnGrid()
     * @param x // starting x coord
     * @param y // starting y coord
     * @param boat // reports its size and orientation
     * @return success // yes, or no; Ship could be placed in the requested
     *                 // location
     * preconditions:  x and y starting locations are valid for the ship
     * postconditions:  if tiles are open ("^") ship is placed on grid and
     *                  method returns true; otherwise returns false
     */
    private boolean placedOnGrid(int x, int y, Ship boat)
    {
        boolean success = false;    // ship placed on seaGrid
        boolean clear = true;       // seaGrid tile is available
        // ship orientation is horizontal
         if (boat.getDirection() == Orientation.horizontal)
         {
            for (int i = x, j = 0; j < boat.getSize() && clear ; i++, j++)
            {
                //**********************************************
                // WHEN CHECKING 2 DIMENSIONAL ARRAY
                // X & Y ARE INVERTED
                // i.e. grid[y][x] 
                // X is the horizontal dimension & X is incremented
                // Y is the vertical dimension
                //**********************************************
                if(!(grid[y][i].getTile() == "^"))
                {
                   clear = false; 
                }
            }
            if (clear)  // place ship on grid, then set success to true
            {
                success = true;
                for (int i = x, j = 0; j<boat.getSize(); i++, j++)
                {
                    grid[y][i].setTile(boat.getSymbol());
                    grid[ y ][ i ].setOccupied( true );
                }
            }
         }
         
         else // follows same logic for horizontal placement of ship except
              // y is incremented instead of x
         {
            for (int i = y, j = 0; j < boat.getSize() && clear ; i++, j++)
            {
                if(!(grid[i][x].getTile() == "^"))
                {
                   clear = false; 
                }
            }
            if (clear)  // place ship on grid, then set success to true
            {
                success = true;
                for (int i = y, j = 0; j<boat.getSize(); i++, j++)
                {
                    grid[i][x].setTile(boat.getSymbol());
                    grid[ i ][ x ].setOccupied( true );
                }
            }
         }
         if (!success && !auto)
         {
            // Prints statement that you can't overlap ships if the user tries to
            // overlap ships
            // also only prints this statement if the ships are manually arranged
            // ships are manually arranged if auto == false
             System.out.println("You cannot overlap ships. Please enter"
                    + " a valid positioning of the ship.");
         }
        return success;
    }
    
    /**  Dan Wallace
     * Mutator:  clearShip()
     * @param x // starting x coord
     * @param y // starting y coord
     * @param ship // reports its size and orientation
     * preconditions:  x and y starting locations are valid for the ship
     * postconditions:  replaces all tiles of the previous ship placed on 
     *                  the grid with "^"
     */
    public void clearShip(int x, int y, Ship ship)
    {
        // ship orientation is vertical
        if (ship.getDirection() == Orientation.vertical)
        {
            for (int i = y, j = 0; j < ship.getSize(); i++, j++)
            {
                // sets all tiles of the previously placed ship to "^"
                grid[i][x].setTile("^");
            }
        }
        else
        {
            // ship orientation is horizontal
            for (int i = x, j = 0; j < ship.getSize(); i++, j++)
            {
                // sets all tiles of the previously placed ship to "^"
                grid[y][i].setTile("^");
            }
        }
    }
    
    public Tile getTile( int x, int y )
    {
        return grid[ y - 1 ][ x  - 1 ];
    }
    
    // returns string representation of the seaGrid created
    public String toString()
    {
       String grid1 = ""; // string representing grid
       int y = 1; // int to display y values of grid
       for (int x = 0; x <= 10; x++)
       {
           grid1 += "" + x + "\t"; // // displays x values of grid
       }
       grid1 += "\n\n";
       for (int i = 0; i < SIZE; i++, y++) 
       {
           grid1 += "" + y + "\t"; // displays y value of grid
           for (int j = 0; j < SIZE; j++)
           {
               grid1 = grid1.concat(grid[i][j] + "\t");
           }
           grid1 = grid1.concat("\n\n");
       }
       return grid1; // returns completed string representation of grid
    }
}
