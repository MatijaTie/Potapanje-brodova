import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import java.awt.Label;

public class Igra extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int brojPostavljenihBrodova = 0; 						// brojac pri postavljanju brodova
	private int brojProtivnickihBrodova = 0; 						// brojac pri postavljanju protivnickih brodova
	private int mojiUnisteniBrodovi = 0; 							// brojac mojih unistenih brodova za trigger kraja
	private int protivnickiUnisteniBrodovi = 0; 					// brojac protivnickih unistenih brodova za trigger kraja
	private int brojPucanja = 0; 									// brojac odigranih poteza za high score
	private boolean prvi = false; 									// flag za odlucivanje protivnika kada pogodi prvi dio broda
	JToggleButton[][] mojaPolja = new JToggleButton[10][10]; 		// moja polja predstavljena toggle buttonima
	JToggleButton[][] protivnikPolja = new JToggleButton[10][10];   // protivnicka polja predstavljena toggle buttonima
	JToggleButton okreniBtn = new JToggleButton("Okreni brod"); 	// button za okretanje broda pri pocetnom postavljanju
	Vector<Brod> mojiBrodovi = new Vector<>(); 						// vektor u koji spremam moje brodove
	Vector<Brod> protivnikBrodovi = new Vector<>(); 				// vektor u koji se spremaju protivnicki brodovi
	AI ai = new AI(); 												// pomocna klasa za odlucivanje kako ce protivnik odigrati
	Label score = new Label(String.valueOf(brojPucanja)); 			// Label za score
	JTextPane text = new JTextPane(); 								// Text pane za prikaz odigranoga igracu
	JScrollPane jsp = new JScrollPane(text); 						// Scroll panel u koji se posalje textPane
	JButton izlaz = new JButton("X"); 								// Button za izlaz
	JLabel background = new JLabel();

	public Igra() {

		postaviDijelove();
		postaviOznake();
		napraviMojeBrodove();
		postaviBrodoveListener(mojiBrodovi);
		napraviProtivnickeBrodove();
		postaviProtivnickeBrodove();

	}

	/**
	 * 
	 * Postavljanje pocetnik komponenata
	 */
	public void postaviDijelove() {
		/*
		 * background.setBounds(0, 0, WindowSize.igraWidth,
		 * WindowSize.igraHeight); java.net.URL imgUrl =
		 * getClass().getResource("img/background.png"); ImageItIcon(icon);
		 * add(background); ImageIcon icon = new ImageIcon(imgUrl); background
		 */
		setLayout(null);
		score.setBounds(600, 20, 50, 24);
		score.setFont(new Font("Serif", Font.BOLD, 25));
		add(score);

		addMouseListener(this);
		okreniBtn.setBounds(26, 666, 137, 25);
		okreniBtn.setVisible(false);
		add(okreniBtn);

		text.setBounds(425, 610, 350, 180);
		text.setEditable(false);
		text.setFont(new Font("Serif", Font.BOLD, 20));
		text.setText("Postavite brodove\n");

		jsp.setBounds(425, 610, 350, 180);
		jsp.setVisible(true);
		add(jsp);

		izlaz.setBounds(1185, 0, 44, 44);
		izlaz.setVisible(true);
		izlaz.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ocistiSve();
			}
		});
		add(izlaz);

		initiate();
	}
	
	public void postaviOznake(){
		int yOS = 15;
		for(int i = 0 ; i < 10 ; i++){
			JLabel pozicijaLabel = new JLabel();
			pozicijaLabel.setBounds(45 + i * 55, yOS, 55, 55);
			pozicijaLabel.setText(String.valueOf(i+1));
			add(pozicijaLabel);
			
			JLabel pozicijaLabel2 = new JLabel();
			pozicijaLabel2.setBounds(670 + i * 55, yOS, 55, 55);
			pozicijaLabel2.setText(String.valueOf(i+1));
			add(pozicijaLabel2);
		}
		
		for(int j = 0 ; j < 10 ; j++){
			JLabel pozicijaLabel = new JLabel();
			pozicijaLabel.setBounds(8 ,55 + 55 * j, 55, 55);
			char s = (char)(65+j);
			pozicijaLabel.setText(String.valueOf(s));
			add(pozicijaLabel);
			
			JLabel pozicijaLabel2 = new JLabel();
			pozicijaLabel2.setBounds(1208 , 55 + 55*j , 55, 55);
			pozicijaLabel2.setText(String.valueOf(s));
			add(pozicijaLabel2);
		}
	}

	/**
	 * Funkcija koja stvara brodove
	 */
	public void initiate() {

		/**
		 * Moji brodovi
		 */
		int yOS = 55;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				String naziv = Character.toString((char) (i + 65)) + (j + 1);
				mojaPolja[i][j] = new JToggleButton(naziv);
				mojaPolja[i][j].setName(naziv);

				mojaPolja[i][j].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				mojaPolja[i][j].setBorderPainted(false);
				mojaPolja[i][j].setMargin(new Insets(0, 0, 0, 0));

				java.net.URL imgUrl = getClass().getResource("img/qwe.png");
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				mojaPolja[i][j].setIcon(icon);

				java.net.URL imgUrl2 = getClass().getResource("img/qwex.png");
				ImageIcon icon2 = null;
				icon2 = new ImageIcon(imgUrl2);

				mojaPolja[i][j].setDisabledIcon(icon2);
				mojaPolja[i][j].setText("");
				mojaPolja[i][j].setToolTipText(naziv);

				Color c = new Color(37, 149, 196);
				mojaPolja[i][j].setBackground(c);
				mojaPolja[i][j].setForeground(Color.white);
				mojaPolja[i][j].setBounds(25 + j * 55, yOS, 55, 55);
				add(mojaPolja[i][j]);

			}
			yOS += 55;
		}

		/**
		 * Protivnicki brodovi
		 */
		int y2OS = 55;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				String naziv = Character.toString((char) (i + 65)) + (j + 1);
				protivnikPolja[i][j] = new JToggleButton(naziv);
				protivnikPolja[i][j].setName(naziv);
				protivnikPolja[i][j].setBounds(650 + j * 55, y2OS, 55, 55);

				protivnikPolja[i][j].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
				protivnikPolja[i][j].setBorderPainted(false);
				protivnikPolja[i][j].setMargin(new Insets(0, 0, 0, 0));
				protivnikPolja[i][j].setToolTipText(naziv);

				java.net.URL imgUrl = getClass().getResource("img/qwe.png");
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				protivnikPolja[i][j].setIcon(icon);

				java.net.URL imgUrl2 = getClass().getResource("img/qwex.png");
				ImageIcon icon2 = null;
				icon2 = new ImageIcon(imgUrl2);
				protivnikPolja[i][j].setDisabledIcon(icon2);

				protivnikPolja[i][j].setText("");
				Color c = new Color(37, 149, 196);
				protivnikPolja[i][j].setBackground(c);
				protivnikPolja[i][j].setForeground(Color.white);
				add(protivnikPolja[i][j]);
			}
			y2OS += 55;
		}
	}

	/**
	 * Postavlja action listenere za postavljanje brodova koji u sebi pokrece
	 * funkciju addShipPart
	 * 
	 * @param brodica
	 */
	public void postaviBrodoveListener(Vector<Brod> brodovi) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int k = i;
				int l = j;
				if (brojPostavljenihBrodova < 6) {
					mojaPolja[i][j].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							addShipPart(mojiBrodovi.elementAt(brojPostavljenihBrodova), l, k);
							System.out.println("brojBrodova:" + brojPostavljenihBrodova);
						}
					});
					mojaPolja[i][j].addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent event) {
							if (event.getButton() == MouseEvent.BUTTON3) {
								if (okreniBtn.isSelected()) {
									okreniBtn.setSelected(false);
								} else {
									okreniBtn.setSelected(true);
								}
							}
						}
					});

				}
			}
		}
	}

	/**
	 * Dodavanje buttona u brod objekt kad se naprave svi brodovi pokrece
	 * funkciju postaviZaIgru(moja polja) postaviZaIgru(protivnikPolja) i
	 * setPocetakGadanja();
	 * 
	 * @param brodica
	 * @param posX
	 * @param posY
	 */
	public void addShipPart(Brod brodica, int posX, int posY) {
		if (okreniBtn.isSelected()) {

			if (posX + brodica.velicina > 10) {

				System.out.println("ne moze");
				mojaPolja[posY][posX].setSelected(false);

			} else {

				boolean preklapa = false;
				for (int i = 0; i < brodica.velicina; i++) {
					if (!mojaPolja[posY][posX + i].isEnabled()) {
						preklapa = true;
					}
				}
				/**
				 * Ako se ne preklapa sve radi
				 */
				if (!preklapa) {
					brojPostavljenihBrodova++;
					
					/**
					 * Provjera lijeve strane i gasenje
					 */
					if (posX - 1 >= 0) {
						mojaPolja[posY][posX - 1].setEnabled(false);
						if (posY - 1 >= 0) {
							mojaPolja[posY - 1][posX - 1].setEnabled(false);
						}
						if (posY + 1 < 10) {
							mojaPolja[posY + 1][posX - 1].setEnabled(false);
						}
					}

					/**
					 * Postavljanje broda i gasenje polja oko njega
					 */
					for (int j = 0; j < brodica.velicina; j++) {
						brodica.addDio(mojaPolja[posY][posX + j]);
					
						// dodavanje ikona na brod
						postaviIkone(brodica.velicina, j, mojaPolja[posY][posX + j], true);
						String iconString = "img/brodunisten.png";
						java.net.URL imgUrl = getClass().getResource(iconString);
						ImageIcon icon = null;
						icon = new ImageIcon(imgUrl);
						mojaPolja[posY][posX + j].setDisabledIcon(icon);

						mojaPolja[posY][posX + j].setText("");
						mojaPolja[posY][posX + j].setSelected(true);
						if (posY - 1 >= 0) {
							mojaPolja[posY - 1][posX + j].setEnabled(false);
						}
						if (posY + 1 < 10) {
							mojaPolja[posY + 1][posX + j].setEnabled(false);
						}
					}
					/**
					 * Provjera desne strane i gasenje
					 */
					if (posX + brodica.velicina < 10) {
						mojaPolja[posY][posX + brodica.velicina].setEnabled(false);
						if (posY - 1 >= 0) {
							mojaPolja[posY - 1][posX + brodica.velicina].setEnabled(false);
						}
						if (posY + 1 < 10) {
							mojaPolja[posY + 1][posX + brodica.velicina].setEnabled(false);
						}
					}
				} else {
					System.out.println("ne moze preklapa se");
					mojaPolja[posY][posX].setSelected(false);
				}
			}
		} else {

			if (posY + brodica.velicina > 10) {

				System.out.println("ne moze");
				mojaPolja[posY][posX].setSelected(false);

			} else {

				boolean preklapa = false;
				for (int i = 0; i < brodica.velicina; i++) {
					if (!mojaPolja[posY + i][posX].isEnabled()) {
						preklapa = true;
					}
				}
				/**
				 * Ako se ne preklapa sve radi
				 */
				if (!preklapa) {
					brojPostavljenihBrodova++;
					/**
					 * Provjera lijeve strane i gasenje
					 */
					if (posY - 1 >= 0) {
						mojaPolja[posY - 1][posX].setEnabled(false);
						if (posX - 1 >= 0) {
							mojaPolja[posY - 1][posX - 1].setEnabled(false);
						}
						if (posX + 1 < 10) {
							mojaPolja[posY - 1][posX + 1].setEnabled(false);
						}
					}

					/**
					 * Postavljanje broda i gasenje polja oko njega
					 */
					for (int j = 0; j < brodica.velicina; j++) {
						brodica.addDio(mojaPolja[posY + j][posX]);

						// dodavanje ikona na brod
						postaviIkone(brodica.velicina, j, mojaPolja[posY + j][posX], false);
						String iconString = "img/brodunisten.png";
						java.net.URL imgUrl = getClass().getResource(iconString);
						ImageIcon icon = null;
						icon = new ImageIcon(imgUrl);
						mojaPolja[posY + j][posX].setDisabledIcon(icon);

						mojaPolja[posY + j][posX].setText("");
						mojaPolja[posY + j][posX].setSelected(true);
						if (posX - 1 >= 0) {
							mojaPolja[posY + j][posX - 1].setEnabled(false);
						}
						if (posX + 1 < 10) {
							mojaPolja[posY + j][posX + 1].setEnabled(false);
						}
					}
					/**
					 * Provjera desne strane i gasenje
					 */
					if (posY + brodica.velicina < 10) {
						mojaPolja[posY + brodica.velicina][posX].setEnabled(false);
						if (posX - 1 >= 0) {
							mojaPolja[posY + brodica.velicina][posX - 1].setEnabled(false);
						}
						if (posX + 1 < 10) {
							mojaPolja[posY + brodica.velicina][posX + 1].setEnabled(false);
						}
					}
				} else {
					System.out.println("ne moze preklapa se");
					mojaPolja[posY][posX].setSelected(false);
				}
			}
		}

		/**
		 * Kada se svi brodovi postave ocisti i onemoguci daljnje dodavanje
		 */
		if (brojPostavljenihBrodova == 6) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					int k = i;
					int l = j;
					ActionListener[] ar = mojaPolja[i][j].getActionListeners();
					mojaPolja[i][j].removeActionListener(ar[0]);
					mojaPolja[i][j].addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							mojaPolja[k][l].setSelected(false);
						}
					});
				}
			}
			postaviZaIgru(mojaPolja);
			postaviZaIgru(protivnikPolja);
			setPocetakGadanja();
		}

	}

	/**
	 * Funkcija za postavljanje protivnickih brodova ista kao i addShipPart uz
	 * promjenu static varijable za kolicinu brodova "brojProtivnickihBrodova" i
	 * prima varijavlu orjentacija umjesto btn.isChecked() za smjer broda
	 * 
	 * @param brodica
	 * @param posX
	 * @param posY
	 */
	public void addEnemyShipPart(Brod brodica, int posX, int posY, int orjentacija) {
		if (orjentacija == 1) {

			if (posX + brodica.velicina > 10) {

				System.out.println("ne moze");
				protivnikPolja[posY][posX].setSelected(false);

			} else {

				boolean preklapa = false;

				if (!protivnikPolja[posY][posX].isEnabled()) {
					preklapa = true;
				}

				for (int i = 0; i < brodica.velicina; i++) {
					if (!protivnikPolja[posY][posX + i].isEnabled()) {
						preklapa = true;
					}
				}
				/**
				 * Ako se ne preklapa sve radi
				 */
				if (!preklapa) {
					brojProtivnickihBrodova++;
					System.out.println("broj protivnickih brodova " + brojProtivnickihBrodova);
					/**
					 * Provjera lijeve strane i gasenje
					 */
					if (posX - 1 >= 0) {
						protivnikPolja[posY][posX - 1].setEnabled(false);
						if (posY - 1 >= 0) {
							protivnikPolja[posY - 1][posX - 1].setEnabled(false);
						}
						if (posY + 1 < 10) {
							protivnikPolja[posY + 1][posX - 1].setEnabled(false);
						}
					}

					/**
					 * Postavljanje broda i gasenje polja oko njega
					 */
					protivnikPolja[posY][posX].setEnabled(false);

					for (int j = 0; j < brodica.velicina; j++) {
						brodica.addDio(protivnikPolja[posY][posX + j]);

						String iconString = "img/brodunisten.png";
						java.net.URL imgUrl = getClass().getResource(iconString);
						ImageIcon icon = null;
						icon = new ImageIcon(imgUrl);
						protivnikPolja[posY][posX + j].setDisabledIcon(icon);
						protivnikPolja[posY][posX + j].setEnabled(false);

						if (posY - 1 >= 0) {
							protivnikPolja[posY - 1][posX + j].setEnabled(false);
						}
						if (posY + 1 < 10) {
							protivnikPolja[posY + 1][posX + j].setEnabled(false);
						}
					}
					/**
					 * Provjera desne strane i gasenje
					 */
					if (posX + brodica.velicina < 10) {
						protivnikPolja[posY][posX + brodica.velicina].setEnabled(false);
						if (posY - 1 >= 0) {
							protivnikPolja[posY - 1][posX + brodica.velicina].setEnabled(false);
						}
						if (posY + 1 < 10) {
							protivnikPolja[posY + 1][posX + brodica.velicina].setEnabled(false);
						}
					}
				} else {
					System.out.println("ne moze preklapa se");
					protivnikPolja[posY][posX].setSelected(false);
				}
			}
		} else {

			if (posY + brodica.velicina > 10) {

				System.out.println("ne moze");
				protivnikPolja[posY][posX].setSelected(false);

			} else {

				boolean preklapa = false;

				if (!protivnikPolja[posY][posX].isEnabled()) {
					preklapa = true;
				}
				for (int i = 0; i < brodica.velicina; i++) {
					if (!protivnikPolja[posY + i][posX].isEnabled()) {
						preklapa = true;
					}
				}
				/**
				 * Ako se ne preklapa sve radi
				 */

				if (!preklapa) {
					brojProtivnickihBrodova++;
					System.out.println("broj protivnickih brodova " + brojProtivnickihBrodova);
					/**
					 * Provjera lijeve strane i gasenje
					 */
					if (posY - 1 >= 0) {
						protivnikPolja[posY - 1][posX].setEnabled(false);
						if (posX - 1 >= 0) {
							protivnikPolja[posY - 1][posX - 1].setEnabled(false);
						}
						if (posX + 1 < 10) {
							protivnikPolja[posY - 1][posX + 1].setEnabled(false);
						}
					}

					/**
					 * Postavljanje broda i gasenje polja oko njega
					 */
					protivnikPolja[posY][posX].setEnabled(false);
					for (int j = 0; j < brodica.velicina; j++) {
						brodica.addDio(protivnikPolja[posY + j][posX]);
						protivnikPolja[posY + j][posX].setEnabled(false);
						// provjera da vidim gdje je protivnik postavio brodove
						// protivnikPolja[posY+j][posX].setText("x");
						String iconString = "img/brodunisten.png";
						java.net.URL imgUrl = getClass().getResource(iconString);
						ImageIcon icon = null;
						icon = new ImageIcon(imgUrl);
						protivnikPolja[posY + j][posX].setDisabledIcon(icon);

						protivnikPolja[posY + j][posX].setSelected(true);
						if (posX - 1 >= 0) {
							protivnikPolja[posY + j][posX - 1].setEnabled(false);
						}
						if (posX + 1 < 10) {
							protivnikPolja[posY + j][posX + 1].setEnabled(false);
						}
					}
					/**
					 * Provjera desne strane i gasenje
					 */
					if (posY + brodica.velicina < 10) {
						protivnikPolja[posY + brodica.velicina][posX].setEnabled(false);
						if (posX - 1 >= 0) {
							protivnikPolja[posY + brodica.velicina][posX - 1].setEnabled(false);
						}
						if (posX + 1 < 10) {
							protivnikPolja[posY + brodica.velicina][posX + 1].setEnabled(false);
						}
					}
				} else {
					System.out.println("ne moze preklapa se");
					protivnikPolja[posY][posX].setSelected(false);
				}
			}
		}
	}

	/**
	 * Inicijalizacija brodova i postavljanje u vektor
	 */
	public void napraviMojeBrodove() {
		Brod maliBrod1 = new Brod(2);
		Brod maliBrod2 = new Brod(2);
		Brod srednjiBrod1 = new Brod(3);
		Brod srednjiBrod2 = new Brod(3);
		Brod velikiBrod1 = new Brod(4);
		Brod velikiBrod2 = new Brod(4);
		mojiBrodovi.addElement(velikiBrod1);
		mojiBrodovi.addElement(velikiBrod2);
		mojiBrodovi.addElement(srednjiBrod1);
		mojiBrodovi.addElement(srednjiBrod2);
		mojiBrodovi.addElement(maliBrod1);
		mojiBrodovi.addElement(maliBrod2);
	}

	/**
	 * Iniciranje protivnickih brodova i stavljanje u vektor
	 */
	public void napraviProtivnickeBrodove() {
		Brod maliBrod1 = new Brod(2);
		Brod maliBrod2 = new Brod(2);
		Brod srednjiBrod1 = new Brod(3);
		Brod srednjiBrod2 = new Brod(3);
		Brod velikiBrod1 = new Brod(4);
		Brod velikiBrod2 = new Brod(4);
		protivnikBrodovi.addElement(velikiBrod1);
		protivnikBrodovi.addElement(velikiBrod2);
		protivnikBrodovi.addElement(srednjiBrod1);
		protivnikBrodovi.addElement(srednjiBrod2);
		protivnikBrodovi.addElement(maliBrod1);
		protivnikBrodovi.addElement(maliBrod2);
	}

	/**
	 * Postavlja protivnicke brodove
	 */
	public void postaviProtivnickeBrodove() {
		Random rnd = new Random();
		for (int i = 0; i < protivnikBrodovi.size(); i++) {
			do {
				addEnemyShipPart(protivnikBrodovi.elementAt(i), rnd.nextInt(10), rnd.nextInt(10), rnd.nextInt(2));
			} while (brojProtivnickihBrodova < i + 1);
		}
		postaviZaIgru(protivnikPolja);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int m = i;
				int n = j;
				protivnikPolja[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						protivnikPolja[m][n].setSelected(false);
					}
				});
			}
		}
	}

	/**
	 * Postaljvanje predanog polja na polje.setEnabled(true)
	 * polje.setChecked(false)
	 * 
	 * @param polje
	 */
	public void postaviZaIgru(JToggleButton[][] polje) {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				polje[i][j].setEnabled(true);
				polje[i][j].setSelected(false);
				if (mojaPolja[i][j].getText().equals("x")) {
					Color c = new Color(72, 68, 82);
					mojaPolja[i][j].setBackground(c);
				}
			}
		}
	}

	/**
	 * Postavlja action listenere na buttone, postavlja funkciju pucanj() na
	 * buttone
	 */
	public void setPocetakGadanja() {
		String tx = text.getText();
		text.setText("Poèetak bitke!\n" + tx);
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				int m = i;
				int n = j;
				protivnikPolja[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						brojPucanja++;
						score.setText(String.valueOf(brojPucanja));
						pucanj(protivnikPolja[m][n], n, m);
					}
				});
			}
		}
	}

	/**
	 * Provjeriti
	 * 
	 * @param xy
	 * @param x
	 * @param y
	 */
	public void pucanj(JToggleButton xy, int x, int y) {
		// System.out.println("Ja pucam");
		protivnikPolja[y][x].setEnabled(false);
		protivnikPolja[y][x].setForeground(Color.black);
		if (provjeriPucanj(xy.getName(), protivnikBrodovi, true, x, y)) {
			String tx = text.getText();
			text.setText(tx + "Uništio si brod!\n");
			protivnickiUnisteniBrodovi++;

		}

		/**
		 * Kraj igre
		 */
		if (protivnickiUnisteniBrodovi == 6) {
			System.out.println("Pobjedili ste u " + brojPucanja + " pucnjeva!");
			String ime = JOptionPane.showInputDialog("Pobjedili ste u " + brojPucanja + " pucnjeva!\n Unesite ime:");
			HighScoreManager hm = new HighScoreManager();
			if (ime != null) {
				hm.addScore(ime, brojPucanja);
			}
			ocistiSve();

		} else {

			protivnikPucanj();
		}
	}

	/**
	 * Provjeri pucanj za komp
	 * 
	 * @param xyMjesto
	 * @param brodovi
	 */
	public boolean provjeriPucanj(String xyMjesto, Vector<Brod> brodovi, boolean cijiPucanj) {

		boolean pogodak = false;
		for (int i = 0; i < brodovi.size(); i++) {
			for (int j = 0; j < brodovi.elementAt(i).velicina; j++) {
				// System.out.println(brodovi.elementAt(i).getPartName(j)+"
				// "+xyMjesto);
				if (brodovi.elementAt(i).getPartName(j).equals(xyMjesto)) {
					System.out.println("Pogodak");
					if (cijiPucanj) {
						Color c = new Color(72, 68, 82);
						protivnikPolja[j][j].setBackground(c);
					}
					pogodak = true;
					if (brodovi.elementAt(i).checkIfDestroyed()) {
						return true;
					}

				}
			}
		}
		if (!pogodak) {
			System.out.println("Promasaj!");
		}
		return false;
	}

	/**
	 * Provjeri pucanj za igraca
	 * 
	 * @param xyMjesto
	 * @param brodovi
	 * @param cijiPucanj
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean provjeriPucanj(String xyMjesto, Vector<Brod> brodovi, boolean cijiPucanj, int x, int y) {
		boolean pogodak = false;
		for (int i = 0; i < brodovi.size(); i++) {
			for (int j = 0; j < brodovi.elementAt(i).velicina; j++) {

				if (brodovi.elementAt(i).getPartName(j).equals(xyMjesto)) {

					if (cijiPucanj) {
						String tx = text.getText();
						text.setText(tx + "Pucao si u " + xyMjesto + " i pogodio!\n");
						jsp.getVerticalScrollBar().setValue(0);
						Color c = new Color(72, 68, 82);
						protivnikPolja[y][x].setBackground(c);

					} else {
						String tx = text.getText();
						text.setText(tx + "Protivnik je pucao u " + xyMjesto + " i pogodio!\n");
						PoljeXY poljeXY = new PoljeXY(x, y);
						ai.zadnjiXY.addElement(poljeXY);
					}
					pogodak = true;

					if (brodovi.elementAt(i).checkIfDestroyed()) {
						return true;
					}

				}
			}
		}

		if (!pogodak) {
			System.out.println("Promasaj!");
			if (cijiPucanj) {
				String tx = text.getText();
				text.setText(tx + "Pucao si u " + xyMjesto + " i promašio!\n");
				jsp.getVerticalScrollBar().setValue(0);
			} else {
				String tx = text.getText();
				text.setText(tx + "Protivnik je pucao u " + xyMjesto + " i promašio!\n");
			}
		}

		return false;
	}

	/*
	 * Funkcija gdje protivnik bira sta ce gadjati
	 */
	public void protivnikPucanj() {
		/**
		 * nista nije pogodeno -> pucaj na random
		 */
		if (ai.zadnjiXY.isEmpty()) {
			System.out.println("Protivnik puca");
			int x, y;
			boolean daGadjam = false;
			while (true) {
				Random rnd = new Random();
				x = rnd.nextInt(10);
				y = rnd.nextInt(10);
				/**
				 * Nemoj bas gadjati ako ima samo jedna
				 */
				if (mojaPolja[y][x].isEnabled()) {
					if (y - 1 >= 0 && mojaPolja[y - 1][x].isEnabled()) {
						daGadjam = true;
					} else if (y + 1 < 10 && mojaPolja[y + 1][x].isEnabled()) {
						daGadjam = true;
					} else if (x - 1 >= 0 && mojaPolja[y][x - 1].isEnabled()) {
						daGadjam = true;
					} else if (x + 1 < 10 && mojaPolja[y][x + 1].isEnabled()) {
						daGadjam = true;
					}
				}
				if (daGadjam) {
					break;
				}
			}

			System.out.println("Pucam u:" + Character.toString((char) (y + 65)) + " " + (x + 1));
			mojaPolja[y][x].setEnabled(false);
			mojaPolja[y][x].setForeground(Color.blue);
			Color c = new Color(37, 149, 196);
			mojaPolja[y][x].setBackground(c);
			/**
			 * Provjera jel unisteno
			 */
			if (provjeriPucanj(mojaPolja[y][x].getName(), mojiBrodovi, false, x, y)) {
				String tx = text.getText();
				text.setText(tx + "Uništen ti je brod!\n");
				mojiUnisteniBrodovi++;
				ai.poljeXY.clear();
				ai.zadnjiXY.clear();
				prvi = false; // reset flaga za dodavanje mogucnosti nakon prvih
								// pogodjenih
				unistiOkolo();

			}
			if (mojiUnisteniBrodovi == 6) {
				otkrijSve();
				System.out.println("Izgubili ste!");
				JFrame izgubiliSteDialog = new JFrame();
				izgubiliSteDialog.setBounds(730, 360, 300, 370);
				izgubiliSteDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JOptionPane.showMessageDialog(izgubiliSteDialog, "Izgubili ste");
				mainFrame.mylayout.removeLayoutComponent(this);
				mainFrame.frame.setBounds(WindowSize.width / 2 - WindowSize.menuWidth / 2,
						WindowSize.height / 2 - WindowSize.menuHeight / 2, WindowSize.menuWidth, WindowSize.menuHeight);
				mainFrame.mylayout.show(mainFrame.mainPanel, "menu");
			}
		}
		/**
		 * Pogodjeno je -> napravi nesto
		 */
		else if (ai.zadnjiXY.size() == 1) {
			/**
			 * uzim zadnju lokaciju sto si pogodio
			 */
			int x = ai.zadnjiXY.elementAt(0).x;
			int y = ai.zadnjiXY.elementAt(0).y;
			System.out.println("zadnji pogodak:" + Character.toString((char) (ai.zadnjiXY.lastElement().y + 65)) + " "
					+ (ai.zadnjiXY.lastElement().x + 1));

			/**
			 * Provjeri moze li gadjati oko nje ako mozes stavi u listu za
			 * gadanje ai.poljeXY
			 */
			if (!prvi) {
				if (x - 1 >= 0 && mojaPolja[y][x - 1].isEnabled()) {
					ai.poljeXY.addElement(new PoljeXY(x - 1, y));
				}
				if (x + 1 < 10 && mojaPolja[y][x + 1].isEnabled()) {
					ai.poljeXY.addElement(new PoljeXY(x + 1, y));
				}
				if (y - 1 >= 0 && mojaPolja[y - 1][x].isEnabled()) {
					ai.poljeXY.addElement(new PoljeXY(x, y - 1));
				}
				if (y + 1 < 10 && mojaPolja[y + 1][x].isEnabled()) {
					ai.poljeXY.addElement(new PoljeXY(x, y + 1));
				}
				prvi = true;
			}
			/**
			 * Makni zadnji pogodjeni element
			 */
			for (int i = 0; i < ai.poljeXY.size(); i++) {
				if (ai.poljeXY.equals(ai.zadnjiXY)) {
					ai.poljeXY.remove(i);
				}
			}

			/**
			 * gadaj neki oko njega iz vektora mogucih igranja
			 */
			Random rnd = new Random();
			PoljeXY poljexy = ai.poljeXY.elementAt(rnd.nextInt(ai.poljeXY.size()));

			/**
			 * Ugasi polje koje gadja
			 */
			mojaPolja[poljexy.y][poljexy.x].setEnabled(false);
			Color c = new Color(37, 149, 196);
			mojaPolja[poljexy.y][poljexy.x].setBackground(c);

			/**
			 * makni odabrani pucanj iz liste pucnjeva
			 */
			ai.ukloni(poljexy.x, poljexy.y);

			System.out.println("Mogu pucati u:");
			ispis();
			System.out.println("Pucam u:" + Character.toString((char) (poljexy.y + 65)) + " " + (poljexy.x + 1));

			/**
			 * provjeriPucanj()
			 */
			if (provjeriPucanj(mojaPolja[poljexy.y][poljexy.x].getName(), mojiBrodovi, false, poljexy.x, poljexy.y)) {
				String tx = text.getText();
				text.setText(tx + "Uništen ti je brod!\n");
				mojiUnisteniBrodovi++;
				ai.poljeXY.clear();
				ai.zadnjiXY.clear();
				prvi = false;
				unistiOkolo();
			}
			/**
			 * Kraj igre
			 */
			if (mojiUnisteniBrodovi == 6) {
				otkrijSve();
				System.out.println("Izgubili ste!");
				JFrame izgubiliSteDialog = new JFrame();
				izgubiliSteDialog.setBounds(730, 360, 300, 370);
				izgubiliSteDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JOptionPane.showMessageDialog(izgubiliSteDialog, "Izgubili ste");
				ocistiSve();

			}

		}
		/**
		 * Ako su pogodjena 2 u nizu logika
		 */
		else if (ai.zadnjiXY.size() > 1) {

			/**
			 * usporedi x i y os prvog pogodjenog i zadnjeg pogodjenog te na
			 * temelju toga preuredi izbor za gadjanje
			 */

			/**
			 * u slucaju gdje su 1. i 2. pogodjeni na x osi
			 */
			if (ai.zadnjiXY.elementAt(0).x != ai.zadnjiXY.lastElement().x) {
				for (int i = 0; i < ai.poljeXY.size(); i++) {
					/**
					 * makni sve iz izbora gadjanja koji imaju razliku y
					 */
					if (ai.poljeXY.elementAt(i).y != ai.zadnjiXY.elementAt(0).y) {
						ai.poljeXY.remove(i);
						i--;
					}
				}
				/**
				 * Dodavanje mogucnosti pucanja u slucaju da je pogodio
				 */
				if (ai.zadnjiXY.lastElement().x < ai.zadnjiXY.elementAt(0).x) {
					if (ai.zadnjiXY.lastElement().x - 1 >= 0
							&& mojaPolja[ai.zadnjiXY.lastElement().y][ai.zadnjiXY.lastElement().x - 1].isEnabled()) {
						PoljeXY poljeXY = new PoljeXY(ai.zadnjiXY.lastElement().x - 1, ai.zadnjiXY.lastElement().y);
						ai.poljeXY.addElement(poljeXY);
					}
				} else if (ai.zadnjiXY.lastElement().x > ai.zadnjiXY.elementAt(0).x) {
					if (ai.zadnjiXY.lastElement().x + 1 < 10
							&& mojaPolja[ai.zadnjiXY.lastElement().y][ai.zadnjiXY.lastElement().x + 1].isEnabled()) {
						PoljeXY poljeXY = new PoljeXY(ai.zadnjiXY.lastElement().x + 1, ai.zadnjiXY.lastElement().y);
						ai.poljeXY.addElement(poljeXY);
					}
				}

			}
			/**
			 * u slucaju gdje su 1. i zadnji pogodjeni na y osi
			 */
			if (ai.zadnjiXY.elementAt(0).y != ai.zadnjiXY.lastElement().y) {
				for (int i = 0; i < ai.poljeXY.size(); i++) {
					/**
					 * makni sve iz izbora gadjanja koji imaju razliku x
					 */
					if (ai.poljeXY.elementAt(i).x != ai.zadnjiXY.elementAt(0).x) {
						ai.poljeXY.remove(i);
						i--;

					}
				}
				if (ai.zadnjiXY.lastElement().y < ai.zadnjiXY.elementAt(0).y) {
					if (ai.zadnjiXY.lastElement().y - 1 >= 0
							&& mojaPolja[ai.zadnjiXY.lastElement().y - 1][ai.zadnjiXY.lastElement().x].isEnabled()) {
						PoljeXY poljeXY = new PoljeXY(ai.zadnjiXY.lastElement().x, ai.zadnjiXY.lastElement().y - 1);
						ai.poljeXY.addElement(poljeXY);
					}
				} else if (ai.zadnjiXY.lastElement().y > ai.zadnjiXY.elementAt(0).y) {
					if (ai.zadnjiXY.lastElement().y + 1 < 10
							&& mojaPolja[ai.zadnjiXY.lastElement().y + 1][ai.zadnjiXY.lastElement().x].isEnabled()) {
						PoljeXY poljeXY = new PoljeXY(ai.zadnjiXY.lastElement().x, ai.zadnjiXY.lastElement().y + 1);
						ai.poljeXY.addElement(poljeXY);
					}
				}
			}
			/**
			 * Makni zadnji pogodjeni element
			 */
			ai.ukloni(ai.zadnjiXY.lastElement().x, ai.zadnjiXY.lastElement().y);

			/**
			 * Random biraj iz liste sto bi mogao pucati
			 */
			Random rnd = new Random();
			PoljeXY poljexy = ai.poljeXY.elementAt(rnd.nextInt(ai.poljeXY.size()));
			/**
			 * Ugasi polje koje gadja
			 */
			mojaPolja[poljexy.y][poljexy.x].setEnabled(false);
			Color c = new Color(37, 149, 196);
			mojaPolja[poljexy.y][poljexy.x].setBackground(c);
			System.out.println("zadnji pogodak:" + Character.toString((char) (ai.zadnjiXY.lastElement().y + 65)) + " "
					+ (ai.zadnjiXY.lastElement().x + 1));
			System.out.println("Mogu pucati u:");
			ispis();
			System.out.println("Pucam u :" + Character.toString((char) (poljexy.y + 65)) + " " + (poljexy.x + 1));

			/**
			 * Makni taj u koji puca
			 */
			ai.ukloni(poljexy.x, poljexy.y);

			/**
			 * Ako je brod unisten isprazni listu mogucih i pocni ponovno pucati
			 * random
			 */
			if (provjeriPucanj(mojaPolja[poljexy.y][poljexy.x].getName(), mojiBrodovi, false, poljexy.x, poljexy.y)) {
				String tx = text.getText();
				text.setText("Uništen ti je brod!\n" + tx);
				mojiUnisteniBrodovi++;
				System.out.println("brisem podatke!");
				ai.poljeXY.clear();
				ai.zadnjiXY.clear();
				prvi = false;
				System.out.println("poljeXY size:" + ai.poljeXY.size() + " zadnjiXY size:" + ai.zadnjiXY.size());
				unistiOkolo();
			}
			/**
			 * Kraj igre
			 */
			if (mojiUnisteniBrodovi == 6) {
				otkrijSve();
				System.out.println("Izgubili ste!");
				JFrame izgubiliSteDialog = new JFrame();
				izgubiliSteDialog.setBounds(730, 360, 300, 370);
				izgubiliSteDialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				JOptionPane.showMessageDialog(izgubiliSteDialog, "Izgubili ste");
				ocistiSve();

			}

		}

	}

	public void unistiOkolo() {
		for (int i = 0; i < mojiBrodovi.size(); i++) {
			if (mojiBrodovi.elementAt(i).checkIfDestroyed()) {
				int x, y;
				char a, b;
				for (int j = 0; j < mojiBrodovi.elementAt(i).velicina; j++) {
					a = mojiBrodovi.elementAt(i).getPartName(j).charAt(0);
					if(mojiBrodovi.elementAt(i).getPartName(j).length() == 3){
						b = 58;				 
					}else{
						b = mojiBrodovi.elementAt(i).getPartName(j).charAt(1);
					}
					System.out.println("a:"+a + " "+"b:"+b);
					x = Integer.valueOf(b) - 49;
					y = Integer.valueOf(a) - 65;
					System.out.println("gasim:");
					if (y - 1 >= 0 && x - 1 >= 0) {
						mojaPolja[y - 1][x - 1].setEnabled(false);
						System.out.println("prvi"+(y-1)+" "+(x-1));
					}
					
					if (y - 1 >= 0 && x + 1 < 10) {
						mojaPolja[y - 1][x + 1].setEnabled(false);
						System.out.println("prvi"+(y-1)+" "+(x+1));
					}
					if (y + 1 < 10 && x - 1 >= 0) {
						mojaPolja[y + 1][x - 1].setEnabled(false);
						System.out.println("drugi"+(y+1)+" "+(x-1));
					}
					if (y + 1 < 10 && x + 1 < 10) {
						mojaPolja[y + 1][x + 1].setEnabled(false);
						System.out.println("treci"+(y+1)+" "+(x+1));
					}

					if (y - 1 >= 0) {
						mojaPolja[y - 1][x].setEnabled(false);
						System.out.println("cetvrti"+(y-1)+" "+(x));
					}

					if (y + 1 < 10) {
						mojaPolja[y + 1][x].setEnabled(false);
						System.out.println("peti"+(y+1)+" "+(x));
					}

					if (x - 1 >= 0) {
						mojaPolja[y][x - 1].setEnabled(false);
						System.out.println("sesti"+(y)+" "+(x-1));
					}

					if (x + 1 < 10) {
						mojaPolja[y][x + 1].setEnabled(false);
						System.out.println("sedmi"+(y)+" "+(x+1));
					}

				}

			}
		}
	}

	public void ispis() {
		for (int i = 0; i < ai.poljeXY.size(); i++) {
			System.out.println(Character.toString((char) (ai.poljeXY.elementAt(i).y + 65)) + " "
					+ (ai.poljeXY.elementAt(i).x + 1));
		}
	}

	public void ispisBrodova() {
		for (int i = 0; i < protivnikBrodovi.size(); i++) {
			System.out.println("Brod " + i);
			for (int j = 0; j < protivnikBrodovi.elementAt(i).velicina; j++) {
				System.out.println(protivnikBrodovi.elementAt(i).brod[j].getName());
			}
		}

	}

	/**
	 * Funkcija koja dodaje ikone brodovima
	 * 
	 * @param velicina
	 * @param pozicija
	 * @param brodica
	 * @param rotacija
	 */
	public void postaviIkone(int velicina, int pozicija, JToggleButton brodica, boolean rotacija) {

		if (rotacija) {
			if (velicina == 4) {
				String iconString = "img/br" + (pozicija + 1) + "2.png";
				java.net.URL imgUrl = getClass().getResource(iconString);
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				brodica.setIcon(icon);
			} else if (velicina == 3) {
				if (pozicija == 2) {
					pozicija++;
				}
				String iconString = "img/br" + (pozicija + 1) + "2.png";
				java.net.URL imgUrl = getClass().getResource(iconString);
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				brodica.setIcon(icon);
			} else if (velicina == 2) {

				String iconString = "img/br" + (pozicija + 1) + "2m.png";
				java.net.URL imgUrl = getClass().getResource(iconString);
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				brodica.setIcon(icon);
			}

		} else {
			if (velicina == 4) {
				String iconString = "img/br" + (pozicija + 1) + ".png";
				java.net.URL imgUrl = getClass().getResource(iconString);
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				brodica.setIcon(icon);
			} else if (velicina == 3) {
				if (pozicija == 2) {
					pozicija++;
				}
				String iconString = "img/br" + (pozicija + 1) + ".png";
				java.net.URL imgUrl = getClass().getResource(iconString);
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				brodica.setIcon(icon);
			} else if (velicina == 2) {

				String iconString = "img/br" + (pozicija + 1) + "m.png";
				java.net.URL imgUrl = getClass().getResource(iconString);
				ImageIcon icon = null;
				icon = new ImageIcon(imgUrl);
				brodica.setIcon(icon);
			}
		}
	}

	/*
	 * Funkcija koja brise sve varijable zaduzene za igru, potrebno za
	 * pokretanje nove igre
	 */
	public void ocistiSve() {
		brojPostavljenihBrodova = 0;
		brojProtivnickihBrodova = 0;
		mojiUnisteniBrodovi = 0;
		protivnickiUnisteniBrodovi = 0;
		brojPucanja = 0;
		prvi = false;
		mainFrame.mylayout.removeLayoutComponent(this);
		mainFrame.mylayout.show(mainFrame.mainPanel, "menu");
		mainFrame.frame.setBounds(WindowSize.width / 2 - WindowSize.menuWidth / 2,
				WindowSize.height / 2 - WindowSize.menuHeight / 2, WindowSize.menuWidth, WindowSize.menuHeight);

	}

	public void otkrijSve() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				protivnikPolja[i][j].setEnabled(false);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (okreniBtn.isSelected()) {
				okreniBtn.setSelected(false);
			} else {
				okreniBtn.setSelected(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
