package lucene;

import com.hp.hpl.jena.rdf.model.Model;
<<<<<<< HEAD
import com.hp.hpl.jena.rdf.model.ModelFactory;
=======
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
<<<<<<< HEAD
import com.hp.hpl.jena.util.FileManager;
import java.io.File;
import java.io.InputStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
=======
import java.io.File;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


/*
 * @author alassane ndiaye
 * @version 1.O
 */
public class Indexing {
	
	/*
	 * indexe les documents rdf.
	 */
	private IndexWriter index;
	/*repertoire contenant l'index
	 * 
	 */
<<<<<<< HEAD
	private FSDirectory IndexDirectory;
=======
	private FSDirectory indexDirectory;
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
	/*
	 * sert à la configuration de l'index et prends en paramètre l'analyseur et 
	 * la version de la version de librairie lucene
	 */
	private IndexWriterConfig indexConfig;
	/*
	 * analyseur de l'index
	 */
	private Analyzer indexAnalyser;
	/*
	 * model du graphe jena
	 */
	private Model model;
	
	/*
	 * @throws lance une exception si le repertoire de l'index
	 * n'existe pas.
<<<<<<< HEAD
	 */
	
	/*
	 * description d'une ressource
	 */
	
	 private String descrRessource="";
	
	
	 public Indexing(){
		 
	 }
	
=======
	 * cette classe permet de creer un index de ressources à partir du modele RDFJena.
	 * Une ressource est un champ du document lucene(ressource,propriete,litteral)
	 */
	
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
	public Indexing(Model model) throws Exception{
		/*
		 * on instance tous les objets nécessaires à la création
		 * de l'index puis on instancie l'index.
		 */
	
<<<<<<< HEAD
		indexAnalyser = new SimpleAnalyzer(Version.LUCENE_40);
		IndexDirectory = FSDirectory.open(new File("src/lucene/indexFolder"));
		indexConfig = new IndexWriterConfig(Version.LUCENE_40, indexAnalyser);
		index = new IndexWriter(IndexDirectory, indexConfig);
		
		
	
	
		//itérator pour parcourir les statements
  // obtenir la prochaine déclaration
		
		
	  
	
		
				
				Document doc = new Document();
				doc.add(new StringField("ressource","",Field.Store.YES));
				doc.add(new TextField("description","des",Field.Store.YES));
				index.addDocument(doc);
						
			
	
		
=======
		this.model = model;
		indexAnalyser = new StandardAnalyzer();
		File indexFile = new File("lucene\\indexDirectory");
		
		indexDirectory = FSDirectory.open(indexFile);
		indexConfig = new IndexWriterConfig(Version.LATEST, indexAnalyser);
		index = new IndexWriter(indexDirectory, indexConfig);		
	
	
		//itérator pour parcourir les statements
		// obtenir la prochaine déclaration
		
		  StmtIterator iter = model.listStatements();//on l'ensemble des statements du model à l'aide d'un iterator
	      while(iter.hasNext()){
	    	    Statement st = iter.next();
	           // String ressDesc = this.descripRessource(st,"");// decrit entierement la ressource du triplet
	            Document doc = new Document();
				doc.add(new StringField("ressource",st.getSubject().toString(),Field.Store.YES));
				doc.add(new TextField("propriete",st.getPredicate().toString(),Field.Store.YES));
				doc.add(new TextField("litteral",st.getObject().toString(),Field.Store.YES));
				index.addDocument(doc);
	      }
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
		
		index.close();
			
	}
<<<<<<< HEAD


	public String getDescrRessource() {
		return descrRessource;
	}

	public void setDescrRessource(String descrRessource) {
		this.descrRessource = descrRessource;
	}
=======
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
	
	public void setIndex(IndexWriter index) {
		this.index = index;
	}

	public IndexWriter getIndex() {
		return index;
	}

	public void setIndexDirectory(FSDirectory indexDirectory) {
<<<<<<< HEAD
		IndexDirectory = indexDirectory;
	}

	public Directory getIndexDirectory() {
		return IndexDirectory;
=======
		this.indexDirectory = indexDirectory;
	}

	public Directory getIndexDirectory() {
		return indexDirectory;
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
	}

	public void setIndexConfig(IndexWriterConfig indexConfig) {
		this.indexConfig = indexConfig;
	}

	public IndexWriterConfig getIndexConfig() {
		return indexConfig;
	}

	public void setIndexAnalyser(Analyzer indexAnalyser) {
		this.indexAnalyser = indexAnalyser;
	}

	public Analyzer getIndexAnalyser() {
		return indexAnalyser;
	}
	
<<<<<<< HEAD
	/*
	 * @param node est de type littéral ou ressources
	 * @return la description complete du node c'est à dire les propriétés et les littraux auxquels l'asscociér
	 */
	
	
	
	public String descripRessource(Statement stat){//cette fonction permet de décrire entierement une ressource en l'associant
=======
	 public Model getModel() {
			return model;
		}

		public void setModel(Model model) {
			this.model = model;
		}
	
	/*
	 * @param stat est dont on veut connaitre les  et les propriétés littéraux(directs et indirects)
	 * @param propriete est la concaténation des propriétès séparées d'un espace
	 * @param litteral est la concatenation des litteraux séparés d'un espace
	 * @return la description complete du node c'est à dire les propriétés et les littraux auxquels l'asscociér
	 */	
	
	public String descripRessource(Statement stat,String litteral){//cette fonction permet de décrire entierement une ressource en l'associant
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
		//à toutes les propriètes et littéraux qui la décrivent directement et indirectement.
		//String description;// c'est la description de la ressource
		
		RDFNode node = stat.getObject();// on lit l'objet du statement
<<<<<<< HEAD
		
		if(node.isLiteral())//si le noeud est litteral
			return descrRessource+" "+ node.toString();//on prend la valeur du noeud
		
		
=======
		//RDFNode prt = stat.getPredicate();// on lit la propriete
		
		if(node.isLiteral())
			return litteral+node.toString();//on prend la valeur du noeud
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
		else{//si le noeud est une ressource 
			
			Resource res = (Resource) node;//continuer à l'explorer
			
<<<<<<< HEAD
			StmtIterator iter = model.listStatements(new SimpleSelector(res,null,true));//chercher tous statement
			                                                                            //qui ont res pour ressource
			
			    do
				 return  descrRessource+" "+descripRessource(iter.next());
				 while(iter.hasNext());
=======
			StmtIterator iter = model.listStatements(new SimpleSelector(res,null,null,null));//chercher tous les statement
		                                                    //qui ont res pour ressource
			while(iter.hasNext())
				
				litteral =  descripRessource(iter.nextStatement(),litteral);	
			
			return litteral;
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc

				}
		
	}
	
	/*@param path est le chemin du fichier rdf 
	 * @return retourne un modèle construit à partir du fichier rdf
	 */
<<<<<<< HEAD
	public Model builModel(String path) throws IllegalArgumentException{
		model = ModelFactory.createDefaultModel();
		
	 // utiliser le FileManager pour trouver le fichier d'entrée 
	 InputStream in = FileManager.get().open(path);
	if (in == null) 
	    throw new IllegalArgumentException("Fichier: "+ path +" non trouvé");
	
	model.read(in, null);
	
	return model;
	}
=======
	
>>>>>>> 7a31a3a8f029582c32aa5dae5ea432ff67eba6fc
	

}
