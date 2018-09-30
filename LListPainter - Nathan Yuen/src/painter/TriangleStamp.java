package painter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * This class represents the triangular stamp.
 * 
 * @author nyuen, BradleyWu
 *
 */
public class TriangleStamp extends Stamp{
	private Polygon shape;

	public TriangleStamp(){
		shape = new Polygon();
	}
	
	/**
	 * Draws a solid triangle based on the direction in which the mouse is
	 * dragged.
	 * 
	 * @param g The canvas on which the triangle will be drawn
	 */
	@Override
	public void render(Graphics2D g){
		shape.reset();
		Dimension d = getSize();
		int x = (int)getX();
		int y = (int)getY();
		int w = (int)d.getWidth();
		int h = (int)d.getHeight();

		// If mouse is moved upward, the triangle will be "pointed" upward
		if (invertedY()){
			shape.addPoint(x+w/2, y);
			shape.addPoint(x, y+h);
			shape.addPoint(x+w, y+h);
		}

		// Otherwise, the triangle will be "pointed" downward
		else{
			shape.addPoint(x, y);
			shape.addPoint(x+w, y);
			shape.addPoint(x+w/2, y+h);
		}
		g.setColor(getColor());
		g.fill(shape);
	}

	@Override
	public Stamp newStamp(){
		return new TriangleStamp();
	}

	@Override
	public boolean contains(int x, int y){
		return shape.contains(x, y);
	}
}
