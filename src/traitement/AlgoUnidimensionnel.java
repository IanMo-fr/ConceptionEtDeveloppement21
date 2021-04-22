package traitement;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AlgoUnidimensionnel implements ArbreGeneralisation {
    /**
     * @return le Workbook après passage par l'algorithme
     */
    @Override
    public HSSFWorkbook anonyme(List<String> attributStrg) {
        List<Integer> attribut = new LinkedList<Integer>();
        attribut.add(Integer.parseInt(attributStrg.get(1)));


        this.mediane(attribut);

        return null;
    }

}
