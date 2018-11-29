package serie51;

public class Article51 extends AbstractArticle {

	public Article51(int code, String designation, double prix) {
		super(code, designation, prix);
	}

	@Override
	public double prixFacture(int quantite) {
		return this.getPrix() * quantite;
	}

}
