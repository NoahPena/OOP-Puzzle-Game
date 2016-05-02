package test;

import javax.swing.*;
import java.awt.*;

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
        frame.setIgnoreRepaint(true);

        //SetSize of frame and record size of content pane for later use
        frame.setSize(Settings.getFrameWidth(), Settings.getFrameHeight());
        frame.getContentPane().setPreferredSize(new Dimension(Settings.getFrameWidth(), Settings.getFrameHeight()));
        frame.pack();
        Settings.setWindowWidth((int)frame.getContentPane().getSize().getWidth());
        Settings.setWindowHeight((int)frame.getContentPane().getSize().getHeight());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
        //while(Settings.gameOver != false)
       // {
            TitleScreen title = new TitleScreen();
            title.setSize(Settings.getFrameWidth(), Settings.getFrameHeight());

            frame.setContentPane(title);

            //Set Character
            Settings.setPlayerSelection(5);

            //Set up Level
            LevelOne levelOne = new LevelOne();
            levelOne.execute();
            levelOne.setKeyListener(frame);

            frame.setContentPane(levelOne.getMap());
      //  }


    }
}
