package pkg.engine;

/**
 * List of sprites.
 */
public class Spr
{

	public final static String resPath = "resources/";

	//frames_h,frames_v,center_x,center_y
	public final static Sprite peasant = new Sprite("peasant.png", 2, 1, 12, 32),
		kitten = new Sprite("testkitten.png", 1, 1, 40, 40),
		path = new Sprite("path.png", 2, 2, 0, 0),
		tree = new Sprite("tree.png", 1, 1, 24, 59),
		stone = new Sprite("stone.png", 1, 1, 17, 17),
		gunpowder_stone = new Sprite("gunpowder_stone.png", 1, 1, 18, 30),
		inv_items = new Sprite("inv_items.png", 4, 3, 0, 0),
		peasant_items = new Sprite("peasant_items.png", 4, 3, 12, 14),
		terrain = new Sprite("terrain.png", 4, 6, 0, 0),
		menu_buttons = new Sprite("menu_buttons.png", 1, 3, 101, 45),
		timer_buttons = new Sprite("timer_buttons.png", 3, 1, 0, 0),
		button_back = new Sprite("button_back.png", 1, 1, 0, 0),
		gui_buttons = new Sprite("gui_buttons.png", 2, 3, 0, 0),
		recipe_buttons = new Sprite("recipy_buttons.png", 2, 1, 0, 0),
		recipe_gui = new Sprite("recipy_gui.png", 1, 1, 34, 59),
		paper = new Sprite("paper.png", 1, 1, 128, 128),
		timer = new Sprite("timer.png", 1, 1, 1, 17),
		match_results = new Sprite("match_results.png", 1, 3, 257, 100),
		big_shadow = new Sprite("big_shadow.png", 1, 1, 16, 16),
		shadow = new Sprite("shadow.png", 1, 1, 14, 7),
		wood = new Sprite("wood.png", 1, 1, 9, 26),
		blood = new Sprite("blood.png", 1, 1, 9, 26);

}
