
import java.awt.*;
import java.awt.image.BufferStrategy;

import javax.swing.*;

public class Game extends Canvas implements Runnable {

	public final int WIDTH = 800, HEIGHT = 600;

	Thread thread;
	boolean running = false;
	JAntiyoy jagame;
	Window window;

	public Game() {
		window = new Window(WIDTH, HEIGHT, "JAntiyoy", this);
		addMouseListener(jagame);
		addMouseMotionListener(jagame);
		addMouseWheelListener(jagame);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
		jagame = new JAntiyoy(WIDTH, HEIGHT);
	}

	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Game();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (running)
				render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				window.getFrame().setTitle("JAntiyoy FPS: " + frames);
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	public void tick() {
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		
		//g.setColor(Color.black);
		//g.fillRect(0, 0, WIDTH, HEIGHT);
		jagame.tick(g);
		
		g.dispose();
		bs.show();
	}

}
