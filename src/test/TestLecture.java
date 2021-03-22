package test;
import java.io.*;

public class TestLecture {

    public static void main(String[] args) {
        File ods = new File("E:\\Documents\\Etude\\L2\\Semestre 4\\Conception et dev\\projetanon\\exemple_ce.ods");
        File xls = new File("E:\\Documents\\Etude\\L2\\Semestre 4\\Conception et dev\\projetanon\\exemple_ce.xls");


        try {
            FileReader rizdheur = new FileReader(ods);
            FileReader rideur = new FileReader(xls);

            BufferedReader in = new BufferedReader(rideur);

            System.out.println(in.readLine());

        } catch (java.io.FileNotFoundException pasgrave) {
            System.out.print("oskour");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
