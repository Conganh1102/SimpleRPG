package game.entities;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.Handler;

public abstract class Entity {

	//We chose to use floats as the position of the entity on the map does not require alot of precision
	public static final float DEFAULT_HEALTH = 1000;
	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected float health;
	protected boolean active = true;
	protected Rectangle bounds;
	
	//constructor
	public Entity(){
		
	}
	
	public Entity(Handler handler, float x, float y, int width, int height){
		this.handler = handler;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = DEFAULT_HEALTH;
		
		bounds = new Rectangle(0, 0, width, height);
	}
	
	//abstract because player and creatures will do these differently
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public abstract void die();
	
	//get damaged
	public void hurt(int amt){
		health -= amt;
		if(health <= 0){
			active = false;
			die();
		}
	}
	public void heal(float amt){
		if(health < DEFAULT_HEALTH)
			health += amt;
		else
			health = DEFAULT_HEALTH;
	}
	public boolean checkEntityCollisions(float xOffset, float yOffset){
		for(Entity e : handler.getWorld().getEntityManager().getEntities()){
			//if this entity attacks itself then do not damage it, rectangle can overlap entity model
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	//check hitbox of entity
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	//getters / setters
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
