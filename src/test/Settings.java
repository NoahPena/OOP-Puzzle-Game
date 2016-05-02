package test;

/**
 * Created by Saphix on 5/1/2016.
 */
public class Settings {
    private static final int frameWidth = 1600;
    private static final int frameHeight = 896;
    private static int playerSelection;
    private static int windowWidth;
    private static int windowHeight;

    protected static boolean gameOver = false;

    public static int getFrameWidth() {
        return frameWidth;
    }

    public static int getFrameHeight() {
        return frameHeight;
    }

    public static int getPlayerSelection() {
        return playerSelection;
    }

    public static void setPlayerSelection(int playerSelection) {
        Settings.playerSelection = playerSelection;
    }

    public static int getWindowWidth() {
        return windowWidth;
    }

    public static void setWindowWidth(int windowWidth) {
        Settings.windowWidth = windowWidth;
    }

    public static int getWindowHeight() {
        return windowHeight;
    }

    public static void setWindowHeight(int windowHeight) {
        Settings.windowHeight = windowHeight;
    }

    public static void setGameOver(boolean b){Settings.gameOver = b;}

    public static boolean getGameOver(){return gameOver;}
}
