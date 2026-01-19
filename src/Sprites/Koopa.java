package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class Koopa extends Enemy{

    private double dblStartX;
    private int intRange = 100;
    private int intDirection = 1;
    private double dblSpeed = 0.5, dblShellSpeed = 2, dblShellDirection;
    private long stompTimer;
    private boolean blnInShell, blnIsMoving;
    // Animations
    Image[] shellImages = new Image[3];

    Image imgDisplayed;
    private int intFrame = 0;
    private long lastFrameTime;
    private final int intFrameDelay = 400;

    public Koopa(int x, int y){
        super(x,y, 40, 100);
        this.dblStartX = x;
        loadImages();
    }

    public void loadImages(){
        for (int i = 0; i < 3; i++){
            // Gets the image from the source files
            Image img = new ImageIcon("src/KoopaShell_" + (i + 1) + ".png").getImage();
            // Resizes the image
            shellImages[i] = img.getScaledInstance(68, 60, Image.SCALE_SMOOTH);
        }
        imgDisplayed = shellImages[0];
    }
    @Override
    public void draw(Graphics g){
        if (blnInShell) {
            if (dblShellDirection != 0){
                updateShellAnimations();
            }
            g.drawImage(imgDisplayed, (int)dblX, (int)dblY, intWidth, intHeight, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect((int) dblX, (int) dblY, intWidth, intHeight); // shell smaller
        }
    }
    @Override
    public void collidesWith(GameController gmc, Player player){
        if (!blnInShell){
            player.takeDamage();
        }
        else if (System.currentTimeMillis() - stompTimer > 500){
            blnIsMoving = true;
            if (player.getDirection() > 0){
                dblShellDirection = 1;
            }
            else {
                dblShellDirection = -1;
            }
        }
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
    public void onStomp(GameController gmc, int index, Player player){
        if (!blnInShell) {
            blnInShell = true;   // hide in shell
            blnIsMoving = false; // initially stationary
            stompTimer = System.currentTimeMillis();
            dblY += 40;
            intWidth = 68;
            intHeight = 60;
            player.bounce();

        } else {
            // shell kicked
            blnIsMoving = true;
            dblShellDirection = 2 * (int)(Math.random() * 2) -1;
        }
    }
    @Override
    public void onHitWithFireball(GameController gmc, int index, Player player) {
        if (!blnInShell){
            gmc.removeEnemy(index);
        }
    }
    public void updateShellAnimations(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= intFrameDelay) {
            intFrame = (intFrame + 1) % shellImages.length;
            imgDisplayed = shellImages[intFrame];
            lastFrameTime = currentTime;
        }
    }
}