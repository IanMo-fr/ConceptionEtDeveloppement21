import controleur.Controleur;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import traitement.AlgoMulti;
import traitement.AlgoUni;
import traitement.LectureFichier;
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

        Controleur controleur = new Controleur("Julien"); //Préciser ici en paramètre le chemin d'accès d'arrivée du fichier, sinon il ya un chemin par défaut
        // controleur.CreerDocPseudonymisé("C:/Users/jujuo/Desktop/CDA_proj/exemple_ce.xls");

        // controleur.CreerDocsBucketisés(pathname_Julien, 3, "QID", "DS"); //Attention cette commande prend une base de données PSEUDONYMISEE en paramètre

       // controleur.CreerDocAlgo1(pathname_Julien,"toto","CP", 2);

        // controleur.VerifierDiversité(pathname_Julien, 3,3);
      //controleur.controleurIHM(); //On précise ici l'utilisateur


        LectureFichier lect = new LectureFichier();
        lect.OuvrirFichier(pathname_Julien);
        List<List<String>> liste_QID = lect.getListeQuasiIdentifiants();
        AlgoMulti algM = new AlgoMulti();

        List<Integer> selectedQID= algM.selectQIDAtt(liste_QID, "Age");

        List<List<String>> lis_finale = new ArrayList<>();
       // [18000.0, 18500.0, 18510.0, 69100.0, 38000.0, 37000.0]
        List<String> lis_lys = new ArrayList<>();
        lis_lys.add("18000.0");
        lis_lys.add("18500.0");
        lis_lys.add("18510.0");
        lis_lys.add("69100.0");
        lis_lys.add("38000.0");
        lis_lys.add("37000.0");
        lis_finale.add(lis_lys);
        lis_finale = algM.groupeAlgoMulti(lis_finale,selectedQID,2,liste_QID, "Age");
        System.out.println(lis_finale);
      //  System.out.println(algM.ggmulti(selectedQID,lis_groupe,liste_QID,"Age"));


     //   [[18000, 18500, 18510], [38000, 37000], [69000, 69100, 69300], [98000, 74000]]
      /*  selectedQID= algM.selectQIDAtt(liste_QID,"CP");
        lis_groupe.clear();
        lis_groupe  = algM.groupeAlgoMulti(lis_groupe,selectedQID,5);
        liste_QID = algM.ggmulti(selectedQID,lis_groupe,liste_QID,"CP");
        System.out.println(liste_QID);
// [[1, 3, 4, 9, 10], [2, 5, 6, 7, 8]]
       */



















    }
}
