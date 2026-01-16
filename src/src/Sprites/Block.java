package Sprites;

import java.awt.*;

public class Block extends Obstacle{

    Block(int x, int y, int size){
        super(x, y, size, size);
    }
    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillRect(intX, intY, intWidth, intHeight);
    }
    public void collidesWith(){

    }
}
