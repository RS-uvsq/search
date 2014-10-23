package graphisme;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;


public class genererRDFwithJENA {
	genererRDFwithJENA(){
	
	}
	
	public void parcours(Model mo){
		StmtIterator iter = mo.listStatements();

		// affiche l'objet, le prédicat et le sujet de chaque déclaration
		while (iter.hasNext()) {
			
			Statement stmt      = iter.nextStatement();  // obtenir la prochaine déclaration
		  
			Resource  subject   = stmt.getSubject();     // obtenir le sujet
		    Property  predicate = stmt.getPredicate();   // obtenir le prédicat
		    RDFNode   object    = stmt.getObject();      // obtenir l'objet
		    
		    System.out.println("resource   : "+subject.toString());
		    System.out.println("propriete  : " + predicate.toString() + " ");
		    if (object instanceof Resource) {
		       System.out.print("literal   :  "+object.toString());
		    } else {
		        // l'objet est un littéral
		       System.out.println("literal   : " + object.toString() + "\"");
		    }
		    System.out.println(" .");
		}
	}
	/*public String chercher(Model model,String mot){
		
		StmtIterator iter = model.listStatements();

		// affiche l'objet, le prédicat et le sujet de chaque déclaration
		while (iter.hasNext()) {
		    Statement stmt      = iter.nextStatement();  // obtenir la prochaine déclaration
		    Resource  subject   = stmt.getSubject();     // obtenir le sujet
		    Property  predicate = stmt.getPredicate();   // obtenir le prédicat
		    RDFNode   object    = stmt.getObject();      // obtenir l'objet
		   
		    if(subject.toString()== mot || predicate.toString()==mot || object.toString()==mot) 
		    	 System.out.print(subject.toString());
		    System.out.print(" " + predicate.toString() + " ");
		    }
		   
	//	return 
		
	}*/
	public void createModel(Model model){
		
		String resource    = "http://www.dmnhfdjhgjhdg.com";
		String resource1    = "http://www.lio.com";
		String prenom    = "mehdi";
		String nom  = "abdallaoui";
		//String ressource1="http://www.mehdi.com";
		String nomcomplet     = prenom + " " + nom;

		Resource rsr= model.createResource(resource);
		rsr.addProperty(VCARD.FN, nomcomplet);
		
		
		Resource rsr1= model.createResource( resource1);
		
		rsr1.addProperty(VCARD.Given, prenom);
		rsr1.addProperty(VCARD.Family, nom);
		rsr.addProperty(VCARD.N, rsr1);
		//rsr.addProperty(VCARD.N, nom);
	}
	public Model lireFile(String fichier){
		
		 Model model = ModelFactory.createDefaultModel();
		
		 // utiliser le FileManager pour trouver le fichier d'entrée 
		 InputStream in = FileManager.get().open(fichier);
		if (in == null) {
		    throw new IllegalArgumentException("Fichier: " + fichier + " non trouvé");
		}
		model.read(in, null);
		
		return model;
	}
public void ecrireFileRDF(Model m,String fichier){

	 try (OutputStream out = new BufferedOutputStream(new FileOutputStream(fichier),1024)) {
		 m.write(out);
		        } catch (IOException e) {
	        e.printStackTrace();
		    }
}
public void ecri(FileWriter ffw){
	try{
	ffw.write("****titi______** ");  // écrire une ligne dans le fichier resultat.txt
	ffw.write("\n"); 
	}
	catch(IOException e){
		
	}
}
public void ecrire2(String nom){
	try{
		File ff=new File(nom); // définir l'arborescence
		ff.createNewFile();
		FileWriter ffw=new FileWriter(ff);
		this.ecri(ffw);
		// forcer le passage à la ligne
		ffw.close(); // fermer le fichier à la fin des traitements
		} catch (Exception e) {}
}
public void RemplireFichier(Model mo,FileWriter filew){
	StmtIterator iter = mo.listStatements();

	  
	while (iter.hasNext()) {
		
		Statement stmt      = iter.nextStatement();  
	  
		Resource  subject   = stmt.getSubject();     // obtenir le sujet
	    Property  predicate = stmt.getPredicate();   // obtenir le prédicat
	    RDFNode   object    = stmt.getObject();      // obtenir l'objet
	   
		 try{
			    	filew.write(subject.toString()+"##"+predicate.toString()+"##"+object.toString()); 
			    	filew.write("\n"); 
			    	
		 }
		 catch (IOException e){
			 
		 }
		 
			    
	}
	
}

public void rdf_to_text(String cheminrdf,String chemintxt){
	
	
	 try{
		 File ff=new File(chemintxt);
		 File ftf=new File(chemintxt);
		 ff.createNewFile();
		 FileWriter ffw=new FileWriter(ff);
		 this.RemplireFichier(this.lireFile(cheminrdf),ffw);
		
		 ffw.close();
	 } catch (Exception e) {
		 System.out.println("impossible d ouvrire ou d ecrire dans le fichier");
	 
	 }
	 
	
}

	public static void main(String[] args) {
		
		
	}

}
