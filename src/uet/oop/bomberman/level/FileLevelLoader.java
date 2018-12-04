package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(String path, Board board) throws LoadLevelException {
		super(path, board);
	}
	
	@Override
	public void loadLevel(String path) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map

		try {


			BufferedReader in = new BufferedReader(
					new FileReader("C:/Users/home/IdeaProjects/bomberman-starter-starter-project-1/res/"  + path));

			String[] data = (in.readLine()).split("\\s", 3);


			_level = Integer.parseInt(data[0]);
			_height = Integer.parseInt(data[1]);
			_width = Integer.parseInt(data[2]);

			_lineTiles = new String[_height];

			for(int i = 0; i < _height; ++i) {
				_lineTiles[i] = in.readLine().substring(0, _width);
			}

			in.close();

		} catch (IOException e) {
			System.out.println(e);
		}

	}



	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int pos = x + y * getWidth();
				switch(_lineTiles[y].charAt(x)) {
					case '#':
						_board.addEntity(pos, new Wall(x, y, Sprite.wall));
						break;
						//Item
					case 'b':
						LayeredEntity layer = new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new Brick(x ,y, Sprite.brick));
						if(_board.isItem(x, y, _level) == false) {
							layer.addBeforeTop(new BombItem(x, y, _level, Sprite.powerup_bombs));
						}
						_board.addEntity(pos, layer);
						break;
					case 's':
						layer = new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new Brick(x ,y, Sprite.brick));
						if(_board.isItem(x, y, _level) == false) {
							layer.addBeforeTop(new SpeedItem(x, y, _level, Sprite.powerup_speed));
						}


						_board.addEntity(pos, layer);
						break;
					case 'f':
						layer = new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new Brick(x ,y, Sprite.brick));
						if(_board.isItem(x, y, _level) == false) {
							layer.addBeforeTop(new FlameItem(x, y, _level, Sprite.powerup_flames));
						}

						_board.addEntity(pos, layer);
						break;
					case '*':
						_board.addEntity(pos, new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new Brick(x ,y, Sprite.brick)) );
						break;
					case 'x':
						_board.addEntity(pos, new LayeredEntity(x, y,
								new Grass(x ,y, Sprite.grass),
								new Portal(x ,y, _board, Sprite.portal),
								new Brick(x ,y, Sprite.brick)) );
						break;
					case ' ':
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					case 'p':
						_board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
						Screen.setOffset(0, 0);

						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					//Enemies
					case '1':
						_board.addCharacter( new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					case '2':
						_board.addCharacter( new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					case 'k':
						_board.addCharacter( new khiem(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					case 'g':
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						_board.addCharacter( new Ghost(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));

						break;
					case '3':
						_board.addCharacter( new Oneal2(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					case 'd':
						_board.addCharacter( new Doll(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
					default:
						_board.addEntity(pos, new Grass(x, y, Sprite.grass) );
						break;
				}
			}

//				Sprite sprite = y == 0 || x == 0 || x == 30 || y == 12 || (x % 2 == 0 && y % 2 == 0) ? Sprite.wall : Sprite.grass;
//				_board.addEntity(pos, new Grass(x, y, sprite));
			}
		}



}
