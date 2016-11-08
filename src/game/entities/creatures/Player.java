package game.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import game.tiles.Tile;
import game.Handler;
import game.entities.Entity;
import game.entities.projectile.FireProjectile;
import game.entities.projectile.Projectile;
import game.gfx.Animation;
import game.gfx.Assets;

public class Player extends Creature {
	// Animations
	private Animation animDown, animUp, animLeft, animRight, aRight, aLeft, aUp, aDown;
	// Attack timer
	private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;
	private float healing;
	private int plusmana;
	private int tire;
	private boolean isAttack, isShoot;
	private int fireRate = 0;
	private int dir = 0;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, 256, 128);
		dameged = 500;
		healing = 0.1f;
		plusmana = 1;
		isAttack = false;
		isShoot = false;
		speed = 2.0f;
		tire = 10;

		// from entity class, change to player model size
		bounds.x = 118;// 22
		bounds.y = 85;// 44
		bounds.width = 19;// 19
		bounds.height = 19;// 19

		// Animations
		animDown = new Animation(500 / 2, Assets.player2_down);
		animUp = new Animation(500 / 2, Assets.player2_up);
		animLeft = new Animation(500 / 2, Assets.player2_left);
		animRight = new Animation(500 / 2, Assets.player2_right);
		aRight = new Animation(500, Assets.player_aright);
		aDown = new Animation(500, Assets.player_adown);
		aUp = new Animation(500, Assets.player_aup);
		aLeft = new Animation(500, Assets.player_aleft);
	}

	@Override
	public void tick() {

		// Animations
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		aDown.tick();
		aUp.tick();
		aRight.tick();
		aLeft.tick();

		updateShooting();
		// Movement
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		// Attack
		checkAttacks();

		recuperate();
	}

	private boolean checkAttackTimer() {
		// update cool downs on attack
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		// if the player cannot attack, stop
		if (attackTimer < attackCooldown)
			return true;
		return false;
	}

	private void recuperate() {
		if (!checkAttackTimer()) {
			if (attackTimer > 3000) {
				strong(plusmana);
			}
			if (attackTimer > 60000)
				healing(healing);
		}

	}

	private void checkAttacks() {
		if (checkAttackTimer())
			return;

		// collision bounds rectangle
		Rectangle cb = getCollisionBounds(0, 0);
		// attack rectangle ( range )
		Rectangle ar = new Rectangle();
		int arSize = 60;
		ar.width = arSize;
		ar.height = arSize;

		// check which direction the player is attacking and move the attack
		// rectangle accordingly
		if (handler.getKeyManager().aUp || (isAttack && dir == 1)) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y - arSize;
		} else if (handler.getKeyManager().aDown || (isAttack && dir == 2)) {
			ar.x = cb.x + cb.width / 2 - arSize / 2;
			ar.y = cb.y + cb.height;
		} else if (handler.getKeyManager().aLeft || (isAttack && dir == 3)) {
			ar.x = cb.x - arSize;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else if (handler.getKeyManager().aRight || (isAttack && dir == 4)) {
			ar.x = cb.x + cb.width;
			ar.y = cb.y + cb.height / 2 - arSize / 2;
		} else {
			return;
		}

		attackTimer = 0;

		// check if the player damaged anything
		for (Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if (e.equals(this))
				continue;
			if (e.getCollisionBounds(0, 0).intersects(ar) && mana > 10) {
				e.hurt(dameged);
				return;
			}
		}

	}

	// display output if the player dies
	@Override
	public void die() {
		System.out.println("You lose");
	}

	// get move input from keymanager
	private void getInput() {
		xMove = 0;
		yMove = 0;
		attack = 0;
		isAttack = false;
		isShoot = false;
		// System.out.println(fireRate);
		if (fireRate > 0)
			fireRate--;

		if (handler.getKeyManager().up) {
			yMove = -speed;
			dir = 1;
		}
		if (handler.getKeyManager().down) {
			dir = 2;
			yMove = speed;
		}
		if (handler.getKeyManager().left) {
			xMove = -speed;
			dir = 3;
		}
		if (handler.getKeyManager().right) {
			xMove = speed;
			dir = 4;
		}
		
		
		if (mana > 0) {
			
			if (handler.getMouseManager().isRightPressed()) {
				isAttack = true;
				tired(tire);	
			}
			if ((handler.getKeyManager().fire || handler.getMouseManager().isLeftPressed()) && fireRate <= 0) {
				tired(tire);
				isShoot = true;
				fireRate = FireProjectile.FIRE_RATE;
			}
			
//			if (handler.getKeyManager().aUp) {
//				attack = 1;
//				tired(tire);
//			}
//			if (handler.getKeyManager().aDown) {
//				attack = 2;
//				tired(tire);
//			}
//			if (handler.getKeyManager().aLeft) {
//				attack = 3;
//				tired(tire);
//			}
//			if (handler.getKeyManager().aRight && fireRate <= 0) {
//				attack = 4;
//				tired(tire);
//			}
		}

	}

	private void updateShooting() {
		if (isShoot) {
			shoot((int) (x + this.width/2), (int) (y + this.height/2), dir);
		}

	}

	private void shoot(int x, int y, double dir) {

		Projectile p = new FireProjectile(handler, x, y, dir);
		handler.getWorld().getEntityManager().addEntity(p);
	}

	public void dare(Graphics g) {
		if (isAttack) {
			// System.out.println(attackRa
			if (dir == 4)
				g.drawImage(Assets.kunai[0], (int) (x + 45 - handler.getGameCamera().getxOffset()),
						(int) (y + 15 - handler.getGameCamera().getyOffset()), 64, 64, null);
			else if (dir == 3)
				g.drawImage(Assets.kunai[1], (int) (x - 43 - handler.getGameCamera().getxOffset()),
						(int) (y + 15 - handler.getGameCamera().getyOffset()), 64, 64, null);
			else if (dir == 2)
				g.drawImage(Assets.kunai[3], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y + 60 - handler.getGameCamera().getyOffset()), 64, 64, null);
			else if (dir == 1)
				g.drawImage(Assets.kunai[2], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y - 30 - handler.getGameCamera().getyOffset()), 64, 64, null);
			else
				g.drawImage(Assets.kunai[3], (int) (x - handler.getGameCamera().getxOffset()),
						(int) (y + 60 - handler.getGameCamera().getyOffset()), 64, 64, null);

		}

	}

	// render graphics
	@Override
	public void render(Graphics g) {
		// center the game camera on the player
		dare(g);
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset() - 17),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset() - Tile.TILEHEIGHT - 15),
				(int) (1.0 * health / 20), 4);

		 g.fillRect((int) (x + bounds.x -
		 handler.getGameCamera().getxOffset()),
		 (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
		 bounds.width, bounds.height);

		g.setColor(Color.green);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset() - 17),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset() - Tile.TILEHEIGHT - 10), mana / 20, 4);

	}

	// return animation frame
	private BufferedImage getCurrentAnimationFrame() {
		// if (attack == 1)
		// return aUp.getCurrentFrame();
		// else if (attack == 2 && fireRate <= 0)
		// return aDown.getCurrentFrame();
		// else if (attack == 3 && fireRate <= 0)
		// return aLeft.getCurrentFrame();
		// else if (attack == 4)
		// return aRight.getCurrentFrame();
		if (xMove < 0)
			return animLeft.getCurrentFrame();
		else if (xMove > 0)
			return animRight.getCurrentFrame();
		else if (yMove < 0)
			return animUp.getCurrentFrame();
		else if (yMove > 0)
			return animDown.getCurrentFrame();
		else if (dir == 1)
			return animUp.getFrame(2);
		else if (dir == 2)
			return animDown.getFrame(2);
		else if (dir == 3)
			return animLeft.getFrame(2);
		else if (dir == 4)
			return animRight.getFrame(2);
		else
			return animDown.getFrame(2);

	}

}
