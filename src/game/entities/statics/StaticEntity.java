package game.entities.statics;
import game.Handler;
import game.entities.Entity;

//create a class of entity that do not move ( static )
public abstract class StaticEntity extends Entity {
	
	public StaticEntity(Handler handler, float x, float y, int width, int height){
		super(handler, x, y, width, height);
	}

}
