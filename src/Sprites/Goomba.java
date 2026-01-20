package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class Goomba extends Enemy{

    private double dblStartX;
    private int intRange = 100;
    private int intDirection = -1;
    private double dblSpeed = 0.1;
    private final Image[] goombaImagesLeft = new Image[8];
    private final Image[] goombaImagesRight = new Image[7];
    private Image imgDisplayed;
    // Animation
    private final int turnDelay = 200;
    private long turnStartTime;
    private boolean blnTurning = false;

    public Goomba(int x, int y){
        super(x,y, 50, 50);
        this.dblStartX = x;
        loadImages();
    }
    public void loadImages(){
        int[] intAnimationSequence = {1, 3, 4, 3, 5, 3, 2,1};
        
        for (int i = 0; i < intAnimationSequence.length; i++) {
            // Gets the image from the source files
            Image img = new ImageIcon("src/Resources/Goomba_" + intAnimationSequence[i] + ".png").getImage();
            // Resizes the image
            goombaImagesLeft[i] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
        }
        intAnimationSequence = new int[]{6, 7, 8, 7, 9, 7, 1};

        for (int i = 0; i < intAnimationSequence.length; i++) {
            // Gets the image from the source files
            Image img = new ImageIcon("src/Resources/Goomba_" + intAnimationSequence[i] + ".png").getImage();
            // Resizes the image
            goombaImagesRight[i] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
        }

        // Defaults to setting to looking to the left
        imgDisplayed = goombaImagesLeft[5];
    }

    @Override
    public void draw(Graphics g){
        g.drawImage(imgDisplayed, (int)dblX, (int)dblY, intWidth, intHeight, null);
    }
    @Override
    public void collidesWith(GameController gmc, Player player){
        player.takeDamage();
    }
    @Override
    public void move(){
        // Stops movement if the goomba is turning
        if (blnTurning) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - turnStartTime >= turnDelay) {
                blnTurning = false;
                intDirection *= -1; // reverse direction after pause
            }
            // Do not move while turning
            return;
        }

        dblX += dblSpeed * intDirection;

        // Turns around at the end of the range
        if (dblX >= dblStartX + intRange){
            blnTurning = true;
            turnStartTime = System.currentTimeMillis();
            dblX = dblStartX + intRange; // snap exactly to end
        }
        else if (dblX <= dblStartX - intRange){
            blnTurning = true;
            turnStartTime = System.currentTimeMillis();
            dblX = dblStartX - intRange; // snap exactly to end
        }

        updateAnimations();
    }
    @Override
    public void onStomp(GameController gmc, int index, Player player){
        gmc.removeEnemy(index);
        gmc.increaseScore();
        player.bounce();
    }
    @Override
    public void onHitWithFireball(GameController gmc, int index, Player player) {
        gmc.removeEnemy(index);
        gmc.increaseScore();
    }
    public void updateAnimations(){
        if (blnTurning) {
            return;
        }

        double dblDistanceFromLeftEnd = dblX - (dblStartX - intRange);
        double dblFraction = dblDistanceFromLeftEnd / (intRange * 2); // Calculates the distance moved in percentage
        dblFraction = Math.min(Math.max(dblFraction, 0), 1);

        // Pick frame from left or right depending on direction
        if (intDirection == 1) {
            int intFrameIndex = (int)(dblFraction * goombaImagesRight.length); // Transitions the distance moved to the animation frame
            intFrameIndex = Math.min(intFrameIndex, goombaImagesRight.length - 1);
            imgDisplayed = goombaImagesRight[intFrameIndex];
        } else {
            int intFrameIndex = (int)(dblFraction * goombaImagesLeft.length);
            intFrameIndex = Math.min(intFrameIndex, goombaImagesLeft.length - 1);
            imgDisplayed = goombaImagesLeft[intFrameIndex];
        }
    }
}
