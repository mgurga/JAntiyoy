import java.awt.Color;

public class PlayerTurn
{

	// TURN COLORS:
	// 0 - RED
	// 1 - BLUE

	public Hex[][] world;
	public World worldGen;
	public final static Color[] teamColors =
	{ Color.pink, Color.cyan, Color.yellow, Color.green };
	public final static int[] itemPrice =
	{ 10, // soldier0
			20, // soldier1
			30, // soldier2
			40, // soldier3
			15, // tower0
			35, // tower1
			12, // farm + amount of already placed farms
	};
	public int[] teamMoney =
	{ 10, 10, 10, 10, 10, 10, 10, 10 };
	public int currentturn = 0;
	public int totalplayers = 2;

	public PlayerTurn(Hex[][] world, World worldGen)
	{
		this.world = world;
		this.worldGen = worldGen;
	}

	public void endTurn(World worldGen)
	{
		System.out.println("ended turn for player " + currentturn + " going to " + (currentturn + 1));
		setCurrentPlayerMoney(getCurrentPlayerMoney() + getCurrentPlayerEarnings());
		currentturn++;
		if (currentturn == totalplayers)
		{
			currentturn = 0;
		}
	}

	public int getPriceOfItemName(String itemName)
	{
		if (itemName == "soldier0")
			return itemPrice[0];
		if (itemName == "soldier1")
			return itemPrice[1];
		if (itemName == "soldier2")
			return itemPrice[2];
		if (itemName == "soldier3")
			return itemPrice[3];

		if (itemName == "tower0")
			return itemPrice[4];
		if (itemName == "tower1")
			return itemPrice[5];

		if (itemName == "farm")
			return itemPrice[6];

		return -1;
	}

	public void undo()
	{

	}

	public int getCurrentPlayerEarnings()
	{
		return worldGen.getSpecificHexs(currentturn + 2).length;
	}

	public int getCurrentPlayerMoney()
	{
		return teamMoney[currentturn];
	}
	
	public void setCurrentPlayerMoney(int newMoney) {
		teamMoney[currentturn] = newMoney;
	}
}
