package serie51;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.Vector;

import IPane.ES;
import Interfaces.InterfaceTables;
import utils.DateUser;

public class UneCommande51 implements InterfaceTables<LigneDeCommande51, Integer>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Vector<LigneDeCommande51> commande = new Vector<>();
	private String numCommande;
	private DateUser dateCommande = new DateUser();
	private DateUser dateFacture;
	private boolean etatFacture = false;
	private String codeClient;

	public UneCommande51() {

	}

	public UneCommande51(String codeClient, String numCommande, DateUser date) {
		this.codeClient = codeClient;
		this.numCommande = numCommande;
		this.dateCommande = date;
	}

	// Accesseurs
	public Vector getCommande() {
		return commande;
	}

	public void setCommande(Vector<LigneDeCommande51> facture) {
		this.commande = facture;
	}

	public DateUser getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(DateUser date) {
		this.dateCommande = date;
	}

	public void setNumCommande(String numCommande) {
		this.numCommande = numCommande;
	}

	public String getNumCommande() {
		return numCommande;
	}

	public DateUser getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(DateUser dateFacture) {
		this.dateFacture = dateFacture;
	}

	public boolean getEtatFacture() {
		return etatFacture;
	}

	public void setEtatFacture(boolean etatFacture) {
		this.etatFacture = etatFacture;
	}

	public String getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}

	// Methodes de la classe
	/**
	 * Retourne la ligne de commande dont le code produit correspont a l'entier code
	 * 
	 * @param code d'un article
	 * @return une ligne de commande
	 */
	@Override
	public LigneDeCommande51 retourner(Integer code) {
		for (int i = 0; i < taille(); i++) {
			if (code == commande.get(i).getCode())
				return commande.get(i);
		}
		return null;
	}

	/**
	 * Ajoute une ligne de commande a la facture, si elle est d�j� pr�sente on
	 * modifie la quantite
	 * 
	 * @param ligne
	 */
	@Override
	public void ajouter(LigneDeCommande51 ligne) {
		commande.addElement(ligne);
	}

	/**
	 * Supprime une ligne de commande de la facture
	 * 
	 * @param ligne
	 */
	@Override
	public void supprimer(Integer code) {
		commande.remove(retourner(code));
	}

	/**
	 * Retourne le nombre de produits differents dans la facture
	 * 
	 * @return un entier
	 */
	@Override
	public int taille() {
		return commande.size();
	}

	/**
	 * 
	 * @param stock
	 * @return
	 */

	public String facturer(TableArticles51 stock) {
		String entete, detail, pied;
		DateUser dateFacture = new DateUser();
		if (taille() == 0)
			return "Facture vide";

		entete = "\t\t\tFACTURE n�" + this.getNumCommande() + "\nDate Commande : " + this.getDateCommande()
				+ "\nDate Facture : " + dateFacture + "\n" + "Code Client : " + codeClient
				+ "\n__________________________________________________________________________________________________________________\n"
				+ ES.aligner("*** Code ", 10) + ES.aligner("\t*** DESIGNATION ", 50) + ES.aligner("\t*** QUANTITE ", 10)
				+ ES.aligner("\t*** PU(HT) ", 10) + ES.aligner("\t*** PU(TTC) ", 10)
				+ ES.aligner("\t*** TOTAL(HT)\n", 10)
				+ "\n__________________________________________________________________________________________________________________\n";
		detail = "";
		double totalHT = 0;
		for (LigneDeCommande51 ligne : commande) {
			detail = detail + ligne.facturer(stock);
			totalHT = totalHT + ligne.prixTotal(stock);
			BigDecimal bdHT = new BigDecimal(totalHT);
			bdHT = bdHT.setScale(2, BigDecimal.ROUND_DOWN);
		}
		double totalTVA = totalHT - (totalHT * (1 - (19.6 / 100)));
		BigDecimal bdTVA = new BigDecimal(totalTVA);
		bdTVA = bdTVA.setScale(2, BigDecimal.ROUND_DOWN);
		totalTVA = bdTVA.doubleValue();

		pied = "\n__________________________________________________________________________________________________________________\n"
				+ "\t\t\t\t\t\t\tTOTAL(HT) " + totalHT + "\n" + "\t\t\t\t\t\t\tTVA (20%) " + totalTVA + "\n"
				+ "\t\t\t\t\t\t\tTOTAL TTC " + (totalHT + totalTVA)
				+ "\n__________________________________________________________________________________________________________________\n";

		return entete + detail + pied;
	}

	public String toString() {
		Enumeration<LigneDeCommande51> em = commande.elements();
		String mes = new String("COMMANDE n�" + numCommande + "\nClient num�ro : " + codeClient + "\nDate Commande : "
				+ dateCommande.toString());
		if (etatFacture)
			mes = mes + "\nDate Facture " + dateFacture + "\n";
		else
			mes = mes + "\n*** Pas encore Factur� !\n";
		while (em.hasMoreElements()) {
			mes = mes + em.nextElement().toString() + "\n";
		}
		return mes;
	}

	/**
	 * ancienne version
	 */
	/*
	 * public String toString() { String mes = ""; if (taille() == 0) return mes =
	 * "\n *** Commande Vide ***\n"; else mes = "          COMMANDE n�" +
	 * getNumCommande() + "               " + "Date : " + getDateCommande() + "\n";
	 * for (int i = 0; i < taille(); i++) { mes = mes + commande.get(i).toString() +
	 * "\n"; } return mes; }
	 */
}
