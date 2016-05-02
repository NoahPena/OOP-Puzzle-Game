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


        public TitleScreen(Dimension d)
        {
            this.setPreferredSize(d);

            titleScreen = new BufferedImage(Settings.getWindowWidth(), Settings.getWindowHeight(), BufferedImage.TYPE_INT_RGB);

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
                titleScreen = ImageIO.read(getClass().getResourceAsStream("/title/Placeholder.png"));
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
            while(pressed == false)
            {
                if(pressed == true)
                    break;

            }
            if(charSelect == true)
            {
                return true;
            }
            else
            {
                return false;
            }
        }


}



    class StartHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.out.print(TitleScreen.charSelect);
            System.out.println("Here2");
            TitleScreen.charSelect = true;
            TitleScreen.pressed = true;
            System.out.print(TitleScreen.charSelect);
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





