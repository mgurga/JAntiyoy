import java.awt.Color;
import java.awt.Polygon;

class Hex implements Comparable<Hex>
{

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
	// (mostly handled by the Item class now)

	private int status;
	private Item item = new Item("");
	public boolean cleaned = false; // cleaning variables used during world create and debug
	private Hex cleanedBy;
	public int x, y;
	public boolean isHighlighted = false;

	public Hex(int status, int x, int y)
	{
		this.status = status;
		this.x = x;
		this.y = y;
	}

	public int getStatus()
	{
		return status;
	}

	public boolean getCleaned()
	{
		return cleaned;
	}

	public Hex getCleanedBy()
	{
		return cleanedBy;
	}

	public void setClean(boolean newCleaned)
	{
		cleaned = newCleaned;
	}

	public void setCleanedBy(Hex cleaner)
	{
		cleanedBy = cleaner;
	}

	public Color getColor()
	{
		// gets colors from PlayerTurn.java

		if (status == 0)
		{
			return Color.blue;
		}
		if (status == 1)
		{
			return Color.lightGray;
		}
		if (status == 2)
		{
			return PlayerTurn.teamColors[0];
		}
		if (status == 3)
		{
			return PlayerTurn.teamColors[1];
		}
		if (status == 4)
		{
			return PlayerTurn.teamColors[2];
		}
		if (status == 5)
		{
			return PlayerTurn.teamColors[3];
		}
		return null;
	}

	public void setItem(Item newItem)
	{
		item = newItem;
	}

	public Item getItem()
	{
		return item;
	}

	public void setStatus(int newStatus)
	{
		this.status = newStatus;
	}

	public Polygon getPolygon(double x, double y, double rad)
	{
		// makes Polygon shape to draw
		Polygon polygon = new Polygon();
		for (int i = 0; i < 6; i++)
		{
			int xval = (int) (x + rad * Math.cos(i * 2 * Math.PI / 6D));
			int yval = (int) (y + rad * Math.sin(i * 2 * Math.PI / 6D));
			polygon.addPoint(xval, yval);
		}
		// g.drawPolygon(polygon);
		return polygon;
	}

	@Override
	public int compareTo(Hex o)
	{
		// compares with other hex to see if 
		// they are owned by different people
		if (status != o.status)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

}