import lecture.LectureFichier;
import java.util.*;
import contrôleur.*;


import java.io.IOException;
import lecture.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


/**
 * Main du projet
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Controleur controleur = new Controleur(); //Préciser ici en paramètre le chemin d'accès d'arrivée du fichier, sinon il ya un chemin par défaut
        controleur.CreerDocPseudonymisé("C:/Users/jujuo/Desktop/CDA_projet/exemple_ce.xls");


    }

}
