package test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by noah-pena on 4/28/16.
 */
public class Sprite extends JPanel
{

    private BufferedImage image;
    private float width;
    private float height;

    private float x;
    private float y;

    public Sprite(BufferedImage image, float x, float y)
    {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.x = x;
        this.y = y;
    }

    public float getSpriteX()
    {
        return x;
    }

    public float getSpriteY()
    {
        return y;
    }

    public void setSpriteX(float x)
    {
        this.x = x;
    }

    public void setSpriteY(float y)
    {
        this.y = y;
    }

    public void setSpritePosition(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.drawImage(image, (int)x, (int)y, null);
    }
}
