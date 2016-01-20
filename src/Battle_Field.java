import javax.swing.*;
import javax.swing.border.*;
// import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Battle_Field{
    public static void main(String[] argv){
        JFrame playground = new MyFrame(800, 730, "Card Wars");
    }
}

class MyFrame extends JFrame implements ActionListener {
    JPanel fieldPanel = new JPanel();
	JPanel handPanel = new JPanel();

    public MyFrame(int width, int height, String title) {
		// set frame
		super(title);
		setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		
		// --- set field panel ---
		// set battleField
		GridLayout layoutG1 = new GridLayout(2, 4, 0, 0);
		JPanel battleField = new JPanel(layoutG1);
		battleField.setPreferredSize(new Dimension(800, 660));

		// catch whether the field has creature or building from program
		boolean[] hasCreature = new boolean[8];
		boolean[] hasBuilding = new boolean[8];
		for(int i = 0; i < 8; i++) {
			hasCreature[i] = false;
			hasBuilding[i] = false;
		}

		// set field: corn or sandy panel for empty field
		for(int i = 0; i < 8; i++) {
			JLayeredPane field = new JLayeredPane();
			field.setPreferredSize(new Dimension(800, 600));
			field.setLayout(new BorderLayout());
			if(i > 3) {
				JPanel cornPanel = new Corn_Empty_Panel();
				cornPanel.setPreferredSize(new Dimension(200, 300));
				field.add(cornPanel, BorderLayout.CENTER, new Integer(0));
			}
			else {
				JPanel sandyPanel = new Sandy_Empty_Panel();
				sandyPanel.setPreferredSize(new Dimension(200, 300));
				field.add(sandyPanel, BorderLayout.CENTER, new Integer(0));
			}
			if(!hasCreature[i]) {
				JButton fBtn = new JButton(new AbstractAction("Floop") {
					public void actionPerformed(ActionEvent e) {
						System.out.println("Floop this creature!");
					}
				});
				field.add(fBtn, BorderLayout.SOUTH, new Integer(1));
			}
			battleField.add(field);
		}

		// field panel, can swap to hand panel
		fieldPanel.add(battleField, BorderLayout.NORTH);		
		JButton ftohBtn = new JButton("swap to Hand panel");
		ftohBtn.setActionCommand("SwapPanel");
		ftohBtn.addActionListener(this);
		fieldPanel.add(ftohBtn, BorderLayout.SOUTH);

		// --- set hand panel ---
		// hand panel, can swap to field panel
		JPanel hands = new Table_Background();
		hands.setLayout(new GridLayout());
		hands.setPreferredSize(new Dimension(800, 660));
		hands.setBorder(new EmptyBorder(140, 10, 270, 10));

		int cardNum = 5;
		for(int i = 0; i < cardNum; i++) {
			JPanel cardPlace = new JPanel();
			JPanel card;
			if(i == 2 || i == 4 || i == 5) {
				card = new Corn_Dog_Card();
			}
			else if(i == 1) {
				card = new Sand_Castle_Card();
			}
			else {	// i == 3
				card = new Corn_Scepter_Card();
			}
			card.setPreferredSize(new Dimension(140, 210));
			cardPlace.add(card, BorderLayout.NORTH);
			cardPlace.setOpaque(false);
			final int cardIndex = i;
			JButton playCardBtn = new JButton(new AbstractAction("Play this!") {
				public void actionPerformed(ActionEvent e) {
					System.out.println("Play this card!");
					int opt = JOptionPane.showConfirmDialog(null, 
						"You want to play this card?", "Warning", 
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, 
						new ImageIcon("lib/Jakesalad.jpg"));
					if(opt == JOptionPane.YES_OPTION) {
						String[] playerChooseField = {"1", "2", "3", "4"};
						String playerChosenField = (String) JOptionPane.showInputDialog(null, 
							"Please select field you want to place this creature.", "Input", 
							JOptionPane.INFORMATION_MESSAGE, new ImageIcon("lib/Jakesalad.jpg"), 
							playerChooseField, "1");
						int selectedField = 0;
						boolean putField = false;
						if(playerChosenField == "1") {
							selectedField = 1;
							putField = true;
						}
						else if(playerChosenField == "2") {
							selectedField = 2;
							putField = true;
						}
						else if(playerChosenField == "3") {
							selectedField = 3;
							putField = true;
						}
						else if(playerChosenField == "4") {
							selectedField = 4;
							putField = true;
						}
						if(putField == true) {
							hands.remove(cardPlace);
							if(cardIndex != 3) {	// not Spell
								Component[] playerFields = battleField.getComponents();	// fields
								((JComponent) playerFields[selectedField + 3]).setBorder(new EmptyBorder(0, 30, 0, 20));
								if(cardIndex == 1) {	// Building
									JPanel onField = new Sand_Castle_On_Field();
									onField.setPreferredSize(new Dimension(140, 210));
									onField.setOpaque(false);
									//((JComponent) playerFields[selectedField + 3]).setComponentZOrder(onField, 0);
									((JComponent) playerFields[selectedField + 3]).add(onField, BorderLayout.SOUTH, new Integer(2));
								}
								else {	// Creature
									JPanel onField = new Corn_Dog_Card();
									onField.setPreferredSize(new Dimension(140, 210));
									onField.setOpaque(false);
									//((JComponent) playerFields[selectedField + 3]).setComponentZOrder(onField, 0);
									((JComponent) playerFields[selectedField + 3]).add(onField, BorderLayout.NORTH, new Integer(2));
								}
							}
							repaint();
							revalidate();
						}
					}
	  			}
			});
			playCardBtn.setActionCommand("PlayCard");
			playCardBtn.addActionListener(this);
			cardPlace.add(playCardBtn, BorderLayout.SOUTH);
			hands.add(cardPlace);
		}
		handPanel.add(hands, BorderLayout.NORTH);
		JButton htofBtn = new JButton("swap to Field panel");
		htofBtn.setActionCommand("SwapPanel");
		htofBtn.addActionListener(this);
		handPanel.add(htofBtn, BorderLayout.SOUTH);
		
		// frame first show field panel
		this.getContentPane().add(handPanel);
		setVisible(true);
    }

