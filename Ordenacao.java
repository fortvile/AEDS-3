/*

Professor: Hayala Curto Nepomuceno
Aluno: Raul Cruz
Aluno: Rafael Pereira Vilefort


*/

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Random;

public class Ordenacao {

    public static int last_id;
    public static CRUD<Jogo> Library;
    private static ArrayList<Long> indices_lapide;

    public Ordenacao() {
        //Limpa os arquivos ordenados antes de fazer uma nova ordenação
        //Serve para evitar que os arquivos ordenados fiquem com registros duplicados
        //e para limpar os arquivos da execução anterior do programa
        FileHandler.deleteSortedFiles();
        //Vetor que marca os ids de jogos que foram excluidos
        indices_lapide = new ArrayList<Long>();
        try {
            //Cria uma nova instância de Library para armazenar os jogos ordenados
            Library = new CRUD<>(Jogo.class.getConstructor(), "sortdb");
        } catch (NoSuchMethodException e) {
            System.err.println("Falha ao criar database ordenado");
        }
    }
    
    public static Jogo[] ordenar(Jogo[] ordenados) {
        int menor = 0;

        for (int a = 1; a < ordenados.length - 1; a++) {
            menor = a;
            for (int b = a + 1; b < ordenados.length; b++) {
                if (ordenados[b] != null && ordenados[menor] != null) {
                    if (ordenados[b].getID() < ordenados[menor].getID()) {
                        menor = b;
                    }
                }
            }

            Jogo temp = ordenados[menor];
            ordenados[menor] = ordenados[a];
            ordenados[a] = temp;
        }

        return ordenados;
    }
    

