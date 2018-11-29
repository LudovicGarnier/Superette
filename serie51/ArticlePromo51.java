package serie51;

public class ArticlePromo51 extends AbstractArticle {

	private double reduction;
	private int quantiteMin;

	public ArticlePromo51() {
	}

	public ArticlePromo51(int code, String designation, double prix, double reduc, int qteMin) {
		super(code, designation, prix);
		reduction = reduc;
		quantiteMin = qteMin;
	}

	public String toString() {
		return (super.toString() + " *** PROMO R�duction: " + reduction + "% " + "Quantit� Minimum : " + quantiteMin+"\t");
	}

	public double getReduction() {
		return reduction;
	}

	public int getQuantiteMin() {
		return quantiteMin;
	}

	@Override
	public double prixFacture(int quantite) {
		if (quantite < quantiteMin)
			return quantite * prix;
		else
			return quantite * (prix * (1 - reduction / 100));
	}

}
