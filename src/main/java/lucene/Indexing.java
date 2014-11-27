package lucene;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.SimpleSelector;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.util.FileManager;
import java.io.File;
import java.io.InputStream;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
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
	private FSDirectory IndexDirectory;
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
	 */
	
	/*
	 * description d'une ressource
	 */
	
	 private String descrRessource="";
	
	
	 public Indexing(){
		 
	 }
	
	public Indexing(Model model) throws Exception{
		/*
		 * on instance tous les objets nécessaires à la création
		 * de l'index puis on instancie l'index.
		 */
	
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
						
			
	
		
		
		index.close();
			
	}


	public String getDescrRessource() {
		return descrRessource;
	}

	public void setDescrRessource(String descrRessource) {
		this.descrRessource = descrRessource;
	}
	
	public void setIndex(IndexWriter index) {
		this.index = index;
	}

	public IndexWriter getIndex() {
		return index;
	}

	public void setIndexDirectory(FSDirectory indexDirectory) {
		IndexDirectory = indexDirectory;
	}

	public Directory getIndexDirectory() {
		return IndexDirectory;
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
	
	/*
	 * @param node est de type littéral ou ressources
	 * @return la description complete du node c'est à dire les propriétés et les littraux auxquels l'asscociér
	 */
	
	
	
	public String descripRessource(Statement stat){//cette fonction permet de décrire entierement une ressource en l'associant
		//à toutes les propriètes et littéraux qui la décrivent directement et indirectement.
		//String description;// c'est la description de la ressource
		
		RDFNode node = stat.getObject();// on lit l'objet du statement
		
		if(node.isLiteral())//si le noeud est litteral
			return descrRessource+" "+ node.toString();//on prend la valeur du noeud
		
		
		else{//si le noeud est une ressource 
			
			Resource res = (Resource) node;//continuer à l'explorer
			
			StmtIterator iter = model.listStatements(new SimpleSelector(res,null,true));//chercher tous statement
			                                                                            //qui ont res pour ressource
			
			    do
				 return  descrRessource+" "+descripRessource(iter.next());
				 while(iter.hasNext());

				}
		
	}
	
	/*@param path est le chemin du fichier rdf 
	 * @return retourne un modèle construit à partir du fichier rdf
	 */
	public Model builModel(String path) throws IllegalArgumentException{
		model = ModelFactory.createDefaultModel();
		
	 // utiliser le FileManager pour trouver le fichier d'entrée 
	 InputStream in = FileManager.get().open(path);
	if (in == null) 
	    throw new IllegalArgumentException("Fichier: "+ path +" non trouvé");
	
	model.read(in, null);
	
	return model;
	}
	

}
