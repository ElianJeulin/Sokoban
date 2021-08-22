/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Permits to regroup all informations of different builders
 * @author Ejeul
 */
public class TextBoardBuilder{

    private final ArrayList<String> board = new ArrayList<>();
    
    private int index = 0;
    private int numberRow;

    /**
     * Takes the parameters of a board and construct an ArrayList according to him.
     * @param row The line
     * @throws IOException 
     */
    public void addRow(String row) throws IOException {
        board.add(row);
        if (numberRow < row.length()) {
            numberRow = row.length();
        }  
        index++;
    }
    
    /**
     * Get the index 
     * @return the width
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Get the number of rows
     * @return the height
     */
    public int getNbRow() {
        return numberRow;
    }  
    
    /**
     * Get the board
     * @return an ArrayList which contains the board
     */
    public ArrayList<String> getBoard() {
        return board;
    }
}
