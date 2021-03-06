
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    public final int WIDTH = 800, HEIGHT = 600;

    Thread thread;
    boolean running = false;
    JAntiyoy jagame;
    Window window;

    public Game() {
        window = new Window(WIDTH, HEIGHT, "JAntiyoy", this);
        jagame = new JAntiyoy(WIDTH, HEIGHT, window.getFrame());
        addMouseListener(jagame);
        addMouseMotionListener(jagame);
        addMouseWheelListener(jagame);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
        // jagame.init();
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
            if (running) {
                // runs every frame
                render();
                frames++;
            }

            if (System.currentTimeMillis() - timer > 100) {
                // this runs very 1 second
                timer += 500;
                window.getFrame().setTitle("JAntiyoy FPS: " + frames);
                // System.out.println("FPS: " + frames);
                frames = 0;
                jagame.blink = !jagame.blink;
            }

        }
        stop();
    }

    public void tick() {

    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        try {
            jagame.tick(g);
        } catch (NullPointerException e) {
            // jagame is null for a few frames for some reason
            // TODO wait for jagame to no be null???
            // e.printStackTrace();
        }

        g.dispose();
        bs.show();
    }

}
