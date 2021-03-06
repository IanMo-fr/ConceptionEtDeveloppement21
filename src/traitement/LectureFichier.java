package traitement;

import java.io.*;
import java.util.Iterator;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;



/**
 * Classe responsable de la lecture des fichiers <code>.xls</code> places dans <code>../</code>.
 */
public class LectureFichier {

    // **** Attributs ****

    private List<List<String>> ListeIdentifiants;
    private List<List<String>> ListeQuasiIdentifiants;
    private List<List<String>> ListeDonneesSensibles;
    private final List<List<String>> ListeDSBucket;
    private String pathname;
    private HSSFWorkbook wb;
    private int colonne_deb_QID;



    // **** Constructeurs ****

    /**
     * Constructeur par defaut de LectureFichier
     */

   public LectureFichier() {
        this.ListeIdentifiants = null;
        this.ListeQuasiIdentifiants = null;
        this.ListeDonneesSensibles = null;
        this.ListeDSBucket = null;
        this.pathname = null;
        this.wb = null;
        this.colonne_deb_QID=0;
    }


    // **** Méthodes ****

    /**
     * Methode pour l'ouverture du fichier en type <code>HSSFWorkbook</code>. Lit les donnees contenues dans le fichier et les stocke dans des ArrayList
     * @param pathname      Le chemin d'acces du fichier a ouvrir
     * @throws IOException      Exception a l'ouverture du fichier
     */
    public void OuvrirFichier(String pathname) throws IOException {
        File fichier = new File(pathname);
        FileInputStream inputStream = new FileInputStream(fichier);  //Lecture du fichier au chemin d'accès donné
        // Get the workbook instance for XLS file
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);  //Lecture du fichier comme fichier .xls
        this.pathname = pathname;
        this.wb = wb;
        this.colonne_deb_QID=CompteurID_QID_DS("QID");
        LireIdentifiants();
        LireQID();
        LireDS();
    }


    /**
     * Permet de compter le nombre de colonnes ID/QID/DS
     * @param nature
     * @return compteur
     */
    private int CompteurID_QID_DS(String nature) {

        HSSFSheet sheet = wb.getSheet("attributs");
        Row first_row = sheet.getRow(0);
        Iterator<Cell> Iter = first_row.cellIterator(); //Itérateur qui va permettre de parcourir toutes les cellules de la ligne
        int index_colonne=0;
        while (Iter.hasNext()) {
            String cellule = Iter.next().getStringCellValue();
            if (cellule.equals(nature)) {
                break;
            }
            index_colonne+=1;
        }

        int index_ligne = 1 ; //Permet de commencer à la deuxième ligne
        Row row = sheet.getRow(index_ligne++) ;
        int compteur=0;
        while (row != null) {
            Cell cellule = row.getCell(index_colonne);
            if (cellule!=null) {
                compteur++;
            }
            row = sheet.getRow(index_ligne++) ;
        }

        return compteur;
    }

    /**
     * Permet de lire les identifiants d'un fichier excel et de les stocker dans un ArrayList
     *
     */
    public void LireIdentifiants() {

        List<List<String>> ListeID = new ArrayList<>();
        int compteur_ID = CompteurID_QID_DS("ID");  //On cherche le nombre de colonne de type ID
        HSSFSheet sheet = wb.getSheet("donnees");  //On se place dans la page "donnees"

        int numColonne = 0;    //Les colonnes sont triées, les IDs commencent donc TOUJOURS à la colonne 0
        for (int i = 0; i < compteur_ID; i++) {      //On va lire les colonnes autant de fois qu'il y a de colonnes ID
            int index = 0;
            Row row = sheet.getRow(index);
            List<String> ListeID_temp = new ArrayList<>();  //Déclaration d'une arraylist temporaire vide qui va contenir toutes les données de la première colonne ID
            while (row != null) {
                String Id_lu = " ";         //Initialisation de Id_lu, c'est la valeur de la cellule qui sera lue par la suite
                if (row.getCell(numColonne) != null) {
                    CellType type_lu = row.getCell(numColonne).getCellType();  //On vérifie le type de la cellule, qu'on va finir par convertir dans tous les cas en String
                    switch (type_lu) {
                        case NUMERIC:
                            double Id_lu_double = row.getCell(numColonne).getNumericCellValue();  //Si c'est une valeur numérique => on la met en String
                            Id_lu = Double.toString(Id_lu_double);
                            break;
                        case STRING:                                                             //Si c'est déjà un String, on l'utilise tel quel
                            Id_lu = row.getCell(numColonne).getStringCellValue();
                            break;
                    }
                }
                    ListeID_temp.add(Id_lu);      //Ajout de la cellule lue à la liste temporaire
                    row = sheet.getRow(++index);  //On passe à la ligne suivante
                }
                ListeID.add(ListeID_temp);    //Ajout de la liste temporaire à la liste contenant TOUTES les données
                numColonne += 1;                          //On recommence avec la colonne suivante
            }
        this.ListeIdentifiants=ListeID;

    }
    /**
     * Permet de lire les quasi-identifiants d'un fichier excel et de les stocker dans un ArrayList
     *
     */
    public void LireQID() {
        List<List<String>> ListeQID = new ArrayList<>();
        int compteur_QID = CompteurID_QID_DS("QID");  //On cherche le nombre de colonne de type ID
        HSSFSheet sheet = wb.getSheet("donnees");  //On se place dans la page "donnees"
        int numColonne = CompteurID_QID_DS("ID");    //On part de la première colonne des QID, repéré lors du comptage
        for (int i =0; i<compteur_QID;i++) {      //On va lire les colonnes autant de fois qu'il y a de colonnes ID
            int index = 0 ;
            Row row = sheet.getRow(index) ;
            List<String> ListeQID_temp = new ArrayList<>();  //Déclaration d'une arraylist temporaire vide qui va contenir toutes les données de la première colonne ID
            while (row != null) {
                String Qid_lu = " ";
                if (row.getCell(numColonne) != null) {
                    CellType type_lu = row.getCell(numColonne).getCellType();  //On vérifie le type de la cellule, qu'on va finir par convertir dans tous les cas en String
                    switch (type_lu) {
                        case NUMERIC:
                            double Qid_lu_double = row.getCell(numColonne).getNumericCellValue();  //Si c'est une valeur numérique => on la met en String
                            Qid_lu = Double.toString(Qid_lu_double);
                            break;
                        case STRING:                                                             //Si c'est déjà un String, on l'utilise comme telle
                            Qid_lu = row.getCell(numColonne).getStringCellValue();
                            break;
                    }
                }
                ListeQID_temp.add(Qid_lu);      //Ajout de la cellule lue à la liste temporaire
                row = sheet.getRow(++index) ;  //On passe à la ligne suivante
            }
            ListeQID.add(ListeQID_temp);    //Ajout de la liste temporaire à la liste contenant TOUTES les données
            numColonne+=1;                          //On recommence avec la colonne suivante
        }
        this.ListeQuasiIdentifiants=ListeQID;
    }

    /**
     * Permet de lire les donnees sensibles d'un fichier excel et de les stocker dans un ArrayList
     *
     */
    public void LireDS() {
        List<List<String>> ListeDS = new ArrayList<>();
        int compteur_DS = CompteurID_QID_DS("DS");  //On cherche le nombre de colonne de type ID
        HSSFSheet sheet = wb.getSheet("donnees");  //On se place dans la page "donnees"

        int numColonne = CompteurID_QID_DS("ID")+CompteurID_QID_DS("QID");    //On part de la première colonne des DS, repéré lors du comptage
        for (int i =0; i<compteur_DS;i++) {      //On va lire les colonnes autant de fois qu'il y a de colonnes ID
            int index = 0 ;
            Row row = sheet.getRow(index) ;
            List<String> ListeDS_temp = new ArrayList<>();  //Déclaration d'une arraylist temporaire vide qui va contenir toutes les données de la première colonne ID
            while (row != null) {
                String DS_lu=" ";
                if (row.getCell(numColonne) != null) {
                    CellType type_lu = row.getCell(numColonne).getCellType();  //On vérifie le type de la cellule, qu'on va finir par convertir dans tous les cas en String
                    switch (type_lu) {
                        case NUMERIC:
                            double DS_lu_double = row.getCell(numColonne).getNumericCellValue();  //Si c'est une valeur numérique => on la met en String
                            DS_lu = Double.toString(DS_lu_double);
                            break;
                        case STRING:                                                             //Si c'est déjà un String, on l'utilise comme telle
                            DS_lu = row.getCell(numColonne).getStringCellValue();
                            break;
                    }
                }
                ListeDS_temp.add(DS_lu);      //Ajout de la cellule lue à la liste temporaire
                row = sheet.getRow(++index) ;  //On passe à la ligne suivante
            }
            ListeDS.add(ListeDS_temp);    //Ajout de la liste temporaire à la liste contenant TOUTES les données
            numColonne+=1;                          //On recommence avec la colonne suivante
        }
        this.ListeDonneesSensibles=ListeDS;
    }

    
    /**
     * getter de la liste des identifiants
     * @return ListeIdentifiants
     */
    public List<List<String>> getListeIdentifiants() {
        return ListeIdentifiants;
    }

    /**
     * getter de la liste des quasi-identifiants
     * @return ListeIdentifiants
     */
    public List<List<String>> getListeQuasiIdentifiants() {
        return ListeQuasiIdentifiants;
    }

    /**
     * getter de la liste des donnees sensibles
     * @return ListeIdentifiants
     */
    public List<List<String>> getListeDonneesSensibles() {
        return ListeDonneesSensibles;
    }


    /**
     * getter du wb ouvert
     * @return wb
     */
    public HSSFWorkbook getWb() {
        return wb;
    }

    /**
     * getter de l'index du début de la colonne des QID
     * @return
     */
    public int getColonne_deb_QID() {
        return colonne_deb_QID;
    }

}



