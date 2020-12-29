import java.lang.reflect.Array;
import java.util.ArrayList;
public class Grille {
//Ce code gère la grille du jeu.

	public Case[][]plateau;
	public Case[][]copie;
	public int[][]adja;
	public int hauteur, largeur, nbAnimaux;
	boolean[][][] cotes;
	boolean[][] etat;
	//Le tableau cotes nous aidera à éliminer les cubes voisins de même couleur.
	
	public Grille(int l,int h, int nbAnimaux) {
		this.hauteur = h;
		this.largeur = l;
		this.plateau = new Case[l][h];
		this.copie= new Case[l][h];
		this.adja = new int[l][h];
		this.nbAnimaux = nbAnimaux;
		this.cotes=new boolean[l][h][4];
		for(int i=0;i<l;i++){
			for(int j=0;j<h;j++){
				for(int k=0;k<4;k++){
					cotes[i][j][k]=false;
				}
			}
		}
		construction_etat();
	}

	public void construction_etat(){
		boolean[][] etat = new boolean[largeur][hauteur];
		for(int i = 0; i < hauteur; i++){
			for(int j = 0; j < largeur; j++){
				try{
					if(!plateau[i][j].estVide){
						etat[i][j] = true;
					}
				}catch(Exception e){
					etat[i][j] = false;
				}
			}
		}
		this.etat = etat;
	}
	
	public boolean horsLimite(int x, int y) {
	//On vérifie que les coordonnées entrées respectent les dimensions du plateau. 
		if(x>=0 && x < this.largeur && y >= 0 && y < this.hauteur) return false;
		return true;
	}
	
	public void AnimalAuSol() {
	//Lorsqu'un animal est au niveau le plus bas du plateau, on le supprime.
		int i=plateau.length;
		for(int j=0;j<plateau[i].length;j++) {
			if(plateau[i][j].piece instanceof Animal) {
				Animal a=(Animal)this.plateau[i][j].piece;
				a.estAuSol=true;
				this.nbAnimaux--;
			}
		}
	}


	public int adjacences(int x,int y) {
		//On regarde si un cube se trouvant dans l'une des cases du tableau possède d'autres cubes de la même couleur dans les cases adjacentes. Si tel est le cas, il faut alors aussi vérifier les cases adjacentes de ces cubes. On ne prend pas en compte les diagonales.	
		int adj=0;
		String val=plateau[x][y].getValeur();
		if(!horsLimite(x,y) && plateau[x][y].piece instanceof Cube) {
			if(x!=plateau.length-1 && val.equals(plateau[x+1][y].getValeur())) {
				adj++;
				//afin d'éviter que deux cases adjacentes s'appellent récursivement à l'infini, on met la valeur de la case présente à null avant d'appeller la fonction sur une autre case.On stocke cette valeur dans une pièce p1, afin que la case reprenne sa valeur d'origine et puisse continuer de scanner le tableau.
				cotes[x][y][0]=true;
				cotes[x+1][y][1]=true;
				plateau[x][y].piece=null;
				adj+=adja(x+1,y,0);
				plateau[x][y].piece=copie[x][y].piece;
			}					
			if(x!=0 && val.equals(plateau[x-1][y].getValeur())) {
				adj++;
				cotes[x][y][1]=true;
				cotes[x-1][y][0]=true;
				plateau[x][y].piece=null;
				adj+=adja(x-1,y,0);
				plateau[x][y].piece=copie[x][y].piece;					
			}
			if(y!=plateau[x].length-1 && val.equals(plateau[x][y+1].getValeur())) {
				adj++;
				cotes[x][y][2]=true;
				cotes[x][y+1][3]=true;
				plateau[x][y].piece=null;
				adj+=adja(x,y+1,0);
				plateau[x][y].piece=copie[x][y].piece;
			}
			if(y!=0 && val.equals(plateau[x][y-1].getValeur())) {
				adj++;
				cotes[x][y][3]=true;
				cotes[x][y-1][2]=true;
				plateau[x][y].piece=null;
				adj+=adja(x,y-1,0);
				plateau[x][y].piece=copie[x][y].piece;
			}
		}
		//Si l'entier retourné est supérieur ou égal à 1, il est possible de supprimer les cubes adjacents.
		return adj;
	}
	
