package Sprites;

import java.awt.*;

public class Coin {
    private int intX;
    private int intY;
    private int intSize = 50;

    public Coin(int x, int y){
        this.intX = x;
        this.intY = y;
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
        g.setColor(Color.yellow);
        g.fillRect(intX, intY, intSize, intSize);
    }

    public void updateAnimation(){

    }
}
