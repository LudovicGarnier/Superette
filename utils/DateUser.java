package utils;

import java.io.Serializable;
import java.util.*;

public class DateUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int jour;
	private int mois;
	private int annee;

	/**
	 * Constructeur avec les variables d'instance
	 * 
	 * @param jour
	 * @param mois
	 * @param annee
	 */
	public DateUser(int jour, int mois, int annee) {
		this.jour = jour;
		this.mois = mois;
		this.annee = annee;
	}

	/**
	 * Constructeur par defaut utilisant la classe Calendar
	 */
	public DateUser() {
		Calendar calc = Calendar.getInstance();
		this.jour = calc.get(Calendar.DAY_OF_MONTH);
		this.mois = calc.get(Calendar.MONTH) + 1;
		this.annee = calc.get(Calendar.YEAR);
	}

	/**
	 * Accesseurs
	 * 
	 * @return
	 */
	public int getJour() {
		return this.jour;
	}

	public int getMois() {
		return this.mois;
	}

	public int getAnnee() {
		return this.annee;
	}

	public void setJour(int j) {
		this.jour = j;
	}

	public void setMois(int m) {
		this.mois = m;
	}

	public void setAnnee(int a) {
		this.annee = a;
	}

	/**
	 * methode toString()
	 */
	public String toString() {
		return jour + "/" + mois + "/" + annee;
	}

	public String afficheInverse() {
		return "" + annee + mois + jour;
	}

	/**
	 * methode valiDate doit etre en methode de class! car elle doit verifier la
	 * coherence de la date Avant de la cr�er
	 */
	public static boolean validate(int jour, int mois, int annee) {
		boolean apres = dateApresGregorien(jour, mois, annee);
		if (apres) {
			int max = nbMax(mois, annee);
			return jour <= max;
		} else
			return false;
	}

	private static boolean dateApresGregorien(int jour, int mois, int annee) {
		if (annee > 1582)
			return true;
		if (annee < 1582)
			return false;

		if (mois > 10)
			return true;
		if (mois < 10)
			return false;

		if (jour > 14)
			return true;
		return false;
	}

	/**
	 * retourne le nombre de jours selon le mois et l'annee choisis comme nbMax est
	 * appel� par validate(qui est static) doit etre static
	 */
	public static int nbMax(int mois, int annee) {
		switch (mois) {
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			if (bissextile(annee))
				return 29;
			else
				return 28;
		default:
			return 31;
		}
	}

	/**
	 * methode booleene verifie si annee est bisextile ou non
	 * 
	 * @param a
	 *            annee
	 * @return true si bissextile
	 */
	private static boolean bissextile(int a) {
		return ((a % 100 != 0) && (a % 4 == 0));
	}

	/**
	 * modifie l'objet courant pour pointer vers le jour d'avant
	 */
	public void hier() {
		jour--;
		// si le jour est le premier du mois on d�cr�mente le mois et on passe le jour
		// au jour max du mois precedent
		if (jour < 1) {
			mois--;
			jour = nbMax(mois, annee);
			// si le jour est 01/01 on passe le mois a decembre et on decremente l'ann�e
			if (mois == 1) {
				mois = 12;
				annee--;
			}
		}
	}

	/**
	 * Applique la methode hier() a l'objet courant et retourne une nouvelle
	 * instance
	 * 
	 * @return
	 */
	public DateUser hier2() {
		DateUser dat = new DateUser(jour, mois, annee);
		dat.hier();
		return dat;
	}

	/**
	 * modifie l'objet courant pour pointer vers le jour d'apres
	 */
	public void lendemain() {
		jour++;
		// si le jour est le dernier du mois, on incremente le mois et on passe le jour
		// a 1
		if (jour > nbMax(mois, annee)) {
			jour = 1;
			mois++;
			// si le jour est le 31 decembre, on incremente l'annee et on passe le mois a 1
			if (mois > 12) {
				mois = 1;
				annee++;
			}
		}
	}

	/**
	 * lendemain 2 soluce prof
	 */
	public DateUser lendemain2() {
		DateUser dat = new DateUser(jour, mois, annee);
		dat.lendemain();
		return dat;
	}

	/**
	 * methode compare, les ann�es, puis les mois, puis les jours de l'objet courant
	 * et de Calendar d
	 * 
	 * @param d
	 * @return
	 */
	public boolean avant2(DateUser dat2) {
		if (dat2.getAnnee() < this.annee)
			return false;
		else if (dat2.getAnnee() > this.annee)
			return true;

		if (dat2.getMois() < this.mois)
			return false;
		else if (dat2.getMois() > this.mois)
			return true;

		if (dat2.getJour() < this.jour)
			return false;
		else if (dat2.getJour() > this.jour)
			return true;
		return false;

	}

	/**
	 * correction prof
	 */
	public int age2() {
		DateUser today = new DateUser();

		int age = today.getAnnee() - this.annee;

		if (today.getMois() > this.mois)
			age--;
		else if (today.getMois() == this.getMois() && today.getJour() > this.getJour())
			age--;

		return age;
	}

	/**
	 * retourn l'age de la date point�e par this � la date du jour
	 * 
	 * @return
	 */
	public int age() {
		DateUser tmp = new DateUser();
		int age = tmp.getAnnee() - this.getAnnee();
		if (this.getMois() > tmp.getMois()) {
			age -= 1;
		} else if (age == 1 && this.getMois() == tmp.getMois() && this.getJour() > tmp.getJour()) {
			age = 0;
		} else if (age == 1 && this.getMois() == tmp.getMois() && this.getJour() < tmp.getJour()) {
			age = 1;
		}
		return age;
	}

	/**
	 * Formule de Zeller
	 */
	public int Zeller() {
		int mz, az, sz, z;
		if (mois > 2)
			mz = mois - 2;
		else {
			mz = mois + 10;
		}
		annee--;
		az = annee % 100;
		sz = (int) annee / 100;

		z = ((int) (2.6 * mz - 0.2) + jour + az + (int) (az / 4) + (int) +(sz / 4) - 2 * sz) % 7;
		if (z < 0) {
			z = z + 7;
		}
		return z;
	}

	/**
	 * retourne le jour de la semaine sous forme de String en appelant zeller()
	 * 
	 * @return
	 */
	public String jourDeSemaine() {
		String[] t = { "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi" };
		return t[Zeller()];
	}

	public void ajouterNombreJour(int nbj) {
		this.setJour(nbj + this.getJour());
	}
}
