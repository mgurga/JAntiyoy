import java.awt.Image;

public class Item
{

	private boolean ready = false;
	private String itemtype = ""; // could be 'tower', 'farm', 'soldier', 'townhall'
	private int itemlevel = 0; // 0-3

	public Item(String type, int level)
	{
		itemtype = type;
		itemlevel = level;
	}

	public Item(String itemname)
	{
		itemnameToItem(itemname);
	}

	public String toString()
	{
		return itemtype + itemlevel;
	}

	public String getItemtype()
	{
		return itemtype;
	}

	public int getItemlevel()
	{
		return itemlevel;
	}

	public int getPrice()
	{
		String itemName = this.toString();

		if (itemName == "soldier0")
			return PlayerTurn.itemPrice[0];
		if (itemName == "soldier1")
			return PlayerTurn.itemPrice[1];
		if (itemName == "soldier2")
			return PlayerTurn.itemPrice[2];
		if (itemName == "soldier3")
			return PlayerTurn.itemPrice[3];

		if (itemName == "tower0")
			return PlayerTurn.itemPrice[4];
		if (itemName == "tower1")
			return PlayerTurn.itemPrice[5];

		if (itemName == "farm")
			return PlayerTurn.itemPrice[6];

		return -1;
	}

	public Image getImage()
	{
		String itemName = this.toString();

		if (itemName == "soldier0")
			return JAntiyoy.soldiers[0];
		if (itemName == "soldier1")
			return JAntiyoy.soldiers[1];
		if (itemName == "soldier2")
			return JAntiyoy.soldiers[2];
		if (itemName == "soldier3")
			return JAntiyoy.soldiers[3];

		if (itemName == "tower0")
			return JAntiyoy.towers[0];
		if (itemName == "tower1")
			return JAntiyoy.towers[1];

		if (itemName.contains("farm"))
			return JAntiyoy.building[0];
		if (itemName.contains("townhall"))
			return JAntiyoy.building[3];

		return null;
	}

	public void itemnameToItem(String itemname)
	{
		if (itemname.contains("tower"))
		{
			itemtype = "tower";
		}
		if (itemname.contains("farm"))
		{
			itemtype = "farm";
		}
		if (itemname.contains("soldier"))
		{
			itemtype = "soldier";
		}
		if (itemname.contains("townhall"))
		{
			itemtype = "townhall";
		}

		if (itemname.contains("0"))
		{
			itemlevel = 0;
		}
		if (itemname.contains("1"))
		{
			itemlevel = 1;
		}
		if (itemname.contains("2"))
		{
			itemlevel = 2;
		}
		if (itemname.contains("3"))
		{
			itemlevel = 3;
		}
	}

}
