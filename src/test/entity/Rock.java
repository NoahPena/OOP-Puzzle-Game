package test.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Saphix on 5/1/2016.
 */
public class Rock extends Entity
{
    private ArrayList<BufferedImage> imageStates;
    private boolean pickedUp;

    public Rock()
    {
        BufferedImage playerStates;

        this.pickedUp = false;
        this.imageStates =  new ArrayList<>();

        try
        {
            playerStates = ImageIO.read(getClass().getResourceAsStream("/objects/rock.png"));


            for (int x = 0; x < (playerStates.getWidth()/32); x++)
                for (int y = 0; y < (playerStates.getHeight()/32); y++)
                    this.imageStates.add(playerStates.getSubimage(x*32, y*32, 32, 32));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        this.setImg(this.imageStates.get(2));

        //Set staring location
        this.setX(150);
        this.setY(150);

        //Set Velocity to 0
        this.setVelX(0);
        this.setVelY(0);
    }

    public void update()
    {
        if(pickedUp)
        {

        }
    }

    public void draw(Graphics g)
    {
        if(this.getImg() != null)
        {
            g.drawImage(this.getImg(), (int)this.getX(), (int)this.getY(), this.getImg().getWidth(), this.getImg().getHeight(), null);
        }
    }

}
