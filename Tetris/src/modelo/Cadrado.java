 package modelo;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

public class Cadrado {
	/**
	 * Constructor sobrecargado
	 * @param x
	 * @param y
	 * @param colorBackgraund
	 */
	public Cadrado(int x,int y,Color colorBackgraund) {
		this.x=x;
		this.y=y;
		this.corRecheo=colorBackgraund;
		lblCadrado=new JLabel();
		 lblCadrado.setBackground(corRecheo);
		 lblCadrado.setBounds(getX(), getY(), Xogo.lADO_CADRADO, Xogo.lADO_CADRADO);
		 lblCadrado.setBorder(BorderFactory.createBevelBorder(SoftBevelBorder.LOWERED));
		 lblCadrado.setOpaque(true);
		 lblCadrado.setVisible(true);
	}
	
 //position x and y 
	private int x,y;
	private Color corRecheo;
	private JLabel lblCadrado;
	
 //getters and setters
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getCorRecheo() {
		return corRecheo;
	}
	
	public void setCorRecheo(Color corRecheo) {
		this.corRecheo = corRecheo;
	}

	public JLabel getLblCadrado() {
		return lblCadrado;
	}

	public void setLblCadrado(JLabel lblCadrado) {
		this.lblCadrado = lblCadrado;
	}
	

//metodos
	public String getCoordenadas() {
		return getX()+","+getY();
	}
 
 /**
  * metodo que establece las propiedades del cuadrado
  */
	/*
 private void settingSquare() {
	 lblCadrado=new JLabel();
	 lblCadrado.setBackground(corRecheo);
	 lblCadrado.setBounds(x, y, Xogo.lADO_CADRADO, Xogo.lADO_CADRADO);
	 lblCadrado.setBorder(BorderFactory.createBevelBorder(SoftBevelBorder.LOWERED));
	 lblCadrado.setOpaque(true);
	 lblCadrado.setVisible(true);
 }*/
 
}
