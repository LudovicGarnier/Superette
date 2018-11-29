
package serie51;

import java.util.Enumeration;

import IPane.ES;
import Interfaces.InterfaceGestionUneTable;
import MesExceptions.ErreurSaisieException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Connexions.ConnexionFichiers;

public class GestionTableArticles51 implements InterfaceGestionUneTable<TableArticles51, TableDesCommandes51> {

	private ConnexionFichiers<TableArticles51> fichArt;
	//
	ImageIcon help = new ImageIcon("ressources/help.PNG");
	ImageIcon stop = new ImageIcon("ressources/stop.PNG");
	ImageIcon approval = new ImageIcon("ressources/approval.PNG");
	ImageIcon cancel = new ImageIcon("ressources/cancel.PNG");
	//
	ImageIcon menuArticle = new ImageIcon("ressources/menuArticle.PNG");
	ImageIcon product = new ImageIcon("ressources/product.PNG");
	ImageIcon supprimeArticle = new ImageIcon("ressources/supprimeArticle.PNG");
	ImageIcon designation = new ImageIcon("ressources/designation.PNG");
	ImageIcon coins = new ImageIcon("ressources/prix.PNG");
	ImageIcon discount = new ImageIcon("ressources/discount.PNG");
	ImageIcon edit = new ImageIcon("ressources/edit.PNG");
	ImageIcon catalogue = new ImageIcon("ressources/catalogue.PNG");
	ImageIcon wait = new ImageIcon("ressources/wait.PNG");

	/**
	 * constructeur
	 * 
	 * @param nomFichier
	 */
	public GestionTableArticles51(String nomFichier) {
		this.fichArt = new ConnexionFichiers<>(nomFichier);
	}

	public TableArticles51 recuperation() {
		ES.affiche("*** CHARGEMENT du FICHIER des ARTICLES ***", " - Chargement", wait);
		TableArticles51 tabArt = fichArt.lire();
		if (tabArt == null) {
			ES.affiche("*** La Table des Articles est vide ! Création par défaut de la Table des Articles ***",
					" - Chargement", stop);
			tabArt = new TableArticles51();
		}
		return tabArt;
	}

	public void ecrire(TableArticles51 tabArt) {
		fichArt.ecrire(tabArt);
	}

	@Override
	public void menuGeneral(TableArticles51 stock, TableDesCommandes51 tableCommande) throws ErreurSaisieException {
		//GestionMenuPrincipal51 menuPrincipal = new GestionMenuPrincipal51();
		int choix;
		do {
			choix = menuChoix();
			try {
				switch (choix) {

				case 1:
					creer(stock);
					break;
				case 2:
					supprimer(stock, tableCommande);
					break;
				case 3:
					modifier(stock, tableCommande);
					break;
				case 4:
					affiche(stock);
					break;
				case 5:
					editerTabArticles(stock);
					break;
				case 0:
					break;
				default:
					throw new ErreurSaisieException("Choix inexistant, réessayez s'il vous plaît :\n");
				}
			} catch (ErreurSaisieException e) {
				ES.affiche(e.getMessage() + "\n", " - Gestion des Articles", stop);
			}

		} while (choix != 0);
	}

	@Override
	public int menuChoix() throws ErreurSaisieException {
		String menu = "GESTION des ARTICLES\n" + "\n_________________________________________________________\n\n"
				+ "1 .................           CREER un NOUVEL ARTICLE\n"
				+ "2 .................           SUPPRIMER un ARTICLE\n"
				+ "3 .................           MODIFIER un ARTICLE\n"
				+ "4 .................           AFFICHER le CATALOGUE\n"
				+ "5 .................           AFFICHER les ARTICLES en PROMOTION\n"
				+ "0 .................           RETOUR\n"
				+ "_________________________________________________________\n"
				+ "                              Choix : ........ \n";
		return ES.saisie(menu, " - Gestion des Articles", 0, 5, menuArticle);
	}

	/**
	 * Methode qui cr�e et ajoute un nouvel Article
	 * 
	 * @param stock
	 * @throws ErreurSaisieException
	 */
	@Override
	public void creer(TableArticles51 stock) throws ErreurSaisieException {
		AbstractArticle tmp = saisiArticle(stock);
		if (tmp != null)
			stock.ajouter(tmp);
	}

