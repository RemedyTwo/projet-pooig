import java.io.Serializable;

public class Case implements Serializable{
//Ce code gère une case du plateau.
	boolean estVide = false;
	//boolean visible;
	Piece piece;
	
	public Case(Piece piece) {
		this.piece = piece;
		if(piece.nom == "vide"){
			estVide = true;
		}
	}
}