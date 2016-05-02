package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by ismael on 5/1/16.
 */
public class TitleScreen extends JPanel
{

        private BufferedImage titleScreen;

        private final JButton startButton; // TitleScreen Buttons
        private final JButton endButton;

        private final int xCor = 0; //Coordinates for printing on screen
        private final int yCor = 0;
    
        protected static boolean pressed; //Flag for engaging the charselect in paintcomponent
        protected static boolean charSelect;

        private long lastLoopTime = System.nanoTime();

        private final int TARGET_FPS = 60;
        private final long BEST_TIME = 1000000000 / TARGET_FPS;


        public TitleScreen(Dimension d)
        {
            this.setPreferredSize(d);

            titleScreen = new BufferedImage((int)d.getWidth(), (int)d.getHeight(), BufferedImage.TYPE_INT_RGB);

            pressed = false; //Flag for the
            charSelect = false;

            startButton = new JButton("Start Game");
            endButton = new JButton("Quit");

            StartHandler startHand = new StartHandler();
            startButton.addActionListener(startHand);

            EndHandler endHand = new EndHandler();
            endButton.addActionListener(endHand);

            setBackground(Color.BLACK);
            try
            {
                BufferedImage temp = ImageIO.read(getClass().getResourceAsStream("/title/Placeholder.png"));
                titleScreen.getGraphics().drawImage(temp, titleScreen.getWidth()/2 - temp.getWidth()/2, titleScreen.getHeight()/2 - temp.getHeight()/2, temp.getWidth(), temp.getHeight(), null);
            }
            catch (IOException e)
            {
                System.out.print("Image definitely didn't load");
            }

            this.add(startButton);
            this.add(endButton);

        }

        @Override
        public void paintComponent(Graphics g)
        {
                super.paintComponent(g);
                g.drawImage(titleScreen, xCor, yCor, null);
        }

        public void draw()
        {
            this.getGraphics().drawImage(this.titleScreen, 0, 0, null);
        }

        public boolean run()
        {
            while(true)
            {
                long now = System.nanoTime();
                long updateTime = now - lastLoopTime;
                lastLoopTime = now;
                float delta = updateTime / BEST_TIME;

                if(pressed)
                    if(charSelect)
                        return true;
                    else
                        return false;
                try
                {
                    Thread.sleep((lastLoopTime - System.nanoTime() + BEST_TIME) / 1000000);
                }
                catch(Exception e)
                {

                }
            }

//            while(pressed == false)
//            {
//                if(pressed == true)
//                    break;
//
//            }
//            if(charSelect == true)
//            {
//                return true;
//            }
//            else
//            {
//                return false;
//            }
        }


}



    class StartHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.println(TitleScreen.pressed);
            System.out.println("Here2");
            TitleScreen.charSelect = true;
            TitleScreen.pressed = true;
            System.out.println(TitleScreen.pressed);
        }
    }

    class EndHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Settings.gameOver = true;
        }
    }





