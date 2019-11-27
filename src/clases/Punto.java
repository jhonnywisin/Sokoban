package clases;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Punto extends Actor{

	public Punto(int x, int y) {
		super(x, y);
		
		iniciaPunto();
	}

	private void iniciaPunto() {
		
		ImageIcon iicon = new ImageIcon("src/imgs/spot.png");
        Image image = iicon.getImage();
        setImage(image);
	}
}
