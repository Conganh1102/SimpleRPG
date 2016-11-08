package game.gfx;

import java.awt.image.BufferedImage;

public class Assets {

	private static final int width = 32, height = 32;

	public static BufferedImage dirt, grass, stone, tree, rock;
	public static BufferedImage wood;
	public static BufferedImage[] player_down, player_up, player_left, player_right;
	public static BufferedImage[] player2_down, player2_up, player2_left, player2_right;
	public static BufferedImage[] player2_left_up, player2_left_down, player2_right_down, player2_right_up;
	public static BufferedImage[] player_adown, player_aup, player_aleft, player_aright;
	public static BufferedImage[] player2_adown, player2_aup, player2_aleft, player2_aright;
	public static BufferedImage[] zombie_down, zombie_up, zombie_left, zombie_right;
	public static BufferedImage[] btn_start;
	public static BufferedImage[] projectile_hero;
	public static BufferedImage[] kunai;

	// set all the animation frames
	public static void init() {
		SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("res/textures/sheet.png"));
		SpriteSheet sheet2 = new SpriteSheet(ImageLoader.loadImage("res/textures/player.png"));
		SpriteSheet sheet3 = new SpriteSheet(ImageLoader.loadImage("res/textures/kunai.png"));
		SpriteSheet sheet4 = new SpriteSheet(ImageLoader.loadImage("res/textures/zombie.png"));
		//
		projectile_hero = new BufferedImage[4];

		projectile_hero[0] = sheet3.crop(0, 0, 256, 256);
		projectile_hero[1] = new SpriteSheet(ImageLoader.loadImage("res/textures/kunai2.png")).crop(0, 0, 256, 256);
		projectile_hero[2] = new SpriteSheet(ImageLoader.loadImage("res/textures/kunai3.png")).crop(0, 0, 256, 256);
		projectile_hero[3] = new SpriteSheet(ImageLoader.loadImage("res/textures/kunai4.png")).crop(0, 0, 256, 256);

		kunai = new BufferedImage[4];
		kunai[0] = (new SpriteSheet(ImageLoader.loadImage("res/textures/kiem.png"))).crop(0, 0, 800, 600);
		kunai[1] = new SpriteSheet(ImageLoader.loadImage("res/textures/kiem2.png")).crop(0, 0, 800, 600);
		kunai[2] = new SpriteSheet(ImageLoader.loadImage("res/textures/kiem3.png")).crop(0, 0, 800, 600);
		kunai[3] = new SpriteSheet(ImageLoader.loadImage("res/textures/kiem4.png")).crop(0, 0, 800, 600);
		wood = sheet.crop(width, height, width, height);

		// get picture from texture sheet
		btn_start = new BufferedImage[2];
		btn_start[0] = sheet.crop(width * 6, height * 4, width * 2, height);
		btn_start[1] = sheet.crop(width * 6, height * 5, width * 2, height);

		// get picture from texture sheet
		player_down = new BufferedImage[3];
		player_up = new BufferedImage[3];
		player_left = new BufferedImage[3];
		player_right = new BufferedImage[3];
		player_adown = new BufferedImage[2];
		player_aup = new BufferedImage[2];
		player_aleft = new BufferedImage[2];
		player_aright = new BufferedImage[2];

		// get picture from texture sheet
		player2_down = new BufferedImage[8];
		player2_up = new BufferedImage[8];
		player2_left = new BufferedImage[8];
		player2_right = new BufferedImage[8];
		
		player2_adown = new BufferedImage[8];
		player2_aup = new BufferedImage[8];
		player2_aleft = new BufferedImage[8];
		player2_aright = new BufferedImage[8];
		
		int number_down = 49;
		int number_up = 17;
		int number_left = 33;
		int number_right = 1;
		int number_adown;
		int number_aup;
		int number_aleft;
		int number_aright;
		for(int i = 0; i < 8; i++){
			player2_down[i] = new SpriteSheet(ImageLoader.loadImage("res/textures/C_113/C_113_00" + number_down +".png")).crop(0, 0, 256, 128);
			player2_up[i] = new SpriteSheet(ImageLoader.loadImage("res/textures/C_113/C_113_00" + number_up +".png")).crop(0, 0, 256, 128);
			player2_left[i] = new SpriteSheet(ImageLoader.loadImage("res/textures/C_113/C_113_00" + number_left +".png")).crop(0, 0, 256, 128);
			player2_right[i] = new SpriteSheet(ImageLoader.loadImage("res/textures/C_113/C_113_000" + number_right +".png")).crop(0, 0, 256, 128);
			number_down ++;
			number_up ++;
			number_left ++;
			number_right ++;
			//player2_aleft[i] = new SpriteSheet(ImageLoader.loadImage("res/textures/C_113/C_113_19" + number_down +".png")).crop(0, 0, 256, 128);
			
		}

