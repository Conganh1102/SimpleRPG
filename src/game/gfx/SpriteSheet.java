package game.gfx;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage sheet;
	
	//constructor 
	public SpriteSheet(BufferedImage sheet){
		this.sheet = sheet;
	}
	
	//create crop function, get a subimage from the larger texture sheet
	public BufferedImage crop(int x, int y, int width, int height){
		return sheet.getSubimage(x, y, width, height);
	}
	
}
