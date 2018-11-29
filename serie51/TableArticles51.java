package serie51;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.TreeMap;

import Interfaces.InterfaceTables;

public class TableArticles51 implements InterfaceTables<AbstractArticle, Integer>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TreeMap<Integer, AbstractArticle> stock = new TreeMap<>();

	/**
	 * Constructeur
	 */
	public TableArticles51() {

		ajouter(new Article51(1, "Disk Dur", 50.5));
		ajouter(new Article51(4, "Carte m�re", 99.9));
		ajouter(new Article51(5, "Carte R�seau", 24.7));
		ajouter(new ArticlePromo51(12, "Boite 100CD", 75.5, 15.5, 10));
		ajouter(new Article51(18, "MEMOIRE FLASH", 17));
		ajouter(new ArticlePromo51(22, "Barette RAM", 56.8, 20.0, 2));
		ajouter(new Article51(25, "Lecteur DVD-ROM", 15.5));
		ajouter(new Article51(32, "Souris bluetooth", 19.5));
		ajouter(new Article51(42, "Carte Graphique GeForce GTX", 454.9));
		ajouter(new Article51(23, "Processeur Intel i7", 300.5));

	}

	/**
	 * Accesseurs
	 */
	public TreeMap<Integer, AbstractArticle> getStock() {
		return stock;
	}

	public void setStock(TreeMap<Integer, AbstractArticle> stock) {
		this.stock = stock;
	}

	/**
	 * METHODES de CLASSE
	 */
	@Override
	public AbstractArticle retourner(Integer code) {
		return stock.get(code);
	}

	@Override
	public void ajouter(AbstractArticle article) {
		stock.put(article.getCode(), article);
	}

	@Override
	public void supprimer(Integer code) {
		stock.remove(code);
	}

	public StringBuffer cle() {
		StringBuffer mes = new StringBuffer();
		if (taille() == 0)
			return mes.append("*** CATALOGUE VIDE ***\n");
		else
			mes.append("Liste des codes des Articles du Catalogue :\n");
		for (AbstractArticle com : stock.values()) {
			mes.append(" *** " + com.getCode() + "\n");
		}
		return mes;
	}

	@Override
	public int taille() {
		return stock.size();
	}

	public String toString() {
		String mes = "";
		if (taille() == 0)
			mes = "\n*** CATALOGUE VIDE ***\n";
		else
			mes = "LISTE DES ARTICLES DU CATALOGUE\n"
					+ "__________________________________________________________________________________________________________________\n";
		for (AbstractArticle article : stock.values()) {
			mes = mes + article.toString() + "\n";
		}
		return mes
				+ "__________________________________________________________________________________________________________________\n";
	}

	public String toStringPromo() {
		String s = "LISTE DES ARTICLES DU CATALOGUE EN PROMOTION \n"
				+ "__________________________________________________________________________________________________________________\n";
		;
		for (AbstractArticle article : stock.values()) {
			if (article instanceof ArticlePromo51)
				s = s + article.toString() + "\n";
		}
		return s + "__________________________________________________________________________________________________________________\n";
	}
}
