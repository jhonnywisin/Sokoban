package clases;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Puerta extends Actor{

	public Puerta(int x, int y) {
		super(x, y);
		
		iniciaPuerta();
	}

	private void iniciaPuerta() {
		
		ImageIcon iicon = new ImageIcon("src/imgs/outline2.png");
        Image image = iicon.getImage();
        setImage(image);
	}
}
