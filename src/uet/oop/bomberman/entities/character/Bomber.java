package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.PlayAudio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    private static int lives;
    private PlayAudio explosive;

    /**
     * nếu giá trị này < 0 thì cho phép đặt đối tượng Bomb tiếp theo,
     * cứ mỗi lần đặt 1 Bomb mới, giá trị này sẽ được reset về 0 và giảm dần trong mỗi lần update()
     */
    protected int _timeBetweenPutBombs = 0;

    public static List<Item> _items = new ArrayList<Item>();

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
        explosive = new PlayAudio("/music/tiengbom.wav");
        setLive(3);
    }

    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

    void setLive(int x) {
        lives = x;
    }

    public static int getLive() {
        return lives;
    }

    /**
     * Kiểm tra xem có đặt được bom hay không? nếu có thì đặt bom tại vị trí hiện tại của Bomber
     */
    private void detectPlaceBomb() {
        // TODO: kiểm tra xem phím điều khiển đặt bom có được gõ và giá trị _timeBetweenPutBombs, Game.getBombRate() có thỏa mãn hay không
        // TODO:  Game.getBombRate() sẽ trả về số lượng bom có thể đặt liên tiếp tại thời điểm hiện tại
        // TODO: _timeBetweenPutBombs dùng để ngăn chặn Bomber đặt 2 Bomb cùng tại 1 vị trí trong 1 khoảng thời gian quá ngắn
        // TODO: nếu 3 điều kiện trên thỏa mãn thì thực hiện đặt bom bằng placeBomb()
        // TODO: sau khi đặt, nhớ giảm số lượng Bomb Rate và reset _timeBetweenPutBombs về 0
        if(_input.space && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {

            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile( (_y + _sprite.getSize() / 2) - _sprite.getSize() ); //subtract half player height and minus 1 y position

            placeBomb(xt,yt);
            Game.addBombRate(-1);
            explosive.play();
            _timeBetweenPutBombs = 30;
        }
    }
    protected void placeBomb(int x, int y) {
        // TODO: thực hiện tạo đối tượng bom, đặt vào vị trí (x, y)
        Bomb bom = new Bomb(x,y, _board);
        _board.addBomb(bom);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: xử lý nhận tín hiệu điều khiển hướng đi từ _input và gọi move() để thực hiện di chuyển
        int xa = 0, ya = 0;
        if(_input.up) ya--;
        if(_input.down) ya++;
        if(_input.left) xa--;
        if(_input.right) xa++;

        if(xa != 0 || ya != 0)  {
            move(xa * Game.getBomberSpeed(), ya * Game.getBomberSpeed());
            _moving = true;
        } else {
            _moving = false;
        }

        // TODO: nhớ cập nhật lại giá trị cờ _moving khi thay đổi trạng thái di chuyển
    }

    @Override
    public boolean canMove(double x, double y) {
        // TODO: kiểm tra có đối tượng tại vị trí chuẩn bị di chuyển đến và có thể di chuyển tới đó hay không
        //TODO: size của bomber là 11x13
            double xt = _x + x;
            double yt = _y - Game.TILES_SIZE + y;
            Entity a;

            a = _board.getEntity(xt/Game.TILES_SIZE, (yt+8)/Game.TILES_SIZE, this);
            if(!a.collide(this))  return false;

            a = _board.getEntity((xt+10)/Game.TILES_SIZE, (yt+8)/Game.TILES_SIZE, this);
            if(!a.collide(this))  return false;

            a = _board.getEntity((xt)/Game.TILES_SIZE, (yt+15)/Game.TILES_SIZE, this);
            if(!a.collide(this))  return false;

            a = _board.getEntity((xt+10)/Game.TILES_SIZE, (yt+15)/Game.TILES_SIZE, this);
            if(!a.collide(this))  return false;

        return true;
    }

    @Override
    public void move(double xa, double ya) {
        // TODO: sử dụng canMove() để kiểm tra xem có thể di chuyển tới điểm đã tính toán hay không và thực hiện thay đổi tọa độ _x, _y
       if(xa>0&&ya==0) {
           if (canMove(xa, ya)) {
               _x += xa;
               _direction = 1;
           }
       }
       if(xa<0&&ya==0) {
           if (canMove(xa, ya)) {
               _x += xa;
               _direction = 3;
           }
       }
       if(xa==0&&ya>0) {
           if(canMove(xa,ya)) {
               _y+=ya;
               _direction = 2;
           }
       }

       if(xa==0&&ya<0) {
           if(canMove(xa, ya)) {
               _y+=ya;
               _direction = 0;
           }
       }
        // TODO: nhớ cập nhật giá trị _direction sau khi di chuyển

    }

    public Bomber getBomber() {
        return this;
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: xử lý va chạm với Flame
        // TODO: xử lý va chạm với Enemy
        if(e instanceof Flame) {
            if(lives > 1) lives--;
            else {
                kill();
                lives--;
            }
            return false;
        }
        if(e instanceof Enemy) {
            if(lives > 1) {
                lives--;
                ((Enemy) e).kill();
            }
            else {
                kill();
                lives--;
            }
            return false;
        }

        return true;
    }

    public void dregreAlive() {
        lives --;
    }

    public static void addAlive() {
        lives ++;
    }

    public void addPowerup(Item p) {
        if(p.isRemoved()) return;

        _items.add(p);

        p.setValues();
    }

    public void clearUsedPowerups() {
        Item p;
        for (int i = 0; i < _items.size(); i++) {
            p = _items.get(i);
            if(p.isActive() == false)
                _items.remove(i);
        }
    }

    public void removePowerups() {
        for (int i = 0; i < _items.size(); i++) {
            _items.remove(i);
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Mob Sprite
    |--------------------------------------------------------------------------
     */
    private void chooseSprite() {
        switch(_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if(_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if(_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if(_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if(_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if(_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
}
