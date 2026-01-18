package Sprites;

import Game.GameController;

import java.awt.*;

public abstract class Enemy {
    protected double dblX;
    protected double dblY;
    protected int intWidth = 40;
    protected int intHeight = 100;
    protected boolean blnAlive;

    public Enemy(int x, int y){
        this.dblX = x;
        this.dblY = y;
    }
    // Accessor Methods
    public double getX() {
        return dblX;
    }

    public double getY() {
        return dblY;
    }

    public int getWidth() {
        return intWidth;
    }

    public int getHeight() {
        return intHeight;
    }

    public abstract void draw(Graphics g);
    public abstract void collidesWith(GameController gmc, Player player);
    public abstract void move();
    public abstract void onStomp(GameController gmc, int index);
    public abstract void onHitWithFireball();

    // Makes a rectangle object for the built-in collision checks
    public Rectangle getBounds() {
        return new Rectangle((int)dblX, (int)dblY, intWidth, intHeight);
    }
}
