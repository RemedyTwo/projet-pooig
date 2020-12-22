public class Grille {
//Ce code gère la grille du jeu.

	public Case[][]plateau;
	public int[][]adja;
	public int hauteur, largeur, nbAnimeaux;
	
	public Grille(int h,int l, int nbAnimeaux) {
		this.hauteur = h;
		this.largeur = l;
		this.plateau = new Case[h][l];
		this.adja = new int[h][l];
		this.nbAnimeaux = nbAnimeaux;
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
				this.nbAnimeaux--;
			}
		}
	}
	
	public int adjacences(int x,int y) {
	//On regarde si un cube se trouvant dans l'une des cases du tableau possède d'autres cubes de la même couleur dans les cases adjacentes. Si tel est le cas, il faut alors aussi vérifier les cases adjacentes de ces cubes. On ne prend pas en compte les diagonales.
		int val=plateau[x][y].getValeur();
		int adj=0;
		if(!horsLimite(x,y) && plateau[x][y].piece instanceof Cube) {
			if(x==0 || x<plateau.length-1) {
				if(y==0 || y<plateau[x].length-1) {
					if(val==plateau[x+1][y].getValeur()) {
						adj++;
						adj+=adjacences(x+1,y);					
					}
					if(val==plateau[x][y+1].getValeur()) {
						adj++;
						adj+=adjacences(x,y+1);
					}
				}else if(y==plateau[x].length-1 || y>0) {
					if(val==plateau[x][y-1].getValeur()) {
						adj++;
						adj+=adjacences(x,y-1);
					}
				}
			}else if(x==plateau.length-1) {
				if(y==0|| y<plateau[x].length-1) {
					if(val==plateau[x-1][y].getValeur()) {
						adj++;
						adj+=adjacences(x-1,y);					
					}
					if(val==plateau[x][y+1].getValeur()) {
						adj++;
						adj+=adjacences(x,y+1);
					}
				}else if(y==plateau[x].length-1 || y>0) {
					if(val==plateau[x-1][y].getValeur()) {
						adj++;
						adj+=adjacences(x-1,y);
					}
					if(val==plateau[x][y-1].getValeur()) {
						adj++;
						adj+=adjacences(x,y-1);
					}
				}
			}else {
				if(y==0|| y<plateau[x].length-1) {
					if(val==plateau[x-1][y].getValeur()) {
						adj++;
						adj+=adjacences(x-1,y);					
					}
					if(val==plateau[x][y+1].getValeur()) {
						adj++;
						adj+=adjacences(x,y+1);
					}
					if(val==plateau[x+1][y].getValeur()) {
						adj++;
						adj+=adjacences(x+1,y);					
					}
				}else if(y==plateau[x].length-1 || y>0) {
					if(val==plateau[x-1][y].getValeur()) {
						adj++;
						adj+=adjacences(x-1,y);
					}
					if(val==plateau[x+1][y].getValeur()) {
						adj++;
						adj+=adjacences(x+1,y);
					}
					if(val==plateau[x][y-1].getValeur()) {
						adj++;
						adj+=adjacences(x,y-1);
					}
				}
			}
		}
		//Si l'entier retourné est supérieur à 3, il est possible de supprimer les cubes adjacents.
		return adj;
	}
}
