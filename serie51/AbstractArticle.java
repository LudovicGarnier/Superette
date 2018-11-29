package serie51;

import java.io.Serializable;

import IPane.ES;

public abstract class AbstractArticle implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int code;
	protected String designation;
	protected double prix;

	public abstract double prixFacture(int quantite);

	public AbstractArticle() {

	}

	public AbstractArticle(int code, String designation, double prix) {
		this.code = code;
		this.designation = designation;
		this.prix = prix;
	}

	public String toString() {
		String rep = ES.aligner("Code: " + this.getCode(), 10)
				+ ES.aligner("\tDesignation: " + this.getDesignation(), 40)
				+ ES.aligner("\tPrix Unitaire: " + this.getPrix() + " euros", 15);
		return rep;
	}

	public int getCode() {
		return code;
	}

	public String getDesignation() {
		return designation;
	}

	public double getPrix() {
		return prix;
	}
}
