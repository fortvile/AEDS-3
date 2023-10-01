/*Classe Main para a realizacao de testes*/

import java.util.ArrayList;

public class Main {
  
  public static void main(String[] args) throws NoSuchMethodException{


    FileHandler.deleteFiles();

    //Criando o CRUD do tipo 'Jogo'
    CRUD<Jogo> Library = new CRUD<>(Jogo.class.getConstructor(),"jogo");

    try{
    FileHandler.criarBD(Library);
    }catch(Exception e){
      System.err.println("Falha ao criar o BD");
    }

    /*Testando o "Create"*/
    //Criando o objeto "teste1 e armazenando ele no CRUD
    Jogo teste1 = new Jogo(1200, "Vilefort", "2000-11-0", "aaaa, bbbbbbbbbbbbb");
    Library.create(teste1);
    System.out.println(teste1.toString());

    
    
    //Criando o objeto "teste2" e armazenando ele no CRUD
    Jogo teste2 = new Jogo(1400, "Pereira","2002-04-22", "bbbb, bbbb");
    Library.create(teste2);
    System.out.println(teste2.toString());
    
    //Testando os "Read"

    //Caso 1: Lendo o objeto de ID '0' do CRUD e 
    //atribuindo ao objeto 'm' do tipo jogo
    Jogo teste3 = Library.read(0);
    System.out.println(teste3.toString());


    
    //Caso 2:Lendo o objeto de String 'teste4' do CRUD e 
    //atribuindo ao objeto 'n' do tipo livro
    Jogo teste4 = Library.read("1");
    System.out.println(teste4.toString());

    
    
    //Testando o Update

    //Caso 1: Fazendo atualizacao do objeto 'teste3' no registro
    //Obs: Nesse caso, o novo registro e maior do que o antigo
    teste3.setGame_name("Testando1");
    Library.update(teste3);
    System.out.println(teste3.toString());

    

    //Caso 2: Fazendo atualizacao do objeto 'teste4' no registro
    //Obs: Nesse caso, o novo registro e menor do que o antigo
    teste4.setGame_name("Test2");
    Library.update(teste4);
    System.out.println(teste4.toString());

    
    //Testando o delete

    //Caso 1: Deletando a partir da ID
    Library.delete(1);

    //Tentando ler o objeto do ID Deletando
    Jogo del = Library.read(1);

    //Confirmando que este foi deletado
    if(del == null) {

      System.out.println("\nDeu certo");
    }
    else {

      System.out.println("\nPois e parceiro, hora de meter 2^10 prints");
    }
    

    //Caso 2: Deletando a partir da String
    Library.delete(teste3.chaveSecundaria());

    //Tentando ler o objeto do ID Deletando
    del = Library.read(teste3.chaveSecundaria());

    //Confirmando que o mesmo foi Deletando
    if(del == null) {

      System.out.println("\nDeu certo");
    }
    else {

      System.out.println("\nPois e parceiro, hora de meter 2^10 prints");
    }

    //Mensagem para ser imprimida caso tudo dê certo
    System.out.println("\nSe voce chegou aaaaaaaqui, entao parabens :D !");

    //Testando Lista Invertida Tipo 1, que armazena os nomes dos jogos
    ArrayList<Jogo> jogo = Library.readRevertedIndex("Death");
    if(jogo != null){
      for(int i = 0; i < jogo.size(); i++){
        if(jogo.get(i) != null){
          System.out.println(jogo.get(i).toString());
        }
        else{
          System.out.println("Nao foi possivel encontrar o jogo");
        }
      }
    } else { 
      System.out.println("Nao ha correspondencia de indices e strings");
    }
    

    //Testando ordenação
    try{
      Ordenacao o = new Ordenacao();
      Library = o.intercalar();
    }catch(Exception e){
      System.err.println("Falha ao intercalar");
    }

    //Testando leitura após ordenação

    Jogo teste7 = Library.read(0);
    System.out.println(teste7.toString());

    Jogo teste8 = Library.read(26193);
    System.out.println(teste8.toString());

    //Testando exclusão após ordenação
    Library.delete(26193);

    Jogo teste9 = Library.read(8);
    System.out.println(teste9.toString());

    //Testando Lista Invertida Tipo 2, que armazena as plataformas dos jogos
    ArrayList<Jogo> jogo2 = Library.readRevertedIndex_2("mac");
      if(jogo != null){
        for(int i = 0; i < jogo.size(); i++){
          if(jogo.get(i) != null){
            System.out.println(jogo.get(i).toString());
          }
          else{
            System.out.println("Nao foi possivel encontrar o jogo");
          }
        }
      } else { 
        System.out.println("Nao ha correspondencia de indices e strings");
      }
  }

  
}
