package Sprites;

import javax.swing.*;
import java.awt.*;

public class Coin {
    private int intX;
    private int intY;
    private int intSize = 50;
    Image coinImage;

    public Coin(int x, int y){
        this.intX = x;
        this.intY = y;
        Image img = new ImageIcon("src/Coin_1.png").getImage();
        // Resizes the image
        coinImage = img.getScaledInstance(intSize, intSize, Image.SCALE_SMOOTH);
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
        g.drawImage(coinImage, intX, intY, null);
    }

    public void updateAnimation(){

    }
}
