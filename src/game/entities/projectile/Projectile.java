package game.entities.projectile;

import java.awt.Rectangle;
import game.Handler;
import game.entities.Entity;

public abstract class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	protected double distance, speed, range, angle,  damage;
	
	//constructor
	public Projectile(Handler handler, int x, int y, double dir, int width, int height) {
		super(handler, (float)x,(float) y, width, height);
		xOrigin = x;
		yOrigin = y;
		angle = dir;
	}

	protected void move() {
		if (distance() > range ) 
			active = false;	
		if (angle == 1)
			y -= speed;
		else if (angle == 2)
			y += speed;
		else if (angle == 3)
			x -= speed;
		else if (angle == 4)
			x += speed;
		else
			y += speed;
		
	}
	
	protected void checkAttacks(){
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this) || e.equals(handler.getWorld().getEntityManager().getPlayer()))
				continue;
			if (e.getCollisionBounds(0, 0).intersects(new Rectangle((int)(x) ,(int) (y), width, height))) {
				e.hurt((int)damage);
				active = false;
				return;
			}
		}
	}
	protected double distance() {
		return Math.sqrt(Math.abs(Math.pow((xOrigin - x), 2)) + Math.abs(Math.pow((yOrigin - y), 2)));
	}
	
}
