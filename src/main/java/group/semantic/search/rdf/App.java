package group.semantic.search.rdf;

import jena.Test;
import lucene.Indexing;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
        Test t = new Test();
        

       Indexing ind = new Indexing();
       ind.builModel("/home/alassane/Downloads/genererRDFwithJENA.java");
    }
}
