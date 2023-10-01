/*

Professor: Hayala Curto Nepomuceno
Aluno: Raul Cruz
Aluno: Rafael Pereira Vilefort


*/

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class Jogo implements Register{
    private int app_id;
    private String game_name;
    private Date releaseDate;
    private Vector<String> platforms;

    /* ----------------------------------------- Construtores ---------------------------------------- */
    public Jogo(){
    }

    public Jogo(byte[] load) throws Exception{
        ByteArrayInputStream bin = new ByteArrayInputStream(load);
        DataInputStream data = new DataInputStream(bin);

        this.app_id = data.readInt();
        this.game_name = data.readUTF();
        this.releaseDate = new Date(data.readLong());
        this.platforms = ListaHandlerInput(data.readUTF());
    }

    public Jogo(int app_id, String nome, String data, String platforms){
        this.app_id = app_id;
        this.game_name = nome;
        DateFormat ft = new SimpleDateFormat("yyyy-dd-MM");
        try {
            releaseDate = ft.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.platforms = RetornaLista(platforms);
    }

    public Jogo(String nome, Date data, Vector<String> platforms) {
        this.game_name = nome;
        this.releaseDate = data;
        this.platforms = platforms;
    }

    public Jogo(int app_id, String game_name, Date release_date, Vector <String> platforms){
        this.app_id = app_id;
        this.game_name = game_name;
        this.releaseDate = release_date;
        this.platforms = platforms;
    }

    public String chaveSecundaria(){
        return Integer.toString(this.app_id);
    }

    public int getID(){
		return this.app_id;
	}
	public void setID(int app_id) {
		this.app_id = app_id;
	}
    public String getGame_name() {
		return this.game_name;
	}
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}
    public Date getData() {
        return this.releaseDate;
    }
    public void setData(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setData(String releaseDate){
        DateFormat ft = new SimpleDateFormat("yyyy-dd-MM");
        try {
            this.releaseDate = ft.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setPlatforms(String platforms){
        this.platforms = RetornaLista(platforms);
    }

    public Vector<String> getPlatforms() {
        return this.platforms;
    }
    public void setPlatforms(Vector<String> platforms) {
        this.platforms = platforms;
    }

    public static String ListaHandlerOutput(Vector<String> lista){
        String output = "";

        for (int i = 0; i < lista.size()-1; i++) {
            output += lista.get(i) + "/j";
        }
        if(lista.size() > 0){
            output += lista.get(lista.size()-1);
        }
        return output;
    }

    public static Vector<String> ListaHandlerInput(String input){
        String[] aux = input.split("/j");
        Vector<String> Vinput = new Vector<String>(Arrays.asList(aux));

        return Vinput;
    }

    /*--------------------------------------------- Armazena Bytes ----------------------------------------*/
    public byte[] toByteArray() throws IOException {

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream data = new DataOutputStream(bout);

  
        data.writeInt(this.app_id);
        data.writeUTF(this.game_name);
        data.writeLong(this.releaseDate.getTime());
        data.writeUTF(ListaHandlerOutput(this.platforms));


        return bout.toByteArray();
    }

    /*--------------------------------------------- Carrega Bytes ----------------------------------------*/
    public void fromByteArray(byte[] load) throws IOException {
        ByteArrayInputStream bin = new ByteArrayInputStream(load);
        DataInputStream data = new DataInputStream(bin);

        this.app_id = data.readInt();
        this.game_name = data.readUTF();
        this.releaseDate = new Date(data.readLong());
        this.platforms = ListaHandlerInput(data.readUTF());

    }

    /*----------------------------------------------------- Método Ler -------------------------------------------- */

    public Vector<String> removeEspaco(String[] texto){
        Vector<String> resp = new Vector<String>();

        for(int i = 0; i < texto.length; i++){
            if(!texto[i].equals("") && !texto[i].equals(" ")){
                resp.add(texto[i]);
            }
        }
        return resp;

    }

    public Vector<String> RetornaLista(String texto){
        String[] aux = texto.split(",");
        return removeEspaco(aux);
    }

    public void ler(String linha) {

        String atributos[] = linha.split("<");
        //Id
        this.app_id = Integer.parseInt(atributos[0]);
        //Nome
        this.game_name = atributos[1];
        //Data
        this.releaseDate = new Date();
        DateFormat ft = new SimpleDateFormat("yyyy-dd-MM");
        try {
            releaseDate = ft.parse(atributos[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Platforms
        this.platforms = RetornaLista(atributos[3]);

    }

    /*---------------------------------------------- To String -------------------------------------------- */

    public String toString(){
        return "\nID: " + this.app_id + "\nNome: " + this.game_name + "\nData: " + this.releaseDate + "\nPlataformas: " + this.platforms.toString(); 
    }

}
