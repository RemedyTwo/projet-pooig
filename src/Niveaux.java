public class Niveaux {
    Case[][] niveau2 = {
        {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())},
        {new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune())},
        {new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Jaune()), new Case(new Cube.Bleu()), new Case(new Cube.Jaune()), new Case(new Cube.Rouge()), }
    };

    public static void main(String[] args){
        Case[][] niveau2 = {
            {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal()), new Case(new Animal())},
            {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())},
            {new Case(new Cube.Rouge()), new Case(new Cube.Rouge()), new Case(new Cube.Vert()), new Case(new Cube.Bleu()), new Case(new Cube.Vert()), new Case(new Cube.Jaune()), new Case(new Cube.Vert()), new Case(new Cube.Bleu())},
        };
        Grille g = new Grille(niveau2.length, niveau2[0].length, 4);
        g.plateau = niveau2;
        Joueur joueur = new Joueur(10, g, false);
        joueur.tour();
    }
}
