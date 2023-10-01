/*

Professor: Hayala Curto Nepomuceno
Aluno: Raul Cruz
Aluno: Rafael Pereira Vilefort


*/

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUI{

	private static final int SCREENSIZE = 500;
	private static final int PADDING = (int)SCREENSIZE/15;
	private static final int OFFSET = (int)SCREENSIZE/30;

	private final static Color backGroundColor = Color.darkGray;
	private final static Color foregroundColor = Color.white;

	private static final JButton[] buttons = new JButton[5];

	private static final String[] boxName = {"Harsh", "Arvore B+", "Lista-Nome", "Lista-Plataforma"};
	private static final JCheckBox[] checkBoxes = new JCheckBox[4];
	private static JPanel[] panels = new JPanel[4];
	private static JFrame frame = new JFrame();

	private static CRUD<Jogo> Library;
	
	static {
		try {
			FileHandler.deleteFiles();
			Library = new CRUD<>(Jogo.class.getConstructor(),"jogo");
			FileHandler.criarBD(Library);
		} catch (NoSuchMethodException e) {
			System.err.println("Falha ao criar CRUD na classe GUI");
		} catch (Exception e) {
            System.out.println("Erro ao criar o Banco de Dados na classe GUI");
        }
	}

	public static void main(String[] args) {
        
		String mockGame = "response";

		for(int c = 0; c < panels.length; c++) panels[c] = new JPanel();

		setTitle(panels[0]);
		setIndexPanel(panels[1]);
		setButtonsPanel(panels[2]);
		setResultPanel(panels[3], mockGame);
		setFrame(frame);

		for(int c = 0; c < panels.length; c++) frame.add(panels[c]);


	}

	public static void renderResp(String resp){
		frame.remove(panels[3]);
		panels[3] = new JPanel();
		setResultPanel(panels[3], resp);
		frame.add(panels[3]);	
		frame.validate();
		frame.repaint();
	}
	public static void renderResp(ArrayList<String> resp){

		JPanel response = new JPanel();
		for(int i = 0; i < resp.size(); i++){
			setResultPanel(response, resp.get(i));
		}
		//setResultPanel(response, resp);
		frame.remove(panels[3]);
		frame.add(response);	
		frame.validate();
		frame.repaint();
	}

	public static void setResultPanel(JPanel panel, String resp){

		JTextArea textArea = new JTextArea(5, 20);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		textArea.setText(resp);
		textArea.setEditable(false);
		textArea.setBackground(backGroundColor);
		textArea.setForeground(foregroundColor);


		panel.setBackground(backGroundColor);
		panel.setBounds(OFFSET+(SCREENSIZE/3), (OFFSET + ((SCREENSIZE/7)*2)), ((SCREENSIZE/3)*2 - PADDING), (SCREENSIZE - PADDING*2 -((SCREENSIZE/7)*2)));
		panel.add(scrollPane);
	}


	public static void setButtonsPanel(JPanel panel){

		String[] buttonNames = {"Create", "Read", "Update", "Delete", "Sort"};

		panel.setBackground(backGroundColor);
		panel.setBounds(OFFSET, (OFFSET + ((SCREENSIZE/7)*2)), (SCREENSIZE/3), (SCREENSIZE - PADDING*2 - ((SCREENSIZE/7)*2)));

		for(int c = 0; c < buttons.length; c++){
			buttons[c] = new JButton(buttonNames[c]);
			buttons[c].setFocusable(false); 
			buttons[c].setBackground(backGroundColor);
			buttons[c].setForeground(foregroundColor);
			buttons[c].setBorder(BorderFactory.createLineBorder(Color.black));
			panel.add(buttons[c]);
		}

		buttons[0].addActionListener(e -> crudInterface("Create"));
		buttons[1].addActionListener(e -> crudInterface("Read"));
		buttons[2].addActionListener(e -> crudInterface("Update"));
		buttons[3].addActionListener(e -> crudInterface("Delete"));
		buttons[4].addActionListener(e -> crudInterface("Sort"));

	}

	public static void setIndexPanel(JPanel panel){


		JLabel label = new JLabel("Ordem de prioridade: Harsh -> Arvore -> Lista -> Sen Index");
		label.setForeground(foregroundColor);
		panel.add(label);
		

		for(int c = 0; c < checkBoxes.length; c++){
			checkBoxes[c] = new JCheckBox(boxName[c]);
			checkBoxes[c].setFocusable(false);
			checkBoxes[c].setForeground(foregroundColor);
			checkBoxes[c].setBackground(backGroundColor);
			panel.add(checkBoxes[c]);
		}


		panel.setBackground(backGroundColor);
		panel.setBounds(OFFSET, (OFFSET + SCREENSIZE/7), (SCREENSIZE - PADDING), (SCREENSIZE/7));

	}

	public static void setTitle(JPanel panel){

		JLabel label = new JLabel("AEDS3 - TP1");
		label.setForeground(foregroundColor);
		//label.setFont(new Font("titleFont", Font.PLAIN, 28));

		panel.setBackground(backGroundColor);
		panel.setBounds(OFFSET, OFFSET, (SCREENSIZE - PADDING), (SCREENSIZE/12));
		
		panel.add(label);
		
	}

	public static void setFrame(JFrame frame){
		frame.setName("AEDS3-TP1");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(backGroundColor);
		frame.setResizable(false);
		frame.setSize(SCREENSIZE, (SCREENSIZE/3)*2);
		frame.setVisible(true);
		frame.setLayout(null);
	}

	public static int getSelectedCheckBox(){

		for(int c = 0; c < checkBoxes.length; c++){
			if(checkBoxes[c].isSelected()) return c;
		}

		return -1;

	}

	public static void crudInterface(String cruds){

		int boxIndex = getSelectedCheckBox();
		String selected = "Sem index";

		if(boxIndex >= 0){
			selected = boxName[boxIndex];
		}

		switch (cruds) {
			case "Create":
				create(selected);
				break;
			case "Read":
				read(selected);
				break;	
			case "Update":
				update(selected);
				break;
			case "Delete":
				delete(selected);
				break;	
			case "Sort":
				sort();
				break;	
			default:
				break;
		}
	}


	public static void create(String indexType){

		int id = 0;
		String name = JOptionPane.showInputDialog("Nome do jogo: ");
		String releaseDate = JOptionPane.showInputDialog("Data de lancamento (YYYY-MM-DD): ");
		String platforms = JOptionPane.showInputDialog("Plataformas (separadas por virugla): ");
		String gameString = "null";

		Jogo jogo = new Jogo(0, name, releaseDate, platforms);
		Library.create(jogo);

		gameString = jogo.toString();

		renderResp(gameString);
	}

	public static void read(String indexType){

		int id = -1;
		String gameString = "";
		Jogo jogo = new Jogo();
		String listValue;

		//ArrayList<String> game_String = new ArrayList<String>();
		ArrayList<Jogo> jogos = new ArrayList<Jogo>();

		if(indexType.equals("Lista-Nome") || indexType.equals("Lista-Plataforma")){

			if(indexType.equals("Lista-Nome")){
				listValue = JOptionPane.showInputDialog("Digite o nome:");
				jogos = Library.readRevertedIndex(listValue);
			}else{
				listValue = JOptionPane.showInputDialog("Digite a plataforma: (linux, windows, mac)");
				jogos = Library.readRevertedIndex_2(listValue);
			}


			for(int i = 0; i < jogos.size(); i++){
				if(jogos.get(i) != null){
					gameString += jogos.get(i).toString() + "\n";
				}
				else{
					gameString = "Nao foi possivel encontrar o jogo";
				}
				
			} 

		}else{

			try{
				id = Integer.parseInt(JOptionPane.showInputDialog("id do jogo: "));
				jogo.setID(id);
			}catch(NumberFormatException e){
				gameString = "id invalido";
				renderResp(gameString);
				return;
			}

			gameString = Library.read(id).toString();
		}

		renderResp(gameString);
	}

	public static void update(String indexType){

		int id = -1;
		String gameString = "null";
		Jogo jogo;

		try{
			id = Integer.parseInt(JOptionPane.showInputDialog("id do jogo: "));
		}catch(NumberFormatException e){
			gameString = "id invalido";
		}

		jogo = Library.read(id);

		jogo.setGame_name(JOptionPane.showInputDialog("Nome do jogo: "));
		jogo.setData(JOptionPane.showInputDialog("Data de lancamento (YYYY-MM-DD): "));
		jogo.setPlatforms(JOptionPane.showInputDialog("Plataformas (separadas por virugla): "));
		Library.update(jogo);
		gameString = jogo.toString();
		renderResp(gameString);

		}
		/*
		switch (indexType) {
			case "Harsh":
				//renderResp(gameString do jogo sem atualizar);
				//update game pelo harsh
				//gameString = game atualizado
				break;
			case "Arvore B+":
				//renderResp(gameString do jogo sem atualizar);
				//update game pela arvore
				//gameString = game atualizado				
				break;	
			case "Lista invertida":
				//renderResp(gameString do jogo sem atualizar);
				//update game pela lista
				//gameString = game atualizado				
				break;		
			case "Sem index":
				//renderResp(gameString do jogo sem atualizar);
				//update game linear
				//gameString = game atualizado				
				break;		
			default:
				break;
		}
		*/



	public static void delete(String indexType){

		int id = -1;
		String gameString = "null";
		Jogo jogo = new Jogo();

		try{
			id = Integer.parseInt(JOptionPane.showInputDialog("id do jogo: "));
			jogo = Library.read(id);
		}catch(NumberFormatException e){
			gameString = "id invalido";
			renderResp(gameString);
			return;
		}

		Library.delete(id);
		if(jogo != null){
			gameString = jogo.toString();
		} else {
			gameString = "Nao foi possivel encontrar o jogo";
		}
		
		renderResp(gameString);


		  

		/* 
		switch (indexType) {
			case "Harsh":
				//gameString = game deletado pelo harsh
				break;
			case "Arvore B+":
				//gameString = game deletado pelo harsh
				break;	
			case "Lista invertida":
				//gameString = game deletado pelo harsh
				break;		
			case "Sem index":
				//gameString = game deletado pelo harsh
				break;		
			default:
				break;
		}
		*/


	}

	public static void sort(){
		try{
			Ordenacao o = new Ordenacao();
			Library = o.intercalar();
		  }catch(Exception e){
			System.err.println("Falha ao intercalar");
		  }
	}


}
 