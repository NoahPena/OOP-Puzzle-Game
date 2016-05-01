package test;

import javax.swing.*;
import java.awt.*;

/**
 * Created by noah-pena on 4/28/16.
 */
public class GraphicsEngine extends JFrame
{
    private JPanel jPanel;
    private int sizeX;
    private int sizeY;

    public GraphicsEngine(Map map, int sizeX, int sizeY)
    {
        super();

        setIgnoreRepaint(true);

        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.setSize(sizeX, sizeY);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(map);

//        jPanel = new JPanel();
//        jPanel.setPreferredSize(new Dimension(sizeX, sizeY));
//        jPanel.add(map);
//        this.add(jPanel);

        this.setVisible(true);
    }
}
