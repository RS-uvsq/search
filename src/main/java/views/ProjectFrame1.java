package views;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneLayout;
import javax.swing.filechooser.FileNameExtensionFilter;








import lucene.Indexing;

import org.apache.lucene.document.Document;
import org.graphstream.graph.implementations.SingleGraph;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.util.FileManager;

import edu.uci.ics.jung.graph.Graph;

public class ProjectFrame1 {

	
	private static final long serialVersionUID = 1L;
	
	private JButton suiv;
	private JButton prec;	
	private Icon precIcon;
	private Icon suivIcon;
	
	private JScrollPane centerPanel = new JScrollPane();
	private JPanel topPanel = new JPanel(new FlowLayout());
	private JPanel mainPanel = new JPanel(new FlowLayout());
	private JPanel mainPanelgrph = new JPanel(new FlowLayout());
	
	private JMenuBar menuBar = new JMenuBar();	//barre de menu
	
	private JMenu menu = new JMenu("File");	
    private OpenFileChooserAction action;	  
   
	private ImageIcon iconNew = new ImageIcon("src\\icones\\open.png");;
	private ImageIcon iconExit = new ImageIcon("src\\icones\\exit.png");;
	private static JFrame f ;
	private JMenuItem open= new JMenuItem("New",iconNew);					  
	private JMenuItem exit = new JMenuItem("Exit", iconExit);
						
	private JTable tableau; 				//tableau pourr visualiser les resultats de la recherche
	static JTableRessourceModel tableModel;  //modele de donnees de notre table
	
	private ChampTexte recherch;    //champs pour la recherche	
	
	private JLabel recherchLabel;	
	private	JButton updateGraph;
	
