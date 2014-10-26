package graphisme;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;



public class JTableRDFTripleModel extends  AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String [] tripleAttrib = {"ressources","propriete","valeur"};
	private ArrayList<Triple> triples;
	
	
	public JTableRDFTripleModel(ArrayList<Triple> liste){
		super();
		
		setTriples(liste);
	}
	
	
	 public String getColumnName(int column) {
	        return tripleAttrib[column];
	    }
	 

	public int getColumnCount() {
		return tripleAttrib.length;
	}

	public int getRowCount() {
		return triples.size();
	}
	
	
	
	public Object getValueAt(int row, int column) {
		switch(column){
		case 0:
		    return this.triples.get(row).getRessource();
		case 1:
			return this.triples.get(row).getPropriete();
		case 2:
			return this.triples.get(row).getValeur();
		default:
			return null;	
		}
	}

	public String [] getPassAttrib() {
		return tripleAttrib;
	}



	public ArrayList<Triple> getPassList() {
		return triples;
	}

	public void setPassList(ArrayList<Triple> triples) {
		this.triples = triples;
	}


	public void setTriples(ArrayList<Triple> liste) {
		this.triples = liste;
	}


	public ArrayList<Triple> getTriples() {
		return triples;
	}

}
