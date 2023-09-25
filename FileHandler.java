import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class FileHandler {

    public static void deleteFiles() {
        File[] file = new File[9];
        file[0] = new File("data/jogo.data");
        file[1] = new File("data/jogocestos.data");
        file[2] = new File("data/jogoId.data");
        file[3] = new File("data/jogoIi.data");
        file[4] = new File("tmp/arq1.bin");
        file[5] = new File("tmp/arq2.bin");
        file[6] = new File("tmp/arq3.bin");
        file[7] = new File("tmp/arq4.bin");
        file[8] = new File("tmp/arqFinal.bin");
        for (int i = 0; i < 9; i++) {
            if (file[i].exists()) {
                file[i].delete();
            }
        }
    }

    public static ArrayList<Jogo> lerCSV() throws InterruptedException {
        String linha[] = new String[27075];
        ArrayList<Jogo> jogo = new ArrayList<Jogo>();
        Jogo aux;

        int i = 0;

        try {
            BufferedReader leitor = new BufferedReader(new FileReader("steamparsed.csv"));

            leitor.readLine();
            while (i < 27000) {
                linha[i] = leitor.readLine();
                aux = null;
                aux = new Jogo();
                aux.ler(linha[i]);
                jogo.add(aux);
                i++;
            }
            leitor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jogo;
    }

    public static void criarBD(CRUD<Jogo> crud) throws Exception {

        ArrayList<Jogo> jogo = lerCSV();

        try {
            RandomAccessFile arq = new RandomAccessFile("data/jogo.data", "rw");

            for (int j = 0; j < jogo.size(); j++) {

                crud.create(jogo.get(j));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
