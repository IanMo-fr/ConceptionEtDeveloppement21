package traitement;

import java.util.ArrayList;
import java.util.List;

public class Diversité {

    private boolean est_diverse;

    public void Verification(List<List<String>> ListeDS, int k, int l) {

        List<String> Liste_test=new ArrayList<>();  // [[Age,12,40,15,10]]
        String val;
        int compteur_differences = 0;
        boolean est_diverse = true;
        for (int a = 1; a <= ListeDS.get(0).size()-k-1; a += k) {
            Liste_test = new ArrayList<>();
            compteur_differences = 0;
            for (int b = 0; b < k; b++) {
                val = ListeDS.get(0).get(a + b );
                if (Liste_test.contains(val) == false) {
                    compteur_differences +=1;
                }
                Liste_test.add(ListeDS.get(0).get(a + b));

            }
            if ((compteur_differences<l) &&  (a!=ListeDS.get(0).size()-k-1)) { //On veut faire le dernier groupe à part
                est_diverse=false;
                break;
            }
        }

       for (int c=ListeDS.get(0).size()-((ListeDS.get(0).size()-1)%k);c<ListeDS.get(0).size();c++) {
           val = ListeDS.get(0).get(c);
           if (Liste_test.contains(val) == false) {
               compteur_differences +=1;
           }
           Liste_test.add(ListeDS.get(0).get(c));
       }
        if (compteur_differences<l) {
            est_diverse=false;
        }

        this.est_diverse = est_diverse;
        }

    public boolean isEst_diverse() {
        return est_diverse;
    }
}






