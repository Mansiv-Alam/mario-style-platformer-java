package Sprites;

import java.awt.*;

public class Flower {

    private int intX;
    private int intY;
    private int intHeight = 60, intWidth = 50;
    private boolean blnActive = false;
    private long displayTime;

    public Flower(int x, int y){
        this.intX = x;
        this.intY = y;
        displayTime = System.currentTimeMillis();
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
        return new Rectangle(intX, intY, intWidth, intHeight);
    }

    public void draw(Graphics g){
        if (blnActive){
            g.setColor(Color.RED);
            g.fillRect(intX, intY, intWidth, intHeight);
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
