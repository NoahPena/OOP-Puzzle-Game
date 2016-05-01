package test;

import javax.swing.*;

/**
 * Created by npena9 on 4/13/2016.
 */
public class Main
{
    public static boolean DEBUG = true;
    private static JFrame frame;

    public static void main(String[] args)
    {
        frame = new JFrame();

        frame.setName("OPP Game");


        LevelOne levelOne = new LevelOne();
        levelOne.execute();
        levelOne.setKeyListener(frame);

        frame.setSize(800, 600);
        frame.setContentPane(levelOne.getMap());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();

        frame.setVisible(true);
    }
}
