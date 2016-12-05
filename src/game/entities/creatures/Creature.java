package game.entities.creatures;

import game.Handler;
import game.entities.Entity;
import game.tiles.Tile;

public abstract class Creature extends Entity {
	
	public static final int DEFAULT_MANA = 1000;

	public static final float DEFAULT_SPEED = 3.0f;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
	
	protected int mana;
	protected float speed;
	protected float xMove, yMove;
	protected int dameged;
	protected int attack;

	// constructor
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		mana = DEFAULT_MANA;
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
		attack = 0;
		
	}

	protected void tire(int m) {
		if (mana > m)
			mana = mana - m;
	}

	protected void strong(int m){
		if(mana < DEFAULT_MANA)
			mana = mana + m; 
		else
			mana = DEFAULT_MANA;
	 }

	
	// 
	public void move() {
		if (!checkEntityCollisions(xMove, 0f))
			moveX();
		if (!checkEntityCollisions(0f, yMove))
			moveY();
	}

	// di chuyển phải
	public void moveX() {
		if (xMove > 0) {
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILEWIDTH;
			// kiểm tra va chạm với tường
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				// đi chuyển đoạn ngắn nhất đến ranh giới với tường
				x = tx * Tile.TILEWIDTH - bounds.x - bounds.width - 1;
			}

		} else if (xMove < 0) {
			int tx = (int) (x + xMove + bounds.x) / Tile.TILEWIDTH;
			// kiểm tra va chạm với tường
			if (!collisionWithTile(tx, (int) (y + bounds.y) / Tile.TILEHEIGHT)
					&& !collisionWithTile(tx, (int) (y + bounds.y + bounds.height) / Tile.TILEHEIGHT)) {
				x += xMove;
			} else {
				// đi chuyển đoạn ngắn nhất đến ranh giới với tường
				x = tx * Tile.TILEWIDTH + Tile.TILEWIDTH - bounds.x;
			}

		}
	}
// di chuyển lên xuống
	public void moveY() {
		if (yMove < 0) {
			int ty = (int) (y + yMove + bounds.y) / Tile.TILEHEIGHT;

			// kiểm tra va chạm với tường
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				// đi chuyển đoạn ngắn nhất đến ranh giới với tường
				y = ty * Tile.TILEHEIGHT + Tile.TILEHEIGHT - bounds.y;
			}

		} else if (yMove > 0) {// Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILEHEIGHT;

			//  kiểm tra va chạm với tường
			if (!collisionWithTile((int) (x + bounds.x) / Tile.TILEWIDTH, ty)
					&& !collisionWithTile((int) (x + bounds.x + bounds.width) / Tile.TILEWIDTH, ty)) {
				y += yMove;
			} else {
				// đi chuyển đoạn ngắn nhất đến ranh giới với tường
				y = ty * Tile.TILEHEIGHT - bounds.y - bounds.height - 1;
			}

		}
	}

	// kiểm tra vật thể có thể đi chuyển xuyên qua hay không ?
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
