package serie51;

import java.util.Enumeration;

import javax.swing.ImageIcon;
import IPane.ES;
import Interfaces.InterfaceGestionDeuxTables;
import MesExceptions.ErreurSaisieException;

public class GestionUneCommande51 implements InterfaceGestionDeuxTables<UneCommande51, TableArticles51> {

	ImageIcon help = new ImageIcon("ressources/help.PNG");
	ImageIcon stop = new ImageIcon("ressources/stop.PNG");
	ImageIcon approval = new ImageIcon("ressources/approval.PNG");
	ImageIcon cancel = new ImageIcon("ressources/cancel.PNG");
	//
	ImageIcon menuCommande = new ImageIcon("ressources/menuCommande.PNG");
	ImageIcon supprimeCommande = new ImageIcon("ressources/supprimeCommande.PNG");
	ImageIcon modifCommande = new ImageIcon("ressources/modifCommande.PNG");
	ImageIcon nouvCommande = new ImageIcon("ressources/nouvCommande.PNG");
	ImageIcon discount = new ImageIcon("ressources/discount.PNG");

	
	
	@Override
	public void menuGeneral(UneCommande51 commande, TableArticles51 stock) throws ErreurSaisieException {
		int choix;
		// int numCommande = commande.getNumCommande();
		do {
			choix = menuChoix();
			try {
				switch (choix) {
				case 1:
					creer(commande, stock);
					break;
				case 2:
					supprimer(commande);
					break;
				case 3:
					modifier(commande);
					break;
				case 4:
					affiche(commande);
					break;
				case 0:
					break;
				}
			} catch (ErreurSaisieException e) {
				ES.affiche(e.getMessage() + "\n", " - Gestion d'une Commande", stop);
			}

		} while (choix != 0);
	}

	@Override
	public int menuChoix() throws ErreurSaisieException {
		String menu = "GESTION d'une COMMANDE\n" + "\n_________________________________________________________\n\n"
				+ "1 .................           SAISIR une LIGNE de COMMANDE\n"
				+ "2 .................           SUPPRIMER une LIGNE de COMMANDE\n"
				+ "3 .................           MODIFIER la COMMANDE\n"
				+ "4 .................           AFFICHER la COMMANDE en COURS\n"
				+ "0 .................           RETOUR\n"
				+ "_________________________________________________________\n"
				+ "                              Choix : ........ \n";
		return ES.saisie(menu, " - Gestion d'une Commande", 0, 4, menuCommande);
	}

	/**
	 * Cree un nouvelle Commande et l'ajoute dans la table des commandes
	 * 
	 * @param commande
	 * @param stock
	 * @throws ErreurSaisieException
	 */
	@Override
	public void creer(UneCommande51 commande, TableArticles51 stock) throws ErreurSaisieException {
		LigneDeCommande51 tmp = saisiLigneDeCommande(commande, stock);
		if (tmp != null)
			commande.ajouter(tmp);
		else
			ES.affiche("Ce Client n'exista pas !","",stop);
	}

	/**
	 * Supprime une ligne de commande
	 * 
	 * @param commande
	 * @throws ErreurSaisieException
	 */

	@Override
	public void supprimer(UneCommande51 commande) throws ErreurSaisieException {
		if (commande.taille() > 0) {
			int choix = ES.saisie(
					"SUPPRIMER UNE LIGNE DE COMMANDE :\n"
							+ "_________________________________________________________\n" + commande.toString() + "\n"
							+ "Quelle Ligne voulez-vous supprimer ? (0 pour revenir) :",
					" - Suppression Ligne de Commande", 0, supprimeCommande);
			LigneDeCommande51 tmp = commande.retourner(choix);
			if (choix == 0) {
				ES.affiche("Op�ration annul�e", " - Suppression Ligne de Commande", cancel);
			} else if (tmp == null)
				ES.affiche("La Ligne n'existe pas !\n", " - Suppression Ligne de Commande", stop);
			else
				commande.supprimer(choix);
		} else {
			ES.affiche("*** Commande Vide ***", " - Suppression Ligne de Commande", supprimeCommande);
		}
	}

