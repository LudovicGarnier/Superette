package serie51;

import IPane.ES;
import Interfaces.InterfaceGestionDeuxTables;
import MesExceptions.ErreurSaisieException;
import javax.swing.ImageIcon;
import Connexions.ConnexionFichiers;
import utils.*;

public class GestionTablesCommandes51 implements InterfaceGestionDeuxTables<TableDesCommandes51, TableArticles51> {

	private ConnexionFichiers<TableDesCommandes51> fichCommande;
	private TableDesClients tableClient;
	GestiontableDesClients gtcl;

	// private int numCommande = 1;
	//
	ImageIcon help = new ImageIcon("ressources/help.PNG");
	ImageIcon stop = new ImageIcon("ressources/stop.PNG");
	ImageIcon approval = new ImageIcon("ressources/approval.PNG");
	ImageIcon cancel = new ImageIcon("ressources/cancel.PNG");
	//
	ImageIcon commandeIco = new ImageIcon("ressources/commande.PNG");
	ImageIcon supprimeCommande = new ImageIcon("ressources/supprime.PNG");
	ImageIcon modifeCommande = new ImageIcon("ressources/edit.PNG");
	ImageIcon facture = new ImageIcon("ressources/facture.PNG");
	ImageIcon wait = new ImageIcon("ressources/wait.PNG");

	public GestionTablesCommandes51(String nomFichier, TableDesClients tabClient) {
		this.fichCommande = new ConnexionFichiers<>(nomFichier);
		this.tableClient=tabClient;
	}

	public TableDesClients getTableClient() {
		return tableClient;
	}

	public void setTableClient(TableDesClients tableClient) {
		this.tableClient = tableClient;
	}

	public TableDesCommandes51 recuperation() {
		ES.affiche("*** CHARGEMENT du FICHIER des COMMANDES ***", " - Chargement", wait);
		TableDesCommandes51 tabCommandes = fichCommande.lire();
		if (tabCommandes == null) {
			ES.affiche("*** La Table des Commandes est vide ! ***", " - Chargement", stop);
			tabCommandes = new TableDesCommandes51();
		}
		return tabCommandes;
	}

	public void ecrire(TableDesCommandes51 tabCommande) {
		fichCommande.ecrire(tabCommande);
	}

	@Override
	public void menuGeneral(TableDesCommandes51 tableCommande, TableArticles51 stock) throws ErreurSaisieException {
		int choix;
		do {
			choix = menuChoix();
			try {

				switch (choix) {
				case 1:
					creer(tableCommande, stock);
					break;
				case 2:
					// supprimer(tableCommande);
					break;
				case 3:
					modifier(tableCommande, stock);
					break;
				case 4:
					visualiserUneCommande(tableCommande);
					break;
				case 5:
					affiche(tableCommande);
					break;
				case 6:
					editerFacture(tableCommande, stock);
					break;
				case 0:
					break;
				default:
					throw new ErreurSaisieException("Choix inexistant réessayez, s'il vous plait :\n");
				}
			} catch (ErreurSaisieException e) {
				ES.affiche(e.getMessage() + "\n", " - Gestion des Commandes", stop);
			}
		} while (choix != 0);
	}

	@Override
	public int menuChoix() throws ErreurSaisieException {
		String menu = "GESTION des COMMANDE\n" + "\n_________________________________________________________\n\n"
				+ "1 .................           CREER une COMMANDE\n"
				// + "2 ................. SUPPRIMER une COMMANDE\n"
				+ "3 .................           MODIFIER une COMMANDE\n"
				+ "4 .................           VISUALISER une COMMANDE\n"
				+ "5 .................           VISUALISER toutes les COMMANDES\n"
				+ "6 .................           EDITION FACTURE\n" + "0 .................           RETOUR\n"
				+ "_________________________________________________________\n"
				+ "                              Choix : ........ \n";
		return ES.saisie(menu, " - Gestion des Commandes", 0, 6, commandeIco);
	}

	/**
	 * Methode qui cr�e et ajoute une nouvelle commande
	 * 
	 * @param tableCommande
	 * @param stock-
	 * @param numCommande
	 * @throws ErreurSaisieException
	 */
	@Override
	public void creer(TableDesCommandes51 tableCommande, TableArticles51 stock) throws ErreurSaisieException {
		GestionUneCommande51 menuUneCommande = new GestionUneCommande51();
		String numCommande = premierNumDispo(tableCommande);
		UneCommande51 commande = saisiCommande(tableCommande, stock, numCommande);
		if (commande != null) {
			tableCommande.ajouter(commande);
			menuUneCommande.menuGeneral(commande, stock);
		}
	}

