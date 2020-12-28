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
		try{
			if(this.piece.nom != null){
				return this.piece.nom;
			}
		}catch(Exception e){
			return null;
		}
		return null;
	}
}
