package core;

import java.awt.Image;

public class Item implements Comparable<Item> {
	// this object holds the item type, level, and if it is ready
	//
	// this is a replacement for string based changes, but still
	// supports these legacy changes if needed

	public boolean isReady = false; // whether an item is ready to be used
	private String itemtype = ""; // could be 'tower', 'farm', 'soldier', 'townhall'
	private int itemlevel = 0; // 0-3

	// can initalize with nothing, old itemname, or type and level

	public Item(String type, int level) {
		itemtype = type;
		itemlevel = level;
	}

	public Item(String itemname) {
		itemnameToItem(itemname);
	}

	public Item() {
		itemnameToItem("");
	}

	public String toString() {
		if(itemtype.equals("")) {
			return "";
		}
		return itemtype + itemlevel;
	}
	
	public boolean isEmpty() {
		if(itemtype.equals("")) {
			return true;
		}
		return false;
	}
	
	public String getItemtype() {
		return itemtype;
	}

	public int getItemlevel() {
		return itemlevel;
	}

	public int getPrice() {
		// price based on values from PlayerTurn.java

		if (itemtype.equals("soldier")) {
			return Assets.itemPrice[itemlevel];
		}

		if (itemtype.equals("tower")) {
			return Assets.itemPrice[itemlevel + 4];
		}

		if (itemtype.equals("farm"))
			return Assets.itemPrice[6];

		return -1;
	}

	public Image getImage() {
		// images defined in JAntiyoy

		if (itemtype.equals("soldier"))
			return Assets.soldiers[itemlevel];

		if (itemtype.equals("tower"))
			return Assets.towers[itemlevel];

		if (itemtype.equals("farm"))
			return Assets.building[0];

		if (itemtype.equals("townhall"))
			return Assets.building[3];

		return null;
	}

	public void itemnameToItem(String itemname) {
		// converts old itemnames to new Items,
		// useful for temporary fixes

		if (itemname.contains("tower")) {
			itemtype = "tower";
		}
		if (itemname.contains("farm")) {
			itemtype = "farm";
		}
		if (itemname.contains("soldier")) {
			itemtype = "soldier";
		}
		if (itemname.contains("townhall")) {
			itemtype = "townhall";
		}
		if (itemname.contains("end_turn")) {
			itemtype = "end_turn";
		}
		if (itemname.contains("undo")) {
			itemtype = "undo";
		}

		if (itemname.contains("0")) {
			itemlevel = 0;
		}
		if (itemname.contains("1")) {
			itemlevel = 1;
		}
		if (itemname.contains("2")) {
			itemlevel = 2;
		}
		if (itemname.contains("3")) {
			itemlevel = 3;
		}
	}

	public int compareTo(Item o) {
		// compares similarities when fighting
		int totaldiffs = 0;

		if (!itemtype.equals(o.getItemtype())) {
			totaldiffs++;
		}
		if (itemlevel != o.getItemlevel()) {
			totaldiffs++;
		}
		if (isReady != o.isReady) {
			totaldiffs++;
		}

		return totaldiffs;
	}

}
