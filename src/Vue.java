import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Vue{
	public Grille grille;
	public Modele modele;
	public Controleur controleur;
		
	JFrame frame = new JFrame();
		JLabel label = new JLabel("bruh");
		JPanel panneau = new JPanel();
			JButton launch = new JButton("Lancer le jeu");
			JButton level_select = new JButton("Choix du niveau");
			JButton rules = new JButton("Règles");
			JButton credits = new JButton("Crédits");
	
	public Vue(){
		
		panneau.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

		GridLayout layout = new GridLayout(0, 1);
		layout.setVgap(30);
		panneau.setLayout(layout);

		panneau.add(launch);
		panneau.add(level_select);
		panneau.add(rules);
		panneau.add(credits);
		panneau.add(label);

		launch.addActionListener((event) -> {
			Jeu();
		});

		rules.addActionListener((event) -> {
			rules();
		});

		frame.add(panneau, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Pet Rescue Saga");
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.pack();
        frame.setVisible(true);
	}

	private void Jeu(){
		frame.setVisible(false);

		JFrame game_frame = new JFrame();
		JButton[][] game_buttons = new JButton[grille.largeur][grille.hauteur];

		JMenuBar jmb = new JMenuBar();
		game_frame.setJMenuBar(jmb);
		jmb.add(grille.score);

		game_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		game_frame.pack();
		game_frame.setVisible(true);
	}

	private void rules() {
		frame.setVisible(false);

		JFrame rules_frame = new JFrame();
		JLabel rules_text = new JLabel("Le but est simple : il s'agit de libérer les animaux bloqués en haut des boites en détruisant celles-ci. Seuls les boites détruisables sont celles qui ont une de la même couleur en adjacence. BLABLABLA");
		JPanel rules_panel = new JPanel();
		JButton rules_return = new JButton("Retour");

		BorderLayout rules_layout = new BorderLayout();
		rules_panel.setLayout(rules_layout);

		rules_panel.add(rules_return);

		rules_return.addActionListener((event) ->{
			frame.setVisible(true);
			rules_frame.dispose();
		});

		rules_frame.add(rules_panel, BorderLayout.SOUTH);
		rules_frame.add(rules_text);

		rules_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(800, 600));
		rules_frame.pack();
		rules_frame.setVisible(true);
	}

	public void mouseClicked(MouseEvent event) {
	//avec cette fonction on veut essaier de supprimer le cube sur lequel le joueur a cliqué.
		int x=event.getPoint().x;
		int y=event.getPoint().y;
	}

	public static void main(String[] args){
		new Vue();
	}
}
