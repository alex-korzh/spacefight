package name.dezalator.core;

import name.dezalator.core.util.Event;
import name.dezalator.ui.GameFrame;

import java.util.ArrayList;

public class Engine {
    static Player player1;
    static Player player2;
    static Player currentPlayer;
    static int turn;
    static GameFrame gameFrame;

    public Engine() {
        player1 = new Player("Player1", new ArrayList<>());
        player2 = new Player("Player2", new ArrayList<>());
        turn = 1;
        currentPlayer = player1;
    }

    public void startGame() {
        gameFrame = new GameFrame();
        gameFrame.gamePanel.updatePlayerName(player1.getName());
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
                gameFrame.gamePanel.updatePlayerName(currentPlayer.getName());
            }
        }
    }
}
