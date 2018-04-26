package iu;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import modelo.Xogo;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Font;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.DebugGraphics;

import java.text.SimpleDateFormat;



public class VentanaPrincipal  extends JFrame {
//atributos
	private JPanel contentPane;
	public JPanel panel;
	private JButton buttonPause;
	private JLabel numberLines;
	private JLabel numberLevel;
	private JLabel timePanel;
	private JPanel panelWin;
	private JPanel panel_GameOver;
	private JLabel labelWinTime;
	private JLabel TimeGameOver;
	private JLabel labelLevelWin;
	private JLabel levelGameOver;
	private Xogo xogo;
	private int selectInitialLevel; //se almacena el nivel de dificultad elegida por el usuario
//-------sonido----------
	private AudioInputStream audioInputStream;
	private Clip sonido;
//-----------------------
	private int horas=0, minutos=0, segundos=0;
	private int levelDefault=900;
	//public int tmp=0;
//------timer para mover las fichas hacia abajo automaticamente
	private Timer timer=new Timer(levelDefault,  new ActionListener() { //mueve automaticamente las fichas hacia abajo
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				xogo.moveFileDown();
			} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
			
			}
		}
	});
	
//------timer para el cronometro
	private Timer cronometro=new Timer(1000,  new ActionListener() {  //inicia el cronometro del tiempo
		@Override
		public void actionPerformed(ActionEvent e) {
			cronometro();
		}
	});
	

//getters and setters
	public int getSelectInitialLevel() {
		return selectInitialLevel;
	}

	public void setSelectInitialLevel(int selectInitialLevel) {
		this.selectInitialLevel = selectInitialLevel;
	}
	
