package modelo;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import Fichas.FichaBarra;
import Fichas.FichaCadrada;

public abstract class Ficha {
	public Xogo xogo;
	
	public ArrayList<Cadrado> cadrados=new ArrayList<Cadrado>();
	
	/**
	 * modifica la cordenada x para mover la figura a la derecha
	 * @return
	 */
	public boolean moveRight() {
		boolean move=false;
		int count=0;
		Iterator it=cadrados.iterator();
		while(it.hasNext()) {
			Cadrado mover=(Cadrado) it.next();
			mover.setX((mover.getX())+xogo.lADO_CADRADO);	
			mover.getLblCadrado().setBounds(mover.getX(), mover.getY(), xogo.lADO_CADRADO, xogo.lADO_CADRADO);
			count++;
		}
		if(count==4) {
			move=true;
		}
		return move;
	}
	
	/**
	 * modifica la cordenada x para mover la figura a la izquierda
	 * @return
	 */
	public boolean moveLeft() {
		boolean move=false;
		int count=0;
		Iterator it=cadrados.iterator();
		while(it.hasNext()) {
			Cadrado mover=(Cadrado) it.next();
			mover.setX((mover.getX())-xogo.lADO_CADRADO);	
			mover.getLblCadrado().setBounds(mover.getX(), mover.getY(), xogo.lADO_CADRADO, xogo.lADO_CADRADO);
			count++;
		}
		if(count==4) {
			move=true;
		}
		return move;
	}
	
	/**
	 * modifica la cordenada y para mover la figura hacia abajo
	 * @return
	 */
	public boolean moveDown() {
		boolean move=false;
		int count=0;
		Iterator it=cadrados.iterator();
		while(it.hasNext()) {
			Cadrado mover=(Cadrado) it.next();
			mover.setY((mover.getY())+xogo.lADO_CADRADO);	
			mover.getLblCadrado().setBounds(mover.getX(), mover.getY(), xogo.lADO_CADRADO, xogo.lADO_CADRADO);
			count++;		
		}
		if(count==4) {
			move=true;
		}
		return move;
	}
 
	/**
	 * rota la figura
	 * @return
	 */
	public abstract boolean rotate();
}
