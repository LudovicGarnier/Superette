
package serie51;

import javax.swing.ImageIcon;

import IPane.ES;
import MesExceptions.ErreurSaisieException;

public class ClientJava51 {

	static ImageIcon icon = new ImageIcon("ressources/shop.PNG");
	static ImageIcon stop = new ImageIcon("ressources/stop.PNG");

	public static void main(String[] args) throws ErreurSaisieException {
		String nomFichierArticles = "articles.data";
		String nomFichierCommandes = "commandes.data";
		String nomFichierClient = "client.data";
		GestionTableArticles51 gta = new GestionTableArticles51(nomFichierArticles);
		TableArticles51 tabArt = gta.recuperation();

		GestiontableDesClients gc = new GestiontableDesClients(nomFichierClient);
		TableDesClients tabClient = gc.recuperation();

		GestionTablesCommandes51 gtc = new GestionTablesCommandes51(nomFichierCommandes,tabClient);
		TableDesCommandes51 tabCom = gtc.recuperation();

		int choix;
		do {
			choix = menuChoix();

			try {
				switch (choix) {
				case 1:
					gta.menuGeneral(tabArt, tabCom);
					break;
				case 2:
					gtc.menuGeneral(tabCom, tabArt);
					break;
				case 3:
					gc.menuGeneral(tabClient, tabCom);
					break;
				case 0:
					ES.affiche("AU REVOIR ... A BIENTOT A LA SUPERETTE !!", " - Menu Principal", icon);
					gta.ecrire(tabArt);
					gc.ecrire(tabClient);
					gtc.ecrire(tabCom);
					break;
				default:
					throw new ErreurSaisieException("Choix inexistant réessayez, s'il vous plait :\n");
				}
			} catch (ErreurSaisieException e) {
				ES.affiche(e.getMessage() + "\n", " - Menu Principal", stop);
			}
		} while (choix != 0);

	}

	// MENU PRINCIPAL
	public static int menuChoix() throws ErreurSaisieException {
		String menu = "BIENVENUE A LA SUPERETTE\n" + "\n_________________________________________________________\n\n"
				+ "1 .................           Gestion des Articles\n"
				+ "2 .................           Gestion des Commandes\n"
				+ "3 .................           Gestion des Clients\n" + "0 .................           Quitter\n"
				+ "\n_________________________________________________________\n" + "Choix : ........ \n";
		return ES.saisie(menu, " - Menu Principal", 0, 3, icon);
	}

}
