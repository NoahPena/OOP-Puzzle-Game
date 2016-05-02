package test.entity;

import test.Settings;

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
    private boolean collison;
    private boolean collisonW;
    private boolean collisonA;
    private boolean collisonS;
    private boolean collisonD;

    public Rock()
    {
        BufferedImage rock;

        this.pickedUp = false;
        this.imageStates =  new ArrayList<>();
        this.collison = false;
        this.collisonW = false;
        this.collisonA = false;
        this.collisonS = false;
        this.collisonD = false;

        try
        {
            rock = ImageIO.read(getClass().getResourceAsStream("/objects/hardrock.png"));


            for (int x = 0; x < (rock.getWidth()/32); x++)
                for (int y = 0; y < (rock.getHeight()/32); y++)
                    this.imageStates.add(rock.getSubimage(x*32, y*32, 32, 32));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        this.setImg(this.imageStates.get(8));

        //Set staring location
        this.setX(150);
        this.setY(150);

        //Set Velocity to 0
        this.setVelX(0);
        this.setVelY(0);
    }

    public void update(ArrayList<Entity> list)
    {

        if(pickedUp)
        {
            this.setVelX(Player.getInstance().getVelX());
            this.setVelY(Player.getInstance().getVelY());

            Rectangle nextMove = this.getBounds();
            nextMove.setLocation((int) (nextMove.x + this.getVelX()), (int) (nextMove.y + this.getVelY()));

            if(Player.getInstance().iswPressed())
            {
                collisonS = false;
                if (!(nextMove.getY() > 0 && !collison(list)))
                {
                    this.setVelY(0);
                    collisonW = true;
                }
            }
            if(Player.getInstance().issPressed())
            {
                collisonW = false;
                if(!(nextMove.getY() + this.getImg().getHeight() < Settings.getWindowHeight() && !collison(list)))
                {
                    this.setVelY(0);
                    this.setCollison(true);
                    collisonS = true;
                }
            }
            if(Player.getInstance().isaPressed())
            {
                collisonD = false;
                if(!(nextMove.getX() > 0 && !collison(list)))
                {
                    this.setVelX(0);
                    collisonA = true;
                }
            }
            if(Player.getInstance().isdPressed())
            {
                collisonA = false;
                if(!(nextMove.getX() + this.getImg().getWidth() < Settings.getWindowWidth() && !collison(list)))
                {
                    this.setVelX(0);
                    collisonD = true;
                }
            }

            System.out.println("W: " + collisonW);
            System.out.println("A: " + collisonA);
            System.out.println("S: " + collisonS);
            System.out.println("D: " + collisonD);
            if(collisonW || collisonA || collisonS || collisonD)
                this.setCollison(true);
            else
                this.setCollison(false);

            this.setX(this.getX() + this.getVelX()); //Move on the x axis
            this.setY(this.getY() + this.getVelY()); //Move on the y axis
        }
    }

    public boolean collison(ArrayList<Entity> list)
    {
        Rectangle nextMove = this.getBounds();
        nextMove.setLocation((int) (nextMove.x + Player.getInstance().getVelX()), (int) (nextMove.y + Player.getInstance().getVelY()));

        if(!this.getMap().testCollision(nextMove))
            return true;

        for (Entity e: list) {
            //check collison
            if (e != this) {
                if (nextMove.intersects(e.getBounds())) {
                    return true;
                }
            }
        }

        return false;
    }

    public void draw(Graphics g)
    {
        if(this.getImg() != null)
        {
            g.drawImage(this.getImg(), (int)this.getX(), (int)this.getY(), this.getImg().getWidth(), this.getImg().getHeight(), null);
        }
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isCollison() {
        return collison;
    }

    public void setCollison(boolean collison) {
        this.collison = collison;
    }
}
