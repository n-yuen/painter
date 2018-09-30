package painter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

	private JButton reset;
	private DrawingPane drawing;

	/**
	 * Setup the main Window
	 * @param title The title of the window
	 */
	public MainWindow(String title){
		super(title);
		this.getContentPane().setBackground(Color.WHITE);
		JPanel toolbar = setupToolbar();
		drawing = new DrawingPane();
		setLayout(new BorderLayout());
		add(toolbar, BorderLayout.WEST);
		add(drawing, BorderLayout.CENTER);

		// Creates and positions the Reset button
		reset = new JButton("Reset");
		reset.addActionListener(new ResetListener());
		this.add(reset, BorderLayout.SOUTH);
	}

	public JPanel setupToolbar(){
		JPanel toolbar = new JPanel();
		toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.Y_AXIS));
		toolbar.setBackground(Color.WHITE);

		// The stamps to use for the buttons
		Stamp[] stamps = {new RectangleStamp(), new EllipseStamp(), new
				TriangleStamp()};

		toolbar.add(Box.createGlue());
		for(Stamp s : stamps){
			Tool t = new Tool(s);
			t.setPreferredSize(new Dimension(70,70));
			t.addActionListener(new BtnListener());
			toolbar.add(t);
		}
		toolbar.add(Box.createGlue());
		
		return toolbar;		
	}

	public class BtnListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e){
			Tool t = (Tool) e.getSource();		
			drawing.setSelected(t.getStamp().newStamp());
		}
	}

	/**
	 *  This class represents the reset command executed on pressing "Reset"
	 */
	public class ResetListener implements ActionListener {
		/**
		 *  Resets DrawingPane and clears LList of shapes
		 * 
		 *  @param e the reset button being pressed
		 */
		public void actionPerformed(ActionEvent e){
			drawing.reset();
			reset.setFocusable(false);
		}
	}

}