    // Listen to the button and perform the swap.
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd == "SwapPanel") {
			for (Component component : getContentPane().getComponents()) {
				if (component == fieldPanel) {
					remove(fieldPanel);
					add(handPanel);
				}
				else if(component == handPanel) {
					remove(handPanel);
					add(fieldPanel);
				}
				repaint();
				revalidate();
			}
		}
		// else if(cmd == "...") {
	}
}

class Sandy_Empty_Panel extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Sandylands.jpg").getImage();
		g.drawImage(i, 0, 0, 200, 300, this);
	}
}

class Corn_Empty_Panel extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Corn.jpg").getImage();
		g.drawImage(i, 0, 0, 200, 300, this);
	}
}

class Table_Background extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Hand_Back.jpg").getImage();
		g.drawImage(i, 0, 0, 800, 650, this);
	}
}

class Corn_Dog_Card extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Corn_Dog.jpg").getImage();
		g.drawImage(i, 0, 0, 140, 210, this);
	}
}

class Sand_Castle_Card extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Sand_Castle.jpg").getImage();
		g.drawImage(i, 0, 0, 140, 210, this);
	}
}

class Sand_Castle_On_Field extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Sand_Castle_On_Field.jpg").getImage();
		g.drawImage(i, 0, 0, 140, 210, this);
	}
}

class Corn_Scepter_Card extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Corn_Scepter.jpg").getImage();
		g.drawImage(i, 0, 0, 140, 210, this);
	}
}

/*
class Legion_of_Earlings_Card extends JPanel {
	public void paintComponent(Graphics g) {
		Image i = new ImageIcon("lib/Legion_of_Earlings.jpg").getImage();
		g.drawImage(i, 0, 0, 140, 210, this);
	}
}
*/