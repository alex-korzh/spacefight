package name.dezalator.core;

import name.dezalator.core.util.Event;
import name.dezalator.ui.GameFrame;


public class Engine {
    static GameFrame gameFrame;

    public Engine() {
        Data.init();
    }

    public void startGame() {
        gameFrame = new GameFrame();
    }

    public static void notifyGame(Event event) {
        switch (event) {
            case END_TURN -> {
                if (Data.isCurrentPlayer(Data.getPlayer2())) {
                    Data.nextTurn();
                }
                Data.switchCurrentPlayer();
            }
        }
    }
}
