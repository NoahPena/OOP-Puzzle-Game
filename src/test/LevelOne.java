package test;

import test.entity.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by noah-pena on 4/28/16.
 */
public class LevelOne extends Level implements KeyListener
{
	Map map;
	//GraphicsEngine graphics;
	Player player;

	public LevelOne()
	{
		super();
	}

	@Override
	public void onCreate()
	{
		map = new Map(".//assets//maps//basicMap", "basicBitchMap.tmx", 0, 0);
		map.setDrawSize(800, 600);

		//graphics = new GraphicsEngine(map, 800, 600);
		//graphics.addKeyListener(this);


		//Add player
		player = Player.getInstance();
	}

	@Override
	public void onUpdate(float deltaTime)
	{
		this.player.update();
	}

	@Override
	public void onDraw()
	{
		if(this.map.getGraphics() != null)
		{
			map.repaint();
			this.player.draw(this.map.getGraphics());
		}
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
		//send event to player
		this.player.keyPressed(keyEvent);

		switch(keyEvent.getKeyCode())
		{
			case KeyEvent.VK_LEFT:

				map.setDrawPosition(map.getDrawX() - 5, map.getDrawY());

				break;

			case KeyEvent.VK_RIGHT:

				map.setDrawPosition(map.getDrawX() + 5, map.getDrawY());

				break;

			case KeyEvent.VK_DOWN:

				map.setDrawPosition(map.getDrawX(), map.getDrawY() - 5);

				break;

			case KeyEvent.VK_UP:

				map.setDrawPosition(map.getDrawX(), map.getDrawY() + 5);

				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		this.player.keyReleased(keyEvent);
	}

	public Map getMap() {
		return map;
	}

	public void setKeyListener(JFrame frame)
	{
		frame.addKeyListener(this);
	}
}
