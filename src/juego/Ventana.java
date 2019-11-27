package juego;

import javax.swing.JFrame;

public class Ventana extends JFrame{
	
	private static final long serialVersionUID = 3078848139782097643L;

	private final int OFFSET = 30;

	Marco m = new Marco();
	
	public Ventana() {
		
		add(m);
		
		setTitle("SOKOBAN");
		setSize(m.getMarcoWidth() + OFFSET,
				m.getMarcoHeight() + 2 * OFFSET);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	} 
}
