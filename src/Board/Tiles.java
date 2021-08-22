/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

/**
 * A tile constitutes a space on the board
 * @author ejeulin
 */
public class Tiles {
    
    private Asset asset;

    protected Tiles() {
        this.asset = Asset.NONE;
    }

    /**
     * Changes the asset of a case
     * @param a the new asset
     */
    protected void setAsset(Asset a) {
        this.asset = a;
    }

    /**
     * Prints the asset of the case
     * @return a string corresponding to the asset
     */
    @Override
    public String toString() {
        switch (asset) {
            case NONE:
                return " . ";
            case TARGET:
                return " X ";
            case PLAYER:
                return " P ";
            case BOX:
                return " C ";
            case WALL:
                return " # ";
            default:
                return "";
        }
    }
    
    /**
     * Return the asset of the case
     * @return the asset value
     */
    public Asset getAsset() {
        return this.asset;
    }
}
