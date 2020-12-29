public class Case {
//Ce code gère une case du plateau.
	boolean estVide;
	//boolean visible;
	Piece piece;
	
	public Case(Piece piece) {
		this.piece = piece;
		estVide = false;
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
