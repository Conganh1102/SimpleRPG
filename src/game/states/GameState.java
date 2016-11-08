package game.states;
import java.awt.Graphics;
import game.Handler;
import game.tiles.Tile;
import game.worlds.World;

public class GameState extends State {
	
	private World world;
	private World world3;
	private boolean ismap;
	private int currentHealth;
	private int currentMana;
	//constructor
	public GameState(Handler handler){
		super(handler);
		
		world3 = new World(handler, "res/worlds/world3.txt");
		world = new World(handler, "res/worlds/world1.txt");
		
		handler.setWorld(world3);
		ismap = false;
		
	}
	private void changeWorld(){
		if(handler.getWorld().getEntityManager().getPlayer().getX() > handler.getWorld().getWidth()*Tile.TILEWIDTH 
				|| handler.getWorld().getEntityManager().getPlayer().getY() > handler.getWorld().getHeight()*Tile.TILEHEIGHT){
			if(ismap){
				handler.setWorld(world3);
				handler.getWorld().getEntityManager().getPlayer().setX(16);
				handler.getWorld().getEntityManager().getPlayer().setY(18*64);
				handler.getWorld().getEntityManager().getPlayer().setHealth(currentHealth);
				handler.getWorld().getEntityManager().getPlayer().setMana(currentMana);
				ismap = false;
				
			}
			else{
				handler.setWorld(world);
				handler.getWorld().getEntityManager().getPlayer().setX(32f);
				handler.getWorld().getEntityManager().getPlayer().setY(32f);
				handler.getWorld().getEntityManager().getPlayer().setHealth(currentHealth);
				handler.getWorld().getEntityManager().getPlayer().setMana(currentMana);
				ismap = true;
			}
			
		}
		else{
			currentHealth = (int)handler.getWorld().getEntityManager().getPlayer().getHealth();
			currentMana = (int)handler.getWorld().getEntityManager().getPlayer().getMana();
		}
	}
	
	@Override
	public void tick() {
		changeWorld();
		handler.getWorld().tick();		
	}

	@Override
	public void render(Graphics g) {
		handler.getWorld().render(g);
	}

}
