package game.worlds;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import game.Handler;
import game.entities.EntityManager;
import game.entities.creatures.Player;
import game.entities.creatures.Monster;
import game.entities.statics.Rock;
import game.entities.statics.Tree;
import game.tiles.Tile;
import game.utils.Utils;

public class World {

	private Handler handler;
	private int width, height;
	private int spawnX, spawnY;
	private int[][] tiles;
	private ArrayList<Point> spawnMonster = new ArrayList<Point>();
	private ArrayList<Point> spawnTree = new ArrayList<Point>();
	
	// Entities
	private EntityManager entityManager;

	// constructor
	public World(Handler handler, String path) {
		this.handler = handler;
		entityManager = new EntityManager(handler, new Player(handler, 100, 100));

		// Temporary entities
		
		entityManager.addEntity(new Rock(handler, 100, 450));

		loadWorld(path);
		// Monster
		for (int i = 0; i < spawnMonster.size(); i++) {
			entityManager.addEntity(
					new Monster(handler,(int) (spawnMonster.get(i).getX() * Tile.TILEWIDTH)
							,(int) (spawnMonster.get(i).getY() * Tile.TILEHEIGHT)));
		}
		for(int j = 0; j < spawnTree.size(); j++){
			entityManager.addEntity(new Tree(handler,(int) (spawnTree.get(j).getX() * Tile.TILEWIDTH)
					,(int) (spawnTree.get(j).getY() * Tile.TILEHEIGHT - 3*Tile.TILEHEIGHT/2 )));
		}

		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);

	}
	
	

	public void tick() {
		entityManager.tick();
	}

	// render world graphics
	public void render(Graphics g) {
		// create largest size drawing area for game camera angle
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILEWIDTH);
		int xEnd = (int) Math.min(width,
				(handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILEWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILEHEIGHT);
		int yEnd = (int) Math.min(height,
				(handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILEHEIGHT + 1);

		// render each tile
		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				getTile(x, y).render(g, (int) (x * Tile.TILEWIDTH - handler.getGameCamera().getxOffset()),
						(int) (y * Tile.TILEHEIGHT - handler.getGameCamera().getyOffset()));
			}
		}

		// render Entities
		entityManager.render(g);
	}

	// tile getter
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.grassTile;

		Tile t = Tile.tiles[tiles[x][y]];
		if (t == null)
			return Tile.grassTile;
		return t;
	}

	// load world
	private void loadWorld(String path) {
		String file = Utils.loadFileAsString(path);
		// split single line string into lines
		String[] tokens = file.split("\\s+");
		// redefined parseInt, as the string uses integers for tile types
		width = Utils.parseInt(tokens[0]);
		height = Utils.parseInt(tokens[1]);
		spawnX = Utils.parseInt(tokens[2]);
		spawnY = Utils.parseInt(tokens[3]);

		tiles = new int[width][height];
		// extract each tile type from the rows
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (Utils.parseInt(tokens[(x + y * width) + 4]) == 3) {
					spawnMonster.add(new Point(x, y));
					
				}
				if(Utils.parseInt(tokens[(x + y * width) + 4]) == 4){
					spawnTree.add(new Point(x, y));
				}
				tiles[x][y] = Utils.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}

	// getters and setters

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
