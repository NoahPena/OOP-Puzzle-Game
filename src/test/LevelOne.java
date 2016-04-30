package test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by noah-pena on 4/28/16.
 */
public class LevelOne extends Level implements KeyListener
{
	Map map;
	GraphicsEngine graphics;

	public LevelOne()
	{
		super();
	}

	@Override
	public void onCreate()
	{
		map = new Map(".//assets//maps//basicMap", "basicBitchMap.tmx", 0, 0);
		map.setDrawSize(800, 600);

		graphics = new GraphicsEngine(map, 800, 600);
		graphics.addKeyListener(this);
	}

	@Override
	public void onUpdate(float deltaTime)
	{

	}

	@Override
	public void onDraw()
	{
		map.repaint();
		graphics.repaint();
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent)
	{
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

	}
}
