package Fichas;
import java.awt.Color;

import modelo.Cadrado;
import modelo.Ficha;
import modelo.Xogo;

public class FichaBarra extends Ficha {
	Cadrado square_0;
	Cadrado square_1;
	Cadrado square_2;
	Cadrado square_3;
	private int counter=0;
	
	public FichaBarra() {
		square_0=new Cadrado(Xogo.MAX_X/2-Xogo.lADO_CADRADO,0, Color.YELLOW);
		square_1=new Cadrado(Xogo.MAX_X/2-Xogo.lADO_CADRADO, Xogo.lADO_CADRADO, Color.YELLOW);
		square_2=new Cadrado(Xogo.MAX_X/2-Xogo.lADO_CADRADO, Xogo.lADO_CADRADO*2, Color.YELLOW);
		square_3=new Cadrado(Xogo.MAX_X/2-Xogo.lADO_CADRADO,Xogo.lADO_CADRADO*3 , Color.YELLOW);
		cadrados.add(square_0);
		cadrados.add(square_1);
		cadrados.add(square_2);
		cadrados.add(square_3);
	}
	
	/*
	 * Metodo para rotar la figura 
	 * (non-Javadoc)
	 * @see modelo.Ficha#rotate()
	 */
	@Override
	public boolean rotate() {
		boolean rotate=false;
		if(counter%2==0) {
			square_0.setX(Xogo.MAX_X/2-Xogo.lADO_CADRADO*2);
			square_0.setY(square_0.getY());
			square_1.setX(Xogo.MAX_X/2-Xogo.lADO_CADRADO);
			square_1.setY(square_0.getY());
			square_2.setX(Xogo.MAX_X/2);
			square_2.setY(square_1.getY());
			square_3.setX(Xogo.MAX_X/2+Xogo.lADO_CADRADO);
			square_3.setY(square_2.getY());
			counter++;
			rotate=true;
		}else {
			square_0.setX(square_0.getX()+xogo.lADO_CADRADO);
			square_0.setY(square_0.getY()-xogo.lADO_CADRADO);
			square_1.setX(Xogo.MAX_X/2-Xogo.lADO_CADRADO);
			square_1.setY(square_1.getY());
			square_2.setX(square_2.getX()-xogo.lADO_CADRADO);
			square_2.setY(square_2.getY()+xogo.lADO_CADRADO);
			square_3.setX(square_3.getX()-Xogo.lADO_CADRADO*2);
			square_3.setY(square_3.getY()+xogo.lADO_CADRADO*2);
			counter++;
			rotate=true;
		}
		return rotate;
	}
	
	
}
