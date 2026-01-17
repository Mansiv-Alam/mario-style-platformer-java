package Sprites;

import Game.GameController;

import java.awt.*;

public class Spikes extends Obstacle{
    public Spikes(int x, int y, int size){
        super(x, y, size, size);
    }
    public void draw(Graphics g){
        g.setColor(Color.GRAY);
        g.fillRect(intX, intY, intWidth, intHeight);
    }
    public void collidesWith(GameController gmc, Player player){
        player.onPlatform();
        player.jump();
        player.takeDamage();
    }
}
