/*  
 *  CSC-223 Project #5 Battleship
 *  Fall 2015 Team Glowing Burito
 *  
*/

package battleship;

/**
 *  CSC-223 CS2
 *
 * @author A Wright
 * @date  12/4/2015
 * Class Description:  A Ship consists of a type, a size, a status (hit, sunk)
 *       a hit count (how much damage), and a direction (on the grid)
 *       The size, status, and type have Set/Get functionality
*        The Direction, xCoord, yCoord have get methods
 
 */
public class Ship
{
   private int size;          // ship size (2 - 5)
   private Status state;      // open, hit, or sunk
   private ShipKind type;     // destroyer, frigate, sub, carrier, cruiser
   private String symbol;     // corresponds to type: D, F, U, A, C, 
   private int hitCount;      // how many hits -- initially 0
   private Orientation direction; // portrait or landscape ;-)
   // private Coordinate location; // where ship is positioned on the grid
   private int rowStart, // if not using Coordinate class
               colStart;
  
   public Ship( ShipKind typeIn)
    {
       type = typeIn;
       state = Status.Open;
       hitCount = 0;
       setSize();     // size is in accordance with shipKind
       setSymbol();   // symbol is in accordance with shipKind
       
    }
//-----------------------------------------------------------------------   
/**
 * Mutator: hit() increments hit count when a Ship is hit
 * preconditions:  none
 * postconditions: adds one to hit count of the ship, if hitCount = size
                  change state to SUNK
 */   
   public void hit()
    {
       hitCount++;
       state = Status.Hit;
       
       if (hitCount == size)
       {
           state = Status.Sunk;
       }
    }
//-----------------------------------------------------------------------   
   /**
    * Accessor: toString()
    * returns a string to display the status of the ship (type & state)
    * also includes rowStart, colStart and Orientation for debugging
    * @return out //infoAboutThisShip
    */
   public String toString()
   {
       String out = "";
       out += ("Type: " + type + "\tStatus: " + state );
      // for debug
      // out += ("Coordinates: [" + rowStart + ", " + colStart + 
       //        "]\tOrientation: " + direction );
       return out;
   }
   //----------------------S E T T E R S -------------------------------
   /**
    * preconditions: Ship is constructed with type -- a ShipKind
    * postconditions: size is set corresponding to type of ship
    * @param squares must correspond to the ship type
    */
   private void setSize()
   {
       switch(type)
       {
           case destroyer:
               size = 2; break;
           case frigate:
           case submarine:
               size = 3; break;
           case cruiser:
               size = 4; break;
           case carrier:
               size = 5; break;
               
       }
   }
//-----------------------------------------------------------------------
   /**
    * Mutator: setSymbol()  according to type of Ship
    */
   private void setSymbol()
    {
        String symbol = "";
        switch(getType())
            {
                case destroyer:
                    this.symbol = "D";
                    break;
                case frigate:
                     this.symbol = "F";
                    break;
                case carrier:
                    this.symbol = "A";
                    break;
                case submarine:
                     this.symbol = "U";
                    break;   
                    case cruiser:
                    this.symbol = "C";
                    break;               
            }
    }
   //----------------------------------------------------------------------- 
   /**
   Mutator:  setCoordinates  Set the starting row, col and direction
   * preconditions: x, y < gridXSize, gridYSize
   * postconditions:  rowStart and colStart are set to inputs, direction is
   *                  set to input
   */
   public void setCoordinates(Orientation facing, int x, int y)
   {
       direction = facing;
       if (x < 0 ) rowStart = 0;
       else  rowStart = x;
       
       if ( y < 0  )  colStart = 0;
       else colStart = y;
   }
   public void setDirection(Orientation facing)
   {
       direction = facing;
   }
   //---------------------- G E T T E R S -----------------------------
   /**
    * Accessor getStartRow()
    * @return rowStart
    */
   public int getStartRow()
   {
       return rowStart;
   }
   //-----------------------------------------------------------------------
    /**
    * Accessor getStartRow()
    * @return rowStart
    */
   public int getStartCol()
   {
       return colStart;
   }
   //-----------------------------------------------------------------------
   /**
    * Accessor:  getDirection
    * @return direction // ship is either vertical or horizontal on the grid
    */
   public Orientation getDirection()
   {
       return direction;
   }
   //-----------------------------------------------------------------------
    /**
    *  Accessor:  getStatus()
    * 
    * @return state // of the ship, init Open, after being guessed will
    *                  be Hit, after HitCount == size, Sunk
    */
   public Status getStatus()
   {
       return state;
   }
   //-----------------------------------------------------------------------
   /**
    * Accessor: getSize()
    * @return size    // ship Size based on type (e.g. Carrier = 5)
    */
   public int getSize()
   {
       return size;
   }
   //-----------------------------------------------------------------------
/**
 * Accessor: getType()
 */  
   public ShipKind getType()
   {
       return type;
   }
     //-----------------------------------------------------------------------
/**
 * Accessor: getSymbol()
 */
    public String getSymbol()
    {
        
        return symbol;
    }
}
