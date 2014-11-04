package views;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import org.apache.lucene.document.Document;


public class JTableRessourceModel extends  AbstractTableModel{

	
	private static final long serialVersionUID = 1L;
	
	/**
	 * contient l'association mot clé et set de documents trouvés
	 */
	private HashMap<String,Set<Document>> keyDocs;
	
	//constructeur
	public JTableRessourceModel(HashMap<String,Set<Document>> keyDocs){	  
	  this.keyDocs = keyDocs;
	  
	}

	public final HashMap<String, Set<Document>> getKeyDocs() {
		return keyDocs;
	}

	public final void setKeyDocs(HashMap<String, Set<Document>> keyDocs) {
		this.keyDocs = keyDocs;
		fireTableDataChanged();
	}

	public int getColumnCount() {		
		return 2;
	}

	public int getRowCount() {		
		int nDocuments=0;
		
		for(Entry<String, Set<Document>> entry : keyDocs.entrySet()) {
		    String key = entry.getKey();
		    System.out.println("nombre de key" +key);
		    Set<Document> value = entry.getValue();
		    System.out.println("nombre de Docs" +value.size());
		    // comptabilisons le nombre de documents 
		    nDocuments += value.size();		    
		}
		System.out.println("nombre de docs: "+nDocuments);
		return nDocuments;
	}

	public Object getValueAt(int row, int col) {
		
		Set<String> keys = keyDocs.keySet();
		Iterator<String> itKey = keys.iterator();
		while (itKey.hasNext()){
		   String cle = itKey.next(); // prend la valeur de la clé
		   Set<Document> valeur = keyDocs.get(cle); 
		   Iterator<Document> itDoc = valeur.iterator();
		   while(itDoc.hasNext()){			   
			   switch(col){
			   case 0:
				   return cle; //valeur de la clé stockée-->itKey.next()
			   case 1:
				   return itDoc.next().get("ressource"); // on recupère la valeur de la ressource a partir du document
			   }			   
		   }
		}
		return null;
	}
	
	/* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int col) {
        switch (col) {
        case 0:            
            return "Keyword";
        case 1:
            return "Document Path";        
        }
        return null;
    }    
    
}
