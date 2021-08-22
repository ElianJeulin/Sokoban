/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import Board.Board;

/**
 * Builder which permits to regroup the same methods for DatabaseBuilder and FileBoardBuilder
 * @author Ejeul
 */
public class BoardBuilder {
    
    /**
     * Makes a board with what TextBoardBuilder includes.
     * @param tbd a TextBoardBuilder
     * @return a Board
     */
    protected Board makesGrid(TextBoardBuilder tbd) {      
        char ch;
        int actualRow = 0;
        int actualCol = 0;
        
        Board board = new Board(tbd.getIndex(), tbd.getNbRow());
        
        for (int i = 0; i < tbd.getBoard().size(); i++) {
            for (int j = 0; j < tbd.getBoard().get(i).length(); j++) {
                ch = tbd.getBoard().get(i).charAt(j);

                switch (ch) {
                    case '#':
                        board.addHorizontalWall(1, actualRow, actualCol);
                        break;
                    case 'C':
                        board.addBox(actualRow, actualCol);
                        break;
                    case 'P':
                        board.setPosition(actualRow, actualCol);
                        break;
                    case 'X':
                        board.initializeTarget(actualRow, actualCol);
                        board.addTarget(actualRow, actualCol);
                        break;
                    default:
                        board.addNothing(actualRow, actualCol);
                        break;
                }
                if (actualRow == board.getNbCol() - 1) {
                    actualRow = 0;
                    actualCol++;
                } else {
                    actualRow++;
                }
            }
        }
        return board;
    }
}
