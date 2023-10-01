/*

Professor: Hayala Curto Nepomuceno
Aluno: Raul Cruz
Aluno: Rafael Pereira Vilefort


*/

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

    /* 
    * A lista invertida consiste em um HashMap de uma String com uma ArrayList de inteiros.
    * Cada ocorrência de uma String possui um vetor de ids que indiciam qual registro contém essa palavra.
    */

public class ListaInvertida {

    private HashMap <String, ArrayList<Integer>> listaInvertida;
    private HashMap<String, Long> posicoes;
    private RandomAccessFile raf;

    //Construtor padrão
    public ListaInvertida(){
        listaInvertida = new HashMap<>();
    }

    //Construtor para criar um arquivo binário que contém os dados da lista invertida
    public ListaInvertida(String diretorio){
        //System.out.println(diretorio);
        listaInvertida = new HashMap<>();
        posicoes = new HashMap<>();
        try{
            raf = new RandomAccessFile(diretorio, "rw");
        }catch(FileNotFoundException e){
            System.err.println("Impossivel abrir o arquivo " + diretorio);
        }
    }

    /*
     * Método de inserir da Lista invertida que recebe um jogo, splita o nome com sepadores
     * e armazena em um vetor de String. Depois, chama a sobrecarga do método de inserir que
     * irá inserir cada palavra do vetor e o id do jogo no HashMap
     */
    public void inserir(Jogo jogo){
        String[] aux = jogo.getGame_name().split("[-:, ]+");

        for(int i = 0; i < aux.length; i++){
            inserir(aux[i], jogo.getID());
        }
    }

    private void inserir(String string, int id){
        if(listaInvertida.containsKey(string)){
            listaInvertida.get(string).add(id);
            /*---------------------------------------- */
                /*
                 *Escrevendo no arquivo binario
                 */
                 try {
                    Long pos = posicoes.get(string);
                    raf.seek(pos);
                    raf.writeInt(id);
                    posicoes.put(string, raf.getFilePointer());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            /*---------------------------------------- */
        }else{
            ArrayList<Integer> aux = new ArrayList<>();
            aux.add(id);
            listaInvertida.put(string, aux);
            /*---------------------------------------- */
            /*
             *Escrevendo no arquivo binario
             */
                try {
                    //simbolo para sinalizar que é uma palavra nova
                    raf.writeChar('*');
                    raf.writeUTF(string);
                    raf.writeInt(id);
                    posicoes.put(string, raf.getFilePointer());
                    raf.skipBytes(2000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            /*---------------------------------------- */
        }
    }

    /*O método de remover splita o nome do jogo e remove cada palavra
     * individualmente do HashMap
     */

    public void remover(Jogo jogo){
        String[] aux = jogo.getGame_name().split("[ ,-:]+");

        for(int i = 0; i < aux.length; i++){
            remover(aux[i], jogo.getID());
        }
    }

    public void remover(String string, int id){
        if(listaInvertida.containsKey(string)){
            listaInvertida.get(string).remove(id);
            /*---------------------------------------- */
            /*
             * Removendo do arquivo binario
            */
            try {
                raf.seek(2);
                while (raf.getFilePointer() < raf.length()){
                    if(raf.readUTF().equals(string)){
                        raf.seek(raf.getFilePointer() - 2);
                        raf.writeChar('_');
                        break;
                    } else {
                        raf.skipBytes(2002);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*---------------------------------------- */
            
        }
    }

    /*
     * O método de buscar procura a String e retorna a ArrayList de ids
     * correspondente a essa String que será usada no método de busca
     */

    public ArrayList<Integer> buscar(String string){
        if(listaInvertida.containsKey(string)){
            return listaInvertida.get(string);
        }else{
            return null;
        }
    }
}
