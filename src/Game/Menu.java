/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Regroups the same methods for the two mains.
 * @author Ejeul
 */
public class Menu {

    /**
     * Permits to quit the game.
     *
     * @throws GamePlayerLeaves when the player leaves
     */
    protected static void playerLeft() throws GamePlayerLeaves {
        throw new GamePlayerLeaves("Player left the game");
    }

    /**
     * Permits to read a int when the player entry a word.
     *
     * @param in the line of command
     * @return an integer
     * @throws IOException line of command exeption
     * @throws NumberFormatException when the player entry a number exeption
     */
    private static int readInt(BufferedReader in) throws IOException, NumberFormatException {
        return Integer.parseInt(in.readLine());
    }

    /**
     * Permits to choose a game mode
     * @param min the minimum number which can be enter
     * @param max the maximum number which can be enter
     * @return the number choosen
     */
    protected static int chooseMenu(int min, int max) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        int menu = 0;
        
        do {
            try {
                menu = readInt(in);
                if (menu < min || menu > max) {
                    System.out.println("This number is invalid, enter a new number please.");
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("This is not a number, enter a valid number please.");
            }
        } while (menu < min || menu > max);
        
        return menu;
    }
}
