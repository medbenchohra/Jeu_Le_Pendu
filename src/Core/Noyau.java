package Core;

import com.sun.istack.internal.Interned;
import com.sun.istack.internal.NotNull;
import sun.text.resources.es.FormatData_es_AR;

import java.io.*;
import java.util.*;

/**
 * Created by The King Mohamed on 27/04/2017.
 */

public class Noyau extends Constantes {

    public static Joueur user;
    public static Session session;

    public static boolean pseudonymeValide (String pseudonyme){
        return (pseudonyme != null) && (pseudonyme.length() != 0) && ALPHABETS.contains(pseudonyme.subSequence(0,1));
    }

    public static void saveScore (int score) {
        ArrayList<Integer> scores = user.getScores();
        scores.add(score);
        user.setScores(scores);
        if (score > user.getMeilleurScore())
            user.setMeilleurScore(score);
        addScoreToPlayer(user.getPseudonyme(),score);

    }

    private static void addScoreToPlayer(String pseudonyme, int score) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream("src/Core/fichiers/users.txt"), "UTF-8"));
            HashMap<String,Joueur> users = fileToHashMap(in);
            users.get(pseudonyme).getScores().add(score);
            hashMapToFile(users);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Joueur connecter(@NotNull String pseudonyme) {
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

    public static Joueur inscription (@NotNull String pseudonyme) {
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
                String[] scoresStringArray = ligne.split(";")[1].split(",");
                ArrayList<Integer> scores = toIntegerArrayList(scoresStringArray);
                meilleurScore = Collections.max(toIntegerArrayList(scoresStringArray));
                users.put(pseudonyme,new Joueur(pseudonyme,meilleurScore,scores));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    static private void hashMapToFile (HashMap<String,Joueur> users) {
        BufferedWriter out = null;
        String line = "";
        String scoresString = "";
        try {
            out = new BufferedWriter(new FileWriter("src/Core/fichiers/users.txt"));
            for (String pseudonyme : users.keySet()) {
                scoresString = line = "";
                ArrayList<Integer> scores = users.get(pseudonyme).getScores();
                for (int i = 0; i < scores.size(); i++) {
                    scoresString = scoresString + scores.get(i);
                    if (i != scores.size() - 1)
                        scoresString = scoresString + ",";
                }
                line = pseudonyme + ";" + scoresString + "\n";
                out.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static private ArrayList<Integer> toIntegerArrayList(String[] str) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
           result.add(Integer.valueOf(str[i]));
        }
        return result;
    }

}
