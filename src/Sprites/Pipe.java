package Sprites;

import Game.GameController;

import java.awt.*;

public class Pipe extends Obstacle{

    public Pipe(int x, int y){
        super(x, y, 50, 100);
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(intX, intY, intWidth, intHeight);
    }
    public void collidesWith(GameController gmc, Player player){
        player.onPlatform();
    }

}
