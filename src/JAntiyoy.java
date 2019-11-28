import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;

public class JAntiyoy implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	public final int WIDTH, HEIGHT;
	public int xoffset, yoffset, zoomoffset = 1;
	public int frame = 0;
	public World worldGen;
	public Hex[][] world;
	public int mousepressx, mousepressy;
	public int mousedragx, mousedragy;
	
	public JAntiyoy(int w, int h) {
		// init
		this.WIDTH = w;
		this.HEIGHT = h;
		worldGen = new World(1);
		worldGen.generateWorld();
		world = worldGen.getWorld();
	}
	
	public void tick(Graphics g) {
		// loop
		drawBackground(g, Color.blue);
		drawMap(g);
		
		frame++;
	}
	
	public void drawMap(Graphics g) {
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				double hexoffset = 0 * zoomoffset;
				double xgap = 30 * zoomoffset;
				double ygap = 8.5 * zoomoffset;
				double radius = 10 * zoomoffset;
				if(i % 2 == 0) {
					hexoffset = xgap / 2;
					//g.setColor(Color.green);
				} else {
					hexoffset = 0;
					//g.setColor(Color.red);
				}
				Polygon hex = world[i][j].getPolygon(j*xgap + hexoffset + xoffset, i*ygap + yoffset, radius);
				g.setColor(world[i][j].getColor());
				g.fillPolygon(hex);
				g.setColor(Color.black);
				g.drawPolygon(hex);
			}
		}
	}
	
	public void drawBackground(Graphics g, Color c) {
		Color oldColor = g.getColor();
		g.setColor(c);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(oldColor);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouse clicked");
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Mouse Pressed");
		mousedragx = e.getX();
		mousedragy = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//System.out.println("mouse dragged " + e.getX() + ", " + e.getY());
		
		System.out.println("xdiff: " + (e.getX() - mousedragx));
		System.out.println("ydiff: " + (e.getY() - mousedragy));
		
		if(e.getX() - mousedragx < 20) {
			xoffset += e.getX() - mousedragx;
			yoffset += e.getY() - mousedragy;
		}
		
		mousedragx = e.getX();
		mousedragy = e.getY();
		
		//System.out.println("idle: " + e.getX());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		zoomoffset -= e.getUnitsToScroll() / 3;
		if(zoomoffset > 10) {
			zoomoffset = 10;
		}
		if(zoomoffset < 1) {
			zoomoffset = 1;
		}
		
	}
}
