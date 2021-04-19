package sauvegarde;

import org.apache.poi.hssf.usermodel.*;
import java.io.*;

import java.util.List;

/**
 * Class responsable de sauvegarder les fichiers traitées.
 */
public class EnregistrementFichier {

    // **** Attributs ****


    // **** constructeurs ****

    // **** Méthodes ****

    /**
     * Méthode qui permet de créer un fichier Excel identique au premier mais avec les ID pseudonymisés, et d'enregistrer ce dernier au chemin d'accès spécifié
     * @param ListePseudos
     * @param wb
     */
    public void ModifierIDFichier(List<List<String>> ListePseudos, HSSFWorkbook wb) {

        HSSFSheet sheet_donnees = wb.getSheet("donnees");

        for (int a = 0; a < ListePseudos.size(); a++) {
            for (int b = 0; b < ListePseudos.get(a).size(); b++) {
                sheet_donnees.getRow(b).getCell(a).setCellValue(ListePseudos.get(a).get(b));
            }
        }
    }


    /**
     * Méthode qui permet de créer un fichier Excel identique au premier mais avec les ID pseudonymisés, et d'enregistrer ce dernier au chemin d'accès spécifié
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
