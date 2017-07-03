import java.util.HashMap;

import javax.swing.JToggleButton;

public class Brod {
	// HashMap<String, JToggleButton> brodHM = new
	// HashMap<String,JToggleButton>();
	int count = 0;
	int velicina;
	JToggleButton[] brod;

	public Brod(int velicina) {
		brod = new JToggleButton[velicina];
		this.velicina = velicina;
	}

	public void addDio(JToggleButton btn) {
		if (count < brod.length) {
			brod[count] = btn;
			// brodHM.put(btn.getName(), btn);
			count++;
			System.out.println(btn.getName());
		}
	}

	public boolean checkIfDestroyed() {
		boolean unisten = true;
		System.out.println("Svi dijelovi broda:");
		for (int t = 0; t < velicina; t++) {
			System.out.println(brod[t].getName());
			// if(brod[i].isSelected() || brod[i].isEnabled()){
			if (brod[t].isEnabled()) {
				unisten = false;
			}
		}
		if (unisten) {
			return true;
		} else {

			return false;
		}
	}

	public String getPartName(int mjesto) {
		return brod[mjesto].getName();
	}

}
