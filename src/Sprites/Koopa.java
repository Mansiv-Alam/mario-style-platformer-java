package Sprites;

import Game.GameController;

import java.awt.*;

public class Koopa extends Enemy{

    public Koopa(int x, int y){
        super(x,y);
    }

    @Override
    public void draw(Graphics g){
        g.setColor(new Color(207, 200, 143));
        g.fillRect((int)dblX, (int)dblY, intWidth, intHeight);
    }
    @Override
    public void collidesWith(GameController gmc, Player player){
        player.onPlatform();
    }
    @Override
    public void move(){

    }
    @Override
    public void onStomp(){

    }
    @Override
    public void onHitWithFireball() {

    }
}