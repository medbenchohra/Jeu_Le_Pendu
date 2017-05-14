package Core;

import com.sun.istack.internal.Interned;
import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Created by The King Mohamed on 27/04/2017.
 */

public class Noyau extends Constantes {

    public static Joueur user;
    public static Session session;

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
                meilleurScore = toIntegerTreeSet(ligne.split(";")[1].split(",")).last().intValue();
                users.put(pseudonyme,new Joueur(pseudonyme,meilleurScore));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    static private TreeSet<Integer> toIntegerTreeSet(String[] str) {
        TreeSet<Integer> result = new TreeSet<>();
        for (int i = 0; i < str.length; i++) {
            result.add(Integer.valueOf(str[i]));
        }
        return result;
    }

}
