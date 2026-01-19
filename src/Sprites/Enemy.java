package Sprites;

import Game.GameController;

import java.awt.*;

public abstract class Enemy {
    protected double dblX;
    protected double dblY;
    protected int intWidth;
    protected int intHeight;

    public Enemy(int x, int y, int width,int height){
        this.dblX = x;
        this.dblY = y;
        this.intWidth = width;
        this.intHeight = height;
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
    public abstract void onStomp(GameController gmc, int index, Player player);
    public abstract void onHitWithFireball(GameController gmc, int index, Player player);

    // Makes a rectangle object for the built-in collision checks
    public Rectangle getBounds() {
        return new Rectangle((int)dblX, (int)dblY, intWidth, intHeight);
    }
}
