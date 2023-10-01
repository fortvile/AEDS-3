/*

Professor: Hayala Curto Nepomuceno
Aluno: Raul Cruz
Aluno: Rafael Pereira Vilefort


*/
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaInvertida2 {
    private HashMap <String, ArrayList<Integer>> listaInvertida;
    private RandomAccessFile raf;

    //Construtor padrão

    public ListaInvertida2(){
        listaInvertida = new HashMap<>();
    }

    //Construtor para criar um arquivo binário que contém os dados da lista invertida

    public ListaInvertida2(String diretorio){
        listaInvertida = new HashMap<>();
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
        for(int i = 0; i < jogo.getPlatforms().size(); i++){
            inserir(jogo.getPlatforms().get(i), jogo.getID());
        }
    }

    private void inserir(String string, int id){
        if(listaInvertida.containsKey(string)){
            listaInvertida.get(string).add(id);
        }else{
            ArrayList<Integer> aux = new ArrayList<>();
            aux.add(id);
            listaInvertida.put(string, aux);
        }
    }

    /*O método de remover splita o nome do jogo e remove cada palavra
     * individualmente do HashMap
    */

    public void remover(Jogo jogo){
        for(int i = 0; i < jogo.getPlatforms().size(); i++){
            remover(jogo.getPlatforms().get(i), jogo.getID());
        }
    }

    private void remover(String string, int id){
        if(listaInvertida.containsKey(string)){
            listaInvertida.get(string).remove(id);
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
