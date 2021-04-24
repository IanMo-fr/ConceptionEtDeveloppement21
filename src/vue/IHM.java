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

public class IHM extends JFrame implements ActionListener {

    private String pathname;
    private  JButton Bucket = new JButton("Bucketisation");
    private Controleur controleur = new Controleur("Marilou", true);


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

        Bucket.setPreferredSize(new Dimension(300,30));
        contentPane.add(Bucket);
        Bucket.addActionListener( this);



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


    public String getPathname() {
        return pathname;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource()==Bucket){
            try {
                controleur.CreerDocPseudonymisé(pathname);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


