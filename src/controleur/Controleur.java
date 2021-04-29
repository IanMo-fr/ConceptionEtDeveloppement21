package controleur;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import traitement.*;


import traitement.LectureFichier;
import traitement.EnregistrementFichier;
import vue.IHM;


import java.io.IOException;
import java.util.List;


/**
 * Fait le lien entre l'interface Homme-machine du package <code>vue</code> et les class de type "modele" du package <code>traitement</code>
 */
public class Controleur {

    // **** Attributs ****

    private String pathname;
    private String arrivee;
    private String user;


    // **** constructeurs ****
    /**
     * Constructeur par defaut
     */
    public Controleur(){
        this.arrivee = null;
        this.pathname = null;
        this.user=null;
    }

    /**
     * Constructeur Controle : permet d'avoir un chemin d'arrivee par defaut suivant l'utilisateur
     * @param user      Chemin d'acces
     */
    public Controleur(String user) {
        this.user=user;
        //todo : mettre nos chemins d'accès pour les tests dans un "case"
        switch (user) {
            case "Julien":
                this.arrivee ="C:/Users/jujuo/Desktop/CDA_projet/";
                break;
            case "Marilou":
                this.arrivee ="F:/Semestre 4/";
                break;
            case "Ian" :
                this.arrivee ="E:/Documents/Etude/L2/Semestre 4/Conception et dev/projetanon/";
                break;

        }
    }


    /**
     * Permet de realiser bout a bout la creation et l'enregistrement d'un fichier Excel Pseudonymise a partir d'un fichier Excel donne (par son chemin d'acces)
     * @param pathname          Chemin d'acces pour la lecture du fichier
     * @throws IOException      Si le fichier n'existe pas
     */
    public void CreerDocPseudonymise(String pathname) throws IOException {
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        HSSFWorkbook wb_depart = Ouverture.getWb();
        List<List<String>> ListeId = Ouverture.getListeIdentifiants();
        Pseudonymisation pseudonymisation = new Pseudonymisation();
        pseudonymisation.Pseudonymiser(ListeId, wb_depart);
        EnregistrementFichier Enregistrement = new EnregistrementFichier();
        Enregistrement.EnregistrerFichier(pseudonymisation.getWorkbook(), arrivee +"pseudos.xls");

    }

    /**
     * Methode du Controleur qui permet de realiser de bout en bout la bucketisation d'une base de donnees pseudonymisee.
     * L'utilisateur a indique le chemin d'acces de la bdd pseudonymisee, le nombre k d'elements par groupe, le nom du fichier de sortie des QID et celui des DS
     * @param pathname
     * @param k
     * @param nomQID
     * @param nomDS
     * @throws IOException
     */
    public void CreerDocsBucketises(String pathname, int k, String nomQID, String nomDS) throws IOException {
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        List<List<String>> ListeQID = Ouverture.getListeQuasiIdentifiants();
        List<List<String>> ListeDS = Ouverture.getListeDonneesSensibles();
        List<List<String>> ListeIdentifiants = Ouverture.getListeIdentifiants();
        Bucketisation bucket = new Bucketisation();
        bucket.Bucketiser(k, ListeIdentifiants, ListeQID, ListeDS);
        EnregistrementFichier Enregistrement = new EnregistrementFichier();
        HSSFWorkbook wbQID = bucket.getWbQID();
        String arrivee_QID = this.arrivee +nomQID+".xls";
        Enregistrement.EnregistrerFichier(wbQID, arrivee_QID);
        HSSFWorkbook wbDS = bucket.getWbDS();
        String arrivee_DS = this.arrivee +nomDS+".xls";
        Enregistrement.EnregistrerFichier(wbDS, arrivee_DS);

    }

    /**
     * Permet de realiser bout a bout la creation et l'enregistrement d'un fichier Excel Pseudonymise a partir d'un fichier Excel donne (par son chemin d'acces)
     * @param pathname
     * @throws IOException
     */
    public void CreerDocBucketiseAPartirdeBDD(String pathname, int k, String nomQID, String nomDS) throws IOException {
        CreerDocPseudonymise(pathname);
        CreerDocsBucketises(this.arrivee +"pseudos.xls", k, nomQID, nomDS);

    }


    /**
     * Methode du Controleur qui permet de realiser de A à Z le test de diversite d'une BDD bucketisee dont l'utilisateur a indique le k et le l
     * @param pathname
     * @param k
     * @param l
     * @throws IOException
     */
    public void VerifierDiversite(String pathname, int k, int l) throws IOException {
        LectureFichier Ouverture = new LectureFichier();
        Ouverture.OuvrirFichier(pathname);
        List<List<String>> ListeDS = Ouverture.getListeDonneesSensibles();

        Diversite diversite = new Diversite();
        diversite.Verification(ListeDS,k,l);
        boolean resultat = diversite.isEst_diverse();
        if (resultat == true) {
            System.out.println("Cette base de données " +k +"-anonymisée est bien "+l+"-diverse.");
        }
        else {
            System.out.println("Cette base de données " +k +"-anonymisée n'est pas "+l+"-diverse.");
        }



    }

    /**
     * Methode du Controleur qui va permettre d'appeler l'interface graphique
     */
       public void controleurIHM() {
        IHM fenetre = new IHM();
        fenetre.setUser(this.user);
        fenetre.setVisible(true);

    }

    /**
     * Enregistre un document anonymisé passe par l'algorithme 1
     * @param pathname
     * @param nom_sortie
     * @param attribut
     * @param k
     * @throws IOException
     */
    public void CreerDocAlgo1(String pathname, String nom_sortie, String attribut, int k ) throws IOException {
        CreerDocPseudonymise(pathname);
        algo1(this.arrivee+"pseudos.xls", nom_sortie, attribut, k);

    }

    /**
     * Transmet les information et fichiers necessaire a la realisation de l'algorithme 1
     * @param pathname
     * @param nom_sortie
     * @param nom_attr
     * @param k
     * @throws IOException
     */
    public void algo1 (String pathname, String nom_sortie, String nom_attr, int k) throws IOException {
           LectureFichier Ouverture = new LectureFichier();
           Ouverture.OuvrirFichier(pathname);
           List<List<String>> listeQID = Ouverture.getListeQuasiIdentifiants();
           HSSFWorkbook wb = Ouverture.getWb();
           int colonne_deb_QID = Ouverture.getColonne_deb_QID();
           AlgoUni algo = new AlgoUni();
           HSSFWorkbook wbAlgo = algo.appliquerAlgoUni(listeQID, nom_attr, k, wb, colonne_deb_QID);
           EnregistrementFichier enregistrement = new EnregistrementFichier();
           enregistrement.EnregistrerFichier(wbAlgo, this.arrivee+nom_sortie+".xls");



    }

}