	public int adja(int x,int y,int a){
		String val=plateau[x][y].getValeur();
		if(!horsLimite(x,y) && plateau[x][y].piece instanceof Cube) {
			if(x!=plateau.length-1 && val.equals(plateau[x+1][y].getValeur())) {
				a++;
				cotes[x][y][0]=true;
				cotes[x+1][y][1]=true;
				plateau[x][y].piece=null;
				a+=adja(x+1,y,0);
				plateau[x][y].piece=copie[x][y].piece;
			}					
			if(x!=0 && val.equals(plateau[x-1][y].getValeur())) {
				a++;cotes[x][y][1]=true;
				cotes[x-1][y][0]=true;
				plateau[x][y].piece=null;
				a+=adja(x-1,y,0);
				plateau[x][y].piece=copie[x][y].piece;					
			}
			if(y!=plateau[x].length-1 && val.equals(plateau[x][y+1].getValeur())) {
				a++;
				cotes[x][y][2]=true;
				cotes[x][y+1][3]=true;
				plateau[x][y].piece=null;
				a+=adja(x,y+1,0);
				plateau[x][y].piece=copie[x][y].piece;
			}
			if(y!=0 && val.equals(plateau[x][y-1].getValeur())) {
				a++;
				cotes[x][y][3]=true;
				cotes[x][y-1][2]=true;
				plateau[x][y].piece=null;
				a+=adja(x,y-1,0);
				plateau[x][y].piece=copie[x][y].piece;
			}
		}
		return a;
	}
	
