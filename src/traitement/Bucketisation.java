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
     *
     * @param k
     * @param ListeQuasiIdentifiants
     * @param ListeDonneesSensibles
     */
    public void Bucketiser(int k, List<List<String>> ListeIdentifiants, List<List<String>> ListeQuasiIdentifiants, List<List<String>> ListeDonneesSensibles) {

        // [[Age, 10,8,40,18,19],[Groupe, G1,G1,G2,G2,G2]]  pour k = 2
        List<String> GroupeBucketQID= new ArrayList<>();
        List<String> GroupeBucketDS= new ArrayList<>();
        int nb_personnes = ListeQuasiIdentifiants.get(0).size()-1;  // 5

        GroupeBucketQID.add(ListeDonneesSensibles.get(0).get(0));
        GroupeBucketDS.add("Groupe");  // [Groupe]


        for (int i = 1; i <= nb_personnes/k; i++) {  //i de 1 à 2
            for (int j=0; j<k;j++) {
                GroupeBucketQID.add("G" + i);  //[Groupe, G1]
                GroupeBucketDS.add("G" + i);
            }
        }
        //[Groupe, G1,G1,G2,G2]
        for (int a=0 ;a<nb_personnes%k;a++) {
            GroupeBucketQID.add("G" + nb_personnes / k); //ajout du numéro du dernier groupe
            GroupeBucketDS.add("G" + nb_personnes / k);
        }

        for (int i=0;i<ListeIdentifiants.size();i++) {
            ListeQuasiIdentifiants.add(0,ListeIdentifiants.get(i));
        }

        ListeQuasiIdentifiants.add(GroupeBucketQID);  //[[Age, 10,8,40,18,19]].add [Groupe, G1,G1,G2,G2,G2]
        this.ListeQIDBucket=ListeQuasiIdentifiants;

        ListeDonneesSensibles.add(0,GroupeBucketDS);
        this.ListeDSBucket=ListeDonneesSensibles;

        CreerFichierBucketQID(ListeQIDBucket);
        CreerFichierBucketDS(ListeDSBucket);


    }

    /**
     *
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
     *
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