	/**
	 * Supprime un Article et appelle la m�thode purge pour effacer l'Article de
	 * toutes les commandes et demande confirmation a la fin.
	 * 
	 * @param stock
	 * @param tableCommande
	 * @throws ErreurSaisieException
	 */
	@Override
	public void supprimer(TableArticles51 stock, TableDesCommandes51 tableCommande) throws ErreurSaisieException {
		visualiser(stock);
		if (stock.taille() > 0) {
			int code = ES.saisie(
					"SUPPRESSION D'UN ARTICLE : \n" + "_________________________________________________________\n"
							+ stock.cle() + "\n_________________________________________________________\n"
							+ "\nQuel Article voulez-vous supprimer ? (0 pour revenir) :",
					" - Suppression d'un Article", 0, supprimeArticle);
			if (code == 0) {
				ES.affiche("Op�ration annul�e", " - Suppression d'un Article", cancel);
			} else if (stock.retourner(code) != null) {
				int conf = ES.saisieConfirmation("Etes-vous sûr de supprimer l'article " + code + " ?",
						" - Suppression d'un Article", help);
				if (conf == JOptionPane.YES_OPTION) {
					tableCommande.purge(code);
					stock.supprimer(code);
					ES.affiche("L'Article de code " + code + " est supprimé !\n", " - Suppression d'un Article",
							approval);
				} else if (conf == JOptionPane.NO_OPTION || conf == JOptionPane.CLOSED_OPTION) {
					ES.affiche("L'Article de code " + code + " n'a pas été supprimé", " - Suppression d'un Article",
							cancel);
				} else if (stock.retourner(code) == null) {
					ES.affiche("L'Article de code " + code + " n'existe pas !\n", " - Suppression d'un Article", stop);
				}
			}
		} else {
			ES.affiche("*** Aucune Article ***", " - Supprimer un Article", supprimeArticle);
		}
	}

	/**
	 * Modifie la designation et le prix de l'article et demande confirmation a la
	 * fin.
	 * 
	 * @param stock
	 * @param tableCommande
	 * @throws ErreurSaisieException
	 */
	public void modifier(TableArticles51 stock, TableDesCommandes51 tableCommande) throws ErreurSaisieException {
		if (stock.taille() > 0) {
			boolean okPrix = false;
			boolean okDesignation = false;
			visualiser(stock);
			int choix = ES.saisie(
					"MODIFIER UN ARTICLE : \n" + "_________________________________________________________\n"
							+ stock.cle() + "\n_________________________________________________________\n"
							+ "\nQuel Article voulez-vous modifier ? (0 pour revenir) : ",
					" - Edition d'un Article ", 0, edit);
			AbstractArticle modif = stock.retourner(choix);
			if (choix == 0) {
				ES.affiche("Opération annulée", " - Edition d'un Article ", cancel);
			} else if (modif == null) {
				ES.affiche("L'Article n'existe pas !", " - Edition d'un Article ", stop);
			} else {
				String des = modif.getDesignation();
				double prix = modif.getPrix();
				okDesignation = ES.saisieOuiNon(
						"Voulez-vous modifier la Désignation de l'Article n°" + choix + " ? (O/N)",
						" - Edition d'un Article ", help);
				if (okDesignation) {
					des = ES.saisie("Nouvelle Désignation :", " - Edition d'un Article ", designation);
					okPrix = ES.saisieOuiNon("Voulez-vous modifier le prix de l'Article n°" + choix + " ? (O/N)",
							" - Edition d'un Article ", help);
					if (okPrix)
						prix = ES.saisie("Nouveau Prix: ", "", 0.0, coins);
				} else if (!okDesignation) {
					okPrix = ES.saisieOuiNon("Voulez-vous modifier le prix de l'Article n°" + choix + " ? (O/N)",
							" - Edition d'un Article ", help);
					if (okPrix)
						prix = ES.saisie("Nouveau Prix: ", "", 0.0, coins);
				}
				if (!ES.saisieOuiNon("Cet Article est-il en Promotion ? (O/N)", " - Edition d'un Article ", discount)) {
					ES.affiche(
							"Article code " + choix + "\nNouvelle Désignation : " + des + " - Nouveau Prix : " + prix,
							" - Edition d'un Article ", help);
					int conf = ES.saisieConfirmation("Confirmez-vous les Modifications ?", " - Edition d'un Article ",
							help);
					if (conf == JOptionPane.YES_OPTION) {
						stock.supprimer(choix);
						Article51 nouveau = new Article51(choix, des, prix);
						stock.ajouter(nouveau);
						actualiseArticle(nouveau, stock, tableCommande);
					}
					// break;
				} else {
					ArticlePromo51 modifPromo = new ArticlePromo51();
					double reduc = ES.saisie("Entrez la réduction à appliquer :", " - Edition d'un Article ", 0.0,
							discount);
					int qteMin = ES.saisie("Quantité minimum pour appliquer la réduction : ",
							" - Cr�ation d'un nouvel Article", 0, discount);
					modifPromo = new ArticlePromo51(choix, des, prix, reduc, qteMin);
					ES.affiche(
							"Article code " + choix + "\nNouvelle Désignation : " + des + " - Nouveau Prix : " + prix
									+ "\nRéduction : " + reduc + "%" + " - Quantité minimum : " + qteMin,
							" - Edition d'un Article ", help);
					int conf = ES.saisieConfirmation("Confirmez-vous les Modifications ?", " - Edition d'un Article ",
							help);
					if (conf == JOptionPane.YES_OPTION)
						stock.supprimer(choix);
					stock.ajouter(modifPromo);
					actualiseArticle(modif, stock, tableCommande);
					// break;
				}

			}
		} else {
			ES.affiche("*** Aucun Article ***", " - Edition d'un Article ", edit);
		}
	}

