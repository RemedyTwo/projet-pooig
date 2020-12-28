import java.util.Scanner;
public class Joueur {
//Ce code prend les commandes du joueur et les éxecute.

	public int nbTours,score;
	boolean fini,robot;
	public Grille grille;
	
	public Joueur(int nbTours,Grille g,boolean robot){
		this.nbTours=nbTours;
		this.fini=false;
		this.grille=g;
		this.score=0;
		this.robot=robot;
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
			grille.affichage();
			int x=0;
			int y=0;
			if(robot){
			//Si le joueur est un robot, il prend la case avec le plus grand nombre de cubes qu'il peut supprimer.
				int max=0;
				for(int i=0;i<grille.length;i++){
					for(int j=0;j<grille[i].length;j++){
						if(max<grille.adjacences(i,j)){
							max=grille.adjacences(i,j);
						}
					}
				}
				while(grille.adjacences(x,y)<max && x<grille.length){
					while(grille.adjacences(x,y)<max && y<grille[x].length){
						y++;
					}
					x++;
				}
			}else{	
				System.out.print("colonne :");
				x=scanner.nextInt();
				System.out.println("\n");
				System.out.print("ligne :");
				y=scanner.nextInt();
			}
			if(grille.peutSupprimer(x,y)){
				int adj=grille.adjacences(x,y);
				score+=adj*100;
				if(adj>3 && adj<9){
					score+=250;
				}else if(adj>9){
					score+=500;
				}
				grille.supprimeCube(x,y);
				grille.gravite();
				nbTours--;
			}else{
				System.out.println("Tour invalide");
			}
			finDuJeu();
			tour();
		}
	}
	
	public void lanceJeu(){
		if(!robot){
			Scanner scanner = new Scanner(System.in); 
			System.out.println("Voulez-vous jouer? (Y/N)");
			String s=scanner.nextLine();
			if(s.equals"Y"){
				tour();
			}if(s.equals "N"){
				System.out.println("D'accord, au revoir.");
			}else{
				System.out.println("Commande invalide");
			}
		}else{
			tour();
		}
	}
}
