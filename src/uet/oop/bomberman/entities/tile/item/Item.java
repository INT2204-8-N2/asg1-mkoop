package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.PlayAudio;

public abstract class Item extends Tile {

	protected int _duration = -1; // -1 is infinite, duration in lifes
	protected boolean _active = false;
	protected int _level;
	protected PlayAudio selectitem;

	public Item(int x, int y, Sprite sprite) {
		super(x, y, sprite);
		_level = 1;
	}


	public abstract void setValues();

	public void playSelecItem() {
		selectitem = new PlayAudio("/music/selecItem.wav");
		selectitem.play();
	}
	public void removeLive() {
		if(_duration > 0)
			_duration--;

		if(_duration == 0)
			_active = false;
	}

	public int getDuration() {
		return _duration;
	}

	public int getLevel() {
		return _level;
	}

	public void setDuration(int duration) {
		this._duration = duration;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		this._active = active;
	}
}
