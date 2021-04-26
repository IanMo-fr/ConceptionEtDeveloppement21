package traitement;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AlgoUnidimensionnel implements ArbreGeneralisation {

    private HSSFWorkbook creerAnonyme(HSSFWorkbook wb, List<String> val_string, String nomAttr) {
       HSSFSheet sheet_donnees = wb.getSheet("donnees");
       int colonne = -999;
        for (int i=0; i < sheet_donnees.getRow(0).getLastCellNum(); i++) {
            if (sheet_donnees.getRow(0).getCell(i).getStringCellValue().compareTo(nomAttr) == 0)
                colonne = i;
        }

        for (int i= 1; i < val_string.size(); i++){
            sheet_donnees.getRow(i).getCell(colonne).setCellValue(val_string.get(i));
        }

        return wb;
    }

    public HSSFWorkbook algoUni(List<List<String>> listeQID, String nomAttr, HSSFWorkbook wb){
        List<Integer> liste = selectQIDAtt(listeQID, nomAttr);
        List<String> aprox =  anonyme(liste);
        return creerAnonyme(wb, aprox, nomAttr);
    }


  private List<Integer> selectQIDAtt (List<List<String>> listeQID, String nomAttr) {
      //la liste de l'attibut QID à modifier
      List<String> listeAttribut = new ArrayList<>();


      for (int i = 0; i < listeQID.size(); i++ ) {
          if(listeQID.get(i).get(0).equals(nomAttr))
              listeAttribut = listeQID.get(i);
      }

      //on retire la première ligne : le nom de l'attribut
      listeAttribut.remove(0);


      //liste utilisee pour stocker les valeurs numériques de la liste des QID choisit
      List<Integer> attributNum = new LinkedList<Integer>();


      //On transforme les valeurs numérique stocké en String en Integer pour l'appel de la fct mediane
      for (int taille = 0; taille < listeAttribut.size(); taille++) {
          float float_val = Float.parseFloat(listeAttribut.get(taille));
          int ajout = Math.round(float_val);
          attributNum.add(ajout);
      }
      return attributNum;
  }

    /**
     * @return le Workbook après passage par l'algorithme
     */
    @Override
    public List<String> anonyme(List<Integer> listeQID) {

        //appel de fct mediane sur l'attribut
        int mediane = this.mediane(listeQID);

        //borne de la médiane
        int lowMediane = mediane;
        int upperMediane = mediane;

        List<Integer> attributOG = new ArrayList<>();

        attributOG.addAll(listeQID);
        //on trie la liste d'attibuts
        Collections.sort(listeQID);

        //on coupe en deux la liste de l'attibut pour avoir une généralisation par la médiane
        List<Integer> rHands = new ArrayList<>();
        List<Integer> lHands = new ArrayList<>();

        //Si la médiane est une valeur de liste initialement ou non
        if (listeQID.contains(mediane)) {
            lHands = listeQID.subList(0, listeQID.lastIndexOf(mediane)+1);
            rHands = listeQID.subList(listeQID.indexOf(mediane)+1, listeQID.size());
            upperMediane++;
        }else {
            //si jamais la médiane ne se trouve pas dans la liste, on cherche la borne la plus proche de la valeur de la médiane
                while (!listeQID.contains(lowMediane)) {
                    lowMediane--;
                }
                lHands = listeQID.subList(0, listeQID.lastIndexOf(lowMediane) + 1);

            //on mets un +1 car subList prends la borne sup exclue

                while (!listeQID.contains(upperMediane)) {
                    upperMediane++;
                }
                rHands = listeQID.subList(listeQID.indexOf(upperMediane), listeQID.size());

        }
        //liste de stockage des valeurs aprox
        List<String> val_String = new ArrayList<String>();
        //on verifie si la valeur à la cellule visitée est égale à une valeur de l'une des sub-listes
        //et on modifie par la borne basse et haute de la sub-liste correspondante
        for (int i = 0; i <attributOG.size(); i++) {


            //on rentre l'approximation dans la liste
            if (lHands.contains(attributOG.get(i)))
                val_String.add(lHands.get(0) + "-" + lHands.get(lHands.size()-1));
            else
                val_String.add(rHands.get(0) + "-" + rHands.get(rHands.size()-1));
        }

        if (lHands.size()>4 ) {
            anonyme(listeQID.subList(0, listeQID.lastIndexOf(lowMediane)));
        }

        if (rHands.size()>4) {
            anonyme(listeQID.subList(listeQID.indexOf(upperMediane), listeQID.size()));
        }

        return val_String;
    }

}
