package Game;

import Sprites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameController extends JPanel {
    private int intScore;
    private int intLevel;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();;
    private ArrayList<Coin> coins = new ArrayList<>();;
    private ArrayList<Fireball> fireballs = new ArrayList<>();;
    private boolean blnMovingRight, blnMovingLeft;
    boolean blnMouseClicked;
    private Image[] BgImages = new Image[1];

    public class MyKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 'A') {blnMovingLeft = true;}
            if (e.getKeyCode() == 'D') { blnMovingRight = true;}
            if (e.getKeyCode() == ' ') {player.jump();}
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 'A') {blnMovingLeft = false;}
            if (e.getKeyCode() == 'D') {blnMovingRight = false;}
            System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
        }
    }

    GameController(){
        player = new Player(100, 800);
        MyKeyListener listener = new MyKeyListener();
        addKeyListener(listener);
        setFocusable(true);
        obstacles.add(new Platform(800,680, 200, 50));
        obstacles.add(new Platform(1200,680, 200, 50));
        loadBGImages();
    }
    public void loadBGImages(){
        // Gets the image from the source files
        BgImages[0] = new ImageIcon("src/MorioGround.png").getImage();
    }

    public void paint(Graphics g) {
        // Background
        g.setColor(Color.CYAN);
        g.fillRect(0,0, this.getWidth(), this.getHeight());

        g.drawImage(BgImages[0], 0, 863, null);

        update();

        player.draw(g);
        for (int i = 0; i < obstacles.size(); i++){
            obstacles.get(i).draw(g);
        }

        // create a delay
        try {
            Thread.sleep(1);
        }
        catch(InterruptedException ex) {}

        repaint();
    }
    public void update(){
        player.updatePosition(blnMovingRight, blnMovingLeft);

        // Collision check
        for (int i = 0; i < obstacles.size(); i++){
            if (player.getPlayerBounds().intersects(obstacles.get(i).getBounds())){
                resolveCollision(obstacles.get(i));
                System.out.println("Collision");
            }
        }
    }

    private void resolveCollision(Obstacle obstacle) {
        Rectangle obstacleRect = obstacle.getBounds();
        Rectangle playerRect = player.getPlayerBounds();
        //System.out.println(player.blnIsFalling + ", " + player.blnIsJumping + ", " + player.getVelocityY());

        // Figure out where the collision is happening
        // Land on top of platform
        if ((int)player.getPrevY() + playerRect.height <= obstacleRect.y && player.getVelocityY() >= 0 ) {
            //System.out.println(player.getPrevY() + ", " + playerRect.height + ", " + obstacleRect.y);
            player.setPlayerPosition(player.getX(), obstacleRect.y - player.getHeight());
            player.stopVerticalVel();
            player.onPlatform();
        }
        // Hit the bottom of a block (NOT DONE!!)
        else if ((int)player.getPrevY() >= obstacleRect.y + obstacleRect.height) {
            //player.hitCeiling(b.y + b.height);
        }
        // Hit obstacle from the left
        else if ((int)player.getPrevX() + playerRect.width <= obstacleRect.x) {
            player.setPlayerPosition(obstacleRect.x - player.getWidth(), player.getY());
            player.stopHorizontalVel();
        }
        // Hit obstacle from the right
        else if ((int)player.getPrevX() >= obstacleRect.x + obstacleRect.width) {
            player.setPlayerPosition(obstacleRect.x + obstacleRect.width, player.getY());
            player.stopHorizontalVel();
        }
    }

    public void startGame(){

    }
    public void saveGame(){

    }
    public void loadGame(){

    }
    public void gameOver(){

    }
    public void nextLevel(){

    }
    public void increaseScore(){

    }
}