	/**
	 * modifie une ligne de commande
	 * 
	 * @param commande
	 * @throws ErreurSaisieException
	 */
	public void modifier(UneCommande51 commande) throws ErreurSaisieException {
		if (commande.taille() > 0) {
			int choix = ES.saisie(
					"MODIFIER UNE LIGNE DE COMMANDE :\n" + "_________________________________________________________\n"
							+ commande.toString() + "Quelle Ligne voulez-vous modifier ? : (0 pour revenir)",
					" - Edition d'une Ligne", 0, modifCommande);
			LigneDeCommande51 modif = commande.retourner(choix);
			if (choix == 0) {
				ES.affiche("Op�ration annulée", " - Edition d'une Ligne", cancel);
			} else if (modif == null) {
				ES.affiche("L'Article n'existe pas !\n", " - Edition d'une Ligne", stop);
			} else {
				int qte = ES.saisie("Nouvelle Quantité : ", " - Edition d'une Ligne", 0, help);
				if (qte == 0)
					commande.supprimer(choix);
				else
					modif.setQuantite(qte);
			}
		} else {
			ES.affiche("*** Commande Vide ***", " - Edition d'une Ligne", modifCommande);
		}
	}

	@Override
	public void affiche(UneCommande51 commande) {
		ES.affiche(commande.toString(), " - Commande n°" + commande.getNumCommande(), menuCommande);
	}

	/**
	 * appel� par la methode creer() pour saisir une nouvelle ligne de commande
	 * 
	 * @param commande
	 * @param stock
	 * @return
	 * @throws ErreurSaisieException
	 */
	public LigneDeCommande51 saisiLigneDeCommande(UneCommande51 commande, TableArticles51 stock)
			throws ErreurSaisieException {
		int qte;
		int code = ES.saisie("SAISIE D'UNE LIGNE DE COMMANDE :\n"
				+ "_________________________________________________________\n" + stock.cle()
				+ "\n_________________________________________________________\n" + "Code Article (0 pour revenir) : ",
				" - Nouvelle Ligne de Commande", 0, nouvCommande);
		AbstractArticle art = stock.retourner(code);
		if (code == 0) {
			ES.affiche("Op�ration annul�e", " - Nouvelle Ligne de Commande", cancel);
		} else if (art != null) {
			if (art instanceof ArticlePromo51) {
				ArticlePromo51 artPromo = (ArticlePromo51) art;
				ES.affiche("L'Article est actuellement en Promotion !\nQuantité minimum pour en profiter : "
						+ artPromo.getQuantiteMin() + "\n", " - Nouvelle Ligne de Commande", discount);
				qte = ES.saisie("Quelle quantité ?:", " - Nouvelle Ligne de Commande", 1, help);
				return (new LigneDeCommande51(code, qte));
			} else if (!(art instanceof ArticlePromo51)) {
				LigneDeCommande51 ligne = commande.retourner(code);
				if (stock.retourner(code) != null) {
					qte = ES.saisie("Quelle Quantité ? :", " - Nouvelle Ligne de Commande", 0, help);
					if (ligne == null)
						return (new LigneDeCommande51(code, qte));
					else if (ligne != null)
						ligne.setQuantite(qte + ligne.getQuantite());
				}
			}
		}
		return null;
	}

	/**
	 * parcours la table des commande pour verifier si un code commande est present,
	 * sinon on supprime la commande
	 * 
	 * @param tableCommande
	 * @param code
	 */
	public void purge(TableDesCommandes51 tableCommande, int code) {
		Enumeration<UneCommande51> enumCommande = tableCommande.getCommande().elements();
		while (enumCommande.hasMoreElements()) {
			UneCommande51 cde = enumCommande.nextElement();
			if (cde.retourner(code) != null)
				cde.supprimer(code);
			if (cde.taille() == 0)
				tableCommande.supprimer(cde.getNumCommande());
		}
	}

	/**
	 * valide la prsence d'un code article dans le stock
	 * 
	 * @param stock
	 * @param code
	 * @return
	 */
	public boolean valide(TableArticles51 stock, int code) {
		return stock.retourner(code) != null;
	}
}
