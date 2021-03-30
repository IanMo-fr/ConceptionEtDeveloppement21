package interaction;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Class de prise d'information et d'affichage auprès de l'utilisateur
 */
public class Console {
    // **** Attributs ****

    Scanner in = new Scanner(System.in);

    // **** constructeurs ****

    // **** Méthodes ****

    /**
     * Méthode de demande du chemin d'accès du fichier à lire //TODO : tester le patern de vérification
     * @return String path      le chemin d'accès utilisable
     */
    public String demandePath() {
        boolean err = false;
        String path = null;

        //On demande un string qui correspond au patern d'un path : soit en regex .:/*? (un char quelconque; un : ; un /
        // ; une suite de caractères quelconque optionnelle ;)
        do {
            try {
                System.out.println("Veuillez donner l'adresse mémoire du fichier à lire de la forme C:/.. : \n");
                path=in.next(Pattern.compile(".:/*?"));
            } catch (Exception e) {
                System.out.println("Mauvaise saisie");
                err = true;
            }
        } while (err);

        return path;
    }
}
