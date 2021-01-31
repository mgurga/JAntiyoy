package core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Assets extends Canvas {
	public static Image[] building = {
			new ImageIcon(Assets.class.getClassLoader().getResource("buildings/farm0.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("buildings/farm1.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("buildings/farm2.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("buildings/townhall.png")).getImage() }; // farms
																												// and
																												// townhall
	public static Image[] soldiers = {
			new ImageIcon(Assets.class.getClassLoader().getResource("soldiers/soldier0.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("soldiers/soldier1.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("soldiers/soldier2.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("soldiers/soldier3.png")).getImage() }; // soldiers
	public static Image[] towers = {
			new ImageIcon(Assets.class.getClassLoader().getResource("towers/tower0.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("towers/tower1.png")).getImage() }; // towers
	public static Image[] menuicons = {
			new ImageIcon(Assets.class.getClassLoader().getResource("menuicons/undo.png")).getImage(),
			new ImageIcon(Assets.class.getClassLoader().getResource("menuicons/end_turn.png")).getImage() }; // undo and
																												// endturn
	public final static Color[] teamColors = { Color.pink, Color.cyan, Color.yellow, Color.green };
	public final static int[] itemPrice = { 10, // soldier0
			20, // soldier1
			30, // soldier2
			40, // soldier3
			15, // tower0
			35, // tower1
			12, // farm + amount of already placed farms
	};
}
