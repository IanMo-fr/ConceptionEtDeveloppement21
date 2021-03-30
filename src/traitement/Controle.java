package traitement;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import sauvegarde.*;
import lecture.*;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Class regroupant les differentes sous-classes de traitement de données
 */
public class Controle {


    // **** Attributs ****

    private String pathname;
    private String arrivee;


    // **** constructeurs ****

    /**
     * Constructeur de Controle
     * @param arrivee
     */
    public Controle(String arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * Constructeur surchargé de Controle : permet d'avoir un chemin d'arrivée par défaut si non spécifié
     */
    public Controle() {
        this("C:/Users/jujuo/Desktop/CDA_projet/test.xls");
    }



    // **** Méthodes ****

    /**
     * Permet de réaliser bout à bout la création et l'enregistrement d'un fichier Excel Pseudonymisé à partir d'un fichier Excel donné (par son chemin d'accès)
     * @param pathname
     * @throws IOException
     */
    public void CreerDocPseudonymisé(String pathname) throws IOException {
        this.pathname = pathname;
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        Ouverture.LireIdentifiants();
        List<List<String>> ListeId = Ouverture.getListeIdentifiants();
        Pseudonymisation GenererPseudos = new Pseudonymisation();
        GenererPseudos.Pseudonymiser(ListeId);
        List<List<String>> ListePseudos = GenererPseudos.getListePseudos();
        EnregistrementFichier Enregistrement = new EnregistrementFichier();
        HSSFWorkbook wb = Ouverture.getWb();
        Enregistrement.EnregistrerFichier(ListePseudos, wb, arrivee);

    }

    /**
     * Class permettant la pseudomisation
     */
    private static class Pseudonymisation {
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

            for (int a = 0; a < ListeIdentifiants.size(); a++) {    //Test du tableau pour en vérifier le contenu
                ArrayList<String> ListePseudos_temp = new ArrayList<>();
                for (int b = 0; b < ListeIdentifiants.get(a).size(); b++) {
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
}


