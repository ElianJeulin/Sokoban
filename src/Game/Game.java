/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;

import Board.Asset;
import Board.Board;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Manages all systems of the game.
 * @author ejeulin
 */
class Game {

    private final Board board;

    protected Game(Board b) {
        this.board = b;
    }

    /**
     * Plays a turn
     *
     * @throws IOException
     * @throws Game.GamePlayerLeaves
     */
    protected void oneTurn() throws IOException, GamePlayerLeaves {
        board.displayGrid();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String answer;
        boolean error = true;

        do {
            System.out.println(" CHOOSE YOUR MOVEMENT (L/R/U/D/quit)\n");
            answer = in.readLine();
            if (!"L".equals(answer) && !"R".equals(answer) && !"U".equals(answer) && !"D".equals(answer) && !"quit".equals(answer)) {
                System.out.println("This answer is invalid");
            } else {
                error = false;
            }
        } while (error);

        switch (answer) {
            case "U":
                playerMvt(board.getPlayerPosX(), board.getPlayerPosY(), 0, -1);
                break;
            case "D":
                playerMvt(board.getPlayerPosX(), board.getPlayerPosY(), 0, 1);
                break;
            case "L":
                playerMvt(board.getPlayerPosX(), board.getPlayerPosY(), -1, 0);
                break;
            case "R":
                playerMvt(board.getPlayerPosX(), board.getPlayerPosY(), 1, 0);
                break;
            case "quit":
                Player.playerLeft();
            default:
                break;
        }
    }

    /**
     * Moves the position of player according to a direction
     *
     * @param playerPosX the position of the player on the axe X
     * @param playerPosY the position of the player on the axe Y
     * @param dirX the direction on the axe X
     * @param dirY the direction on the axe Y
     */
    protected void playerMvt(int playerPosX, int playerPosY, int dirX, int dirY) {

        try {
            switch (board.getBoard()[playerPosY + dirY][playerPosX + dirX].getAsset()) {
                case WALL:
                    System.out.println("Incorrect direction.");
                    break;
                case BOX:
                    switch (board.getBoard()[playerPosY + (dirY * 2)][playerPosX + (dirX * 2)].getAsset()) {
                        case WALL:
                            System.out.println("You can't push this box in that direction.");
                            break;
                        case BOX:
                            System.out.println("You can't push this box in that direction.");
                            break;
                        case TARGET:
                            System.out.println("A cash register has hit a target.");
                        default:
                            board.addNothing(playerPosX, playerPosY);
                            int tmp = board.getTargetX().size();
                            for (int i = 0; i < tmp; i++) {
                                if (playerPosX == board.getTargetX().get(i) && playerPosY == board.getTargetY().get(i)) {
                                    board.addTarget(playerPosX, playerPosY);
                                }
                            }
                            board.setPosition(playerPosX + dirX, playerPosY + dirY);
                            board.addBox(playerPosX + (dirX * 2), playerPosY + (dirY * 2));
                            break;
                    }
                    break;
                default:
                    board.addNothing(playerPosX, playerPosY);
                    int tmp = board.getTargetX().size();
                    for (int i = 0; i < tmp; i++) {
                        if (playerPosX == board.getTargetX().get(i) && playerPosY == board.getTargetY().get(i)) {
                            board.addTarget(playerPosX, playerPosY);
                        }
                    }
                    board.setPosition(playerPosX + dirX, playerPosY + dirY);
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("You can't get off the board");
        }

    }

    /**
     * Returns true if the player win the game
     *
     * @return true if the player won
     */
    protected boolean winGame() {
        boolean win = true;
        int i = 0;

        while (i < board.getTargetX().size() && win) {
            if (board.getBoard()[board.getTargetY().get(i)][board.getTargetX().get(i)].getAsset() != Asset.BOX) {
                win = false;
            }
            i++;
        }

        return win;
    }

    /**
     * Prints the congratulation to the winner.
     */
    protected void congratulateWinner() {
        board.displayGrid();
        System.out.println("\nCongratulations, you passed this level !\n");
    }
}
