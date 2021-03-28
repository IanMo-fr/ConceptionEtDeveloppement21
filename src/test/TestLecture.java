package test;
import java.io.*;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;



public class TestLecture {
/*
        String pathname = "C:/Users/jujuo/Desktop/CDA_projet/exemple_ce.xls"; //Chemin d'accès vers le fichier
        String sheet1 = "donnees"; //Nom des sheets excel
        String sheet2 = "attributs";
        // Read XSL file
        FileInputStream inputStream = new FileInputStream(new File(pathname));  //Lecture du fichier au chemin d'accès donné

        // Get the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);  //Lecture du fichier comme fichier .xls

        // Get first sheet from the workbook
        HSSFSheet sheet = workbook.getSheet(sheet1);

        //Ici, on va faire une recherche localisée dans la page (exemple : la ligne 2)

      /*  Row test = sheet.getRow(2);
        Iterator<Cell> Iter = test.cellIterator(); //Itérateur qui va permettre de parcourir toutes les cellules de la ligne

        while (Iter.hasNext()) {
            Cell selle = Iter.next();
            System.out.println(selle);
        }

      */

        //Ici, on va faire une recherche très localisée, ex : la cellule B4
/*
       Cell recherche = sheet.getRow(3).getCell(1); //Attention : 0 = ligne 1, etc et idem pour les colonnes)
       System.out.println(recherche);

 */

        //Ici, on va parcourir TOUTE la page ligne par ligne
        /*
        // Get iterator to all the rows in current sheet
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // Get iterator to all cells of current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                // Change to getCellType() if using POI 4.x
                CellType cellType = cell.getCellType();

                switch (cellType) {           //Intéressant ici, on traite plus sérieusement la valeur que contient la cellule selon son type
                    case _NONE:
                        System.out.print("");
                        System.out.print("\t");
                        break;
                    case BOOLEAN:
                        System.out.print(cell.getBooleanCellValue());
                        System.out.print("\t");
                        break;
                    case BLANK:
                        System.out.print("");
                        System.out.print("\t");
                        break;
                    case FORMULA:
                        // Formula
                        System.out.print(cell.getCellFormula());
                        System.out.print("\t");

                        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                        // Print out value evaluated by formula
                        System.out.print(evaluator.evaluate(cell).getNumberValue());
                        break;
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue());
                        System.out.print("\t");
                        break;
                    case STRING:
                        System.out.print(cell.getStringCellValue());
                        System.out.print("\t");
                        break;
                    case ERROR:
                        System.out.print("!");
                        System.out.print("\t");
                        break;
                }

            }
            System.out.println("");
        }

         */
        //On va maintenant voir l'écriture : comment créer un fichier .xls, et le remplir
        /*
        HSSFWorkbook workbook2 = new HSSFWorkbook();
        HSSFSheet newsheet = workbook2.createSheet("donnees"); //Création d'une nouvelle sheet "donnees"

        //Une fois la page créée localement, on la met dans l'outputstream et on vient rééllement créé le fichier .xls
        try {
            FileOutputStream fileOut = new FileOutputStream("C:/Users/jujuo/Desktop/newfichier.xls"); //Ici mettre chemin où l'on veut stocker le fichier
            workbook2.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        // De 97 à 122 = tout l'alphabet


        /*

        for(int i = 97;i <=122;i++)
            {
                ascii = (char) i;
                String lettre = Character.toString(ascii);
                System.out.print(lettre + " ");
            }


        String lettre = Character.toString(ascii);
        System.out.println(lettre);

        */



    }



