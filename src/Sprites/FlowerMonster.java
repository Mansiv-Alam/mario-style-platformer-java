package Sprites;

import Game.GameController;

import java.awt.*;

public class FlowerMonster extends Enemy{

    private double dblFlowerTimer;
    private boolean blnActive;


    public FlowerMonster(int x, int y){
        super(x,y);
        dblFlowerTimer = System.currentTimeMillis();
    }

    @Override
    public void draw(Graphics g){
        if (blnActive){
            g.setColor(new Color(57, 57, 57));
            g.fillRect((int)dblX, (int)dblY, intWidth, intHeight);
        }
        else {
            g.setColor(Color.GREEN);
            g.fillRect((int)dblX, (int)dblY, intWidth, intHeight);
        }
    }
    @Override
    public void collidesWith(GameController gmc, Player player){
        // Hit obstacle from the left
        if ((int)player.getPrevX() + player.getWidth() <= (int)dblX) {
            player.setPlayerPosition((int)dblX - player.getWidth(), player.getY());
            player.stopHorizontalVel();
        }
        // Hit obstacle from the right
        else if ((int)player.getPrevX() >= (int)dblX + intWidth) {
            player.setPlayerPosition((int)dblX + intWidth, player.getY());
            player.stopHorizontalVel();
        }
    }
    @Override
    public void move(){
        // 5 Second delay
        if (!blnActive && System.currentTimeMillis() - dblFlowerTimer >= 1000){
            blnActive = true;
            dblFlowerTimer = System.currentTimeMillis();
        }
        else if (blnActive && System.currentTimeMillis() - dblFlowerTimer >= 1000){
            blnActive = false;
            dblFlowerTimer = System.currentTimeMillis();
        }
    }
    @Override
    public void onStomp(GameController gmc, int index, Player player){
        if (blnActive){
            player.takeDamage();
        }
        else {
            player.setPlayerPosition(player.getX(), dblY - player.getHeight());
            player.stopVerticalVel();
            player.onPlatform();
        }
    }
    @Override
    public void onHitWithFireball() {

    }
}
