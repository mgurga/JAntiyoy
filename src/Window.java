
import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas {
	private JFrame frame;

	public Window(int width, int height, String title, Game ay) {
		frame = new JFrame(title);

		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.add(ay);
		frame.setVisible(true);

		ay.start();
	}

	public JFrame getFrame() {
		return frame;
	}

}
