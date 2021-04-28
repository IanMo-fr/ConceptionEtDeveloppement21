package traitement;

import java.util.ArrayList;
import java.util.List;

public class Diversite {

    private boolean est_diverse;

    /**
     * Constructeur par défaut de Diversite
     */
    public Diversite() {
        this.est_diverse=false;
    }

    /**
     * Fonction qui vérifie si une liste de données sensibles k-bucketisée respecte une l-diversité. l et k étant indiqués par l'utilisateur
     * @param ListeDS
     * @param k
     * @param l
     */

    public void Verification(List<List<String>> ListeDS, int k, int l) {

        int taille_listeDS= ListeDS.get(0).size(); // On récupère la taille de la liste des données sensibles
        List<String> Liste_donnees=new ArrayList<>();  //Liste dans laquelle on va ajouter les données de chaque groupe, qu'on effacera pour y mettre celles du prochain groupe
        String val;                                     //Contiendra successivement la valeur de chaque élément d'un groupe
        int compteur_differences = 0;                   //Compteur de différents éléments au sein d'un groupe de taille k
        boolean est_diverse = true;                     //Booléen représentant la diversité ou non de la BDD, considérée comme vraie jusqu'à preuve du contraire
        for (int a = 1; a <= taille_listeDS-k-1; a += k) {  //On parcourt de groupe en groupe, sans oublier que le premier élément est un label de catégorie
                                                                    //a sera l'indice du premier élément de chaque groupe, va permettre de parcourir un par un chaque groupe
            Liste_donnees = new ArrayList<>();                      //On vide la liste
            compteur_differences = 0;                               //On réinitialise le compteur
            for (int b = 0; b < k; b++) {
                val = ListeDS.get(0).get(a + b );                   //On parcourt tous les éléments du groupe de position de départ a, donc k fois
                if (Liste_donnees.contains(val) == false) {         //On vérifie si l'élément du groupe était déjà présent ou non dans les éléments du groupe parcouru
                    compteur_differences +=1;                       //Si présence d'un nouvel élément -> on incrémente le compteur
                }
                Liste_donnees.add(ListeDS.get(0).get(a + b));       //On ajoute l'élément testé à la liste contenant les éléments déjà testés d'un groupe

            }

            //Maintenant qu'un groupe a été entièrement testé, on regarde si la l-diversité est respectée ou non
            //Attention, on exclut le dernier groupe de ce test car il est fait à part comme un peu spécial

            if ((compteur_differences<l) &&  (a!=taille_listeDS-k-1)) {
                est_diverse=false;
                break;
            }
        }

        //A ce stade, on a testé tous les éléments sauf potentiellement certains du dernier groupe si nb_personne%k !=0
        //Il faut donc tester le dernier groupe
        //A préciser que la liste_données ainsi que le compteur n'ont pas été réinitialisés après le dernier groupe, on va donc compléter le groupe et finir le test de diversité

        for (int c=taille_listeDS-((taille_listeDS-1)%k);c<taille_listeDS;c++) {  //On va chercher l'indice prochain élément à traiter, s'il reste des éléments et on effectue cette boucle pour chacun des éléments du dernier groupe encore à tester
           val = ListeDS.get(0).get(c);     //On récupère l'élément concerné
           if (Liste_donnees.contains(val) == false) {  //On regarde si c'est un nouvel élément
               compteur_differences +=1;                //On incrémente le compteur en fonction
           }
           Liste_donnees.add(ListeDS.get(0).get(c));   //On l'ajoute pour continuer les tests
        }

        if (compteur_differences<l) {   //On vérifie si le dernier groupe vérifie ou non la l-diversité
            est_diverse=false;
        }

        this.est_diverse = est_diverse;   //On stocke le résultat finale obtenu
        }

    /**
     * Getter de est_diverse
      * @return est_diverse
     */

    public boolean isEst_diverse() {
        return est_diverse;
    }
}






