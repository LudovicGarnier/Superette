package serie51;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import Connexions.ConnexionFichiers;
import IPane.ES;
import Interfaces.InterfaceGestionDeuxTables;
import MesExceptions.ErreurSaisieException;
import utils.DateUser;

public class GestiontableDesClients implements InterfaceGestionDeuxTables<TableDesClients, TableDesCommandes51> {

	private ConnexionFichiers<TableDesClients> fichClient;
	// private int numCommande = 1;
	//
	ImageIcon help = new ImageIcon("ressources/help.PNG");
	ImageIcon stop = new ImageIcon("ressources/stop.PNG");
	ImageIcon approval = new ImageIcon("ressources/approval.PNG");
	ImageIcon cancel = new ImageIcon("ressources/cancel.PNG");
	//
	ImageIcon wait = new ImageIcon("ressources/wait.PNG");
	ImageIcon clientH = new ImageIcon("ressources/clientH.PNG");
	ImageIcon ajoutClient = new ImageIcon("ressources/ajoutClientH.PNG");
	ImageIcon suppClient = new ImageIcon("ressources/suppClientH.PNG");
	ImageIcon modifClient = new ImageIcon("ressources/modifClientH.PNG");
	ImageIcon cleClient = new ImageIcon("ressources/cleClientH.PNG");

	public GestiontableDesClients(String nomFichierClient) {
		this.fichClient = new ConnexionFichiers<>(nomFichierClient);
	}

