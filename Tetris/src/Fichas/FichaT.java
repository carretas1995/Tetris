package Fichas;

import java.awt.Color;

import modelo.Cadrado;
import modelo.Ficha;
import modelo.Xogo;

public class FichaT extends Ficha {
	private Cadrado square_0;
	private Cadrado square_1;
	private Cadrado square_2;
	private Cadrado square_3;
	private int counter=1;
	
	public FichaT() {
		square_0=new Cadrado(Xogo.MAX_X/2-xogo.lADO_CADRADO,0, Color.RED);
		square_1=new Cadrado(Xogo.MAX_X/2-xogo.lADO_CADRADO, Xogo.lADO_CADRADO, Color.RED);
		square_2=new Cadrado(Xogo.MAX_X/2-xogo.lADO_CADRADO, Xogo.lADO_CADRADO*2, Color.RED);
		square_3=new Cadrado(Xogo.MAX_X/2, Xogo.lADO_CADRADO, Color.RED);
		cadrados.add(square_0);
		cadrados.add(square_1);
		cadrados.add(square_2);
		cadrados.add(square_3);
	}

	@Override
	public boolean rotate() {
		boolean rotate=false;
		if(counter==1) {
			square_0.setX(square_0.getX()-xogo.lADO_CADRADO);
			square_0.setY(square_0.getY()+xogo.lADO_CADRADO);
			square_1.setX(square_1.getX());
			square_1.setY(square_1.getY());
			square_2.setX(square_2.getX());
			square_2.setY(square_2.getY());
			square_3.setX(square_3.getX());
			square_3.setY(square_3.getY());
			counter++;
			rotate=true;
		}else if(counter==2) {
			square_0.setX(square_0.getX()+xogo.lADO_CADRADO);
			square_0.setY(square_0.getY()-xogo.lADO_CADRADO);
			square_1.setX(square_1.getX());
			square_1.setY(square_1.getY());
			square_2.setX(square_2.getX());
			square_2.setY(square_2.getY());
			square_3.setX(square_3.getX()-xogo.lADO_CADRADO*2);
			square_3.setY(square_3.getY());
			counter++;
			rotate=true;
		}else if(counter==3) {
			square_0.setX(square_0.getX()-xogo.lADO_CADRADO);
			square_0.setY(square_0.getY()+xogo.lADO_CADRADO);
			square_1.setX(square_1.getX());
			square_1.setY(square_1.getY());
			square_2.setX(square_2.getX()+xogo.lADO_CADRADO);
			square_2.setY(square_2.getY()-xogo.lADO_CADRADO);
			square_3.setX(square_3.getX()+xogo.lADO_CADRADO);
			square_3.setY(square_3.getY()-xogo.lADO_CADRADO);
			counter++;
			rotate=true;
		}else if(counter==4) {
			square_0.setX(square_0.getX()+xogo.lADO_CADRADO);
			square_0.setY(square_0.getY()-xogo.lADO_CADRADO);
			square_1.setX(square_1.getX());
			square_1.setY(square_1.getY());
			square_2.setX(square_2.getX()-xogo.lADO_CADRADO);
			square_2.setY(square_2.getY()+xogo.lADO_CADRADO);
			square_3.setX(square_3.getX()+xogo.lADO_CADRADO);
			square_3.setY(square_3.getY()+xogo.lADO_CADRADO);
			counter=1;
			rotate=true;
		}
		return rotate;
	}
}
