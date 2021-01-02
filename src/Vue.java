import javax.swing.*;
import java.awt.*;

public class Vue{
//gère l'affichage de l'interface graphique du jeu
	public Grille grille;
	public Modele modele;
	public Controleur controleur;
	public Niveaux niveau = new Niveaux();
		
	JFrame frame = new JFrame();
		//JLabel label = new JLabel("bruh");
		JPanel panneau = new JPanel();
			JButton launch = new JButton("Lancer le jeu");
			JButton level_select = new JButton("Modifier la difficulté");
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

		launch.addActionListener((event) -> {
			Jeu();
		});

		rules.addActionListener((event) -> {
			rules();
		});
		
		level_select.addActionListener((event) -> {
			selectLevel();
		});
		
		credits.addActionListener((event) -> {
			credits();
		});

		frame.add(panneau, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Pet Rescue Saga");
		frame.setPreferredSize(new Dimension(800, 600));
		frame.setExtendedState( frame.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		frame.pack();
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

	private void Jeu(){
		/*Case[][] niveau2 = {
			{new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal())}, //ligne 1
			{new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 2
			{new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 3
			{new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune())}, //ligne 4
			{new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Rouge())}, //ligne 5
			{new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())}, //ligne 6
			{new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())}, //ligne 7
			{new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())} //ligne 8
		};
        Grille g = new Grille(niveau2.length, niveau2[0].length, 4);
        g.plateau = niveau2;
		displayGrid(g);*/
	}

	public void displayGrid(Grille g){
		frame.setVisible(false);
		
		JFrame grid_frame = new JFrame();
		JPanel grid_buttonlist = new JPanel();
		JButton[][] button_grid = new JButton[g.largeur][g.hauteur];
		JButton grid_return = new JButton("Retour");

		makingGrid(grid_frame, grid_buttonlist, button_grid, g);

		GridLayout grid_layout = new GridLayout(g.hauteur + 1, g.largeur + 1);
		grid_buttonlist.setLayout(grid_layout);

		grid_buttonlist.add(grid_return);

		grid_return.addActionListener((event) ->{
			frame.setVisible(true);
			grid_frame.dispose();
		});

		grid_frame.add(grid_buttonlist);

		grid_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		grid_frame.setPreferredSize(new Dimension(800, 600));
		grid_frame.pack();
		grid_frame.setLocationRelativeTo(null);
		grid_frame.setVisible(true);
	
	}

	public void makingGrid(JFrame grid_frame, JPanel grid_buttonlist, JButton[][] button_grid, Grille g){
		for(int i = 0; i < g.largeur; i++){
			for(int j = 0; j < g.hauteur; j++){
				//if(!g.plateau[i][j].estVide){
					JButton piece = new JButton(String.valueOf(i + ", " + j));
					piece.setName(i + "" + j);
					piece.setSize(new Dimension(10, 10));
					piece.addActionListener((event) -> {
						int x = Integer.parseInt(String.valueOf(piece.getName().charAt(0)));
						int y = Integer.parseInt(String.valueOf(piece.getName().charAt(1)));
						if(g.peutSupprimer(x, y)){
							g.supprime(x, y);
							grid_buttonlist.removeAll();
							makingGrid(grid_frame, grid_buttonlist, button_grid, g);
							grid_buttonlist.revalidate();
							grid_buttonlist.repaint();
							grid_frame.revalidate();
							grid_frame.repaint();
						}
					});
					if(g.plateau[i][j].piece.nom == "rouge"){
						piece.setBackground(Color.RED);
					}else if(g.plateau[i][j].piece.nom == "vert"){
						piece.setBackground(Color.GREEN);
					}else if(g.plateau[i][j].piece.nom == "bleu"){
						piece.setBackground(Color.BLUE);
					}else if(g.plateau[i][j].piece.nom == "jaune"){
						piece.setBackground(Color.YELLOW);
					}else if(g.plateau[i][j].piece instanceof Obstacle){
						piece.setBackground(Color.BLACK);
					}else if(g.plateau[i][j].estVide){
						piece.setBackground(Color.gray);
					}
					button_grid[i][j] = piece;
					grid_buttonlist.add(button_grid[i][j]);
				//}
			}
		}
	}

	private void rules() {
		frame.setVisible(false);

		JFrame rules_frame = new JFrame();
		JLabel rules_text = new JLabel("Le but est simple : il s'agit de libérer les animaux bloqués en haut des boites en détruisant celles-ci. Les boîtes destructibles sont celles qui ont au moins une boîte de même couleur en adjacence. Faite descendre tous les animaux en bas de l'écran et vous gagnez! Attention: les cases noires ne peuvent pas être supprimées.");
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
		rules_frame.setTitle("Pet Rescue Saga");
		rules_frame.pack();
		rules_frame.setLocationRelativeTo(null);
		rules_frame.setVisible(true);
	}
	
	private void selectLevel() {
		frame.setVisible(false);
		
		JPanel level_panel=new JPanel();
		level_panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		GridLayout level_layout = new GridLayout(0, 1);
		level_layout.setVgap(30);
		level_panel.setLayout(level_layout);
		
		JPanel level_panel2=new JPanel();
		JFrame level_frame= new JFrame();
		JLabel level_text= new JLabel("Choisissez votre niveau de difficulté.");
		JButton niveau1=new JButton("Facile : 5x5");
		JButton niveau2=new JButton("Normal : 10x10");
		JButton niveau3=new JButton("Difficile : 15x15");
		JButton level_return=new JButton("Retour");
		
		
		level_panel.add(niveau1);
		level_panel.add(niveau2);
		level_panel.add(niveau3);
		level_panel2.add(level_return);
		
		level_return.addActionListener((event) ->{
			frame.setVisible(true);
			level_frame.dispose();
		});
	
		niveau1.addActionListener((event)->{
			Grille grille=new Grille(5,5,5);
			this.grille=grille;
			frame.setVisible(true);
			level_frame.dispose();
		});
			
		niveau2.addActionListener((event)->{
			Grille grille=new Grille(10,10,10);
			this.grille=grille;
			frame.setVisible(true);
			level_frame.dispose();
		});
			
		niveau3.addActionListener((event)->{
			Grille g = new Grille(niveau.niveau2.length, niveau.niveau2[0].length, 4);
			g.plateau = niveau.niveau2;
			displayGrid(g);
		});
			
		level_frame.add(level_panel, BorderLayout.CENTER);
		level_frame.add(level_text, BorderLayout.NORTH);
		level_frame.add(level_panel2, BorderLayout.SOUTH);
		
		
		level_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		level_frame.setTitle("Pet Rescue Saga");
		level_frame.setPreferredSize(new Dimension(800, 600));
		level_frame.pack();
		level_frame.setLocationRelativeTo(null);
		level_frame.setVisible(true);
	}
	
	private void credits() {
		frame.setVisible(false);

		JFrame credits_frame = new JFrame();
		JLabel credits_text = new JLabel("programme crée par Bilal Seddiki et Gaspard Romele dans le cadre d'un projet de seconde année de licence informatique,2021");
		JPanel credits_panel = new JPanel();
		JButton credits_return = new JButton("Retour");
		
		BorderLayout credits_layout = new BorderLayout();
		credits_panel.setLayout(credits_layout);

		credits_panel.add(credits_return);

		credits_return.addActionListener((event) ->{
			frame.setVisible(true);
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
