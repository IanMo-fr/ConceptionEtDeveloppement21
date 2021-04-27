import controleur.Controleur;
import traitement.AlgoUni;
import vue.*;
import java.util.*;


import java.io.IOException;
//ATTENTION IMPORT VUE POUR TEST

/**
 * Main du projet : réalise les intéractions utilisateur et fait l'appel <code>Controleur</code>*/
public class Main {
    public static void main(String[] args) throws Exception {

        String pathname_Ian = "E:/Documents/Etude/L2/Semestre 4/Conception et dev/projetanon/exemple_ce.xls";
        String pathname_Julien = "C:/Users/jujuo/Desktop/CDA_projet/exemple_ce.xls";
        String pathname_Marilou = "F:/Semestre 4/exemple.xls";

     //   Controleur controleur = new Controleur("Julien"); //Préciser ici en paramètre le chemin d'accès d'arrivée du fichier, sinon il ya un chemin par défaut
       // controleur.CreerDocPseudonymisé("C:/Users/jujuo/Desktop/CDA_proj/exemple_ce.xls");

        // controleur.CreerDocsBucketisés(pathname_Julien, 3, "QID", "DS"); //Attention cette commande prend une base de données PSEUDONYMISEE en paramètre

      // controleur.controleurAlgo1(pathname_Julien,"algo","Age", 4);

      // controleur.VerifierDiversité(pathname_Julien, 3,3);
     // controleur.controleurIHM(); //On précise ici l'utilisateur


        List<Integer>test = new ArrayList<>();
        test.add(53712);
        test.add(53710);
        test.add(53712);
        test.add(53711);
        test.add(53711);
        test.add(53711);



       // [4,4,9,1,8]-> [[4,4,1],[9,8]
        // -> [1-4, 1-4, 6-8, 6-8, 1-4]


        AlgoUni alg = new AlgoUni();
        List<List<Integer>> testo =new ArrayList<>();
        testo = alg.faireAlgoUni(testo,test, "toto");

        List<String> test3= new ArrayList<>();

        for (int i=0;i<test.size();i++) {
            String val = test.get(i).toString();
            test3.add(val);
        }



//      [4,5,9,1,8]
        for (int i=0;i<testo.size();i++) {

            for (int j=0; j<testo.get(i).size();j++) {

                int position=0;

                while (testo.get(i).get(j)!=test.get(position)) { // 4 != 4
                  position++;
                }
                test.set(position,-9999);   //[-9999,5,9,1,8]
                test3.set(position,Collections.min(testo.get(i))+"-"+Collections.max(testo.get(i)));
            }
        }

        System.out.println(testo);
        System.out.println(test3);

    }

}
