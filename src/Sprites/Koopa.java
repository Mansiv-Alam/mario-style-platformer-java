package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class Koopa extends Enemy{

    private double dblStartX;
    private int intRange = 75;
    private int intDirection = 1;
    private double dblSpeed = 0.18, dblShellSpeed = 1.5, dblShellDirection;
    private long stompTimer;
    private boolean blnInShell, blnIsMoving;
    // Animations
    Image[] shellImages = new Image[4];
    Image[] KoopaImagesLeft = new Image[5];
    Image[] KoopaImagesRight = new Image[5];

    Image imgDisplayed;
    private int intFrame = 0;
    private long lastFrameTime;
    private final int intFrameDelay = 400;

    public Koopa(int x, int y){
        super(x,y, 58, 90);
        this.dblStartX = x;
        loadImages();
    }

    public void loadImages(){
        int[] intAnimationSequence = {1, 3, 4, 2};

        for (int i = 0; i < intAnimationSequence.length; i++){
            // Gets the image from the source files
            Image img = new ImageIcon("src/KoopaShell_" + intAnimationSequence[i] + ".png").getImage();
            // Resizes the image
            shellImages[i] = img.getScaledInstance(68, 60, Image.SCALE_SMOOTH);
        }

        intAnimationSequence = new int[]{1, 2, 1, 3, 1};
        // Left walking animations
        for (int i = 0; i < intAnimationSequence.length; i++){
            Image img = new ImageIcon("src/Koopa_" + intAnimationSequence[i] + ".png").getImage();
            KoopaImagesLeft[i] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
        }

        intAnimationSequence = new int[]{4, 5, 4, 6, 4};
        // Right walking animations
        for (int i = 0; i < intAnimationSequence.length; i++){
            Image img = new ImageIcon("src/Koopa_" + intAnimationSequence[i] + ".png").getImage();
            KoopaImagesRight[i] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
        }

        imgDisplayed = shellImages[0];
    }
    @Override
    public void draw(Graphics g){
        if (blnInShell) {
            if (dblShellDirection != 0){
                updateShellAnimations();
            }
            else {
                imgDisplayed = shellImages[0];
            }
            g.drawImage(imgDisplayed, (int)dblX, (int)dblY, intWidth, intHeight, null);
        } else {
            updateWalkingAnimations();
            g.drawImage(imgDisplayed, (int)dblX, (int)dblY, intWidth, intHeight, null);
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
            gmc.increaseScore();
        }
    }
    public void updateWalkingAnimations(){
        double dblDistanceFromLeftEnd = dblX - (dblStartX - intRange);
        double dblFraction = dblDistanceFromLeftEnd / (intRange * 2); // Calculates the distance moved in percentage
        dblFraction = Math.min(Math.max(dblFraction, 0), 1);

        // Pick frame from left or right depending on direction
        if (intDirection == 1) {
            int intFrameIndex = (int)(dblFraction * KoopaImagesRight.length); // Transitions the distance moved to the animation frame
            intFrameIndex = Math.min(intFrameIndex, KoopaImagesRight.length - 1);
            imgDisplayed = KoopaImagesRight[intFrameIndex];
        } else {
            int intFrameIndex = (int)(dblFraction * KoopaImagesLeft.length);
            intFrameIndex = Math.min(intFrameIndex, KoopaImagesLeft.length - 1);
            imgDisplayed = KoopaImagesLeft[intFrameIndex];
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