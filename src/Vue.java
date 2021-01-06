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
	public Niveaux niveau = new Niveaux();
	public Joueur joueur = new Joueur("");

	//constructeur
	public Vue() {
		menuPrincipal();

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Pet Rescue Saga");
		setPreferredSize(new Dimension(800, 600));
		pack();
		setLocationRelativeTo(null);
	}

	//affiche le menu principal
	public void menuPrincipal() {
		JPanel panneau = new JPanel();
		try {
			FileInputStream fis = new FileInputStream("joueur.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			joueur = (Joueur) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JPanel top_msg = new JPanel();
		JLabel message = new JLabel("Bienvenue " + joueur.nom + " !");
		JLabel score_total = new JLabel("Score cumulé : " + joueur.score_cumule);
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

		top_msg.add(message);
		top_msg.add(score_total);

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
			JFrame avertissement = new JFrame();
			JLabel avertissement_text = new JLabel("<html>Êtes-vous sûr? Vous perdrez toute votre progression dans<br/>chaque niveau et votre score cumulé !</html>");
			JPanel avertissement_buttons = new JPanel();
			JButton avertissement_oui = new JButton("Oui");
			JButton avertissement_non = new JButton("Non");

			avertissement_text.setHorizontalAlignment(JLabel.CENTER);

			avertissement_oui.addActionListener((event2) ->{
				try {
					PrintWriter f1 = new PrintWriter("joueur.ser");
					f1.close();
					PrintWriter f2 = new PrintWriter("niveau.ser");
					f2.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				avertissement.dispose();
				dispose();
			});

			avertissement_non.addActionListener((event2) ->{
				avertissement.dispose();
				menuPrincipal();
			});

			avertissement_buttons.add(avertissement_oui);
			avertissement_buttons.add(avertissement_non);

			avertissement.add(avertissement_buttons, BorderLayout.SOUTH);
			avertissement.add(avertissement_text, BorderLayout.CENTER);

			avertissement.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			avertissement.setPreferredSize(new Dimension(400, 150));
			avertissement.setTitle("Avertissement");
			avertissement.pack();
			avertissement.setLocationRelativeTo(null);
			avertissement.setVisible(true);
		});

		credits.addActionListener((event) -> {
			credits();
		});

		retour.addActionListener((event) ->{
			dispose();
			String[] args = {};
			Main.main(args);
		});

		add(top_msg, BorderLayout.PAGE_START);
		add(panneau, BorderLayout.CENTER);

		revalidate();
		repaint();
	}

	//affiche la grille mise en commentaire
	public void displayGrid(Grille grille, Grille niveau_og){
		getContentPane().removeAll();

		JPanel grid_buttonlist = new JPanel();
			JButton[][] button_grid = new JButton[grille.largeur][grille.hauteur];
		JPanel grid_south_buttons = new JPanel();
			JButton grid_save = new JButton("Sauvegarder & quitter");
			JButton grid_load = new JButton("Charger");
			JButton grid_bot = new JButton("Laisser le robot jouer");
			JButton grid_help = new JButton("Aide");
			JButton grid_reset = new JButton("Recommencer");
			JButton grid_return = new JButton("Retour");
		
		GridLayout grid_layout = new GridLayout(grille.largeur, grille.hauteur);

		Grille grille2 = new Grille(grille.copiePlateau(), 0, 0);

		makingGrid(grid_buttonlist, button_grid, grille, grille2);

		grid_buttonlist.setLayout(grid_layout);

		grid_save.addActionListener((event) ->{
			try{
				FileOutputStream fos = new FileOutputStream("niveau.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(grille);
			}catch (Exception e){
				e.printStackTrace();
			}
			dispose();
		});

		grid_load.addActionListener((event) ->{
			try{
				FileInputStream fis = new FileInputStream("niveau.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				Grille grille3 = (Grille)ois.readObject();
				displayGrid(grille3, niveau_og);
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

		grid_bot.addActionListener((event) ->{
			JFrame fenetre = new JFrame();
			JLabel texte = new JLabel("Êtes-vous sûr ? Demander au robot de jouer <br>votre tour vous fait perdre 200 points...");
			JPanel buttons = new JPanel();
			JButton confirmer = new JButton("Oui");
			JButton retour = new JButton("Non");
	
			texte.setHorizontalAlignment(JLabel.CENTER);
	
			fenetre.add(texte, BorderLayout.CENTER);
	
			confirmer.addActionListener((event2) ->{
				Robot robot = new Robot(grille);
				int[] coordonnees = grille.aide();
				button_grid[coordonnees[0]][coordonnees[1]].doClick();
				grille.modele.score =- 200;
				fenetre.dispose();
			});

			retour.addActionListener((event2) ->{
				fenetre.dispose();
			});
	
			buttons.add(confirmer);
			buttons.add(retour);
			fenetre.add(buttons, BorderLayout.PAGE_END);
	
			fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			fenetre.setPreferredSize(new Dimension(400, 150));
			fenetre.setTitle("Avertissement");
			fenetre.pack();
			fenetre.setLocationRelativeTo(null);
			fenetre.setVisible(true);
		});

		grid_help.addActionListener((event) ->{
			grille.modele.score -= 100;
			
			int[] aide = grille.aide();

			aide[0]++;
			aide[1]++;

			JFrame help = new JFrame();
			JLabel help_text = new JLabel("<html>La case aux coordonnées [" + aide[0] + ", " + aide[1] + "] est jouable...<br/>Afin de connaitre les coordonnées d'une case, survolez-le avec la souris...<br>Avoir demander de l'aide vous donne un malus de 100 points<br>sur votre prochaine suppression !</html>");
			JButton help_return = new JButton("Retour");

			help_text.setHorizontalAlignment(JLabel.CENTER);

			help.add(help_text, BorderLayout.CENTER);

			help_return.addActionListener((event2) ->{
				help.dispose();
			});

			help.add(help_return, BorderLayout.PAGE_END);

			help.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			help.setPreferredSize(new Dimension(400, 150));
			help.setTitle("Aide");
			help.pack();
			help.setLocationRelativeTo(null);
			help.setVisible(true);

		});

		grid_reset.addActionListener((event) ->{
			grille2.plateau = grille2.copiePlateau();
			displayGrid(grille2, niveau_og);
		});

		grid_return.addActionListener((event) ->{
			selectLevel();
		});
		
		grid_south_buttons.add(grid_save);
		grid_south_buttons.add(grid_load);
		grid_south_buttons.add(grid_bot);
		grid_south_buttons.add(grid_help);
		grid_south_buttons.add(grid_reset);
		grid_south_buttons.add(grid_return);

		add(grid_south_buttons, BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

	//fonction récursive pour l'affichage du plateau
	public void makingGrid(JPanel grid_buttonlist, JButton[][] button_grid, Grille grille, Grille grille2){
		JPanel top_components = new JPanel();

		JLabel tour = new JLabel();
		if(grille.modele.tour_max == 0){
			tour.setText("Tour " + grille.modele.tour);
		}else{
			tour.setText("Tour " + grille.modele.tour + "/" + grille.modele.tour_max);
		}

		JLabel score = new JLabel("Score : " + grille.modele.score);
		JLabel animaux_restants = new JLabel("Nombre d'animaux restants : " + grille.nbAnimaux);
		JLabel grid_scoreMax = new JLabel("Score maximal atteignable : " + grille.modele.score_max);

		top_components.add(grid_scoreMax);
		top_components.add(score);
		top_components.add(animaux_restants);
		top_components.add(tour);

		for(int i = 0; i < grille.largeur; i++){
			for(int j = 0; j < grille.hauteur; j++){
				JButton piece = new JButton();
				piece.setName(i + "," + j);
				piece.setToolTipText(String.valueOf(i + 1) + ", " + String.valueOf(j + 1));
				piece.setSize(new Dimension(10, 10));
				piece.addActionListener((event) -> {
					String s = "";
					int c = 0;
					while(piece.getName().charAt(c) != ','){
						s += piece.getName().charAt(c);
						c++;
					}int x = Integer.parseInt(s);
					int y = Integer.parseInt(String.valueOf(piece.getName().charAt(piece.getName().length()-1)));
					if(grille.peutSupprimer(x, y)){
						grille.modele.tour++;

						grille.supprime(x, y);
						grille.graviteRecursive();
						grid_buttonlist.removeAll();
						makingGrid(grid_buttonlist, button_grid, grille, grille2);
						grid_buttonlist.setLayout(new GridLayout(grille.largeur, grille.hauteur));
						grid_buttonlist.revalidate();
						grid_buttonlist.repaint();
						if(grille.nbAnimaux == 0){
							joueur.addScore(grille.modele.score);
							try{
								FileOutputStream fos = new FileOutputStream("joueur.ser");
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								oos.writeObject(joueur);
							}catch (Exception e){
								e.printStackTrace();
							}
							if(grille2.comparaison(niveau.plateau_niveau1))
								joueur.niveau_etat[1] = true;
							if(grille2.comparaison(niveau.plateau_niveau2))
								joueur.niveau_etat[2] = true;
							if(grille2.comparaison(niveau.plateau_niveau3))
								joueur.niveau_etat[3] = true;
							try {
								FileOutputStream fos = new FileOutputStream("joueur.ser");
								ObjectOutputStream oos = new ObjectOutputStream(fos);
								oos.writeObject(joueur);
							}catch(Exception e){
								e.printStackTrace();
							}
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
							grid_victoire.setTitle("Victoire");
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
				if(grille.plateau[i][j].piece instanceof Cube.Rouge){
					piece.setBackground(Color.RED);
				}else if(grille.plateau[i][j].piece instanceof Cube.Vert){
					piece.setBackground(Color.GREEN);
				}else if(grille.plateau[i][j].piece instanceof Cube.Bleu){
					piece.setBackground(Color.BLUE);
				}else if(grille.plateau[i][j].piece instanceof Cube.Jaune){
					piece.setBackground(Color.YELLOW);
				}else if(grille.plateau[i][j].piece instanceof Cube.Orange){
					piece.setBackground(Color.ORANGE
					);
				}else if(grille.plateau[i][j].piece instanceof Animal && grille.plateau[i][j].estVide == false){
					piece.setBackground(Color.WHITE);
					try{
						Image img = ImageIO.read(getClass().getResource("ressources/animal.jpg")).getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
						piece.setIcon(new ImageIcon(img));
					}catch(IOException e){
						e.printStackTrace();
					}
				}if(grille.plateau[i][j].estVide){
					piece.setBackground(Color.WHITE);
				}if(grille.plateau[i][j].piece instanceof Obstacle){
					piece.setBackground(Color.BLACK);
				}
				button_grid[i][j] = piece;
				grid_buttonlist.add(button_grid[i][j]);
			}
		}

		add(grid_buttonlist, BorderLayout.CENTER);
		add(top_components, BorderLayout.NORTH);
	}

	private void rules() {

		JLabel rules_text = new JLabel("<html>Le but est simple : il s'agit de libérer les animaux bloqués en haut des boites en détruisant ces dernières.<br/>Les boîtes destructibles sont celles qui ont au moins une boîte de même couleur en adjacence.<br/>Faite descendre tous les animaux en bas de l'écran et vous gagnez!<br/>Les cases noires désignent les obstacles et les cases blanches sont vides, et ne peuvent donc pas être sélecitonné<br>Il est possible d'obtenir de l'aide en laissant un robot joué ou en affichant une case supprimable, mais vous subirez un malus de points...</html>");
		JButton rules_return = new JButton("Retour");

		getContentPane().removeAll();

		rules_return.addActionListener((event) ->{
			menuPrincipal();
		});

		add(rules_return, BorderLayout.SOUTH);
		add(rules_text, BorderLayout.CENTER);

		revalidate();
		repaint();
	}
	
	private void selectLevel(){	
		JPanel top_panel = new JPanel();
			JButton unlock = new JButton("Débloquer tous les niveaux.");
				unlock.addActionListener((event) ->
				{
					for(int i = 0; i < joueur.niveau_etat.length; i++){
						joueur.niveau_etat[i] = true;
					}
					try{
						FileOutputStream fos = new FileOutputStream("joueur.ser");
						ObjectOutputStream oos = new ObjectOutputStream(fos);
						oos.writeObject(joueur);
					}catch(Exception e){
						e.printStackTrace();
					}
					selectLevel();
				});

		JPanel level_panel = new JPanel();
		GridLayout level_layout = new GridLayout(0, 1);
			JLabel level_text = new JLabel("Choisissez votre niveau de difficulté.");
				level_text.setHorizontalAlignment(JLabel.CENTER);
			JButton niveau1 = new JButton("Niveau 1");
				niveau1.setBackground(Color.GREEN);
			JButton niveau2 = new JButton("Niveau 2");
				niveau2.setBackground(Color.ORANGE);
			JButton niveau3 = new JButton("Niveau 3");
				niveau3.setBackground(Color.ORANGE);
			JButton niveau4 = new JButton("Niveau 4");
				niveau4.setBackground(Color.RED);

		JPanel level_panel2 = new JPanel();
			JButton level_return = new JButton("Retour");

		getContentPane().removeAll();

		if(!joueur.niveau_etat[0])
			niveau1.setEnabled(false);
		if(!joueur.niveau_etat[1])
			niveau2.setEnabled(false);
		if(!joueur.niveau_etat[2])
			niveau3.setEnabled(false);
		if(!joueur.niveau_etat[3])
			niveau4.setEnabled(false);

		top_panel.add(level_text);
		top_panel.add(unlock);
		level_panel.add(niveau1);
		level_panel.add(niveau2);
		level_panel.add(niveau3);
		level_panel.add(niveau4);
		level_panel2.add(level_return);

		level_panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		level_return.addActionListener((event) ->{
			menuPrincipal();
		});
	
		niveau1.addActionListener((event)->{
			Grille grille = new Grille(niveau.plateau_niveau1, 0, 0);
			grille.plateau = grille.copiePlateau();
			displayGrid(grille, niveau.niveau1);
		});
			
		niveau2.addActionListener((event)->{
			Grille grille = new Grille(niveau.plateau_niveau2, 0, 0);
			grille.plateau = grille.copiePlateau();
			displayGrid(grille, niveau.niveau2);
		});
			
		niveau3.addActionListener((event)->{
			Grille grille = new Grille(niveau.plateau_niveau3, 0, 0);
			grille.plateau = grille.copiePlateau();
			displayGrid(grille, niveau.niveau3);
		});

		niveau4.addActionListener((event) ->{
			Grille grille = new Grille(niveau.plateau_niveau42, 0, 0);
			grille.plateau = grille.copiePlateau();
			displayGrid(grille, niveau.niveau4);
		});

		add(level_panel, BorderLayout.CENTER);
		add(top_panel, BorderLayout.NORTH);
		add(level_panel2, BorderLayout.SOUTH);

		level_layout.setVgap(30);
		level_panel.setLayout(level_layout);

		revalidate();
		repaint();
	}
	
	private void credits() {
		getContentPane().removeAll();

		JLabel credits_text = new JLabel("<html>Programme crée par Bilal Seddiki et Gaspard Romele dans le cadre d'un projet de seconde année de licence informatique, 2021.<br/>Niveaux tirés du jeu originel développé par King.</html>");
			credits_text.setHorizontalAlignment(JLabel.CENTER);
		JPanel credits_panel = new JPanel();
		JButton credits_return = new JButton("Retour");
		
		BorderLayout credits_layout = new BorderLayout();
		credits_panel.setLayout(credits_layout);

		credits_panel.add(credits_return);

		credits_return.addActionListener((event) ->{
			menuPrincipal();
		});

		add(credits_panel, BorderLayout.SOUTH);
		add(credits_text, BorderLayout.CENTER);

		revalidate();
		repaint();
	}

	public void notificationFenetre(String title, String text, String button){
		JFrame fenetre = new JFrame();
		JLabel texte = new JLabel(text);
		JButton retour = new JButton(button);

		texte.setHorizontalAlignment(JLabel.CENTER);

		fenetre.add(texte, BorderLayout.CENTER);

		retour.addActionListener((event) ->{
			fenetre.dispose();
		});

		fenetre.add(retour, BorderLayout.PAGE_END);

		fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		fenetre.setPreferredSize(new Dimension(400, 150));
		fenetre.setTitle(title);
		fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
	}
}
