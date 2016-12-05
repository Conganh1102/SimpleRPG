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
	// hình động cho nhân vật
	private Animation animDown, animUp, animLeft, animRight, aRight, aLeft, aUp, aDown;
	// khoảng cách thời gian giữa 2 lần gây sát thương
	private long lastAttackTimer, attackCooldown = 400, attackTimer = attackCooldown;
	private float heal;
	private int plusmana;
	private int tire;
	private boolean isAttack, isShoot;
	private int fireRate = 0;
	private int dir = 0;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, 256, 128);
		dameged = 200;
		heal = 0.5f;
		plusmana = 2;
		isAttack = false;
		isShoot = false;
		speed = 2.5f;
		tire = 1;

		// hình bao quanh nhân vật
		bounds.x = 118;// 22
		bounds.y = 85;// 44
		bounds.width = 19;// 19
		bounds.height = 19;// 19

		// Animations
		animDown = new Animation(100, Assets.player2_down);
		animUp = new Animation(100, Assets.player2_up);
		animLeft = new Animation(100, Assets.player2_left);
		animRight = new Animation(100, Assets.player2_right);
		aRight = new Animation(100, Assets.player2_aright);
		aDown = new Animation(100, Assets.player2_adown);
		aUp = new Animation(100, Assets.player2_aup);
		aLeft = new Animation(100, Assets.player2_aleft);
	}

	@Override
	public void tick() {

		handler.getGameCamera().centerOnEntity(this);
		// hình động
		animDown.tick();
		animUp.tick();
		animRight.tick();
		animLeft.tick();
		aDown.tick();
		aUp.tick();
		aRight.tick();
		aLeft.tick();

		// kiểm tra di chuyển
		getInput();
		move();

		// kiểm tra tấn công
		checkAttacks();
		updateShooting();
		// hồi phục
		recuperate();
	}

	private boolean checkAttackTimer() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
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
				heal(heal);
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
			if (e.getCollisionBounds(0, 0).intersects(ar) && mana > tire) {
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
		if (fireRate > 0)
			fireRate--;

		if (handler.getKeyManager().up) {
			yMove = -speed;
			dir = 1;
		}
		if (handler.getKeyManager().down) {
			yMove = speed;
			dir = 2;
		}
		if (handler.getKeyManager().left) {
			xMove = -speed;
			dir = 3;
		}
		if (handler.getKeyManager().right) {
			xMove = speed;
			dir = 4;
		}
		if (handler.getMouseManager().isLeftPressed()) {
			isAttack = true;
			tire(tire);
		}
		if ((handler.getKeyManager().fire || handler.getMouseManager().isRightPressed()) && fireRate <= 0
				&& mana > 50 * tire) {
			isShoot = true;
			tire(50 * tire);
			fireRate = FireProjectile.FIRE_RATE;
		}
	}

	private void updateShooting() {
		if (isShoot && mana > tire) {
			attackTimer = 0;
			shoot((int) (x + this.width / 2 - 15), (int) (y + this.height / 2), dir);
			isShoot = false;
		}
	}

	private void shoot(int x, int y, double dir) {
		Projectile p = new FireProjectile(handler, x, y, dir);
		handler.getWorld().getEntityManager().addEntity(p);
	}

	// render graphics
	@Override
	public void render(Graphics g) {
		// nhân vật
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()),
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		// thanh máu
		g.setColor(Color.black);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset() - 17),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset() - Tile.TILEHEIGHT - 15),(int) (1.0 * DEFAULT_HEALTH / 20), 4);
		g.setColor(Color.red);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset() - 17),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset() - Tile.TILEHEIGHT - 15),(int) (1.0 * health / 20), 4);
		// thanh mana
		g.setColor(Color.green);
		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset() - 17),
				(int) (y + bounds.y - handler.getGameCamera().getyOffset() - Tile.TILEHEIGHT - 10), mana / 20, 2);

		// g.fillRect((int) (x + bounds.x -
		// handler.getGameCamera().getxOffset()),
		// (int) (y + bounds.y - handler.getGameCamera().getyOffset()),
		// bounds.width, bounds.height);
	}

	// return animation frame
	private BufferedImage getCurrentAnimationFrame() {
		if (xMove < 0)
			return animLeft.getCurrentFrame();
		else if (xMove > 0)
			return animRight.getCurrentFrame();
		else if (yMove < 0)
			return animUp.getCurrentFrame();
		else if (yMove > 0)
			return animDown.getCurrentFrame();
		else if (dir == 1) {
			return isAttack && mana > tire ? aUp.getCurrentFrame() : animUp.getFrame(2);
		} else if (dir == 2) {
			return isAttack && mana > tire ? aDown.getCurrentFrame() : animDown.getFrame(2);
		} else if (dir == 3) {
			return isAttack && mana > tire ? aLeft.getCurrentFrame() : animLeft.getFrame(2);
		} else if (dir == 4) {
			return isAttack && mana > tire ? aRight.getCurrentFrame() : animRight.getFrame(2);
		}
		else
			return animDown.getFrame(2);
	}

}
