package serie51;

import java.io.Serializable;

import IPane.ES;

public class LigneDeCommande51 implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int code;
	private int quantite;

	// Constructeurs
	public LigneDeCommande51(int code, int quantite) {
		this.code = code;
		this.quantite = quantite;
	}

	public LigneDeCommande51() {
	}

	// accesseurs
	public int getCode() {
		return this.code;
	}

	public int getQuantite() {
		return this.quantite;
	}

	public void setCode(int c) {
		this.code = c;
	}

	public void setQuantite(int q) {
		this.quantite = q;
	}

	public String facturer(TableArticles51 tabArt) {
		String facture = "";
		AbstractArticle art = tabArt.retourner(code);
		double PTHT = art.prixFacture(quantite);
		double PTTTC = PTHT * (1 + (20 / 100));
		double reduc;
		int qteMini;
		String remise = "";
		if (art instanceof ArticlePromo51) {
			reduc = (((ArticlePromo51) art).getReduction());
			qteMini = ((ArticlePromo51) art).getQuantiteMin();
			remise = "---> Promotion! : remise de " + reduc + "% si la quantité commandée est >= à " + qteMini;
		}
		facture = ES.aligner(" *** " + code, 10) + ES.aligner("\t*** " + art.getDesignation(), 50)
				+ ES.aligner("\t*** " + quantite, 10) + ES.aligner("\t*** " + art.getPrix(), 10)
				+ ES.aligner("\t*** " + prixTotal(tabArt), 10) + ES.aligner("\t*** " + PTHT, 10)
				+ ES.aligner("\t*** " + PTTTC, 10) + remise + "\n";
		return facture;
	}

	public double prixTotal(TableArticles51 tabArt) {
		return tabArt.retourner(code).prixFacture(quantite);
	}

	public String toString() {
		return "Code : " + this.getCode() + " - Quantit� : " + this.getQuantite();
	}

}
