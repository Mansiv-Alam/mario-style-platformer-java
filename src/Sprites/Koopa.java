package Sprites;

import Game.GameController;

import java.awt.*;

public class Koopa extends Enemy{

    private double dblStartX;
    private int intRange = 100;
    private int intDirection = 1,dblShellDirection;
    private double dblSpeed = 0.5, dblShellSpeed = 2;

    private boolean blnInShell, blnIsMoving;

    public Koopa(int x, int y){
        super(x,y);
        this.dblStartX = x;
    }

    @Override
    public void draw(Graphics g){
        if (blnInShell) {
            g.setColor(Color.GREEN);
            g.fillRect((int) dblX, (int) dblY, intWidth, intHeight); // shell smaller
        } else {
            g.setColor(new Color(207, 200, 143));
            g.fillRect((int) dblX, (int) dblY, intWidth, intHeight);
        }
    }
    @Override
    public void collidesWith(GameController gmc, Player player){
        player.onPlatform();
    }
    @Override
    public void move(){
        if (!blnInShell) {
            // normal walking
            dblX += dblSpeed * intDirection;

            if (dblX > dblStartX + intRange) intDirection = -1;
            else if (dblX < dblStartX - intRange) intDirection = 1;

        } else if (blnIsMoving) {
            // sliding shell
            dblX += dblShellSpeed * dblShellDirection;
        }
    }
    @Override
    public void onStomp(GameController gmc, int index){
        if (!blnInShell) {
            blnInShell = true;   // hide in shell
            blnIsMoving = false; // initially stationary
        } else {
            // shell kicked
            blnIsMoving = true;
            dblShellDirection = 2 * (int)(Math.random() * 2) -1;
        }
    }
    @Override
    public void onHitWithFireball() {

    }
}