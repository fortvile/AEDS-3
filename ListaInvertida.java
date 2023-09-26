/*

Aluno: Rafael Pereira Vilefort

*/

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaInvertida {
    private HashMap <String, ArrayList<Integer>> listaInvertida;
    private RandomAccessFile raf;

    public ListaInvertida(){
        listaInvertida = new HashMap<>();
    }

    public ListaInvertida(String diretorio){
        listaInvertida = new HashMap<>();
        try{
            raf = new RandomAccessFile(diretorio, "rw");
        }catch(FileNotFoundException e){
            System.err.println("Impossivel abrir o arquivo " + diretorio);
        }
    }

    public void inserir(Jogo jogo){
        String[] aux = jogo.getGame_name().split("[-:, ]+");

        for(int i = 0; i < aux.length; i++){
            //System.out.println("aux[" + "i" + "] = " + aux[i]);
            inserir(aux[i], jogo.getID());
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

    public void remover(Jogo jogo){
        String[] aux = jogo.getGame_name().split("[ ,-:]+");

        for(int i = 0; i < aux.length; i++){
            remover(aux[i], jogo.getID());
        }
    }

    public void remover(String string, int id){
        if(listaInvertida.containsKey(string)){
            listaInvertida.get(string).remove(id);
        }
    }

    public ArrayList<Integer> buscar(String string){
        if(listaInvertida.containsKey(string)){
            return listaInvertida.get(string);
        }else{
            return null;
        }
    }
}
