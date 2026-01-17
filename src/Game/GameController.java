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
        obstacles.add(new Platform(500,680, 200, 50));
        obstacles.add(new Platform(900,580, 200, 50));
        obstacles.add(new Block(1000,200, 50));
        obstacles.add(new Spikes(1300, 810, 50));
        coins.add(new Coin(300,300));
        coins.add(new Coin(500,300));
        coins.add(new Coin(600,500));
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

        // Draws all objects
        player.draw(g);
        for (int i = 0; i < obstacles.size(); i++){
            obstacles.get(i).draw(g);
        }
        for (int i = 0; i < coins.size(); i++){
            coins.get(i).draw(g);
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
                System.out.println("Obstacle Collision");
            }
        }
        updateCoins();
    }
    public void updateCoins(){
        for (int i = 0; i < coins.size(); i++){
            if (player.getPlayerBounds().intersects(coins.get(i).getBounds())){
                coins.remove(i);
                intScore++;
                System.out.println("Obstacle Collision");
            }
        }
    }

    private void resolveCollision(Obstacle obstacle) {
        Rectangle obstacleRect = obstacle.getBounds();
        Rectangle playerRect = player.getPlayerBounds();
        //System.out.println(player.blnIsFalling + ", " + player.blnIsJumping + ", " + player.getVelocityY());

        // Figure out where the collision is happening
        // Land on top of the obstacle
        if ((int)player.getPrevY() + playerRect.height <= obstacleRect.y && player.getVelocityY() >= 0 ) {
            //System.out.println(player.getPrevY() + ", " + playerRect.height + ", " + obstacleRect.y);
            player.setPlayerPosition(player.getX(), obstacleRect.y - player.getHeight());
            player.stopVerticalVel();
            obstacle.collidesWith(this, player);
        }
        // Hit the bottom of the obstacle
        else if ((int)player.getPrevY() >= obstacleRect.y + obstacleRect.height) {
            player.setPlayerPosition(player.getX(), obstacleRect.y + obstacleRect.height);
            player.stopVerticalVel();
            obstacle.collidesWith(this, player);
        }
        // Hit obstacle from the left
        else if ((int)player.getPrevX() + playerRect.width <= obstacleRect.x) {
            player.setPlayerPosition(obstacleRect.x - player.getWidth(), player.getY());
            player.stopHorizontalVel();
            obstacle.collidesWith(this, player);
        }
        // Hit obstacle from the right
        else if ((int)player.getPrevX() >= obstacleRect.x + obstacleRect.width) {
            player.setPlayerPosition(obstacleRect.x + obstacleRect.width, player.getY());
            player.stopHorizontalVel();
        }
    }
    public void addCoin(Coin coin){
        coins.add(coin);
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
