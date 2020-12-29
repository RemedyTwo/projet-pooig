import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Vue extends JFrame{
	public JPanel[][] panneau=new JPanel[][]();
	public Modele modele;
	public Controleur controleur;
	
	public Vue(){
		setTitle("Titre du jeu");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JMenuBar jmb=new JMenuBar();
		setJMenuBar(jmb);
		jmb.add(grille.score);
		JPanel.length=modele.grille.lenght;
		JPanel[].length=modele.grille[0].length;
	}	
		
	public void mouseClicked(MouseEvent event) {
	//avec cette fonction on veut essaier de supprimer le cube sur lequel le joueur a cliqué.
		int x=event.getPoint().x;
		int y=event.getPoint().y;
		grille.supprimeCube(x0,y0);	
	}
}
