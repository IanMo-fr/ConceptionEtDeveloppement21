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

import static java.awt.FlowLayout.CENTER;


/**
 * Class de prise d'information et d'affichage auprès de l'utilisateur
 */

public class IHM extends JFrame implements ActionListener {

    private String pathname;
    private Controleur controleur = new Controleur("Julien", true);
    private JButton Bucket = new JButton("Bucketisation");
    private JButton Algo1 = new JButton("Algorithme 1");
    private JTextField k = new JTextField();
    private JTextField nomFileDS = new JTextField();
    private JTextField nomFileQID = new JTextField();
    private JLabel erreur = new JLabel("Les données entrées sont incorrectes");
    private JLabel message_bucket = new JLabel("La bucketisation a bien été faite.");


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
        contentPane.setLayout(new FlowLayout(CENTER, 50,50));

        contentPane.add(composantBucket());

        erreur.setVisible(false);
        contentPane.add(erreur);

        message_bucket.setVisible(false);
        contentPane.add(message_bucket);


        Bucket.setPreferredSize(new Dimension(150,30));
        contentPane.add(Bucket);
        Bucket.addActionListener( this);

        contentPane.add(composantAlgo1());
        Algo1.setPreferredSize(new Dimension(150,30));
        contentPane.add(Algo1);
        Algo1.addActionListener(this);

        }

        private JPanel composantBucket(){

            JPanel panelBucket = new JPanel();
            panelBucket.setLayout(new GridLayout(3,2,30,30));

            JLabel nomQID = new JLabel("Nom du fichier contenant les QID :");
            panelBucket.add(nomQID);

            nomFileQID.setPreferredSize(new Dimension(100,30));
            panelBucket.add(nomFileQID);

            JLabel nomDS = new JLabel("Nom du fichier contenant les Données Sensibles:");
            panelBucket.add(nomDS);


            panelBucket.add(nomFileDS);

            JLabel selectk = new JLabel("k :");
            panelBucket.add(selectk);


            panelBucket.add(k);


            return panelBucket;
        }

        private JPanel composantAlgo1(){

            JPanel panelAlgo1 = new JPanel();
            panelAlgo1.setLayout(new GridLayout(2,2,30,30));

            JLabel nom = new JLabel("Nom du fichier :");
            panelAlgo1.add(nom);

            JTextField nomFile = new JTextField();
            nomFile.setPreferredSize(new Dimension(100,30));
            panelAlgo1.add( nomFile);

            JLabel labelAttributQID = new JLabel("Attribut QID :");
            panelAlgo1.add(labelAttributQID);

            JTextField AttributQID = new JTextField();
            AttributQID.setPreferredSize(new Dimension(100,30));
            panelAlgo1.add( AttributQID);

            return panelAlgo1;
        }

    public String getPathname() {
        return pathname;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource()==Bucket){
            try {
                String nomQID = nomFileQID.getText();
                String nomDS = nomFileDS.getText();
                String val_k = k.getText();
                if ( nomQID.equals("") || nomDS.equals("") || val_k.equals("") || nomQID.equals(nomDS) ) {
                    erreur.setVisible(true);
                } else {
                    erreur.setVisible(false);
                    message_bucket.setVisible(true);
                    int int_k = Integer.parseInt(val_k);
                    controleur.CreerDocBucketiséAPartirdeBDD(pathname, controleur.getArrivee(), int_k, nomQID, nomDS);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (event.getSource()==Algo1){
            System.out.println("c algo 1");
        }
    }
}


