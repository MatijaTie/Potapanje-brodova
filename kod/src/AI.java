
import java.util.Vector;

public class AI {

	Vector<PoljeXY> poljeXY = new Vector<>();
	Vector<PoljeXY> zadnjiXY = new Vector<>();

	public boolean poljePostoji(int x, int y) {
		boolean flagX = false;
		boolean flagY = false;
		for (int i = 0; i < poljeXY.size(); i++) {
			if (poljeXY.elementAt(i).x == x) {
				flagX = true;
			}
		}
		for (int i = 0; i < poljeXY.size(); i++) {
			if (poljeXY.elementAt(i).y == y) {
				flagY = true;
			}
		}
		if (flagX && flagY) {
			return true;
		} else {
			return false;
		}
	}

	public void ukloni(int x, int y) {
		for (int i = 0; i < poljeXY.size(); i++) {
			if (x == poljeXY.elementAt(i).x && y == poljeXY.elementAt(i).y) {
				poljeXY.removeElementAt(i);
				break;
			}
		}
	}

}