	//construction du menu
	private void initMenu(){
		
		menu.setMnemonic(KeyEvent.VK_F);
		open.setMnemonic(KeyEvent.VK_N);
		 action=new OpenFileChooserAction(f);
		open.addActionListener(action);
		exit.setMnemonic(KeyEvent.VK_C);
	    exit.setToolTipText("Exit application");
	    exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.CTRL_MASK));
	    exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
					//quitter l'application
				System.exit(0);
			}
	    });
	        
	    menu.add(open);
	    menu.addSeparator();
	    menu.add(exit);
	        
	   //positionnment de la barre de menu
	    menuBar.add(menu);
	    f.setJMenuBar(menuBar);		
	}//end initMenu
	
	/**
	 * the constructor
	 */
	public ProjectFrame1(){	
		
	    //mainPanel = new JPanel();	
		
		f=new JFrame();
		
	f.setLocationRelativeTo(null);
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setTitle("Searching in RDF graphs");
	   
	    f.setSize(800, 800);
	    f.setAlwaysOnTop(true);
	    f.setResizable(true);
	    f.setExtendedState( f.getExtendedState()|JFrame.MAXIMIZED_BOTH );
		      Label lblCount;    
	    //f.pack();
		f.setLayout(new BorderLayout());
		
		suiv = new JButton();
		suiv.setPreferredSize(new Dimension(25,25));
		prec = new JButton();
		prec.setPreferredSize(new Dimension(25,25));
		updateGraph = new JButton("graphe");
		updateGraph.setPreferredSize(new Dimension(25,25));
		precIcon = new ImageIcon("src\\icones\\prec.png");
		suivIcon = new ImageIcon("src\\icones\\suiv.png");
		prec.setIcon(precIcon);
		suiv.setIcon(suivIcon);
		
		JPanel pane = new JPanel();
		JLabel image = new JLabel( new ImageIcon( "src\\icones\\logo.png"));
		pane.add(image);
		
		topPanel.setSize(660, 40);
		centerPanel.setPreferredSize(new Dimension(300,520));
		mainPanel.setSize(660,600);
		mainPanelgrph.setSize(400,600);
		
		
		
		//construction du modele de donnees de la table	    
	    //construction du map de Documents lucene vide
	    HashMap<String,Set<Document>> map = new HashMap<String,Set<Document>>();
	    
		//initialisation du modele de la table avec le map vide
	    tableModel = new JTableRessourceModel(map);
	    tableModel.fireTableDataChanged();
	    
	    tableau = new JTable(tableModel); 
	    tableau.setBackground(Color.WHITE);
        recherch = new ChampTexte(30);
        recherch.setSize(40,25);
   
	    recherchLabel = new JLabel("Recherche:");
	    
	    //contruction de du paneau superieur
	    topPanel.add(image);
	    topPanel.add(recherchLabel);
	    topPanel.add(recherch);
	    topPanel.add(suiv);
	    topPanel.add(prec);
	    topPanel.add(updateGraph);
	    updateGraph.addActionListener(new ActionListener()
	    
	    {
	    	public void actionPerformed(ActionEvent arg0) {
	    		 List<String>Resource=new ArrayList<String>();
	    		int val=tableModel.getRowCount();
	    		int i=0;
	    		 for(i=0;i<val;i++)
	    		 {
	    			 Resource.add(tableModel.getValueAt(i,1).toString()); 
	    			// System.out.println(tableModel.getValueAt(i,1).toString());
	    		 }

			    org.graphstream.graph.Graph graphfinal = new SingleGraph("GraphSteiner");
				
				/*Resource.add("http://www.lio.com");
				Resource.add("http://www.lio4.com");*/
				
				 String a=action.getpath();
				SousGraph grp=new SousGraph(a,Resource,graphfinal,
						 f);
				  grp.execute();
			}
	    }
	    		
	    		
	    		
	    		);
	   
	    
	    centerPanel.setViewportView(tableau);
	    topPanel.setBackground(Color.WHITE);
	    mainPanel.add(centerPanel); 
	 //********Debut Partie ajouté****************************//
	    
		  
		  
		//********Fin Partie ajouté****************************//
	    
	    
	    mainPanel.add(mainPanelgrph) ;
	    
	    System.out.println("hello");
	   f.getContentPane().add(topPanel, BorderLayout.NORTH);
	   //getContentPane().add(image, BorderLayout.WEST);
	   f.getContentPane().add(mainPanel, BorderLayout.WEST) ;	    
	    f.setVisible(true);
	    //f.setLocation(300,50);
	    initMenu();
	       
	}
		
}

/**
 * classe qui implémente l'ouverture d'une Dialog pour le choix d'un fichier
 * notamment dans notre cas un fichier RDF
 * @author Daniel
 *
 */
class OpenFileChooserAction implements ActionListener{
	
	private String pass;
	JFrame fen;
	public String getpath()
	{
		return pass;
	}
	public OpenFileChooserAction(JFrame f)
	{
		fen=f;
	}
	public void actionPerformed(ActionEvent e) {
		
		
	    String DEFAULT_PATH ="src\\main\\java\\jena\\";
	    
		//open a directory on a precise directory			
		final JFileChooser fc = new JFileChooser(DEFAULT_PATH);
		
		//filter to show only the given types of file
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers RDF", "rdf", "rdfs", "owl");
		    fc.setFileFilter(filter);
		    
		int returnVal = fc.showOpenDialog(fen);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();	            	            
          // String  filegraphname = file.getPath().toString();
            /*********************************/
             /* applying the indexing method  */
             /*********************************/
            
            //creation of the Jena's empty file
           
            pass=file.getPath();
        	// FileManager to open the file 
        	InputStream in = FileManager.get().open(pass);
        	if (in == null) 
        	  throw new IllegalArgumentException("Fichier: non trouvé");
        	 pass=file.getPath();
          	Model model = FileManager.get().loadModel(pass);
        	System.out.println("NOM DU FICHIER :"+pass);
        	//creation of the index on the passed model
        	try {
        			group.semantic.search.rdf.App.index = new Indexing(model);
    		} 
        	catch (Exception ex) {			
    				ex.printStackTrace();
    		}      
            
		}
	
}

	
	

}
 
	

