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


public  List<List<Integer>> groupeAlgoMulti( List<List<Integer>> lis_lis_position, List<Integer> listeAttribut, int k, List<List<String>> tous_QID, List<Integer> liste_position) {


  /*  Hashtable dic = new Hashtable(tous_QID.size());
    for (int i = 0; i < tous_QID.size(); i++) {
        dic.put(tous_QID.get(i).get(0), i);
    }

   */

    int mediane = calculMediane(listeAttribut);


    List<Integer> liste_position_gauche = new ArrayList<>();
    List<Integer> liste_position_droite = new ArrayList<>();
    for (int i = 0; i < listeAttribut.size(); i++) {
        if (listeAttribut.get(i) <= mediane) {
            liste_position_gauche.add(liste_position.get(i));

        } else {

            liste_position_droite.add(liste_position.get(i));

        }
    }




        if (liste_position_droite.size() < k) {

            liste_position_gauche.addAll(liste_position_droite);
            liste_position_droite.clear();
        }

        if (liste_position_gauche.size() >= k) {
                lis_lis_position.add(liste_position_gauche);


            }

        if (liste_position_droite.size() >= k) {
            lis_lis_position.add(liste_position_droite);


        }

        lis_lis_position.remove(liste_position);

        Random rand = new Random();

        int new_QID = rand.nextInt(tous_QID.size());

        List<String> prochain_attr_gauche_string = new ArrayList<>();
        List<String> prochain_attr_droite_string = new ArrayList<>();

        for (int i=0;i<liste_position_gauche.size();i++) {
            prochain_attr_gauche_string.add(tous_QID.get(new_QID).get(liste_position_gauche.get(i)));
        }
        for (int i=0;i<liste_position_droite.size();i++) {
        prochain_attr_droite_string.add(tous_QID.get(new_QID).get(liste_position_droite.get(i)));
        }






        List<Integer> prochain_attr_gauche = new ArrayList<>();
        for (int i = 0; i < prochain_attr_gauche_string.size(); i++) {
            float float_val = Float.parseFloat(prochain_attr_gauche_string.get(i));
            int ajout = Math.round(float_val);
            prochain_attr_gauche.add(ajout);
        }

        List<Integer> prochain_attr_droite = new ArrayList<>();
        for (int i = 0; i < prochain_attr_droite_string.size(); i++) {
            float float_val = Float.parseFloat(prochain_attr_droite_string.get(i));
            int ajout = Math.round(float_val);
            prochain_attr_droite.add(ajout);
        }





        if (liste_position_gauche.size() / k >= 2 && liste_position_droite.size() != 0) {

            groupeAlgoMulti(lis_lis_position, prochain_attr_gauche, k, tous_QID, liste_position_gauche);


        }
        if (liste_position_droite.size() / k >= 2) { //&& liste_position_gauche.size() != 0) {

            groupeAlgoMulti(lis_lis_position, prochain_attr_droite, k, tous_QID, liste_position_droite);


        }



        return lis_lis_position;

    }

    public HSSFWorkbook ggM(List<List<Integer>> liste_groupe_qid, List<List<String>> tous_QID, HSSFWorkbook wb, int colonne_deb_QID) {


        //Première étape : construction des groupes, on va chercher chaque élément grâce à sa position

        // lis = [[1, 2], [3, 4], [5, 6], [7, 8], [13, 14, 15], [9, 10], [11, 12]]
        List<List<List<String>>> absolument_tout = new ArrayList<>();
        for (int a = 0; a < tous_QID.size(); a++) {

            List<List<String>> liste_tout_qid = new ArrayList<>();

            for (int i = 0; i < liste_groupe_qid.size(); i++) {

                List<String> QID_select = new ArrayList<>();

                for (int j = 0; j < liste_groupe_qid.get(i).size(); j++) {

                    String ajout = tous_QID.get(a).get(liste_groupe_qid.get(i).get(j)); //Collections.min(liste_groupe_qid.get(i)) + "-" + Collections.max(liste_groupe_qid.get(i));
                    QID_select.add(ajout);
                }

                liste_tout_qid.add(QID_select);

            }

            absolument_tout.add(liste_tout_qid);
        }

       //Les groupes sont créés, il ne reste plus qu'à modifier le wb. On peut facilement obtenir le min/max de chaque groupe


        for (int a = 0; a < tous_QID.size(); a++) {

            for (int i = 0; i < liste_groupe_qid.size(); i++) {

                for (int j = 0; j < liste_groupe_qid.get(i).size(); j++) {

                   tous_QID.get(a).set(liste_groupe_qid.get(i).get(j), Collections.min(absolument_tout.get(a).get(i)) + "-" + Collections.max(absolument_tout.get(a).get(i)));
                }



            }
        }


       HSSFSheet sheet_donnees = wb.getSheet("donnees");


        for (int a = 0; a < tous_QID.size(); a++) {
            for (int b = 0; b < tous_QID.get(a).size(); b++) {
                sheet_donnees.getRow(b).getCell(a+colonne_deb_QID-1).setCellValue(tous_QID.get(a).get(b));
            }
        }


        return wb;
    }





    public HSSFWorkbook appliquerAlgoMulti (List<List<String>> liste_QID, int k, HSSFWorkbook wb, int colonne_deb_QID, String nomAttrdép) {

        List<Integer> QID_select = selectQIDAtt(liste_QID, nomAttrdép);
        List<List<Integer>> liste_groupe_qid = new ArrayList<>();

        List<Integer> liste_position = new ArrayList<>();
        for (int i =0; i < QID_select.size();i++) {
            liste_position.add(i+1);
        }
        liste_groupe_qid = groupeAlgoMulti(liste_groupe_qid,QID_select, k, liste_QID, liste_position);

        HSSFWorkbook wbAlgo = ggM(liste_groupe_qid,liste_QID, wb, colonne_deb_QID);

        return wbAlgo;
    }


}











