package game.entities;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import game.Handler;
import game.entities.creatures.Player;

public class EntityManager {
	
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			//order the entities if they are overlapping
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight())
				return -1;
			return 1;
		}
	};
	//constructor
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
	}
	
	
	public void tick(){
		//update entities if they are alive
//		Iterator<Entity> it = entities.iterator();
//		while(it.hasNext()){
//			Entity e = it.next();a
//			e.tick();
//			if(!e.isActive())
//				it.remove();
//		}
		for(int i = 0; i < entities.size(); i++){
			entities.get(i).tick();
			if(!entities.get(i).isActive())
				entities.remove(i);
		}
		entities.sort(renderSorter);
	}
	
	//draw the entities
	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
	}

	public void addEntity(Entity e){
		entities.add(e);
	}
	
	//getters / setters

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

}
