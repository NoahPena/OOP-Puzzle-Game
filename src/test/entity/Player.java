package test.entity;

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


	private static Player instance;

	private Player()
	{
		BufferedImage playerStates;

		this.imageStates =  new ArrayList<>();

		try
		{
			playerStates = ImageIO.read(getClass().getResourceAsStream("testSprites.png"));

			for (int a = 0; a < (playerStates.getWidth()/32)/4; a++)
				for (int b = 0; b < (playerStates.getHeight()/32)/4; b++)
					this.imageStates.add(playerStates.getSubimage(a*32, b*32, 32, 32));

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

	public void update()
	{
		//Check movement
		if(wPressed && !sPressed)						//Up
		{
			this.setVelY(-1);
			this.setImg((this.imageStates.get(3)));
		}
		else if(sPressed && !wPressed)					//Down
		{
			this.setVelY(1);
			this.setImg((this.imageStates.get(0)));
		}
		else											//No Vertical movements
		{
			this.setVelY(0);
		}

		if(aPressed && !dPressed)						//Left
		{
		 	this.setVelX(-1);
		 	this.setImg((this.imageStates.get(1)));
		}
		else if(dPressed && !aPressed)					//Right
		{
			this.setVelX(1);
			this.setImg((this.imageStates.get(2)));
		}
		else											//No Horizontal movements
		{
			this.setVelX(0);
		}

		this.setX(this.getX() + this.getVelX()); //Move on the x axis
		this.setY(this.getY() + this.getVelY()); //Move on the x axis
	}

	public void collison(ArrayList<Entity> list, int x, int y)
	{
		for (Entity e: list)
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
}
