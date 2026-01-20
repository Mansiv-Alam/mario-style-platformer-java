package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class Block extends Obstacle{

    private int intPowerUp; // 1 for coin, 2 for power up
    private boolean blnBlockUsed = false;
    private Image blockImage;
    private final Image usedBlockImage;

    public Block(int x, int y){
        super(x, y, 80,60);

        Image img = new ImageIcon("src/Resources/PowerupBox.png").getImage();
        // Resizes the image
        blockImage = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);

        img = new ImageIcon("src/Resources/UsedBlock.png").getImage();
        // Resizes the image
        usedBlockImage = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);

        spawnPowerUp();
    }
    public void draw(Graphics g){
        g.drawImage(blockImage, intX, intY, null);
    }
    public void collidesWith(GameController gmc, Player player){
        // Adds powerups or coins to the player or coin list in the game controller by passing game controller as a parameter
        if (!blnBlockUsed){
            blnBlockUsed = true; // Make sure only one coin or power up is made per block
            blockImage = usedBlockImage;
            if (intPowerUp == 1){
                gmc.addCoin(new Coin(intX + (intWidth / 2) - 25, intY - 50, true));
                gmc.increaseScore();
            }
            else {
                player.setPlayerState(2);
                gmc.addFlower(new Flower(intX + (intWidth / 2) - 25, intY - 50));
            }
        }
    }
    public void spawnPowerUp(){
        double dblRandom = Math.random();
        // 70% chance of a coin
        if (dblRandom < 0.7){
            intPowerUp = 1;
        }
        else {
            intPowerUp = 2;
        }
    }
}
