package traitement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public interface ArbreGeneralisation {

    // **** Attributs ****

    // **** Methodes ****

    /**
     *
     * @return      le Workbook après passage par l'algorithme
     */
   public HSSFWorkbook anonyme();

    /**
     * Calcule la fréquence d'apparition d'un attribut donné
     * @param donnee
     * @return
     */
   private List<Integer> frequences(List<List<String>> donnee, int attribut){
       //liste de stockage des fréquences
       List <Integer> res = new LinkedList<Integer>();
       //liste des attibuts à évaluer
       List <String> att = donnee.get(attribut);

       //on itère pour toute la liste, chaque attribut :: oskour j c pa koman retiré lé doublon
       for (int i = 0; i < att.size(); i++){
            for (int frequence = 0; frequence < att.size(); frequence++){

            }
       }
       return res;
   }

    /**
     * /!\ on récupère la valeur de la médiane et non pas l'indice où se trouve la médiane:: peut être à changer /!\
     * Calcul de la médiane d'une liste <Integer> donnée
     * @param frequence     la liste <Integer> avec la fréquence d'apparition d'un attribut
     * @return int          la médiane
     */
   private int mediane(List<Integer> frequence){
       //on classe les valeurs de la serie dans l'orde croissant
       frequence.sort(Integer::compareTo);

       //Si le nombre de valeurs de la liste est pair : on fait la moyenne des deux valeurs du milieu
       if (frequence.size()%2 ==0 )
           return ( (frequence.get(frequence.size()/2)+frequence.get((frequence.size()/2)+1)) /2 );
       //Si le nombre de valeurs est impair : on prend la valeur du milieu
       if (frequence.size()%2 != 0)
           return (frequence.get(frequence.size()/2 ));
       // on n'est pas censé arriver là mais au cas où
       else
           return -9999;
   }

}