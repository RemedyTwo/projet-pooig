import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("__________        __    __________                                       _________");
        System.out.println("\\______   \\ _____/  |_  \\______   \\ ____   ______ ____  __ __   ____    /   _____/____     _________");
        System.out.println("|     ___// __ \\   __\\  |       _// __ \\ /  ___// ___\\|  |  \\_/ __ \\   \\_____  \\__  \\   / ___\\__  \\  ");
        System.out.println("|    |   \\  ___/|  |    |    |   \\  ___/ \\___ \\  \\___|  |  /\\  ___/   /        \\/ __ \\_/ /_/  > __ \\_");
        System.out.println("|____|    \\___  >__|    |____|_  /\\___  >____  >\\___  >____/  \\___  > /_______  (____  /\\___  (____  /");
        System.out.println("              \\/               \\/     \\/     \\/     \\/            \\/          \\/     \\//_____/     \\/ \n");

        System.out.print("Bienvenue, que voulez-vous faire?\n\n"
        + "1/ Lancer l'interface graphique\n"
        + "2/ Sélectionner un niveau\n"
        + "3/ Quitter\n"
        + "\n\nVotre choix : "
        );
        Scanner scanner = new Scanner(System.in);

        int choix = scanner.nextInt();

        if(choix == 1){
            new Vue();
        }

        if(choix == 2){
            //TODO:à remplir
        	JPanel level_panel=new JPanel();
    		level_panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
    		GridLayout level_layout = new GridLayout(0, 1);
    		level_layout.setVgap(30);
    		level_panel.setLayout(level_layout);
    		
    		JFrame level_frame= new JFrame();
    		JLabel level_text= new JLabel("Choisissez votre niveau de difficulté.");
    		JButton niveau1=new JButton("Facile : 5x5");
    		JButton niveau2=new JButton("Normal : 10x10");
    		JButton niveau3=new JButton("Difficile : 15x15");
    		
    		level_panel.add(niveau1);
    		level_panel.add(niveau2);
    		level_panel.add(niveau3);
    	
    		niveau1.addActionListener((event)->{
    			Grille g=new Grille(5,5,5);
    			Vue v=new Vue();
    			v.grille=g;
    			level_frame.dispose();
    		});
    			
    		niveau2.addActionListener((event)->{
    			Grille g=new Grille(10,10,10);
    			Vue v=new Vue();
    			v.grille=g;
    			level_frame.dispose();
    		});
    			
    		niveau3.addActionListener((event)->{
    			Grille g=new Grille(15,15,15);
    			Vue v=new Vue();
    			v.grille=g;
    			level_frame.dispose();
    		});
    			
    		level_frame.add(level_panel, BorderLayout.CENTER);
    		level_frame.add(level_text, BorderLayout.NORTH);
    		
    		
    		level_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    		level_frame.setTitle("Pet Rescue Saga");
    		level_frame.setPreferredSize(new Dimension(800, 600));
    		level_frame.pack();
    		level_frame.setLocationRelativeTo(null);
    		level_frame.setVisible(true);
    	}

        if(choix == 3){
            System.out.print("\n\nà bientôt !\n");
            System.exit(0);
        }
    }
}