import lecture.LectureFichier;
import java.util.*;


import java.io.IOException;
import lecture.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import traitement.Controle;

/**
 * Main du projet
 */
public class Main {
    public static void main(String[] args) throws IOException {
        LectureFichier test = new LectureFichier();
        test.OuvrirFichier("C:\\Users\\jujuo\\Desktop\\CDA_projet\\exemple_ce.xls");
        test.LireIdentifiants();
        List<List<String>> testeur = test.getListeIdentifiants();
        Controle.Pseudonymisation letest = new Controle.Pseudonymisation();
        letest.Pseudonymiser(testeur);
    }

}
