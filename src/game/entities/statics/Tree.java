package game.entities.statics;

import java.awt.Graphics;
import game.Handler;
import game.gfx.Assets;
import game.tiles.Tile;

public class Tree extends StaticEntity {

	//constructor
	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, Tile.TILEWIDTH, Tile.TILEHEIGHT * 2);
		//set bounds
		bounds.x = 10;
		bounds.y = (int) (height / 1.5f);
		bounds.width = width - 20;
		bounds.height = (int) (height - height / 1.5f);
	}

	@Override
	public void tick() {	
	}
	
	@Override
	public void die(){
	}

	//render tree image, draw on map
	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
	}

}
