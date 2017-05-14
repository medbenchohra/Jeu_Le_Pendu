package Core;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by The King Mohamed on 27/04/2017.
 */

public class Noyau extends Constantes{

    public static void main (String[] args) {

        String pseudonyme = null;
        Scanner input = new Scanner(System.in);
        Joueur joueur = null;

        System.out.println(" 1 - Se connecter \n 2 - S'inscrire \n");
        int choix = 0;
        while (choix != 1 && choix != 2) {
            System.out.print("\n\t\t\t Votre choix : ");
            choix = input.nextInt();
        }

        while (joueur == null) {
            System.out.print("\n\n   Entrez un pseudonyme : ");
            switch (choix) {
                case 1:
                    pseudonyme = input.next();
                    while (!ALPHABETS.contains(pseudonyme.toLowerCase().subSequence(0,0))){
                        System.out.print("\n\n   Entrez un pseudonyme : ");
                        pseudonyme = input.next();
                    }
                    joueur = Connecter(pseudonyme);
                    if (joueur != null) break;
                case 2:
                    System.out.println("\n\n   Vous n'êtes pas inscrit,");
                    System.out.print("\n\n   Vouler-vous s'inscrire avec ce pseudonyme" +
                            " (" + pseudonyme + ") ? ");
                    System.out.println(
                            "\n\t\t\t 1 - Non, je veux m'inscrire avec un autre pseudonyme" +
                            "\n\t\t\t 2 - Oui, je veux m'inscrire avec (" + pseudonyme + ") " +
                            "\n\t\t\t 3 - Je ne veux pas m'inscrire");
                    int choix2 = 0;
                    while (choix2 < 1 || choix2 > 3) {
                        System.out.print("\n\t\t\t\t\t Votre choix : ");
                        choix2 = input.nextInt();
                    }
                    switch (choix2) {
                        case 1:
                            System.out.print("\n\n   Entrez un pseudonyme : ");
                            pseudonyme = input.next();
                            while (!ALPHABETS.contains(pseudonyme.toLowerCase().subSequence(0,0))){
                                System.out.print("\n\n   Entrez un pseudonyme : ");
                                pseudonyme = input.next();
                            }
                        case 2:
                            joueur = Inscription(pseudonyme);
                            if (joueur == null)
                                System.out.println("Vous-êtes déja inscrit, essayez de vous re-connnectez");
                            break;
                        default:
                            break;
                    }

            }
        }

        Session session = new Session();

        System.out.println(" Votre meilleur score : " + joueur.getMeilleurScore());
        System.out.println("\n   Appuyez sur n'importe quel bouton pour commencer a jouer ...");
        input.nextLine();
        input.nextLine();

        for (int i = 0; i < 10; i++) {
            System.out.println("\n Mot N°0" + (i+1) + ":" + (session.getQuestions())[i].getQuestion());
        }

    }

    public static boolean pseudonymeValide (String pseudonyme){
        return (pseudonyme!=null) && (pseudonyme.length()!=0) && ALPHABETS.contains(pseudonyme.subSequence(0,1));
    }
    public static Joueur Connecter(@NotNull String pseudonyme) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("src/Core/fichiers/users.txt"), "UTF-8"));
            HashMap<String,Joueur> donneesUtilisateurs = fileToHashMap(in);
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (donneesUtilisateurs.containsKey(pseudonyme)) {
                return donneesUtilisateurs.get(pseudonyme);
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Joueur Inscription (@NotNull String pseudonyme) {
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("src/Core/fichiers/users.txt"), "UTF-8"));
            HashMap<String,Joueur> donneesUtilisateurs = fileToHashMap(in);
            if (donneesUtilisateurs.containsKey(pseudonyme))
                return null;
            else {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = new BufferedWriter(new FileWriter("src/Core/fichiers/users.txt",true));
                String str = pseudonyme + ";0" + "\n";
                out.append(str);
                out.flush();
                out.close();
                return new Joueur(pseudonyme, 0);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static private HashMap<String,Joueur> fileToHashMap(BufferedReader in) {
        String ligne = null;
        String pseudonyme = null;
        int meilleurScore = 0;
        HashMap<String,Joueur> users = new HashMap<>();
        try {
            while ((ligne = in.readLine()) != null) {
                pseudonyme = (ligne.split(";"))[0];
                meilleurScore = Integer.valueOf((ligne.split(";"))[1]);
                users.put(pseudonyme,new Joueur(pseudonyme,meilleurScore));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

}
