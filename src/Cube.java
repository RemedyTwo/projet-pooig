public class Cube extends Piece{
	
	public String couleur;
	
	public Cube(String couleur) {
		this.couleur=couleur;
	}

	public class Rouge extends Cube{
		public Rouge(){
			super("rouge");
		}
	}

	public class Vert extends Cube{
		public Vert(){
			super("vert");
		}
	}

	public class Bleu extends Cube{
		public Bleu(){
			super("bleu");
		}
	}
}
