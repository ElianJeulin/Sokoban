/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Board.Asset;
import Board.Board;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author ejeulin
 */
public class BoardTest {

    @Test
    public void oneTurnTest() {
        Board b = new Board(5, 6);
        System.out.println("Affichage Plateau Vide");
        b.displayGrid();
        
        System.out.println("\n");
        
        System.out.println("Affichage Plateau");
        
        b.addHorizontalWall(6, 0, 0);
        b.addHorizontalWall(6, 0, 4);
        b.addVerticalWall(5, 0, 0);
        b.addVerticalWall(5, 5, 0);
        b.addBox(1, 2);
        b.addBox(3, 2);
        b.addTarget(1, 3);
        b.addTarget(2, 3);
        b.setPosition(4, 3);
        
        b.displayGrid();
    }
    
    @Test
    public void addTest() {
        Board b = new Board(5, 6);
        
        b.addBox(3, 3);
        b.addTarget(4, 3);
        assertEquals(Asset.BOX, b.getBoard()[3][3].getAsset());
        assertEquals(Asset.TARGET, b.getBoard()[3][4].getAsset());
    }
}
