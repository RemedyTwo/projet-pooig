import java.util.ArrayList;

public class Grille {
//Ce code gère la grille du jeu.

	public Case[][]plateau;
	public int hauteur, largeur, nbAnimaux;

	public Grille(int l,int h, int nbAnimaux) {
		this.largeur = l;
		this.hauteur = h;
		this.plateau = new Case[l][h];
		this.nbAnimaux = nbAnimaux;
	}

	public void AnimalAuSol() {
	//Lorsqu'un animal est au niveau le plus bas du plateau, on le supprime.
		int i = plateau.length - 1;
		for(int j = 0; j < plateau[i].length; j++) {
			if(plateau[i][j].piece instanceof Animal && plateau[i][j].estVide == false) {
				plateau[i][j].estVide = true;
				this.nbAnimaux--;
			}
		}
	}
	
	public boolean peutSupprimer(int x,int y){//vérifie s'il existe au moins une adjacente à un point pour savoir s'il peut etre supprimé
		int[][] adjacents = adjacentes(x, y);
		if(adjacents.length > 0 && !(plateau[x][y].piece instanceof Animal) && !(plateau[x][y].piece instanceof Obstacle) && !plateau[x][y].estVide){
			return true;
		}
		return false;
	}

	public int[][] casesAdjacentesIgnorer(int x, int y, ArrayList<int[]> listeAIgnorer, ArrayList<int[]> casesAdjacentes){
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
					casesAdjacentesIgnorer(adj[i][0], adj[i][1], listeAIgnorer, casesAdjacentes);
			}
		}
		int[][] casesAdjacentesTableau = casesAdjacentes.toArray(new int[0][0]);
		return casesAdjacentesTableau;
	}
		
	public int[][] adjacentes(int x, int y){ //retourne un tableau listant les adjacentes des coordonnées d'un point. ce tableau passe par un filtre
		int[][] adjacentes = {
			{x, y+1},
			{x+1, y},
			{x, y-1},
			{x-1, y}
		};
		return filtreAdjacentesCoordonnees(adjacentes, x, y);
	}

	public int[][] filtreAdjacentesCoordonnees(int[][] adjacentes, int x, int y){ //retourne le tableau d'adjacentes avec les coordonnées qui dépassent la taille du tableau retirés, ce tableau passe un dernier filtre
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

	public int[][] filtreAdjacentesCouleur(int[][] adjacentes, int x, int y){ //retourne le tableau d'adjancentes filtré avec les coordoonées des pièces d'une différente couleur retirées
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
	
	public boolean verifierSiDejaPasse(ArrayList<int[]> listeAIgnorer, int[] tmp){//Vérifie si une case a été analysée précédemment.
		for(int i = 0; i < listeAIgnorer.size(); i++){
			int[] coordonnes = listeAIgnorer.get(i);
			if(coordonnes[0] == tmp[0] && coordonnes[1] == tmp[1]){
				return false;
			}
		}
		return true;
	}

	public void supprime(int x, int y){ //supprime un point et ses adjacents de la même couleur
		int[][] adjacents = (casesAdjacentesIgnorer(x, y, null, null));
		plateau[x][y].estVide = true;
		for(int i = 0; i < adjacents.length; i++){
			plateau[adjacents[i][0]][adjacents[i][1]].estVide = true;;
		}
	}

	public int nombrePieceColonneAuDessus(int x, int y){ //compte le nombre de pièces dans une colonne 
		int nbColonnes = 0;
		for(int i = 0; i < x; i++){
			if(!plateau[i][y].estVide && !(plateau[i][y].piece instanceof Obstacle)){
				nbColonnes++;
			}
		}
		return nbColonnes;
	}

	public int nombrePieceLigneADroite(int x, int y){
		int nbLignes = 0;
		for(int j = hauteur - 1; j > y; j--){
			if(!plateau[x][j].estVide && !(plateau[x][j].piece instanceof Obstacle)){
				nbLignes++;
			}
		}
		return nbLignes;
	}

	//applique la gravité, pour se faire, la fonction traverse le plateau en commencant par le coin inférieur droit colonne par colonne puis ligne par ligne.
	//si ce n'est pas un obstacle, on continue. 
	public void graviteVerticale(){ //applique la gravité
		for(int i = largeur - 1; i > 0; i--){
			for(int j = hauteur - 1 ; j >= 0; j--){
				if(!(plateau[i][j].piece instanceof Obstacle) && plateau[i][j].estVide){
					int nb = nombrePieceColonneAuDessus(i, j);
					if(nb > 0){
						int r = i - 1;
						while(plateau[i][j].estVide){
							Case tmp = plateau[i][j];
							plateau[i][j] = plateau[r][j];
							plateau[r][j] = tmp;
							r--;
						}
					}
				}
			}
		}
	}

	public boolean colonneVide(int y){
		for(int i = 0; i < largeur; i++){
			if(!plateau[i][y].estVide){
				return false;
			}
		}
		return true;
	}

	public void graviteHorizontale(){
		for(int i = largeur - 1; i > 0; i--){
			for(int j = hauteur - 1 ; j >= 0; j--){
				if(!(plateau[i][j].piece instanceof Obstacle) && plateau[i][j].estVide){
					int nb = nombrePieceLigneADroite(i, j);
					if(nb > 0){
						int r = j + 1;
						while(plateau[i][j].estVide){
							Case tmp = plateau[i][j];
							plateau[i][j] = plateau[i][r];
							plateau[i][r] = tmp;
							r++;
						}
					}
				}
			}
		}
	}

	public Case[][] copiePlateau() {
		Case[][] plateau_copie = new Case[largeur][hauteur];
		for(int i = 0; i < largeur; i++){
			for(int j = 0; j < hauteur; j++){
				if(plateau[i][j].piece instanceof Obstacle){
					plateau_copie[i][j] = new Case(new Obstacle());
				}if(plateau[i][j].piece instanceof Animal){
					plateau_copie[i][j] = new Case(new Animal());
				}if(plateau[i][j].piece instanceof Cube){
					if(plateau[i][j].piece.nom == "rouge"){
						plateau_copie[i][j] = new Case(new Cube.Rouge());
					}if(plateau[i][j].piece.nom == "vert"){
						plateau_copie[i][j] = new Case(new Cube.Vert());
					}if(plateau[i][j].piece.nom == "bleu"){
						plateau_copie[i][j] = new Case(new Cube.Bleu());
					}if(plateau[i][j].piece.nom == "jaune"){
						plateau_copie[i][j] = new Case(new Cube.Jaune());
					}
				}if(plateau[i][j].estVide){
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

	public void gravite(){
		Case[][] plateau_copie = copiePlateau();
		graviteVerticale();
		graviteHorizontaleRecursive();
		AnimalAuSol();
		if(!comparaison(plateau_copie)){
			gravite();
		}
	}

	public void graviteHorizontaleRecursive(){
		Case[][] plateau_copie = copiePlateau();
		graviteHorizontale();
		if(!comparaison(plateau_copie)){
			graviteHorizontaleRecursive();
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
			System.out.print(i + 1 + " | ");
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
