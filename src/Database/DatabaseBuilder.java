/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Board.Board;
import File.BoardBuilder;
import File.TextBoardBuilder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Permits to create a board with the Database.
 *
 * @author Ejeul
 */
public class DatabaseBuilder extends BoardBuilder {

    private final Scanner entree = new Scanner(System.in);

    /**
     * Constructs a board according to a board choose into the database
     *
     * @return a Board
     * @throws SQLException
     * @throws IOException
     */
    public Board databaseBuilder() throws SQLException, IOException {

        Connection c = Administrator.connexionToDatabase();
        Database d = new Database(c);
        TextBoardBuilder tbd;
        boolean confirm;

        System.out.println("\n Please choose a board from this list. \n");
        d.listBoard();

        do {
            tbd = showBoard(c);
            confirm = confirmChoice();
        } while (!confirm);

        Board board = makesGrid(tbd);
        return board;
    }

    /**
     * Ask the player to have his confirmation for a choice
     *
     * @return the confirmation of the player
     */
    private boolean confirmChoice() {
        boolean confirm;
        System.out.println("Would you like to play on this board? (yes/no)");
        String answer = entree.nextLine();
        switch (answer) {
            case "yes":
                confirm = true;
                System.out.println("Level launched.");
                break;
            case "no":
                System.out.println("Please enter a new board from this list.");
                confirm = false;
                break;
            default:
                System.out.println("Misunderstood answer");
                confirm = false;
                break;
        }
        return confirm;
    }

    /**
     * Shows a board according to his ID and construct a TextBoardBuilder according to him
     *
     * @throws SQLException
     */
    private TextBoardBuilder showBoard(Connection c) throws IOException, SQLException {
        TextBoardBuilder tbd = new TextBoardBuilder();
        System.out.println("ID of board ? ");
        String id = entree.nextLine();
        String sql = "select description from ROWS "
                + "where board_id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, id);
        ResultSet r = ps.executeQuery();
        while (r.next()) {
            String description = r.getString("description");
            System.out.format("%s\n", description);
            tbd.addRow(description);
        }
        
        return tbd;
    }
}
