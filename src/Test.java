public class Test {

    Case c = new Case(false, new Piece(), false);
    Case[][] plateau = {
    { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Vert(), true) },
    { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Bleu(), true) },
    { new Case(false, new Cube.Vert(), true), new Case(false, new Cube.Bleu(), true) },
    };

    public static void main(String[] args) {

        Grille g = new Grille(plateau[0].length, plateau.length, 0);
        g.plateau = plateau;
        g.affichage();
    }
}