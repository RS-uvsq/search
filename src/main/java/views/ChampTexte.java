package views;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import lucene.Searching;

import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;

/**********************************************************************************/
/*****       CLASSE QUI CONSTRUIT NOTRE ZONE DE RECHERCHE   ***********************/
/**********************************************************************************/

/**
 * 
 * @author Daniel
 *
 */
public class ChampTexte extends JTextField implements DocumentListener {

	
	private static final long serialVersionUID = 1L;	
	
	/**
	 * constructor with the reference to frame table model
	 * @param tableModel
	 */
	public ChampTexte(){
		super();		
		this.getDocument().addDocumentListener(this);
	}
	
	/**
	 * constructor with the given field's width
	 * @param i the field's width
	 * @param tableModel the reference to the the frame table's model
	 */
	public ChampTexte(int i) {
		super(i);		
		this.getDocument().addDocumentListener(this);
	}

	public void changedUpdate(DocumentEvent arg0) {
		//rien a faire dans notre cas
	}

	public void insertUpdate(DocumentEvent arg0) {
		try {
			refreshSearch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void removeUpdate(DocumentEvent arg0) {
		try {
			refreshSearch();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * effectue la recherche dans l'index à partir de la chaine actuelle entrée dans le champs de recheerche
	 * @throws Exception 
	 */
	void refreshSearch() throws Exception{
		
		try{
		//recupération du texte-> mots clé à chercher
		String motif = this.getText();
		HashMap<String,Set<Document>> resultats= new HashMap<String,Set<Document>>();
		
		//lancement recherche
		File indexFile = new File("lucene");
		if(indexFile.listFiles().length == 0) throw new Exception("l'index est vide. Veuillez en créer un!");
		FSDirectory indexDirectory = FSDirectory.open(indexFile);
		
		
		Searching search= new Searching(indexDirectory,motif);
			
		//recuperation des eventuels documents trouvés
		resultats= search.getKeywordResource();	
			
		//System.out.println(motif+": result ds champ texte "+resultats);
		
		//traitement et affichage du resultat sur le tableau
		//recharger l'ancien model de la table par le nouveau 
		
		//on recharge le model
		/**
		 * version avec ProjectFrame
		 */
		//ProjectFrame.modelJTable.setKeyDocs(resultats);	
		
		/**
		 * version avec ProjectFrame1
		 */
		ProjectFrame.tableModel.setKeyDocs(resultats);
		
		
		/** on informe la table que les données du modèle ont changé
		 *  table.setTableModel(newModel);
		 */
		//ProjectFrame.modelJTable.fireTableDataChanged();
		
		ProjectFrame.tableModel.fireTableDataChanged();	
		}
		catch(NullPointerException e){
		}
		
	}
}