    public static CRUD<Jogo> intercalar() throws IOException {

        
        RandomAccessFile raf = new RandomAccessFile("data/jogo.data", "rw");

        last_id = raf.readInt(); // Lê o último id
        raf.seek(0); // Volta para o início do arquivo

        //FileHandler.deleteFiles(); // Deleta os arquivos já existentes

        System.out.println("\n-> Distribuindo ...");

        ArrayList<Jogo> jogos = new ArrayList<Jogo>();
        Jogo jogo = new Jogo();
        byte[] ba;
        int tam;

        RandomAccessFile arq1 = new RandomAccessFile("tmp/arq1.bin", "rw");
        RandomAccessFile arq2 = new RandomAccessFile("tmp/arq2.bin", "rw");

        
    

        raf.seek(4); // Posiciona o ponteiro no inicio do arquivo
        while (raf.getFilePointer() < raf.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
            /*if (raf.readByte() == 0) {
                //raf.skipBytes(1);
                jogo = null;
                jogo = new Jogo();
                tam = raf.readInt();
                ba = new byte[tam];
                raf.read(ba);
                jogo.fromByteArray(ba);
                jogos.add(jogo);
            } else {
                raf.skipBytes(raf.readInt());
            }*/
            if (raf.readByte() == 0) {
                //Se não for lápide, adiciona-se o jogo na arraylist normalmente
                //raf.skipBytes(1);
                jogo = null;
                jogo = new Jogo();
                tam = raf.readInt();
                ba = new byte[tam];
                raf.read(ba);
                jogo.fromByteArray(ba);
                jogos.add(jogo);
            } else {
                //Se for lápide, adiciona o índice no array de índices de lápide
                //Isso vai ser útil para marcar as lápides do arquivo ordenado
                    long pos = raf.getFilePointer() - 1;
                    indices_lapide.add(pos);
                jogo = null;
                jogo = new Jogo();
                tam = raf.readInt();
                ba = new byte[tam];
                raf.read(ba);
                jogo.fromByteArray(ba);
                jogos.add(jogo);
            }
        }


        ArrayList<Jogo> jogosTmp = new ArrayList<Jogo>(); // Array temporário para armazenar as jogos
        int contador = 0; // Contador para saber quantos jogos foram adicionadas no arquivo
        while (jogos.size() > 0) { // Enquanto o array jogos nao estiver vazio
            for (int j = 0; j < 5; j++) { // Adiciona 5 jogos no array temporário
                if (jogos.size() > 0) { // Se o array jogos nao estiver vazio
                    jogosTmp.add(jogos.get(0)); // Adiciona a primeira conta do array jogos no array temporário
                    jogos.remove(0); // Remove a primeira conta do array jogos
                }
            }

            jogosTmp.sort((Jogo j1, Jogo j2) -> j1.getID() - j2.getID()); // Ordena o array temporário

            contador++;

            if (contador % 2 != 0) { // Se o contador for impar adiciona no arquivo 1
                for (Jogo j : jogosTmp) {
                    arq1.writeByte(0);
                    arq1.writeInt(j.toByteArray().length);
                    arq1.write(j.toByteArray());
                }
            } else { // Se o contador for par adiciona no arquivo 2
                for (Jogo j: jogosTmp) {
                    arq2.writeByte(0);
                    arq2.writeInt(j.toByteArray().length);
                    arq2.write(j.toByteArray());
                }
            }

            jogosTmp.clear(); // Limpa o array temporário
        }

        /*
         * System.out.println("\nArquivo 1: ");
         * printFile(arq1);
         * System.out.println("\nArquivo 2: ");
         * printFile(arq2);
         */

        // ------------------------------------------------------------------- //

        ArrayList<Jogo> jogos1 = new ArrayList<Jogo>();
        ArrayList<Jogo> jogos2 = new ArrayList<Jogo>();

        System.out.println("\n-> Intercalacao 1 ...");

        arq1.seek(0); // Posiciona o ponteiro no inicio do arquivo 1

        
        while (arq1.getFilePointer() < arq1.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
            jogo = null;
            jogo = new Jogo();
            arq1.readByte();
            tam = arq1.readInt();
            ba = new byte[tam];
            arq1.read(ba);
            jogo.fromByteArray(ba); // Le o registro
            jogos1.add(jogo); // Adiciona o registro no array jogos1
        }

        arq2.seek(0); // Posiciona o ponteiro no inicio do arquivo 2
        while (arq2.getFilePointer() < arq2.length()) { // Enquanto o ponteiro nao chegar no final do arquivo
            jogo = null;
            jogo = new Jogo();
            arq2.readByte();
            tam = arq2.readInt();
            ba = new byte[tam];
            arq2.read(ba);
            jogo.fromByteArray(ba); // Le o registro
            jogos2.add(jogo); // Adiciona o registro no array jogos2
        }

        RandomAccessFile arq3 = new RandomAccessFile("tmp/arq3.bin", "rw");
        RandomAccessFile arq4 = new RandomAccessFile("tmp/arq4.bin", "rw");

        contador = 0; // Contador para saber quantas jogos foram adicionadas no arquivo
        jogosTmp.clear(); // Limpa o array temporário
        int m = 5; // Tamanho do array temporário

        while (jogos1.size() > 0 || jogos2.size() > 0) { // Enquanto o array jogos1 ou o array jogos2 nao estiverem
                                                           // vazios
            for (int i = 0; i < m; i++) {
                if (jogos1.size() > 0) { // Se o array jogos1 nao estiver vazio adiciona a primeira conta no array
                                          // temporário e remove do array jogos1
                    jogosTmp.add(jogos1.get(0));
                    jogos1.remove(0);
                }
                if (jogos2.size() > 0) { // Se o array jogos2 nao estiver vazio adiciona a primeira conta no array
                                          // temporário e remove do array jogos2
                    jogosTmp.add(jogos2.get(0));
                    jogos2.remove(0);
                }
            }

            jogosTmp.sort((Jogo j1, Jogo j2) -> j1.getID() - j2.getID()); // Ordena o array temporário

            contador++;

            if (contador % 2 != 0) { // Se o contador for impar adiciona no arquivo 3
                for (Jogo j : jogosTmp) {
                    arq3.writeByte(0);
                    arq3.writeInt(j.toByteArray().length);
                    arq3.write(j.toByteArray());
                }
            } else { // Se o contador for par adiciona no arquivo 4
                for (Jogo j : jogosTmp) {
                    arq4.writeByte(0);
                    arq4.writeInt(j.toByteArray().length);
                    arq4.write(j.toByteArray());
                }
            }

            jogosTmp.clear(); // Limpa o array temporário
        }

        /*
         * System.out.println("\nArquivo 3: ");
         * printFile(arq3);
         * System.out.println("\nArquivo 4: ");
         * printFile(arq4);
         */

        // ------------------------------------------------------------------- //

        int qdt = 2; // Numero inicial da intercalação
        while (arq2.length() > 0) { // Enquanto o arquivo 2 nao estiver vazio

            System.out.println("\n-*-Intercalacao " + qdt + " -*-"); // Imprime o numero da intercalação
            arq3.seek(0);
            while (arq3.getFilePointer() < arq3.length()) { // Enquanto o ponteiro nao chegar no final do arquivo 3 le o
                                                            // registro e adiciona no array jogos1
                jogo = null;
                jogo = new Jogo();
                arq3.readByte();
                tam = arq3.readInt();
                ba = new byte[tam];
                arq3.read(ba);
                jogo.fromByteArray(ba);
                jogos1.add(jogo);
            }

            arq4.seek(0);
            while (arq4.getFilePointer() < arq4.length()) { // Enquanto o ponteiro nao chegar no final do arquivo 4 le o
                                                            // registro e adiciona no array jogos2
                jogo = null;
                jogo = new Jogo();
                arq4.readByte();
                tam = arq4.readInt();
                ba = new byte[tam];
                arq4.read(ba);
                jogo.fromByteArray(ba);
                jogos2.add(jogo);
            }

            arq1.setLength(0);// Limpa o arquivo
            arq2.setLength(0); // Limpa o arquivo

            contador = 0;
            jogosTmp.clear();
            m *= 2; // Aumenta o tamanho do array temporário
            while (jogos1.size() > 0 || jogos2.size() > 0) { // Enquanto o array jogos1 ou o array jogos2 nao
                                                               // estiverem vazios
                for (int i = 0; i < m; i++) { // Adiciona as jogos nos arrays temporários
                    if (jogos1.size() > 0) { // Se o array jogos1 nao estiver vazio adiciona a primeira conta no array
                                              // temporário e remove do array jogos1
                        jogosTmp.add(jogos1.get(0));
                        jogos1.remove(0);
                    }
                    if (jogos2.size() > 0) { // Se o array jogos2 nao estiver vazio adiciona a primeira conta no array
                                              // temporário e remove do array jogos2
                        jogosTmp.add(jogos2.get(0));
                        jogos2.remove(0);
                    }
                }

                jogosTmp.sort((Jogo j1, Jogo j2) -> j1.getID() - j2.getID()); // Ordena o array temporário

                contador++;

                if (contador % 2 != 0) { // Se o contador for impar adiciona no arquivo 1
                    for (Jogo j : jogosTmp) {
                        arq1.writeByte(0);
                        arq1.writeInt(j.toByteArray().length);
                        arq1.write(j.toByteArray());
                    }
                } else { // Se o contador for par adiciona no arquivo 2
                    for (Jogo j : jogosTmp) {
                        arq2.writeByte(0);
                        arq2.writeInt(j.toByteArray().length);
                        arq2.write(j.toByteArray());
                    }
                }

                jogosTmp.clear(); // Limpa o array temporário
            }

            /* 
            System.out.println("\nArquivo 1: ");
            printFile(arq1);
            System.out.println("\nArquivo 2: ");
            printFile(arq2);
            */

            qdt++;
        }

        // ------------------------------------------------------------------- //
        
        RandomAccessFile arqFinal = new RandomAccessFile("tmp/arqFinal.bin", "rw");
        arqFinal.seek(0); // Limpa o arquivo
        arqFinal.writeInt(last_id); // Grava o último id

        arqFinal.seek(4); // Pula o último id
        arq1.seek(0); // Volta para o início do arquivo1
        while (arq1.getFilePointer() < arq1.length()) { // Copia o arquivo 1 para o arquivo final
            jogo = null;
            jogo = new Jogo();
            arq1.readByte(); // Lê o byte de lapide
            tam = arq1.readInt(); // Lê o tamanho do registro
            ba = new byte[tam];
            arq1.read(ba);
            jogo.fromByteArray(ba); // Lê o registro
                Library.create(jogo); // Adiciona o registro no arquivo final
            arqFinal.writeByte(0); // Escreve o byte de lapide
            arqFinal.writeInt(jogo.toByteArray().length); // Escreve o tamanho do registro
            arqFinal.write(jogo.toByteArray()); // Escreve o registro
        }

        
        for(int i = 0; i < indices_lapide.size(); i++) { // Percorre o array de indices de lapide
            arqFinal.seek(indices_lapide.get(i)); // Posiciona o ponteiro no indice de lapide
            arqFinal.writeByte(1); //   Escreve o byte de lapide
        }



        // Fecha os arquivos
        arq1.close(); 
        arq2.close();
        arq3.close();
        arq4.close();
        arqFinal.close();

        return Library;
    }

}