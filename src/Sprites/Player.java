package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    private double dblPositionX;
    private double dblPositionY;
    // For Collisions
    private double dblPrevX, dblPrevY;
    private int intWidth = 80, intHeight = 103;
    // Physics Variable
    private double dblVelocityY = 0, dblVelocityX;
    private final double dblJumpPower = -3.5, dblGravityUp = 0.02, dblGravityDown = 0.035, dblMaxFallSpeed = 6,dblSpeed = 0.8;
    private int intDirection = 1;
    // Health
    private int intLives = 2, intPlayerState = 1;
    private boolean blnInvincible;
    private long invincibleTime, lastFireTime;

    public boolean blnIsJumping, blnIsFalling;
    // Animation Variables
    private int intAnimationFrame = 0;
    private int intFrameCounter ;
    private final int intFrameDelay = 50;
    private final Image[] playerImages = new Image[2];
    private final Image[] playerImagesRight = new Image[7];
    private final Image[] playerImagesLeft = new Image[7];
    private Image imageDisplayed;

    public Player(double startX, double startY){
        this.dblPositionX = startX;
        this.dblPositionY = startY;

        loadImages();
    }
    public void loadImages(){
        // Gets the image from the source files
        Image img = new ImageIcon("src/Morio_1.png").getImage();
        // Resizes the image
        playerImagesRight[0] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);

        img = new ImageIcon("src/Morio_2.png").getImage();
        playerImagesLeft[0] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);

        for (int i = 0; i < 2; i++) {
            img = new ImageIcon("src/Morio_" + (i + 3) + ".png").getImage();
            playerImages[i] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);

        }
        // Loops through the running frames to load them into the arrays
        for (int i = 0; i < 6; i++) {
            img = new ImageIcon("src/Morio_" + (i + 5 ) + ".png").getImage();
            playerImagesRight[i + 1] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);

            img = new ImageIcon("src/Morio_" + (i + 11 ) + ".png").getImage();
            playerImagesLeft[i + 1] = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);

        }
        // Defaults to setting to looking to the left
        imageDisplayed = playerImagesRight[0];
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
    public int getDirection(){
        return this.intDirection;
    }
    public int getLives(){
        return this.intLives;
    }
    public int getPlayerState(){
        return intPlayerState;
    }
    public void setPlayerState(int newPlayerState){
        this.intPlayerState = newPlayerState;
    }
    public void setLives(int intLives){
        this.intLives = intLives;
    }
    public double getVelocityX(){
        return this.dblVelocityX;
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
    public void bounce() {
        // small upward velocity
        this.dblVelocityY = -2.0;
        blnIsJumping = true;
        blnIsFalling = false;
    }
    public void applyGravity(){
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
    }
    public void takeDamage(){
        if(blnInvincible) return;

        blnInvincible = true;
        invincibleTime = System.currentTimeMillis();
        // knock back
        dblPositionX -= 50; // push back horizontally
        dblPositionY -= 10;
        dblVelocityY = -2.2; // optional small hop
        intLives--;
        intPlayerState = 1;
    }
    public void shootFireball(ArrayList<Fireball> fireballs){
        long currentTime = System.currentTimeMillis();
        // 400-millisecond delay between shooting fireballs
        if (currentTime - lastFireTime >= 400) {
            fireballs.add(new Fireball(dblPositionX + intWidth / 2.0, dblPositionY + intHeight / 2.0, intDirection));
            lastFireTime = currentTime; // reset cooldown
        }
    }

    public void stopHorizontalVel(){
        this.dblVelocityX = 0;
    }
    public void stopVerticalVel(){
        this.dblVelocityY = 0;
    }
    public void updatePlayer(boolean blnIsMovingRight, boolean blnIsMovingLeft, GameController gmc){

        updatePosition(blnIsMovingRight, blnIsMovingLeft, gmc);
        if (blnInvincible) {
            long elapsedTime = System.currentTimeMillis() - invincibleTime;
            if (elapsedTime >= 1000){
                blnInvincible = false; // invincibility ends after 1 second
            }
        }
    }

    public void updatePosition(boolean blnIsMovingRight, boolean blnIsMovingLeft, GameController gmc){
        // Saves previous position
        dblPrevX = this.dblPositionX;
        dblPrevY = this.dblPositionY;

        // Horizontal Movement
        if (blnIsMovingRight){
            dblVelocityX = dblSpeed;
            intDirection = 1;
        }
        else if (blnIsMovingLeft){
            dblVelocityX = -dblSpeed;
            intDirection = -1;
        }
        else {
            stopHorizontalVel();
        }

        applyGravity();

        dblPositionX += dblVelocityX;
        dblPositionY += dblVelocityY;

        // left boundary
        if (dblPositionX < 0) {
            dblPositionX = 0;
        }
        // right boundary
        if (dblPositionX + intWidth > 1920) {
            gmc.nextLevel();
        }

        // Check if player hits the ground
        if (dblPositionY + intHeight >= 863) {
            dblPositionY = 863 - intHeight;
            dblVelocityY = 0;
            blnIsJumping = false;
            blnIsFalling = false;
        }
        else {
            if ((int)dblVelocityY > 0) {blnIsFalling = true;}
        }

        updateAnimations();
    }
    // Collisions
    public void onPlatform(){
        blnIsFalling = false;
        blnIsJumping = false;
    }
    public Rectangle getPlayerBounds(){
        return new Rectangle((int)dblPositionX, (int)dblPositionY, intWidth, intHeight);
    }
    public void updateAnimations(){

        // Jump animations
        if (Math.abs((int)dblVelocityY) != 0 || (blnIsFalling || blnIsJumping)){
            intAnimationFrame = 0;
            if (intDirection == 1) {
                imageDisplayed = playerImages[0];
            }
            else{
                imageDisplayed = playerImages[1];
            }
            return;
        }

        // Idle Animations
        if(dblVelocityX == 0){
            intAnimationFrame = 0;
            if (intDirection == 1) {
                imageDisplayed = playerImagesRight[0];
            }
            else{
                imageDisplayed = playerImagesLeft[0];
            }
            return;
        }

        // Running animations
        intFrameCounter++;
        if (intFrameCounter >= intFrameDelay) {
            intAnimationFrame = (intAnimationFrame + 1) % 6;
            intFrameCounter = 0;
        }
        if (intDirection == 1) {
            imageDisplayed = playerImagesRight[intAnimationFrame];
        } else {
            imageDisplayed = playerImagesLeft[intAnimationFrame];
        }
    }
}
