package utils;

import java.awt.Image;
import java.math.BigDecimal;

import javax.swing.ImageIcon;

public class Tools {
	public static double arrondir(double d, int taille) {
		BigDecimal bigD = new BigDecimal(d);
		bigD = bigD.setScale(taille, BigDecimal.ROUND_DOWN);
		double arrondis = bigD.doubleValue();
		return arrondis;
	}
	
	public static ImageIcon getPic(String chemin) {
		return new ImageIcon(Image.class.getResource(chemin));
	}
}
