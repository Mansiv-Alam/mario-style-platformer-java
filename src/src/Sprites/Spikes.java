package Sprites;

import java.awt.*;

public class Spikes extends Obstacle{
    Spikes(int x, int y, int size){
        super(x, y, size, size);
    }
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(intX, intY, intWidth, intHeight);
    }
    public void collidesWith(){

    }
}
