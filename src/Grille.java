import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Grille implements Serializable{
//Ce code gère la grille du jeu.

	Scanner scanner;
	public Case[][]plateau;
	public int largeur;
	public int hauteur;
	public int nbAnimaux;
	public Modele modele;

	public Grille(Case[][] plateau, int score_min, int tour_max) {
		this.plateau = plateau;

		largeur = plateau.length;
		hauteur = plateau[0].length;

		nbAnimaux = 0;
		for(int i = 0; i < largeur; i++){
			for(int j = 0; j < hauteur; j++){
				if(plateau[i][j].piece instanceof Animal){
					nbAnimaux++;
				}
			}
		}
		
		modele = new Modele(this, score_min, tour_max);
	}

	//lorsqu'un animal est au niveau le plus bas du plateau, on le supprime.
	public void AnimalAuSol() {
		int i = plateau.length - 1;
		for(int j = 0; j < plateau[i].length; j++) {
			if(plateau[i][j].piece instanceof Animal && plateau[i][j].estVide == false) {
				plateau[i][j].estVide = true;
				this.nbAnimaux--;
			}
		}
	}

	//fonction récursive qui donne la liste des adjacentes d'un cube de la même couleur que celui-ci dans les coordonnées du plateau
	public int[][] casesAdjacentes(int x, int y, ArrayList<int[]> listeAIgnorer, ArrayList<int[]> casesAdjacentes){
		if(casesAdjacentes == null && listeAIgnorer == null){
			casesAdjacentes = new ArrayList<>();
			listeAIgnorer = new ArrayList<>();
		}
		int[][] adj = adjacentes(x, y);
		int[] coordonnees = {x, y};
		
		listeAIgnorer.add(coordonnees);;
		for(int i = 0; i < adj.length; i++){
			int[] tmp = {adj[i][0], adj[i][1]};
			if(verifierSiDejaPasse(listeAIgnorer, tmp))
				if(plateau[tmp[0]][tmp[1]].piece instanceof Cube){
					int[] a = {adj[i][0], adj[i][1]};
					casesAdjacentes.add(a);
					casesAdjacentes(adj[i][0], adj[i][1], listeAIgnorer, casesAdjacentes);
			}
		}
		int[][] casesAdjacentesTableau = casesAdjacentes.toArray(new int[0][0]);
		return casesAdjacentesTableau;
	}
		
	//retourne un tableau listant les adjacentes des coordonnées d'un point. ce tableau passe par un filtre
	public int[][] adjacentes(int x, int y){ 
		int[][] adjacentes = {
			{x, y+1},
			{x+1, y},
			{x, y-1},
			{x-1, y}
		};
		return filtreAdjacentesCoordonnees(adjacentes, x, y);
	}

	//retourne le tableau d'adjacentes avec les coordonnées qui dépassent la taille du tableau retirés, ce tableau passe un dernier filtre
	public int[][] filtreAdjacentesCoordonnees(int[][] adjacentes, int x, int y){ 
		ArrayList<Integer> filtre = new ArrayList<>();
		for(int i = 0; i < adjacentes.length; i++){
				if(adjacentes[i][0] < largeur && adjacentes[i][1] < hauteur){
					filtre.add(i);
				}
		}
		int[][] adjacentesFiltres = new int[filtre.size()][2];
		for(int i = 0; i < filtre.size(); i++){
			for(int j = 0; j < 2; j++){
				adjacentesFiltres[i][j] = adjacentes[filtre.get(i)][j];
			}
		}
		return filtreAdjacentesCouleur(adjacentesFiltres, x, y);
	}

	//retourne le tableau d'adjancentes filtré avec les coordoonées des pièces d'une différente couleur retirées
	public int[][] filtreAdjacentesCouleur(int[][] adjacentes, int x, int y){ 
		ArrayList<Integer> filtre = new ArrayList<>();
		for(int i = 0; i < adjacentes.length; i++){
			try{
				if(plateau[adjacentes[i][0]][adjacentes[i][1]].piece.nom == plateau[x][y].piece.nom){
				filtre.add(i);
				}
			}catch(Exception e){}
		}
		int[][] adjacentesFiltres = new int[filtre.size()][2];
		for(int i = 0; i < filtre.size(); i++){
			for(int j = 0; j < 2; j++){
				adjacentesFiltres[i][j] = adjacentes[filtre.get(i)][j];
			}
		}
		return adjacentesFiltres;
	}
	
	//vérifie si une case a été analysée précédemment l'a déjà été, permet de les skip pour éviter une récursivité infinie
	public boolean verifierSiDejaPasse(ArrayList<int[]> listeAIgnorer, int[] tmp){
		for(int i = 0; i < listeAIgnorer.size(); i++){
			int[] coordonnes = listeAIgnorer.get(i);
			if(coordonnes[0] == tmp[0] && coordonnes[1] == tmp[1]){
				return false;
			}
		}
		return true;
	}
	//vérifie si les points données en coordonnées peuvent être supprimés
	public boolean peutSupprimer(int x, int y){
		int[][] adjacents = adjacentes(x, y);
		if(adjacents.length > 0 && !(plateau[x][y].piece instanceof Animal) && !(plateau[x][y].piece instanceof Obstacle) && !plateau[x][y].estVide){
			return true;
		}
		return false;
	}

	//supprime la case situé aux coordonnées mises en argument et ses adjacents, et ajoute +100 points score à chaque suppression
	public void supprime(int x, int y){
		int[][] adjacents = (casesAdjacentes(x, y, null, null));
		plateau[x][y].estVide = true;
		modele.score += 100;
		for(int i = 0; i < adjacents.length; i++){
			plateau[adjacents[i][0]][adjacents[i][1]].estVide = true;
			modele.score += 100;
		}
	}

	//compte le nombre de cube+animal non vide dans la colonne au dessus des coordonnées 
	public int nombrePieceColonneAuDessus(int x, int y){
		int nbColonnes = 0;
		for(int i = 0; i < x; i++){
			if(!plateau[i][y].estVide && !(plateau[i][y].piece instanceof Obstacle)){
				nbColonnes++;
			}
		}
		return nbColonnes;
	}

	//de même que ci-dessus sans les animaux
	public int nombrePieceColonneAuDessusSansAnimaux(int x, int y){
		int nbColonnes = 0;
		for(int i = 0; i < x; i++){
			if(!plateau[i][y].estVide && !(plateau[i][y].piece instanceof Obstacle) && !(plateau[i][y].piece instanceof Animal)){
				nbColonnes++;
			}
		}
		return nbColonnes;
	}

	//compte le nombre de cube+animal non vide à droite des coordonnées citées
	public int nombrePieceLigneADroite(int x, int y){
		int nbLignes = 0;
		for(int j = hauteur - 1; j > y; j--){
			if(!plateau[x][j].estVide && !(plateau[x][j].piece instanceof Obstacle)){
				nbLignes++;
			}
		}
		return nbLignes;
	}

	//applique la gravité au plateau, càd fait tomber les cases au dessus de case vide
	public void graviteVerticale(){ //applique la gravité
		for(int i = largeur - 1; i > 0; i--){
			for(int j = hauteur - 1 ; j >= 0; j--){
				if(!(plateau[i][j].piece instanceof Obstacle) && plateau[i][j].estVide){
					int nb = nombrePieceColonneAuDessus(i, j);
					if(nb > 0){
						int r = i - 1;
						while(plateau[i][j].estVide){
							if(!(plateau[r][j].piece instanceof Obstacle)){
								Case tmp = plateau[i][j];
								plateau[i][j] = plateau[r][j];
								plateau[r][j] = tmp;
							}
							r--;
						}
					}
				}
			}
		}
	}

	public void graviteHorizontale(){
		int a = largeur - 1;
		for(int j = 0; j < hauteur - 1; j++){
			if(plateau[a][j].piece instanceof Obstacle){
				while(plateau[a][j].piece instanceof Obstacle){
					a--;
				}
			}
			if(plateau[a][j].estVide){
				int nb = nombrePieceColonneAuDessusSansAnimaux(largeur - 1, j+1);
				if(nb == 0){
					for(int i = 0; i < largeur; i++){
						if(!(plateau[i][j].piece instanceof Obstacle) && !(plateau[i][j+1].piece instanceof Obstacle) && !plateau[i][j+1].estVide){
							Case tmp = plateau[i][j];
							plateau[i][j] = plateau[i][j+1];
							plateau[i][j+1] = tmp;
						}
					}
				}
			}
		}
	}

	public void graviteHorizontale_recursive(){
		Case[][] plateau_copie = copiePlateau();
		graviteHorizontale();
		if(!comparaison(plateau_copie)){
			graviteHorizontale_recursive();
		}
	}

	public void graviteRecursive(){
		Case[][] plateau_copie = copiePlateau();
		graviteVerticale();
		graviteHorizontale_recursive();
		AnimalAuSol();
		if(!comparaison(plateau_copie)){
			graviteRecursive();
		}
	}

	public Case[][] copiePlateau() {
		Case[][] plateau_copie = new Case[largeur][hauteur];
		for(int i = 0; i < largeur; i++){
			for(int j = 0; j < hauteur; j++){
				plateau_copie[i][j] = new Case(new Piece(""));
			}
		}
		for(int i = 0; i < largeur; i++){
			for(int j = 0; j < hauteur; j++){
				if(plateau[i][j].piece instanceof Obstacle){
					plateau_copie[i][j] = new Case(new Obstacle());
				}if(plateau[i][j].piece instanceof Animal){
					plateau_copie[i][j] = new Case(new Animal());
				}if(plateau[i][j].piece instanceof Cube){
					if(plateau[i][j].piece instanceof Cube.Rouge){
						plateau_copie[i][j] = new Case(new Cube.Rouge());
					}if(plateau[i][j].piece instanceof Cube.Vert){
						plateau_copie[i][j] = new Case(new Cube.Vert());
					}if(plateau[i][j].piece instanceof Cube.Bleu){
						plateau_copie[i][j] = new Case(new Cube.Bleu());
					}if(plateau[i][j].piece instanceof Cube.Jaune){
						plateau_copie[i][j] = new Case(new Cube.Jaune());
					}if(plateau[i][j].piece instanceof Cube.Orange){
						plateau_copie[i][j] = new Case(new Cube.Orange());
					}
				}if(plateau[i][j].estVide || plateau[i][j].piece.nom == "vide"){
					plateau_copie[i][j].estVide = true;
				}else{
					plateau_copie[i][j].estVide = false;
				}
			}
		}
		return plateau_copie;
	}

	public boolean comparaison(Case[][] plateau_copie){
		for(int i = 0; i < largeur; i++){
			for(int j = 0; j < hauteur; j++){
				if(plateau[i][j].piece instanceof Obstacle && !(plateau_copie[i][j].piece instanceof Obstacle)){
					return false;
				}if(plateau[i][j].piece instanceof Animal && !(plateau_copie[i][j].piece instanceof Animal)){
					return false;
				}if(plateau[i][j].piece instanceof Cube && !(plateau_copie[i][j].piece instanceof Cube)){
					if(plateau[i][j].piece.nom == "rouge" && !(plateau_copie[i][j].piece.nom == "rouge")){
						return false;
					}if(plateau[i][j].piece.nom == "vert" && !(plateau_copie[i][j].piece.nom == "vert")){
						return false;
					}if(plateau[i][j].piece.nom == "bleu" && !(plateau_copie[i][j].piece.nom == "bleu")){
						return false;
					}if(plateau[i][j].piece.nom == "jaune" && !(plateau_copie[i][j].piece.nom == "jaune")){
						return false;
					}
				}if(plateau[i][j].estVide && !(plateau_copie[i][j].estVide)){
					return false;
				}
			}
		}
		return true;
	}

	public int scoreMax(){
		int scoreMax = 0;
		for(int i = 0; i < largeur; i++){
			for(int j = 0; j < hauteur; j++){
				if(plateau[i][j].piece instanceof Cube){
					scoreMax += 100;
				}
			}
		}
		return scoreMax;
	}

	public int[] aide(){
		int[] aide = new int[2];
		for(int i = 0; i < largeur; i++){
			for(int j = 0; j < hauteur; j++){
				if(peutSupprimer(i, j)){
					aide[0] = i;
					aide[1] = j;
				}
			}
		}
		return aide;
	}

	public void tour(){
		//On joue un tour en entrant 2 entiers correspondant aux coordonnées d'un cube. si le cube sélectionné ne peut pas être supprimé, on relance le tour. A la fin du tour on vérifie si le jeu n'est pas fini et, dans le cas où il ne l'est pas, on joue le tour suivant.
		affichage();
		scanner = new Scanner(System.in); 
		System.out.print("\nSélectionnez la ligne de la case à supprimer : ");
		int x = scanner.nextInt() - 1;
		System.out.print("\nSélectionnez la colonne de la case à supprimer : ");
		int y = scanner.nextInt() - 1;
		System.out.print("\n");
		if(peutSupprimer(x,y)){
			supprime(x,y);
			graviteRecursive();
		}else{
			System.out.println("Vous ne pouvez pas supprimer ce bloc.\n");
		}
		if(nbAnimaux >= 0){
			tour();
		}else{
			if(modele.score_min == 0 || modele.score_min < modele.score){
				System.out.println("Vous avez gagné !");
			}
		}
	}

	public void affichage(){
	//Cette fonction affiche la grille actuelle.
		System.out.print("  | ");
		for(int i = 0; i < hauteur; i++){
			System.out.print(i + 1 + " | ");
		}
		System.out.println("");
		for(int i = 0; i < largeur; i++){ // ligne
			if(i < 9){
				System.out.print(i + 1 + "  | ");
			}else{
				System.out.print(i + 1 + " | ");
			}
			for(int j = 0; j < hauteur; j++){ // colonne
				if(!plateau[i][j].estVide){
					if(plateau[i][j].piece instanceof Animal){
						System.out.print("A");
					}
					if(plateau[i][j].piece instanceof Cube){
						System.out.print(plateau[i][j].piece.nom.charAt(0));
					}
					if(plateau[i][j].piece instanceof Obstacle){
						System.out.print("O");
					}
				}
				if(plateau[i][j].estVide){
					System.out.print("/");
				}
				System.out.print(" | ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}
