package game.entities.creatures;

import game.Handler;
import game.entities.Entity;
import game.tiles.Tile;

public abstract class Creature extends Entity {

	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
	public static final int DEFAULT_MANA = 1000;

	protected int mana;
	protected float speed;
	protected float xMove, yMove;
	protected int dameged;
	protected int attack;

	// constructor
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		attack = 0;
		mana = DEFAULT_MANA;
	}

	protected void tired(int e) {
		if (mana > 0)
			mana = mana - e;
	}

	protected void strong(int e){
		if(mana < DEFAULT_MANA)
			mana = mana + e; 
		else
			mana = DEFAULT_MANA;
	 }

	// public void shoot(int x, int y, double dir){
	// Projectile p = new HeroProjectile(handler,x, y, dir);
	// //level.add(p);
	// }

	// move the entity if there is no collision with other objects or walls
	public void move() {
		if (!checkEntityCollisions(xMove, 0f))
			moveX();
		if (!checkEntityCollisions(0f, yMove))
			moveY();
	}

	// move left/right
	public void moveX() {
		if (xMove > 0) {// Moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			// if the entity does not collide with walls / objects then move
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				// move it to the closest possible position to the boundary
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}

		} else if (xMove < 0) {// Moving left
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			// if the entity does not collide with walls / objects then move
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				// move it to the closest possible position to the boundary
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}

		}
	}

	public void moveY() {
		if (yMove < 0) {// Up
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			// if the entity does not collide with walls / objects then move
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				// move it to the closest possible position to the boundary
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}

		} else if (yMove > 0) {// Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

			// if the entity does not collide with walls / objects then move
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				// move it to the closest possible position to the boundary
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}

		}
	}

	// check if the tile is a solid (if its a wall or rock)
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
	}

	// getters / setters

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
	

}
