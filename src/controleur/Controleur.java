package controleur;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import traitement.*;


import traitement.LectureFichier;
import traitement.EnregistrementFichier;


import java.io.IOException;
import java.util.List;


/**
 * Fait le lien entre l'interface Homme-machine du package <code>vue</code> et les class de type "modèle" du package <code>traitement</code>
 */
public class Controleur {

    // **** Attributs ****

    private String pathname;
    private String arrivee;


    // **** constructeurs ****
    /**
     * Constructeur par défaut
     */
    public Controleur(){
        this.arrivee = null;
        this.pathname = null;
    }

    /**
     * Constructeur de Controle
     * @param arrivee
     */
    public Controleur(String arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * Constructeur surchargé de Controle : permet d'avoir un chemin d'arrivée par défaut si non spécifié
     */
    public Controleur(String user, boolean test) {
        //todo : mettre nos chemin d'accès pour les testes dans un "case"
        switch (user) {
            case "Julien":
                this.arrivee = ("C:/Users/jujuo/Desktop/CDA_projet/test.xls");
                break;

        }
    }



    /**
     * Permet de réaliser bout à bout la création et l'enregistrement d'un fichier Excel Pseudonymisé à partir d'un fichier Excel donné (par son chemin d'accès)
     * @param pathname
     * @throws IOException
     */
    public void CreerDocPseudonymisé(String pathname) throws IOException {
        this.pathname = pathname;
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        List<List<String>> ListeId = Ouverture.getListeIdentifiants();
        Pseudonymisation GenererPseudos = new Pseudonymisation();
        GenererPseudos.Pseudonymiser(ListeId);
        List<List<String>> ListePseudos = GenererPseudos.getListePseudos();
        EnregistrementFichier Enregistrement = new EnregistrementFichier();
        HSSFWorkbook wb = Ouverture.getWb();
        Enregistrement.ModifierIDFichier(ListePseudos,wb);
        Enregistrement.EnregistrerFichier(wb,arrivee);

    }

}
