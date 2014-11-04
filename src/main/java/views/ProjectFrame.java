package views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.lucene.document.Document;

import views.JTableRessourceModel;

public class ProjectFrame extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private FlowLayout layout;
	private JPanel upPanel;
	private JPanel bottomPanel;
	private JPanel middlePanel;
	private JPanel middleLeft;
	private JPanel middleRight;
	
	//private JTextField recherch;
	
	private ChampTexte recherch;
	private JLabel labelResearch;
	private JScrollPane centerPanel;
	private JButton suiv;
	private JButton prec;
	private JFileChooser dialogue;
	private JLabel pathLabel;
	private Icon precIcon;
	private Icon suivIcon;
	static JTableRessourceModel modelJTable;
	private 	JResourcesTable table;
	
		
	public ProjectFrame(){
		
		//recherch = new JTextField(20);
		recherch = new ChampTexte(30);
		
		//version1 avec validation ENTER
		//recherch.addActionListener(new RecherchAction());
		
		//version2 avec Document et temps reeel
		//recherch.getDocument().addDocumentListener(new RechercheActionXDocument());
		
		setLabelResearch(new JLabel("Recherche"));
		labelResearch.setForeground(Color.black);
		menu = new Menu();
		//setJMenuBar(menu);
		suiv = new JButton();
		suiv.setPreferredSize(new Dimension(35,35));
		prec = new JButton();
		prec.setPreferredSize(new Dimension(35,35));
		precIcon = new ImageIcon("src\\icones\\prec.png");
		suivIcon = new ImageIcon("src\\icones\\suiv.png");
		prec.setIcon(precIcon);
		suiv.setIcon(suivIcon);
		upPanel = new JPanel();
		upPanel.setSize(640,30);
		
		bottomPanel = new JPanel();
		upPanel.add(labelResearch);
		upPanel.add(recherch);
		upPanel.add(suiv);
		upPanel.add(prec);
		bottomPanel.setBackground(Color.gray);
		bottomPanel.setSize(640,60);
		
		HashMap<String,Set<Document>> map = new HashMap<String,Set<Document>>();
		/*int i=0;
		while(i!=10){
		t.add(new String("source"));
		i++;
		}*/
		
		//HashMap<String,Set<Document>> map = new HashMap() {{ put("j1", "true");}{ put("j2", "true");}};
				
		modelJTable = new JTableRessourceModel(map);		
		table = new JResourcesTable(modelJTable);
		modelJTable.fireTableDataChanged();
		
		//make it easier to use table as a drag and drop target
		//table.setFillsViewportHeight(true);
		
		centerPanel = new JScrollPane();
		centerPanel.setSize(640,550);
		//centerPanel.setViewportView(table);
		
	/*  middlePanel = new JPanel();
		middlePanel.setSize(640,550);
		middlePanel.add(centerPanel);
	*/

		pathLabel = new JLabel();
		pathLabel.setForeground(Color.white);
		
		setLayout(new BorderLayout());
		getContentPane().add(upPanel,BorderLayout.NORTH);
		getContentPane().add(centerPanel,BorderLayout.CENTER);
		getContentPane().add(bottomPanel,BorderLayout.SOUTH);
				
		setLayout(layout);
		setSize(660,660);
		setLocation(300,50);
	    setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	
	public void setMenu(Menu menu) {
		this.menu = menu;	
	}

	public Menu getMenu() {
		return menu;
	}

	public void setLayout(FlowLayout layout) {
		this.layout = layout;
	}


	public FlowLayout getLayout() {
		return layout;
	}


	public JPanel getUpPanel() {
		return upPanel;
	}


	public void setBottomPanel(JPanel bottomPanel) {
		this.bottomPanel = bottomPanel;
	}


	public JPanel getBottomPanel() {
		return bottomPanel;
	}


	public void setRecherch(ChampTexte recherch) {
		this.recherch = recherch;
	}


	public ChampTexte getRecherch() {
		return recherch;
	}


	public void setLabelResearch(JLabel labelResearch) {
		this.labelResearch = labelResearch;
	}


	public JLabel getLabelResearch() {
		return labelResearch;
	}


	public void setCenterPanel(JScrollPane centerPanel) {
		this.centerPanel = centerPanel;
	}


	public JScrollPane getCenterPanel() {
		return centerPanel;
	}


	public void setSuiv(JButton suiv) {
		this.suiv = suiv;
	}


	public JButton getSuiv() {
		return suiv;
	}


	public void setPrec(JButton prec) {
		this.prec = prec;
	}


	public JButton getPrec() {
		return prec;
	}
	
	public void setDialogue(JFileChooser dialogue) {
		this.dialogue = dialogue;
	}


	public JFileChooser getDialogue() {
		return dialogue;
	}

	public void setPathLabel(JLabel pathLabel) {
		this.pathLabel = pathLabel;
	}


	public JLabel getPathLabel() {
		return pathLabel;
	}



	public void setPrecIcon(Icon precIcon) {
		this.precIcon = precIcon;
	}


	public Icon getPrecIcon() {
		return precIcon;
	}

	public void setSuivIcon(Icon suivIcon) {
		this.suivIcon = suivIcon;
	}


	public Icon getSuivIcon() {
		return suivIcon;
	}

	
	public JPanel getMiddlePanel() {
		return middlePanel;
	}


	public void setMiddlePanel(JPanel middlePanel) {
		this.middlePanel = middlePanel;
	}

	public JPanel getMiddleLeft() {
		return middleLeft;
	}


	public void setMiddleLeft(JPanel middleLeft) {
		this.middleLeft = middleLeft;
	}

	public JPanel getMiddleRight() {
		return middleRight;
	}


	public void setMiddleRight(JPanel middleRight) {
		this.middleRight = middleRight;
	}

	public JTableRessourceModel getModelJTable() {
		return modelJTable;
	}


	public final JResourcesTable getTable() {
		return table;
	}


	public final void setTable(JResourcesTable table) {
		this.table = table;
	}


	public final void setUpPanel(JPanel upPanel) {
		this.upPanel = upPanel;
	}


	/*public void setModelJTable(JTableRessourceModel modelJTable) {
		this.modelJTable = modelJTable;
	}*/

	class AfficherSourceAction implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
		
			dialogue = new JFileChooser();
			dialogue.showOpenDialog(null);
			if(dialogue.getSelectedFile()!=null){
			pathLabel.setText(dialogue.getSelectedFile().getAbsolutePath());
			bottomPanel.add(pathLabel);		
			
			}		
			
		}		
	}
	
	class PrecSourceAction implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
		
			dialogue = new JFileChooser();
			dialogue.showOpenDialog(null);
			pathLabel.setText(dialogue.getSelectedFile().getAbsolutePath());
			bottomPanel.add(pathLabel);			
		}		
		
	}	
	
	class SuivAction implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
		
			dialogue = new JFileChooser();
			dialogue.showOpenDialog(null);
			pathLabel.setText(dialogue.getSelectedFile().getAbsolutePath());
			
			bottomPanel.add(pathLabel);
			
		}		
		
	}
		
	/*class RecherchAction implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
			//recherch.setText("ok");
			//recupération du texte-> mots clé à chercher
			String motif = recherch.getText();
			HashMap<String,Set<Document>> resultats= new HashMap<String,Set<Document>>();
			
			//lancement de la recherche			
			try {
				Searching search= new Searching(group.semantic.search.rdf.App.index.getIndexDirectory(),motif);
				//System.out.print(search.getKeywordResource().get(new String("l2")).iterator().next().get("ressource"));
				
				//recuperation des eventuels documents trouvés
				resultats= search.getKeywordResource();	
				
				System.out.println(motif+": result"+resultats);
				
			} 
	    	catch (Exception e1) {			
				e1.printStackTrace();
			}
			
			//traitement et affichage du resultat sur le tableau
			//recharger l'ancien model de la table par le nouveau 
			
			//on recharge le model
			//JTableRessourceModel newModel= new JTableRessourceModel(resultats);
			modelJTable.setKeyDocs(resultats);
			
			//on informe la table que les données du modèle ont changé
			//table.setTableModel(newModel);
			modelJTable.fireTableDataChanged();			
			//centerPanel.setViewportView(table);
			table.repaint();
		}		
	}*/
	
	/*class RechercheActionXDocument implements DocumentListener{

		public void changedUpdate(DocumentEvent arg0) {
			//ne sert pas dans notre cas
		}
		
		//quand un caractère est inséré
		public void insertUpdate(DocumentEvent arg0) {
			refreshSearch();
		}
		
		//quand un caractère est supprimé
		public void removeUpdate(DocumentEvent arg0) {
			refreshSearch();
		}
		
		void refreshSearch(){
			recherch.setText("refresh");
		}
		
	}*/
}
