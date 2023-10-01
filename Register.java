/*

Professor: Hayala Curto Nepomuceno
Aluno: Raul Cruz
Aluno: Rafael Pereira Vilefort


*/

import java.io.IOException;  

/*
Interface usada pelo registro
*/
public interface Register{

  public int getID();
  public void setID(int id);
  public String chaveSecundaria();  //Criado para poder fazer o read(String secKey)

  public byte[] toByteArray() throws IOException;;
  public void fromByteArray(byte[] ba) throws IOException;
}