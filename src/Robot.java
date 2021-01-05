public class Robot {
    Grille grille;

    public Robot(Grille grille){
        this.grille = grille;
    }

    public void doLevel(){
        int[] coordonnees = grille.aide();
        grille.supprime(coordonnees[0], coordonnees[1]);
        grille.graviteRecursive();
        grille.AnimalAuSol();
        grille.modele.score -= 200;
    }

}
