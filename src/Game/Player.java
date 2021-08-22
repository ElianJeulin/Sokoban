/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Board.Board;
import Database.DatabaseBuilder;
import File.FileBoardBuilder;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Do the communication between the player and the game.
 *
 * @author ejeulin
 */
class Player extends Menu {

    private static Board board;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        boolean error;
        try {
            do {
                do {
                    error = false;
                    printMenu();
                    int menu = chooseMenu(1, 5);
                    
                    switch (menu) {
                        case 1:
                            board = new Board(5, 6);
                            board.makesGrid();
                            break;
                        case 2:
                            FileBoardBuilder fbd = new FileBoardBuilder();
                            try {
                                board = fbd.readFile("Level1.txt");
                            } catch (IOException ex) {
                                System.out.println(ex.getMessage());
                                error = true;
                            }
                            break;
                        case 3:
                            FileBoardBuilder fbd2 = new FileBoardBuilder();
                            try {
                                fbd2.createFile();
                                board = fbd2.readFile("MyLevel.txt");
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                                error = true;
                            }
                            break;
                        case 4:
                            DatabaseBuilder bdd = new DatabaseBuilder();
                            try {
                                board = bdd.databaseBuilder();
                            } catch (SQLException | IOException e) {
                                System.out.println("No database is instancied yet.");
                                error = true;
                            }
                            break;
                        default:
                            playerLeft();
                            break;
                    }
                } while (error);

                Game g = new Game(board);

                //Repeat while the player didn't passed the level
                do {
                    try {
                        g.oneTurn();
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                } while (!g.winGame());

                //Advert when you passed a level
                g.congratulateWinner();
            } while (true);
        } catch (GamePlayerLeaves e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prints the game menu.
     */
    private static void printMenu() {
        System.out.println("================");
        System.out.println("SOKOBAN");
        System.out.println("================\n\n");
        System.out.println("MENU\n");

        System.out.println("1. Level 1\n");
        System.out.println("2. Level 2\n");
        System.out.println("3. Create your own level\n");
        System.out.println("4. Use databases\n");
        System.out.println("5. Quit the game\n\n");
        System.out.println("What is your choice ?");
    }
}
