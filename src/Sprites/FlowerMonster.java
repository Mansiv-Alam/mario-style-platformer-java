package Sprites;

import Game.GameController;

import javax.swing.*;
import java.awt.*;

public class FlowerMonster extends Enemy{

    private double dblFlowerTimer;
    private boolean blnActive;
    private Image monsterImage;

    public FlowerMonster(int x, int y){
        super(x,y, 64, 76);
        dblFlowerTimer = System.currentTimeMillis();

        Image img = new ImageIcon("src/PiranhaPlant.png").getImage();
        // Resizes the image
        monsterImage = img.getScaledInstance(intWidth, intHeight, Image.SCALE_SMOOTH);
    }

    @Override
    public void draw(Graphics g){
        if (blnActive){
            g.drawImage(monsterImage, (int)dblX, (int)dblY, null);
        }
    }
    @Override
    public void collidesWith(GameController gmc, Player player){
        if (blnActive){
            player.takeDamage();
        }
    }
    @Override
    public void move(){
        // 5 Second delay
        if (!blnActive && System.currentTimeMillis() - dblFlowerTimer >= 1000){
            blnActive = true;
            dblFlowerTimer = System.currentTimeMillis();
        }
        else if (blnActive && System.currentTimeMillis() - dblFlowerTimer >= 1000){
            blnActive = false;
            dblFlowerTimer = System.currentTimeMillis();
        }
    }
    @Override
    public void onStomp(GameController gmc, int index, Player player){
        if (blnActive){
            player.takeDamage();
        }
    }
    @Override
    public void onHitWithFireball(GameController gmc, int index, Player player) {

    }
}
