import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class HighScoreWindow extends JPanel {

	public HighScoreWindow() {
		setLayout(null);

		JTextPane textPane = new JTextPane();
		textPane.setBounds(80, 50, 245, 240);
		HighScoreManager hsm = new HighScoreManager();
		String highScore = hsm.getHighscoreString();
		textPane.setText(highScore);
		add(textPane);

		JLabel hsPlace = new JLabel("High Score");
		hsPlace.setBounds(138, 10, 200, 30);
		hsPlace.setFont(new Font("Serif", Font.BOLD, 23));
		add(hsPlace);

		JButton backToMenu = new JButton("Povratak na menu");
		backToMenu.setBounds(102, 330, 200, 40);
		backToMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.mylayout.show(mainFrame.mainPanel, "menu");
			}

		});
		add(backToMenu);

	}
}
