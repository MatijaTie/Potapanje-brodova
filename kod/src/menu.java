import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class menu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public menu() {
		setLayout(null);
		
		JButton igraBtn = new JButton("Nova igra");
		igraBtn.setBounds(100, 80, 200, 65);
		igraBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Igra igraFrame = new Igra();
				igraFrame.setBackground(Color.white);
				mainFrame.mainPanel.add(igraFrame, "igra");
				mainFrame.mylayout.show(mainFrame.mainPanel, "igra");
				mainFrame.frame.setBounds(WindowSize.width / 2 - WindowSize.igraWidth / 2,
						WindowSize.height / 2 - WindowSize.igraHeight / 2, WindowSize.igraWidth, WindowSize.igraHeight);
			}
		});	
		add(igraBtn);

		JButton highScoreBtn = new JButton("High Score");
		highScoreBtn.setBounds(100, 170, 200, 65);
		highScoreBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HighScoreWindow hsFrame = new HighScoreWindow();
				hsFrame.setBackground(Color.WHITE);
				mainFrame.mainPanel.add(hsFrame, "highScoreWindow");
				mainFrame.mylayout.show(mainFrame.mainPanel, "highScoreWindow");
				mainFrame.frame.setBounds(WindowSize.width / 2 - WindowSize.menuWidth / 2,
						WindowSize.height / 2 - WindowSize.menuHeight / 2, WindowSize.menuWidth, WindowSize.menuHeight);
			}
		});
		add(highScoreBtn);

	}

}
