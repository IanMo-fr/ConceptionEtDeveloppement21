package traitement;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.List;

public class Bucketisation {

    private List<List<String>> ListeQIDBucket;
    private List<List<String>> ListeDSBucket;
    private HSSFWorkbook wbQID;
    private HSSFWorkbook wbDS;

    public void Bucketiser(int k,List<List<String>> ListeQuasiIdentifiants, List<List<String>> ListeDonneesSensibles) {
        List<String> GroupeBucket= new ArrayList<>();
        int nb_personnes = ListeQuasiIdentifiants.get(0).size()-1;
        GroupeBucket.add("Groupe");
       // if (test==0) {      //cas où ça tombe juste = 6 personnes et k=2 : 2 groupes de 3
        for (int i = 1; i <= nb_personnes/k; i++) {
            for (int j=0; j<k;j++) {
                GroupeBucket.add("G" + i);
            }
        }
        for (int a=0 ;a<nb_personnes%k;a++) {
            GroupeBucket.add("G" + nb_personnes / k);
        }

        ListeQuasiIdentifiants.add(GroupeBucket);
        this.ListeQIDBucket=ListeQuasiIdentifiants;

        ListeDonneesSensibles.add(GroupeBucket);
        this.ListeDSBucket=ListeDonneesSensibles;

        CreerFichierBucketQID(ListeQIDBucket);
        CreerFichierBucketDS(ListeDSBucket);


    }

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
        this.wbQID=wb;
    }

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

    public HSSFWorkbook getWbQID() {
        return wbQID;
    }

    public HSSFWorkbook getWbDS() {
        return wbDS;
    }
}

