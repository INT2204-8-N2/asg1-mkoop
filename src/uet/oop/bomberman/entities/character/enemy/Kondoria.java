package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.ai.AIHigh;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {

        public Kondoria(int x, int y, Board board) {
            super(x, y, board, Sprite.kondoria_dead, Game.getBomberSpeed() * 0.4, 800);

            _sprite = Sprite.kondoria_right1;

            _ai = new AIHigh(_board.getBomber(), this);
            _direction  = _ai.calculateDirection();
        }

        @Override
        protected void chooseSprite() {
            switch(_direction) {
                case 0:
                case 1:
                    if(_moving)
                        _sprite = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, _animate, 60);
                    else
                        _sprite = Sprite.kondoria_left1;
                    break;
                case 2:
                case 3:
                    if(_moving)
                        _sprite = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, _animate, 60);
                    else
                        _sprite = Sprite.kondoria_left1;
                    break;
            }
        }

        @Override
        public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Bomber
        if(e instanceof Flame) {
            kill();
            _board.addEntity(this.getXTile()+this.getYTile()* _board.getWidth(), new LayeredEntity(this.getXTile(), this.getYTile(),
                    new Grass(this.getXTile() ,this.getYTile(), Sprite.grass),
                    new BombItem(this.getXTile(), this.getYTile(), Sprite.powerup_bombs)
            ));
            return false;
        }

        if(e instanceof Bomber) {
            ((Bomber) e).kill();
            return false;
        }
        return true;
    }

}
