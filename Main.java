/*Classe Main para a realizacao de testes*/

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
    //Jogo teste1 = new Jogo(1200, "Vilefort", "2000-11-0", "aaaa");
    //Criando o objeto "meuLivro" e armazenando ele no CRUD
    Jogo teste1 = new Jogo(1200, "Vilefort", "2000-11-0", "aaaa");
    Library.create(teste1);
    System.out.println(teste1.toString());

    
    
    //Criando o objeto "meuLivro2" e armazenando ele no CRUD
    Jogo teste2 = new Jogo(1400, "Pereira","2002-04-22", "bbbb, bbbb");
    Library.create(teste2);
    System.out.println(teste2.toString());
    
    //Testando os "Read"

    //Caso 1: Lendo o objeto de ID '0' do CRUD e 
    //atribuindo ao objeto 'm' do tipo livro
    Jogo teste3 = Library.read(0);
    System.out.println(teste3.toString());


    
    //Caso 2:Lendo o objeto de String 'Teste2' do CRUD e 
    //atribuindo ao objeto 'n' do tipo livro
    Jogo teste4 = Library.read("1");
    System.out.println(teste4.toString());

    
    
    //Testando o Update

    //Caso 1: Fazendo atualizacao do objeto 'm' no registro
    //Obs: Nesse caso, o novo registro e maior do que o antigo
    teste3.setGame_name("Testando1");
    Library.update(teste3);
    System.out.println(teste3.toString());

    

    //Caso 2: Fazendo atualizacao do objeto 'n' no registro
    //Obs: Nesse caso, o novo registro e menor do que o antigo
    teste4.setGame_name("Test2");
    Library.update(teste4);
    System.out.println(teste4.toString());

    
    //Testando o delete

    //Caso 1: Deletando a partir da ID
    Library.delete(1);

    //Tentando ler o objeto do ID Deletando
    Jogo del = Library.read(1);

    //Confirmando que o mesmo foi Deletando
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
    System.out.println("\nSe voce chegou aqui, entao parabens :D !");


    try{
    Ordenacao.intercalar();
    }catch(Exception e){
      System.err.println("Falha ao intercalar");
    }
  }

  
}
