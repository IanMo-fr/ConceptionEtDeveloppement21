package traitement;

import java.util.ArrayList;
import java.util.List;

/**
 * Class regroupant les differentes sous-classes de traitement de données
 */
public class Controle {

    /**
     * Class permettant la pseudomisation
     */
    public static class Pseudonymisation {
        // char 97 -> 122 alphabet minuscule
        // Si ça ne marche pas passer en static
        private char char1 = 97;
        private char char2 = 97;
        private char char3 = 97;

        private String pseudo;

        private List<List<String>> ListePseudos = new ArrayList<List<String>>();


        /**
         * getter de pseudo
         *
         * @return pseudo
         */
        public List<List<String>> getListePseudos() {
            return ListePseudos;
        }

        /**
         * méthode qui génère des pseudos sous forme de String de 3 caractères aaa incrémenté de 1 à chaque nouveau pseudo généré
         *
         * @return pseudo
         */
        private String genererPseudo() {
            if (char1 < 122) {
                pseudo = (String.valueOf(char1++)) + (String.valueOf(char2)) + (String.valueOf(char3));
            } else if (char2 < 122) {
                pseudo = (String.valueOf(char1)) + (String.valueOf(char2++)) + (String.valueOf(char3));
            } else {
                pseudo = (String.valueOf(char1)) + (String.valueOf(char2)) + (String.valueOf(char3++));
            }
            return pseudo;
        }

        /**
         * Méthode créant un tableau à 2 dimensions de même taille que les
         *
         * @param ListeIdentifiants
         * @return tab      un tableau à 2 dimensions contenant les données pseudonimisées
         */
        public void Pseudonymiser(List<List<String>> ListeIdentifiants) {

            for (int a = 0; a < ListeIdentifiants.size(); a++) {    //Test du tableau pour en vérifier le contenu
                ArrayList<String> ListePseudos_temp = new ArrayList<>();
                for (int b = 0; b < ListeIdentifiants.get(a).size(); b++) {
                    String new_pseudo = genererPseudo();
                    ListePseudos_temp.add(new_pseudo);
                }
                ListePseudos.add(ListePseudos_temp);
            }

            for (int a = 0; a < ListePseudos.size(); a++) {
                for (int b = 0; b < ListePseudos.get(a).size(); b++) {
                    System.out.println(ListePseudos.get(a).get(b));
                }


            }


        }
    }
}


