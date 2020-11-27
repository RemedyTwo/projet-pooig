public class Case {

	boolean estVide;
	Pieces piece;
	
	public Case(boolean estVide,Pieces piece) {
		this.estVide=estVide;
		if(!estVide) {
			this.piece=piece;
		}
	}
	
	public int getValeur() {
		if(this.piece instanceof Cube) {
			return ((Cube) this.piece).couleur+1;
		}else if (this.piece instanceof Animal) {
			return 5;
		}else{
			return 0;
		}
	}
}
