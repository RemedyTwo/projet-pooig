public class Test {

    public static void main(String[] args) {
        Case[][] plateau = {
            { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Vert(), true), new Case(false, new Cube.Vert(), true) },
            { new Case(false, new Cube.Rouge(), true), new Case(false, new Cube.Bleu(), true), new Case(false, new Cube.Vert(), true)  },
            { new Case(false, new Cube.Vert(), true), new Case(false, new Cube.Bleu(), true), new Case(false, new Cube.Vert(), true)  },
            };
        Grille g = new Grille(plateau.length, plateau[0].length, 0);
        g.plateau = plateau;
        Joueur j = new Joueur(10, g);
        System.out.print("plateau[0].length = " + plateau[0].length + "\nhauteur = " + g.hauteur + "\n longueur = " + g.largeur + "\n");
        j.tour();
    }
}
