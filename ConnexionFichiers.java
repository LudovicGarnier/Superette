package Connexions;

import java.io.*;

import javax.swing.ImageIcon;

import IPane.*;

public class ConnexionFichiers<TypeStructure> {

	static ImageIcon stop = new ImageIcon("ressources/stop.png");

	ES ES = new ES();

	private String nomPhysique;

	public ConnexionFichiers(String nomPhysique) {
		this.nomPhysique = nomPhysique;
	}

	public TypeStructure lire() {
		TypeStructure tab = null;

		Object obj;

		try {
			FileInputStream fis = new FileInputStream(nomPhysique);
			ObjectInputStream ois = new ObjectInputStream(fis);

			obj = ois.readObject();
			tab = (TypeStructure) obj;
			ois.close();

		} catch (FileNotFoundException e) {
			ES.affiche("Nouveau fichier à créer\n", " - Connexion", stop);
		} catch (IOException e) {
			ES.affiche("Fichier existant mais problème de lecture", " - Connexion", stop);
		} catch (ClassNotFoundException e) {
			ES.affiche("Types non correspondants", " - Connexion", stop);
			e.printStackTrace();
		}
		return tab;
	}

	public void ecrire(TypeStructure tab) {
		try {
			FileOutputStream fos = new FileOutputStream(nomPhysique);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject((TypeStructure) tab);
			oos.close();
		} catch (FileNotFoundException e) {
			ES.affiche("Nouveau Fichier", " - Connexion", stop);
		} catch (IOException e) {
			ES.affiche("Problème d'écriture (objet non serializable)", " - Connexion", stop);
		}
	}

	public void fermer() {
		try {
			FileInputStream fis = new FileInputStream(nomPhysique);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ois.close();
		} catch (FileNotFoundException e) {
			System.out.print(" Exception levée par FileInputStream... \n");
		} catch (IOException e) {
			System.out.print(" Exception levée par ObjectInputStream... \n");
		}
	}

	public String getNomPhysique() {
		return nomPhysique;
	}

	public void setNomPhysique(String nomPhysique) {
		this.nomPhysique = nomPhysique;
	}
}
