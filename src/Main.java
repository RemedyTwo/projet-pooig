import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Main implements Serializable{

	static Scanner scanner;

    public static void main(String[] args)
    {
        System.out.println("__________        __    __________                                       _________");
        System.out.println("\\______   \\ _____/  |_  \\______   \\ ____   ______ ____  __ __   ____    /   _____/____     _________");
        System.out.println("|     ___// __ \\   __\\  |       _// __ \\ /  ___// ___\\|  |  \\_/ __ \\   \\_____  \\__  \\   / ___\\__  \\  ");
        System.out.println("|    |   \\  ___/|  |    |    |   \\  ___/ \\___ \\  \\___|  |  /\\  ___/   /        \\/ __ \\_/ /_/  > __ \\_");
        System.out.println("|____|    \\___  >__|    |____|_  /\\___  >____  >\\___  >____/  \\___  > /_______  (____  /\\___  (____  /");
        System.out.println("              \\/               \\/     \\/     \\/     \\/            \\/          \\/     \\//_____/     \\/ \n");

		Joueur j = new Joueur("");

		try{
			FileInputStream fis = new FileInputStream("joueur.ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			j = (Joueur)ois.readObject();
		}catch (Exception e){
			System.out.print("Votre nom : ");
			scanner = new Scanner(System.in);
			String s = scanner.nextLine();
			j = new Joueur(s);
			try{
				FileOutputStream fos = new FileOutputStream("joueur.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(j);
			}catch (Exception e1){
				e1.printStackTrace();
			}
		}

        System.out.print("Bienvenue " + j.nom +", que voulez-vous faire?\n\n"
        + "1/ Lancer l'interface graphique\n"
        + "2/ Sélectionner un niveau\n"
        + "3/ Quitter\n"
        + "\nVotre choix : "
        );
        
		scanner = new Scanner(System.in);

		int choix = scanner.nextInt();
		
        if(choix == 1){
            new Vue();
        }

        else if(choix == 2){
			System.out.print("\nSélectionnez un niveau :\n"
			+ "1/ Niveau 1\n"
			+ "2/ Niveau 2\n"
			+ "3/ Niveau 3\n"
			+ "\nVotre choix : "
			);

			Niveaux niveaux = new Niveaux();

			scanner = new Scanner(System.in);

			int choix2 = scanner.nextInt();

			Grille g = new Grille(null, 0, 0);
			if(choix2 == 1){
				g = niveaux.niveau1;
			}else if(choix2 == 2){
				g = niveaux.niveau2;
			}else if(choix2 == 3){
				g = niveaux.niveau3;
			}else{
				System.out.println("Niveau sélectionné inexistant.");
			}
			g.tour();
		}	

        else if(choix == 3){
            System.out.print("\n\nà bientôt, " + j.nom + " !\n");
            System.exit(0);
		}
		
		else{
			System.out.println("\nVous avez sélectionné une option indisponible.");
		}
    }
}
