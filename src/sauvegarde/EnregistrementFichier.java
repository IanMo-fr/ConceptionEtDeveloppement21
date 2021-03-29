package sauvegarde;

import org.apache.poi.hssf.usermodel.*;
import java.io.*;
import lecture.*;

import traitement.Controle;

import java.util.ArrayList;
import java.util.List;

/**
 * Class responsable de sauvegarder les fichiers traitées.
 */
public class EnregistrementFichier {

    HSSFWorkbook wb;

    public void EnregistrerFichier(List<List<String>> ListePseudos, HSSFWorkbook wb, String arrivee) {
        HSSFSheet sheet_donnees = wb.getSheet("donnees");

        for (int a = 0; a < ListePseudos.size(); a++) {
            for (int b = 0; b < ListePseudos.get(a).size(); b++) {
                sheet_donnees.getRow(b+1).getCell(a).setCellValue(ListePseudos.get(a).get(b));
            }
        }

        this.wb = wb;

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
