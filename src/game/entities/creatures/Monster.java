
package game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import game.Handler;
import game.gfx.Animation;
import game.gfx.Assets;
import game.tiles.Tile;

public class Monster extends Creature {
	// Animations
	private Animation animDown, animUp, animLeft, animRight;
	// Attack timer
	private long lastAttackTimer, attackCooldown = 800, attackTimer = attackCooldown;
	private float distanceAttack;
	private boolean attackPlayer;
	private float distanceLeft, distanceRight, distanceUp, distanceDown;
	private int moveRate;

	public Monster(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		speed = 1.5f;
		dameged = 50;
		distanceAttack = 200f;
		attackPlayer = false;
		distanceLeft = 64f;
		distanceRight = 0f;
		distanceUp = 64f;
		distanceDown = 0f;
		moveRate = 100;
		// from entity class, change to player model size
		bounds.x = 22;
		bounds.y = 44;
		bounds.width = 19;
		bounds.height = 19;

		// Animations
		animDown = new Animation(500, Assets.zombie_down);
		animUp = new Animation(500, Assets.zombie_up);
		animLeft = new Animation(500, Assets.zombie_left);
		animRight = new Animation(500, Assets.zombie_right);
	}

	@Override
	public void tick() {
		// Animations
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();

		// Movement
		getInput();
		attackPlayer();
		move();

	}

	public float getDistance(float v, float h) {
		double distance = Math.sqrt((double) (v * v) + (double) (h * h));
		return (float) distance;
	}

	public void attackPlayer() {
		// update cool downs on attack
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		// if the player cannot attack, stop
		if (attackTimer < attackCooldown)
			return;
		attackTimer = 0;
		if (attackPlayer)
			handler.getWorld().getEntityManager().getPlayer().hurt(dameged);
	}

	// display output if the player dies
	@Override
	public void die() {
	}

	// get move input from random number generator
	private void getInput() {
		if (moveRate > 0)
			moveRate--;
		xMove = 0;
		yMove = 0;
		attackPlayer = false;

		float v = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).x
				- this.getCollisionBounds(0, 0).x;
		float h = handler.getWorld().getEntityManager().getPlayer().getCollisionBounds(0, 0).y
				- this.getCollisionBounds(0, 0).y;

		if (getDistance(v, h) < distanceAttack) {
			speed = 1.5f;
			if (getDistance(v, h) < 30)
				attackPlayer = true;
			if (v > 0) {
				xMove = speed;
			}
			if (v < 0) {
				xMove = -speed;
			}
			if (h > 0) {
				yMove = speed;
			}
			if (h < 0) {
				yMove = -speed;	
			}
		} else {

			speed = 0.3f;

			if (distanceRight >= 0f && distanceRight <= 64f) {
				xMove = speed;
				distanceRight += speed;
			}
			if (distanceRight > 64f) {
				xMove = -speed;
				distanceLeft -= speed;
			}

			if (distanceLeft < 0f) {
				distanceLeft = 64f;
				distanceRight = 0f;
			}

			if (distanceDown >= 0f && distanceDown <= 64f) {
				yMove = speed;
				distanceDown += speed;
			}
			if (distanceDown > 64f) {
				yMove = -speed;
				distanceUp -= speed;
			}

			if (distanceUp < 0f) {
				distanceUp = 64f;
				distanceDown = 0f;
			}

		}

	}

	// render graphics
	@Override
	public void render(Graphics g) {

		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		g.setColor(Color.RED);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset() - 15),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset() - Tile.TILEHEIGHT + 10),
				(int) (1.0 * health / 20), 3);

		// g.fillRect((int) (x + bounds.x -
		// handler.getGameCamera().getxOffset()),
		// (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
		// bounds.width, bounds.height);

	}

	// return animation frame
	private BufferedImage getCurrentAnimationFrame() {
		if (xMove < 0) {
			return animLeft.getCurrentFrame();
		} else if (xMove > 0) {
			return animRight.getCurrentFrame();
		} else if (yMove < 0) {
			return animUp.getCurrentFrame();
		} else {
			return animDown.getCurrentFrame();
		}
	}
}
