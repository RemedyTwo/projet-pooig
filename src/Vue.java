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

	public void displayGrid(Grille grille){
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

		makingGrid(grid_buttonlist, button_grid, grille);

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
				Grille grille2 = (Grille)ois.readObject();
				displayGrid(grille2);
			}catch(Exception e){
				notificationFenetre("Erreur", "Aucune sauvegarde trouvée !", "Retour");
			}
		});

		grid_bot.addActionListener((event) ->{
			JFrame fenetre = new JFrame();
			JLabel texte = new JLabel("Êtes-vous sûr ? Vous perdrez 200 points...");
			JPanel buttons = new JPanel();
			JButton confirmer = new JButton("Oui");
			JButton retour = new JButton("Non");
	
			texte.setHorizontalAlignment(JLabel.CENTER);
	
			fenetre.add(texte, BorderLayout.CENTER);
	
			confirmer.addActionListener((event2) ->{
				Robot robot = new Robot(grille);
				robot.doLevel();
				displayGrid(robot.grille);
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
			grille.modele.score -= 200;
			
			int[] aide = grille.aide();

			aide[0]++;
			aide[1]++;

			String text = "<html>La case aux coordonnées [" + aide[0] + ", " + aide[1] + "] est jouable...<br/>Afin de connaitre les coordonnées d'une case, survolez-le avec la souris...</html>";
			notificationFenetre("Aide", text, "Retour");
		});

		Grille grille2 = new Grille(grille.copiePlateau(), 0, 0);

		grid_reset.addActionListener((event) ->{
			grille2.plateau = grille2.copiePlateau();
			displayGrid(grille2);
		});

		grid_return.addActionListener((event) ->{
			selectLevel();
		});
		
		grid_south_buttons.add(grid_save);
		grid_south_buttons.add(grid_load);
		grid_south_buttons.add(grid_help);
		grid_south_buttons.add(grid_bot);
		grid_south_buttons.add(grid_reset);
		grid_south_buttons.add(grid_return);

		add(grid_south_buttons, BorderLayout.SOUTH);

		revalidate();
		repaint();
	}

	public void makingGrid(JPanel grid_buttonlist, JButton[][] button_grid, Grille grille){
		for(int i = 0; i < grille.largeur; i++){
			for(int j = 0; j < grille.hauteur; j++){
				JButton piece = new JButton();
				piece.setName(i + "" + j);
				piece.setToolTipText(String.valueOf(i + 1) + ", " + String.valueOf(j + 1));
				piece.setSize(new Dimension(10, 10));
				piece.addActionListener((event) -> {
					int x = Integer.parseInt(String.valueOf(piece.getName().charAt(0)));
					int y = Integer.parseInt(String.valueOf(piece.getName().charAt(1)));
					if(grille.peutSupprimer(x, y)){
						grille.modele.tour++;

						grille.supprime(x, y);
						grille.graviteRecursive();
						grid_buttonlist.removeAll();
						makingGrid(grid_buttonlist, button_grid, grille);
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
		JPanel level_panel = new JPanel();
		GridLayout level_layout = new GridLayout(0, 1);
	
		JPanel level_panel2 = new JPanel();
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
		JButton level_return = new JButton("Retour");

		getContentPane().removeAll();

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
			Grille grille = niveau.niveau1;
			grille.plateau = grille.copiePlateau();
			displayGrid(grille);
		});
			
		niveau2.addActionListener((event)->{
			Grille grille = niveau.niveau2;
			grille.plateau = grille.copiePlateau();
			displayGrid(grille);
		});
			
		niveau3.addActionListener((event)->{
			Grille grille = niveau.niveau3;
			grille.plateau = grille.copiePlateau();
			displayGrid(grille);
		});

		niveau4.addActionListener((event) ->{
			Grille grille = niveau.niveau4;
			grille.plateau = grille.copiePlateau();
			displayGrid(grille);
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
