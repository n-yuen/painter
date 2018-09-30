package painter;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.*;

/**
 * A Custom button that uses the Stamp as its icon
 */

public class Tool extends JButton{
	private Stamp stamp;

	public Tool( Stamp icon ){
		stamp = icon;

	}

	public void paintComponent( Graphics g ){
		super.paintComponent( g );
		if( stamp != null ){
			stamp.setLocation(getWidth()/4, getHeight()/4);
			stamp.setSize(getWidth()/2, getHeight()/2);
			stamp.render((Graphics2D) g);
		}
	}

	public Stamp getStamp(){
		return stamp;
	}
}
