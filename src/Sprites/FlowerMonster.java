package Sprites;

import Game.GameController;

import java.awt.*;

public class FlowerMonster extends Enemy{

    public FlowerMonster(int x, int y){
        super(x,y);
    }

    @Override
    public void draw(Graphics g){
        g.setColor(Color.GREEN);
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
    public void onStomp(GameController gmc, int index){

    }
    @Override
    public void onHitWithFireball() {

    }
}
