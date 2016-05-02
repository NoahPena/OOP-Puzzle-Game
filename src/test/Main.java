package test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by npena9 on 4/13/2016.
 */
public class Main
{
    public static boolean DEBUG = false;
    private static JFrame frame;

    public static void main(String[] args)
    {
        frame = new JFrame();

        frame.setName("OOP Game");

        //SetSize of frame and record size of content pane for later use
        frame.setSize(Settings.getFrameWidth(), Settings.getFrameHeight());
        frame.getContentPane().setPreferredSize(new Dimension(Settings.getFrameWidth(), Settings.getFrameHeight()));
        frame.pack();

        Settings.setWindowHeight((int)frame.getContentPane().getSize().getHeight());
        System.out.println("Window W: " + Settings.getWindowWidth());
        System.out.println("Window H: " + Settings.getWindowHeight());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //while(Settings.gameOver != false)
        // {
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        TitleScreen title = new TitleScreen(new Dimension(900, 600));
        frame.setContentPane(title);
        frame.setVisible(true);
        title.repaint();
        if(title.run())
            frame.getContentPane().removeAll();
        else
            System.exit(0);
        //if run returns true continue if run returns false exit game

        CharSelect select = new CharSelect();
        frame.getContentPane().add(select);
        frame.getContentPane().validate();
        frame.getContentPane().repaint();

        if(select.run()) {
            frame.removeAll();
            frame.setVisible(false);
        }

        frame = new JFrame();

        frame.setName("OOP Game");
        frame.setSize(Settings.getFrameWidth(), Settings.getFrameHeight());
        frame.getContentPane().setPreferredSize(new Dimension(Settings.getFrameWidth(), Settings.getFrameHeight()));
        frame.setResizable(false);
        //frame.setLocationRelativeTo(null);

        LevelOne levelOne = new LevelOne();
        levelOne.execute();

        frame.getContentPane().add(levelOne.getMap());

        frame.setVisible(true);
        frame.setFocusable(true);

        levelOne.setKeyListener(frame);

        //  }


    }
}
