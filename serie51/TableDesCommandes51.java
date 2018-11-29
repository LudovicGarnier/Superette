package serie51;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

import Interfaces.InterfaceTables;

public class TableDesCommandes51 implements InterfaceTables<UneCommande51, String>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<String, UneCommande51> commande = new Hashtable<>();
	private TableDesClients tabClient;

	/**
	 * Constructeurs
	 */
	public TableDesCommandes51() {
	}

	public TableDesCommandes51(TableDesClients tabClient) {
		this.setTabClient(tabClient);
	}

	public TableDesClients getTabClient() {
		return tabClient;
	}

	public void setTabClient(TableDesClients tabClient) {
		this.tabClient = tabClient;
	}

	/**
	 * Accesseurs
	 */
	public Hashtable<String, UneCommande51> getCommande() {
		return commande;
	}

	public void setCommande(Hashtable<String, UneCommande51> commande) {
		this.commande = commande;
	}

	/**
	 * METHODES DE CLASSES
	 */
	/**
	 * 
	 * @param code
	 * @return
	 */
	@Override
	public UneCommande51 retourner(String code) {
		return commande.get(code);
	}

	@Override
	public void ajouter(UneCommande51 ligne) {
		if (!commande.containsKey(ligne.getNumCommande()))
			commande.put(ligne.getNumCommande(), ligne);
	}

	@Override
	public void supprimer(String code) {
		commande.remove(code);
	}

	public StringBuffer cle() {
		StringBuffer mes = new StringBuffer();
		if (taille() == 0)
			return mes.append("\n*** AUCUNE COMMANDE ***\n");
		else
			mes.append("LISTE des NUMEROS des COMMANDES EFFECTUEES :\n_________________________________________________________\n");
		for (UneCommande51 com : commande.values()) {
			mes.append("*** "+com.getNumCommande() + "\n");
		}
		return mes;
	}

	@Override
	public int taille() {
		return commande.size();
	}

	public String toString() {
		String mes = "";
		if (taille() == 0)
			mes = "\n*** Aucune Commande ***\n";
		else
			mes = "TOUTES LES COMMANDES :\n" + "_________________________________________________________\n";
		Enumeration<UneCommande51> enumCommande = commande.elements();
		while (enumCommande.hasMoreElements()) {
			UneCommande51 cde = enumCommande.nextElement();
			mes = mes + cde.toString() + "\n";
		}
		return mes;
	}

	public void purge(int code) {
		Enumeration<UneCommande51> enumCde = commande.elements();
		while (enumCde.hasMoreElements()) {
			UneCommande51 cde = enumCde.nextElement();
			cde.supprimer(code);
			if (cde.taille() == 0)
				supprimer(cde.getNumCommande());
		}
	}
}
