package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.character.enemy.ai.AIMedium;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Oneal2 extends Enemy {
    public Oneal2(int x, int y, Board board) {
        super(x, y, board, Sprite.balloom_dead, Game.getBomberSpeed() / 2, 100);

        _sprite = Sprite.balloom_left1;

        _ai = new AIMedium(_board.getBomber(), this);
        _direction = _ai.calculateDirection();
    }

    @Override
    protected void chooseSprite() {
        switch(_direction) {
            case 0:
            case 1:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 60);
                else
                    _sprite = Sprite.oneal_left1;
                break;
            case 2:
            case 3:
                if(_moving)
                    _sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, _animate, 60);
                else
                    _sprite = Sprite.oneal_left1;
                break;
        }
    }
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        double xr = _x, yr = _y - 16;


        if(_direction == 0) { yr += _sprite.getSize() -1 ; xr += _sprite.getSize()/2; }
        if(_direction == 1) {yr += _sprite.getSize()/2; xr += 1;}
        if(_direction == 2) { xr += _sprite.getSize()/2; yr += 1;}
        if(_direction == 3) { xr += _sprite.getSize() -1; yr += _sprite.getSize()/2;}

        int xx = Coordinates.pixelToTile(xr) +(int)x;
        int yy = Coordinates.pixelToTile(yr) +(int)y;

        Entity a = _board.getEntity(xx, yy, this); // entity tai vi tri muon di

        if(a instanceof Wall)
            return false;
        return a.collide(this);
    }
    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Bomber
        if(e instanceof Flame) {
            kill();
            return false;
        }

        if(e instanceof Bomber) {
            ((Bomber) e).kill();
            return false;
        }

        return true;
    }

}