	/**
	 * Actualise la tables des commandes pour la mettre en correspondance avec le
	 * stock.
	 * 
	 * @param modif
	 * @param stock
	 * @param tableCommande
	 */
	public void actualiseArticle(AbstractArticle modif, TableArticles51 stock, TableDesCommandes51 tableCommande) {
		Enumeration<UneCommande51> enumCommande = tableCommande.getCommande().elements();
		while (enumCommande.hasMoreElements()) {
			UneCommande51 cde = enumCommande.nextElement();
			if (cde.retourner(modif.getCode()) != null) {
				LigneDeCommande51 tmp = cde.retourner(modif.getCode());
				tmp.setCode(modif.getCode());
			}
		}
	}

	@Override
	public void affiche(TableArticles51 stock) {
		ES.afficheListing(stock.toString(), " - Etat du Catalogue", catalogue);
	}

	public void editerTabArticles(TableArticles51 stock) {
		ES.afficheListing(stock.toStringPromo(), " - Articles en Promotion", discount);
	}

	/**
	 * affiche les codes produits du stock.
	 * 
	 * @param stock
	 */
	public void visualiser(TableArticles51 stock) {
		stock.cle();
	}

	/**
	 * Appel� par la methode creer(), permet la saisi d'un nouvel Article
	 * 
	 * @param stock
	 * @return
	 * @throws ErreurSaisieException
	 */
	public AbstractArticle saisiArticle(TableArticles51 stock) throws ErreurSaisieException {
		int code = ES.saisie("CREATION D'UN NOUVEL ARTICLE\nCode Article : (0 pour revenir)",
				" - Création d'un nouvel Article", 0, product);
		AbstractArticle art = stock.retourner(code);
		if (code == 0) {
			ES.affiche("Opération annulée", " - Création d'un nouvel Article", cancel);
		} else if (!valide(stock, code)) {
			ES.affiche("L'Article de code " + code + " existe déjà !\n", " - Création d'un nouvel Article", stop);
		} else if (art == null) {
			String des = ES.saisie("Désignation : ", " - Création d'un nouvel Article", designation);
			double prix = ES.saisie("Prix : ", " - Création d'un nouvel Article", 0.0, coins);
			do {
				if (!ES.saisieOuiNon("Cet Article est-il en Promotion ? (O/N)",
						"Cet Article est-il en Promotion ? (O/N)", discount)) {
					int conf = ES.saisieConfirmation("Confirmez-vous l'ajout de l'article " + code + " ?",
							" - Edition d'un Article ", help);
					if (conf == JOptionPane.YES_OPTION) {
						art = new Article51(code, des, prix);
						ES.affiche("L'Article de code " + code + " a été ajouté au catalogue !\n",
								" - Création d'un nouvel Article", approval);
						break;
					} else {
						ES.affiche("L'Article de code " + code + " n'a pas été ajouté au catalogue !\n",
								" - Création d'un nouvel Article", cancel);
						break;
					}
				} else {
					double reduc = ES.saisie("Entrez la réduction à appliquer :", " - Création d'un nouvel Article",
							0.0, discount);
					int qteMin = ES.saisie("Quantité minimum pour appliquer la réduction : ",
							" - Cr�ation d'un nouvel Article", 1, discount);
					int conf = ES.saisieConfirmation("Confirmez-vous l'ajout de l'article " + code + " ?",
							" - Edition d'un Article ", help);
					if (conf == JOptionPane.YES_OPTION) {
						art = new ArticlePromo51(code, des, prix, reduc, qteMin);
						ES.affiche("L'Article " + code + " a été ajouté au catalogue !\n",
								" - Création d'un nouvel Article", approval);
						break;
					} else {
						ES.affiche("L'Article " + code + " n'a pas été ajouté au catalogue !\n",
								" - Création d'un nouvel Article", cancel);
					}
				}
			} while (true);
		}
		return art;

	}

	/**
	 * Confirme la pr�sence d'un code article dans le stock
	 * 
	 * @param stock
	 * @param code
	 * @return
	 */
	public boolean valide(TableArticles51 stock, int code) {
		return stock.retourner(code) == null;
	}
}
