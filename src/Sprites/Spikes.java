package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class Spikes extends Obstacle{

    Image spikeImage;
    public Spikes(int x, int y){
        super(x, y, 50, 50);
        Image img = new ImageIcon("src/MorioSpike.png").getImage();
        // Resizes the image
        spikeImage = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
    }
    public void draw(Graphics g){
        g.drawImage(spikeImage, intX, intY, intWidth, intHeight, null);
    }
    public void collidesWith(GameController gmc, Player player){
        player.takeDamage();
    }
}
