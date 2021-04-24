package controleur;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import traitement.*;


import traitement.LectureFichier;
import traitement.EnregistrementFichier;
import vue.IHM;


import java.io.IOException;
import java.util.List;


/**
 * Fait le lien entre l'interface Homme-machine du package <code>vue</code> et les class de type "modèle" du package <code>traitement</code>
 */
public class Controleur {

    // **** Attributs ****

    private String pathname;
    private String arrivee;

    public String getArrivee() {
        return arrivee;
    }

    // **** constructeurs ****
    /**
     * Constructeur par défaut
     */
    public Controleur(){
        this.arrivee = null;
        this.pathname = null;
    }

    /**
     * Constructeur de Controle
     * @param arrivee
     */
    public Controleur(String arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * Constructeur surchargé de Controle : permet d'avoir un chemin d'arrivée par défaut si non spécifié
     */
    public Controleur(String user, boolean test) {
        //todo : mettre nos chemins d'accès pour les tests dans un "case"
        switch (user) {
            case "Julien":
                this.arrivee = "C:/Users/jujuo/Desktop/CDA_proj/pseudos.xls";
                break;
            case "Marilou":
                this.arrivee="F:/Semestre 4/pseudos.xls";
                break;
        }
    }


    /**
     * Permet de réaliser bout à bout la création et l'enregistrement d'un fichier Excel Pseudonymisé à partir d'un fichier Excel donné (par son chemin d'accès)
     * @param pathname
     * @throws IOException
     */
    public void CreerDocPseudonymisé(String pathname) throws IOException {
        this.pathname = pathname;
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        HSSFWorkbook wb_depart = Ouverture.getWb();
        List<List<String>> ListeId = Ouverture.getListeIdentifiants();
        Pseudonymisation pseudonymisation = new Pseudonymisation();
        pseudonymisation.Pseudonymiser(ListeId, wb_depart);
        EnregistrementFichier Enregistrement = new EnregistrementFichier();
        Enregistrement.EnregistrerFichier(pseudonymisation.getWorkbook(),arrivee);

    }

    public void CreerDocsBucketisés(String pathname, int k, String nomQID, String nomDS) throws IOException {
        this.pathname = pathname;
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        List<List<String>> ListeQID = Ouverture.getListeQuasiIdentifiants();
        List<List<String>> ListeDS = Ouverture.getListeDonneesSensibles();
        List<List<String>> ListeIdentifiants = Ouverture.getListeIdentifiants();
        Bucketisation bucket = new Bucketisation();
        bucket.Bucketiser(k, ListeIdentifiants, ListeQID, ListeDS);
        EnregistrementFichier Enregistrement = new EnregistrementFichier();
        HSSFWorkbook wbQID = bucket.getWbQID();
        String arrivee_QID = "C:/Users/jujuo/Desktop/CDA_proj/"+nomQID+".xls";
        Enregistrement.EnregistrerFichier(wbQID, arrivee_QID);
        HSSFWorkbook wbDS = bucket.getWbDS();
        String arrivee_DS = "C:/Users/jujuo/Desktop/CDA_proj/"+nomDS+".xls";
        Enregistrement.EnregistrerFichier(wbDS, arrivee_DS);

    }

    /**
     * Permet de réaliser bout à bout la création et l'enregistrement d'un fichier Excel Pseudonymisé à partir d'un fichier Excel donné (par son chemin d'accès)
     * @param pathname
     * @throws IOException
     */
    public void CreerDocBucketiséAPartirdeBDD(String pathname, String arrivee, int k, String nomQID, String nomDS) throws IOException {
        CreerDocPseudonymisé(pathname);
        CreerDocsBucketisés(arrivee, k, nomQID, nomDS);

    }


    public void VerifierDiversité(String pathname, int k, int l) throws IOException {
        this.pathname=pathname;
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        List<List<String>> ListeDS = Ouverture.getListeDonneesSensibles();

        Diversité diversité = new Diversité();
        diversité.Verification(ListeDS,k,l);
        boolean resultat = diversité.isEst_diverse();
        if (resultat == true) {
            System.out.println("Cette base de données " +k +"-anonymisée est bien "+l+"-diverse.");
        }
        else {
            System.out.println("Cette base de données " +k +"-anonymisée n'est pas "+l+"-diverse.");
        }



    }

       public void controleurIHM() throws IOException {
        IHM fenetre = new IHM();
        fenetre.setVisible(true);
        String chemin = fenetre.getPathname();
        System.out.println(chemin);
        this.pathname = chemin;

    }





}
