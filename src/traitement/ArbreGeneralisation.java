package traitement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface ArbreGeneralisation {

    // **** Attributs ****

    // **** Methodes ****

    /**
     *
     * @return      le Workbook après passage par l'algorithme
     */
   public List<String> anonyme(List<Integer> listeQID, List<String> val_string, int k) ;

    /**
     * Calcule la fréquence d'apparition d'un attribut donné
     * @param donnee
     * @return
     */
   private List<Integer> frequences(List<List<String>> donnee, int attribut){
       //On ne choisit que des attributs numériques
       return null;
   }

    /**
     * /!\ on récupère la valeur de la médiane et non pas l'indice où se trouve la médiane:: peut être à changer /!\
     * Calcul de la médiane d'une liste <Integer> donnée
     * @param list     la liste <Integer> avec la fréquence d'apparition d'un attribut
     * @return int          la médiane
     */
    default int mediane(List<Integer> list){
       //on classe les valeurs de la serie dans l'orde croissant
       //frequence.sort(Integer::compareTo);
        List<Integer> frequence = new ArrayList<Integer>();
        frequence.addAll(list);
        Collections.sort(frequence);

       //Si le nombre de valeurs de la liste est pair : on fait la moyenne des deux valeurs du milieu
       if (frequence.size()%2 ==0 )
           return ( ((frequence.get(frequence.size()/2)-1)+frequence.get(frequence.size()/2)) /2 );
       //Si le nombre de valeurs est impair : on prend la valeur du milieu
       if (frequence.size()%2 != 0)
           return (frequence.get(frequence.size()/2 ));

   }

}
