public class Case {
//Ce code gère une case du plateau.
	boolean estVide;
	Pieces piece;
	
	public Case(boolean estVide,Pieces piece) {
		this.estVide=estVide;
		if(!estVide) {
			this.piece=piece;
		}
	}
	
	public int getValeur() {
	//On identifie quelle pièce se trouve dans la case.
		if(this.piece instanceof Cube) {
			return ((Cube) this.piece).couleur+1;
		}else if (this.piece instanceof Animal) {
			return 5;
		}else{
			return 0;
		}
	}
}
