package vue;

import javax.swing.*;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Class de prise d'information et d'affichage auprès de l'utilisateur
 */

public class IHM extends JFrame {
    public IHM(){
        super ("Projet CdA");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.add(new JLabel("Choisissez votre fichier : "));
        // contentPane.add(new JFileChooser());
    }



/*
    // **** Attributs ****

    Scanner in = new Scanner(System.in);

    // **** constructeurs ****

    // **** Méthodes ****

    /**
     * Méthode de demande du chemin d'accès du fichier à lire //TODO : tester le patern de vérification
     * @return String path      le chemin d'accès utilisable
     */

    /*
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
    }*/
}


