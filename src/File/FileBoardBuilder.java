/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import Board.Board;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Permits to create a board with a file.
 * @author Ejeul
 */
public class FileBoardBuilder extends BoardBuilder {

    /**
     * Reads a file and construct a Board according to the contents
     *
     * @param path the path where the file of the board is
     * @return a Board
     * @throws IOException
     */
    public Board readFile(String path) throws IOException {
        TextBoardBuilder tbd = new TextBoardBuilder();
        File file = new File(path);

        try ( Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                tbd.addRow(scanner.nextLine());
            }
            Board board = makesGrid(tbd);
            return board;
        }
    }

    /**
     * Creates a File to allow the player to create his own level.
     * @throws IOException
     */
    public void createFile() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean quit = false;
        boolean error = true;
        int width = 0;

        TextBoardBuilder builder = new TextBoardBuilder();

        do {
            System.out.println("Please enter the width of the board");
            String w = in.readLine();
            try {
                width = Integer.parseInt(w);
                if (width > 0) {
                    error = false;
                } else {
                    System.out.println("The number cannot be negative.");
                }         
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        } while (error);

        do {
            System.out.println("Please enter the line of your level and enter quit when your level is finished");
            String line = in.readLine();
            if (line.length() == width || line.equals("quit")) {
                if (!line.equals("quit")) {
                    builder.addRow(line);
                } else {
                    quit = true;
                }
            } else {
                System.out.println("you have entered a bad entry");
            }
        } while (!quit);

        //Creates file
        try ( FileOutputStream fos = new FileOutputStream("MyLevel.txt")) {
            for (int i = 0; i < builder.getBoard().size(); i++) {
                fos.write(builder.getBoard().get(i).getBytes());
                fos.write("\n".getBytes());
                fos.flush();
            }
        }
    }
}
