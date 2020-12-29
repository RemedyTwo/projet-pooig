import java.util.ArrayList;
public class Grille {
//Ce code gère la grille du jeu.

	public Case[][]plateau;
	public int hauteur, largeur, nbAnimaux;
	//Le tableau cotes nous aidera à éliminer les cubes voisins de même couleur.
	
	public Grille(int l,int h, int nbAnimaux) {
		this.hauteur = h;
		this.largeur = l;
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
	
	public boolean peutSupprimer(int x,int y){//vérifie s'il existe au moins une adjacente à un point pour savoir s'il peut etre supprimé
		int[][] adjacents = adjacentes(x, y);
		if(adjacents.length > 0){
			return true;
		}
		return false;
	}

	public int[][] cases_adjacentes_ignorer(int x, int y, ArrayList<int[]> liste_à_ignorer, ArrayList<int[]> cases_adjacentes){
		if(cases_adjacentes == null && liste_à_ignorer == null){
			cases_adjacentes = new ArrayList<>();
			liste_à_ignorer = new ArrayList<>();
		}
		int[][] adj = adjacentes(x, y);
		int[] coordonnees = {x, y};
		
		liste_à_ignorer.add(coordonnees);;
		for(int i = 0; i < adj.length; i++){
			int[] tmp = {adj[i][0], adj[i][1]};
			if(verifier_si_deja_passe(liste_à_ignorer, tmp))
				if(plateau[tmp[0]][tmp[1]].piece instanceof Cube){
					int[] a = {adj[i][0], adj[i][1]};
					cases_adjacentes.add(a);
					cases_adjacentes_ignorer(adj[i][0], adj[i][1], liste_à_ignorer, cases_adjacentes);
			}
		}
		int[][] cases_adjacentes_tableau = cases_adjacentes.toArray(new int[0][0]);
		return cases_adjacentes_tableau;
	}
		
	public int[][] adjacentes(int x, int y){ //retourne un tableau listant les adjacentes des coordonnées d'un point. ce tableau passe par un filtre
		int[][] adjacentes = {
			{x, y+1},
			{x+1, y},
			{x, y-1},
			{x-1, y}
		};
		return filtre_adjacentes_coordonnees(adjacentes, x, y);
	}

	public int[][] filtre_adjacentes_coordonnees(int[][] adjacentes, int x, int y){ //retourne le tableau d'adjacentes avec les coordonnées qui dépassent la taille du tableau retirés, ce tableau passe un dernier filtre
		ArrayList<Integer> filtre = new ArrayList<>();
		for(int i = 0; i < adjacentes.length; i++){
				if(adjacentes[i][0] < largeur && adjacentes[i][1] < hauteur){
					filtre.add(i);
				}
		}
		int[][] adjacentes_filtres = new int[filtre.size()][2];
		for(int i = 0; i < filtre.size(); i++){
			for(int j = 0; j < 2; j++){
				adjacentes_filtres[i][j] = adjacentes[filtre.get(i)][j];
			}
		}
		return filtre_adjacentes_couleur(adjacentes_filtres, x, y);
	}

	public int[][] filtre_adjacentes_couleur(int[][] adjacentes, int x, int y){ //retourne le tableau d'adjancentes filtré avec les coordoonées des pièce d'une différente couleur retirés
		ArrayList<Integer> filtre = new ArrayList<>();
		for(int i = 0; i < adjacentes.length; i++){
			try{
				if(plateau[adjacentes[i][0]][adjacentes[i][1]].piece.nom == plateau[x][y].piece.nom){
				filtre.add(i);
				}
			}catch(Exception e){}
		}
		int[][] adjacentes_filtres = new int[filtre.size()][2];
		for(int i = 0; i < filtre.size(); i++){
			for(int j = 0; j < 2; j++){
				adjacentes_filtres[i][j] = adjacentes[filtre.get(i)][j];
			}
		}
		return adjacentes_filtres;
	}
	
	public boolean verifier_si_deja_passe(ArrayList<int[]> liste_à_ignorer, int[] tmp){
		for(int i = 0; i < liste_à_ignorer.size(); i++){
			int[] coordonnes = liste_à_ignorer.get(i);
			if(coordonnes[0] == tmp[0] && coordonnes[1] == tmp[1]){
				return false;
			}
		}
		return true;
	}

	public void supprime(int x, int y){ //supprime un point et ses adjacents de la même couleur
		int[][] adjacents = (cases_adjacentes_ignorer(x, y, null, null));
		plateau[x][y].estVide = true;
		for(int i = 0; i < adjacents.length; i++){
			plateau[adjacents[i][0]][adjacents[i][1]].estVide = true;;
		}
	}

	public int nombre_piece_colonne_au_dessus(int x, int y){ //compte le nombre de piece dans une colonne 
		int nbColonnes = 0;
		for(int i = 0; i < x; i++){
			if(!plateau[i][y].estVide){
				nbColonnes++;
			}
		}
		return nbColonnes;
	}

	public void gravite(){ //applique la gravite
		for(int i = largeur - 1; i > 0; i--){
			for(int j = hauteur - 1 ; j >= 0; j--){
				int nb = nombre_piece_colonne_au_dessus(i, j);
				if(plateau[i][j].estVide && nb > 0){
					for(int k = i; k >= 0; k--){
						if(!plateau[k][j].estVide){
							Case tmp = plateau[i][j];
							plateau[i][j] = plateau[k][j];
							plateau[k][j] = tmp;
						}
					}
				}
			}
		}
	}

	public void affichage(){
	//Cette fonction affiche la grille actuelle.
		System.out.print("  | ");
		for(int i = 0; i < largeur; i++){
			System.out.print(i + " | ");
		}
		System.out.println("");
		for(int i = 0; i < hauteur; i++){ // ligne
			System.out.print(i + " | ");
			for(int j = 0; j < largeur; j++){ // collone
				if(!plateau[i][j].estVide){
					if(plateau[i][j].piece instanceof Animal){ 

						System.out.print("A");
					}
					if(plateau[i][j].piece instanceof Cube){
						System.out.print(plateau[i][j].piece.nom.charAt(0));
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
