import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import javax.swing.undo.*;
public class Modele{

	private BufferedImage[][] image;
	public Grille grille;
	
	public Modele() throws IOException{
		image.length=grille.length;
		image[].length=grille[0].length;
		for(int i=0;i<grille.length;i++){
			for(int j=0;j<grille.length;j++){
				if(!grille[i][j].estVide){
					try {
						this.image[i][j]=ImageIO.read(new File(grille[i][j].));
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
