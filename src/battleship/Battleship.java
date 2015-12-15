/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;

import java.util.Scanner;

/**
 *
 * @author cim114
 */
public class Battleship {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
//        SeaGrid grid = new SeaGrid();
//        System.out.println(grid);
//        Ship [] s = new Ship[5];
//        for (int i = 0; i < s.length; i++)
//        {
//            s[i] = new Ship(ShipKind.carrier);
//        }
//        grid.arrangeShipsManual(s);
//        System.out.println(grid);
        
        Player player = new Player( "Sarim" );
        Player enemy  = new Player( "Computer" );
        
        player.arrangeShipsAuto();
        enemy.arrangeShipsAuto();
        
        System.out.println( player );
        
        int x, y;
        
        Scanner cin = new Scanner( System.in );
        
        for ( int i = 0; i < 100; i++ )
        {
            System.out.println( "\n" );
            System.out.print( "Enter x coordinate to attack: " );
            x = cin.nextInt();
            System.out.print( "Enter y coordinate to attack: " );
            y = cin.nextInt();
            
            player.attack( enemy, x, y );
            enemy.randomAttack( player );
            
            System.out.println( "\n" );
            System.out.println( player );
       }
    }
}
