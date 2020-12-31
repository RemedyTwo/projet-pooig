import java.util.Scanner;

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
        }

        if(choix == 3){
            System.out.print("\n\nÀ bientôt !\n");
            System.exit(0);
        }
    }
}
