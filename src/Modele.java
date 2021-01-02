import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class Modele{

	private BufferedImage[][] image;
	public Grille grille;
	
	public Modele() throws IOException{
		image = new BufferedImage[grille.largeur][grille.hauteur];
		for(int i=0;i<grille.plateau.length;i++){
			for(int j=0;j<grille.plateau[i].length;j++){
				if(!grille.plateau[i][j].estVide){
					try {
						String nom = grille.plateau[i][j].piece.nom + ".jpg";
						this.image[i][j]=ImageIO.read(new File(nom));
					}catch (IOException e) {
			 			System.err.println("Path not found");
            					e.printStackTrace();
					}
				}
			}
		}
	}
	
	public BufferedImage getImage(int x,int y) {
			return image[x][y];			
	}			
}
