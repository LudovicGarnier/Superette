package serie51;

import java.io.Serializable;

import IPane.ES;
import utils.DateUser;

public class Client implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeClient;
	private String nom;
	private String prenom;
	private DateUser dateNaissance;
	private String tel;
	private String email;
	private String adresse;

	public Client() {

	}

	public Client(String codeClient, String nom, String prenom, DateUser dateNaissance, String adresse) {
		this.codeClient = codeClient;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
	}

	public String getCodeClient() {
		return codeClient;
	}

	public void setCodeClient(String codeClient) {
		this.codeClient = codeClient;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public DateUser getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(DateUser dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return ES.aligner(codeClient, 10) + ES.aligner("\t" + nom, 20) + ES.aligner("\t" + prenom, 20)+ES.aligner("\t"+dateNaissance.toString(), 10)
				+ ES.aligner("\t" + adresse, 50);
	}
}
