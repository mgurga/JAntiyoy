import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set; 

public class World {
	public Hex[][] world;
	public Hex[][] cleanWorld;
	public int worldSize;
	public int finishedCleaning = 0;
	Random rand = new Random();
	public PlayerTurn pt;
	public int predefinedstatuses = 2;
	
	public World(int size) {
		if(size == 0) {
			// small
			world = new Hex[20][10];
			cleanWorld = new Hex[20][10];
			worldSize = 0;
		}
		if(size == 1) {
			// medium
			world = new Hex[30][15];
			cleanWorld = new Hex[30][15];
			worldSize = 1;
		}
		if(size == 2) {
			// large
			world = new Hex[50][25];
			cleanWorld = new Hex[50][25];
			worldSize = 2;
		}
		
		pt = new PlayerTurn(world, this);
	}
	
	public void generateWorld() {
		cleanWorld = new Hex[world.length][world[0].length];
		// make all unclaimed land
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				world[i][j] = new Hex(1, j, i);
			}
		}
		
		// Roughens up the edge of the map
		// 90% chance the first row of hexs are sea
		// 50% chance the seconds row of hexs are sea
		// 20% chance the third row of hexes are sea
		// 10% chance the fourth row of hexes are sea
		for(int i = 0; i < world[0].length; i++) {
			if(rand.nextInt(10) < 9) {world[0][i].setStatus(0);}
			if(rand.nextInt(10) < 9) {world[world.length - 1][i].setStatus(0);}
			if(rand.nextInt(2) == 0) {world[1][i].setStatus(0);}
			if(rand.nextInt(2) == 0) {world[world.length - 2][i].setStatus(0);}
			if(rand.nextInt(5) == 0) {world[2][i].setStatus(0);}
			if(rand.nextInt(5) == 0) {world[world.length - 3][i].setStatus(0);}
			if(rand.nextInt(9) == 0) {world[2][i].setStatus(0);}
			if(rand.nextInt(9) == 0) {world[world.length - 3][i].setStatus(0);}
		}
		for(int i = 0; i < world.length; i++) {
			if(rand.nextInt(10) < 9) {world[i][0].setStatus(0);}
			if(rand.nextInt(10) < 9) {world[i][world[0].length - 1].setStatus(0);}
			if(rand.nextInt(2) == 0) {world[i][1].setStatus(0);}
			if(rand.nextInt(2) == 0) {world[i][world[0].length - 2].setStatus(0);}
			if(rand.nextInt(5) == 0) {world[i][2].setStatus(0);}
			if(rand.nextInt(5) == 0) {world[i][world[0].length - 3].setStatus(0);}
			if(rand.nextInt(9) == 0) {world[i][3].setStatus(0);}
			if(rand.nextInt(9) == 0) {world[i][world[0].length - 4].setStatus(0);}
		}
		
		// make lakes
		for(int i = 0; i < worldSize; i++) {
			makeLake(world[0].length / 4 + rand.nextInt(world[0].length / 2), world.length / 4 + rand.nextInt(world.length / 2), 0);
		}
		
		// cleanup world
		//cleanupWorld(world[0].length / 4, world.length / 4, 0);
		world = cleanup();
		
	}
	
	public Hex[][] cleanup() {
		Hex[][] newWorld = new Hex[world.length][world[0].length];
		
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				newWorld[i][j] = new Hex(0, j, i);
			}
		}
		
		cleanupWorld(world[0].length / 4, world.length / 4, 0);
		
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				newWorld[i][j] = new Hex(0, j, i);
				if(world[i][j].getCleaned() == true) {
					newWorld[i][j] = new Hex(1, j, i);
					newWorld[i][j].setClean(true);
					newWorld[i][j].setCleanedBy(world[i][j].getCleanedBy());
				}
			}
		}
		
		return newWorld;
	}
	
	public void highlightPlayerHexs(int playerNum) {
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				if(world[i][j].getStatus() == playerNum + predefinedstatuses) {
					world[i][j].isHighlighted = true;
				}
			}
		}
	}
	
	public void unhighlightAllPlayerHexs(int playerNum) {
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				if(world[i][j].getStatus() == playerNum + predefinedstatuses) {
					world[i][j].isHighlighted = false;
				}
			}
		}
	}
	
	public void highlightValidHexsForItem() {
		
	}
	
	public void unhighlightAll() {
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				world[i][j].isHighlighted = false;
			}
		}
	}
	
	public void cleanupWorld(int x, int y, int iter) {
		finishedCleaning++;
		if(iter < 100 && x >= 1 && x < world[0].length - 1 && y >= 2 && y < world.length - 2) {
			//System.out.println(finishedCleaning);
			world[y][x].setClean(true);
			
			if(world[y+1][x].getStatus() != 0 && !world[y+1][x].getCleaned() && y % 2 == 0) {
				cleanupWorld(x, y+1, iter+1);//down left 
				world[y+1][x].setCleanedBy(world[y][x]);
			} else if(world[y+1][x-1].getStatus() != 0 && !world[y+1][x-1].getCleaned() && y % 2 == 1) {
				cleanupWorld(x-1, y+1, iter+1);//down left 
				world[y+1][x-1].setCleanedBy(world[y][x]);
			}
			if(world[y-1][x].getStatus() != 0 && !world[y-1][x].getCleaned() && y % 2 == 0) {
				cleanupWorld(x, y-1, iter+1);//up left
				world[y-1][x].setCleanedBy(world[y][x]);
			} else if(world[y-1][x-1].getStatus() != 0 && !world[y-1][x-1].getCleaned() && y % 2 == 1) {
				cleanupWorld(x-1, y-1, iter+1);//up left
				world[y-1][x-1].setCleanedBy(world[y][x]);
			}
			if(world[y-2][x].getStatus() != 0 && !world[y-2][x].getCleaned()) {
				cleanupWorld(x, y-2, iter+1);//up mid
				world[y-2][x].setCleanedBy(world[y][x]);
			}
			if(world[y+1][x+1].getStatus() != 0 && !world[y+1][x+1].getCleaned() && y % 2 == 0) {
				cleanupWorld(x+1, y+1, iter+1);//down right
				world[y+1][x+1].setCleanedBy(world[y][x]);
			} else if(world[y+1][x].getStatus() != 0 && !world[y+1][x].getCleaned() &&  y % 2 == 1) {
				cleanupWorld(x, y+1, iter+1);//down right
				world[y+1][x].setCleanedBy(world[y][x]);
			}
			if(world[y+2][x].getStatus() != 0 && !world[y+2][x].getCleaned()) {
				cleanupWorld(x, y+2, iter+1);//down mid
				world[y+2][x].setCleanedBy(world[y][x]);
			}
			if(world[y-1][x+1].getStatus() != 0 && !world[y-1][x+1].getCleaned() && y % 2 == 0) {
				cleanupWorld(x+1, y-1, iter+1);//up right
				world[y-1][x+1].setCleanedBy(world[y][x]);
			} else if(world[y-1][x].getStatus() != 0 && !world[y-1][x].getCleaned() && y % 2 == 1) {
				cleanupWorld(x, y-1, iter+1);//up right
				world[y-1][x].setCleanedBy(world[y][x]);
			}
		} else {
			finishedCleaning--;
		}
	}
	
	public void makePlayerCamp(int x, int y, int size, int player) {
		makePlayerCampRecurs(x, y, size, player);
		world[y][x].setOccupation("townhall");
	}
	
	public void makePlayerCampRecurs(int x, int y, int size, int player) {
		if(size > 0) {
			Hex[] adjacent = getAdjHexs(new Hex(0, x, y));
			
			world[y][x].setStatus(player);
			world[y][x].setOccupation("");
			
			for(int i = 0; i < 2; i++) {
				int randHex = rand.nextInt(5);
				if(randHex == 0 && adjacent[0].getStatus() != 0)
					makePlayerCampRecurs(adjacent[0].x, adjacent[0].y, size-1, player);
				if(randHex == 1 && adjacent[1].getStatus() != 0)
					makePlayerCampRecurs(adjacent[1].x, adjacent[1].y, size-1, player);
				if(randHex == 2 && adjacent[2].getStatus() != 0)
					makePlayerCampRecurs(adjacent[2].x, adjacent[2].y, size-1, player);
				if(randHex == 3 && adjacent[3].getStatus() != 0)
					makePlayerCampRecurs(adjacent[3].x, adjacent[3].y, size-1, player);
				if(randHex == 4 && adjacent[4].getStatus() != 0)
					makePlayerCampRecurs(adjacent[4].x, adjacent[4].y, size-1, player);
				if(randHex == 5 && adjacent[5].getStatus() != 0)
					makePlayerCampRecurs(adjacent[5].x, adjacent[5].y, size-1, player);
			}
		}
	}
	
	public void highlightSpecificHexs(int target) {
		Hex[] toHighlight = getSpecificHexs(target);
		for(Hex hex : toHighlight) {
			hex.isHighlighted = true;
		}
	}
	
	public Hex[] validSoldierHexs(int player, int workerlevel) {
		ArrayList<Hex> alout = new ArrayList<Hex>();
		Hex[] teamHexs = getSpecificHexs(player+2);
		//System.out.println("team hexs: " + teamHexs.length);
		for(int i = 0; i < teamHexs.length; i++) {
			alout.add(teamHexs[i]);
			
			Hex[] adjs = getValidAdjHexs(teamHexs[i]);
			for(int j = 0; j < adjs.length-1; j++) {
				alout.add(adjs[j]);
			}
		}
		
		Set<Hex> set = new HashSet<>(alout);
		alout.clear();
		alout.addAll(set);
		
		Hex[] out = new Hex[alout.size()];
		for(int i = 0; i < out.length-1; i++) {
			out[i] = alout.get(i);
		}
		return out;
	}
	
	public void highlightValidSoldierHexs(int player, int workerlevel) {
		Hex[] tohighlight = validSoldierHexs(player, workerlevel);
		//System.out.println(tohighlight.length);
		for(int i = 0; i < tohighlight.length-1; i++) {
			if(tohighlight[i] != null)
				tohighlight[i].isHighlighted = true;
		}
	}
			
	public Hex[] getSpecificHexs(int targetStatus) {
		int totalHexs = 0;
		ArrayList<Hex> alout = new ArrayList<Hex>();
		
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				if(world[i][j].getStatus() == targetStatus) {
					alout.add(world[i][j]);
					totalHexs++;
				}
			}
		}
		
		//System.out.println("total: " + totalHexs);
		Hex[] out = new Hex[totalHexs];
		
		for(int i = 0; i < alout.size(); i++) {
			out[i] = alout.get(i);
		}
		
		return out;
	}
	
	public Hex[] getAdjHexs(Hex inputHex) {
		int x = inputHex.x;
		int y = inputHex.y;
		Hex[] out = new Hex[6];
		
		//System.out.println("x: " + x);
		//System.out.println("y: " + y);
		
		if(y >= 2 && y <= world.length-3) {
			if(!(world[y-2][x] == null))
				out[2] = world[y-2][x]; // up mid			
			
			if(!(world[y+2][x] == null))
				out[4] = world[y+2][x]; // down mid
		}
		
		if(y >= 1 && x >= 1 && y < world.length-2 && x < world[0].length-2) {
			if(y % 2 == 0) {
				if(!(world[y+1][x] == null))
					out[0] = world[y+1][x]; // down left
				if(!(world[y+1][x+1] == null))
					out[3] = world[y+1][x+1]; // down right
				if(!(world[y-1][x] == null))
					out[1] = world[y-1][x]; // up left
				if(!(world[y-1][x+1] == null))
					out[5] = world[y-1][x+1]; // up right
			} else if(y % 2 == 1) {
				if(!(world[y+1][x-1] == null))
					out[0] = world[y+1][x-1]; // down left
				if(!(world[y+1][x] == null))
					out[3] = world[y+1][x]; // down right
				if(!(world[y-1][x-1] == null))
					out[1] = world[y-1][x-1]; // up left
				if(!(world[y-1][x] == null))
					out[5] = world[y-1][x]; // up right
			}
		}
		
		return out;
	}
	
	public Hex[] getValidAdjHexs(Hex inputHex) {
		List<Hex> alin = Arrays.asList(getAdjHexs(inputHex));
		ArrayList<Hex> alout = new ArrayList<Hex>();
		
		for(int i = 0; i < alin.size(); i++) {
			if(alin.get(i).getStatus() != 0) 
				alout.add(alin.get(i));
		}
		
		Hex[] out = new Hex[alout.size()];
		for(int i = 0; i < alout.size(); i++) {
			out[i] = alout.get(i);
		}
		
		return out;
	}
	
	public Hex getRandomHex(int status) {
		ArrayList<Hex> validHexs = new ArrayList<Hex>();
		
		for(int i = 0; i < world.length; i++) {
			for(int j = 0; j < world[0].length; j++) {
				if(world[i][j].getStatus() == status)
					validHexs.add(world[i][j]);
			}
		}
		
		return validHexs.get(rand.nextInt(validHexs.size()));
		
	}
	
	public void makeLake(int x, int y, int iter) {
		world[y][x].setStatus(0);
		
		if(iter < worldSize*2 + 2) {
			for(int i = 0; i < 2; i++) {
				int randHex = rand.nextInt(6);
				if(randHex == 1)
					makeLake(x, y-1, iter+1);
				if(randHex == 2)
					makeLake(x+1, y-1, iter+1);
				if(randHex == 3)
					makeLake(x-1, y, iter+1);
				if(randHex == 4)
					makeLake(x+1, y, iter+1);
				if(randHex == 5)
					makeLake(x, y+1, iter+1);
				if(randHex == 6)
					makeLake(x+1, y+1, iter+1);
			}
		}
	}
	
	public void updateWorld(Hex[][] newWorld) {
		world = newWorld;
	}
	
	public Hex[][] getWorld() {
		return world;
	}
}

