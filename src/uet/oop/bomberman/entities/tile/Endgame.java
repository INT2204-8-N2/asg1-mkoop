package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

    public class Endgame extends Tile {

        protected Board _board;

        public Endgame(int x, int y, Board board, Sprite sprite) {
            super(x, y, sprite);
            _board = board;
        }

        @Override
        public boolean collide(Entity e) {

            if(e instanceof Bomber) {

                if(!_board.detectNoEnemies()) return false;

                else _board.endGame();

                return true;
            }

            return false;
        }

}
