package game.entities.projectile;

import java.awt.Graphics;
import game.Handler;
import game.gfx.Animation;
import game.gfx.Assets;

public class FireProjectile extends Projectile {

	public static final int FIRE_RATE = 35;
	private Animation kunai;
	public FireProjectile(Handler handler, int x, int y, double dir) {
		super(handler, x, y, dir);
		range = 500;
		damage = 500;
		speed = 5;
		// from entity class, change to player model size
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 19;
		bounds.height = 19;
		//
		kunai = new Animation(30, Assets.projectile_hero);
	}
	public void update() {				
		checkAttacks();
	}

	@Override
	public void tick() {
		kunai.tick();
		update();
		move();

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(kunai.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		


	}

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}
	
	
}
