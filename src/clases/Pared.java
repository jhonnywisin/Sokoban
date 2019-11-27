package clases;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Pared extends Actor{
	
	private Image image;

	public Pared(int x, int y) {
		super(x, y);
		
		IniciaPared();
	}
	private void IniciaPared() {
		
		 ImageIcon iicon = new ImageIcon("src/imgs/redBrick.png");
	     image = iicon.getImage();
	     setImage(image);
	}
}
