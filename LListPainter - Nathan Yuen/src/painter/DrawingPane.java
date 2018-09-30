package painter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

import java.util.Iterator;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.omg.CORBA.Current;

public class DrawingPane extends JPanel {

	private Stamp selected;
	private LList<Stamp> shapes;
	private Random rand;
	private Mouser mouser;
	private boolean strobe;
	private boolean isSelected;

	public DrawingPane(){
		setBackground(Color.WHITE);
		isSelected = true;
		strobe = false;

		// Creates LList of stamps to save the stamps the user has drawn
		shapes = new LList<Stamp>();

		// Sets default Stamp shape to rectangle
		selected = new RectangleStamp();

		rand = new Random();
		mouser = new Mouser();
		this.addMouseListener(mouser);
		this.addMouseMotionListener(mouser);

		// Sets up keybinding
		InputMap i = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_0, 0, true), 0);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0), 1);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true), 2);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, 0, true), 3);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), 4);
		i.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), 5);
		ActionMap a = this.getActionMap();

		// When the "0" key is pressed, the DrawingPane will be reset
		a.put(0, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e){
				reset();
			}
		});

		/*
		 * When the backspace key is pressed, the "topmost" shape will be
		 * removed
		 */
		a.put(1, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e){
				if (!shapes.isEmpty()){
					shapes.remove(shapes.size()-1);
					/*
					 *  This ensures that selected is not changed to null
					 *  if the backspace key is pressed while a shape is 
					 *  being drawn
					 */

					if (mouser.isSelected){
						Stamp current = selected.newStamp();
						selected = null;
						repaint();
						selected = current;
					}else{
						repaint();
					}
				}
			}
		});

		// When the enter key is pressed, all the shapes will change color
		a.put(2, new AbstractAction() { 
			@Override
			public void actionPerformed(ActionEvent e){
				strobe();
				repaint();
			}
		});

		// For people without epilepsy. If this function is enabled, colors will change when shapes are dragged
		/*a.put(3, new AbstractAction() { 
			@Override
			public void actionPerformed(ActionEvent e){
				if (strobe){
					strobe = false;
				}else{
					strobe = true;
				}
			}
		});*/
		
		a.put(4, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e){
				shapes.reversIterator();
			}
		});
	}

	public boolean setSelected(Stamp toUse){
		if(toUse != null){
			this.selected = toUse;
			return true;
		}
		return false;
	}

	public Stamp getSelected(){
		return selected;
	}

	/**
	 * Clears DrawingPane and LList of stamps
	 */
	public void reset(){
		Stamp current = selected.newStamp();
		shapes.clear();
		selected = null;
		repaint();
		selected = current;
	}

	/** 
	 * Paints all the shapes stored in the LList
	 * Paints the current shape
	 */
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		for (Stamp s:shapes){
			s.render(g2);
		}
		if(!mouser.isSelected){
			selected.render(g2);
		}
	}

	public void strobe(){
		for (Stamp s:shapes){
			s.setColor(new Color(rand.nextInt(255),
					rand.nextInt(255), rand.nextInt(255)));
		}
		selected.setColor(new Color(rand.nextInt(255),
				rand.nextInt(255), rand.nextInt(255)));
		repaint();
	}

	private class Mouser implements MouseListener, MouseMotionListener {

		/* 
		 *  This variable keeps track of whether or not the Stamp
		 *  selected has been added to the LList.
		 */
		public boolean isSelected;
		private int anchorX, anchorY;

		@Override
		public void mouseClicked(MouseEvent e){
			int x = e.getX();
			int y = e.getY();
			Stamp next;

			/*
			 *  Starting from the end of the list, searches for the first
			 *  Stamp that contains the clicked location, then removes it
			 *  and appends it to the end of the list
			 */
			Iterator<Stamp> revers = shapes.reversIterator();
			while (revers.hasNext()){
				next = revers.next();
				if (next.contains(x, y) && shapes.size() != 1){
					revers.remove();
					shapes.add(next);
					Stamp current = selected.newStamp();
					selected = null;
					repaint();
					selected = current;
					return;
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e){
			isSelected = false;

			// Saves original point 
			anchorX = e.getX();
			anchorY = e.getY();

			// Creates copy of the Stamp being currently made
			Stamp current = selected;
			if(current == null){
				return;
			}
			current = current.newStamp();

			// Randomizes color
			current.setColor(new Color(rand.nextInt(255),
					rand.nextInt(255), rand.nextInt(255)));

			setSelected(current);
		}

		@Override
		public void mouseReleased(MouseEvent e){
			setBackground(Color.WHITE);
			isSelected = true;

			int x = e.getX();
			int y = e.getY();

			// Does not draw a new Stamp if the mouse is moved only slightly
			if (Math.sqrt(Math.pow(anchorX - x, 2) +
					Math.pow(anchorY - y, 2)) > 5){

				// Makes another copy of the Stamp about to be made
				Stamp current = selected;
				if(current == null){
					return;
				}

				/*
				 *  Defaults the Stamp's top left corner to the point at 
				 *  which the mouse was first clicked
				 */
				float locationX = anchorX;
				float locationY = anchorY;

				/*
				 *  Sets dimensions of Stamp as the distances between start
				 *  and end points
				 */
				current.setSize(e.getX()-anchorX, e.getY()-anchorY);

				// Finds and sets the top left corner of the Stamp
				if (current.invertedX()){
					locationX = e.getX();
				}
				if (current.invertedY()){
					locationY = e.getY();
				}
				current.setLocation(locationX, locationY);

				shapes.add(current);
				paintComponent(getGraphics());
			}

			/*
			 *  Slight movement of the mouse between press and release will 
			 *  be considered a mouse click
			 */
			else if (x != anchorX || y != anchorY){
				mouseClicked(e);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e){

		}

		@Override
		public void mouseExited(MouseEvent e){

		}

		@Override
		public void mouseDragged(MouseEvent e){

			// Makes another copy of the Stamp about to be made
			Stamp current = selected;
			if(current == null){
				return;
			}

			/*
			 *  Defaults the Stamp's top left corner to the point at which
			 *  the mouse was first clicked
			 */
			float locationX = anchorX;
			float locationY = anchorY;

			/*
			 *  Sets dimensions of Stamp as the distances between start and
			 *  end points
			 */
			current.setSize(e.getX()-anchorX, e.getY()-anchorY);

			// Finds and sets the top left corner of the Stamp
			if (current.invertedX()){
				locationX = e.getX();
			}

			if (current.invertedY()){
				locationY = e.getY();
			}
			current.setLocation(locationX, locationY);

			setSelected(current);
			paintComponent(getGraphics());

			if (strobe){
				strobe();
				setBackground(new Color(rand.nextInt(255),
						rand.nextInt(255), rand.nextInt(255)));
			}
			repaint();

		}

		@Override
		public void mouseMoved(MouseEvent e){
		}
	}
}
