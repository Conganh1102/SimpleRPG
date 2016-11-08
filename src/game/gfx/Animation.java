package game.gfx;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int speed, index;
	private long lastTime, timer;
	private BufferedImage[] frames;
	
	//constructor 
	public Animation(int speed, BufferedImage[] frames){
		this.speed = speed;
		this.frames = frames;
		index = 0;
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	   
	public BufferedImage getFrame(int index) {
		return frames[index];
	}


	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
	}


	//change animation frame per update
	public void tick(){
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(timer > speed){
			index++;
			timer = 0;
			if(index >= frames.length)
				index = 0;
		}
	}
	
	//getter
	public BufferedImage getCurrentFrame(){
		return frames[index];
	}

}
