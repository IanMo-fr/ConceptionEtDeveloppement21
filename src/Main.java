import controleur.Controleur;

import java.io.IOException;
//ATTENTION IMPORT VUE POUR TEST

/**
 * Main du projet : réalise les intéractions utilisateur et fait l'appel <code>Controleur</code>*/
public class Main {
    public static void main(String[] args) throws IOException {
        Controleur controleur = new Controleur("Julien", true); //Préciser ici en paramètre le chemin d'accès d'arrivée du fichier, sinon il ya un chemin par défaut
        controleur.CreerDocPseudonymisé("C:/Users/jujuo/Desktop/CDA_proj/exemple_ce.xls");

       // controleur.CreerDocsBucketisés("C:/Users/jujuo/Desktop/CDA_proj/exemple_ce.xls", 3);
       //  IHM fenetre = new IHM();
       // fenetre.setVisible(true);


    }

}
