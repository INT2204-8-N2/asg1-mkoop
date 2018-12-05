package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class AIDoll extends AI{

    Bomber _bomber;
    Enemy _e;
    ArrayList<Bomb> _bombs;
    Random random = new Random();
    public AIDoll(Enemy e, Board board){
        _bomber = board.getBomber();
        _e = e;
        _bombs= (ArrayList<Bomb>) board.getBombs();
    }
    @Override
    public int calculateDirection() {
        double difX, difY;
        for( int i=0;i<_bombs.size();i++){
            int kc = Game.getBombRadius();
            difX = _e.getXTile() - _bombs.get(i).getX();
            difY = _e.getYTile() - _bombs.get(i).getY();
            if (difX <= kc && difX >= 0) return 1;
            else if (difX >= -kc && difX <= 0) return 3;
            else  if (difY <= kc && difY >= 0) return 2;
            else if (difY >= -kc && difY <= 0) return 0;
        }
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
