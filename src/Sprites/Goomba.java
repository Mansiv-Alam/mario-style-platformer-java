package Sprites;

import Game.GameController;

import java.awt.*;

public class Goomba extends Enemy{

    private double dblStartX;
    private int intRange = 100;
    private int intDirection = 1;
    private double dblSpeed = 0.5;

    public Goomba(int x, int y){
        super(x,y);
        this.intHeight = 100;
        this.intWidth = 50;
        this.dblStartX = x;
    }

    @Override
    public void draw(Graphics g){
        g.setColor(new Color(184, 117, 44));
        g.fillRect((int)dblX, (int)dblY, intWidth, intHeight);
    }
    @Override
    public void collidesWith(GameController gmc, Player player){
        player.takeDamage();
    }
    @Override
    public void move(){
        dblX += dblSpeed * intDirection;

        // Turns around at the end of the range
        if (dblX >= dblStartX + intRange){
            intDirection = -1;
        }
        else if (dblX <= dblStartX - intRange){
            intDirection = 1;
        }

    }
    @Override
    public void onStomp(GameController gmc, int index, Player player){
        gmc.removeEnemy(index);
        player.bounce();
    }
    @Override
    public void onHitWithFireball() {

    }
}
