/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Ejeul
 */
public class TilesTest {
    
    @Test
    public void setColorTest() {
        Tiles a = new Tiles();
        a.setAsset(Asset.BOX);
        assertEquals(Asset.BOX, a.getAsset());
        a.setAsset(Asset.WALL);
        assertEquals(Asset.WALL, a.getAsset());
    }
    
    @Test
    public void toStringTest() {
        Tiles a = new Tiles();
        assertEquals(" . ", a.toString());
        a.setAsset(Asset.PLAYER);
        assertEquals(" P ", a.toString());
        a.setAsset(Asset.WALL);
        assertEquals(" # ", a.toString());        
    }
}
