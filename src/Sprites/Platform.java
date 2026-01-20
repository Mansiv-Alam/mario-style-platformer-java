package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class Platform extends Obstacle{
    // Variables
    private final Image platformImage;

    public Platform(int x, int y){
        super(x, y, 200, 67);
        // Stores image
        Image img = new ImageIcon("src/Resources/Platform.png").getImage();
        // Resizes the image
        platformImage = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
    }

    public void draw(Graphics g){
        g.drawImage(platformImage, intX, intY, intWidth, intHeight, null);
    }
    public void collidesWith(GameController gmc, Player player){
        player.onPlatform();
    }

}
