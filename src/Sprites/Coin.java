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

    Image[] coinImages = new Image[3];
    Image displayImage;

    public Coin(int x, int y){
        this.intX = x;
        this.intY = y;

        loadImages();
    }

    public void loadImages(){
        for (int i = 0; i < 3; i++){
            Image img = new ImageIcon("src/Coin_" + (i + 1)  +".png").getImage();
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
        g.drawImage(displayImage, intX, intY, null);

        updateAnimation();
    }

    public void updateAnimation(){
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameTime >= intFrameDelay) {
            intFrame = (intFrame + 1) % coinImages.length; // loop through frames
            displayImage = coinImages[intFrame];
            lastFrameTime = currentTime;
        }
    }
}
