import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class World
{
	public Hex[][] world;
	public Hex[][] cleanWorld;
	public int worldSize;
	public int finishedCleaning = 0;
	Random rand = new Random();
	public PlayerTurn pt;
	public int predefinedstatuses = 2;

	public World(int size)
	{
		if (size == 0)
		{
			// small
			world = new Hex[20][10];
			cleanWorld = new Hex[20][10];
			worldSize = 0;
		}
		if (size == 1)
		{
			// medium
			world = new Hex[30][15];
			cleanWorld = new Hex[30][15];
			worldSize = 1;
		}
		if (size == 2)
		{
			// large
			world = new Hex[50][25];
			cleanWorld = new Hex[50][25];
			worldSize = 2;
		}

		pt = new PlayerTurn(world, this);
	}

	public void generateWorld()
	{
		cleanWorld = new Hex[world.length][world[0].length];
		// make all unclaimed land
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				world[i][j] = new Hex(1, j, i);
			}
		}

		// Roughens up the edge of the map
		// 90% chance the first row of hexs are sea
		// 50% chance the seconds row of hexs are sea
		// 20% chance the third row of hexes are sea
		// 10% chance the fourth row of hexes are sea
		for (int i = 0; i < world[0].length; i++)
		{
			if (rand.nextInt(10) < 9)
			{
				world[0][i].setStatus(0);
			}
			if (rand.nextInt(10) < 9)
			{
				world[world.length - 1][i].setStatus(0);
			}
			if (rand.nextInt(2) == 0)
			{
				world[1][i].setStatus(0);
			}
			if (rand.nextInt(2) == 0)
			{
				world[world.length - 2][i].setStatus(0);
			}
			if (rand.nextInt(5) == 0)
			{
				world[2][i].setStatus(0);
			}
			if (rand.nextInt(5) == 0)
			{
				world[world.length - 3][i].setStatus(0);
			}
			if (rand.nextInt(9) == 0)
			{
				world[2][i].setStatus(0);
			}
			if (rand.nextInt(9) == 0)
			{
				world[world.length - 3][i].setStatus(0);
			}
		}
		for (int i = 0; i < world.length; i++)
		{
			if (rand.nextInt(10) < 9)
			{
				world[i][0].setStatus(0);
			}
			if (rand.nextInt(10) < 9)
			{
				world[i][world[0].length - 1].setStatus(0);
			}
			if (rand.nextInt(2) == 0)
			{
				world[i][1].setStatus(0);
			}
			if (rand.nextInt(2) == 0)
			{
				world[i][world[0].length - 2].setStatus(0);
			}
			if (rand.nextInt(5) == 0)
			{
				world[i][2].setStatus(0);
			}
			if (rand.nextInt(5) == 0)
			{
				world[i][world[0].length - 3].setStatus(0);
			}
			if (rand.nextInt(9) == 0)
			{
				world[i][3].setStatus(0);
			}
			if (rand.nextInt(9) == 0)
			{
				world[i][world[0].length - 4].setStatus(0);
			}
		}

		// make lakes
		for (int i = 0; i < worldSize; i++)
		{
			makeLake(world[0].length / 4 + rand.nextInt(world[0].length / 2),
					world.length / 4 + rand.nextInt(world.length / 2), 0);
		}

		// cleanup world
		// cleanupWorld(world[0].length / 4, world.length / 4, 0);
		world = cleanup();

	}

	public Hex[][] cleanup()
	{
		Hex[][] newWorld = new Hex[world.length][world[0].length];

		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				newWorld[i][j] = new Hex(0, j, i);
			}
		}

		cleanupWorld(world[0].length / 4, world.length / 4, 0);

		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				newWorld[i][j] = new Hex(0, j, i);
				if (world[i][j].getCleaned() == true)
				{
					newWorld[i][j] = new Hex(1, j, i);
					newWorld[i][j].setClean(true);
					newWorld[i][j].setCleanedBy(world[i][j].getCleanedBy());
				}
			}
		}

		return newWorld;
	}

	public void highlightPlayerHexs(int playerNum)
	{
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				// System.out.println(world[i][j].getStatus());
				if (world[i][j].getStatus() == playerNum + predefinedstatuses)
				{
					world[i][j].isHighlighted = true;
				}
			}
		}
	}

	public void unhighlightAllPlayerHexs(int playerNum)
	{
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				if (world[i][j].getStatus() == playerNum + predefinedstatuses)
				{
					world[i][j].isHighlighted = false;
				}
			}
		}
	}

	public void highlightValidHexsForItem()
	{

	}

	public void unhighlightAll()
	{
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				world[i][j].isHighlighted = false;
			}
		}
	}

	public void cleanupWorld(int x, int y, int iter)
	{
		finishedCleaning++;
		if (iter < 100 && x >= 1 && x < world[0].length - 1 && y >= 2 && y < world.length - 2)
		{
			// System.out.println(finishedCleaning);
			world[y][x].setClean(true);

			if (world[y + 1][x].getStatus() != 0 && !world[y + 1][x].getCleaned() && y % 2 == 0)
			{
				cleanupWorld(x, y + 1, iter + 1);// down left
				world[y + 1][x].setCleanedBy(world[y][x]);
			}
			else if (world[y + 1][x - 1].getStatus() != 0 && !world[y + 1][x - 1].getCleaned() && y % 2 == 1)
			{
				cleanupWorld(x - 1, y + 1, iter + 1);// down left
				world[y + 1][x - 1].setCleanedBy(world[y][x]);
			}
			if (world[y - 1][x].getStatus() != 0 && !world[y - 1][x].getCleaned() && y % 2 == 0)
			{
				cleanupWorld(x, y - 1, iter + 1);// up left
				world[y - 1][x].setCleanedBy(world[y][x]);
			}
			else if (world[y - 1][x - 1].getStatus() != 0 && !world[y - 1][x - 1].getCleaned() && y % 2 == 1)
			{
				cleanupWorld(x - 1, y - 1, iter + 1);// up left
				world[y - 1][x - 1].setCleanedBy(world[y][x]);
			}
			if (world[y - 2][x].getStatus() != 0 && !world[y - 2][x].getCleaned())
			{
				cleanupWorld(x, y - 2, iter + 1);// up mid
				world[y - 2][x].setCleanedBy(world[y][x]);
			}
			if (world[y + 1][x + 1].getStatus() != 0 && !world[y + 1][x + 1].getCleaned() && y % 2 == 0)
			{
				cleanupWorld(x + 1, y + 1, iter + 1);// down right
				world[y + 1][x + 1].setCleanedBy(world[y][x]);
			}
			else if (world[y + 1][x].getStatus() != 0 && !world[y + 1][x].getCleaned() && y % 2 == 1)
			{
				cleanupWorld(x, y + 1, iter + 1);// down right
				world[y + 1][x].setCleanedBy(world[y][x]);
			}
			if (world[y + 2][x].getStatus() != 0 && !world[y + 2][x].getCleaned())
			{
				cleanupWorld(x, y + 2, iter + 1);// down mid
				world[y + 2][x].setCleanedBy(world[y][x]);
			}
			if (world[y - 1][x + 1].getStatus() != 0 && !world[y - 1][x + 1].getCleaned() && y % 2 == 0)
			{
				cleanupWorld(x + 1, y - 1, iter + 1);// up right
				world[y - 1][x + 1].setCleanedBy(world[y][x]);
			}
			else if (world[y - 1][x].getStatus() != 0 && !world[y - 1][x].getCleaned() && y % 2 == 1)
			{
				cleanupWorld(x, y - 1, iter + 1);// up right
				world[y - 1][x].setCleanedBy(world[y][x]);
			}
		}
		else
		{
			finishedCleaning--;
		}
	}

	public void makePlayerCamp(int x, int y, int size, int player)
	{
		makePlayerCampRecurs(x, y, size, player);
		world[y][x].setItem(new Item("townhall", 0));
	}

	public void makePlayerCampRecurs(int x, int y, int size, int player)
	{
		if (size > 0)
		{
			Hex[] adjacent = getAdjHexs(new Hex(0, x, y));

			world[y][x].setStatus(player);

			for (int i = 0; i < 2; i++)
			{
				int randHex = rand.nextInt(5);
				if (randHex == 0 && adjacent[0].getStatus() != 0)
					makePlayerCampRecurs(adjacent[0].x, adjacent[0].y, size - 1, player);
				if (randHex == 1 && adjacent[1].getStatus() != 0)
					makePlayerCampRecurs(adjacent[1].x, adjacent[1].y, size - 1, player);
				if (randHex == 2 && adjacent[2].getStatus() != 0)
					makePlayerCampRecurs(adjacent[2].x, adjacent[2].y, size - 1, player);
				if (randHex == 3 && adjacent[3].getStatus() != 0)
					makePlayerCampRecurs(adjacent[3].x, adjacent[3].y, size - 1, player);
				if (randHex == 4 && adjacent[4].getStatus() != 0)
					makePlayerCampRecurs(adjacent[4].x, adjacent[4].y, size - 1, player);
				if (randHex == 5 && adjacent[5].getStatus() != 0)
					makePlayerCampRecurs(adjacent[5].x, adjacent[5].y, size - 1, player);
			}
		}
	}

	public void highlightSpecificHexs(int target)
	{
		Hex[] toHighlight = getSpecificHexs(target);
		for (Hex hex : toHighlight)
		{
			hex.isHighlighted = true;
		}
	}

	public Hex[] validSoldierHexs(int player, int workerlevel)
	{
		// gets all player hexs then gets all adjacent hexs to the player hexs
		// adds all to an arraylist then converets it to Hex[]
		ArrayList<Hex> alout = new ArrayList<Hex>();
		Hex[] teamHexs = getSpecificHexs(player + predefinedstatuses);
		for (int i = 0; i < teamHexs.length; i++)
		{
			if (teamHexs[i].getStatus() != 0 && teamHexs[i] != null)
			{
				alout.add(teamHexs[i]);
			}

			Hex[] adjs = getAdjHexs(teamHexs[i]);

			for (int j = 0; j < adjs.length; j++)
			{
				if (adjs[j].getStatus() != 0 && adjs[j] != null)
				{
					alout.add(adjs[j]);
				}
			}
		}

		// converting to Hex[]
		Hex[] out = new Hex[alout.size()];
		for (int i = 0; i < alout.size(); i++)
		{
			out[i] = alout.get(i);
		}

		return out;
	}

	public void highlightValidSoldierHexs(int player, int workerlevel)
	{
		Hex[] tohighlight = validSoldierHexs(player, workerlevel);
		// System.out.println(tohighlight.length);
		for (int i = 0; i < tohighlight.length; i++)
		{
			tohighlight[i].isHighlighted = true;
		}
	}

	public boolean placeItem(Hex placeHex, Item item, int playerturn)
	{
		// finds valid Hexs based on item type,
		// returns true if places successfully and false if not
		Hex[] validHexs = getSpecificHexs(playerturn + predefinedstatuses);

		if (item.getItemtype().equals("soldier"))
			validHexs = validSoldierHexs(playerturn, 0);

		// checks if placeHex is a valid Hex
		for (int i = 0; i < validHexs.length; i++)
		{
			if (validHexs[i].x == placeHex.x && validHexs[i].y == placeHex.y)
			{
				placeHex.setItem(item);
				placeHex.setStatus(playerturn + predefinedstatuses);

				System.out.println("placing " + item.toString() + " at x:" + placeHex.x + " y:" + placeHex.y);
				return true;
			}
		}

		System.out.println("unable to place: " + item.toString() + " at x:" + placeHex.x + " y:" + placeHex.y);
		return false;

	}

	public Hex[] getSpecificHexs(int targetStatus)
	{
		int totalHexs = 0;
		ArrayList<Hex> alout = new ArrayList<Hex>();

		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				if (world[i][j].getStatus() == targetStatus)
				{
					alout.add(world[i][j]);
					totalHexs++;
				}
			}
		}

		// System.out.println("total: " + totalHexs);
		Hex[] out = new Hex[totalHexs];

		for (int i = 0; i < alout.size(); i++)
		{
			out[i] = alout.get(i);
		}

		return out;
	}

	public Hex[] getAdjHexs(Hex inputHex)
	{
		int x = inputHex.x;
		int y = inputHex.y;
		Hex[] out = new Hex[6];

		// System.out.println("x: " + x);
		// System.out.println("y: " + y);

		if (y >= 2 && y <= world.length - 3)
		{
			if (!(world[y - 2][x] == null))
				out[2] = world[y - 2][x]; // up mid

			if (!(world[y + 2][x] == null))
				out[4] = world[y + 2][x]; // down mid
		}

		if (y >= 1 && x >= 1 && y < world.length - 2 && x < world[0].length - 2)
		{
			if (y % 2 == 0)
			{
				if (!(world[y + 1][x] == null))
					out[0] = world[y + 1][x]; // down left
				if (!(world[y + 1][x + 1] == null))
					out[3] = world[y + 1][x + 1]; // down right
				if (!(world[y - 1][x] == null))
					out[1] = world[y - 1][x]; // up left
				if (!(world[y - 1][x + 1] == null))
					out[5] = world[y - 1][x + 1]; // up right
			}
			else if (y % 2 == 1)
			{
				if (!(world[y + 1][x - 1] == null))
					out[0] = world[y + 1][x - 1]; // down left
				if (!(world[y + 1][x] == null))
					out[3] = world[y + 1][x]; // down right
				if (!(world[y - 1][x - 1] == null))
					out[1] = world[y - 1][x - 1]; // up left
				if (!(world[y - 1][x] == null))
					out[5] = world[y - 1][x]; // up right
			}
		}

		return out;
	}

	public Hex[] getValidAdjHexs(Hex inputHex)
	{
		List<Hex> alin = Arrays.asList(getAdjHexs(inputHex));
		ArrayList<Hex> alout = new ArrayList<Hex>();

		for (int i = 0; i < alin.size(); i++)
		{
			if (alin.get(i).getStatus() != 0)
				alout.add(alin.get(i));
		}

		Hex[] out = new Hex[alout.size()];
		for (int i = 0; i < alout.size(); i++)
		{
			out[i] = alout.get(i);
		}

		return out;
	}

	public Hex getRandomHex(int status)
	{
		ArrayList<Hex> validHexs = new ArrayList<Hex>();

		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				if (world[i][j].getStatus() == status)
					validHexs.add(world[i][j]);
			}
		}

		return validHexs.get(rand.nextInt(validHexs.size()));

	}

	public void makeLake(int x, int y, int iter)
	{
		world[y][x].setStatus(0);

		if (iter < worldSize * 2 + 2)
		{
			for (int i = 0; i < 2; i++)
			{
				int randHex = rand.nextInt(6);
				if (randHex == 1)
					makeLake(x, y - 1, iter + 1);
				if (randHex == 2)
					makeLake(x + 1, y - 1, iter + 1);
				if (randHex == 3)
					makeLake(x - 1, y, iter + 1);
				if (randHex == 4)
					makeLake(x + 1, y, iter + 1);
				if (randHex == 5)
					makeLake(x, y + 1, iter + 1);
				if (randHex == 6)
					makeLake(x + 1, y + 1, iter + 1);
			}
		}
	}

	public void updateWorld(Hex[][] newWorld)
	{
		world = newWorld;
	}

	public Hex[][] getWorld()
	{
		return world;
	}
}
