package graphisme;

import javax.swing.JTable;

public class JTableRDFTriple  extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTableRDFTripleModel tableModel;
	
	
	public JTableRDFTriple(JTableRDFTripleModel passModel){
		super(passModel);
		
	}


	public JTableRDFTripleModel getTableModel() {
		return tableModel;
	}


	public void setTableModel(JTableRDFTripleModel tableModel) {
		this.tableModel = tableModel;
	}


	


}
