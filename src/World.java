import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Random; 

public class World {
	public Hex[][] world;
	
	public World(int size) {
		if(size == 0) {
			// small
			world = new Hex[20][10];
		}
		if(size == 1) {
			// medium
			world = new Hex[50][25];
		}
		if(size == 2) {
			// large
			world = new Hex[80][40];
		}
		
	}
	
	public void generateWorld() {
		
		Random rand = new Random();
		
		// make all unclaimed land
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				world[i][j] = new Hex(1);
			}
		}
		
		//int LandToDelete = (int) (Math.floor(world.length / 2.5) + rand.nextInt(5));
	}
	
	public Hex[][] getWorld() {
		return world;
	}
}

class Hex {
	
	// Hex Status:
	// 0 = Nothing / Sea
	// 1 = Unclaimed
	// 2 = Player 1 Owned
	// 3 = Player 2 Owned
	
	// Hex Occupation:
	// soldier1 = worker
	// soldier2 = brawler
	// soldier3 = warrior
	// soldier4 = knight
	// tower1 = weak tower
	// tower2 = strong tower
	// village = village
	// townhall = team center
	
	private int status;
	public String occupiedBy;
	
	public Hex(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	public Color getColor() {
		if(status == 0) {	
			return Color.blue;
		}
		if(status == 1) {
			return Color.lightGray;
		}
		if(status == 2) {
			return Color.pink;
		}
		if(status == 3) {
			return Color.cyan;
		}
		return null;
	}
	
	public String getOccupation() {
		return occupiedBy;
	}
	
	public void setStatus(int newStatus) {
		this.status = newStatus;
	}
	
	public void setOccupation(String newOccu) {
		this.occupiedBy = newOccu;
	}
	
	public Polygon getPolygon(double x, double y, double rad) {
		Polygon polygon = new Polygon();
		for (int i = 0; i < 6; i++) {
            int xval = (int) (x + rad
                    * Math.cos(i * 2 * Math.PI / 6D));
            int yval = (int) (y + rad
                    * Math.sin(i * 2 * Math.PI / 6D));
            polygon.addPoint(xval, yval);
        }
		//g.drawPolygon(polygon);
		return polygon;
	}
	
}
