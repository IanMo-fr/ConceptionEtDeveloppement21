package traitement;

import org.apache.poi.hssf.usermodel.*;
import java.io.*;

import java.util.List;

/**
 * Class responsable de sauvegarder les fichiers traitées.
 */
public class EnregistrementFichier {

    /**
     * Constructeur par défaut d'EnregistrementFichier
     */
    public EnregistrementFichier() {}

    /**
     * Méthode qui permet d'enregistrer un fichier workbook donné en paramètre au chemin "arrivee"
     * @param arrivee
     */
    public void EnregistrerFichier(HSSFWorkbook wb, String arrivee) {
        try {
            FileOutputStream fileOut = new FileOutputStream(arrivee); //Ici mettre chemin où l'on veut stocker le fichier
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
