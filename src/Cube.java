public class Cube extends Piece{
		
	public Cube(String couleur) {
		super(couleur);
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

	static class Jaune extends Cube{
		public Jaune(){
			super("jaune");
		}
	}

	static class Orange extends Cube{
		public Orange(){
			super("orange");
		}
	}
}
