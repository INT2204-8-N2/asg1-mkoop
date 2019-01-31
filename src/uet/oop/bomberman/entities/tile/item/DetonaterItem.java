package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class DetonaterItem extends Item{

        public DetonaterItem(int x, int y, Sprite sprite) {
            super(x, y, sprite);
        }

        @Override
        public boolean collide(Entity e) {

            if(e instanceof Bomber) {
                playSelecItem();
                ((Bomber) e).addPowerup(this);
                remove();
                return true;
            }

            return false;
        }

    @Override
    public void setValues() {
        _active = true;
        Game.addDetonater();
    }
}