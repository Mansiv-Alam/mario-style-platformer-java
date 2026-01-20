package Sprites;

import javax.swing.*;
import java.awt.*;

public class Fireball {
    // Variables
    private double dblX, dblY, dblPrevY;
    private final double GRAVITY = 0.1;
    private final double BOUNCE = -4;
    private double velX, velY = 0;
    private int intSize = 20, intBounceCount = 0;
    private boolean blnActive = true;
    private Image fireballImage;

    // Constructor
    public Fireball(double dblStartX, double dblStartY, int intDirection){
        this.dblX = dblStartX;
        this.dblY = dblStartY;
        this.velX = 2 * intDirection; // Bases velocity off direction
        fireballImage = new ImageIcon("src/Resources/Fireball.png").getImage();
    }
    // Accessor Methods
    public double getY() {
        return this.dblY;
    }
    public double getX() {
        return this.dblX;
    }
    public double getPrevY(){
        return this.dblPrevY;
    }
    public int getHeight() {
        return this.intSize;
    }
    public int getSize(){
        return  this.intSize;
    }
    public boolean isActive() {
        return this.blnActive;
    }
    public void setVelY(double dblNewVelY){
        this.velY = dblNewVelY;
    }
    public void setPositionY(double dblNewY){
        this.dblY = dblNewY;
    }

    public void update(){
        dblPrevY = dblY;
        // Change the position of the fireball
        velY += GRAVITY;
        dblX += velX;
        dblY += velY;

        // bounce on ground
        if (dblY + intSize >= 863) {
            dblY = 863 - intSize;
            velY = BOUNCE;
            incrementBounceCount();
            // Destroy the fireball if it has bounced for 3 times
            if (intBounceCount >= 3) {
                destroyFireball();
            }
        }
    }
    public void incrementBounceCount(){
        intBounceCount++;
    }
    public void draw(Graphics g) {
        g.drawImage(fireballImage, (int)dblX, (int)dblY,null);
    }
    // Get the rectangle of the fireball for easy collision checks
    public Rectangle getBounds() {
        return new Rectangle((int)dblX, (int)dblY, intSize, intSize);
    }
    public void destroyFireball() {
        blnActive = false;
    }
    public int getBounce() {
        return this.intBounceCount;
    }
}
