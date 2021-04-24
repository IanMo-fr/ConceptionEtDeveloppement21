package vue;
import traitement.*;

import controleur.Controleur;

import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


/**
 * Class de prise d'information et d'affichage auprès de l'utilisateur
 */

public class IHM extends JFrame {

    private String pathname;
    private String test;


    public IHM(){
        super ("Projet CdA");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(900, 500);
        this.setLocationRelativeTo(null);

        JFileChooser pathXLS = (new JFileChooser(FileSystemView.getFileSystemView()));

        pathXLS.setDialogTitle("Selectionner votre fichier .xls");
        pathXLS.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("fichiers XLS", "xls");
        pathXLS.addChoosableFileFilter(filter);

        int returnValue = pathXLS.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            File fichierSelectionner = pathXLS.getSelectedFile();

            String  lechemin =fichierSelectionner.getAbsolutePath();
            this.pathname=lechemin;
            System.out.println(returnValue);
        }


        JPanel contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(new FlowLayout());

        JLabel nomQID = new JLabel("Nom du fichier contenant les QID :");
        contentPane.add(nomQID);

        JTextField nomFileQID = new JTextField();
        nomFileQID.setPreferredSize(new Dimension(100,30));
        contentPane.add( nomFileQID);

        JLabel nomDS = new JLabel("Nom du fichier contenant les Données Sensibles:");
        contentPane.add(nomDS);

        JTextField nomFileDS = new JTextField();
        nomFileDS.setPreferredSize(new Dimension(100,30));
        contentPane.add( nomFileDS);

        JLabel selectk = new JLabel("k :");
        contentPane.add(selectk);

        JTextField k = new JTextField();
        k.setPreferredSize(new Dimension(100,30));
        contentPane.add( k);

        JButton Bucket = new JButton("Bucketisation");
        Bucket.setPreferredSize(new Dimension(300,30));
        contentPane.add(Bucket);
        Bucket.addActionListener(e -> {
            String test = nomFileQID.getText();
            System.out.println(test);
            setTest(test);
            System.out.println(pathname);
            Controleur cont = new Controleur();
            try {
                cont.CreerDocPseudonymisé("C:/Users/jujuo/Desktop/CDA_proj/exemple_ce.xls");//pathname);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        });



        // Algo1

        JButton Algo1 = new JButton("Algorithme 1");
        Algo1.setPreferredSize(new Dimension(200,30));
        contentPane.add(Algo1);

        JLabel nom = new JLabel("Nom du fichier :");
        contentPane.add(nom);

        JTextField nomFile = new JTextField();
        nomFile.setPreferredSize(new Dimension(100,30));
        contentPane.add( nomFile);



        }

        //permet de sélectionner le pathname du fichier .xls selectionné => on peut le récupérer en retour de la fonction. A voir où l'utiliser -> paramètre de HIM ? Attribut ?
/*private JPanel selectXLS(){

        JPanel panelXLS = new JPanel((new FlowLayout()));

        JFileChooser pathXLS = (new JFileChooser(FileSystemView.getFileSystemView()));

        pathXLS.setDialogTitle("Selectionner votre fichier .xls");
        pathXLS.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("fichiers XLS", "xls");
        pathXLS.addChoosableFileFilter(filter);

        int returnValue = pathXLS.showOpenDialog(panelXLS);
        if (returnValue == JFileChooser.APPROVE_OPTION){
            File fichierSelectionner = pathXLS.getSelectedFile();

            String  lechemin =fichierSelectionner.getAbsolutePath();
            this.pathname=lechemin;
            System.out.println(returnValue);
        }

        panelXLS.add(pathXLS);
        return panelXLS;

}

 */

private JPanel panelBucket(){
    JPanel panelBucket = new JPanel(new FlowLayout());
    JLabel nomQID = new JLabel("Nom du fichier contenant les QID :");
    panelBucket.add(nomQID);

    JTextField nomFileQID = new JTextField();
    nomFileQID.setPreferredSize(new Dimension(100,30));
    panelBucket.add( nomFileQID);

    JLabel nomDS = new JLabel("Nom du fichier contenant les Données Sensibles:");
    panelBucket.add(nomDS);

    JTextField nomFileDS = new JTextField();
    nomFileDS.setPreferredSize(new Dimension(100,30));
    panelBucket.add( nomFileDS);

    JLabel selectk = new JLabel("k :");
    panelBucket.add(selectk);

    JTextField k = new JTextField();
    k.setPreferredSize(new Dimension(100,30));
    panelBucket.add( k);

    JButton Bucket = new JButton("Bucketisation");
    Bucket.setPreferredSize(new Dimension(300,30));
    panelBucket.add(Bucket);
    Bucket.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String test = nomFileQID.getText();
            setTest(test);
        }
    });

    return panelBucket;
}

/*private JPanel selectBucket(){
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
    }*/
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

    public String getPathname() {
        return pathname;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}


