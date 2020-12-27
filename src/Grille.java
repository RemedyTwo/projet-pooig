import java.util.ArrayList;
public class Grille {
//Ce code gère la grille du jeu.

	public Case[][]plateau;
	public Case[][]copie;
	public int[][]adja;
	public int hauteur, largeur, nbAnimaux;
	
	public Grille(int h,int l, int nbAnimaux) {
		this.hauteur = h;
		this.largeur = l;
		this.plateau = new Case[h][l];
		this.copie= new Case[h][l];
		this.adja = new int[h][l];
		this.nbAnimaux = nbAnimaux;
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
				plateau[x][y].piece=null;
				adj+=adja(x+1,y,0);
				plateau[x][y].piece=null;
			}					
			if(x!=0 && val.equals(plateau[x-1][y].getValeur())) {
				adj++;
				plateau[x][y].piece=null;
				adj+=adja(x-1,y,0);
				plateau[x][y].piece=null;					
			}
			if(y!=plateau[x].length-1 && val.equals(plateau[x][y+1].getValeur())) {
				adj++;
				plateau[x][y].piece=null;
				adj+=adja(x,y+1,0);
				plateau[x][y].piece=null;
			}
			if(y!=0 && val.equals(plateau[x][y-1].getValeur())) {
				adj++;
				plateau[x][y].piece=null;
				adj+=adja(x,y-1,0);
				plateau[x][y].piece=null;
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
				plateau[x][y].piece=null;
				a+=adja(x+1,y,0);
				plateau[x][y].piece=copie[x][y].piece;
			}					
			if(x!=0 && val.equals(plateau[x-1][y].getValeur())) {
				a++;
				plateau[x][y].piece=null;
				a+=adja(x-1,y,0);
				plateau[x][y].piece=copie[x][y].piece;					
			}
			if(y!=plateau[x].length-1 && val.equals(plateau[x][y+1].getValeur())) {
				a++;
				plateau[x][y].piece=null;
				a+=adja(x,y+1,0);
				plateau[x][y].piece=copie[x][y].piece;
			}
			if(y!=0 && val.equals(plateau[x][y-1].getValeur())) {
				a++;
				plateau[x][y].piece=null;
				a+=adja(x,y-1,0);
				plateau[x][y].piece=copie[x][y].piece;
			}
		}
		return a;
	}
	
	public boolean peutSupprimer(int x,int y){
		int i=adjacences(x,y);
		return (i>=1);
	}
		
	public int[][] adjacentes(int x, int y){ //aiguilles d'une montre en commencant a 12:00
		int[][] adjacentes = {
			{x, y+1},
			{x, y},
			{x, y-1},
			{x-1, y}
		};
		return adjacentes;
	}
	
	public int[][] cases_adjacentes(int x, int y) {
		ArrayList<int[]> cases_adjacentes = new ArrayList<>();
		//int[][] cases_adjacentes;
		Case[][] etat = plateau;
		int[][] adj = adjacentes(x, y);
		Case c = etat[x][y];
		etat[x][y] = null;
		for(int i = 0; i < 3; i++){
			if(etat[adj[i][0]][adj[i][1]].piece instanceof Cube){
				int[] a = {adj[i][0], adj[i][1]};
				cases_adjacentes.add(a);
				cases_adjacentes.get(cases_adjacentes.size() - 1);
				cases_adjacentes_r(adj[i][0], adj[i][1], etat, cases_adjacentes);
			}
		}
		return (int[][]) cases_adjacentes.toArray();
	}

	public void cases_adjacentes_r(int x, int y, Case[][] etat, ArrayList<int[]> cases_adjacentes){
		int[][] adj = adjacentes(x, y);
		etat[x][y] = null;
		for(int i = 0; i < 3; i++){
			if(etat[adj[i][0]][adj[i][1]].piece instanceof Cube){
				int[] a = {adj[i][0], adj[i][1]};
				cases_adjacentes.add(a);
				cases_adjacentes.get(cases_adjacentes.size() - 1);
				cases_adjacentes_r(adj[i][0], adj[i][1], etat, cases_adjacentes);
			}
		}
	}
	
	public void supprimeCube(int x,int y){
	//Cette fonction permet de supprimer un cube et fait descendre les cubes qui sont au dessus.
		if(!horsLimite(x,y) && plateau[x][y].getValeur()!=null && plateau[x][y].getValeur()!="animal" && peutSupprimer(x,y)){
			plateau[x][y]=null;
			if(!plateau[x][y-1].estVide){
				for(int i=y;i>0;i--){
					Case tmp=plateau[x][i];
					plateau[x][i]=plateau[x][i-1];
					plateau[x][i-1]=tmp;
				}
			}
		}
	}

	public void supprime(int x, int y){
		plateau[x][y]=null;
	}

	public void gravite(){
		for(int i = 0; i < plateau.length; i++){
			for(int j = 0; j < plateau[i].length; j++){
				if(plateau[i][j].piece == null){
					for(int k = i - 1; k > -1; k--){
						System.out.print(
							"plateau[k][j].piece = " + plateau[k][j].piece + "\nplateau[k+1][j].piece = " + plateau[k+1][j].piece + "\n");
						plateau[k+1][j].piece = plateau[k][j].piece;
						plateau[k][j].piece = null;
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
				if(plateau[i][j].piece == null)
				{
					System.out.print(" ");
				}
				if(plateau[i][j].piece instanceof Animal){ //TODO: différencier les animaux 

					System.out.print("A");
				}
				if(plateau[i][j].piece instanceof Cube){ //TODO: différencier les cubes par couleur
					System.out.print(plateau[i][j].piece.nom.charAt(0));
				}
				System.out.print(" | ");
			}
			System.out.println("");
		}
	}
}
