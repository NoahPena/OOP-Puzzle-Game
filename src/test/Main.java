package test;

/**
 * Created by npena9 on 4/13/2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        //GraphicsEngine graphics = new GraphicsEngine();

        Map map = new Map("//home//noah-pena//School//OOP//OOP-Puzzle-Game//assets//maps//basicMap//basicBitchMap.tmx");

        LevelOne levelOne = new LevelOne();
        levelOne.execute();
    }
}
