import controleur.Controleur;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import traitement.AlgoUni;
import vue.*;
import java.util.*;


import java.io.IOException;
//ATTENTION IMPORT VUE POUR TEST

/**
 * Main du projet : realise les interactions utilisateur et fait l'appel <code>Controleur</code>*/
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
        controleur.controleurIHM(); //On précise ici l'utilisateur
















    }
}
