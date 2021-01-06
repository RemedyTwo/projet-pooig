import java.io.Serializable;

public class Joueur implements Serializable{
//Ce code prend les commandes du joueur et les éxecute.

	public String nom;
	public int score_cumule;
	public boolean[] niveau_etat = {true, false, false, false};
	
	public Joueur(String nom){
		this.nom = nom;
		score_cumule = 0;
	}

	//ajoute au score la valeur mise en argument
	public void addScore(int score){
		score_cumule += score;
	}
}
