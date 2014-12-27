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
import java.util.Iterator;
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

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;

import edu.uci.ics.jung.graph.Graph;

public class ProjectFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JButton suiv;
	private JButton prec;	
	private Icon precIcon;
	private Icon suivIcon;
	private SousGraph GrapheSeiner;
	private JScrollPane centerPanel = new JScrollPane();
	private JPanel topPanel = new JPanel(new FlowLayout());
	private JPanel mainPanel = new JPanel(new FlowLayout());
	private JPanel mainPanelgrph = new JPanel(new FlowLayout());
	
	// Lien  du fichier RDF  DE L'ARBRE DE  STEINER
	private String LienFichierRDF;
	
	 //MODEL RDF de L'ARBRE DE  STEINER
	private Model ModelSparql;
	
	//barre de menu
	private JMenuBar menuBar = new JMenuBar();	
	
	private JMenu menu = new JMenu("File");	
    private OpenFileChooserAction action;	  
    private JButton Graphinit=new JButton("Graph initial");
	private ImageIcon iconNew = new ImageIcon("src\\icones\\open.png");;
	private ImageIcon iconExit = new ImageIcon("src\\icones\\exit.png");;
	private static JFrame f ;
	private JMenuItem open= new JMenuItem("New",iconNew);					  
	private JMenuItem exit = new JMenuItem("Exit", iconExit);
	
	//tableau pourr visualiser les resultats de la recherche					
	private JTable tableau; 	
	
	 //modele de donnees de notre table
	static JTableRessourceModel tableModel; 
	
	 //champs pour la recherche	
	private ChampTexte recherch;   
	
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
	public ProjectFrame(){	
		
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
	    topPanel.add(Graphinit);
	    topPanel.add(updateGraph);
Graphinit.addActionListener(new ActionListener()
	    
	    {
	    	public void actionPerformed(ActionEvent arg0) {
	    		 List<String>Resource=new ArrayList<String>();
	    		int val=tableModel.getRowCount();
	    		int i=0;
			    
				 String a=action.getpath()[0];
				  GrapheSeiner=new SousGraph(a,f);
				 GrapheSeiner.GRAPHINIT();				 
			}
	    });

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
				
				 String a=action.getpath()[0];
				  GrapheSeiner=new SousGraph(a,Resource,graphfinal, f,true);
				 GrapheSeiner.exec();
				 //MODEL A RECUPER POUR SPARQL
				 ModelSparql=GrapheSeiner.getNewmodel();
				
				 
				 String split=action.getpath()[1];
				 
				 LienFichierRDF=split+"out.rdf";
				 System.out.println("liens----->"+a+" "+"split:"+ LienFichierRDF);
				 
				 //création du fichier rdf du sous graph
				 
				 GrapheSeiner.ecrireFileRDF( LienFichierRDF);
				 
				 
				 //***********************************************************************//
				 //***********************  SPARQL QUERIES *******************************//
				 //***********************************************************************//
				 
				// String queryString = "CONSTRUCT {?subj ?prop ?obj }"
				// 					+ " WHERE { ?subj a ?obj "
				// 					+ "FILTER (?obj rdfs:subClassOf* ?subj.)}";
				 
				/* String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "        
						    		+"PREFIX owl:  <http://www.w3.org/2002/07/owl#> "
						    		+"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#> "
						    		+"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
						    		+"PREFIX film: <http://data.linkedmdb.org/directory/film_company#>"
						 			+"CONSTRUCT { ?sub ?prop ?y }"
						    		+"WHERE { ?sub ?prop ?y }";
				 					//+ "WHERE { ?t rdfs:subClassOf* film:Class . ?y rdf:type ?t }";
				 					 
				 	*/
				 //<owl:Class rdf:about="&untitled-ontology-8;Film"/>				 
				 String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "        
				    		+"PREFIX owl:  <http://www.w3.org/2002/07/owl#> "
				    		+"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#> "
				    		+"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				    		//+"PREFIX film: <http://data.linkedmdb.org/directory/film_company#>"
				 			+"CONSTRUCT { ?sub rdf:type  ?obj }"
				 						 //+ "?sub   rdf:type owl:Class }"
				    		+"WHERE {?sub   rdf:type ?obj }";
						 
			 /*String queryString = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "        
			    		+"PREFIX owl:  <http://www.w3.org/2002/07/owl#> "
			    		+"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#> "
			    		+"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
			    		//+"PREFIX film: <http://data.linkedmdb.org/directory/#>"
			    		+"SELECT ?y"
			    		+"WHERE {?y rdf:type ?x }";
			    		//+"WHERE {?t rdfs:subClassOf* film:Class . ?y rdf:type ?t}";
			 */
				 Query query = QueryFactory.create(queryString) ;
				 QueryExecution qexec = QueryExecutionFactory.create(query, ModelSparql);			 
				 				 
				 try {					  
					  	//ResultSet results = qexec.execSelect();
					 	Model resultModel = qexec.execConstruct() ;		
					 	org.graphstream.graph.Graph graphsparc = new SingleGraph("GraphSparql");
					 	SousGraph GrapheSparql=new SousGraph(graphsparc, f,resultModel,false);
					  	//System.out.println("dans try"+results.getRowNumber());
					  	
					  	//output query result
					  	//System.out.println("dans results");
					  	//ResultSetFormatter.out(System.out, results, query);
					  	
					 	/*for ( ; results.hasNext() ; ){
					 		
					 		QuerySolution soln = results.nextSolution() ;					 		
					 		
					 		System.out.println("soln: "+soln.toString());
					 		System.out.println("test = "+soln.varNames().next());
					 		RDFNode n = soln.get(); //soln.get("x");
					 		if ( n.isLiteral() ){
					 	          ((Literal)n).getLexicalForm();
					 	          System.out.println("je suis Literal");
					 		}
					 	    if ( n.isResource() )
					 	    {
					 	         Resource r = (Resource)n ;
					 	        System.out.println("je suis ressource");
					 	          if ( ! r.isAnon() )
					 	          {
					 	            r.getURI();
					 	            System.out.println("je suis noeud anonyme");
					 	          }
					 	    }
					 	}*/			 
					 	
				  } 
				  finally{					  
					  qexec.close();
				  }
				 
				 
					}//end actionPerformed
	    		}//end actionListener updateGraph 	    		
	    		
	    );
	   
	    
	    centerPanel.setViewportView(tableau);
	    topPanel.setBackground(Color.WHITE);
	    mainPanel.add(centerPanel); 	 
	    mainPanel.add(mainPanelgrph) ;	    
	    //System.out.println("hello");
	    f.getContentPane().add(topPanel, BorderLayout.NORTH);
	    //getContentPane().add(image, BorderLayout.WEST);
	    f.getContentPane().add(mainPanel, BorderLayout.WEST) ;	    
	    f.setVisible(true);
	    //f.setLocation(300,50);
	    initMenu();
	}
	 public void parcours(){
			StmtIterator iter =  ModelSparql.listStatements();

			// affiche l'objet, le prédicat et le sujet de chaque déclaration
			while (iter.hasNext()) {
				
				Statement stmt      = iter.nextStatement();  // obtenir la prochaine déclaration
			  
				Resource  subject   = stmt.getSubject();     // obtenir le sujet
			    Property  predicate = stmt.getPredicate();   // obtenir le prédicat
			    RDFNode   object    = stmt.getObject();      // obtenir l'objet
			    
			    System.out.print(subject.toString());
			    System.out.print(" " + predicate.toString() + " ");
			    if (object instanceof Resource) {
			       System.out.print(object.toString());
			    } else {
			        // l'objet est un littéral
			       System.out.print(" \"" + object.toString() + "\"");
			    }
			    System.out.println(" .");
			}
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
	private  File file;
	String  pass1;
	JFrame fen;
	public String[] getpath()
	{
		String[] str={pass,pass1};
		return str;
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
            file = fc.getSelectedFile();
            
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
        	 
        	pass1=file.getPath().replaceAll(file.getName(),"");
        	
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
 
	

