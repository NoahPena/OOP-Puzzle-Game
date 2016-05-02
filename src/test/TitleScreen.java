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
    
        protected static boolean charSelect = false; //Flag for engaging the charselect in paintcomponent


        public TitleScreen(Dimension d)
        {
            this.setPreferredSize(d);

            titleScreen = new BufferedImage(Settings.getWindowWidth(), Settings.getWindowHeight(), BufferedImage.TYPE_INT_RGB);

            charSelect = false; //Flag for the

            startButton = new JButton("Start Game");
            endButton = new JButton("Quit");

            StartHandler startHand = new StartHandler();
            startButton.addActionListener(startHand);

            EndHandler endHand = new EndHandler();
            endButton.addActionListener(endHand);

            setBackground(Color.BLACK);
            try
            {
                titleScreen = ImageIO.read(getClass().getResourceAsStream("/title/Placeholder.png"));
            }
            catch (IOException e)
            {
                System.out.print("Image definitely didn't load");
            }

            this.add(startButton);
            this.add(endButton);
//
//            while(true)
//            {
//                if(charSelect == true || Settings.gameOver == true)
//                {
//                    break;
//
//                }
//            }



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
}



    class StartHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            TitleScreen.charSelect = true;
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





