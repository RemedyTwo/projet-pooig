public class Niveaux {
    Case[][] niveau3 = {
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal())}, //ligne 1
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 2
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 3
        {new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune())}, //ligne 4
        {new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Obstacle())}, //ligne 5
        {new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Obstacle()), new Case(new Obstacle())}, //ligne 6
        {new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle())}, //ligne 7
        {new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle())} //ligne 8
    };

    public static void main(String[] args){
        Case[][] niveau3 = {
            {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal())}, //ligne 1
            {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 2
            {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 3
            {new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune())}, //ligne 4
            {new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Rouge())}, //ligne 5
            {new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())}, //ligne 6
            {new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())}, //ligne 7
            {new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())} //ligne 8
        };
        Grille g = new Grille(niveau3.length, niveau3[0].length, 4);
        g.plateau = niveau3;
        Joueur joueur = new Joueur(10, g, false);
        joueur.tour();
    }
}
