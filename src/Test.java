public class Test {

    public static void main(String[] args) {
        Case c = new Case(false, new Piece(), false);
        Case[][] plateau = {
        { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Vert(), true) },
        { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Bleu(), true) },
        { new Case(false, new Cube.Vert(), true), new Case(false, new Cube.Bleu(), true) },
    };
        Grille g = new Grille(plateau[0].length, plateau.length, 0);
        g.plateau = plateau;
        g.affichage();
    }
}
