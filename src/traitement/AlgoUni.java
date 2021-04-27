package traitement;

import java.util.*;

public class AlgoUni {

    /**
     * Calcul de la médiane d'une liste
     *
     * @param maliste
     * @return
     */

    public int calculMediane(List<Integer> maliste) {
        List<Integer> liste_clone = new ArrayList<>();
        liste_clone.addAll(maliste);
        int mediane;
        int ind_milieu1;
        int ind_milieu2;

        Collections.sort(liste_clone);

        if (liste_clone.size() % 2 == 0) {// [2,3,5,5,6,7,8,9,10] 0 1 2 3 4 5 6 7 8 9/2 = 4
            ind_milieu1 = (liste_clone.size() / 2) - 1;
            ind_milieu2 = liste_clone.size() / 2;
            mediane = (liste_clone.get(ind_milieu1) + liste_clone.get(ind_milieu2)) / 2;
        }
        //Si le nombre de valeurs est impair : on prend la valeur du milieu
        else {
            ind_milieu1 = (liste_clone.size() / 2);
            mediane = liste_clone.get(ind_milieu1);
        }

        return mediane;
    }


    /**
     * Sélection du QID qui nous intéresse et transformation en Integer list sans le label de colonne
     *
     * @param listeQID
     * @param nomAttr
     * @return
     */
    private List<Integer> selectQIDAtt(List<List<String>> listeQID, String nomAttr) {


        List<String> listeAttribut = new ArrayList<>();


        for (int i = 0; i < listeQID.size(); i++) {
            if (listeQID.get(i).get(0).equals(nomAttr))
                listeAttribut = listeQID.get(i);
        }

        //on retire la première ligne : le nom de l'attribut
        listeAttribut.remove(0);


        //liste utilisee pour stocker les valeurs numériques de la liste des QID choisit
        List<Integer> listeAttributNum = new LinkedList<Integer>();


        //On transforme les valeurs numérique stocké en String en Integer pour l'appel de la fct mediane
        for (int taille = 0; taille < listeAttribut.size(); taille++) {
            float float_val = Float.parseFloat(listeAttribut.get(taille));
            int ajout = Math.round(float_val);
            listeAttributNum.add(ajout);
        }

        return listeAttributNum;
    }


    public List<List<Integer>> faireAlgoUni(List<List<Integer>> testo, List<Integer> listeAttribut, String nomAttr) {

        int mediane = calculMediane(listeAttribut);
        List<Integer> Lis_gauche = new ArrayList<>();
        List<Integer> Lis_droite = new ArrayList<>();

        for (int i = 0; i < listeAttribut.size(); i++) {
            if (listeAttribut.get(i) <= mediane) {
                Lis_gauche.add(listeAttribut.get(i ));
            } else {
                Lis_droite.add(listeAttribut.get(i));
            }
        }


        if ((Lis_gauche.size())>=2 || Lis_droite.size()==0) {
            testo.add(Lis_gauche);
        }


       if (Lis_droite.size()<2) {
            testo.get(testo.size()-1).addAll(Lis_droite);
            Lis_droite.clear();
        }
       else {
           testo.add(Lis_droite);
       }


        if (Lis_gauche.size()/2 >=2 && Lis_droite.size()!=0) {

            faireAlgoUni(testo, Lis_gauche,"toto");
            testo.remove(0);

        }
        if (Lis_droite.size()/2 >=2 && Lis_gauche.size()!=0) {

            faireAlgoUni(testo, Lis_droite,"toto");
            testo.remove(0);

        }





        return testo;

    }


}
