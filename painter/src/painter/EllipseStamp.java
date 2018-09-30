package painter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

/**
 * This class represents the elliptical stamp.
 * 
 * @author nyuen, BradleyWu
 *
 */
public class EllipseStamp extends Stamp{
	private Ellipse2D shape;

	public EllipseStamp(){
		shape = new Ellipse2D.Double();
	}

	/**
	 * Draws a solid ellipse.
	 * @param g The canvas on which the ellipse will be drawn
	 */
	@Override
	public void render(Graphics2D g){
		Dimension d = getSize();
		shape.setFrame(new Point(getX(),
				getY()), new Dimension((int)d.getWidth(), (int)d.getHeight()));
		g.setColor(getColor());
		g.fill(shape);
	}

	@Override
	public Stamp newStamp(){
		return new EllipseStamp();
	}

	@Override
	public boolean contains(int x, int y){
		return shape.contains(x, y);
	}
}
