package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ismael on 5/1/16.
 */
public class TitleScreen extends JPanel
{

        private BufferedImage titleScreen;

        private final JButton startButton; // TitleScreen Buttons
        private final JButton endButton;

        private final JButton[] buttons = new JButton[7]; //Character choice array

        private final int xCor = 0; //Coordinates for printing on screen
        private final int yCor = 0;
    
        private boolean charSelect; //Flag for engaging the charselect in paintcomponent

        private ArrayList<BufferedImage> imagePreview;


        public TitleScreen()
        {

            //Code to fill the ArrayList with Preview Images
            BufferedImage playerStates;

            this.imagePreview =  new ArrayList<>();

            try
            {
                int setX = 0;
                int setY = 0;

                playerStates = ImageIO.read(getClass().getResourceAsStream("/player/testSprites.png"));

                for (int x = setX; x < (playerStates.getWidth()/32) + setX; x++)
                    for (int y = setY; y < (playerStates.getHeight()/32) + setY; y++)
                        this.imagePreview.add(playerStates.getSubimage(x*32, y*32, 32, 32));

            } catch (IOException e)
            {
                e.printStackTrace();
            }


            charSelect = false; //Flag for the

            //Filling the Character Buttons
            buttons[0] = new JButton("Dovahkiin", new ImageIcon(imagePreview.get(8)));
            buttons[1] = new JButton("Salim", new ImageIcon(imagePreview.get(12)));
            buttons[2] = new JButton("Aladdin", new ImageIcon(imagePreview.get(32)));
            buttons[3] = new JButton("Gargamel", new ImageIcon(imagePreview.get(36)));
            buttons[4] = new JButton("Dogmeat", new ImageIcon(imagePreview.get(56)));
            buttons[5] = new JButton("Kanye", new ImageIcon(imagePreview.get(60)));
            buttons[6] = new JButton("Pablo Escobar", new ImageIcon(imagePreview.get(80)));


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

            repaint();
            while(true)
            {
                if(charSelect == true || Settings.gameOver == true)
                {
                    break;
                }
            }

            repaint();

        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            if(charSelect == false)
            {
                g.drawImage(titleScreen, xCor, yCor, null);
                this.add(startButton);
                this.add(endButton);
            }
            else {
                //Adding all the Player choice Buttons.
                for (JButton button : buttons) {
                    this.add(button);
                }
            }
        }

    class StartHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            charSelect = true;
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

    class ChoiceHandler implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.print(e.getSource());
        }
    }


}


