 package Fichas;
import java.awt.Color;

import javax.swing.JLabel;

import modelo.Cadrado;
import modelo.Ficha;
import modelo.Xogo;

public class FichaCadrada extends Ficha{
	public FichaCadrada() {
		Cadrado square_0=new Cadrado(Xogo.MAX_X/2-Xogo.lADO_CADRADO,0, Color.BLUE);
		Cadrado square_1=new Cadrado(Xogo.MAX_X/2, 0, Color.BLUE);
		Cadrado square_2=new Cadrado(Xogo.MAX_X/2-Xogo.lADO_CADRADO, Xogo.lADO_CADRADO, Color.BLUE);
		Cadrado square_3=new Cadrado(Xogo.MAX_X/2, Xogo.lADO_CADRADO, Color.BLUE);
		cadrados.add(square_0);
		cadrados.add(square_1);
		cadrados.add(square_2);
		cadrados.add(square_3);

	}

	@Override
	public boolean rotate() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
