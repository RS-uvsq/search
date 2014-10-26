package graphisme;

public class Triple {

	/*
	 * représente le sujet du triplet
	 */
	private String ressource;
	/*
	 * représente la propriété du triplet
	 */
	private String propriete;
	/*
	 * représente la valeur du triplet
	 */
	private String valeur;
	
	
	
	public Triple(String ressource,String propriete,String valeur){
		this.setRessource(ressource);
		this.setPropriete(propriete);
		this.setValeur(valeur);
		
	}


	public void setRessource(String ressource) {
		this.ressource = ressource;
	}


	public String getRessource() {
		return ressource;
	}


	public void setValeur(String valeur) {
		this.valeur = valeur;
	}


	public String getValeur() {
		return valeur;
	}


	public void setPropriete(String propriete) {
		this.propriete = propriete;
	}


	public String getPropriete() {
		return propriete;
	}
	
	
}