	public boolean peutSupprimer(int x,int y){
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
			try{
				int[] tmp = {adj[i][0], adj[i][1]};
				if(verifier_si_deja_passe(liste_à_ignorer, tmp))
					if(plateau[tmp[0]][tmp[1]].piece instanceof Cube){
						int[] a = {adj[i][0], adj[i][1]};
						cases_adjacentes.add(a);
						cases_adjacentes_ignorer(adj[i][0], adj[i][1], liste_à_ignorer, cases_adjacentes);
				}
			}catch(Exception e){
			}
		}
		int[][] cases_adjacentes_tableau = cases_adjacentes.toArray(new int[0][0]);
		return cases_adjacentes_tableau;
	}
		
	public int[][] adjacentes(int x, int y){ //aiguilles d'une montre en commencant a 12:00
		int[][] adjacentes = {
			{x, y+1},
			{x+1, y},
			{x, y-1},
			{x-1, y}
		};
		return filtre_adjacentes_coordonnees(adjacentes, x, y);
	}

	public int[][] filtre_adjacentes_coordonnees(int[][] adjacentes, int x, int y){
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

	public int[][] filtre_adjacentes_couleur(int[][] adjacentes, int x, int y){
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
	
	public int[][] cases_adjacentes(int x, int y, Case[][] etat, ArrayList<int[]> cases_adjacentes){
		if(cases_adjacentes == null && etat == null){
			cases_adjacentes = new ArrayList<>();
			etat = plateau;
		}
		int[][] adj = adjacentes(x, y);
		etat[x][y] = null;
		for(int i = 0; i < adj.length; i++){
			try{
				if(etat[adj[i][0]][adj[i][1]].piece instanceof Cube){
				int[] a = {adj[i][0], adj[i][1]};
				cases_adjacentes.add(a);
				cases_adjacentes.get(cases_adjacentes.size() - 1);
				cases_adjacentes(adj[i][0], adj[i][1], etat, cases_adjacentes);
				}
			}catch(Exception e){
			}
		}
		int[][] cases_adjacentes_tableau = cases_adjacentes.toArray(new int[0][0]);
		return cases_adjacentes_tableau;
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

	public void supprimeCube(int x,int y){
	//Cette fonction permet de supprimer des cubes
		if(!horsLimite(x,y) && plateau[x][y].getValeur()!=null && plateau[x][y].getValeur()!="animal" && peutSupprimer(x,y)){
			plateau[x][y]=null;

			if(cotes[x][y][0]){
				cotes[x+1][y][1]=false;
				supprimeCube(x+1,y);
				cotes[x+1][y][1]=true;
			}
			if(cotes[x][y][1]){
				cotes[x-1][y][0]=false;
				supprimeCube(x-1,y);
				cotes[x-1][y][0]=true;
			}
			if(cotes[x][y][2]){
				cotes[x][y+1][3]=false;
				supprimeCube(x,y+1);
				cotes[x][y+1][3]=true;
			}
			if(cotes[x][y][3]){
				cotes[x][y-1][2]=false;
				supprimeCube(x,y-1);
				cotes[x][y-1][2]=true;
			}
		}
	}

	public void supprime(int x, int y){
		int[][] adjacents = (cases_adjacentes_ignorer(x, y, null, null));
		plateau[x][y].estVide = true;
		for(int i = 0; i < adjacents.length; i++){
			plateau[adjacents[i][0]][adjacents[i][1]].estVide = true;;
		}
		construction_etat();
	}

	public int nombre_piece_colonne_au_dessus(int x, int y){
		int nbColonnes = 0;
		for(int i = 0; i < y; i++){
			if(!plateau[x][i].estVide){
				nbColonnes++;
			}
		}
		return nbColonnes;
	}

	public void gravite(){
		for(int i = largeur - 1; i > 0; i--){
			for(int j = hauteur - 1 ; j > 0; j--){
				int nb = nombre_piece_colonne_au_dessus(i, j);
				if(plateau[i][j].estVide && nb > 0){
					for(int k = i; k >= 0; k--){
						if(!plateau[k][j].estVide){
							Case tmp = plateau[i][j];
							plateau[i][j] = plateau[k][j];
							plateau[k][j] = tmp;
						}
						//plateau[k][j].estVide = true;
					}
				}
			}
		}
	}

	/*public void remplitGrille(){
	//Avec cette fonction on remplit la grille avec des cubes de couleurs aléatoires.
		Random r=new Random();
		int x=r.nextInt(1,5);
		for(int i=0;i<plateau.length;i++){
			for(int j=1;j<plateau[i].length;j++){
				if(plateau[i][j].visible && plateau[i][j].estVide){
					Piece c=new Cube(x);
					plateau[i][j].piece=c;
				}
			}
			//La première colonne ne prend que des animaux (peut être changé plus tard).
			if(plateau[i][0].visible && plateau[i][0].estVide){
				Piece a=new Animal();
				plateau[i][0]=a;
			}
		}
	}
	
	public void retriage(){
	//Si il n'y a aucun déplacement possible, on retrie le tableau.
		int i=0;
		int j=1;
		while(i<plateau.length && !peutSupprimer(i,j)){
			while(j<plateau[i].length){
				j++;
			}
			i++;
		}
		if(!peutSupprimer(i,j)){
			remplitGrille();
		}
	}*/

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
				try{
					if(!plateau[i][j].estVide){
						if(plateau[i][j].piece instanceof Animal){ //TODO: différencier les animaux 

							System.out.print("A");
						}
						if(plateau[i][j].piece instanceof Cube){ //TODO: différencier les cubes par couleur
							System.out.print(plateau[i][j].piece.nom.charAt(0));
						}
					}
				}catch(Exception e){
					if(plateau[i][j].estVide){
						System.out.print("   ");
					}
				}
				System.out.print(" | ");
			}
			System.out.println("");
		}
	}
}
