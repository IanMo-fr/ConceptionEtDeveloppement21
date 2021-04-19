package contrôleur;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sauvegarde.*;
import lecture.*;
import traitement.*;


import lecture.LectureFichier;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sauvegarde.EnregistrementFichier;


import java.io.IOException;
import java.util.List;

public class Controleur {

    // **** Attributs ****

    private String pathname;
    private String arrivee;


    // **** constructeurs ****

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
    public Controleur() {
        this("C:/Users/jujuo/Desktop/CDA_projet/test.xls");
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
