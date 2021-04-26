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
        // /!\ les valeurs de sheet_donnees sont au format 20-23 à la deuxième it'
        HSSFSheet sheet_donnees = wb.getSheet("donnees");
        //liste de stockage des valeurs aprox
        String [] val_String = new String[listeAttribut.size()-1];

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

        // /!\ deuxième it' : pas de première ligne à enlevée
        for (int cell = 0; cell <listeAttribut.size()-1; cell++) {
            //transformation de la valeur des cellules en Integer pour le test

            //on saute la première ligne
            if(cell != 0) {
                float float_val = Float.parseFloat(sheet_donnees.getRow(cell).getCell(row).toString());
                int test = Math.round(float_val);
                Integer val_rh = test;
                //on rentre les données dans le tableau
                if (rHands.contains(val_rh))
                    val_String[cell] = rHands.get(0) + "-" + rHands.get(rHands.size() - 1);
                else
                    val_String[cell] = lHands.get(0) + "-" + lHands.get(lHands.size() - 1);
            }
        }


        //on tri la listeAttribut pour envoyer une sub-liste égale aux données de rHands et lHands
        Collections.sort(listeAttribut);

        if (rHands.size()>4 ) {
            anonyme(listeAttribut.subList(0, attributNum.lastIndexOf(lowMediane)), wb, nomAttr);
        }

        if (lHands.size()>4) {
            anonyme(listeAttribut.subList(attributNum.indexOf(upperMediane), attributNum.size()), wb, nomAttr);
        }


        //parcours de la colonne pour copier les données de l'arrayList
        for (int i= 1; i < val_String.length; i++){
            sheet_donnees.getRow(i).getCell(row).setCellValue(val_String [i]);
        }

        return wb;
    }

}
