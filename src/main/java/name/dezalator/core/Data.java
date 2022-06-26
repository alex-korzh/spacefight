package name.dezalator.core;

import name.dezalator.model.ship.Destroyer;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;

import java.util.ArrayList;

public class Data {
    static Player player1;
    static Player player2;
    static Player currentPlayer;
    static int turn;

    public static void init() {
        ArrayList<SpaceShip> array1 = new ArrayList<>();
        ArrayList<SpaceShip> array2 = new ArrayList<>();

        array1.add(new Destroyer(
                "D1",
                new Coordinates(500, 1100),
                2,
                new ArrayList<>(),
                new ArrayList<>()
        ));

        array2.add(new Destroyer(
                "D2",
                new Coordinates(500, 0),
                2,
                new ArrayList<>(),
                new ArrayList<>()
        ));


        player1 = new Player("Player1", array1);
        player2 = new Player("Player2", array2);
        turn = 1;
        currentPlayer = player1;
    }

    public static Player getPlayer1() {
        return player1;
    }

    public static Player getPlayer2() {
        return player2;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static int getTurn() {
        return turn;
    }

    public static boolean isCurrentPlayer(Player player) {
        return currentPlayer.equals(player);
    }

    public static void nextTurn() {
        turn += 1;
    }

    public static void switchCurrentPlayer() {
        currentPlayer = currentPlayer == player1 ? player2 : player1;
    }

    public static ArrayList<SpaceShip> getCurrentPlayerShips() {
        return currentPlayer.getShips();
    }

    public static String getCurrentPlayerName() {
        return currentPlayer.getName();
    }
}
