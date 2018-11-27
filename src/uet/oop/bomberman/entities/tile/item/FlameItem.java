package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.Game;


public class FlameItem extends Item {

	Game _game;

	public FlameItem(int x, int y,int level, Sprite sprite) {
		super(x, y,level, sprite);
	}


	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý Bomber ăn Item
		if(e instanceof Bomber) {
			((Bomber) e).addPowerup(this);
			remove();
			return true;
		}
		return false;
	}


	@Override
	public void setPower() {

		_game.addBombRadius(1);

	}
}

