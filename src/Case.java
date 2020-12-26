public class Case {
//Ce code gère une case du plateau.
	boolean estVide,visible;
	Piece piece;
	
	public Case(boolean estVide,Piece piece,boolean visible) {
		this.estVide=estVide;
		this.visible=visible;
		if(!estVide && visible) {
			this.piece=piece;
		}
	}
	
	public String getValeur() {
	//On identifie quelle pièce se trouve dans la case.
		if(this.piece instanceof Cube) {
			return ((Cube) this.piece).couleur;
		}else if (this.piece instanceof Animal) {
			return "animal";
		}else{
			return null;
		}
	}
}
