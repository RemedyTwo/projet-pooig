import java.util.ArrayList;
import java.util.Random;
public class Grille {
//Ce code gère la grille du jeu.

	public Case[][]plateau;
	public int[][]adja;
	public int hauteur, largeur, nbAnimaux;
	
	public Grille(int h,int l, int nbAnimaux) {
		this.hauteur = h;
		this.largeur = l;
		this.plateau = new Case[h][l];
		this.adja = new int[h][l];
		this.nbAnimaux = nbAnimaux;
	}
	
	public boolean horsLimite(int x, int y) {
	//On vérifie que les coordonnées entrées respectent les dimensions du plateau. 
		if(x>=0 && x < this.hauteur && y >= 0 && y < this.largeur) return false;
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

	public int[][] adjacentes(int x, int y){
		int[][] adjacentes = {
			{x, y+1},
			{x, y},
			{x, y-1},
			{x-1, y}
		};
		return adjacentes;
	}
	
	public int[][] cases_adjacentes(int x, int y) {
		ArrayList<ArrayList<Integer>> cases_adjacentes = new ArrayList<>();
		//int[][] cases_adjacentes;
		Case[][] etat = plateau;
		int[][] adj = adjacentes(x, y);
		Case c = etat[x][y];
		etat[x][y] = null;
		for(int i = 0; i < 3; i++){
			if(etat[adj[i][0]][adj[i][1]].piece instanceof Cube){
				
			}
		}
	}

	public 
	
	public boolean peutSupprimer(int x,int y){
		int i=adjacences(x,y);
		return (i>=3);
	}
	
	public void supprimeCube(int x,int y){
	//Cette fonction permet de supprimer un cube et fait descendre les cubes qui sont au dessus.
		if(!horsLimite(x,y) && plateau[x][y].getValeur()>0 && plateau[x][y].getValeur()<5 && peutSupprimer(x,y)){
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
		for(int i = 0; i < hauteur; i++){
			System.out.print(i+1 + " | ");
		}
		System.out.println("");
		for(int i = 0; i < largeur; i++){ // ligne
			System.out.print(i+1 + " | ");
			for(int j = 0; j < hauteur; j++){ // collone
				if(plateau[i][j].piece instanceof Animal){ //TODO: différencier les animaux 

					System.out.print("A");
				}
				if(plateau[i][j].piece instanceof Cube){ //TODO: différencier les cubes par couleur
					System.out.print("x");
				}
				System.out.print(" | ");
			}
			System.out.println("");
		}
	}
}
