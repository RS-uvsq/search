package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
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

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

public class ProjectFrame1 extends JFrame{

	
	private static final long serialVersionUID = 1L;
	
	/*private JTable tableau; 		//tableau pourr visualiser les resultats de la recherche
	private JMenu menu;				//menu personnalisée
	private ChampTexte recherch;    //champs pour la recherche	
	private JMenuBar menuBar;		//barre de menu
	private JLabel recherchLabel;
	private ImageIcon iconNew;
	private ImageIcon iconExit;
	
	private JMenuItem open;
	private JMenuItem exit;
	private JPanel panel ;*/
	
	private JButton suiv;
	private JButton prec;	
	private Icon precIcon;
	private Icon suivIcon;
	
	private JScrollPane centerPanel = new JScrollPane();
	private JPanel topPanel = new JPanel(new FlowLayout());
	private JPanel mainPanel;
	
	private JMenuBar menuBar = new JMenuBar();	//barre de menu
	
	private JMenu menu = new JMenu("File");	
				  
 
	private ImageIcon iconNew = new ImageIcon("src\\icones\\open.png");;
	private ImageIcon iconExit = new ImageIcon("src\\icones\\exit.png");;
	
	private JMenuItem open= new JMenuItem("New",iconNew);					  
	private JMenuItem exit = new JMenuItem("Exit", iconExit);
						
	private JTable tableau; 				//tableau pourr visualiser les resultats de la recherche
	static JTableRessourceModel tableModel;  //modele de donnees de notre table
	
	private ChampTexte recherch;    //champs pour la recherche	
	
	private JLabel recherchLabel;	
	
	//construction du menu
	private void initMenu(){
		menu.setMnemonic(KeyEvent.VK_F);
		open.setMnemonic(KeyEvent.VK_N);
		open.addActionListener(new OpenFileChooserAction());
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
	    setJMenuBar(menuBar);		
	}//end initMenu
	
	/**
	 * the constructor
	 */
	public ProjectFrame1(){	
		
	    mainPanel = new JPanel();		 
		
		this.setLocationRelativeTo(null);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setTitle("Searching in RDF graphs");
	    this.setSize(660, 640);
		this.setLayout(new BorderLayout());
		
		suiv = new JButton();
		suiv.setPreferredSize(new Dimension(25,25));
		prec = new JButton();
		prec.setPreferredSize(new Dimension(25,25));
		precIcon = new ImageIcon("src\\icones\\prec.png");
		suivIcon = new ImageIcon("src\\icones\\suiv.png");
		prec.setIcon(precIcon);
		suiv.setIcon(suivIcon);
		
		topPanel.setSize(660, 40);
		mainPanel.setSize(660,600);
		//construction du modele de donnees de la table	    
	    //construction du map de Documents lucene vide
	    HashMap<String,Set<Document>> map = new HashMap<String,Set<Document>>();
	    
		//initialisation du modele de la table avec le map vide
	    tableModel = new JTableRessourceModel(map);
	    tableModel.fireTableDataChanged();
	    
	    tableau = new JTable(tableModel);
	    //tableau.setAutoCreateColumnsFromModel(false);
        recherch = new ChampTexte(30);
        recherch.setSize(40,25);
   
	    recherchLabel = new JLabel("Recherche:");
	    
	    //contruction de du paneau superieur
	    topPanel.add(recherchLabel);
	    topPanel.add(recherch);
	    topPanel.add(suiv);
	    topPanel.add(prec);
	    
	    //mainPanel.add(tableau);
	    centerPanel.setViewportView(tableau);
	    mainPanel.add(centerPanel); 	    
	    //this.add(mainPanel, BorderLayout.SOUTH) ;
	    
	    this.getContentPane().add(topPanel, BorderLayout.NORTH);
	    this.getContentPane().add(mainPanel, BorderLayout.CENTER) ;	    
	    
	    setLocation(300,50);
	    initMenu();
	    setVisible(true);	    
	}
		
}

/**
 * classe qui implémente l'ouverture d'une Dialog pour le choix d'un fichier
 * notamment dans notre cas un fichier RDF
 * @author Daniel
 *
 */
class OpenFileChooserAction implements ActionListener{

	public void actionPerformed(ActionEvent e) {
		
		    String DEFAULT_PATH ="src\\main\\java\\jena\\";
		    
			//open a directory on a precise directory			
			final JFileChooser fc = new JFileChooser(DEFAULT_PATH);
			
			//filter to show only the given types of file
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "Fichiers RDF", "rdf", "rdfs", "owl");
			    fc.setFileFilter(filter);
			    
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();	            	            
	            
	            /*********************************/
	             /* applying the indexing method  */
	             /*********************************/
	            
	            //creation of the Jena's empty file
	            Model	model = ModelFactory.createDefaultModel();
	    		
	        	// FileManager to open the file 
	        	InputStream in = FileManager.get().open(DEFAULT_PATH+file.getName());
	        	if (in == null) 
	        	    throw new IllegalArgumentException("Fichier: non trouvé");
	        	
	        	model.read(in, null);    	
	        	
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
 
	

