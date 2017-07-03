import java.awt.Dimension;
import java.awt.Toolkit;

public class WindowSize {

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public static int width = (int) screenSize.getWidth();
	public static int height = (int) screenSize.getHeight();

	public static int igraWidth = 1240;
	public static int igraHeight = 850;

	public static int menuWidth = 400;
	public static int menuHeight = 450;
}
