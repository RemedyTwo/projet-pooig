import java.io.Serializable;

public class Niveaux implements Serializable {
    Case c = new Case(new Piece(""));

    Case[][] plateau_niveau1 = {
        {new Case(new Obstacle()), new Case(new Animal()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Animal()), new Case(new Obstacle())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Orange()), new Case(new Cube.Orange())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Orange()), new Case(new Cube.Orange())},
        {new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu())},
        {new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Vert()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Vert()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert())}
    };

    Case[][] plateau_niveau2 = {
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Animal()), new Case(new Piece("vide")), new Case(new Animal()), new Case(new Piece("vide")), new Case(new Animal())},
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge())},
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Cube.Vert()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge())},
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Orange()), new Case(new Cube.Vert())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Orange()), new Case(new Cube.Vert())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Obstacle())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Obstacle())},
    };

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

    Case[][] plateau_niveau4 = {
        {new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Animal()), new Case(new Obstacle()), new Case(new Animal()), new Case(new Obstacle()), new Case(new Obstacle()), new Case(new Obstacle())},
        {new Case(new Obstacle()), new Case(new Piece("vide")), new Case(new Obstacle()), new Case(new Cube.Rouge()), new Case(new Piece("vide")), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Piece("vide")), new Case(new Obstacle())},
        {new Case(new Animal()), new Case(new Piece("vide")), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Piece("vide")), new Case(new Animal())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Cube.Rouge()), new Case(new Obstacle()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Obstacle()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Orange())},
        {new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Orange())},
        {new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Obstacle()), new Case(new Cube.Bleu()), new Case(new Obstacle()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu())},
        {new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Obstacle()), new Case(new Cube.Rouge()), new Case(new Obstacle()), new Case(new Cube.Rouge()), new Case(new Obstacle()), new Case(new Cube.Rouge()), new Case(new Cube.Orange())},
        {new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Cube.Bleu()), new Case(new Obstacle()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Orange())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Orange())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Orange())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu())},
        {new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Obstacle()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Cube.Orange()), new Case(new Obstacle()), new Case(new Cube.Bleu()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Bleu()), new Case(new Cube.Orange()), new Case(new Cube.Rouge()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu()), new Case(new Cube.Bleu())}
    };
    Grille niveau1 = new Grille(plateau_niveau1, 0, 0);
    Grille niveau2 = new Grille(plateau_niveau2, 0, 0);
    Grille niveau3 = new Grille(plateau_niveau3, 0, 0);
    Grille niveau4 = new Grille(plateau_niveau4, 5000, 10);
}
