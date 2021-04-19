package traitement;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;



import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
     * Class permettant la pseudomisation
     */
    public class Pseudonymisation {
        // char 97 -> 122 alphabet minuscule
        // Si ça ne marche pas passer en static
        private int index_unite = 0;  //Création de curseurs : à 0 on est sur le a, à 27 on est sur le z
        private int index_dizaine = 0;
        private int index_centaine= 0;
        private String pseudo;
        private List<List<String>> ListePseudos = new ArrayList<List<String>>();

        /**
         * méthode qui génère des pseudos sous forme de String de 3 caractères aaa incrémenté de 1 à chaque nouveau pseudo généré
         *
         * @return pseudo
         */
        private String genererPseudo() {  //De 97 à 122 = tout l'alphabet

            List<String> Alphabet = new ArrayList<String>();
            for (char i=97; i<123;i++) {
                Alphabet.add(String.valueOf(i));
            }

            pseudo = Alphabet.get(index_centaine)+Alphabet.get(index_dizaine)+Alphabet.get(index_unite);
            index_unite++;
            if (index_unite>=26) {
                index_unite=0;
                index_dizaine++;
                if (index_dizaine>=26) {
                    index_dizaine=0;
                    index_centaine++;
                }
            }
            //Création d'une liste contenant tous les caractères que l'on va utiliser

         /*   pseudo =
            if (char1 > 122) {
                char1=97;
            }

            if (char1 < 122) {
                pseudo = (String.valueOf(char1++)) + (String.valueOf(char2)) + (String.valueOf(char3));
            } else if (char2 < 122)  {      //Premier cas = zaa : on repasse z à a et on incrémente le deuxième a à b

                pseudo = (String.valueOf(char1)) + (String.valueOf(char2++)) + (String.valueOf(char3));
            } else {
                pseudo = (String.valueOf(char1)) + (String.valueOf(char2)) + (String.valueOf(char3++));
            }

          */
            return pseudo;
        }

        /**
         * Méthode créant un tableau à 2 dimensions de même taille que les
         *
         * @param ListeIdentifiants
         * @return tab      un tableau à 2 dimensions contenant les données pseudonimisées
         */
        public void Pseudonymiser(List<List<String>> ListeIdentifiants) {

            for (int a = 0; a < ListeIdentifiants.size(); a++) {
                ArrayList<String> ListePseudos_temp = new ArrayList<>();
                ListePseudos_temp.add(ListeIdentifiants.get(a).get(0));
                for (int b = 1; b < ListeIdentifiants.get(a).size(); b++) {
                    String new_pseudo = genererPseudo();
                    ListePseudos_temp.add(new_pseudo);
                }
                ListePseudos.add(ListePseudos_temp);
            }
        }





        /**
         * getter de la liste des pseudos
         *
         * @return pseudo
         */
        public List<List<String>> getListePseudos() {
            return ListePseudos;
        }


    }



