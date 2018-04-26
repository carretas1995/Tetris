package modelo;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.org.apache.regexp.internal.recompile;
import com.sun.prism.paint.Color;

import Fichas.FichaBarra;
import Fichas.FichaCadrada;
import Fichas.FichaL;
import Fichas.FichaT;
import Fichas.FichaZ;
import iu.VentanaPrincipal;

public class Xogo {
	public Xogo(VentanaPrincipal ventana) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		this.ventanaPrincipal=ventana;
		this.generateNewFile();
	}
	
	public static final int lADO_CADRADO=20;
	public static final int MAX_X=400;
	public static final int MAX_Y=600;
	private boolean pausa;
	private int numeroLinias;
	private VentanaPrincipal ventanaPrincipal;
	private Ficha currentFile;
	private int numberLines=4;
	private int level=6;
//-------sonido----------
  //--eliminar linea--	
	private AudioInputStream soundDeleteLine;
	private Clip clipDeleteLine;
  //--partida ganada--	
	private AudioInputStream soundGameWin;
	public Clip clipGameWin;
  //--partida perdida--	
	private AudioInputStream soundGameOver;
	public Clip clipGameOver;
//-----------------------
  
	public ArrayList<Cadrado> cadradosChan=new ArrayList<Cadrado>();
	
//getters and setters
	public boolean isPausa() {
		return pausa;
	}

	public void setPausa(boolean pausa) {
		this.pausa = pausa;
	}

	public int getNumeroLinias() {
		return numeroLinias;
	}

	public void setNumeroLinias(int numeroLinias) {
		this.numeroLinias = numeroLinias;
	}

	public static int getMaxX() {
		return MAX_X;
	}

	public static int getMaxY() {
		return MAX_Y;
	}

