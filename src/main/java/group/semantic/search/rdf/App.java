package group.semantic.search.rdf;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.document.Document;

import views.JTableRessourceModel;
import views.ProjectFrame;
import views.ProjectFrame1;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;

import lucene.Indexing;
import lucene.Searching;


public class App 
{
	public static Indexing index;
    public static void main( String[] args )
    {
    	ProjectFrame1 project = new views.ProjectFrame1();
    	
    	
    	Model	model = ModelFactory.createDefaultModel();
    		
    	// utiliser le FileManager pour trouver le fichier d'entrée 
    	InputStream in = FileManager.get().open("src\\main\\java\\jena\\out.rdf");
    	if (in == null) 
    	    throw new IllegalArgumentException("Fichier: non trouvé");
    	
    	model.read(in, null);    	
    	
    	//indexation du model
    	try {
    			index = new Indexing(model);
		} 
    	catch (Exception e) {			
				e.printStackTrace();
		}      
    	
    }
}
