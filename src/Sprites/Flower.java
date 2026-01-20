package Sprites;

import javax.swing.*;
import java.awt.*;

public class Flower {

    private int intX;
    private int intY;
    private boolean blnActive = false;
    private long displayTime;
    private Image flowerImage;

    public Flower(int x, int y){
        this.intX = x;
        this.intY = y;
        displayTime = System.currentTimeMillis();

        flowerImage = new ImageIcon("src/Resources/Fireflower.png").getImage();
        // Resizes the image
        //flowerImage = img.getScaledInstance(50, 52, Image.SCALE_SMOOTH);
    }

    // Accessor Methods
    public int getX(){
        return this.intX;
    }
    public int getY(){
        return this.intY;
    }

    public void draw(Graphics g){
        if (blnActive){
            g.drawImage(flowerImage, intX, intY, 50, 52, null);
        }
        updateAnimation();
    }
    public void activateFlower(){
        blnActive = true;
    }

    public void updateAnimation(){
        if (System.currentTimeMillis() - displayTime >= 2000){
            blnActive = false;
        }
    }
}
