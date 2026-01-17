package Sprites;

import Game.GameController;

import java.awt.*;

public class Goomba extends Enemy{

    public Goomba(int x, int y){
        super(x,y);
    }

    @Override
    public void draw(Graphics g){
        g.setColor(new Color(184, 117, 44));
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
