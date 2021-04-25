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

    do {
        //On transforme les valeurs numérique stocké en String en Integer pour l'appel de la fct mediane
        for (int taille = 0; taille < listeAttribut.size()-1; taille++) {
            // /!\ bug : ajout à la liste des valeurs null
            float float_val = Float.parseFloat(listeAttribut.get(taille+1));
            int ajout = Math.round(float_val);
            attribut.add(ajout);     //Integer.getInteger(listeAttribut.get(taille+1)));
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
                //on verifie si la valeur à la cellule visitée est égale à une valeur de l'une des sub-listes
                //et on modifie par la borne basse et haute de la sub-liste correspondante
                if (rHands.contains(sheet_donnees.getRow(b).getCell(a).toString()))
                    sheet_donnees.getRow(b).getCell(a).setCellValue(rHands.get(0) + "-" + rHands.get(rHands.size() - 1));
                else
                    sheet_donnees.getRow(b).getCell(a).setCellValue(lHands.get(0) + "-" + lHands.get(lHands.size() - 1));
            }
        }
    }while (attribut.size()>2);


        return wb;
    }

}
