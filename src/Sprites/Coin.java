package Sprites;

import javax.swing.*;
import java.awt.*;

public class Coin {
    private int intX;
    private int intY;
    private int intSize = 50;
    // Animations
    private int intFrame = 0;
    private final int intFrameDelay = 300;
    private long lastFrameTime;
    private boolean blnFromBlock, blnActive;
    private long spawnTime;
    private final int intDisappearDelay = 2000;

    Image[] coinImages = new Image[4];
    Image displayImage;

    public Coin(int x, int y, boolean blnFromBlock){
        this.intX = x;
        this.intY = y;
        this.blnFromBlock = blnFromBlock;
        if (blnFromBlock){
            spawnTime = System.currentTimeMillis();
        }
        blnActive = true;
        loadImages();
    }

    public void loadImages(){
        int[] intAnimationSequence = {1, 2, 1, 3};

        for (int i = 0; i < intAnimationSequence.length; i++){
            Image img = new ImageIcon("src/Coin_" + intAnimationSequence[i] + ".png").getImage();
            // Resizes the image
            coinImages[i] = img.getScaledInstance(intSize, intSize, Image.SCALE_SMOOTH);
        }

        displayImage = coinImages[0];
    }

    // Accessor Methods
    public int getX(){
        return this.intX;
    }
    public int getY(){
        return this.intY;
    }
    // Collisions
    public Rectangle getBounds(){
        return new Rectangle(intX, intY, intSize, intSize);
    }

    public void draw(Graphics g){
        if (!blnActive){return;} // don't display coins that aren't active
            g.drawImage(displayImage, intX, intY, null);
            updateAnimation();
        checkDisappearTiming();
    }

    public void updateAnimation(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= intFrameDelay) {
            intFrame = (intFrame + 1) % coinImages.length; // loop through frames
            displayImage = coinImages[intFrame];
            lastFrameTime = currentTime;
        }
    }
    public void checkDisappearTiming(){
        if (blnFromBlock){
            long currentTime = System.currentTimeMillis();
            if (currentTime - spawnTime >= intDisappearDelay){
                blnActive = false;

            }
        }
    }
}
