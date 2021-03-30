package sauvegarde;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import java.util.ArrayList;



/**
 * Class responsable de sauvegarder les fichiers traitées.
 */
public class EnregistrementFichier {


    // **** Attributs ****

    /**
     * un <code>HSSFWorkbook</code> qui récupère les données traitées
     */
    private HSSFWorkbook xlsAnonymise;

    /**
     * permet de sauvegarder le chemin d'accès du fichier de sortis
     * nb : changement du pathName par Constructeur de la class
     */
    private String pathSauvegardeFichier;


    // **** Constructeurs ****

    /**
     * Constructeur par défaut
     */
    public EnregistrementFichier() {
        this.xlsAnonymise = null;
        this.pathSauvegardeFichier = "./out/production/xlsanon.xls";
    }



    // **** Methodes ****

    /**
     * Methode permettant de créer un ficher <code>.xls</code> vide
     * @return vide     Un fichier <code>.xls</code> vide
     */
    private HSSFWorkbook Initialisation() {
        HSSFWorkbookFactory crea = new HSSFWorkbookFactory();

        return crea.create();
    }

    /**
     * Methode permettant de clonner une liste données sur une colonne
     * @param liste     La liste à cloner
     * @param excel     Le document sur lequel on clone la colonne
     *                  le numéro de la colonne sur laquelle copier la liste
     * @return          Le document modifié
     */
    private  HSSFWorkbook clonageColonne(ArrayList<String> liste, HSSFWorkbook excel, int numColonne) {

    }

}
