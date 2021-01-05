import java.io.Serializable;

public class Niveaux implements Serializable {
    Case[][] plateau_niveau3 = {
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal())}, //ligne 1
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 2
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())}, //ligne 3
        {new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune())}, //ligne 4
        {new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Obstacle())}, //ligne 5
        {new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Obstacle()), new Case(new Obstacle())}, //ligne 6
        {new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle())}, //ligne 7
        {new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle())} //ligne 8
    };
    Grille niveau3 = new Grille(plateau_niveau3);

    public Grille getNiveau3(){
        return niveau3;
    }
}
