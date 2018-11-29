package Interfaces;

import MesExceptions.ErreurSaisieException;

public interface InterfaceGestionUneTable<TableType1, TableType2> {

	public void menuGeneral(TableType1 tab1, TableType2 tab2) throws ErreurSaisieException;

	public int menuChoix() throws ErreurSaisieException;

	public void creer(TableType1 tab1) throws ErreurSaisieException;

	public void supprimer(TableType1 tab1, TableType2 tab2) throws ErreurSaisieException;

	public void affiche(TableType1 tab1);

}
