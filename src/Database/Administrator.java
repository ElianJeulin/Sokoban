/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Game.GamePlayerLeaves;
import Game.Menu;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Do the communication between the administrator and the database
 *
 * @author Ejeul
 */
class Administrator extends Menu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            do {
                Connection c = connexionToDatabase();
                Database database = new Database(c);

                printMenu();
                int menu = chooseMenu(1, 6);

                try {
                    switch (menu) {
                        case 1:
                            database.createTable();
                            break;
                        case 2:
                            database.listBoard();
                            break;
                        case 3:
                            database.showBoard();
                            break;
                        case 4: 
                            try {
                            database.addBoardFromFile();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                        case 5:
                            database.removeBoard();
                            break;
                        default:
                            playerLeft();
                            break;
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            } while (true);
        } catch (GamePlayerLeaves e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Method which prints the database menu.
     */
    private static void printMenu() {
        System.out.println("================");
        System.out.println("SOKOBAN");
        System.out.println("================\n\n");
        System.out.println("ADMINISTRATION INTERFACE - USE WITH CAUTION\n");

        System.out.println("1. Create new database\n");
        System.out.println("2. List boards\n");
        System.out.println("3. Show board\n");
        System.out.println("4. Add board from file\n");
        System.out.println("5. Remove board from database [DANGEROUS]\n");
        System.out.println("6. Quit the game\n\n");
        System.out.println("What is your choice ?");
    }

    /**
     * Do the connexion with the database.
     *
     * @return a Connection
     */
    protected static Connection connexionToDatabase() {
        Connection c = null;
        String path = "data\\test.sqlite3";
        String URL = "jdbc:sqlite:" + path;
        Administrator.loadDriverSQLite();
        try {
            c = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            System.out.println("Impossible connexion");
        }
        return c;
    }

    /**
     * Loads the driver SQlite
     */
    protected static void loadDriverSQLite() {
        String sqlite_driver = "org.sqlite.JDBC";
        try {
            Class.forName(sqlite_driver);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver " + sqlite_driver + " not found.");
            System.exit(1);
        }
    }
}
