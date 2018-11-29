package IPane;

import java.awt.Color;
import javax.swing.*;

import MesExceptions.ErreurSaisieException;
import MesExceptions.ExceptionDivers;

public class ES {

	static ImageIcon help = new ImageIcon("ressources/help.png");
	static ImageIcon stop = new ImageIcon("ressources/stop.png");

	/**
	 * Methode qui retourne un entier. L'entier doit �tre compris entre les bornes
	 * inf et sup. Le String mes sert d'indication � l'utilisateur
	 * 
	 * @param s
	 * @param titre
	 * @param inf
	 * @param sup
	 * @return
	 * @throws ErreurSaisieException
	 */
	public static int saisie(String s, String titre, int inf, int sup, ImageIcon icon) throws ErreurSaisieException {
		int val = 0;
		do {
			String mes = (String) JOptionPane.showInputDialog(null, s, "Superette" + titre, JOptionPane.PLAIN_MESSAGE,
					icon, null, null);
			try {
				val = Integer.parseInt(mes);
				if (val < inf || val > sup)
					throw new ExceptionDivers("Veuillez saisir un nombre compris entre " + inf + " et " + sup
							+ " , réessayez s'il vous plaît :");
				else
					break;
			} catch (NumberFormatException e) {
				if (mes == null) {
					int abandon = saisieConfirmation("Voulez-vous quitter la Superette ?", "Superette" + titre, help);
					if (abandon == 0)
						System.exit(0);
				} else {
					if (mes.equals("")) {
						JOptionPane.showMessageDialog(null, "Vous n'avez rien saisi, réessayez s'il vous plaît :",
								"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
					} else {
						JOptionPane.showMessageDialog(null,
								"Veuillez saisir un nombre entre " + inf + " et " + sup
										+ ", réessayez s'il vous plaît :",
								"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
					}
				}
			} catch (ExceptionDivers e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Superette - Erreur de Saisie !",
						JOptionPane.ERROR_MESSAGE, stop);

			}
		} while (true);
		return val;
	}

	/**
	 * M�thode permettant la saisie d'un r�el. Cet entier doit �tre n�cessairement
	 * sup�rieur au r�el inf
	 * 
	 * @param s
	 * @param titre
	 * @param inf
	 * @return
	 * @throws ErreurSaisieException
	 */
	public static double saisie(String s, String titre, double inf, ImageIcon icon) throws ErreurSaisieException {
		double val = 0.0;
		do {
			String mes = (String) JOptionPane.showInputDialog(null, s, "Superette" + titre, JOptionPane.PLAIN_MESSAGE,
					icon, null, null);
			try {
				if (mes == null) {
					int abandon = saisieConfirmation("Voulez-vous quitter la Superette ?", "Superette" + titre, help);
					if (abandon == 0)
						System.exit(0);
				} else {
					val = Double.parseDouble(mes);
					if (val < inf)
						throw new ExceptionDivers(
								"Veuillez saisir un nombre supérieur à " + inf + ", réessayez s'il vous plaît :");
					else
						break;
				}
			} catch (NumberFormatException e) {
				if (mes.equals("")) {
					JOptionPane.showMessageDialog(null, "Vous n'avez rien saisi, réessayez s'il vous plaît :",
							"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
				} else {
					JOptionPane.showMessageDialog(null,
							"Veuillez saisir un nombre supérieur à " + inf + ", réessayez s'il vous plaît :",
							"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
				}
			} catch (ExceptionDivers e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Superette - Erreur de Saisie !",
						JOptionPane.ERROR_MESSAGE, stop);
			}
		} while (true);
		return val;

	}

	/**
	 * M�thode permettant la saisie d'une cha�ne de caract�res.
	 * 
	 * @param mes
	 * @param titre
	 * @return
	 * @throws ErreurSaisieException
	 */
	public static String saisie(String mes, String titre, ImageIcon icon) throws ErreurSaisieException {
		String s = "";
		do {
			s = (String) JOptionPane.showInputDialog(null, mes, "Superette" + titre, JOptionPane.PLAIN_MESSAGE, icon,
					null, null);
			try {
				if (s == null) {
					int abandon = saisieConfirmation("Voulez-vous quitter la Superette ?", "Superette" + titre, help);
					if (abandon == 0)
						System.exit(0);
				} else if (s.equals("") || s.charAt(0) == ' ') {
					throw new ExceptionDivers("Veuillez remplir le champs, s'il vous plaît");
				} else {
					break;
				}
			} catch (ExceptionDivers e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Superette - Erreur de Saisie !",
						JOptionPane.ERROR_MESSAGE, stop);
			}
		} while (true);
		return s;

	}

	public static double saisie(String s, String titre, double inf, double sup, ImageIcon icon)
			throws ErreurSaisieException {
		double val = 0.0;
		do {
			String mes = (String) JOptionPane.showInputDialog(null, s, "Superette" + titre, JOptionPane.PLAIN_MESSAGE,
					icon, null, null);
			try {
				val = Double.parseDouble(mes);
				if (val < inf || val > sup)
					throw new ExceptionDivers("Veuillez saisir un nombre compris entre " + inf + " et " + sup
							+ " , réessayez s'il vous plaît :");
				else
					break;
			} catch (NumberFormatException e) {
				if (mes == null) {
					int abandon = saisieConfirmation("Voulez-vous quitter la Superette ?", "Superette" + titre, help);
					if (abandon == 0)
						System.exit(0);
				} else {
					if (mes.equals("")) {
						JOptionPane.showMessageDialog(null, "Vous n'avez rien saisi, réessayez s'il vous plaît :",
								"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
					} else {
						JOptionPane.showMessageDialog(null,
								"Veuillez saisir un nombre compris entre " + inf + " et " + sup
										+ " , réessayez s'il vous plaît :",
								"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
					}
				}
			} catch (ExceptionDivers e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Superette - Erreur de Saisie !",
						JOptionPane.ERROR_MESSAGE, stop);

			}
		} while (true);
		return val;
	}

	/**
	 * M�thode permettant la saisie d'un entier. Cet entier doit �tre n�cessairement
	 * sup�rieur � l'entier inf
	 * 
	 * @param s
	 * @param titre
	 * @param inf
	 * @return
	 * @throws ErreurSaisieException
	 */
	public static int saisie(String s, String titre, int inf, ImageIcon icon) throws ErreurSaisieException {
		int val = 0;
		do {
			String mes = (String) JOptionPane.showInputDialog(null, s, "Superette" + titre, JOptionPane.PLAIN_MESSAGE,
					icon, null, null);
			try {
				val = Integer.parseInt(mes);
				if (val < inf)
					throw new ExceptionDivers(
							"Veuillez saisir un nombre supèrieur à " + inf + ", réessayez s'il vous plaît :");
				else
					break;
			} catch (NumberFormatException e) {
				if (mes == null) {
					int abandon = saisieConfirmation("Voulez-vous quitter la Superette ?", "Superette" + titre, help);
					if (abandon == 0)
						System.exit(0);
				} else {
					if (mes.equals("")) {
						JOptionPane.showMessageDialog(null, "Vous n'avez rien saisi, réessayez s'il vous plaît :",
								"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
					} else {
						JOptionPane.showMessageDialog(null,
								"Veuillez saisir un nombre sup�rieur � " + inf + ", réessayez s'il vous plaît :",
								"Superette - Erreur de Saisie !", JOptionPane.ERROR_MESSAGE, stop);
					}
				}
			} catch (ExceptionDivers e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Superette - Erreur de Saisie !",
						JOptionPane.ERROR_MESSAGE, stop);

			}
		} while (true);
		return val;
	}

	/**
	 * Retourne true si la saisie clavier est 'o' ou 'O'. Retourne false si la
	 * saisie clavier est 'n' ou 'N'. Sinon, un message demandant de recommencer est
	 * affich�
	 * 
	 * @param s
	 * @param titre
	 * @param icon
	 * @return
	 * @throws ErreurSaisieException
	 */
	public static boolean saisieOuiNon(String s, String titre, ImageIcon icon) throws ErreurSaisieException {

		do {
			try {
				char rep = ES.saisie(s, titre, icon).charAt(0);
				if (rep == 'N' || rep == 'n')
					return false;
				else if (rep == 'O' || rep == 'o') {
					return true;
				} else
					throw new ErreurSaisieException(
							"Veuillez r�pondre par Oui ou par Non (O/N), réessayez s'il vous plaît");
			} catch (ErreurSaisieException e) {
			}
		} while (true);
	}

	/**
	 * Retourne l'entier correspondant � la valeur retourn�e par le
	 * showConfirmDialog
	 * 
	 * @param mes
	 * @param titre
	 * @return
	 * @throws ErreurSaisieException
	 */
	public static int saisieConfirmation(String mes, String titre, ImageIcon icon) throws ErreurSaisieException {
		int rep = JOptionPane.showConfirmDialog(null, mes, "Superette" + titre, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, icon);
		return rep;
	}

	/**
	 * M�thode affichant la cha�ne de caract�res entr�e en param�tre.
	 * 
	 * @param mes
	 * @param titre
	 */
	public static void affiche(String mes, String titre, ImageIcon icon) {
		JOptionPane.showMessageDialog(null, mes, "Superette" + titre, JOptionPane.INFORMATION_MESSAGE, icon);
	}

	public static void afficheListing(String mes, String titre, ImageIcon icon) {
		JTextArea txt = new JTextArea();
		txt.setBackground(new Color(222, 222, 222));
		txt.append(mes);
		txt.setEditable(false);
		JOptionPane.showMessageDialog(null, txt, "Superette" + titre, JOptionPane.PLAIN_MESSAGE, icon);
	}

	public static String aligner(String tmp, int longueur) {
		String s = String.format("%-" + longueur + "s", tmp);
		int taille = tmp.length();
		if (taille <= longueur) {
			taille = longueur - taille;
			for (int i = 0; i < taille; i++) {
				s += " ";
			}
		}
		return s;
	}

}
