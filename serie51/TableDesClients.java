package serie51;

import java.io.Serializable;
import java.util.TreeMap;

import IPane.ES;
import Interfaces.InterfaceTables;
import MesExceptions.ErreurSaisieException;
import utils.DateUser;

public class TableDesClients implements InterfaceTables<Client, String>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TreeMap<String, Client> tabClient = new TreeMap<>();

	public TableDesClients() throws ErreurSaisieException

	{

		ajouter(new Client("2018531", "GARNIER", "Ludovic", new DateUser(12, 12, 1979),
				"29 Passage Charles Dallery 75011 Paris"));
		ajouter(new Client("2018532", "DEPREZ", "Claire", new DateUser(9, 07, 1982), "24 Passage Thi�r� 75011 Paris"));
		ajouter(new Client("2018533", "AUBONNET", "Tatianna", new DateUser(01, 01, 1980), "30 Rue Cont� 75003 Paris"));
		ajouter(new Client("2018534", "HUA", "Than", new DateUser(01, 01, 1978), "231 Rue Saint Martin 75003 Paris"));

	}

	public TreeMap<String, Client> getTabClient() {
		return tabClient;
	}

	public void setTabClient(TreeMap<String, Client> tabClient) {
		this.tabClient = tabClient;
	}

	@Override
	public void ajouter(Client client) throws ErreurSaisieException {
		tabClient.put(client.getCodeClient(), client);
	}

	@Override
	public void supprimer(String code) {
		tabClient.remove(code);
	}

	@Override
	public Client retourner(String code) {
		return tabClient.get(code);
	}

	@Override
	public int taille() {
		return tabClient.size();
	}

	public StringBuffer cle() {
		StringBuffer mes = new StringBuffer();
		if (taille() == 0)
			return mes.append("*** TABLE DES CLIENTS VIDE ***\n");
		else
			mes.append("Liste des codes des Clients :\n"
					+ "__________________________________________________________________________________________________________________\n");
		for (Client client : tabClient.values()) {
			mes.append(" *** " + client.getCodeClient() + " " + client.getNom() + " " + client.getPrenom() + "\n");
		}
		return mes;
	}

	public String toString() {
		String mes = "";
		if (taille() == 0)
			mes = "\n*** TABLE DES CLIENTS VIDE ***\n";
		else
			mes = "LISTE DES CLIENTS\n" + ES.aligner("CODE", 10) + ES.aligner("\t" + "NOM", 20)
					+ ES.aligner("\t" + "PRENOM", 20) + ES.aligner("\t" + "ADRESSE", 50)
					+ "\n__________________________________________________________________________________________________________________\n";
		for (Client client : tabClient.values()) {
			mes = mes + client.toString() + "\n";
		}
		return mes
				+ "__________________________________________________________________________________________________________________\n";

	}
}
