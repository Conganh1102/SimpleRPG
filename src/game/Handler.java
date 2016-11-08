package game;

 
import game.gfx.GameCamera;
import game.input.KeyManager;
import game.input.MouseManager;
import game.worlds.World;

//holds all the information of the came
public class Handler {
	//declare game and map
	private Game game;
	private World world;
	
	//default constructor
	public Handler(Game game){
		this.game = game;
	}
	
	//getters / setters
	public GameCamera getGameCamera(){
		return game.getGameCamera();
	}
	
	public KeyManager getKeyManager(){
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager(){
		return game.getMouseManager();
	}
	
	public int getWidth(){
		return game.getWidth();
	}
	
	public int getHeight(){
		return game.getHeight();
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
