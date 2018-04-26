package iu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.ImageIcon;

public class Setting extends JFrame {
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the frame.
	 */
	public Setting(VentanaPrincipal windows) {
		Color fondoPantalla=Color.decode("#282828");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources/Img/setting_game.png"));
		setTitle("Setting");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 234, 187);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel CentralPanel = new JPanel();
		CentralPanel.setBackground(fondoPantalla);
		CentralPanel.setBounds(0, 0, 228, 158);
		contentPane.add(CentralPanel);
		CentralPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sonido");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(33, 26, 74, 21);
		CentralPanel.add(lblNewLabel);
		
		JButton btnOn = new JButton("ON");
		btnOn.setFocusPainted(false);
		btnOn.setDefaultCapable(false);
		btnOn.setBorderPainted(false);
		btnOn.setBorder(null);
		btnOn.setBackground(Color.WHITE);
		btnOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					windows.musicOnOff(false);
					btnOn.setText("OFF");
				} catch (IOException | LineUnavailableException e) {
					
				}
			}
		});
		btnOn.setBounds(103, 26, 57, 23);
		CentralPanel.add(btnOn);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBackground(fondoPantalla);
		panel.setBounds(10, 76, 198, 47);
		CentralPanel.add(panel);
		panel.setLayout(null);
		
		JRadioButton rdbtnFacil = new JRadioButton("Facil");
		rdbtnFacil.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		rdbtnFacil.setForeground(Color.WHITE);
		rdbtnFacil.setBounds(6, 23, 58, 23);
		rdbtnFacil.setOpaque(false);
		panel.add(rdbtnFacil);
		rdbtnFacil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						windows.changeDificult(900);
						windows.setSelectInitialLevel(1);
						dispose();
					} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e2) {
			
					}
			}
		});
		buttonGroup.add(rdbtnFacil);
		
		JRadioButton rdbtnMedio = new JRadioButton("Medio");
		rdbtnMedio.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		rdbtnMedio.setForeground(Color.WHITE);
		rdbtnMedio.setBounds(66, 23, 65, 23);
		rdbtnMedio.setOpaque(false);
		panel.add(rdbtnMedio);
		rdbtnMedio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					windows.changeDificult(450);
					windows.setSelectInitialLevel(2);
					dispose();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e2) {
		
				}
			}
		});
		buttonGroup.add(rdbtnMedio);
		
		JRadioButton rdbtnDificil = new JRadioButton("Dificil");
		rdbtnDificil.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		rdbtnDificil.setForeground(Color.WHITE);
		rdbtnDificil.setBounds(133, 23, 58, 23);
		rdbtnDificil.setOpaque(false);
		panel.add(rdbtnDificil);
		rdbtnDificil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					windows.changeDificult(225);
					windows.setSelectInitialLevel(3);
					dispose();
				} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e2) {
		
				}
			}
		});
		buttonGroup.add(rdbtnDificil);
		
		JLabel lblDificultad = new JLabel("Dificultad");
		lblDificultad.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 12));
		lblDificultad.setForeground(Color.WHITE);
		lblDificultad.setBounds(74, 2, 57, 14);
		panel.add(lblDificultad);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\a14gabrielcv\\Documents\\proyectos-eclipse\\Tetris\\resources\\Img\\Music_37px.png"));
		lblNewLabel_1.setBounds(19, 19, 37, 30);
		CentralPanel.add(lblNewLabel_1);
	}
}
