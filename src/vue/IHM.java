package vue;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Class de prise d'information et d'affichage auprès de l'utilisateur
 */

public class IHM extends JFrame {
    public IHM(){
        super ("Projet CdA");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);

        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane();
        contentPane.add(scroll);

        contentPane.add(selectXLS(), BorderLayout.NORTH);
        contentPane.add(selectBucket(), BorderLayout.CENTER);
        contentPane.add(selectAlgo1(),BorderLayout.AFTER_LAST_LINE);
        }

private JPanel selectXLS(){
        JPanel panelXLS = new JPanel(new FlowLayout());

        JLabel labelXSL = new JLabel("Choisissez votre fichier : .xls");
        labelXSL.setPreferredSize(new Dimension(200,30));
        panelXLS.add(labelXSL);

        JFileChooser pathXLS = (new JFileChooser());
        panelXLS.add(pathXLS);


        return panelXLS;
}

private JPanel selectBucket(){
        JPanel panelBucket = new JPanel(new FlowLayout());

        JRadioButton radioBucket = new JRadioButton("Bucketisation");
        radioBucket.setPreferredSize(new Dimension(300,30));
        panelBucket.add(radioBucket);

        panelBucket.add( selectBucketRIGHT() );

        return panelBucket;
}

private JPanel selectBucketRIGHT(){
    JPanel panelBucketRIGHT  = new JPanel(new GridLayout(2,2,10,10));
    JLabel nomQID = new JLabel("Nom du fichier contenant les QID :");
    panelBucketRIGHT.add(nomQID);

    JTextField nomFileQID = new JTextField();
    nomFileQID.setPreferredSize(new Dimension(100,30));
    panelBucketRIGHT.add( nomFileQID);

    JLabel nomDS = new JLabel("Nom du fichier contenant les Données Sensibles:");
    panelBucketRIGHT.add(nomDS);

    JTextField nomFileDS = new JTextField();
    nomFileDS.setPreferredSize(new Dimension(100,30));
    panelBucketRIGHT.add( nomFileDS);

    return panelBucketRIGHT;
}

    private JPanel selectAlgo1(){
        JPanel panelAlgo1 = new JPanel(new FlowLayout());

        JRadioButton radioAlgo1 = new JRadioButton("Algorithme 1");
        radioAlgo1.setPreferredSize(new Dimension(200,30));
        panelAlgo1.add(radioAlgo1);

        panelAlgo1.add( selectAlgo1RIGHT() );

        return panelAlgo1;
    }

    private JPanel selectAlgo1RIGHT(){
        JPanel panelAlgo1RIGHT  = new JPanel(new GridLayout(1,1,10,10));
        JLabel nom = new JLabel("Nom du fichier :");
        panelAlgo1RIGHT.add(nom);

        JTextField nomFile = new JTextField();
        nomFile.setPreferredSize(new Dimension(100,30));
        panelAlgo1RIGHT.add( nomFile);

        return panelAlgo1RIGHT;
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


