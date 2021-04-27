package traitement;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



//Récupérer le QID dont on a besoin dans une liste, la trier, récupérer la médiane, séparer partie haute/ partie basse
//

public class AlgoUnidimensionnel implements ArbreGeneralisation {

    private HSSFWorkbook creerAnonyme(HSSFWorkbook wb, List<String> val_string, String nomAttr) {
       HSSFSheet sheet_donnees = wb.getSheet("donnees");
       int colonne = -999;
        for (int i=0; i < sheet_donnees.getRow(0).getLastCellNum(); i++) {
            if (sheet_donnees.getRow(0).getCell(i).getStringCellValue().compareTo(nomAttr) == 0)
                colonne = i;
        }

        for (int i= 0; i < val_string.size(); i++){
            sheet_donnees.getRow(i+1).getCell(colonne).setCellValue(val_string.get(i));
        }

        return wb;
    }

    public HSSFWorkbook algoUni(List<List<String>> listeQID, String nomAttr, HSSFWorkbook wb,int k){
        List<Integer> liste = selectQIDAtt(listeQID, nomAttr);
        List<String> val_string = new ArrayList<>();
        for (int i=0; i<liste.size();i++) {
            val_string.add("");
        }
        List<String> aprox =  anonyme(liste,val_string, k);
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
    public List<String> anonyme(List<Integer> listeQID, List<String> val_String, int k) {

        int mediane = 0;

        List<Integer> attributOG = new ArrayList<>();

        attributOG.addAll(listeQID);
        //on trie la liste d'attibuts
        Collections.sort(listeQID);

        //appel de fct mediane sur l'attribut
        if (val_String.get(0).equals("")) {
            mediane = this.mediane(listeQID);
        }  else {
            List<String>arrayOfAprox = new ArrayList<>() ;
            arrayOfAprox.addAll(val_String);
            Collections.sort(arrayOfAprox);
            int diversiteI = 1;
            for(int i=0; i< val_String.size()-1; i++) {

                if (arrayOfAprox.get(i).equals(arrayOfAprox.get(i+1))) {
                    diversiteI ++;
                } else {//reset du compteur, la liste arrayOfAprox est triée
                    diversiteI=1;
                }
              /*  if (diversiteI >= k) {
                    List<Integer> frequence = new ArrayList<Integer>();
                    frequence.addAll(listeQID.subList(/*on cherche à recupérer l'ensemble des valeurs à redécouper, i.e toute les valeures comprise
                    entre 16 et 22*//*)));

                    mediane = this.mediane(frequence);
                    break;
                    */

                }
            }
      //  }
        //borne de la médiane
        int lowMediane = mediane;
        int upperMediane = mediane;


        //on coupe en deux la liste de l'attibut pour avoir une généralisation par la médiane
        String rHands;
        String lHands;

            //Si la médiane est une valeur de liste initialement ou non
            if (listeQID.contains(mediane)) {
                lHands = listeQID.get(0) + "-" + listeQID.get(listeQID.lastIndexOf(mediane));
                rHands = (listeQID.get(listeQID.indexOf(mediane) + 1) + "-" + listeQID.get(listeQID.lastIndexOf(lowMediane)*2) );
                //upperMediane++;
            }else{

                //si jamais la médiane ne se trouve pas dans la liste, on cherche la borne la plus proche de la valeur de la médiane
                while (!listeQID.contains(lowMediane) && lowMediane >= 0) {
                    lowMediane--;
                }
                lHands = listeQID.get(0) + "-" + listeQID.get(listeQID.lastIndexOf(lowMediane));

                //on mets un +1 car subList prends la borne sup exclue

                while (!listeQID.contains(upperMediane) && upperMediane < listeQID.get(listeQID.size()-1)) {
                    upperMediane++;
                }
                if(listeQID.indexOf(upperMediane)+1>= listeQID.size())
                    rHands = (listeQID.get(listeQID.indexOf(upperMediane))) + "-" + listeQID.get(listeQID.lastIndexOf(lowMediane)*2);
                else
                    rHands = (listeQID.get(listeQID.indexOf(upperMediane)+1)) + "-" + listeQID.get(listeQID.lastIndexOf(lowMediane)*2);
            }


        //on verifie si la valeur à la cellule visitée est égale à une valeur de l'une des sub-listes
        //et on modifie par la borne basse et haute de la sub-liste correspondante
        for (int i = 0; i <attributOG.size(); i++) {
            //on rentre l'approximation dans la liste


            if ((attributOG.get(i) >= Integer.parseInt(lHands.substring(0,lHands.indexOf("-"))))
                 && (attributOG.get(i) <= Integer.parseInt(lHands.substring(lHands.indexOf("-")+1,lHands.length()))))
                val_String.set(i, lHands);
            else if ((attributOG.get(i) >= Integer.parseInt(rHands.substring(0,rHands.indexOf("-"))))
                    && (attributOG.get(i) <= Integer.parseInt(rHands.substring(rHands.indexOf("-")+1,rHands.length()))))
                val_String.set(i, rHands);
        }

       if (mediane>4 ) {
            anonyme(attributOG,val_String, k);
        }


        return val_String;
    }

}
