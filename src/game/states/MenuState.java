package game.states;

import java.awt.Graphics;

import game.Handler;
import game.gfx.Assets;
import game.ui.ClickListener;
import game.ui.UIImageButton;
import game.ui.UIManager;

public class MenuState extends State {

	private UIManager uiManager;

	public MenuState(Handler handler) {
		super(handler);
		uiManager = new UIManager(handler);
		handler.getMouseManager().setUIManager(uiManager);

		//open menu 
		uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.btn_start, new ClickListener() {
			@Override
			public void onClick() {
				handler.getMouseManager().setUIManager(null);
				State.setState(handler.getGame().gameState);
			}
		}));
	}

	@Override
	public void tick() {
		uiManager.tick();

	}

	@Override
	public void render(Graphics g) {
		//render user interface graphics
		uiManager.render(g);
	}

}
