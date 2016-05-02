package test;

import test.entity.Entity;
import test.entity.ImmovableRock;
import test.entity.Player;
import test.entity.Rock;

import javax.swing.*;
import java.awt.*;
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
    private boolean lock = true;

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
		map = new Map("/maps/levelOne", "levelOne.tmx", 0, 0);
		//map = new Map("/maps/basicMap", "basicBitchMap.tmx", 0, 0);
		map.setDrawSize(Settings.getWindowWidth(), Settings.getWindowHeight());



		//Add player
		player = Player.getInstance();
        player.setPosition(new Point(800, 400));
        player.setMap(this.map);
        entities.add(new Rock());
        entities.get(0).setMap(this.map);
        entities.get(0).setPosition(new Point(850, 400));
        entities.add(new ImmovableRock());
        entities.get(1).setPosition(new Point(384, 672));
        entities.add(new ImmovableRock());
        entities.get(2).setPosition(new Point(416, 672));
	}

	@Override
	public void onUpdate(float deltaTime)
	{
		this.player.update(entities);

        if(map.testForTriggers(entities.get(0).getBounds()))
        {
            if(lock)
            {
                ImmovableRock temp = (ImmovableRock) entities.get(1);
                temp.move("South");
                temp.move("West");

                temp = (ImmovableRock) entities.get(2);
                temp.move("South");
                temp.move("East");
                lock = false;
            }
        }

        for (Entity ent: entities)
        {
            map.testForTriggers(ent.getBounds());
            ent.update(entities);
        }

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

        ImmovableRock temp  = (ImmovableRock)entities.get(1);
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
