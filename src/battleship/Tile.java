// **************************************************************************
// Daniel Wallace
// 
// java.Tile
//
// **************************************************************************

package battleship;

/**
 *
 * @author cim114
 */
public class Tile 
{
    enum tileStatus {hit, miss, notGuessed}
    private String tile = "";
    private boolean occupied;
    private tileStatus guessed;
    
    private int coordX;
    private int coordY;
    
    // Constructor
    // creates tile object and initializes instance variables
    public Tile( int x, int y )
    {
        tile = "^";
        occupied = false;
        guessed = tileStatus.notGuessed;
        
        coordX = x;
        coordY = y;
    }
    
    // Pre: a tile has been created and a String parameter has been passed 
    //      into the method
    // Post: sets the String value of the tile to the String value passed in
    public void setTile(String t)
    {
        tile = t;
    }
    
    // Pre: a tile has been created
    // Post: returns the string representation of the tile
    public String getTile()
    {
        return tile;
    }
    
    // Pre: a tile has been created and a boolean parameter has been passed 
    //      into the method
    // Post: sets the status of the tile to the boolean value passed in
    public void setOccupied(boolean t)
    {
        occupied = t;
    }
    
    // Pre: a tile has been created
    // Post: returns if the specified tile is occupied or not
    public boolean getOccupied()
    {
        return occupied;
    }
    
    // Pre: a tile has been created and an int parameter has been passed 
    //      into the method
    // Post: sets the guessed instance variable of the tile to the boolean 
    //       to the corresponding tileStatus
    public void setGuessed(int g)
    {
        if(g == 1)
        {
            guessed = tileStatus.hit;
            setTile( "H" );
        }
        else if(g == 0)
        {
            guessed = tileStatus.miss;
            setTile( "M" );
        }
    }
    
    // Pre: a tile has been created
    // Post: returns if the specified tile has been hit, missed, or not yet 
    // guessed
    public tileStatus getGuessed()
    {
        return guessed;
    }
    
    public String toString()
    {
        return tile;
    }
    
    public int getCoordX()
    {
        return coordX;
    }
    
    public int getCoordY()
    {
        return coordY;
    }
}
