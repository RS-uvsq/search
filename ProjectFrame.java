package graphisme;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ProjectFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private FlowLayout layout;
	private JPanel upPanel;
	private JPanel bottomPanel;
	private JTextField recherch;
	private JLabel labelResearch;
	private JScrollPane centerPanel;
	private JButton suiv;
	private JButton prec;
	private JFileChooser dialogue;
	private JLabel pathLabel;
	private JTableRDFTriple RDFTriple;
	private JTableRDFTripleModel RDFTripleModel;
	private Icon precIcon;
	private Icon suivIcon;
	private BufferedReader tamponLectur;
	

	
	public ProjectFrame(){
		
		recherch = new JTextField(20);
		setLabelResearch(new JLabel("Recherche"));
		labelResearch.setForeground(Color.black);
		menu = new Menu();
		menu.getNewSource().addActionListener(new AfficherSourceAction());
		setJMenuBar(menu);
		suiv = new JButton();
		suiv.setPreferredSize(new Dimension(35,35));
		prec = new JButton();
		prec.setPreferredSize(new Dimension(35,35));
		precIcon = new ImageIcon("src/icones/prec.png");
		suivIcon = new ImageIcon("src/icones/suiv.png");
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
		
		ArrayList<Triple> t = new ArrayList<Triple>();
		int i=0;
		while(i!=50){
		t.add(new Triple("ressource"+i,"propriete","valeur"));
		i++;
		}
		
		
		
		RDFTripleModel = new JTableRDFTripleModel(t);
		RDFTriple = new JTableRDFTriple(RDFTripleModel);
		centerPanel = new JScrollPane();
		centerPanel.setSize(640,550);
		centerPanel.setViewportView(RDFTriple);

		pathLabel = new JLabel();
		pathLabel.setForeground(Color.white);
		
		setLayout(new BorderLayout());
		getContentPane().add(upPanel,BorderLayout.NORTH);
		getContentPane().add(centerPanel,BorderLayout.CENTER);
		getContentPane().add(bottomPanel,BorderLayout.SOUTH);
		
		
		

		
		
		setLayout(layout);
		setSize(640,640);
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


	public void setUnPanel(JPanel upPanel) {
		this.upPanel = upPanel;
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


	public void setRecherch(JTextField recherch) {
		this.recherch = recherch;
	}


	public JTextField getRecherch() {
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

	public void setRDFTriple(JTableRDFTriple rDFTriple) {
		RDFTriple = rDFTriple;
	}


	public JTableRDFTriple getRDFTriple() {
		return RDFTriple;
	}

	public void setRDFTripleModel(JTableRDFTripleModel rDFTripleModel) {
		RDFTripleModel = rDFTripleModel;
	}


	public JTableRDFTripleModel getRDFTripleModel() {
		return RDFTripleModel;
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

	public void setTamponLectur(BufferedReader tamponLectur) {
		this.tamponLectur = tamponLectur;
	}


	public BufferedReader getTamponLectur() {
		return tamponLectur;
	}
	
	/*@args fichier représente le fichier text contenant les triplé RDF
	 * @return renvoie un tableau de 50 ressources
	 */
	private ArrayList<Triple> lireDansRDF(File fichier){//cette fonction permet de renvoyer les triplés RDF
	 String ligne;//ligne représente un triplé
	 StringTokenizer token;//permet d'extraire les différents élèments d'un triplé
	 String ressource = null;//élèment d'un triplet : ressource
	 String propriete = null;//élèment d'un triplet : ressource
	 String valeur = null;//élèment d'un triplet : ressource
	 ArrayList<Triple> triples = new ArrayList<Triple>() ;//ensembl de triples extraits du fichier
		try {
			tamponLectur = new BufferedReader(new FileReader(fichier));
			int j = 0;
			while ((ligne = tamponLectur.readLine()) != null) {
				int i = 0;
			
				token = new StringTokenizer(ligne,"##");
				while(token.hasMoreTokens()){
					if(i == 0)
				ressource = token.nextToken();
				
					else	if(i == 1)
						propriete = token.nextToken();
					else
						valeur = token.nextToken();
					i++;
				}
				
				Triple tr = new Triple(ressource,propriete,valeur);
				triples.add(tr);
				j++;
				}
				tamponLectur.close();
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
		return (ArrayList<Triple>) triples;
	}
	
	
	

	class AfficherSourceAction implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
		
			dialogue = new JFileChooser();
			dialogue.showOpenDialog(null);
			pathLabel.setText(dialogue.getSelectedFile().getAbsolutePath());
			bottomPanel.add(pathLabel);
			
			
			

			 genererRDFwithJENA rdf_o=new genererRDFwithJENA();
			 
			String txt = "D:\\ws1 2014\\interface projet semantic searching\\resultat.txt";
			 
			 String out2 = dialogue.getSelectedFile().getPath();
		 File ftxt=new File(txt);
		
			
			rdf_o.rdf_to_text(out2,txt);
		
			
			
			RDFTripleModel = new JTableRDFTripleModel(lireDansRDF(ftxt));
			RDFTriple = new JTableRDFTriple(RDFTripleModel);
			
			centerPanel.setViewportView(RDFTriple);
			
			
			
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
	


	
	
	

}
