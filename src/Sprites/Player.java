package Sprites;

import javax.swing.*;
import java.awt.*;

public class Player {
    private double dblPositionX;
    private double dblPositionY;
    // For Collisions
    private double dblPrevX, dblPrevY;
    private int intWidth = 116, intHeight = 146;
    // Physics Variable
    private double dblVelocityY = 0, dblVelocityX;
    private double dblJumpPower = -3.5, dblGravityUp = 0.02, dblGravityDown = 0.035, dblMaxFallSpeed = 6,dblSpeed = 0.8;
    // Health
    private int intLives = 2, intPlayerState, intInvincibilityFrames;
    private boolean blnInvincible;
    private long InvincibleTime;

    public boolean blnIsJumping, blnIsFalling;
    // Animation Variables
    private int intAnimationFrame;
    private int intFrameCounter;
    private int intTotalFrames;
    private Image[] playerImages;
    private Image imageDisplayed;

    public Player(double startX, double startY){
        this.dblPositionX = startX;
        this.dblPositionY = startY;

        playerImages = new Image[2];
        loadImages();
    }
    public void loadImages(){

        // Names of the files
        String[] fileNames = {"Mario_Right.png", "Mario_Left.png"};

        for (int i = 0; i < fileNames.length; i++) {
            // Gets the image from the source files
            Image img = new ImageIcon("src/" + fileNames[i]).getImage();
            // Resizes the image
            playerImages[i] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
        }
        // Defaults to setting to looking to the left
        imageDisplayed = playerImages[0];
    }

    // Accessor methods
    public double getX(){
        return this.dblPositionX;
    }
    public double getY(){
        return this.dblPositionY;
    }
    public double getPrevX(){
        return this.dblPrevX;
    }
    public double getPrevY(){
        return this.dblPrevY;
    }
    public int getHeight(){
        return this.intHeight;
    }
    public int getWidth(){
        return this.intWidth;
    }
    public void setPlayerState(int newPlayerState){
        this.intPlayerState = newPlayerState;
    }
    public double getVelocityY(){
        return this.dblVelocityY;
    }
    public void setPlayerPosition(double newX, double newY){
        this.dblPositionX = newX;
        this.dblPositionY = newY;
    }
    // Draw the player onto the screen
    public void draw(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.drawImage(imageDisplayed, (int)dblPositionX, (int)dblPositionY, intWidth, intHeight, null);
    }
    // Game mechanics
    public void jump(){
        if (!blnIsJumping && !blnIsFalling){
            dblVelocityY = dblJumpPower;
            blnIsJumping = true;
        }
    }
    public void takeDamage(){
        if(blnInvincible) return;

        blnInvincible = true;
        intInvincibilityFrames = 60;
        // knockback
        dblPositionX -= 50;                 // push back horizontally
        dblPositionY -= 10;                 // optional small hop
        dblVelocityY = -1.5;
        intLives--;
        InvincibleTime = System.currentTimeMillis();
    }
    public void stopHorizontalVel(){
        this.dblVelocityX = 0;
    }
    public void stopVerticalVel(){
        this.dblVelocityY = 0;
    }
    public void updatePlayer(boolean blnIsMovingRight, boolean blnIsMovingLeft){
        updatePosition(blnIsMovingRight, blnIsMovingLeft);
        if (blnInvincible) {
            long elaspedTime = System.currentTimeMillis() - InvincibleTime;
            if (elaspedTime > 1000){
                blnInvincible = false; // invincibility ends after 1 second
            }
        }
    }

    public void updatePosition(boolean blnIsMovingRight, boolean blnIsMovingLeft){
        // Saves previous position
        dblPrevX = this.dblPositionX;
        dblPrevY = this.dblPositionY;

        // Apply different gravity for a floaty rise and a quick fall
        if (dblVelocityY < 0) {
            dblVelocityY += dblGravityUp;
        }
        else {
            dblVelocityY += dblGravityDown;
        }
        // Add max speed
        if (dblVelocityY > dblMaxFallSpeed){
            dblVelocityY = dblMaxFallSpeed;
        }
        // Horizontal Movement
        if (blnIsMovingRight){
            dblVelocityX = dblSpeed;
            imageDisplayed = playerImages[0];
        }
        else if (blnIsMovingLeft){
            dblVelocityX = -dblSpeed;
            imageDisplayed = playerImages[1];
        }
        else {
            stopHorizontalVel();
        }

        dblPositionX += dblVelocityX;
        dblPositionY += dblVelocityY;

        // Check if player hits the ground
        if (dblPositionY + intHeight >= 863) {
            dblPositionY = 863 - intHeight;
            dblVelocityY = 0;
            blnIsJumping = false;
            blnIsFalling = false;
        } else {
            if ((int)dblVelocityY > 0) {blnIsFalling = true;}
        }
    }
    public void updateAnimation(){

    }
    // Collisions
    public void onPlatform(){
        blnIsFalling = false;
        blnIsJumping = false;
    }
    public Rectangle getPlayerBounds(){
        return new Rectangle((int)dblPositionX, (int)dblPositionY, intWidth, intHeight);
    }
}
