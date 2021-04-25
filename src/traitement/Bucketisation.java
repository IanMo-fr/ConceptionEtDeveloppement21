package traitement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class Bucketisation {

    // **** Attributs ****

    private List<List<String>> ListeQIDBucket;
    private List<List<String>> ListeDSBucket;
    private HSSFWorkbook wbQID;
    private HSSFWorkbook wbDS;

    // **** constructeurs ****

    /**
     * Constructeur par défaut
     */
    public Bucketisation() {
        this.ListeDSBucket = null;
        this.ListeQIDBucket = null;
        this.wbDS = null;
        this.wbQID = null;
    }

    // **** Méthodes ****

    /**
     * Cette fonction permet de k-bucketisée une BDD. Elle utilise pour cela toutes les informations de la BDD récupérée dans des tableaux de tableaux.
     * k est lui choisi par l'utilisateur
     * @param k
     * @param ListePseudos
     * @param ListeQuasiIdentifiants
     * @param ListeDonneesSensibles
     */
    public void Bucketiser(int k, List<List<String>> ListePseudos, List<List<String>> ListeQuasiIdentifiants, List<List<String>> ListeDonneesSensibles) {

        // Exemple de données sensibles à bucketiser pour k=2 : [[Maladie, 10,8,40,18,19]]
        // Exemple de quasi-identifiants à bucketiser pour k=2 : [[Age, 10,8,40,18,19],[Code Postal, 21000, 23000, 18000, 17000, 45000]]

        List<String> GroupeBucketQID= new ArrayList<>();  //Listes vides qui contiendront à terme les QID et DS bucketisés
        List<String> GroupeBucketDS= new ArrayList<>();
        int nb_personnes = ListeQuasiIdentifiants.get(0).size()-1;  // On cherche le nombre de personnes dont les informations sont stockées (une donnée non remplie ne fausse pas le résultat car il y a un espace vide dans la liste)
                                                                    //taille liste - 1 car premier élément = nom de la catégorie

        GroupeBucketQID.add(ListeDonneesSensibles.get(0).get(0));   //Il n'y a toujours qu'une données sensibles, on récupère donc son libellé
        GroupeBucketDS.add("Groupe");                               //On ajoute le label "Groupe" qui apparaitra dans le fichier des DS


        for (int i = 1; i <= nb_personnes/k; i++) {  //On commence à 1 pour ignorer le nom de la catégorie
                                                    //nb_personnes/k correspond au nombre maximal de groupe contenant au moins k éléments que l'on peut créer (attention il peut y avoir un reste)
            for (int j=0; j<k;j++) {
                GroupeBucketQID.add("G" + i);       //Donc, pour chacun des groupes, on vient ajouter aux deux listes le numéro de groupe qui sera associé à chaque élément : G1,G1,G2,G2, etc
                GroupeBucketDS.add("G" + i);
            }
        }

        //A ce stade, il ne reste que x éléments à numéroter, x étant le reste de la division euclidienne nb_personnes/k (exemple : 7 personnes, k=2, le reste=1, il reste donc une personne à numéroter)

        for (int a=0 ;a<nb_personnes%k;a++) {       //Pour autant d'éléments qu'il reste potentiellement -> on les ajoute au dernier groupe
            GroupeBucketQID.add("G" + nb_personnes / k);
            GroupeBucketDS.add("G" + nb_personnes / k);
        }

        for (int i=0;i<ListePseudos.size();i++) {                  //On ajoute les pseudos au début de la liste des QID pour pouvoir construire le fichier xls par la suite
            ListeQuasiIdentifiants.add(0,ListePseudos.get(i));
        }

        ListeQuasiIdentifiants.add(GroupeBucketQID);            //On ajoute la numérotation de chacun des éléments à la liste des QID, le fichier xls est prêt à être construit
        this.ListeQIDBucket=ListeQuasiIdentifiants;             //Stockage des données

        ListeDonneesSensibles.add(0,GroupeBucketDS);  //Idem mais ajout au début de la liste pour une présentation un peu différente sur le deuxième fichier
        this.ListeDSBucket=ListeDonneesSensibles;          //Stockage des données

        CreerFichierBucketQID(ListeQIDBucket);             //Maintenant que la bucketisation est faite, il ne reste plus qu'à demander la création des Workbook avec les données traitées, puis d'envoyer à l'enregistrement
        CreerFichierBucketDS(ListeDSBucket);

        // Au final, les tableaux de données ressembleront à : (pour k=2)
        // DS: [[Groupe, G1,G1,G2,G2,G2],[[Maladie, 10,8,40,18,19]]]
        // QID: [[Nom, pseudo1, pseudo2, pseudo3, pseudo4, pseudo5],[Age, 10,8,40,18,19],[Maladie, G1,G1,G2,G2,G2]]


    }

    /**
     * Fonction qui permet la création d'un HSSFWorkbook contenant les QID bucketisés
     * @param ListeBucket
     */
    private void CreerFichierBucketQID(List<List<String>> ListeBucket) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("QID");
        for (int a = 0; a < ListeBucket.size(); a++) {
            for (int b = 0; b < ListeBucket.get(a).size(); b++) {
                if (a==0) {
                    HSSFRow row = sheet.createRow(b);
                    HSSFCell cell = row.createCell(a);
                    cell.setCellValue(ListeBucket.get(a).get(b));
                }
                else {
                    HSSFRow row = sheet.getRow(b);
                    HSSFCell cell = row.createCell(a);
                    cell.setCellValue(ListeBucket.get(a).get(b));
                }
            }
        }
        this.wbQID=wb;  //Mise à jour du wb pour l'utiliser plus tard
    }

    /**
     * Fonction qui permet la création d'un HSSFWorkbook contenant les données sensibles bucketisées
     * @param ListeBucket
     */
    private void CreerFichierBucketDS(List<List<String>> ListeBucket) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("DS");
        for (int a = 0; a < ListeBucket.size(); a++) {
            for (int b = 0; b < ListeBucket.get(a).size(); b++) {
                if (a==0) {
                    HSSFRow row = sheet.createRow(b);
                    HSSFCell cell = row.createCell(a);
                    cell.setCellValue(ListeBucket.get(a).get(b));
                }
                else {
                    HSSFRow row = sheet.getRow(b);
                    HSSFCell cell = row.createCell(a);
                    cell.setCellValue(ListeBucket.get(a).get(b));
                }
            }
        }
        this.wbDS=wb;
    }

    /**
     * Getteur de <code>wbQID</code>
     * @return
     */
    public HSSFWorkbook getWbQID() {
        return wbQID;
    }

    /**
     * Getteur de <code>wbDS</code>
     * @return
     */
    public HSSFWorkbook getWbDS() {
        return wbDS;
    }
}

