package views;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.lucene.document.Document;

public class ProjectFrame1 extends JFrame{

	
	private static final long serialVersionUID = 1L;
	private JTable tableau;
	private ChampTexte recherch;
	static JTableRessourceModel tableModel;
	
	public ProjectFrame1(){
				
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("Searching in RDF graphs");
	    this.setSize(660, 640);
	    
	    recherch = new ChampTexte(30);
	    
	    //construction du modele de donnees de la table
	    
	    //construction du map de Documents lucene vide
	    HashMap<String,Set<Document>> map = new HashMap<String,Set<Document>>();
	    
	    //initialisation du modele de la table avec le map vide
	    tableModel = new JTableRessourceModel(map);
	    tableModel.fireTableDataChanged();
	    
	    this.tableau = new JTable(tableModel);
	    this.getContentPane().add(recherch, BorderLayout.NORTH);
	    this.getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER); 
	    
	    setLocation(300,50);
	    setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	    
	}
}
