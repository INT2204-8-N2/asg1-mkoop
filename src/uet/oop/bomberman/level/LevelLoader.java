package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.exceptions.LoadLevelException;

/**
 * Load và lưu trữ thông tin bản đồ các màn chơi
 */
public abstract class LevelLoader {

	protected int _width, _height; // default values just for testing
	protected int _level;
	protected String[] _lineTiles;
	protected Board _board;

	public LevelLoader(String path, Board board) throws LoadLevelException {
		_board = board;
		loadLevel(path);
	}

	public abstract void loadLevel(String path) throws LoadLevelException;

	public abstract void createEntities();

	public int getWidth() {
		return _width;
	}

	public int getHeight() {
		return _height;
	}

	public void setLevel(){
		_level++;
	}
	public int getLevel() {
		return _level;
	}

}
