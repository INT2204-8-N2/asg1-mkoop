package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIOneal extends AI {
    Bomber _bomber;
    Enemy _e;

    public AIOneal(Bomber bomber, Enemy e) {
        _bomber = bomber;
        _e = e;
    }

    protected int calculateCol(){
        if(_bomber.getYTile() < _e.getYTile()) return 0;
        else if(_bomber.getYTile() > _e.getYTile()) return 2;
        return -1;
    }

    protected int calculateRow(){
        if(_bomber.getXTile() < _e.getXTile()) return 3;
        else if(_bomber.getXTile() > _e.getXTile()) return 1;
        return -1;
    }
    @Override
    public int calculateDirection() {
        // TODO: cài đặt thuật toán tìm đường đi
        if(_bomber == null) return random.nextInt(4);

        int vertical = random.nextInt(2);
        if(vertical == 1){
            if(calculateRow() != -1){
                return calculateRow();
            }
            else return calculateCol();
        }
        else {
            if(calculateCol() != 1){
                return calculateCol();
            }
            else return calculateRow();
        }
    }
}
