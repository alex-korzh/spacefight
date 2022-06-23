package name.dezalator.core;

import name.dezalator.core.util.Event;
import name.dezalator.model.ship.Destroyer;
import name.dezalator.model.ship.base.SpaceShip;
import name.dezalator.model.util.Coordinates;
import name.dezalator.ui.GameFrame;

import java.util.ArrayList;

public class Engine {
    static Player player1;
    static Player player2;
    static Player currentPlayer;
    static int turn;
    static GameFrame gameFrame;

    public Engine() {
        ArrayList<SpaceShip> array1 = new ArrayList<>();
        ArrayList<SpaceShip> array2 = new ArrayList<>();

        array1.add(new Destroyer(
                "D1",
                new Coordinates(10, 22),
                2,
                new ArrayList<>(),
                new ArrayList<>()
        ));

        array2.add(new Destroyer(
                "D2",
                new Coordinates(10, 0),
                2,
                new ArrayList<>(),
                new ArrayList<>()
        ));


        player1 = new Player("Player1", array1);
        player2 = new Player("Player2", array2);
        turn = 1;
        currentPlayer = player1;
    }

    public void startGame() {
        gameFrame = new GameFrame();
        gameFrame.gamePanel.providePlayers(player1, player2);
        gameFrame.gamePanel.updateCurrentPlayer(player1.getName());
        gameFrame.gamePanel.updateTurn(turn);
    }

    public static void notifyGame(Event event) {
        switch (event) {
            case END_TURN -> {
                if (currentPlayer == player2) {
                    turn += 1;
                    gameFrame.gamePanel.updateTurn(turn);
                }
                currentPlayer = currentPlayer == player1 ? player2 : player1;
                gameFrame.gamePanel.updateCurrentPlayer(currentPlayer.getName());
            }
        }
    }
}
