package game.entities.projectile;

import java.awt.Rectangle;
import java.util.Date;
import java.util.Random;

import game.Handler;
import game.entities.Entity;
import game.gfx.Assets;

public abstract class Projectile extends Entity {

	protected final int xOrigin, yOrigin;
	protected double angle;
	protected double nx, ny;
	protected double distance;
	protected double speed,range, damage;
	
	protected final Random random = new Random(new Date().getTime());
	
	public Projectile(Handler handler, int x, int y, double dir) {
		super(handler, (float)x,(float) y, 32, 32);
		xOrigin = x;
		yOrigin = y;
		angle = dir;
	}

	protected void move() {
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

		// x += nx;
		// y += ny;

		if (distance() > range) {
			active = false;
		}
	}
	protected double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs(Math.pow((xOrigin - x), 2)) + Math.abs(Math.pow((yOrigin - y), 2)));
		return dist;
	}
	protected void checkAttacks(){
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this) || e.equals(handler.getWorld().getEntityManager().getPlayer()))
				continue;
			if (e.getCollisionBounds(0, 0).intersects(new Rectangle((int)(x)
					,(int) (y), 32, 32))) {
				e.hurt((int)damage);
				active = false;
				return;
			}
		}
	}
}
