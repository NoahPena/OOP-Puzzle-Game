package test;

import sun.font.FontFamily;
import test.entity.Entity;
import test.entity.ImmovableRock;
import test.entity.Player;
import test.entity.Rock;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by noah-pena on 4/28/16.
 */
public class LevelOne extends Level implements KeyListener
{
	Map map;
	Player player;
	ArrayList<Entity> entities;
	private boolean[] lock = new boolean[10];


	private BufferedImage screen;

	public LevelOne()
	{
		super();

		Arrays.fill(lock, true);
		entities =  new ArrayList<>();
		screen = new BufferedImage(Settings.getWindowWidth(), Settings.getWindowHeight(), BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public void onCreate()
	{
		map = new Map("/maps/levelOne", "levelOne.tmx", 0, 0);
		//map = new Map("/maps/basicMap", "basicBitchMap.tmx", 0, 0);
		map.setDrawSize(Settings.getWindowWidth(), Settings.getWindowHeight());

		this.setGoal(new Rectangle(100, 830, 32, 32));

		//Add player
		player = Player.getInstance();
		player.setPosition(new Point(800, 400));
		player.setMap(this.map);
		//entites
		entities.add(new Rock());
		entities.get(0).setMap(this.map);
		entities.get(0).setPosition(new Point(1312, 192));
		entities.add(new Rock());
		entities.get(1).setMap(this.map);
		entities.get(1).setPosition(new Point(850, 400));
		entities.add(new ImmovableRock());
		entities.get(2).setPosition(new Point(384, 672));
		entities.add(new ImmovableRock());
		entities.get(3).setPosition(new Point(416, 672));
		entities.add(new ImmovableRock());
		entities.get(4).setPosition(new Point(1312, 160));
		entities.add(new ImmovableRock());
		entities.get(5).setPosition(new Point(1312, 224));
		entities.add(new ImmovableRock());
		entities.get(6).setPosition(new Point(1280, 192));
		entities.add(new ImmovableRock());
		entities.get(7).setPosition(new Point(1344, 192));
	}

	@Override
	public void onUpdate(float deltaTime)
	{
		this.player.update(entities);

		if(map.testForTriggers(entities.get(0).getBounds()))
		{
			if(lock[0])
			{
				ImmovableRock temp = (ImmovableRock) entities.get(2);
				temp.move("South");
				temp.move("West");

				temp = (ImmovableRock) entities.get(3);
				temp.move("South");
				temp.move("East");
				lock[0] = false;
			}
		}

		if(map.testForTriggers(entities.get(1).getBounds()))
		{
			if(lock[1])
			{
				ImmovableRock temp = (ImmovableRock) entities.get(6);
				temp.move("West");
				lock[1] = false;
			}
		}

		for (Entity ent: entities)
		{
			map.testForTriggers(ent.getBounds());
			ent.update(entities);
		}

		if(player.getBounds().intersects(this.getGoal()))
		{
			Graphics g = this.screen.getGraphics();
			g.setFont(this.screen.getGraphics().getFont().deriveFont(200f));
			g.drawString("Level Complete", 100, 500);
			this.map.getGraphics().drawImage(this.screen, 0, 0, null);
			this.end();
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

			BufferedImage tempGoal = this.screen;
			try
			{
				tempGoal = ImageIO.read(getClass().getResourceAsStream("/objects/goal.png"));
			} catch (IOException e)
			{
				e.getStackTrace();
			}
			this.screen.getGraphics().drawImage(tempGoal, (int)this.getGoal().getX(), (int)this.getGoal().getY(), null);

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
