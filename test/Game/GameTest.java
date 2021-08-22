/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Board.Asset;
import Board.Board;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Ejeul
 */
public class GameTest {
    
    @Test
    public void MvtTest() {
        Board b = new Board(5, 6);
        b.setPosition(0, 0);
        b.addBox(0, 1);
        b.addBox(1, 0);
        b.addBox(2, 0);
        b.addHorizontalWall(1, 1, 1);
        b.initializeTarget(0, 2);
        b.addTarget(0, 2);
        b.displayGrid();
        Game g = new Game(b);
        
        
        //Le joueur ne peut pas pousser 2 caisses et donc reste sur place
        g.playerMvt(0, 0, 1, 0);
        assertEquals(Asset.PLAYER, b.getBoard()[0][0].getAsset());
        b.displayGrid();
        //Le joueur ne peut pas sortir du plateau et donc reste sur place
        g.playerMvt(0, 0, -1, 0);
        assertEquals(Asset.PLAYER, b.getBoard()[0][0].getAsset());
        b.displayGrid();
        //Le joueur pousse une caisse
        g.playerMvt(0, 0, 0, 1);
        assertEquals(Asset.PLAYER, b.getBoard()[1][0].getAsset());
        assertEquals(Asset.BOX, b.getBoard()[2][0].getAsset());
        b.displayGrid();
        //Le joueur ne peut pas se diriger vers un mur et reste donc sur place
        g.playerMvt(0, 1, 1, 0);
        assertEquals(Asset.PLAYER, b.getBoard()[1][0].getAsset());
        b.displayGrid();
        //Le joueur peut se pousser la caisse sur une cible
        g.playerMvt(0, 1, 0, 1);
        assertEquals(Asset.PLAYER, b.getBoard()[2][0].getAsset());
        assertEquals(Asset.BOX, b.getBoard()[3][0].getAsset());
        b.displayGrid();
        //Le joueur peut aller vers la droite et la cible doit se réafficher
        g.playerMvt(0, 2, 1, 0);
        assertEquals(Asset.PLAYER, b.getBoard()[2][1].getAsset());
        b.displayGrid();       
    }
    
    @Test
    public void winGameTest() {
        Board b = new Board(5, 6);
        b.setPosition(0, 0);
        
        //J'ajoute mes cible
        b.initializeTarget(3, 3);
        b.initializeTarget(2, 4);
        b.addTarget(3, 3);
        b.addTarget(2, 4);
        
        //Je les recouvre de box
        b.addBox(3, 3);
        b.addBox(2, 4);
        Game g = new Game(b);
        //Le jeu est donc gagné
        assertTrue(g.winGame());
        
        //Je rajoute une nouvelle cible
        b.initializeTarget(1, 1);
        b.addTarget(1, 1);
        //Le jeu n'est donc plus gagné
        assertFalse(g.winGame());
        
        //Je rajoute un joueur sur la cible
        b.setPosition(1, 1);
        //Le jeu n'est toujours pas gagné car les cibles ne sont pas recouvertes que par des box
        assertFalse(g.winGame());
    }
}
