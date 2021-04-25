import controleur.Controleur;
import vue.*;
import java.util.*;

import java.io.IOException;
//ATTENTION IMPORT VUE POUR TEST

/**
 * Main du projet : réalise les intéractions utilisateur et fait l'appel <code>Controleur</code>*/
public class Main {
    public static void main(String[] args) throws Exception {

        String pathname_Ian = "E:/Documents/Etude/L2/Semestre 4/Conception et dev/projetanon/exemple_ce.xls";
        String pathname_Julien = "C:/Users/jujuo/Desktop/CDA_proj/exemple_ce.xls";
        String pathname_Marilou = "F:/Semestre 4/exemple.xls";


        Controleur controleur = new Controleur("Ian", true); //Préciser ici en paramètre le chemin d'accès d'arrivée du fichier, sinon il ya un chemin par défaut
       //controleur.CreerDocPseudonymisé("C:/Users/jujuo/Desktop/CDA_proj/exemple_ce.xls");

    //  controleur.CreerDocsBucketisés("C:/Users/jujuo/Desktop/CDA_proj/pseudos.xls", 3, "QID", "DS"); //Attention cette commande prend une base de données PSEUDONYMISEE en paramètre

      controleur.controleurAlgo1(pathname_Ian);

      // controleur.VerifierDiversité(pathname_Julien, 3,3);

     // controleur.controleurIHM("Julien"); //On précise ici l'utilisateur








    }

}
