package traitement;

import org.apache.poi.hssf.usermodel.*;


import java.util.*;

/**
 * Classe responsable de l'implementation des methodes de l'algortihme d'anonymistion unidimensionnel
 */
public class AlgoUni {


    /**
     * Calcul de la mediane d'une liste
     *
     * @param maliste   la list de type T : <code>Integer</code> non trie dont on calcule la médiane
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
     * Selection du QID qui nous interesse et transformation en Integer list sans le label, le nom, de la colonne
     *
     * @param listeQID              La liste de liste des QID
     * @param nomAttr               Le label, le nom de la colonne recherchee
     * @return listeAttributNum     la liste d'attribut du QID selectionne par le nom
     */
    private List<Integer> selectQIDAtt(List<List<String>> listeQID, String nomAttr) {


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


    /**
     * divise la liste d'attibut choisit en groupe de taille k
     * @param testo                 La liste d'attibut divisee, peut etre vide
     * @param listeAttribut         La liste QID a diviser
     * @param k                     Le nombre minimum d'elements par groupe
     * @return testo                La liste d'attribut divisee
     */
    public List<List<Integer>> groupeAlgoUni(List<List<Integer>> testo, List<Integer> listeAttribut, int k) {

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


        if ((Lis_gauche.size())>=k || Lis_droite.size()==0) {
            testo.add(Lis_gauche);
        }


       if (Lis_droite.size()<k) {
            testo.get(testo.size()-1).addAll(Lis_droite);
            Lis_droite.clear();
        }
       else {
           testo.add(Lis_droite);
       }


        if (Lis_gauche.size()/k >=2 && Lis_droite.size()!=0) {

            groupeAlgoUni(testo, Lis_gauche, k);
            testo.remove(0);

        }
        if (Lis_droite.size()/k >=2 && Lis_gauche.size()!=0) {

            groupeAlgoUni(testo, Lis_droite, k);
            testo.remove(0);

        }

        return testo;

    }

    /**
     * Enregistre les groupes divises de la liste QID dans un <code>HSSFWorkbook</code>
     * @param QID_select_int
     * @param liste_groupe_qid
     * @param tous_QID
     * @param nom_attr
     * @param wb
     * @param colonne_deb_QID
     * @return
     */
    public HSSFWorkbook gg(List<Integer>QID_select_int, List<List<Integer>> liste_groupe_qid, List<List<String>> tous_QID, String nom_attr, HSSFWorkbook wb, int colonne_deb_QID) {

        List<String> QID_select_string = new ArrayList<>();


        for (int i = 0; i < QID_select_int.size(); i++) {
            String val = QID_select_int.get(i).toString();
            QID_select_string.add(val);
        }

        List<Integer> copie_QID_select_int = new ArrayList<>();
        copie_QID_select_int.addAll(QID_select_int);


        List<List<List<String>>> tout = new ArrayList<>();
        List<List<String>> liste_autre_attr;
        List<String> groupe_autre_attr;
        List<Integer> liste_position = new ArrayList<>();

        for (int a=0;a<tous_QID.size();a++) {
        liste_autre_attr = new ArrayList<>();
        for (int i = 0; i < liste_groupe_qid.size(); i++) {

            groupe_autre_attr = new ArrayList<>();
            for (int j = 0; j < liste_groupe_qid.get(i).size(); j++) {

                int position = 0;
                while (liste_groupe_qid.get(i).get(j) != QID_select_int.get(position)) {
                    position++;
                }
                //System.out.println(position);
                liste_position.add(position);
                QID_select_int.set(position, -9999);
                if (tous_QID.get(a).get(0).equals(nom_attr)) {
                    tous_QID.get(a).set(position + 1, Collections.min(liste_groupe_qid.get(i)) + "-" + Collections.max(liste_groupe_qid.get(i)));
                }
                else {
                    groupe_autre_attr.add(tous_QID.get(a).get(position + 1));
                }


            }
            if (!tous_QID.get(a).get(0).equals(nom_attr)) {
                liste_autre_attr.add(groupe_autre_attr);
            }
        }
        QID_select_int.clear();
        QID_select_int.addAll(copie_QID_select_int);
        if (!tous_QID.get(a).get(0).equals(nom_attr)) {
            tout.add(liste_autre_attr);
        }
    }

     for (int x=0;x<tous_QID.size();x++) {
         int index = 1;
         if (!tous_QID.get(x).get(0).equals(nom_attr)) {
             for (int a = 0; a < tout.size(); a++) {
                 for (int b = 0; b < tout.get(a).size(); b++) {
                     for (int c = 0; c < tout.get(a).get(b).size(); c++) {
                         tous_QID.get(x).set(index, Collections.min(tout.get(a).get(b))+"-"+Collections.max(tout.get(a).get(b)));
                         index++;
                     }
                 }
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


    /**
     * Appel et applique les differentes methodes de la classe
     * @param liste_QID
     * @param nomAttr
     * @param k
     * @param wb
     * @param colonne_deb_QID
     * @return wbAlgo       le workbook anonymise
     */
    public HSSFWorkbook appliquerAlgoUni (List<List<String>> liste_QID, String nomAttr, int k, HSSFWorkbook wb, int colonne_deb_QID) {
        List<Integer> QID_selec_int = selectQIDAtt(liste_QID, nomAttr);
        List<List<Integer>> liste_groupe_qid = new ArrayList<>();
        liste_groupe_qid = groupeAlgoUni(liste_groupe_qid,QID_selec_int, k);
        HSSFWorkbook wbAlgo =gg(QID_selec_int,liste_groupe_qid,liste_QID,nomAttr, wb, colonne_deb_QID);

        return wbAlgo;
    }


}
