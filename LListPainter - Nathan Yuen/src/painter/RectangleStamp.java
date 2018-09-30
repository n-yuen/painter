package painter;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * This class represents the rectangular stamp.
 * 
 * @author nyuen, BradleyWu
 *
 */

public class RectangleStamp extends Stamp {
	private Rectangle shape;
	
	public RectangleStamp(){
		shape = new Rectangle();
	}
	
	/**
	 * Draws a solid rectangle
	 * @param g The canvas on which the ellipse will be drawn
	 */
	@Override
	public void render(Graphics2D g){
		Dimension d = getSize();
		shape.setBounds(getX(), getY(),
				(int)d.getWidth(), (int)d.getHeight());
		g.setColor(getColor());
		g.fill(shape);
	}

	@Override
	public Stamp newStamp(){
		return new RectangleStamp();
	}

	@Override
	public boolean contains(int x, int y){
		return shape.contains(x, y);
	}
}
