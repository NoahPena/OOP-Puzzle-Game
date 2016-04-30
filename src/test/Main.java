package test;

/**
 * Created by npena9 on 4/13/2016.
 */
public class Main
{
    public static void main(String[] args)
    {
        Map map = new Map(".//assets//maps//basicMap", "basicBitchMap.tmx", 0, 0);

        GraphicsEngine graphics = new GraphicsEngine(map);

        //LevelOne levelOne = new LevelOne();
        //levelOne.execute();
    }
}