	/**
	 * Supprime une commande et appelle la m�thode purge pour l'effacer de la table
	 * des commandes et demande confirmation a la fin.
	 * 
	 * @param tableCommande
	 * @throws ErreurSaisieException
	 */
	@Override
	public void supprimer(TableDesCommandes51 tableCommande) throws ErreurSaisieException {
		/*
		 * if (tableCommande.taille() > 0) { int code = ES.saisie(
		 * "SUPPRESSION D'UNE COMMANDE :" +
		 * "\n_________________________________________________________\n" +
		 * tableCommande.cle() +
		 * "Quelle Commande voulez-vous supprimer ? (0 pour revenir) :\n",
		 * " - Suppression d'une Commande", 0, supprimeCommande); if (code == 0) {
		 * ES.affiche("Op�ration annul�e", " - Supprimer une Commande", stop); } else if
		 * (tableCommande.retourner(code) != null) { int conf =
		 * ES.saisieConfirmation("Etes-vous s�r de vouloir supprimer la commande n�" +
		 * code + " ?", " - Supprimer une Commande", help); if (conf ==
		 * JOptionPane.YES_OPTION) { tableCommande.supprimer(code);
		 * ES.affiche("La Commande de code " + code + " a �t� supprim� !",
		 * " - Supprimer une Commande", approval); } else if (conf ==
		 * JOptionPane.NO_OPTION || conf == JOptionPane.CLOSED_OPTION) {
		 * ES.affiche("La Commande de code " + code + " n'a pas �t� supprim� ",
		 * " - Supprimer une Commande", cancel); } else if
		 * (tableCommande.retourner(code) == null) { ES.affiche("\nLa Commande de code "
		 * + code + " n'existe pas !\n", " - Supprimer une Commande", stop); } } } else
		 * { ES.affiche("*** Aucune Commande ***", " - Supprimer une Commande", stop); }
		 */
	}

	/**
	 * modifie la commande
	 * 
	 * @param tableCommande
	 * @param stock
	 * @throws ErreurSaisieException
	 */
	public void editerFacture(TableDesCommandes51 tableCommande, TableArticles51 stock) throws ErreurSaisieException {
		if (tableCommande.taille() > 0) {
			String choix = ES.saisie(
					tableCommande.cle() + "De quelle Commande voulez-vous éditer la Facture ? (0 pour revenir) :\n",
					" - Edition Facture", facture);
			if (tableCommande.retourner(choix) == null) {
				ES.affiche("La Commande n'existe pas !\n", " - Edition Facture", stop);
			} else {
				UneCommande51 cde = tableCommande.retourner(choix);
				facturer(cde, stock);
			}
		} else {
			ES.affiche("Cette Commande n'existe pas !\n", " - Edition Facture", stop);
		}
	}

	public void modifier(TableDesCommandes51 tableCommande, TableArticles51 stock) throws ErreurSaisieException {
		GestionUneCommande51 menuUneCommande = new GestionUneCommande51();
		if (tableCommande.taille() > 0) {
			String code = ES.saisie(
					"MODIFIER UNE COMMANDE :" + "\n_________________________________________________________\n"
							+ tableCommande.cle() + "Quelle Commande voulez-vous modifier ? (0 pour revenir) :\n",
					" - Modifier une Commande", modifeCommande);
			if (code.equals("0")) {
				ES.affiche("Opération annulée", " - Modifier une Commande", stop);
			} else if (tableCommande.retourner(code) != null) {
				menuUneCommande.menuGeneral(tableCommande.retourner(code), stock);
			}
		} else {
			ES.affiche("*** Aucune Commande ***", " - Modifier une Commande", stop);
		}
	}

	public void facturer(UneCommande51 commande, TableArticles51 stock) {
		ES.afficheListing(commande.facturer(stock), " - Factures", facture);
	}

	@Override
	public void affiche(TableDesCommandes51 tableCommande) {
		ES.afficheListing(tableCommande.toString(), " - Visualiser les Commandes", commandeIco);
	}

	public void visualiserUneCommande(TableDesCommandes51 tableCommande) throws ErreurSaisieException {
		if (tableCommande.taille() > 0) {
			String choix = ES.saisie(
					"VISUALISER UNE COMMANDE\nQuelle Commande voulez-vous afficher ? :\n" + tableCommande.cle()
							+ "\n(0 pour revenir) " + "\n_________________________________________________________\n",
					" - Visualiser une Commande", commandeIco);
			if (choix.equals("0"))
				ES.affiche("Opération annulée : ", " - Visualiser une Commande", stop);
			else if (choix != null)
				ES.afficheListing(tableCommande.retourner(choix).toString(), "", commandeIco);

		} else if (tableCommande.taille() == 0) {
			ES.affiche("*** Aucune Commande ***", " - Visualiser une Commande", commandeIco);
		}
	}

	public UneCommande51 saisiCommande(TableDesCommandes51 tableCommande, TableArticles51 stock, String numCommande)
			throws ErreurSaisieException {
		DateUser dateCommande = new DateUser();
		UneCommande51 commande = null;
		String codeClient = "";
		boolean ok = false;
		if (tableClient.taille() > 0) {
			codeClient = ES.saisie(
					"CREER UNE COMMANDE\nQui est le Client ? :\n" + tableClient.cle() + "\n(0 pour revenir) "
							+ "\n_________________________________________________________\n",
					" - Cr�ation d'une Commande", null);
			if (codeClient.equals("0"))
				ES.affiche("Opération annulée : ", " - Création d'une Commande", stop);
			if ((tableClient.retourner(codeClient) != null) && valide(tableCommande, numCommande)) {
				commande = new UneCommande51(codeClient, numCommande, dateCommande);
			} else {
				ES.affiche("Ce Client n'existe pas", " - Création d'une Commande", stop);
			}
		} else {
			ES.affiche("*** Aucun Client ***", " - Création d'une Commande", stop);
		}
		return commande;
	}

	/**
	 * retourne le premier num�ro disponible d la table des commandes
	 * 
	 * @param tab
	 * @return
	 */
	public String premierNumDispo(TableDesCommandes51 tab) {
		DateUser date = new DateUser();
		date.afficheInverse();
		int num = 0;
		String res = "";
		do {
			num++;
			res = date.afficheInverse() + num;
		} while (tab.retourner(res) != null);
		return res;
	}

	public boolean valide(TableDesCommandes51 tableCommande, String code) {
		return tableCommande.retourner(code) == null;
	}

}
