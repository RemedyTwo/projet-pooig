public class Test {

    Case c = new Case(false, new Piece(), false);

    public static void main(String[] args) {
        Case[][] plateau = {
            { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Vert(), true) },
            { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Bleu(), true) },
            { new Case(false, new Cube.Vert(), true), new Case(false, new Cube.Bleu(), true) },
            };
        Grille g = new Grille(plateau[0].length, plateau.length, 0);
        g.plateau = plateau;
        Joueur j = new Joueur(10, g);
        j.tour();
    }
}