class Hex {
	
	// Hex Status:
	// 0 = Nothing / Sea
	// 1 = Unclaimed
	// 2 = Player 1 Owned (red)
	// 3 = Player 2 Owned (blue)
	
	// Hex Occupation:
	// soldier0 = worker
	// soldier1 = brawler
	// soldier2 = warrior
	// soldier3 = knight
	// tower0 = weak tower
	// tower1 = strong tower
	// farm = farm
	// townhall = team center
	
	private int status;
	private String occupiedBy = "";
	public boolean cleaned = false;
	private Hex cleanedBy;
	public int x, y;
	public boolean isHighlighted = false;
	
	public Hex(int status, int x, int y) {
		this.status = status;
		this.x = x;
		this.y = y;
	}
	
	public int getStatus() {
		return status;
	}
	
	public boolean getCleaned() {
		return cleaned;
	}
	
	public Hex getCleanedBy() {
		return cleanedBy;
	}
	
	public void setClean(boolean newCleaned) {
		cleaned = newCleaned;
	}
	
	public void setCleanedBy(Hex cleaner) {
		cleanedBy = cleaner;
	}
	
	public Color getColor() {
		if(status == 0) {	
			return Color.blue;
		}
		if(status == 1) {
			return Color.lightGray;
		}
		if(status == 2) {
			return PlayerTurn.teamColors[0];
		}
		if(status == 3) {
			return PlayerTurn.teamColors[1];
		}
		if(status == 4) {
			return PlayerTurn.teamColors[2];
		}
		if(status == 5) {
			return PlayerTurn.teamColors[3];
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
