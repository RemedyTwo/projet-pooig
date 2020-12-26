import java.util.Scanner;
public class Joueur {
//Ce code prend les commandes du joueur et les éxecute.

	public int nbTours;
	boolean fini;
	public Grille grille;
	
	public Joueur(int nbTours,Grille g){
		this.nbTours=nbTours;
		this.fini=false;
		this.grille=g;
	}
	
	public void finDuJeu(){
	//On vérifie si le joueur a gagné ou perdu la partie.
		if(nbTours==0){
			System.out.println("Perdu !");
			fini=true;
		}else if(grille.nbAnimaux==0){
			System.out.println("Vous avez gagné!");
			fini=true;
		}
	}

	public void tour(){
	//On joue un tour en entrant 2 entiers correspondant aux coordonnées d'un cube. si le cube sélectionné ne peut pas être supprimé, on relance le tour. A la fin du tour on vérifie si le jeu n'est pas fini et, dans le cas où il ne l'est pas, on joue le tour suivant.
		while(!fini){
			Scanner scanner = new Scanner(System.in); 
			System.out.print("colonne :");
			int x=scanner.nextInt();
			System.out.println("\n");
			System.out.print("ligne :");
			int y=scanner.nextInt();
			if(grille.peutSupprimer(x,y)){
				grille.supprimeCube(x,y);
			}else{
				System.out.println("Tour invalide");
			}
			finDuJeu();
			tour();
		}
	}
}
