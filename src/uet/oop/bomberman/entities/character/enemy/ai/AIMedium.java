package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

import java.util.Random;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	
	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		if(_bomber == null){
			Random random = new Random();
			return random.nextInt(4);
		}
		Random random = new Random();

		if(_bomber.getX() > _e.getX() && _bomber.getY() > _e.getY()) {
			if (random.nextBoolean())
				return 1;
			else
				return 2;
		}
		if(_bomber.getX() <= _e.getX()&& _bomber.getY() <= _e.getY()) {
			if (random.nextBoolean())
				return 3;
			else
				return 0;
		}
		if(_bomber.getX() <= _e.getX() && _bomber.getY() > _e.getY()){
			if(random.nextBoolean())
				return 3;
			else
				return 2;
		}

		if(random.nextBoolean())
			return 1;
		else
			return 0;
	}

}
