package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.character.enemy.ai.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class khiem extends Enemy {
    public khiem(int x, int y, Board board) {
        super(x, y, board, Sprite.khiem_dead, Game.getBomberSpeed() / 2, 100);

        _sprite = Sprite.khiem_left1;

        _ai = new AILow();
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                _sprite = Sprite.movingSprite(Sprite.khiem_right1, Sprite.khiem_right2, Sprite.khiem_right3, _animate, 60);
                break;
            case 2:
            case 3:
                _sprite = Sprite.movingSprite(Sprite.khiem_left1, Sprite.khiem_left2, Sprite.khiem_left3, _animate, 60);
                break;
        }
    }
}
