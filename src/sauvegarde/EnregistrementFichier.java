package sauvegarde;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;

/**
 * Class responsable de sauvegarder les fichiers traitées.
 */
public class EnregistrementFichier {
    /**
     * un <code>HSSFWorkbook</code> qui récupère les données traitées
     */
    private HSSFWorkbook xlsAnonymise;

    /**
     * permet de sauvegarder le chemin d'accès du fichier de sortis
     * nb : changement du pathName par Constructeur de la class
     */
    private String pathSauvegardeFichier;


    /**
     * Constructeur par défaut
     */
    public EnregistrementFichier() {
        this.xlsAnonymise = null;
        this.pathSauvegardeFichier = "./out/production";
    }

    /**
     * Methode permettant de créer un ficher <code>.xls</code> vide
     * @return vide     Un fichier <code>.xls</code> vide
     */
    private HSSFWorkbook Initialisation() {
        HSSFWorkbookFactory crea = new HSSFWorkbookFactory();

        return crea.create();
    }

}
