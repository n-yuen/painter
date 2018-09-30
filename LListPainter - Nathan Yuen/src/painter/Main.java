package painter;
import javax.swing.JFrame;

public class Main {

	// The program starts here
	public static void main( String[] args ){

		MainWindow app = new MainWindow("Painter - Nathan Yuen");
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setSize(700, 700);

		// Center the frame on the primary screen
		app.setLocationRelativeTo(null);
		app.setVisible(true);
	}
}
