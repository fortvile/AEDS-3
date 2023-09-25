/*Classe 'Book' usada para testes com o CRUD*/

import java.io.*;

public class Book implements Register {

  private int id;
  private String title;
  private String author;
  private float price;

  public String getTitle() {

    return title;
  }

  public String chaveSecundaria() {

    return title;
  }

  public String getAuthor() {

    return author;
  }

  public float getPrice() {

    return price;
  }

  public int getID() {

    return id;
  }

  public void setID(int id) {

    this.id = id;
  }

  public void setTitle(String title) {

    this.title = title;
  }

  public void setAuthor(String author) {

    this.author = author;
  }

  public void setPrice(float price) {

    this.price = price;
  }

  public String print() {
    
    String resp = "Title: " + title + "\n" +
                  "Author: " + author + "\n" +
                  "Price: $" + price + "\n" ;

    return resp;
  }
  public Book(String title, String author,float price) {

    setTitle(title);
    setAuthor(author);
    setPrice(price);
  }

  public Book() {

    title = "null";
    author = "unknown";
    price = 0;

  }

  public byte[] toByteArray() throws IOException{

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);

    dos.writeInt(id);
    dos.writeUTF(title);
    dos.writeUTF(author);
    dos.writeFloat(price);

    return baos.toByteArray();
  }
  public void fromByteArray(byte[] ba) throws IOException{
    
    ByteArrayInputStream bais = new ByteArrayInputStream(ba);
    DataInputStream dis = new DataInputStream(bais);

    setID(dis.readInt());
    setTitle(dis.readUTF());
    setAuthor(dis.readUTF());
    setPrice(dis.readFloat());
  }
}