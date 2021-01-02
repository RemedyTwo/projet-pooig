import java.util.Scanner;
public class Joueur {
//Ce code prend les commandes du joueur et les éxecute.

	public int nbTours, score;
	public boolean fini, robot;
	public Grille grille;
	
	public Joueur(int nbTours, Grille g, boolean robot){
		this.nbTours = nbTours;
		this.fini = false;
		this.grille = g;
		this.score = 0;
		this.robot = robot;
	}
	
	public void finDuJeu(){
	//On vérifie si le joueur a gagné ou perdu la partie.
		if(nbTours==0){
			System.out.println("Perdu !");
			fini=true;
		}else if(grille.nbAnimaux==0){
			System.out.println("Vous avez gagné!");
			fini=true;
			grille.affichage();
		}
	}

	public void tour(){
	//On joue un tour en entrant 2 entiers correspondant aux coordonnées d'un cube. si le cube sélectionné ne peut pas être supprimé, on relance le tour. A la fin du tour on vérifie si le jeu n'est pas fini et, dans le cas où il ne l'est pas, on joue le tour suivant.
		while(!fini){
			int max=0;
			int x=0;
			int y=0;
			if(robot) {
				for(int i=0;i<grille.plateau.length;i++) {
					for(int j=0;j<grille.plateau.length;j++) {
						if(grille.casesAdjacentesIgnorer(i, j, null, null).length>max) {
							max=grille.casesAdjacentesIgnorer(i, j, null, null).length;
						}
					}
				}
				while(x<grille.plateau.length && grille.casesAdjacentesIgnorer(x, y,null,null).length<max) {
					while(y<grille.plateau.length && grille.casesAdjacentesIgnorer(x, y,null,null).length<max) {
						y++;
					}
					x++;
				}
				grille.supprime(x,y);
			}else{
				Scanner scanner = new Scanner(System.in); 
				grille.affichage();
				System.out.print("colonne :");
				x=scanner.nextInt();
				System.out.println("");
				System.out.print("ligne :");
				y=scanner.nextInt();
				//scanner.close();
			}
			if(grille.peutSupprimer(x,y)){
				nbTours--;
				grille.supprime(x,y);
				
			}
			grille.gravite();
			grille.AnimalAuSol();
			grille.graviteHorizontale();
			finDuJeu();
			tour();
		}
	}

	public void lanceJeu(){
		if(!robot){
			Scanner scanner = new Scanner(System.in); 
			System.out.println("Voulez-vous jouer? (Y/N)");
			String s=scanner.nextLine();
			if(s.equals("Y")){
				tour();
			}if(s.equals ("N")){
				System.out.println("D'accord, au revoir.");
			}else{
				System.out.println("Commande invalide");
			}
		}else{
			tour();
		}
	}
}
