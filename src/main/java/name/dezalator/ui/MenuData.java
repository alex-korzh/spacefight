package name.dezalator.ui;

public class MenuData {
    static final String START_GAME = "Start game";
    static final String EXIT = "Exit";
    String[] messages = { START_GAME, EXIT };
    int menuWidth;
    int internalStep;
    int buttonWidth;
    int menuHeightPoint;
    int buttonHeight;
    int menuHeight;

    public MenuData(int screenWidth, int screenHeight) {
        menuWidth = screenWidth/3;
        menuHeightPoint = screenHeight/4;
        buttonHeight = screenHeight/24;
        internalStep = buttonHeight/3;
        buttonWidth = menuWidth - 2*internalStep;
        menuHeight = buttonHeight*messages.length + internalStep*(messages.length+1);
    }
}
