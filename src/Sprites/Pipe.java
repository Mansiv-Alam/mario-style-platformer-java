package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class Pipe extends Obstacle{
    // Variables
    private final Image pipeImage;

    public Pipe(int x, int y){
        super(x, y, 70, 129); // Height and width based of the png dimensions
        Image img = new ImageIcon("src/Resources/LongPipe.png").getImage();
        // Resizes the image
        pipeImage = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
    }

    public void draw(Graphics g){
        g.drawImage(pipeImage,intX, intY , null);
    }
    public void collidesWith(GameController gmc, Player player){
        player.onPlatform();
    }

}
