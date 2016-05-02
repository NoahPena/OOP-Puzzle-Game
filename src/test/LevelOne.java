package test;

import test.entity.Entity;
import test.entity.Player;
import test.entity.Rock;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by noah-pena on 4/28/16.
 */
public class LevelOne extends Level implements KeyListener
{
	Map map;
	Player player;
    ArrayList<Entity> entities;

	private BufferedImage screen;

	public LevelOne()
	{
		super();

        entities =  new ArrayList<>();
		screen = new BufferedImage(Settings.getWindowWidth(), Settings.getWindowHeight(), BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public void onCreate()
	{
		map = new Map("/maps/shortBasicMap", "shortBasicMap.tmx", 0, 0);
		//map = new Map("/maps/basicMap", "basicBitchMap.tmx", 0, 0);
		map.setDrawSize(Settings.getWindowWidth(), Settings.getWindowHeight());

		//Add player
		player = Player.getInstance();
        player.setMap(this.map);
        entities.add(new Rock());
        entities.get(0).setMap(this.map);
	}

	@Override
	public void onUpdate(float deltaTime)
	{
		this.player.update(entities);

		System.out.println(map.testForTriggers(this.player.getBounds()));

        for (Entity ent: entities)
            ent.update(entities);

	}

	@Override
	public void onDraw()
	{
		if(this.map.getGraphics() != null && this.screen.getGraphics() != null)
		{
			this.map.draw(this.screen.getGraphics());
			this.player.draw(this.screen.getGraphics());
            for (Entity ent: entities)
                ent.draw(this.screen.getGraphics());

			this.map.getGraphics().drawImage(this.screen, 0, 0, null);
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

//		switch(keyEvent.getKeyCode())
//		{
//			case KeyEvent.VK_LEFT:
//
//				map.setDrawPosition(map.getDrawX() - 5, map.getDrawY());
//
//				break;
//
//			case KeyEvent.VK_RIGHT:
//
//				map.setDrawPosition(map.getDrawX() + 5, map.getDrawY());
//
//				break;
//
//			case KeyEvent.VK_DOWN:
//
//				map.setDrawPosition(map.getDrawX(), map.getDrawY() - 5);
//
//				break;
//
//			case KeyEvent.VK_UP:
//
//				map.setDrawPosition(map.getDrawX(), map.getDrawY() + 5);
//
//				break;
//		}
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
