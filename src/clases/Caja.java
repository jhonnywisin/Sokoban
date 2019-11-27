package clases;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Caja extends Actor{

	public Caja(int x, int y) {
		super(x, y);
		
		iniciaPunto();
	}
	
	private void iniciaPunto() {
		
		 ImageIcon iicon = new ImageIcon("src/imgs/boxOff.png");
	     Image image = iicon.getImage();
	     setImage(image);
	}
	
    public void move(int x, int y) {
        
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}
