/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import java.util.ArrayList;

/**
 * The construction of the board.
 * @author ejeulin
 */
public class Board {
    
    private final int NB_ROW;
    private final int NB_COL;

    private int playerPosX;
    private int playerPosY;

    private final ArrayList<Integer> targetsPosX = new ArrayList<>();
    private final ArrayList<Integer> targetsPosY = new ArrayList<>();

    private final Tiles[][] board;

    public Board(int NB_ROW, int NB_COL) {
        this.NB_ROW = NB_ROW;
        this.NB_COL = NB_COL;

        this.board = new Tiles[NB_ROW][NB_COL];
        for (int row = 0; row < NB_ROW; row++) {
            for (int col = 0; col < NB_COL; col++) {
                board[row][col] = new Tiles();
            }
        }
    }
    
    /**
     * Get positions X of targets
     * @return an ArrayList of integer
     */
    public ArrayList<Integer> getTargetX() {
        return targetsPosX;
    }
    
    /**
     * Get positions Y of targets
     * @return an ArrayList of integer
     */
    public ArrayList<Integer> getTargetY() {
        return targetsPosY;
    }
    
    /**
     * Get the number of columns.
     * @return a integer
     */
    public int getNbCol() {
        return NB_COL;
    }
    
    /**
     * Get the player position X
     * @return a integer
     */
    public int getPlayerPosX() {
        return playerPosX;
    }
    
    /**
     * Get the player position Y
     * @return 
     */
    public int getPlayerPosY() {
        return playerPosY;
    }
    
    /**
     * Get the number of rows
     * @return a integer
     */
    public int getNbRow() {
        return NB_ROW;
    }
    
    /**
     * Get the board 
     * @return a board
     */
    public Tiles[][] getBoard() {
        return board;
    }

    /**
     * Method which permits to display the board.
     */
    public void displayGrid() {
        StringBuilder grid = new StringBuilder();

        for (int i = 0; i < NB_ROW; i++) {
            for (int j = 0; j < NB_COL; j++) {
                grid.append(board[i][j]);
            }
            grid.append("\n");
        }
        System.out.println(grid);
    }
    
    /**
     * Makes a hard coded grid which is the level one.
     */
    public void makesGrid() {
        addHorizontalWall(6, 0, 0);
        addHorizontalWall(6, 0, 4);
        addVerticalWall(5, 0, 0);
        addVerticalWall(5, 5, 0);
        addBox(1, 2);
        addBox(3, 2);
        initializeTarget(1, 3);
        initializeTarget(2, 3);
        addTarget(1, 3);
        addTarget(2, 3);
        setPosition(4, 3);
    }

    /**
     * Add vertical walls on the board
     * @param times the number of wall
     * @param posX the position X
     * @param posY the position Y
     */
    public void addVerticalWall(int times, int posX, int posY) {
        for (int i = 0; i < times; i++) {
            board[posY + i][posX].setAsset(Asset.WALL);
        }
    }

    /**
     * Add horizontal walls on the board
     * @param times the number of wall
     * @param posX the position X
     * @param posY the position Y
     */
    public void addHorizontalWall(int times, int posX, int posY) {
        for (int i = 0; i < times; i++) {
            board[posY][posX + i].setAsset(Asset.WALL);
        }
    }

    /**
     * Add a crate on the board
     * @param posX the position X
     * @param posY the position Y
     */
    public void addBox(int posX, int posY) {
        board[posY][posX].setAsset(Asset.BOX);
    }

    /**
     * Initialize positions of target on an ArrayList
     * @param posX the position X
     * @param posY the position Y
     */
    public void initializeTarget(int posX, int posY) {
        targetsPosX.add(posX);
        targetsPosY.add(posY);
    }
    
    /**
     * Add a target on the board
     * @param posX the position X
     * @param posY the position Y
     */
    public void addTarget(int posX, int posY) {
        board[posY][posX].setAsset(Asset.TARGET);
    }

    /**
     * Add the player on the board and keep his actual position 
     * @param posX the position X
     * @param posY the position Y
     */
    public void setPosition(int posX, int posY) {
        board[posY][posX].setAsset(Asset.PLAYER);
        playerPosX = posX;
        playerPosY = posY;
    }

    /**
     * Add nothing on the board (a point '.')
     * @param posX the position X
     * @param posY the position Y
     */
    public void addNothing(int posX, int posY) {
        board[posY][posX].setAsset(Asset.NONE);
    }
}
