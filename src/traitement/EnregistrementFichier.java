package traitement;

import org.apache.poi.hssf.usermodel.*;
import java.io.*;

import java.util.List;

/**
 * Classe responsable de sauvegarder les fichiers traitees.
 */
public class EnregistrementFichier {

    /**
     * Constructeur par defaut d'EnregistrementFichier
     */
    public EnregistrementFichier() {}

    /**
     * Methode qui permet d'enregistrer un fichier workbook donne en parametre au chemin "arrivee"
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
