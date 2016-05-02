package test.entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Saphix on 5/2/2016.
 */
public class ImmovableRock extends Entity
{
    private ArrayList<BufferedImage> imageStates;

    public ImmovableRock()
    {
        BufferedImage rock;
        this.imageStates =  new ArrayList<>();

        try
        {
            rock = ImageIO.read(getClass().getResourceAsStream("/objects/rock.png"));


            for (int x = 0; x < (rock.getWidth()/32); x++)
                for (int y = 0; y < (rock.getHeight()/32); y++)
                    this.imageStates.add(rock.getSubimage(x*32, y*32, 32, 32));

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

    public void move(String direction)
    {
        switch (direction)
        {
            case "North":
                this.setY(this.getY() - 32);
                break;
            case "South":
                this.setY(this.getY() + 32);
                break;
            case "East":
                this.setX(this.getX() + 32);
                break;
            case "West":
                this.setX(this.getX() - 32);
                break;
        }
    }

    public void update(ArrayList<Entity> list)
    {

    }

    public void draw(Graphics g)
    {
        if(this.getImg() != null)
        {
            g.drawImage(this.getImg(), (int)this.getX(), (int)this.getY(), this.getImg().getWidth(), this.getImg().getHeight(), null);
        }
    }


}
