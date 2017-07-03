import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.Color;

public class mainFrame extends JFrame {

	menu menuFrame = new menu();

	public static JFrame frame = new JFrame();
	public static CardLayout mylayout = new CardLayout();
	public static JPanel mainPanel = new JPanel(mylayout);

	public mainFrame() {
		menuFrame.setBackground(Color.WHITE);

		mainPanel.add(menuFrame, "menu");

		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(WindowSize.width / 2 - WindowSize.menuWidth / 2,
				WindowSize.height / 2 - WindowSize.menuHeight / 2, WindowSize.menuWidth, WindowSize.menuHeight);
		frame.setResizable(false);
		frame.setVisible(true);
		mylayout.show(mainPanel, "menu");
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame mainFrame = new mainFrame();

			}
		});
	}

}
