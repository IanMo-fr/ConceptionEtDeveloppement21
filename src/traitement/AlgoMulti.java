package traitement;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.*;

public class AlgoMulti {

    /**
     * Calcul de la médiane d'une liste
     *
     * @param maliste   la list de type T : <code>Integer</code> non trié dont on calcule la médiane
     * @return mediane  la médiane
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
     * Sélection du QID qui nous intéresse et transformation en Integer list sans le label la colonne
     *
     * @param listeQID              La liste de liste des QID
     * @param nomAttr               Le label, le nom de la colonne recherchée
     * @return listeAttributNum     la liste d'attribut du QID séléctionné par le nom
     */
    public List<Integer> selectQIDAtt(List<List<String>> listeQID, String nomAttr) {


        List<String> listeAttribut = new ArrayList<>();


        for (int i = 0; i < listeQID.size(); i++) {
            if (listeQID.get(i).get(0).equals(nomAttr))
                listeAttribut.addAll(listeQID.get(i));
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


public List<List<String>> groupeAlgoMulti(List<List<String>> lis_finale, List<Integer> listeAttribut, int k, List<List<String>> tous_QID, String nomAttrDepart) {

        if (lis_finale.size()!=0) {
            lis_finale.remove(0);
        }


        Hashtable dic = new Hashtable(tous_QID.size());
        for (int i =0;i<tous_QID.size();i++) {
            dic.put(tous_QID.get(i).get(0), i);
        }


        List<List<Integer>> lis_lis_position = new ArrayList<>();
        List<List<List<String>>> liste_liste_groupes = new ArrayList<>();

        int mediane = calculMediane(listeAttribut);

        List<Integer> Lis_gauche = new ArrayList<>();
        List<Integer> Lis_droite = new ArrayList<>();

        List<Integer> liste_position_gauche = new ArrayList<>();
        List<Integer> liste_position_droite = new ArrayList<>();
        for (int i = 0; i < listeAttribut.size(); i++) {
            if (listeAttribut.get(i) <= mediane) {
                liste_position_gauche.add(i+1);
                Lis_gauche.add(listeAttribut.get(i ));
            } else {

                liste_position_droite.add(i+1);
                Lis_droite.add(listeAttribut.get(i ));
            }
        }

        if ((liste_position_gauche.size())>=k || liste_position_droite.size()==0) {
            lis_lis_position.add(liste_position_gauche);
            //lis_finale.add(Lis_gauche);
        }


        if (liste_position_droite.size()<k) {
           // lis_finale.get(lis_finale.size()-1).addAll(Lis_droite);
            Lis_droite.clear();
            liste_position_gauche.addAll(liste_position_droite);
            liste_position_droite.clear();
        }
        else {
            lis_lis_position.add(liste_position_droite);
           // lis_finale.add(Lis_droite);
        }




//[[[18000.0, 18500.0, 18510.0, 69100.0, 38000.0, 37000.0], [69000.0, 69300.0, 98000.0, 74000.0]], [[22.0, 21.0, 20.0, 26.0, 16.0, 26.0], [27.0, 28.0, 40.0, 32.0]]]


        for (int a=0;a<tous_QID.size();a++) {
            List<List<String>> liste_groupes = new ArrayList<>();

            for (int b=0;b<lis_lis_position.size();b++) {

                List<String> groupes = new ArrayList<>();

                for (int c=0; c<lis_lis_position.get(b).size();c++) {
                    groupes.add(tous_QID.get(a).get(lis_lis_position.get(b).get(c)));


                }
                liste_groupes.add(groupes);
                if (nomAttrDepart.equals(tous_QID.get(a).get(0))) {
                    lis_finale.add(groupes);
                }

            }

           // lis_finale.remove(0);
            liste_liste_groupes.add(liste_groupes);

        }

   // System.out.println(liste_liste_groupes);
    //System.out.println(lis_lis_position);

        Random rand = new Random();

        int new_QID = rand.nextInt(tous_QID.size());
        //int new_QID = (int) dic.get("Age");
        List<String> prochain_attr_gauche_string = liste_liste_groupes.get(new_QID).get(0);
        List<String> prochain_attr_droite_string = liste_liste_groupes.get(new_QID).get(1);



        //[[21, 20, 16], [22, 26], [27, 26, 28], [40, 32]]

        //[[18000, 18500, 18510], [38000, 37000], [69000, 69100, 69300], [98000, 74000]]



        List<Integer> prochain_attr_gauche = new ArrayList<>();
        for (int i=0;i<prochain_attr_gauche_string.size();i++) {
            float float_val = Float.parseFloat(prochain_attr_gauche_string.get(i));
            int ajout = Math.round(float_val);
            prochain_attr_gauche.add(ajout);
        }

        List<Integer> prochain_attr_droite = new ArrayList<>();
        for (int i=0;i<prochain_attr_droite_string.size();i++) {
            float float_val = Float.parseFloat(prochain_attr_droite_string.get(i));
            int ajout = Math.round(float_val);
            prochain_attr_droite.add(ajout);
        }

        if (liste_position_gauche.size()/k >=2 && liste_position_droite.size()!=0) {

            groupeAlgoMulti(lis_finale,prochain_attr_gauche,k,tous_QID, nomAttrDepart);
            //lis_finale.remove(0);


        }
        if (liste_position_droite.size()/k >=2 && liste_position_gauche.size()!=0) {

            groupeAlgoMulti(lis_finale,prochain_attr_droite,k,tous_QID, nomAttrDepart);
           // lis_finale.remove(0);



        }


        return lis_finale;

    }















  /*  public  List<List<List<String>>> groupeAlgoMulti( List<List<List<String>>> liste_liste_liste_groupes, List<Integer> listeAttribut, int k, List<List<String>> tous_QID, String nomAttr) {



        Hashtable dic = new Hashtable(tous_QID.size());
        for (int i =0;i<tous_QID.size();i++) {
            dic.put(tous_QID.get(i).get(0), i);
        }
List<List<Integer>> lis_lis_position = new ArrayList<>();
        List<List<List<String>>> liste_liste_groupes = new ArrayList<>();
        int mediane = calculMediane(listeAttribut);

        List<Integer> liste_position_gauche = new ArrayList<>();
        List<Integer> liste_position_droite = new ArrayList<>();
        for (int i = 0; i < listeAttribut.size(); i++) {
            if (listeAttribut.get(i) <= mediane) {
                liste_position_gauche.add(i+1);
            } else {

                liste_position_droite.add(i+1);
            }
        }

        if ((liste_position_gauche.size())>=k || liste_position_droite.size()==0) {
            lis_lis_position.add(liste_position_gauche);

        }


        if (liste_position_droite.size()<k) {
            liste_position_gauche.addAll(liste_position_droite);
            liste_position_droite.clear();
        }
        else {
            lis_lis_position.add(liste_position_droite);
        }






        for (int a=0;a<tous_QID.size();a++) {
            List<List<String>> liste_groupes = new ArrayList<>();

            for (int b=0;b<lis_lis_position.size();b++) {

                List<String> groupes = new ArrayList<>();
                for (int c=0; c<lis_lis_position.get(b).size();c++) {
                    groupes.add(tous_QID.get(a).get(lis_lis_position.get(b).get(c)));

                }
                liste_groupes.add(groupes);
            }


            liste_liste_groupes.add(liste_groupes);

        }

        //System.out.println(liste_liste_groupes);

         int new_QID = (int) dic.get("CP");
         List<String> prochain_attr_gauche_string = liste_liste_groupes.get(new_QID).get(0);
         List<String> prochain_attr_droite_string = liste_liste_groupes.get(new_QID).get(1);
     //   [[18000.0, 18500.0, 18510.0, 69100.0, 38000.0, 37000.0], [69000.0, 69300.0, 98000.0, 74000.0]]

        List<Integer> prochain_attr_gauche = new ArrayList<>();
        for (int i=0;i<prochain_attr_gauche_string.size();i++) {
            float float_val = Float.parseFloat(prochain_attr_gauche_string.get(i));
            int ajout = Math.round(float_val);
            prochain_attr_gauche.add(ajout);
        }

        List<Integer> prochain_attr_droite = new ArrayList<>();
        for (int i=0;i<prochain_attr_droite_string.size();i++) {
            float float_val = Float.parseFloat(prochain_attr_droite_string.get(i));
            int ajout = Math.round(float_val);
            prochain_attr_droite.add(ajout);
        }

        for (int i=0;i<liste_liste_groupes.size();i++) {
                liste_liste_liste_groupes.add(liste_liste_groupes.get(i));
            }

       // liste_liste_liste_groupes.add(liste_liste_groupes);

        if (liste_position_gauche.size()/k >=2 && liste_position_droite.size()!=0) {

            groupeAlgoMulti(liste_liste_liste_groupes,prochain_attr_gauche,k,tous_QID,"Age");


        }
        if (liste_position_droite.size()/k >=2 && liste_position_gauche.size()!=0) {

            groupeAlgoMulti(liste_liste_liste_groupes,prochain_attr_droite,k,tous_QID,"Age");



        }




        return liste_liste_liste_groupes;

    }


    */



}