	@Override
	public void menuGeneral(TableDesClients tabClient, TableDesCommandes51 tableCommande) throws ErreurSaisieException {
		int choix;
		do {
			choix = menuChoix();
			try {

				switch (choix) {
				case 1:
					creer(tabClient, tableCommande);
					break;
				case 2:
					modifier(tableCommande, tabClient);
					break;
				case 3:
					visualiserUnClient(tabClient);
					break;
				case 4:
					affiche(tabClient);
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
		String menu = "GESTION des CLIENTS\n" + "\n_________________________________________________________\n\n"
				+ "1 .................           AJOUTER un CLIENT\n"
				+ "2 .................           MODIFIER un CLIENT\n"
				+ "3 .................           VISUALISER un CLIENT\n"
				+ "4 .................           VISUALISER tous les CLIENTS\n"
				+ "0 .................           RETOUR\n"
				+ "_________________________________________________________\n"
				+ "                              Choix : ........ \n";
		return ES.saisie(menu, " - Gestion des Clients", 0, 4, clientH);
	}

	private void visualiserUnClient(TableDesClients tabClient) throws ErreurSaisieException {
		if (tabClient.taille() > 0) {
			String choix = ES.saisie(
					"VISUALISER UN CLIENT\nQuel Client voulez-vous afficher ? :\n" + tabClient.cle()
							+ "\n(0 pour revenir) " + "\n_________________________________________________________\n",
					" - Visualiser un Client", cleClient);
			if (choix.equals("0")) {
				ES.affiche("Opération annulée : ", " - Visualiser un Client", stop);
			} else if (tabClient.retourner(choix) == null) {
				ES.affiche("Ca Client n'exista pas !", " - Visualiser un Client", stop);
			} else {
				ES.afficheListing(tabClient.retourner(choix).toString(), "", cleClient);
			}
		} else if (tabClient.taille() == 0) {
			ES.affiche("*** Aucun Client ***", " - Visualiser un Client", stop);
		}
	}

	private void modifier(TableDesCommandes51 tableCommande, TableDesClients tabClient) throws ErreurSaisieException {
		if (tabClient.taille() > 0) {
			boolean okNom = false;
			boolean okPrenom = false;
			boolean okAdresse = false;
			String choix = ES.saisie(
					"MODIFIER UN CLIENT : \n" + "_________________________________________________________\n"
							+ tabClient.cle() + "\n_________________________________________________________\n"
							+ "\nQuel Client voulez-vous modifier ? (0 pour revenir) : ",
					" - Modifier un Client ", modifClient);
			Client modif = tabClient.retourner(choix);
			if (choix.equals("0")) {
				ES.affiche("Opération annulée", " - Modifier un Client ", cancel);
			} else if (modif == null) {
				ES.affiche("Le Client n'existe pas !", " - Modifier un Client ", stop);
			} else {
				String nom = modif.getNom();
				String prenom = modif.getPrenom();
				String adresse = modif.getAdresse();
				okNom = ES.saisieOuiNon("Voulez-vous modifier le Nom du Client n°" + choix + " ? (O/N)",
						" - Modifier un Client ", help);
				if (okNom) {
					nom = ES.saisie("Nouveau Nom :", " - Modifier un Client ", modifClient);
				}
				okPrenom = ES.saisieOuiNon("Voulez-vous modifier le Prénom du Client n°" + choix + " ? (O/N)",
						" - Edition d'un Client ", help);
				if (okPrenom) {
					prenom = ES.saisie("Nouveau Pr�énom : ", " - Modifier un Client ", modifClient);
				}
				okAdresse = ES.saisieOuiNon("Voulez-vous modifier l'adresse du Client n°" + choix + " ? (O/N)",
						" - Edition d'un Client ", help);
				if (okAdresse) {
					adresse = ES.saisie("Nouvelle Adresse : ", " - Modifier un Client ", modifClient);
				}
				ES.affiche("Client code " + choix + "\nNouveau Nom : " + nom + " - Nouveau Prénom : " + prenom
						+ " - Nouvelle Adresse : " + adresse, " - Modifier un Client ", help);
				int conf = ES.saisieConfirmation("Confirmez-vous les Modifications ?", " - Edition d'un Article ",
						help);
				if (conf == JOptionPane.YES_OPTION) {
					modif.setNom(nom);
					modif.setPrenom(prenom);
					modif.setAdresse(adresse);
				}
			}
		} else

		{
			ES.affiche("*** Aucun Client ***", " - Modifier un Client ", modifClient);
		}

	}

	@Override
	public void creer(TableDesClients tabClient, TableDesCommandes51 tabCom) throws ErreurSaisieException {
		Client tmp = saisiClient(tabClient);
		if (tmp != null)
			tabClient.ajouter(tmp);
	}

	private Client saisiClient(TableDesClients tabClient) throws ErreurSaisieException {
		String code = codeGenerator(tabClient);
		String nom = ES.saisie("Nom du Client :", " - Ajout d'un nouveau Client", ajoutClient);
		String prenom = ES.saisie("Prenom de Client :", " - Ajout d'un nouveau Client", ajoutClient);
		String adresse = ES.saisie("Adresse du Client :", " - Ajout d'un nouveau Client", ajoutClient);
		DateUser date = intialiseDateNaissance();
		int conf = ES.saisieConfirmation("Confirmez-vous l'ajout du Client " + code + " ?",
				" - Ajout d'un nouveau Client", help);
		if (conf == JOptionPane.YES_OPTION) {
			ES.affiche("Le Client de code " + code + " a été ajouté à la liste !\n", " - Ajout d'un nouveau Client",
					approval);
			return new Client(code, nom, prenom, date, adresse);
		} else {
			ES.affiche("Le Client de code " + code + " n'a pas été ajouté à la liste !\n",
					" - Ajout d'un nouveau Client", cancel);
			return null;
		}

	}

	public String codeGenerator(TableDesClients tab) {
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

	public DateUser intialiseDateNaissance() {
		do {
			try {
				int jour = ES.saisie("Jour de naissance :", " - Ajout d'un nouveau Client", 1, ajoutClient);
				int mois = ES.saisie("Mois de naissance :", " - Ajout d'un nouveau Client", 1, ajoutClient);
				int annee = ES.saisie("Année de naissance :", " - Ajout d'un nouveau Client", 1900, 2017, ajoutClient);
				DateUser date = new DateUser(jour, mois, annee);
				if (date.validate(jour, mois, annee))
					return date;
				else
					ES.affiche("Date incorrecte", " - Ajout d'un nouveau Client", stop);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} while (true);
	}

	@Override
	public void supprimer(TableDesClients tab) throws ErreurSaisieException {

	}

	@Override
	public void affiche(TableDesClients tabClient) {
		ES.afficheListing(tabClient.toString(), " - Visualiser les Clients", cleClient);
	}

	public TableDesClients recuperation() throws ErreurSaisieException {
		ES.affiche("*** CHARGEMENT du FICHIER des CLIENTS ***", " - Chargement", wait);
		TableDesClients tabClient = fichClient.lire();
		if (tabClient == null) {
			ES.affiche("*** La Table des Clients est vide ! Cr�ation par d�faut de la Table des Clients ***",
					" - Chargement", stop);
			tabClient = new TableDesClients();
		}
		return tabClient;
	}

	public void ecrire(TableDesClients tabClient) {
		fichClient.ecrire(tabClient);
	}

}
