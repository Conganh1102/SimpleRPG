package game.entities.projectile;

import java.awt.Graphics;
import game.Handler;
import game.gfx.Animation;
import game.gfx.Assets;

public class FireProjectile extends Projectile {

	public static final int FIRE_RATE = 70;
	private Animation kunai;
	public FireProjectile(Handler handler, int x, int y, double dir) {
		super(handler, x, y, dir, 32, 32);
		range = 300;
		damage = 250;
		speed = 5;
		// 
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = 19;
		bounds.height = 19;
		//
		kunai = new Animation(30, Assets.kunai);
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
