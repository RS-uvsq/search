package lucene;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;

/*
 * @author Alassane Ndiaye
 */
public class Searching {
/*
 * permet de lire l'index comme IndexWriter permet d'écrire dans l'index
 */
	private IndexReader index;
	
/*
 * un ensemble de mots clés	
 */
	private Set<String> setOfKeywords;  

	
	/*
	 * @param indexDirectory est le repertoire de l'index crée pendant la phase d'indexation
	 * @param setOfKeywords est un ensemble de mots clés à rechercher dans l'index
	 */
	public Searching(Directory indexDirectory,String setofKeywords )throws Exception{
		
		Query q = new QueryParser(Version.LUCENE_40,"propriete", new SimpleAnalyzer(Version.LUCENE_40)).parse("propA");
		
		IndexReader reader = DirectoryReader.open(indexDirectory);
		IndexSearcher searcher = new IndexSearcher(reader);
		 int hitsPerPage = 10;
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(q, collector);
		
		ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
		    
		    reader.close();
		
		
		
	}


	public void setIndex(IndexReader index) {
		this.index = index;
	}


	public IndexReader getIndex() {
		return index;
	}

	/*
	 * @param motif est le motif saisi pour la recherche
	 * @return retourne un ensemble de mots clé à partir de la saisi
	 */
	public  Set<String> keywords(String motif){
		
		setOfKeywords = new HashSet<String>();
		
		StringTokenizer tokenizer = new StringTokenizer(motif," ");//pour extraire les mots clés du motifs
		
		while(tokenizer.hasMoreTokens())
			setOfKeywords.add(tokenizer.nextToken());//on ajoute chaque token à l'ensemble des mots-clé 
			
			
		
		return setOfKeywords;
		
		
	}


	public Set<String> getSetOfKeywords() {
		return setOfKeywords;
	}


	public void setSetOfKeywords(Set<String> setOfKeywords) {
		this.setOfKeywords = setOfKeywords;
	}

	
}
