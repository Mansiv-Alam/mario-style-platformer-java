package Sprites;

import Game.GameController;

import java.awt.*;

public abstract class Obstacle {
    // Variables
    protected int intX;
    protected int intY;
    protected int intWidth;
    protected int intHeight;

    Obstacle(int x, int y, int width, int height){
        this.intX = x;
        this.intY = y;
        this.intWidth = width;
        this.intHeight = height;
    }
    // Accessor Methods
    public int getX() {
        return intX;
    }

    public int getY() {
        return intY;
    }

    public int getWidth() {
        return intWidth;
    }

    public int getHeight() {
        return intHeight;
    }

    public abstract void draw(Graphics g);
    public abstract void collidesWith(GameController gmc, Player player);

    // Makes a rectangle object for the built-in collision checks
    public Rectangle getBounds() {
        return new Rectangle(intX, intY, intWidth, intHeight);
    }
}
