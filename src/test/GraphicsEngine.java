package test;

import com.sun.imageio.plugins.jpeg.JPEGImageMetadataFormatResources;

import javax.swing.*;

/**
 * Created by noah-pena on 4/28/16.
 */
public class GraphicsEngine extends JFrame
{
    private JPanel jPanel;

    public GraphicsEngine()
    {
        super();
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanel = new JPanel();
        this.add(jPanel);

        this.setVisible(true);
    }

    public void draw(Sprite sprite, float x, float y)
    {
        
    }
}
