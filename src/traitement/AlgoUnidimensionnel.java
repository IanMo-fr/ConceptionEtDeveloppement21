package traitement;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AlgoUnidimensionnel implements ArbreGeneralisation {
    /**
     * @return le Workbook après passage par l'algorithme
     */
    @Override
    public HSSFWorkbook anonyme(List<String> listeAttribut, HSSFWorkbook wb,String nomAttr) {

        //liste utilisee pour stocker les valeurs numériques de la liste des QID choisit
        List<Integer> attributNum = new LinkedList<Integer>();

        //on coupe en deux la liste de l'attibut pour avoir une généralisation par la médiane
        List<Integer> rHands;
        List<Integer> lHands;
        //on cherche le num de colonne de l'attribut
        int row = -9999;
        HSSFSheet sheet_donnees = wb.getSheet("donnees");
        for (int i=0; i < sheet_donnees.getRow(0).getLastCellNum(); i++) {
            if (sheet_donnees.getRow(0).getCell(i).getStringCellValue().compareTo(nomAttr) == 0)
                row = i;
        }
        //On transforme les valeurs numérique stocké en String en Integer pour l'appel de la fct mediane
        for (int taille = 1; taille < listeAttribut.size(); taille++) {
            float float_val = Float.parseFloat(listeAttribut.get(taille));
            int ajout = Math.round(float_val);
            attributNum.add(ajout);
        }

        //appel de fct mediane sur l'attribut
        int mediane = this.mediane(attributNum);

        //borne de la médiane
        int lowMediane = mediane;
        int upperMediane = mediane;

        //on trie la liste d'attibuts
        Collections.sort(attributNum);

        //while utilisé si jamais la médiane ne se trouve pas dans la liste, on cherche docn la borne la plus proche de
        //la valeur de la médiane
        while (!attributNum.contains(lowMediane)){
            lowMediane--;}
        //on mets un +1 car subList prends la borne sup exclue
        rHands = attributNum.subList(0, attributNum.lastIndexOf(lowMediane)+1);
        while (!attributNum.contains(upperMediane)){
            upperMediane++;}
        lHands = attributNum.subList(attributNum.indexOf(upperMediane), attributNum.size());



        //on verifie si la valeur à la cellule visitée est égale à une valeur de l'une des sub-listes
        //et on modifie par la borne basse et haute de la sub-liste correspondante
        for (int cell = 1; cell <(rHands.size()+ lHands.size()+1); cell++) {
            //NullPointerException ici aussi .. ouais !
            if (rHands.contains(sheet_donnees.getRow(cell).getCell(row).toString()))
                sheet_donnees.getRow(cell).getCell(row).setCellValue(rHands.get(0) + "-" + rHands.get(rHands.size() - 1));
            else
                sheet_donnees.getRow(cell).getCell(row).setCellValue(lHands.get(0) + "-" + lHands.get(lHands.size() - 1));
        }

        Collections.sort(listeAttribut);

        if (rHands.size()<3 ) {
            anonyme(listeAttribut.subList(0, attributNum.lastIndexOf(lowMediane)+1), wb, "Age");
        }

        if (lHands.size()<3) {
            anonyme(listeAttribut.subList(attributNum.indexOf(upperMediane), attributNum.size()), wb, "Age");
        }



        return wb;
    }

}
