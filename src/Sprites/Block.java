package Sprites;

import Game.GameController;

import java.awt.*;

public class Block extends Obstacle{

    private boolean blnBlockUsed = false;
    Color currentColour = new Color(108, 69, 24);

    public Block(int x, int y, int size){
        super(x, y, size, size);
    }
    public void draw(Graphics g){
        g.setColor(currentColour);
        g.fillRect(intX, intY, intWidth, intHeight);
    }
    public void collidesWith(GameController gmc, Player player){
        if (!blnBlockUsed){
            blnBlockUsed = true; // Make sure only one coin or power up is made per block
            currentColour = new Color(211, 168, 118);
            // Adds coin to the coin list in the game controller by passing game controller as a parameter
            gmc.addCoin(new Coin(intX, intY - 50));
        }
    }
}
