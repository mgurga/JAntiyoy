import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;

public class JAntiyoy implements MouseListener, MouseMotionListener, MouseWheelListener
{

	public boolean debug = false;
	public int WIDTH, HEIGHT;
	public int xoffset, yoffset, zoomoffset = 1;
	public int frame = 0;
	public int seconds = 0;
	public Graphics graphics;
	public World worldGen;
	public Hex[][] world;
	public Point2D[][] hexPoints;
	public JFrame jframe;
	public int mousepressx, mousepressy;
	public int mousedragx, mousedragy;
	public int selectedhexx, selectedhexy;
	public PlayerTurn pt;
	public boolean blink = false;
	public static Image[] building = new Image[4];
	public static Image[] soldiers = new Image[4];
	public static Image[] towers = new Image[2];
	public static Image[] menuicons = new Image[2];
	private String toolbarSide = "left";
	private int toolbarSize = 50;
	public Item selecteditem = new Item("");

	public JAntiyoy(int w, int h, JFrame frame)
	{
		// init
		this.WIDTH = w;
		this.HEIGHT = h;
		this.jframe = frame;

		jframe.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent comp)
			{
				resize(jframe.getWidth(), jframe.getHeight());
			}
		});

		worldGen = new World(1);
		worldGen.generateWorld();
		world = worldGen.getWorld();
		worldGen.makePlayerCamp(worldGen.getRandomHex(1).x, worldGen.getRandomHex(1).y, 3, 2); // red
		worldGen.makePlayerCamp(worldGen.getRandomHex(1).x, worldGen.getRandomHex(1).y, 3, 3); // blue
		// world = worldGen.cleanup();
		// System.out.println("done cleaning");
		hexPoints = new Point2D[world.length][world[0].length];

		pt = new PlayerTurn(world, worldGen);

		building[0] = new ImageIcon("res/buildings/farm0.png").getImage();
		building[1] = new ImageIcon("res/buildings/farm1.png").getImage();
		building[2] = new ImageIcon("res/buildings/farm2.png").getImage();
		building[3] = new ImageIcon("res/buildings/townhall.png").getImage();

		soldiers[0] = new ImageIcon("res/soldiers/soldier0.png").getImage();
		soldiers[1] = new ImageIcon("res/soldiers/soldier1.png").getImage();
		soldiers[2] = new ImageIcon("res/soldiers/soldier2.png").getImage();
		soldiers[3] = new ImageIcon("res/soldiers/soldier3.png").getImage();

		towers[0] = new ImageIcon("res/towers/tower0.png").getImage();
		towers[1] = new ImageIcon("res/towers/tower1.png").getImage();

		menuicons[0] = new ImageIcon("res/menuicons/undo.png").getImage();
		menuicons[1] = new ImageIcon("res/menuicons/end_turn.png").getImage();

	}

	public void tick(Graphics g)
	{
		// loop
		graphics = g;
		drawBackground(g, Color.blue);
		drawMap(g);
		drawUI(g);

		// worldGen.unhighlightAll();

		// System.out.println(blink);

		if (selecteditem.getItemtype().equals("end_turn"))
		{
			pt.endTurn();
			selecteditem = new Item("");
		}

		if (selecteditem.getItemtype().equals("undo"))
		{
			pt.undo();
			selecteditem = new Item("");
		}

		if (selecteditem.getItemtype().equals(""))
		{
			worldGen.unhighlightAll();
			worldGen.highlightPlayerHexs(pt.currentturn);
		}

		if (selecteditem.getItemtype().equals("soldier0"))
			worldGen.highlightValidSoldierHexs(pt.currentturn, 0);
		if (selecteditem.getItemtype().equals("soldier1"))
			worldGen.highlightValidSoldierHexs(pt.currentturn, 1);
		if (selecteditem.getItemtype().equals("soldier2"))
			worldGen.highlightValidSoldierHexs(pt.currentturn, 2);
		if (selecteditem.getItemtype().equals("soldier3"))
			worldGen.highlightValidSoldierHexs(pt.currentturn, 3);

		worldGen.highlightSpecificHexs(pt.currentturn + worldGen.predefinedstatuses);

		frame++;
	}

	public void drawUI(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		if (toolbarSide == "left")
		{
			// draw background
			g2.setColor(PlayerTurn.teamColors[pt.currentturn]);
			g2.fillRect(0, 0, toolbarSize, HEIGHT);

			// draw line
			g2.setColor(Color.black);
			g2.setStroke(new BasicStroke(1));
			g2.drawLine(toolbarSize, 0, toolbarSize, HEIGHT);

			// draw money and earnings
			Font oldFont = g2.getFont();
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
			g2.setColor(Color.black);
			g2.drawString("$" + pt.getCurrentPlayerMoney(),
					WIDTH - (g2.getFontMetrics().stringWidth("$" + pt.getCurrentPlayerMoney()) + 16),
					g2.getFontMetrics().getHeight());
			g2.drawString("+" + pt.getCurrentPlayerEarnings(),
					WIDTH - ("+" + pt.getCurrentPlayerEarnings()).length() * g2.getFont().getSize(),
					g2.getFontMetrics().getHeight() * 2);

			// draw soldier, farm, and tower icons
			// soldiers
			g2.drawImage(soldiers[0], 0, 0, toolbarSize, toolbarSize, null);
			g2.drawImage(soldiers[1], 0, toolbarSize, toolbarSize, toolbarSize, null);
			g2.drawImage(soldiers[2], 0, toolbarSize * 2, toolbarSize, toolbarSize, null);
			g2.drawImage(soldiers[3], 0, toolbarSize * 3, toolbarSize, toolbarSize, null);

			// towers
			g2.drawImage(towers[0], 0, toolbarSize * 5, toolbarSize, toolbarSize, null);
			g2.drawImage(towers[1], 0, toolbarSize * 6, toolbarSize, toolbarSize, null);

			// farm
			g2.drawImage(building[0], 0, toolbarSize * 7, toolbarSize, toolbarSize, null);

			// menu icons
			g2.drawImage(menuicons[1], 0, HEIGHT - toolbarSize * 2, toolbarSize, toolbarSize, null);
			g2.drawImage(menuicons[0], 0, HEIGHT - toolbarSize * 3, toolbarSize, toolbarSize, null);

			// show item pic and price
			if (!selecteditem.getItemtype().equals(""))
			{
				g2.setFont(new Font(Font.MONOSPACED, Font.BOLD, 40));
				g2.drawImage(selecteditem.getImage(), toolbarSize, (HEIGHT / 2) - toolbarSize * 2, toolbarSize * 2,
						toolbarSize * 2, null);
				g2.drawString("$" + selecteditem.getPrice(), (int) (toolbarSize * 3),
						(HEIGHT / 2) - (toolbarSize * 2 / 3));
				g2.setFont(oldFont);
			}
		}
	}

	public void drawMap(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				double hexoffset = 0 * zoomoffset;
				double xgap = 30 * zoomoffset;
				double ygap = 8.5 * zoomoffset;
				double radius = 10 * zoomoffset;
				double imgsize = 20 * zoomoffset;

				if (i % 2 == 0)
				{
					hexoffset = xgap / 2;
					// g.setColor(Color.green);
				} else
				{
					hexoffset = 0;
					// g.setColor(Color.red);
				}

				if (world[i][j].getStatus() != 0 || debug)
				{
					double drawx = j * xgap + hexoffset + xoffset;
					double drawy = i * ygap + yoffset;
					Polygon hex = world[i][j].getPolygon(drawx, drawy, radius);
					g.setColor(world[i][j].getColor());
					g.fillPolygon(hex);
					g.setColor(Color.black);
					g2.setStroke(new BasicStroke(1));

					g2.drawPolygon(hex);
					// System.out.println(world[i][j].getItem().toString());
					g2.drawImage(world[i][j].getItem().getImage(), (int) (drawx - radius), (int) (drawy - radius),
							(int) imgsize, (int) imgsize, null);
					// System.out.println("occupied by: " + world[i][j].getOccupation());
					if (debug)
					{
						// g.drawString(world[i][j].getCleaned() + "", (int)drawx, (int)drawy);
						g.drawString("x: " + j + "", (int) drawx, (int) drawy + 10);
						g.drawString("y: " + i + "", (int) drawx, (int) drawy + 20);
						try
						{
							// g.drawLine((int)drawx,(int) drawy,(int) (world[i][j].getCleanedBy().x*xgap +
							// hexoffset + xoffset),(int) (world[i][j].getCleanedBy().y*ygap + yoffset -
							// 10));
						} catch (Exception e)
						{
							// TODO: handle exception
						}
					}
				}
				hexPoints[i][j] = new Point2D.Double(j * xgap + hexoffset, i * ygap);
				if (debug)
					g.fillOval((int) hexPoints[i][j].getX() + xoffset, (int) hexPoints[i][j].getY() + yoffset, 5, 5);
			}
		}
		for (int i = 0; i < world.length; i++)
		{
			for (int j = 0; j < world[0].length; j++)
			{
				double hexoffset = 0 * zoomoffset;
				double xgap = 30 * zoomoffset;
				double ygap = 8.5 * zoomoffset;
				double radius = 10 * zoomoffset;
				if (i % 2 == 0)
				{
					hexoffset = xgap / 2;
					// g.setColor(Color.green);
				} else
				{
					hexoffset = 0;
					// g.setColor(Color.red);
				}
				if (world[i][j].getStatus() != 0 || debug)
				{
					double drawx = j * xgap + hexoffset + xoffset;
					double drawy = i * ygap + yoffset;
					Polygon hex = world[i][j].getPolygon(drawx, drawy, radius);
					g.setColor(world[i][j].getColor());
					// g.fillPolygon(hex);
					g.setColor(Color.black);
					if (world[i][j].isHighlighted && blink)
					{
						g2.setStroke(new BasicStroke(3 + (zoomoffset)));
						g2.drawPolygon(hex);
					} else
					{
						g2.setStroke(new BasicStroke(1));
					}
					// g2.drawPolygon(hex);
				}
			}
		}
	}

	public void drawBackground(Graphics g, Color c)
	{
		Color oldColor = g.getColor();
		g.setColor(c);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(oldColor);
	}

	public void resize(int x, int y)
	{
		WIDTH = x;
		HEIGHT = y;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// System.out.println("mouse clicked");
		if (toolbarSide.equals("left"))
		{
			if (e.getX() > toolbarSize)
			{
				// clicked on world view
				int hexx = -1, hexy = -1;
				double closestdist = 500;
				for (int i = 0; i < world.length; i++)
				{
					for (int j = 0; j < world[0].length; j++)
					{
						double mousedist = hexPoints[i][j].distance(e.getX() - xoffset, e.getY() - yoffset);
						if (mousedist < closestdist && world[i][j].getStatus() != 0)
						{
							closestdist = mousedist;
							hexx = i;
							hexy = j;
						}
					}
				}

				if (!selecteditem.getItemtype().equals(""))
				{
					worldGen.placeItem(world[hexx][hexy], selecteditem, pt.currentturn);
				}

				selecteditem = new Item("");

//				Hex[] adjs = worldGen.getAdjHexs(new Hex(1, hexy, hexx));
//				for(int i = 0; i < adjs.length; i++) {
//					adjs[i].isHighlighted = true;
//				}

			} else
			{
				// clicked on menu

				if (e.getY() < toolbarSize)
				{
					// first button - soldier0
					if (debug)
						System.out.println("clicked soldier0");

					selecteditem = new Item("soldier", 0);
				}
				if (e.getY() < toolbarSize * 2 && e.getY() > toolbarSize)
				{
					// second button - soldier1
					if (debug)
						System.out.println("clicked soldier1");

					selecteditem = new Item("soldier", 1);
				}
				if (e.getY() < toolbarSize * 3 && e.getY() > toolbarSize * 2)
				{
					// third button - soldier2
					if (debug)
						System.out.println("clicked soldier2");

					selecteditem = new Item("soldier", 2);
				}
				if (e.getY() < toolbarSize * 4 && e.getY() > toolbarSize * 3)
				{
					// fourth button - soldier3
					if (debug)
						System.out.println("clicked soldier3");

					selecteditem = new Item("soldier", 3);
				}

				if (e.getY() < toolbarSize * 6 && e.getY() > toolbarSize * 5)
				{
					// fourth button - tower0
					if (debug)
						System.out.println("clicked tower0");

					selecteditem = new Item("tower", 0);
				}
				if (e.getY() < toolbarSize * 7 && e.getY() > toolbarSize * 6)
				{
					// fourth button - tower1
					if (debug)
						System.out.println("clicked tower1");

					selecteditem = new Item("tower", 1);
				}
				if (e.getY() < toolbarSize * 8 && e.getY() > toolbarSize * 7)
				{
					// fourth button - farm
					if (debug)
						System.out.println("clicked farm");

					selecteditem = new Item("tower", 0);
				}

				if (e.getY() < HEIGHT - toolbarSize * 2 && e.getY() > HEIGHT - toolbarSize * 3)
				{
					// fifth button - undo
					if (debug)
						System.out.println("clicked undo");

					selecteditem = new Item("undo");
				}
				if (e.getY() < HEIGHT - toolbarSize && e.getY() > HEIGHT - toolbarSize * 2)
				{
					// sixth button - end turn
					if (debug)
						System.out.println("clicked end turn");

					selecteditem = new Item("end_turn");
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
		// System.out.println("Mouse Pressed");
		// if(e.getX() > toolbarSize && toolbarSide.equals("left")) {
		mousedragx = e.getX();
		mousedragy = e.getY();
		// }
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// System.out.println("mouse dragged " + e.getX() + ", " + e.getY());

		// System.out.println("xdiff: " + (e.getX() - mousedragx));
		// System.out.println("ydiff: " + (e.getY() - mousedragy));

		if (e.getX() > toolbarSize && toolbarSide.equals("left"))
		{
			if (e.getX() - mousedragx < 20)
			{
				xoffset += e.getX() - mousedragx;
				yoffset += e.getY() - mousedragy;
			}

			mousedragx = e.getX();
			mousedragy = e.getY();
		}
		// System.out.println("idle: " + e.getX());

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		// TODO Auto-generated method stub
		zoomoffset -= e.getUnitsToScroll() / 3;
		xoffset += e.getUnitsToScroll() * 50;
		yoffset += e.getUnitsToScroll() * 50;
		if (zoomoffset > 10)
		{
			zoomoffset = 10;
		}
		if (zoomoffset < 1)
		{
			zoomoffset = 1;
		}

	}
}
