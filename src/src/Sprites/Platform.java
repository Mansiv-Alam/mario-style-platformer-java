package Sprites;

import java.awt.*;

public class Platform extends Obstacle{
    public Platform(int x, int y, int width, int height){
        super(x, y, width, height);
    }

    public void draw(Graphics g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(intX, intY, intWidth, intHeight);
    }
    public void collidesWith(){

    }

}