//Metodos----------------------------------
	
	/**
	 * mueve la ficha hacia la derecha
	 */
	public void moveFileRight() {
		boolean mover=false;
		int count=0;
		Iterator it=currentFile.cadrados.iterator();
		while(it.hasNext()) {
			Cadrado square=(Cadrado) it.next();
			if(eValidPosition(square.getX()+lADO_CADRADO,square.getY())) {
				count++;				
			}
			if(count==4) {
				currentFile.moveRight();
				this.paintFile(currentFile);
			}
		}
	}
	
	/**
	 * mueve la ficha hacia la izquierda
	 */
	public void moveFileLeft() {
		int count=0;
		Iterator it=currentFile.cadrados.iterator();
		while(it.hasNext()) {
			Cadrado square=(Cadrado) it.next();
			if(eValidPosition(square.getX()-lADO_CADRADO,square.getY())) {
				count++;
			}	
			if(count==4) {
				currentFile.moveLeft();
				this.paintFile(currentFile);
			}
		}
			
	}
	
	/**
	 * roto la ficha
	 */
	public void rotateFile() {
		currentFile.rotate();
		this.paintFile(currentFile);
	}
	
	/**
	 * mueve la ficha hacia la abajo si no choca contra el suelo o contra otra ficha
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	public void moveFileDown() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		int count=0;
	//	hitFileCeiling();
		if(chocaFichaCoChan()==false) { //si la ficha llega al suelo no deja seguir
			Iterator it=currentFile.cadrados.iterator();
			while(it.hasNext()) {
				Cadrado square=(Cadrado) it.next();
					if(eValidPosition(square.getX(),(square.getY()+lADO_CADRADO))) {
						count++;
					}
			}
			if(count==4) {
				currentFile.moveDown();
				this.paintFile(currentFile);
			}else {
				engadirFichaAoChan();
				deleteCompleteLines(); //compruebo si se puede eliminar una fila
			}
		}
	}
	
	
	/**
	 * comprueba si la posicion de la ficha es valida
	 * @param x eje x
	 * @param y eje y
	 */
	private boolean eValidPosition(int x, int y) {
		boolean valid=true;
		if((x)<0) {  //limite izquierdo
			valid=false;
		}
		if((x)==MAX_X){ //limite derecho
			valid=false;
		}
		Iterator it=cadradosChan.iterator(); //compruebo si una ficha choca contra otra
		while(it.hasNext()) {
			Cadrado square=(Cadrado) it.next();
			if(x==square.getX()&&y==square.getY()) {
				valid=false;
			}
		}
		return valid;
	}

	/**
	 * genero las fichas de manera aleatoria
	 */
	private void generateNewFile() {
		
		Random r=new Random();
		int fileRandom=r.nextInt(5);
		switch (fileRandom) {
		case 0:
			FichaCadrada fileSquare=new FichaCadrada();
			currentFile=fileSquare;
			break;
		case 1:
			FichaBarra fileBar=new FichaBarra();
			currentFile=fileBar;
			break;
		case 2:
			FichaL fileL=new FichaL();
			currentFile=fileL;
			break;
		case 3:
			FichaT fileT=new FichaT();
			currentFile=fileT;
			break;
		case 4:
			FichaZ fileZ=new FichaZ();
			currentFile=fileZ;
			break;
		default:
			break;
		}
		
		this.paintFile(currentFile);

	}
	
	
	/**
	 * añade la ficha que toca el suelo u otra ficha al arraylist y genera una nueva ficha
	 * @param q
	 * @throws IOException 
	 */
	private void engadirFichaAoChan() throws IOException {
		Iterator it=currentFile.cadrados.iterator();
		while(it.hasNext()) {
			Cadrado square=(Cadrado) it.next();
				cadradosChan.add(square);
			}
		hitFileCeiling();//Compruebo si la ficha toca el techo
		generateNewFile();
	}
	
	
	/**
	 * Metodo que al completar una fila de fichas elimina la primera y aumenta el nivel
	 * se que una fila llena son 20 cuadrados
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	private void deleteCompleteLines() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		int count=0;
		Iterator it=cadradosChan.iterator();
		while(it.hasNext()) {
			Cadrado square=(Cadrado) it.next();
			if(square.getY()+lADO_CADRADO==MAX_Y) {
				count++;
			}
		}
		if(count==20) {//una fila completa son 20 cuadrados
			deleteLine();
		}		
	}
	
	/**
	 * metodo que elimina los cuadrados de la fila eliminada
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	private void deleteLine() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		Iterator it=cadradosChan.iterator();
		while(it.hasNext()) {
			Cadrado square=(Cadrado) it.next();
			if(square.getY()+lADO_CADRADO==MAX_Y) {
				it.remove(); //elimino el cuadrado de la coleccion
				ventanaPrincipal.deleteSquare(square.getLblCadrado()); //elimino el cuadrado del panel
			}
			//bajo los cuadrados que estan encima
			square.setY((square.getY())+lADO_CADRADO);	
			square.getLblCadrado().setBounds(square.getX(), square.getY(), lADO_CADRADO, lADO_CADRADO);
		}
		createSoundDeleteLine(); //metodo que crea y reproduce el sonido
		levels(); //actualizo el numero de lineas y compruebo el nivel del juego
	}
	
	/**
	 * suma una linea al marcador y actualiza el nivel del juego de 5 en cinco
	 * Se establecera el nivel segun la dificultad elegida por el usuario
	 * Nivel maximo 7, despues de este el jugador a ganado
	 * @return
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	private boolean levels() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		boolean ok=true;
		numberLines++; //sumo una linea
		ventanaPrincipal.toShowNumberLines(numberLines); //actualizo el valor del jlabel de lineas
			if(numberLines%5==0) {
				level++; 
				ventanaPrincipal.toShowNumberlLevel(level); //actualizo el valor del jlabel de levels
				if(ventanaPrincipal.getSelectInitialLevel()==1) { //nivel facil
					switch (level) {
					case 1:
						ventanaPrincipal.changeDificult(700);
						break;
					case 2:
						ventanaPrincipal.changeDificult(550);
						break;
					case 3:
						ventanaPrincipal.changeDificult(350);
						break;
					case 4:
						ventanaPrincipal.changeDificult(250);
						break;
					case 5:
						ventanaPrincipal.changeDificult(200);
						break;
					case 6:
						ventanaPrincipal.changeDificult(150);
						break;
					case 7:
						ventanaPrincipal.changeDificult(100);
						break;
					default:
						break;
					}
				}
				
				if(ventanaPrincipal.getSelectInitialLevel()==2) { //nivel normal
					switch (level) {
					case 1:
						ventanaPrincipal.changeDificult(410);
						break;
					case 2:
						ventanaPrincipal.changeDificult(370);
						break;
					case 3:
						ventanaPrincipal.changeDificult(330);
						break;
					case 4:
						ventanaPrincipal.changeDificult(250);
						break;
					case 5:
						ventanaPrincipal.changeDificult(140);
						break;
					case 6:
						ventanaPrincipal.changeDificult(90);
						break;
					case 7:
						ventanaPrincipal.changeDificult(70);
						break;
					default:
						break;
					}
				}
				
				if(ventanaPrincipal.getSelectInitialLevel()==3) { //nivel experto
					switch (level) {
					case 1:
						ventanaPrincipal.changeDificult(200);
						break;
					case 2:
						ventanaPrincipal.changeDificult(170);
						break;
					case 3:
						ventanaPrincipal.changeDificult(140);
						break;
					case 4:
						ventanaPrincipal.changeDificult(110);
						break;
					case 5:
						ventanaPrincipal.changeDificult(90);
						break;
					case 6:
						ventanaPrincipal.changeDificult(60);
						break;
					case 7:
						ventanaPrincipal.changeDificult(50);
						break;
					default:
						break;
					}
				}
			}
			if(level==8) { //si obtiene el nivel maximo se gana el juego
				ventanaPrincipal.toShowWinGame();
			}
		return ok;
	}
	
	/**
	 * comprueba si la ficha supera el limite superior del tablero
	 * en caso afirmativo el usuario pierde la partida
	 * @throws IOException 
	 */
	private void hitFileCeiling() throws IOException {
		boolean hit=false;
		Iterator it=currentFile.cadrados.iterator();
		while(it.hasNext()) {
			Cadrado square=(Cadrado) it.next();
			if((square.getY()-lADO_CADRADO)<0){
				hit=true;                                  
	    	}
		}
		if(hit==true) { //si la ficha llega arriba de todo el jugador pierde
			ventanaPrincipal.toShowTheEndGame();
		}
	}
	
	/**
	 * Detectamos cuando la ficha toque el suelo
	 * @return
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	private boolean chocaFichaCoChan() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		boolean floor=false;
		try {
			Iterator it=currentFile.cadrados.iterator();
			while(it.hasNext()) {
				Cadrado square=(Cadrado) it.next();
				if((square.getY()+lADO_CADRADO)==MAX_Y){
					floor=true;                                  
		    	}
			}
			if(floor==true) { //Si la ficha llega al suelo se añade la ficha actual a la coleccion
				engadirFichaAoChan();
				deleteCompleteLines(); //compruebo si se puede eliminar una fila
			}
		}catch (Exception e) {writeException("class: xogo,Method: chocaFichaCoChan, error: "+e.toString());}
		return floor;
	}
	
	/**
	 * Metodo para mostrar la ficha en el tablero de juego
	 * @param f
	 */
	private void paintFile(Ficha f) {
		Iterator it=f.cadrados.iterator();
		while(it.hasNext()) {
			Cadrado newFich=(Cadrado) it.next();
			ventanaPrincipal.printsquare(newFich.getLblCadrado());
		}
	}
	
	/**
	 * crea el sonido de fila eliminada y lo reproduce en cada llamada
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	private void createSoundDeleteLine() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		try {
			soundDeleteLine=AudioSystem.getAudioInputStream(new File("resources/Music/new_line.wav"));
			clipDeleteLine=AudioSystem.getClip();
			clipDeleteLine.open(soundDeleteLine);
			clipDeleteLine.start();
		}catch (Exception e) {writeException("class: xogo,Method: createSoundDeleteLine, error: "+e.toString());}
	}
	
	/**
	 * creo el sonido de partida ganada
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public void createSoundWin() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		try {
			soundGameWin=AudioSystem.getAudioInputStream(new File("resources/Music/gameWin.wav"));
			clipGameWin=AudioSystem.getClip();
			clipGameWin.open(soundGameWin);
			clipGameWin.start();
		}catch (Exception e) {writeException("class: xogo,Method: createSoundWin, error: "+e.toString());}	
	}
	
	/**
	 * creo el sonido de partida perdida
	 * @throws IOException
	 */
	public void createSoundGameOver() throws IOException {
		try {
			soundGameOver=AudioSystem.getAudioInputStream(new File("resources/Music/game_over.wav"));
			clipGameOver=AudioSystem.getClip();
			clipGameOver.open(soundGameOver);
			clipGameOver.start();
		}catch (Exception e) {writeException("class: xogo,Method: createSounfGameOver, error: "+e.toString());}	
	}
	
	/**
	 * Metodo para guardar los errores en logs
	 * @param excepcion
	 * @throws IOException
	 */
	public void writeException(String exception) throws IOException{
		FileWriter file=null;
		Date date=new Date(System.currentTimeMillis());
		try {
			file = new FileWriter("logs/log_principalWindow.txt",true);
			file.write("->"+exception+", "+date+"\r\n");
		}catch (FileNotFoundException e) {}finally {
				file.close();
		}
	}
}
