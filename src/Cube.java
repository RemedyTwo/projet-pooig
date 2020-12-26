public class Cube extends Piece{
	
	public String couleur;
	
	public Cube(String couleur) {
		this.couleur=couleur;
	}

	static class Rouge extends Cube{
		public Rouge(){
			super("rouge");
		}
	}

	static class Vert extends Cube{
		public Vert(){
			super("vert");
		}
	}

	static class Bleu extends Cube{
		public Bleu(){
			super("bleu");
		}
	}
}
