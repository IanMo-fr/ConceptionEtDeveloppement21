package traitement;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlgoUnidimensionnel implements ArbreGeneralisation {
    /**
     * @return le Workbook après passage par l'algorithme
     */
    @Override
    public HSSFWorkbook anonyme(List<String> listeAttribut, HSSFWorkbook wb) {

        //liste utilisee pour stocker les valeurs numériques de la liste des QID choisit
        List<Integer> attribut = new LinkedList<Integer>();


        //On transforme les valeurs numérique stocké en String en Integer pour l'appel de la fct mediane
        for (int taille = 0; taille < listeAttribut.size(); taille++) {
            attribut.add(Integer.parseInt(listeAttribut.get(taille)));
        }

        //appel de fct mediane sur l'attribut
        int mediane = this.mediane(attribut);

        //on coupe en deux la liste de l'attibut pour avoir une généralisation par la médiane
        List<String> rHands;
        List<String> lHands;
        rHands = listeAttribut.subList(0, mediane);
        lHands = listeAttribut.subList(mediane, attribut.size());

        HSSFSheet sheet_donnees = wb.getSheet("donnees");

        for (int a = 0; a < listeAttribut.size(); a++) {
            for (int b = 0; b < listeAttribut.size(); b++) {
                if (rHands.contains(sheet_donnees.getRow(b).getCell(a).toString()))
                    sheet_donnees.getRow(b).getCell(a).setCellValue(rHands.get(0)+"-"+rHands.get(rHands.size()-1));
            }
        }


        return null;
    }

}
