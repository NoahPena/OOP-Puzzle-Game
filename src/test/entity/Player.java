package test.entity;

import test.Map;
import test.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by noah-pena on 4/28/16.
 */
public class Player extends Entity
{
	private ArrayList<BufferedImage> imageStates;

	private boolean wPressed;
	private boolean aPressed;
	private boolean sPressed;
	private boolean dPressed;
    private boolean pickup;
    private Rock currentRock;

	private static Player instance;

	private Player()
	{
		BufferedImage playerStates;

		this.imageStates =  new ArrayList<>();

		try
		{
            int setX = 0;
            int setY = 0;
            Point temp;
			playerStates = ImageIO.read(getClass().getResourceAsStream("/player/playerSprites.png"));

            temp = this.playerSelection(setX, setY);

            setX = temp.x;
            setY = temp.y;
            
			for (int x = setX; x < (playerStates.getWidth()/32)/4 + setX; x++)
				for (int y = setY; y < (playerStates.getHeight()/32)/2 + setY; y++)
					this.imageStates.add(playerStates.getSubimage(x*32, y*32, 32, 32));

		} catch (IOException e)
		{
			e.printStackTrace();
		}

		this.setImg(this.imageStates.get(0));

		//Set staring location
		this.setX(100);
		this.setY(100);

		//Set Velocity to 0
		this.setVelX(0);
		this.setVelY(0);

		//Set no movement direction
		this.setwPressed(false);
		this.setaPressed(false);
		this.setsPressed(false);
		this.setdPressed(false);
	}

	public static Player getInstance() {
		if(instance == null)
			instance = new Player();
		return instance;
	}

//	public static void resetInstance(){
//		instance = new Player();
//	}

    private Point playerSelection(int x, int y)
    {
        Point temp = new Point(0,0);

        switch (Settings.getPlayerSelection())
        {
            case 1:
                temp.x = 3;
                break;

            case 2:
                temp.x = 6;
                break;

            case 3:
                temp.x = 9;
                break;

            case 4:
                temp.y = 4;
                break;

            case 5:
                temp.x = 3;
                temp.y = 4;
                break;

            case 6:
                temp.x = 6;
                temp.y = 4;
                break;

            default:
                //Default character
        }

        return temp;
    }

	public void update(ArrayList<Entity> list)
	{
		//Check movement
		if(wPressed && !sPressed)						//Up
		{
            this.setVelY(-1);
            if (!(this.getY() > 0 && !collison(list)))
			    this.setVelY(0);

			this.setImg((this.imageStates.get(3)));
		}
		else if(sPressed && !wPressed)					//Down
		{
            this.setVelY(1);
            if(!(this.getY() + this.getImg().getHeight() < Settings.getWindowHeight() && !collison(list)))
			    this.setVelY(0);

			this.setImg((this.imageStates.get(0)));
		}
		else											//No Vertical movements
		{
			this.setVelY(0);
		}

		if(aPressed && !dPressed)						//Left
		{
            this.setVelX(-1);
            if(!(this.getX() > 0 && !collison(list)))
		 	    this.setVelX(0);

		 	this.setImg((this.imageStates.get(1)));
		}
		else if(dPressed && !aPressed)					//Right
		{
            this.setVelX(1);
            if(!(this.getX() + this.getImg().getWidth() < Settings.getWindowWidth() && !collison(list)))
			    this.setVelX(0);

			this.setImg((this.imageStates.get(2)));
		}
		else											//No Horizontal movements
		{
			this.setVelX(0);
		}

		this.setX(this.getX() + this.getVelX()); //Move on the x axis
		this.setY(this.getY() + this.getVelY()); //Move on the y axis
	}

	public boolean collison(ArrayList<Entity> list)
	{
        Rectangle nextMove = this.getBounds();
        nextMove.setLocation((int) (nextMove.x + this.getVelX()), (int) (nextMove.y + this.getVelY()));

        if(!this.getMap().testCollision(nextMove))
            return true;

		for (Entity e: list)
		{
            //check collison
            if(e != this.currentRock)
            {
                if (nextMove.intersects(e.getBounds())) {
                    return true;
                }
            }

            if(e == this.currentRock && this.currentRock.isCollison())
            {
                if (nextMove.intersects(e.getBounds())) {
                    return true;
                }
            }

            //check rock nearby
            if(this.isPickup() && e instanceof Rock)
            {
                Rectangle biggerPlayer = this.getBounds();
                biggerPlayer.setBounds((int)biggerPlayer.getX() - 1, (int)biggerPlayer.getY() - 1, (int)biggerPlayer.getWidth() + 2, (int)biggerPlayer.getHeight() + 2);
                if(biggerPlayer.intersects(e.getBounds()))
                {
                    this.currentRock = (Rock) e;
                    this.currentRock.setPickedUp(true);
                }
                else
                    this.setPickup(false);
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

	public void keyPressed(KeyEvent e){
		//Translates key press
		switch(Character.toUpperCase(e.getKeyChar())){
			case 'W':
				setwPressed(true);
				break;
			case 'A':
				setaPressed(true);
				break;
			case 'S':
				setsPressed(true);
				break;
			case 'D':
				setdPressed(true);
				break;
            default:
                //nothing
		}
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            this.setPickup(!this.isPickup());

            if(!this.isPickup())
            {
                this.currentRock.setPickedUp(false);
                this.currentRock = null;
            }
        }
	}

	public void keyReleased(KeyEvent e){
		//Translates key release
		switch(Character.toUpperCase(e.getKeyChar())){
			case 'W':
				setwPressed(false);
				break;
			case 'A':
				setaPressed(false);
				break;
			case 'S':
				setsPressed(false);
				break;
			case 'D':
				setdPressed(false);
				break;
		}
	}

	public void setDirection()
	{

	}

	public boolean iswPressed() {
		return wPressed;
	}

	public void setwPressed(boolean wPressed) {
		this.wPressed = wPressed;
	}

	public boolean isaPressed() {
		return aPressed;
	}

	public void setaPressed(boolean aPressed) {
		this.aPressed = aPressed;
	}

	public boolean issPressed() {
		return sPressed;
	}

	public void setsPressed(boolean sPressed) {
		this.sPressed = sPressed;
	}

	public boolean isdPressed() {
		return dPressed;
	}

	public void setdPressed(boolean dPressed) {
		this.dPressed = dPressed;
	}

    public boolean isPickup() {
        return pickup;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }
}
