package game.gfx;

import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

public class ImageLoader {

	//load image
	public static BufferedImage loadImage(String path){
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
}
