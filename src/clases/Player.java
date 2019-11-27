package clases;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends Actor{

	public Player(int x, int y) {
		super(x, y);
		
		iniciaPlayer();
	}
	
	private void  iniciaPlayer() {
		
		ImageIcon iicon = new ImageIcon("src/imgs/front.png");
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
