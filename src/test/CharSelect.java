package test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ismael on 5/2/16.
 */
public class CharSelect extends JPanel {
    private final JButton[] buttons = new JButton[7]; //Character choice array
    private ArrayList<BufferedImage> imagePreview;

    public CharSelect() {
        //Code to fill the ArrayList with Preview Images
        BufferedImage playerStates;

        this.imagePreview = new ArrayList<>();

        try {
            int setX = 0;
            int setY = 0;

            playerStates = ImageIO.read(getClass().getResourceAsStream("/player/testSprites.png"));

            for (int x = setX; x < (playerStates.getWidth() / 32) + setX; x++)
                for (int y = setY; y < (playerStates.getHeight() / 32) + setY; y++)
                    this.imagePreview.add(playerStates.getSubimage(x * 32, y * 32, 32, 32));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Filling the Character Buttons
        buttons[0] = new JButton("Dovahkiin", new ImageIcon(imagePreview.get(8)));
        buttons[1] = new JButton("Salim", new ImageIcon(imagePreview.get(12)));
        buttons[2] = new JButton("Aladdin", new ImageIcon(imagePreview.get(32)));
        buttons[3] = new JButton("Gargamel", new ImageIcon(imagePreview.get(36)));
        buttons[4] = new JButton("Dogmeat", new ImageIcon(imagePreview.get(56)));
        buttons[5] = new JButton("Kanye", new ImageIcon(imagePreview.get(60)));
        buttons[6] = new JButton("Pablo Escobar", new ImageIcon(imagePreview.get(80)));

    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        //Adding all the Player choice Buttons.
        for (JButton button : buttons) {
            this.add(button);
        }
    }
}
