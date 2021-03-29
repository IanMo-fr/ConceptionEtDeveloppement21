package lecture;

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
 * Class responsable de la lecture des fichiers <code>.xls</code> placés dans <code>../</code>.
 */
public class LectureFichier {
    private List<List<String>> ListeIdentifiants = new ArrayList<List<String>>();
    private String pathname;
    private HSSFWorkbook wb;


    public void OuvrirFichier(String pathname) throws IOException {
        File fichier = new File(pathname);
        FileInputStream inputStream = new FileInputStream(fichier);  //Lecture du fichier au chemin d'accès donné
        // Get the workbook instance for XLS file
        HSSFWorkbook wb = new HSSFWorkbook(inputStream);  //Lecture du fichier comme fichier .xls
        this.pathname = pathname;
        this.wb = wb;
    }


    private int CompteurID_QID_DS(String nature) throws IOException {

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

    public void LireIdentifiants() throws IOException {
        int compteur_ID = CompteurID_QID_DS("ID");  //On cherche le nombre de colonne de type ID
        HSSFSheet sheet = wb.getSheet("donnees");  //On se place dans la page "donnees"
        String Id_lu="";         //Initialisation de Id_lu, c'est la valeur de la cellule qui sera lue par la suite
        int numColonne = 0;    //Les colonnes sont triées, les IDs commencent donc TOUJOURS à la colonne 0
        for (int i =0; i<compteur_ID;i++) {      //On va lire les colonnes autant de fois qu'il y a de colonnes ID
            int index = 1 ; //Permet de commencer à la deuxième ligne
            Row row = sheet.getRow(index) ;
            List<String> ListeID_temp = new ArrayList<>();  //Déclaration d'une arraylist temporaire vide qui va contenir toutes les données de la première colonne ID
            while (row != null) {
                CellType type_lu = row.getCell(numColonne).getCellType();  //On vérifie le type de la cellule, qu'on va finir par convertir dans tous les cas en String
                switch (type_lu) {
                    case NUMERIC:
                        double Id_lu_double = row.getCell(numColonne).getNumericCellValue();  //Si c'est une valeur numérique => on la met en String
                        Id_lu = Double.toString(Id_lu_double);
                        break;
                    case STRING:                                                             //Si c'est déjà un String, on l'utilise comme telle
                        Id_lu = row.getCell(numColonne).getStringCellValue();
                        break;
                }
                ListeID_temp.add(Id_lu);      //Ajout de la cellule lue à la liste temporaire
                row = sheet.getRow(++index) ;  //On passe à la ligne suivante
            }
            ListeIdentifiants.add(ListeID_temp);    //Ajout de la liste temporaire à la liste contenant TOUTES les données
            numColonne+=1;                          //On recommence avec la colonne suivante
        }

        }

    public List<List<String>> getListeIdentifiants() {
        return ListeIdentifiants;
    }

    public HSSFWorkbook getWb() {
        return wb;
    }
}