		// get picture from texture sheet
		// player_down[0] = sheet.crop(width * 4, 0, width, height);
		// //player_down[0] = sheet2.crop(0, 0, 16, 16);
		// player_down[1] = sheet.crop(width * 5, 0, width, height);
		// player_up[0] = sheet.crop(width * 6, 0, width, height);
		// player_up[1] = sheet.crop(width * 7, 0, width, height);
		// player_right[0] = sheet.crop(width * 4, height, width, height);
		// player_right[1] = sheet.crop(width * 5, height, width, height);
		// player_left[0] = sheet.crop(width * 6, height, width, height);
		// player_left[1] = sheet.crop(width * 7, height, width, height);

		player_down[0] = sheet2.crop(0, 0, 75, 100);
		player_down[1] = sheet2.crop(75, 0, 75, 100);
		player_down[2] = sheet2.crop(75 * 2, 0, 75, 100);
		player_up[0] = sheet2.crop(75 * 3, 0, 74, 100);
		player_up[1] = sheet2.crop(0, 100, 75, 100);
		player_up[2] = sheet2.crop(75, 100, 75, 100);
		player_left[0] = sheet2.crop(75 * 2, 100, 75, 100);
		player_left[1] = sheet2.crop(75 * 3, 100, 74, 100);
		player_left[2] = sheet2.crop(0, 100 * 2, 75, 100);
		player_right[0] = sheet2.crop(75, 100 * 2, 75, 100);
		player_right[1] = sheet2.crop(75 * 2, 100 * 2, 75, 100);
		player_right[2] = sheet2.crop(75 * 3, 100 * 2, 74, 100);

		player_adown[0] = sheet.crop(width * 4, height * 5, width, height);
		player_adown[1] = player_down[0];
		player_aup[0] = sheet.crop(width * 5, height * 5, width, height);
		player_aup[1] = player_up[0];
		player_aright[0] = sheet.crop(width * 4, height * 4, width, height);
		player_aright[1] = player_right[0];
		player_aleft[0] = sheet.crop(width * 5, height * 4, width, height);
		player_aleft[1] = player_left[0];

		// player_adown[0] = sheet4.crop(0, 0, 75, 120);
		// player_adown[1] = player_down[2];
		// player_aup[0] = player_up[2];
		// player_aup[1] = player_up[2];
		// player_aright[0] = sheet4.crop(75 *2, 75, 100, 100);
		// player_aright[1] = player_right[2];
		// player_aleft[0] = sheet4.crop(35, 75, 100, 100);
		// player_aleft[1] = player_left[2];

		// get picture from texture sheet
		zombie_down = new BufferedImage[3];
		zombie_up = new BufferedImage[3];
		zombie_left = new BufferedImage[3];
		zombie_right = new BufferedImage[3];

		// get picture from texture sheet
		zombie_down[0] = sheet4.crop(width * 9, 0, width, height);
		zombie_down[1] = sheet4.crop(width * 10, 0, width, height);
		zombie_down[2] = sheet4.crop(width * 11, 0, width, height);
		zombie_up[0] = sheet4.crop(width * 9, height * 3, width, height);
		zombie_up[1] = sheet4.crop(width * 10, height * 3, width, height);
		zombie_up[2] = sheet4.crop(width * 11, height * 3, width, height);
		zombie_right[0] = sheet4.crop(width * 9, height * 2, width, height);
		zombie_right[1] = sheet4.crop(width * 10, height * 2, width, height);
		zombie_right[2] = sheet4.crop(width * 11, height * 2, width, height);
		zombie_left[0] = sheet4.crop(width * 9, height, width, height);
		zombie_left[1] = sheet4.crop(width * 10, height, width, height);
		zombie_left[2] = sheet4.crop(width * 11, height, width, height);

		// get picture from texture sheet
		dirt = new SpriteSheet(ImageLoader.loadImage("res/textures/tile.png")).crop(32 * 3, 0, 32, 32);
		grass = new SpriteSheet(ImageLoader.loadImage("res/textures/tile.png")).crop(32, 0, 32, 32);
		stone = new SpriteSheet(ImageLoader.loadImage("res/textures/tile.png")).crop(32 * 3, 32 * 2, 32, 32);
		tree = new SpriteSheet(ImageLoader.loadImage("res/textures/tree.png")).crop(64 * 3, 0, 64, 100);
		rock = new SpriteSheet(ImageLoader.loadImage("res/textures/tree.png")).crop(0, 315, 64, 64);
	}

}
