package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Levels extends JFrame implements MouseListener{
	
	private static final long serialVersionUID = 6568200633854502963L;
	private MarcoLevels  m   = new MarcoLevels();
	private MarcoDown    md  = new MarcoDown();
	private MarcoLeft    ml  = new MarcoLeft();
	private MarcoLeft    mr  = new MarcoLeft();
	private JPanel       mb  = new JPanel();
	private static final int COLUMNAS = 4;
    private static final int FILAS = 5;
    private JButton btns[][] = new JButton[FILAS][COLUMNAS];

	
	public Levels() {
		
		setSize(600, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		
		mb.setLayout(new GridLayout(FILAS, COLUMNAS));
		agrega_btns();
		
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		c.add(m,BorderLayout.NORTH);
		c.add(mb,BorderLayout.CENTER);
		c.add(md,BorderLayout.SOUTH);
		c.add(ml,BorderLayout.WEST);
		c.add(mr,BorderLayout.EAST);
	}
	
	private void agrega_btns(){
        
        int con = 1;
        
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                btns[i][j] = new JButton();
                mb.add(btns[i][j]);
                btns[i][j].setText("" + con++);
                btns[i][j].addMouseListener(this);
                btns[i][j].setBackground(Color.red);
            }
        }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource().equals(btns[0][0])) {
			Ventana v = new Ventana(1);
			v.setVisible(true);
			dispose();
		}else if(e.getSource().equals(btns[0][1])) {
			Ventana v = new Ventana(2);
			v.setVisible(true);
			dispose();
		}else if(e.getSource().equals(btns[0][2])) {
			Ventana v = new Ventana(3);
			v.setVisible(true);
			dispose();
		}else if(e.getSource().equals(btns[0][3])) {
			Ventana v = new Ventana(4);
			v.setVisible(true);
			dispose();
		}else if(e.getSource().equals(btns[1][0])) {
			Ventana v = new Ventana(5);
			v.setVisible(true);
			dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		for (int i = 0; i < FILAS; i++) {
			for (int j = 0; j < COLUMNAS; j++) {
				if(e.getSource().equals(btns[i][j])){
                    btns[i][j].setBackground(Color.BLACK);
                    btns[i][j].setForeground(Color.red);
                }   
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		 for (int i = 0; i < FILAS; i++) {
	           for (int j = 0; j < COLUMNAS; j++) {
	               if(e.getSource().equals(btns[i][j]))
	                   btns[i][j].setBackground(Color.red); 
	                   btns[i][j].setForeground(Color.BLACK);     
	           }
	     }
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}







class MarcoLevels extends JPanel{
	
	public MarcoLevels() {

		Dimension d = new Dimension(600, 100);
		setPreferredSize(d);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(250,240,170));
		g.fillRect(0, 0, 600, 100);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Serif", Font.BOLD, 40));
		g.drawString("SOKOBAN", 200, 50);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.BOLD, 40));
		g.drawString("SOKOBAN", 202, 52);
	}
}

class MarcoDown extends JPanel{
	
	public MarcoDown() {

		Dimension d = new Dimension(600, 50);
		setPreferredSize(d);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(250,240,170));
		g.fillRect(0, 0, 600, 50);
	}
}

class MarcoLeft extends JPanel{
	
	public MarcoLeft() {

		Dimension d = new Dimension(100, 400);
		setPreferredSize(d);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(new Color(250,240,170));
		g.fillRect(0, 0, 100, 400);
	}
}
