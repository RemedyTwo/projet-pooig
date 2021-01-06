import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class Vue extends JFrame {
	// gère l'affichage de l'interface graphique du jeu
	public Grille grille;
	public Modele modele;
	public Niveaux niveau = new Niveaux();
	Joueur j = new Joueur("");

	public Vue() {
		menuPrincipal();

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Pet Rescue Saga");
		setPreferredSize(new Dimension(800, 600));
		pack();
		setLocationRelativeTo(null);
	}

	public void menuPrincipal() {
		JPanel panneau = new JPanel();
		try {
			FileInputStream fis = new FileInputStream("joueur.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			j = (Joueur) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JLabel message = new JLabel("Bienvenue " + j.nom + " !");
		JLabel score_total = new JLabel("Score cumulé : " + j.score_cumule);
		message.setHorizontalAlignment(JLabel.CENTER);
		JButton level_select = new JButton("Sélectionner un niveau");
		JButton rules = new JButton("Règles");
		JButton delete = new JButton("Supprimer les données");
		JButton credits = new JButton("Crédits");
		JButton retour = new JButton("Retour à l'affichage terminal");

		getContentPane().removeAll();

		panneau.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));

		GridLayout layout = new GridLayout(0, 1);
		layout.setVgap(30);
		panneau.setLayout(layout);

		panneau.add(level_select);
		panneau.add(rules);
		panneau.add(delete);
		panneau.add(credits);
		panneau.add(retour);

		level_select.addActionListener((event) -> {
			selectLevel();
		});

		rules.addActionListener((event) -> {
			rules();
		});

		delete.addActionListener((event) -> {
			try {
				PrintWriter f1 = new PrintWriter("joueur.ser");
				f1.close();
				PrintWriter f2 = new PrintWriter("niveau.ser");
				f2.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			dispose();
		});

		credits.addActionListener((event) -> {
			credits();
		});

		retour.addActionListener((event) ->{
			dispose();
			String[] args = {};
			Main.main(args);
		});

		add(message, BorderLayout.PAGE_START);
		add(panneau, BorderLayout.CENTER);

		revalidate();
		repaint();
	}

	public void displayGrid(Grille g){
		getContentPane().removeAll();

		JPanel grid_buttonlist = new JPanel();
			JButton[][] button_grid = new JButton[g.largeur][g.hauteur];
		JPanel other_buttons = new JPanel();
			JButton grid_save = new JButton("Sauvegarder & quitter");
			JButton grid_load = new JButton("Charger");
			JButton grid_reset = new JButton("Recommencer");
			JButton grid_return = new JButton("Retour");


		int largeur = g.largeur;
		int hauteur = g.hauteur;
		
		GridLayout grid_layout = new GridLayout(largeur, hauteur);

		makingGrid(grid_buttonlist, button_grid, g);

		grid_buttonlist.setLayout(grid_layout);

		grid_save.addActionListener((event) ->{
			try{
				FileOutputStream fos = new FileOutputStream("niveau.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(g);
			}catch (Exception e){
				e.printStackTrace();
			}
			dispose();
		});

		grid_load.addActionListener((event) ->{
			try{
				FileInputStream fis = new FileInputStream("niveau.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				Grille g2 = (Grille)ois.readObject();
				//g2.plateau = g2.copiePlateau();
				displayGrid(g2);
			}catch(Exception e){
				JFrame grid_erreur = new JFrame();
				JLabel grid_erreur_text = new JLabel("Aucune sauvegarde trouvée !");
				JButton grid_erreur_return = new JButton("Retour");

				grid_erreur_text.setHorizontalAlignment(JLabel.CENTER);

				grid_erreur.add(grid_erreur_text, BorderLayout.CENTER);

				grid_erreur_return.addActionListener((event2) ->{
					grid_erreur.dispose();
				});

				grid_erreur.add(grid_erreur_return, BorderLayout.PAGE_END);

				grid_erreur.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				grid_erreur.setPreferredSize(new Dimension(400, 150));
				grid_erreur.setTitle("Erreur");
				grid_erreur.pack();
				grid_erreur.setLocationRelativeTo(null);
				grid_erreur.setVisible(true);
			}
		});

		grid_reset.addActionListener((event) ->{
			Grille g2 = new Grille(niveau.niveau3.plateau);
			g2.plateau = g2.copiePlateau();
			displayGrid(g2);
		});

		grid_return.addActionListener((event) ->{
			menuPrincipal();
		});

		other_buttons.add(grid_save);
		other_buttons.add(grid_load);
		other_buttons.add(grid_reset);
		other_buttons.add(grid_return);

		add(other_buttons, BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

	public void makingGrid(JPanel grid_buttonlist, JButton[][] button_grid, Grille g){
		JPanel grid_scores = new JPanel();
		JLabel score = new JLabel("Score : " + g.score);
		JLabel animaux_restants = new JLabel("Nombre d'animaux restants : " + g.nbAnimaux);
		grid_scores.add(score);
		grid_scores.add(animaux_restants);
		for(int i = 0; i < g.largeur; i++){
			for(int j = 0; j < g.hauteur; j++){
				JButton piece = new JButton();
				piece.setName(i + "" + j);
				piece.setSize(new Dimension(10, 10));
				piece.addActionListener((event) -> {
					int x = Integer.parseInt(String.valueOf(piece.getName().charAt(0)));
					int y = Integer.parseInt(String.valueOf(piece.getName().charAt(1)));
					if(g.peutSupprimer(x, y)){
						g.supprime(x, y);
						g.gravite();
						grid_buttonlist.removeAll();
						makingGrid(grid_buttonlist, button_grid, g);
						grid_buttonlist.setLayout(new GridLayout(g.hauteur, g.largeur));
						grid_buttonlist.revalidate();
						grid_buttonlist.repaint();
						//revalidate();
						//repaint();
						if(g.nbAnimaux == 0){
							JFrame grid_victoire = new JFrame();
							JLabel grid_victoire_text = new JLabel("Vous avez gagné !");
							JButton grid_victoire_return = new JButton("OK");

							grid_victoire_text.setHorizontalAlignment(JLabel.CENTER);

							grid_victoire.add(grid_victoire_text, BorderLayout.CENTER);

							grid_victoire_return.addActionListener((event2) ->{
								grid_victoire.dispose();
								menuPrincipal();
							});

							grid_victoire.add(grid_victoire_return, BorderLayout.PAGE_END);

							grid_victoire.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
							grid_victoire.setPreferredSize(new Dimension(400, 150));
							grid_victoire.setTitle("Erreur");
							grid_victoire.pack();
							grid_victoire.setLocationRelativeTo(null);
							grid_victoire.setVisible(true);
						}
					}else{
						JFrame grid_erreur = new JFrame();
						JLabel grid_erreur_text = new JLabel("Vous ne pouvez pas supprimer ce bloc !");
						JButton grid_erreur_return = new JButton("Retour");

						grid_erreur_text.setHorizontalAlignment(JLabel.CENTER);

						grid_erreur.add(grid_erreur_text, BorderLayout.CENTER);

						grid_erreur_return.addActionListener((event2) ->{
							grid_erreur.dispose();
						});

						grid_erreur.add(grid_erreur_return, BorderLayout.PAGE_END);

						grid_erreur.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
						grid_erreur.setPreferredSize(new Dimension(400, 150));
						grid_erreur.setTitle("Erreur");
						grid_erreur.pack();
						grid_erreur.setLocationRelativeTo(null);
						grid_erreur.setVisible(true);
					}
				});
				if(g.plateau[i][j].piece instanceof Cube.Rouge){
					piece.setBackground(Color.RED);
				}else if(g.plateau[i][j].piece instanceof Cube.Vert){
					piece.setBackground(Color.GREEN);
				}else if(g.plateau[i][j].piece instanceof Cube.Bleu){
					piece.setBackground(Color.BLUE);
				}else if(g.plateau[i][j].piece instanceof Cube.Jaune){
					piece.setBackground(Color.YELLOW);
				}else if(g.plateau[i][j].piece instanceof Animal && g.plateau[i][j].estVide == false){
					piece.setBackground(Color.PINK);
					try{
						Image img = ImageIO.read(getClass().getResource("ressources/animal.jpg")).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
						piece.setIcon(new ImageIcon(img));
					}catch(IOException e){
						e.printStackTrace();
					}
				}if(g.plateau[i][j].estVide){
					piece.setBackground(Color.WHITE);
				}if(g.plateau[i][j].piece instanceof Obstacle){
					piece.setBackground(Color.BLACK);
				}
				button_grid[i][j] = piece;
				grid_buttonlist.add(button_grid[i][j]);
			}
		}
		add(grid_buttonlist, BorderLayout.CENTER);
		add(grid_scores, BorderLayout.NORTH);
	}

	private void rules() {
		setVisible(false);

		JFrame rules_frame = new JFrame();
		JLabel rules_text = new JLabel("Le but est simple : il s'agit de libérer les animaux bloqués en haut des boites en détruisant celles-ci. Les boîtes destructibles sont celles qui ont au moins une boîte de même couleur en adjacence. Faite descendre tous les animaux en bas de l'écran et vous gagnez! Attention: les cases noires ne peuvent pas être supprimées.");
		JPanel rules_panel = new JPanel(); 
		JButton rules_return = new JButton("Retour");

		BorderLayout rules_layout = new BorderLayout();
		rules_panel.setLayout(rules_layout);

		rules_panel.add(rules_return);

		rules_return.addActionListener((event) ->{
			setVisible(true);
			rules_frame.dispose();
		});

		rules_frame.add(rules_panel, BorderLayout.SOUTH);
		rules_frame.add(rules_text);

		rules_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		rules_frame.setTitle("Pet Rescue Saga");
		rules_frame.pack();
		rules_frame.setLocationRelativeTo(null);
		rules_frame.setVisible(true);
	}
	
	private void selectLevel(){	
		JPanel level_panel=new JPanel();
		GridLayout level_layout = new GridLayout(0, 1);
	
		JPanel level_panel2 = new JPanel();
			JLabel level_text = new JLabel("Choisissez votre niveau de difficulté.");
				level_text.setHorizontalAlignment(JLabel.CENTER);
			JButton niveau1 = new JButton("Facile : 5x5");
				niveau1.setBackground(Color.GREEN);
			JButton niveau2 = new JButton("Normal : 10x10");
				niveau2.setBackground(Color.ORANGE);
			JButton niveau3 = new JButton("Difficile : 15x15");
				niveau3.setBackground(Color.RED);
		JButton level_return = new JButton("Retour");

		getContentPane().removeAll();

		level_panel.add(niveau1);
		level_panel.add(niveau2);
		level_panel.add(niveau3);
		level_panel2.add(level_return);

		level_panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		level_return.addActionListener((event) ->{
			menuPrincipal();
		});
	
		niveau1.addActionListener((event)->{
			/*Grille grille=new Grille(5,5,5);
			this.grille=grille;
			frame.setVisible(true);
			frame.dispose();*/
		});
			
		niveau2.addActionListener((event)->{
			/*Grille grille=new Grille(10,10,10);
			this.grille=grille;
			frame.setVisible(true);
			frame.dispose();*/
		});
			
		niveau3.addActionListener((event)->{
			Grille g = new Grille(niveau.plateau_niveau3);
			g.plateau = g.copiePlateau();
			displayGrid(g);
		});

		add(level_panel, BorderLayout.CENTER);
		add(level_text, BorderLayout.NORTH);
		add(level_panel2, BorderLayout.SOUTH);

		level_layout.setVgap(30);
		level_panel.setLayout(level_layout);

		revalidate();
		repaint();
	}
	
	private void credits() {
		setVisible(false);

		JFrame credits_frame = new JFrame();
		JLabel credits_text = new JLabel("programme crée par Bilal Seddiki et Gaspard Romele dans le cadre d'un projet de seconde année de licence informatique,2021");
		JPanel credits_panel = new JPanel();
		JButton credits_return = new JButton("Retour");
		
		BorderLayout credits_layout = new BorderLayout();
		credits_panel.setLayout(credits_layout);

		credits_panel.add(credits_return);

		credits_return.addActionListener((event) ->{
			setVisible(true);
			credits_frame.dispose();
		});

		credits_frame.add(credits_panel, BorderLayout.SOUTH);
		credits_frame.add(credits_text);

		credits_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		credits_frame.setTitle("Pet Rescue Saga");
		credits_frame.pack();
		credits_frame.setLocationRelativeTo(null);
		credits_frame.setVisible(true);
	}

	/*public void mouseClicked(MouseEvent event) {
	//avec cette fonction on veut essaier de supprimer le cube sur lequel le joueur a cliqué.
		int x=event.getPoint().x;
		int y=event.getPoint().y;
	}*/

	public static void main(String[] args){
		new Vue();
	}
}
