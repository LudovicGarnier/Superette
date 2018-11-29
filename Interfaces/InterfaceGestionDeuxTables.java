package Interfaces;

import MesExceptions.ErreurSaisieException;

public interface InterfaceGestionDeuxTables<TypeTable1, TypeTable2> {

	public void menuGeneral(TypeTable1 tab1, TypeTable2 tab2) throws ErreurSaisieException;

	public int menuChoix() throws ErreurSaisieException;

	public void creer(TypeTable1 tab1, TypeTable2 tab2) throws ErreurSaisieException;

	public void supprimer(TypeTable1 tab1) throws ErreurSaisieException;

	public void affiche(TypeTable1 tab1);

}
