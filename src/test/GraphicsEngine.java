package test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by noah-pena on 4/28/16.
 */
public class GraphicsEngine extends JFrame
{
    private JPanel jPanel;

    public GraphicsEngine(Map map)
    {
        super();
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(800, 600));
        map.setPreferredSize(new Dimension(800, 600));
        System.out.println(jPanel.getPreferredSize().toString());
        System.out.println(map.getPreferredSize().toString());
        jPanel.add(map);
        this.add(jPanel);

        this.setVisible(true);
    }

    public void draw(Sprite sprite, float x, float y)
    {
        
    }
}
