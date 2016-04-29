package test;

/**
 * Created by noah-pena on 4/28/16.
 */
public abstract class Level implements GameLoop, Runnable
{
	private Thread thread;

	private long lastLoopTime = System.nanoTime();

	private final int TARGET_FPS = 60;
	private final long BEST_TIME = 1000000000 / TARGET_FPS;

	//private boolean paused = false;
	private static boolean running = false;

	public abstract void onCreate();
	public abstract void onUpdate(float deltaTime);
	public abstract void onDraw();

	public Level()
	{

	}

	public void execute()
	{
		//System.out.println("eh");
		onCreate();
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void end()
	{
		running = false;

		try
		{
			thread.join();
		}
		catch(Exception e)
		{

		}
	}

	@Override
	public void run()
	{
		//System.out.println(running);
		while(running)
		{
			long now = System.nanoTime();
			long updateTime = now - lastLoopTime;
			lastLoopTime = now;
			float delta = updateTime / BEST_TIME;

			//System.out.println("ehhh");

			onUpdate(delta);
			onDraw();

			try
			{
				thread.sleep((lastLoopTime - System.nanoTime() + BEST_TIME) / 1000000);
			}
			catch(Exception e)
			{

			}
		}
	}
}