//metodos-----------------------------------------
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
					frame.setFocusable(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * 
	 * Metodo que crea la ventana principal de la aplicacion
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 */
	public VentanaPrincipal() throws LineUnavailableException, IOException {
		addKeyListener(new KeyAdapter() { //detecto las pulsaciones del teclado para interactuar con el juego
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==e.VK_D){
                    xogo.moveFileRight();
                }
				if(e.getKeyCode()==e.VK_A){
                    xogo.moveFileLeft();
                }
				if(e.getKeyCode()==e.VK_S){
                    try {
						xogo.moveFileDown();
					} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {}
                }
				if(e.getKeyCode()==e.VK_SPACE){
                    xogo.rotateFile();
                }
				if(e.getKeyCode()==e.VK_F2){
					openWindowSetting();
                }
			}
		});
		setResizable(false);
		setFont(new Font("Eras Bold ITC", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources\\Img\\Logo_aplicacion2.png"));
		setTitle("Tetris remastered");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 584, 764);
		Color fondoPantalla=Color.decode("#282828");
		Color fondo=Color.decode("#00090D");
		
		//Panel base que contiene el resto
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//panel que contiene el tablero de juego y otros paneles informativos
		JPanel centralPanel = new JPanel();
		centralPanel.setVisible(false);
		
		JPanel initialPanel = new JPanel();
		initialPanel.setBounds(0, 0, 578, 735);
		contentPane.add(initialPanel);
		initialPanel.setBackground(fondoPantalla);
		initialPanel.setBorder(new LineBorder(Color.RED, 2));
		initialPanel.setLayout(null);
		
		JButton buttonEasyLevel = new JButton("");
		buttonEasyLevel.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
		buttonEasyLevel.setBorderPainted(false);
		buttonEasyLevel.setBorder(null);
		buttonEasyLevel.setIcon(new ImageIcon("resources\\Img\\boton_inicil_facil.png"));
		buttonEasyLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					changeDificult(900);  //establezco la dificultad
					selectInitialLevel=1; //establezco el nivel inicial
					initialPanel.setVisible(false);
					centralPanel.setVisible(true);
					playGame();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
					
				}
			}
		});
		buttonEasyLevel.setBounds(93, 409, 90, 90);
		initialPanel.add(buttonEasyLevel);
		
		JButton buttonNormalLevel = new JButton("");
		buttonNormalLevel.setBorder(null);
		buttonNormalLevel.setBorderPainted(false);
		buttonNormalLevel.setIcon(new ImageIcon("resources\\Img\\boton_inicil_normal.png"));
		buttonNormalLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					changeDificult(450);
					selectInitialLevel=2;
					initialPanel.setVisible(false);
					centralPanel.setVisible(true);
					playGame();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
					
				}
			}
		});
		buttonNormalLevel.setBounds(238, 409, 90, 90);
		initialPanel.add(buttonNormalLevel);
		
		JButton buttonExpertLevel = new JButton("");
		buttonExpertLevel.setBorder(null);
		buttonExpertLevel.setBorderPainted(false);
		buttonExpertLevel.setIcon(new ImageIcon("resources\\Img\\boton_inicil_experto.png"));
		buttonExpertLevel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					changeDificult(225);
					selectInitialLevel=3;
					initialPanel.setVisible(false);
					centralPanel.setVisible(true);
					playGame();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
					
				}
			}
		});
		buttonExpertLevel.setBounds(380, 409, 90, 90);
		initialPanel.add(buttonExpertLevel);
		
		JLabel imageLogotipe = new JLabel("");
		imageLogotipe.setIcon(new ImageIcon("resources\\Img\\logoInicio.png"));
		imageLogotipe.setBounds(126, 0, 326, 174);
		initialPanel.add(imageLogotipe);
		
		JLabel lblNewLabel_3 = new JLabel("Bienvenido a Tetris Remastered");
		lblNewLabel_3.setForeground(Color.YELLOW);
		lblNewLabel_3.setFont(new Font("Unispace", Font.PLAIN, 26));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(45, 235, 494, 50);
		initialPanel.add(lblNewLabel_3);
		
		JLabel lblParaEmpezarSelecciona = new JLabel("Para empezar selecciona el nivel de dificultad:");
		lblParaEmpezarSelecciona.setHorizontalAlignment(SwingConstants.CENTER);
		lblParaEmpezarSelecciona.setForeground(Color.YELLOW);
		lblParaEmpezarSelecciona.setFont(new Font("Unispace", Font.PLAIN, 13));
		lblParaEmpezarSelecciona.setBounds(45, 296, 396, 50);
		initialPanel.add(lblParaEmpezarSelecciona);
		centralPanel.setBackground(fondoPantalla);
		centralPanel.setBounds(0, 0, 578, 735);
		contentPane.add(centralPanel);
		centralPanel.setLayout(null);
		
		//panel que se muestra al ganar la partida
		panelWin = new JPanel();
		panelWin.setVisible(false);
		
		//panel de partida perdida
		panel_GameOver = new JPanel();
		panel_GameOver.setVisible(false);
		panel_GameOver.setBounds(0, 0, 419, 619);
		panel_GameOver.setLayout(null);
		panel_GameOver.setOpaque(false);
		centralPanel.add(panel_GameOver);
		
		
		JLabel imageRandom = new JLabel("");
		imageRandom.setIcon(new ImageIcon("resources\\Img\\emoticono.png"));
		imageRandom.setBounds(164, 11, 90, 82);
		panel_GameOver.add(imageRandom);
		
		
		JLabel ImageGameOver = new JLabel("");
		ImageGameOver.setIcon(new ImageIcon("resources\\Img\\game_over_insertCoin.png"));
		ImageGameOver.setBounds(175, 230, 77, 66);
		ImageGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		ImageGameOver.setForeground(Color.YELLOW);
		ImageGameOver.setFont(new Font("Unispace", Font.PLAIN, 42));
		panel_GameOver.add(ImageGameOver);
		
		//label que muestra el tiempo cuando se pierde la partida
		TimeGameOver = new JLabel("");
		TimeGameOver.setBounds(120, 308, 191, 20);
		TimeGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		TimeGameOver.setForeground(Color.ORANGE);
		TimeGameOver.setFont(new Font("Unispace", Font.PLAIN, 11));
		panel_GameOver.add(TimeGameOver);
		
		//lavel que muestra el nivel en el cual perdio el usuario
		levelGameOver = new JLabel("");
		levelGameOver.setBounds(225, 339, 24, 20);
		levelGameOver.setHorizontalAlignment(SwingConstants.CENTER);
		levelGameOver.setForeground(Color.ORANGE);
		levelGameOver.setFont(new Font("Unispace", Font.PLAIN, 11));
		panel_GameOver.add(levelGameOver);
		
		JLabel labelLevel = new JLabel("Level:");
		labelLevel.setBounds(187, 339, 44, 20);
		labelLevel.setHorizontalAlignment(SwingConstants.CENTER);
		labelLevel.setForeground(Color.ORANGE);
		labelLevel.setFont(new Font("Unispace", Font.PLAIN, 11));
		panel_GameOver.add(labelLevel);
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(10, 60, 400, 412);
		label_4.setIcon(new ImageIcon("resources\\Img\\arcade-clipart.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_GameOver.add(label_4);
		
		JButton buttonRetryPanelGameOver = new JButton("");
		buttonRetryPanelGameOver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					resetDificult(); //reseteo el nivel de dificultad 
					xogo.clipGameOver.close(); //paro la musica
					panel_GameOver.setVisible(false);
					playGame();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
					
				}
			}
		});
		buttonRetryPanelGameOver.setIcon(new ImageIcon("resources\\Img\\button4.png"));
		buttonRetryPanelGameOver.setBounds(75, 505, 90, 90);
		buttonRetryPanelGameOver.setRequestFocusEnabled(false);
		buttonRetryPanelGameOver.setOpaque(false);
		buttonRetryPanelGameOver.setDefaultCapable(false);
		buttonRetryPanelGameOver.setBorder(null);
		panel_GameOver.add(buttonRetryPanelGameOver);
		
		JButton buttonClosePanleGameOver = new JButton("");
		buttonClosePanleGameOver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultValues(); //reseteo  los valores de pantalla 
				panel_GameOver.setVisible(false);
				defaultValues(); //reseteo  los valores de pantalla
				xogo.clipGameOver.close(); //paro la musica 				
				panel.removeAll();
				panel.setVisible(true);
			}
		});
		buttonClosePanleGameOver.setIcon(new ImageIcon("resources\\Img\\button4.png"));
		buttonClosePanleGameOver.setBounds(245, 505, 90, 90);
		buttonClosePanleGameOver.setRequestFocusEnabled(false);
		buttonClosePanleGameOver.setOpaque(false);
		buttonClosePanleGameOver.setDefaultCapable(false);
		buttonClosePanleGameOver.setBorder(null);
		panel_GameOver.add(buttonClosePanleGameOver);
		
		JLabel labelReintentar = new JLabel("Reintentar");
		labelReintentar.setBounds(75, 479, 90, 20);
		labelReintentar.setHorizontalAlignment(SwingConstants.CENTER);
		labelReintentar.setForeground(Color.YELLOW);
		labelReintentar.setFont(new Font("Unispace", Font.PLAIN, 12));
		panel_GameOver.add(labelReintentar);
		
		JLabel labelCerrar = new JLabel("Cerrar");
		labelCerrar.setBounds(245, 479, 90, 20);
		labelCerrar.setHorizontalAlignment(SwingConstants.CENTER);
		labelCerrar.setForeground(Color.YELLOW);
		labelCerrar.setFont(new Font("Unispace", Font.PLAIN, 12));
		panel_GameOver.add(labelCerrar);
		panelWin.setBounds(0, 0, 419, 619);
		centralPanel.add(panelWin);
		panelWin.setOpaque(false);
		panelWin.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("WIN");
		lblNewLabel_1.setFont(new Font("Unispace", Font.PLAIN, 42));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setForeground(Color.YELLOW);
		lblNewLabel_1.setBounds(120, 246, 191, 44);
		panelWin.add(lblNewLabel_1);
		
		//muestro el tiempo al ganar la partida
		labelWinTime = new JLabel("");
		labelWinTime.setFont(new Font("Unispace", Font.PLAIN, 11));
		labelWinTime.setHorizontalAlignment(SwingConstants.CENTER);
		labelWinTime.setForeground(Color.ORANGE);
		labelWinTime.setBounds(120, 308, 191, 20);
		panelWin.add(labelWinTime);
		
		//muestro el nivel al que a ganado el usuario
		labelLevelWin = new JLabel("");
		labelLevelWin.setFont(new Font("Unispace", Font.PLAIN, 11));
		labelLevelWin.setHorizontalAlignment(SwingConstants.CENTER);
		labelLevelWin.setForeground(Color.ORANGE);
		labelLevelWin.setBounds(225, 339, 24, 20);
		panelWin.add(labelLevelWin);
		
		//etiqueta
		JLabel lblLevel = new JLabel("Level:");
		lblLevel.setFont(new Font("Unispace", Font.PLAIN, 11));
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setForeground(Color.ORANGE);
		lblLevel.setBounds(187, 339, 44, 20);
		panelWin.add(lblLevel);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setIcon(new ImageIcon("resources\\Img\\arcade-clipart.png"));
		lblNewLabel.setBounds(10, 66, 400, 412);
		panelWin.add(lblNewLabel);
		
		//boton de la pantalla de partida ganada para volver a jugar
		JButton ButtonRetryPanelWin = new JButton("");
		ButtonRetryPanelWin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					defaultValues(); //reseteo  los valores de pantalla
					panelWin.setVisible(false);
					xogo.clipGameWin.close(); //paro la musica 
					playGame();
					
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
					
				}
			}
		});
		ButtonRetryPanelWin.setDefaultCapable(false);
		ButtonRetryPanelWin.setBorder(null);
		ButtonRetryPanelWin.setRequestFocusEnabled(false);
		ButtonRetryPanelWin.setIcon(new ImageIcon("resources\\Img\\button4.png"));
		ButtonRetryPanelWin.setOpaque(false);
		ButtonRetryPanelWin.setBounds(75, 505, 90, 90);
		panelWin.add(ButtonRetryPanelWin);
		
		//boton de la pantalla de partida ganada para cerrar dicha ventana 
		JButton ButtonClosePanelWin = new JButton("");
		ButtonClosePanelWin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defaultValues(); //reseteo  los valores de pantalla
				panelWin.setVisible(false);
				xogo.clipGameWin.close(); //paro la musica 
				defaultValues(); //reseteo  los valores de pantalla
				panel.removeAll();
				panel.setVisible(true);
			}
		});
		ButtonClosePanelWin.setIcon(new ImageIcon("resources\\Img\\button4.png"));
		ButtonClosePanelWin.setRequestFocusEnabled(false);
		ButtonClosePanelWin.setOpaque(false);
		ButtonClosePanelWin.setDefaultCapable(false);
		ButtonClosePanelWin.setBorder(null);
		ButtonClosePanelWin.setBounds(245, 505, 90, 90);
		panelWin.add(ButtonClosePanelWin);
		
		JLabel lblNewLabel_2 = new JLabel("Reintentar");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(Color.YELLOW);
		lblNewLabel_2.setFont(new Font("Unispace", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(75, 479, 90, 20);
		panelWin.add(lblNewLabel_2);
		
		JLabel lblCerrar = new JLabel("Cerrar");
		lblCerrar.setHorizontalAlignment(SwingConstants.CENTER);
		lblCerrar.setForeground(Color.YELLOW);
		lblCerrar.setFont(new Font("Unispace", Font.PLAIN, 12));
		lblCerrar.setBounds(245, 479, 90, 20);
		panelWin.add(lblCerrar);
		
		//Panel donde se muestran las fichas
		panel = new JPanel();
		centralPanel.add(panel);
		panel.setBorder(null);
		panel.setBackground(fondo);
		panel.setBounds(13, 10, 400, 600);
		panel.setLayout(null);
		
	//Botones
		JButton buttonMoveLeft = new JButton("");
		buttonMoveLeft.setIcon(new ImageIcon("resources\\Img\\left.png"));
		buttonMoveLeft.setFocusable(false);
		buttonMoveLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xogo.moveFileLeft();
			}
		});
		buttonMoveLeft.setBounds(143, 653, 35, 35);
		centralPanel.add(buttonMoveLeft);
		
		JButton buttonsRotate = new JButton("");
		buttonsRotate.setIcon(new ImageIcon("resources\\Img\\Rotate.png"));
		buttonsRotate.setFocusable(false);
		buttonsRotate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xogo.rotateFile();
			}
		});
		buttonsRotate.setBounds(183, 620, 35, 35);
		centralPanel.add(buttonsRotate);
		
		JButton buttonMoveRight = new JButton("");
		buttonMoveRight.setIcon(new ImageIcon("resources\\Img\\right.png"));
		buttonMoveRight.setFocusable(false);
		buttonMoveRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				xogo.moveFileRight();
			}
		});
		buttonMoveRight.setBounds(220, 653, 35, 35);
		centralPanel.add(buttonMoveRight);
		
		JButton buttonMoveDown = new JButton("");
		buttonMoveDown.setIcon(new ImageIcon("resources\\Img\\down+.png"));
		buttonMoveDown.setFocusable(false);
		buttonMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					xogo.moveFileDown();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		buttonMoveDown.setBounds(183, 688, 35, 35);
		centralPanel.add(buttonMoveDown);
		
		JPanel panel_Button = new JPanel();
		panel_Button.setBackground(fondoPantalla);
		panel_Button.setBorder(new LineBorder(Color.BLACK));
		panel_Button.setBounds(424, 10, 133, 55);
		centralPanel.add(panel_Button);
		panel_Button.setLayout(null);
		
		JButton buttonNewGame = new JButton("");
		buttonNewGame.setFocusable(false);
		buttonNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					playGame();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
					
				}
			}
		});
		buttonNewGame.setContentAreaFilled(false);
		buttonNewGame.setBackground(fondoPantalla);
		buttonNewGame.setOpaque(false);
		buttonNewGame.setDefaultCapable(false);
		buttonNewGame.setBorderPainted(false);
		buttonNewGame.setRolloverEnabled(false);
		buttonNewGame.setIcon(new ImageIcon("resources/Img/new_game.png"));
		buttonNewGame.setBounds(10, 11, 28, 28);
		panel_Button.add(buttonNewGame);
		
		//boton de pausa
		buttonPause = new JButton("");
		buttonPause.addActionListener(new ActionListener() {
			private int countButtonPause=1;
			public void actionPerformed(ActionEvent e) {
				if(countButtonPause==1) {
					timer.stop();
					cronometro.stop();
					countButtonPause++;
					buttonPause.setIcon(new ImageIcon("resources/Img/play_game.png"));
				}else {
					timer.start();
					cronometro.start();
					countButtonPause=1;
					buttonPause.setIcon(new ImageIcon("resources/Img/pause_game.png"));
				}
				
			}
		});
		buttonPause.setFocusable(false);
		buttonPause.setIcon(new ImageIcon("resources/Img/pause_game.png"));
		buttonPause.setContentAreaFilled(false);
		buttonPause.setBounds(48, 11, 28, 28);
		buttonPause.setFocusable(false);
		panel_Button.add(buttonPause);
		buttonPause.setBorderPainted(false);
		buttonPause.setFocusTraversalKeysEnabled(false);
		
		JButton buttonSetting = new JButton("");
		buttonSetting.setIcon(new ImageIcon("resources/Img/setting_game.png"));
		buttonSetting.setRolloverEnabled(false);
		buttonSetting.setOpaque(false);
		buttonSetting.setDefaultCapable(false);
		buttonSetting.setContentAreaFilled(false);
		buttonSetting.setBorderPainted(false);
		buttonSetting.setBackground(new Color(40, 40, 40));
		buttonSetting.setBounds(86, 11, 28, 28);
		buttonSetting.setFocusable(false);
		panel_Button.add(buttonSetting);
		buttonSetting.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			openWindowSetting();
		}
		});
		
		JPanel panel_time = new JPanel();
		panel_time.setBackground(fondoPantalla);
		panel_time.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_time.setBounds(424, 77, 133, 75);
		centralPanel.add(panel_time);
		panel_time.setLayout(null);
		
		JLabel iconClock = new JLabel("");
		iconClock.setBounds(53, 5, 28, 28);
		panel_time.add(iconClock);
		iconClock.setIcon(new ImageIcon("resources/Img/icon_Clock.png"));
		
		//tiempo
		timePanel = new JLabel("0:0:0");
		timePanel.setFont(new Font("Unispace", Font.PLAIN, 20));
		timePanel.setForeground(Color.WHITE);
		timePanel.setHorizontalAlignment(SwingConstants.CENTER);
		timePanel.setBounds(10, 44, 113, 19);
		panel_time.add(timePanel);
		
		//panel donde se muestra las lineas y el nivel
		JPanel panel_Level = new JPanel();
		panel_Level.setLayout(null);
		panel_Level.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		panel_Level.setBackground(new Color(40, 40, 40));
		panel_Level.setBounds(424, 164, 133, 64);
		centralPanel.add(panel_Level);
		
		JLabel lines = new JLabel("Linias: ");
		lines.setFont(new Font("Unispace", Font.PLAIN, 11));
		lines.setForeground(Color.WHITE);
		lines.setBounds(10, 11, 62, 14);
		panel_Level.add(lines);
		
		JLabel level = new JLabel("Nivel: ");
		level.setForeground(Color.WHITE);
		level.setFont(new Font("Unispace", Font.PLAIN, 11));
		level.setBounds(10, 36, 49, 14);
		panel_Level.add(level);
		
		//Muestro el numero de lineas conseguidas
		numberLines = new JLabel("0");
		numberLines.setFont(new Font("Unispace", Font.PLAIN, 11));
		numberLines.setHorizontalAlignment(SwingConstants.CENTER);
		numberLines.setForeground(Color.WHITE);
		numberLines.setBounds(58, 12, 35, 14);
		panel_Level.add(numberLines);
		
		//Muestro el nivel actual
		numberLevel = new JLabel("0");
		numberLevel.setFont(new Font("Unispace", Font.PLAIN, 11));
		numberLevel.setHorizontalAlignment(SwingConstants.CENTER);
		numberLevel.setForeground(Color.WHITE);
		numberLevel.setBounds(60, 36, 30, 14);
		panel_Level.add(numberLevel);	
		
	}
	
	/**
	 * metodo que añade los cuadrados al panel de juego
	 * @param square
	 */
	public void printsquare(JLabel square) {
		panel.add(square);
		panel.updateUI();
		panel.repaint();
		
	}
	
	/**
	 * cambia la velocidad de la ficha en el caso de que se cambie la dificultad en la ventana SETTING o el nivel
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	public void changeDificult(int levelIndicator) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		timer.setDelay(levelIndicator);
	}
	
	/**
	 * Metodo que muestra el tiempo por pantalla
	 */
	private void cronometro(){
		if(segundos==59) { 
			segundos=0; 
			minutos++;
		}
		if(minutos==59) { 
			minutos=0; 
			horas++; 
		}
		segundos++;
		timePanel.repaint();
		timePanel.setText(horas+":"+minutos+":"+segundos);
	}
			

	/**
	 * MEtodo que elimina un cuadrado del panel y lo refresca
	 * @param square
	 */
	public void deleteSquare(JLabel square) {
		panel.remove(square);
		panel.updateUI();
	}
	
	/**
	 * Muestra el numero de lineas completadas
	 * @param numberLines
	 */
	public void toShowNumberLines(int totalNumberLines) {
		numberLines.setText(Integer.toString(totalNumberLines));
	}
	
	/**
	 * Actualiza el numero de nivel del juego
	 * @param totalLevel
	 */
	public void toShowNumberlLevel(int totalLevel) {
		numberLevel.setText(Integer.toString(totalLevel));
	}
	
	/**
	 * Metodo de fin del juego
	 * @throws IOException 
	 */
	public void toShowTheEndGame() throws IOException {
		TimeGameOver.setText(Integer.toString(horas)+":"+Integer.toString(minutos)+":"+Integer.toString(segundos));
		levelGameOver.setText(numberLevel.getText());
		panel_GameOver.setVisible(true);
		xogo.createSoundGameOver();
		panel.setVisible(false);
		timer.stop(); //paro el juego
		cronometro.stop(); //paro el tiempo
	}
	
	public void toShowWinGame() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		try {
			labelWinTime.setText(Integer.toString(horas)+":"+Integer.toString(minutos)+":"+Integer.toString(segundos));
			labelLevelWin.setText(numberLevel.getText());
			panelWin.setVisible(true);
			xogo.createSoundWin();
			panel.setVisible(false);
			//changeDificult(900); //reseteo la dificultad del juego
			timer.stop(); //paro el juego
			cronometro.stop(); //paro el tiempo
		}catch (Exception e) {xogo.writeException("class: ventanaPrincipal,Method: toShowWinGame, error: "+e.toString());}
	}
	
	/**
	 * Metodo que inicia el juego
	 * @throws IOException 
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 */
	public void playGame() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		try {
			panel.removeAll();
			panelWin.setVisible(false); //escondo la ventana de partida ganada
			panel.setVisible(true); //muestro el panel de juego
			defaultValues();
			timer.start(); //pongo en marcha el timer
			cronometro.start(); //pongo en marcha el cronometro
			xogo=new Xogo(this);
			musicBackgraund();
			musicOnOff(true);
		}catch (Exception e) {xogo.writeException("class: ventanaPrincipal,Method: playGame, error: "+e.toString());}
	}
	
	/**
	 * resetea los marcadores de la interfaz
	 */
	private void defaultValues() {
		timePanel.setText(0+":"+0+":"+0); //reseteo el cronometro 
		toShowNumberLines(0); //reseteo la visualizacion de lineas
		toShowNumberlLevel(0); //reseteo la visualizacion del nivel
	 //---inicializo las variables del contador a 0
		horas=0;
		minutos=0;
		segundos=0;
	}
	
	/**
	 * resetea la dificultad cuando se pierde o gana en funcion del nivel(facil,normal,experto) seleccionado en el inicio
	 * @throws LineUnavailableException 
	 * @throws UnsupportedAudioFileException 
	 * @throws IOException 
	 */
	private void resetDificult() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		if(selectInitialLevel==1) {
			changeDificult(900);
		}else if(selectInitialLevel==2) {
			changeDificult(450);
		}else if(selectInitialLevel==3) {
			changeDificult(225);
		}
	}
	
	/**
	 * metodo para la musica de fondo
	 * @param music
	 * @throws IOException
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 */
	private void musicBackgraund() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File("resources1/Music/backgroun_tetris.wav"));
			sonido = AudioSystem.getClip();
		}catch (Exception e) {xogo.writeException("class: ventanaPrincipal,Method: musicBackgraund, error: "+e.toString());}
	}
	
	/**
	 * metodo para encender o apagar la musica del fondo
	 * @param status
	 * @throws LineUnavailableException
	 * @throws IOException
	 */
	public void musicOnOff(boolean status) throws LineUnavailableException, IOException {
		try {
			if(status==true) {
				sonido.open(audioInputStream);
				sonido.loop(Clip.LOOP_CONTINUOUSLY);
			}else {
				sonido.stop();
			}
		}catch (NullPointerException e) {xogo.writeException("class: ventanaPrincipal,Method: musicOnOff, error: "+e.toString());}
	}
	
	
	/**
	 * metodo que abre la ventana de ajustes 
	 */
	private void openWindowSetting() {
		Setting windowsSetting=new Setting(this);
		windowsSetting.setVisible(true);
	}
}
