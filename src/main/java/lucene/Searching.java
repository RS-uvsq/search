package lucene;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;

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
 * l'ensemble des documents lucene correspondants au motif de la recherche	
 */
	
	/*
	 * association de mots clé et de ressources.Un mot clé est associé à 0 ou plusieurs ressources
	 */
	private HashMap<String,Set<Document> > keywordResources;

	
	/*
	 * @param indexDirectory est le repertoire de l'index crée pendant la phase d'indexation
	 * @param setOfKeywords est un ensemble de mots clés à rechercher dans l'index
	 */
	public Searching(Directory indexDirectory,String setofKeywords)throws Exception{
		keywordResources = new HashMap<String,Set<Document> >();//on instancie 
		setOfKeywords = keywords(setofKeywords);// permet d'avoir l'ensemble des mots clés du motif de la recherche
		String keyword;//mot est un élèment de setOfKeywords
		Iterator<String> iter = setOfKeywords.iterator();//iterator pour parcourir l'ensemble des mots clés
		Set<Document> documents ;//ensemble de documents associés à un mot-clé
		
		IndexReader reader = DirectoryReader.open(indexDirectory);//on ouvre le repertoire de l'index pour la lecture
		
		while(iter.hasNext()){//tant qu'il y a un mot clé
			documents = new HashSet<Document>();
			QueryParser parser = new QueryParser("litteral", new StandardAnalyzer());//on choisit les champs sur lesquels portera la requete
			keyword = iter.next();// retourne le mot clé suivant
			Query query = parser.parse(keyword);
			IndexSearcher searcher = new IndexSearcher(reader);
						
			
			int hitsPerPage = 1;
			TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, false);
			searcher.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;
			for(int i=0;i<hits.length;++i) {
			    int docId = hits[i].doc;
			    Document doc = searcher.doc(docId);
			    documents.add(doc);
		    }
			
			keywordResources.put(keyword,documents);
		}
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

	public HashMap<String, Set<Document>> getKeywordResource() {
		return keywordResources;
	}


	public void setKeywordResource(HashMap<String, Set<Document>> keywordResource) {
		this.keywordResources = keywordResource;
	}
	
}
