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
		System.out.println(itemName);
		
		if(itemtype.equals("soldier")) {
			return PlayerTurn.itemPrice[itemlevel];
		}
		
		if(itemtype.equals("tower")) {
			return PlayerTurn.itemPrice[itemlevel+3];
		}

		if(itemtype.equals("farm"))
			return PlayerTurn.itemPrice[6];

		return -1;
	}

	public Image getImage()
	{

		if (itemtype.equals("soldier"))
			return JAntiyoy.soldiers[itemlevel];

		if (itemtype.equals("tower"))
			return JAntiyoy.towers[itemlevel];

		if (itemtype.equals("farm"))
			return JAntiyoy.building[0];
		
		if (itemtype.equals("townhall"))
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
