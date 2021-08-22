/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import File.TextBoardBuilder;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Contains all methods concerning database
 *
 * @author Ejeul
 */
class Database {

    private final Scanner entree = new Scanner(System.in);
    private final Connection c;

    protected Database(Connection c) {
        this.c = c;
    }

    /**
     * Lists all the board contained into the database.
     *
     * @throws SQLException
     */
    protected void listBoard() throws SQLException {
        Statement statement = c.createStatement();
        ResultSet resultats = statement.executeQuery("select * from BOARDS");
        System.out.format("%s\t%s\t%s\t%s\n", "ID", "Name", "Nb Rows", "Nb Col");

        while (resultats.next()) {
            String id = resultats.getString("board_id");
            String name = resultats.getString("name");
            int nbRow = resultats.getInt("nb_rows");
            int nbCol = resultats.getInt("nb_cols");
            System.out.format("%s\t%s\t%d\t%d\n", id, name, nbRow, nbCol);
        }
    }

    /**
     * Shows a board according to his ID
     *
     * @throws SQLException
     */
    protected void showBoard() throws SQLException {
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
        }
    }

    /**
     * Add a board into the database from a selected file.
     *
     * @throws SQLException
     * @throws IOException
     */
    protected void addBoardFromFile() throws SQLException, IOException {

        System.out.println("name of board ? ");
        String name = entree.nextLine();

        TextBoardBuilder tbd = new TextBoardBuilder();

        System.out.println("path of board ? ");
        String path = entree.nextLine();
        File file = new File(path);

        try ( Scanner scanner = new Scanner(file)) {
            //Add each line of the file
            while (scanner.hasNextLine()) {
                tbd.addRow(scanner.nextLine());
            }
        } catch (NoSuchElementException e) {
            System.out.println("This file is not a .txt");
            return;
        }

        System.out.println("ID of board ? ");
        String id = entree.nextLine();

        String sql = "insert into BOARDS values (?, ?, ?, ?)";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setInt(3, tbd.getIndex());
        ps.setInt(4, tbd.getNbRow());
        ps.executeUpdate();

        sql = "insert into ROWS (board_id, description) values (?, ?)";
        PreparedStatement ps1 = c.prepareStatement(sql);
        for (int i = 0; i < tbd.getBoard().size(); i++) {
            ps1.setString(1, id);
            ps1.setString(2, tbd.getBoard().get(i));
            ps1.executeUpdate();
        }

        System.out.println("Add board " + id + " into the database.");
    }

    /**
     * Removes a board from the database
     *
     * @throws SQLException
     */
    protected void removeBoard() throws SQLException {
        System.out.println("ID of board ? ");
        String id = entree.nextLine();

        //Delete from Board
        String sql = "delete from BOARDS where board_id = ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, id);
        ps.executeUpdate();

        //Delete from Rows
        sql = "delete from ROWS where board_id = ?";
        ps = c.prepareStatement(sql);
        ps.setString(1, id);
        ps.executeUpdate();

        System.out.println("Remove the board " + id + " with success");
    }

    /**
     * Create table Board and Rows if they don't exist
     */
    protected void createTable() {

        //Creates table Board
        String createBoard = "CREATE TABLE IF NOT EXISTS \"BOARDS\" (\n"
                + "\"board_id\"    TEXT NOT NULL UNIQUE,\n"
                + "\"name\"        TEXT, \n"
                + "\"nb_rows\"     INTEGER NOT NULL, \n"
                + "\"nb_cols\"     INTEGER NOT NULL, \n"
                + "PRIMARY KEY (\"board_id\")\n" + ")";

        //Creates table Rows
        String createRows = "CREATE TABLE IF NOT EXISTS \"ROWS\" (\n"
                + "\"board_id\"    TEXT NOT NULL,\n"
                + "\"row_num\"     INTEGER AUTO_INCREMENT, \n"
                + "\"description\" TEXT, \n"
                + "PRIMARY KEY(\"row_num\"), \n"
                + "FOREIGN KEY(\"board_id\") REFERENCES \"BOARDS\"(\"board_id\")\n" + ")";

        String path = "data\\test.sqlite3";
        String URL = "jdbc:sqlite:" + path;
        Administrator.loadDriverSQLite();
        try ( Connection connexion = DriverManager.getConnection(URL)) {
            Statement s = connexion.createStatement();
            s.executeUpdate(createBoard);
            s.executeUpdate(createRows);
            s.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        System.out.println("Database created with success.");
    }
}
