import java.io.Serializable;

public class Modele implements Serializable{
	Grille grille;
	int score_min;
	int score;
	int score_max;

	int tour;
	int tour_max;

	public Modele(Grille grille, int score_min, int tour_max){
		this.grille = grille;
		this.score_min = score_min;
		tour = 1;
		this.tour_max = tour_max;
		score_max = grille.scoreMax();
	}
}
