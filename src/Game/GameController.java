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
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private Flower flower;
    private ArrayList<Fireball> fireballs = new ArrayList<>();
    private boolean blnMovingRight, blnMovingLeft;
    boolean blnMouseClicked;
    private Image[] BgImages = new Image[2];

    public class MyKeyListener implements KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 'A') {blnMovingLeft = true;}
            if (e.getKeyCode() == 'D') { blnMovingRight = true;}
            if (e.getKeyCode() == ' ') {player.jump();}
            if (e.getKeyCode() == 'F' && player.getPlayerState() == 2) {
                player.shootFireball(fireballs);
            }
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
        levelOne();
        System.out.println(System.currentTimeMillis());

        loadBGImages();
    }
    public void loadBGImages(){
        // Gets the image from the source files
        BgImages[0] = new ImageIcon("src/MorioGround.png").getImage();
        BgImages[1] = new ImageIcon("src/MorioSky.png").getImage();

    }

    public void paint(Graphics g) {
        // Background
        g.drawImage(BgImages[1], 0, 0, null);
        g.drawImage(BgImages[0], 0, 863, null);

        update();

        // Draws all objects
        player.draw(g);
        if (flower != null){
            flower.draw(g);
        }
        for (int i = 0; i < obstacles.size(); i++){
            obstacles.get(i).draw(g);
        }
        for (int i = 0; i < coins.size(); i++){
            coins.get(i).draw(g);
        }
        for (int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(g);
        }
        for (int i = 0; i < fireballs.size(); i++){
            fireballs.get(i).draw(g);
        }

        // create a delay
        try {
            Thread.sleep(1);
        }
        catch(InterruptedException ex) {}

        repaint();
    }
    public void update(){
        player.updatePlayer(blnMovingRight, blnMovingLeft);

        updateFireballs();
        updateEnemies();
        obstacleCollision();
        updateCoins();
    }
    public void updateCoins(){
        for (int i = 0; i < coins.size(); i++){
            if (player.getPlayerBounds().intersects(coins.get(i).getBounds())){
                coins.remove(i);
                intScore++;
            }
        }
    }
    public void updateFireballs(){
        for (int i = 0; i < fireballs.size(); i++){
            Fireball f = fireballs.get(i);

            f.update();
            System.out.println(f.getBounce());
            boolean blnRemoved = false; // stops processing the code after the fireball is removed
            for (int j = 0; j < enemies.size(); j++){
                if (f.getBounds().intersects(enemies.get(j).getBounds())){
                    enemies.get(j).onHitWithFireball(this, j, player);
                    fireballs.remove(i);
                    i--;
                    blnRemoved = true;
                    break; // stop checking enemies
                }
            }
            if (blnRemoved){continue;}

            for (int j = 0; j < obstacles.size(); j++){
                if (f.getBounds().intersects(obstacles.get(j).getBounds())){
                    if ((int)f.getPrevY() + f.getHeight() <= obstacles.get(j).getY() + 5) {
                        f.setPositionY(obstacles.get(j).getY() - f.getHeight());
                        f.setVelY(-4); // bounce
                        f.incrementBounceCount();
                    }
                    else if (f.getX() + f.getSize() > obstacles.get(j).getX() && f.getX() < obstacles.get(j).getX() + obstacles.get(j).getWidth()){
                        fireballs.remove(i); // hit side or bottom
                        i--;
                        blnRemoved = true;
                    }
                    break; // stop checking obstacles
                }
            }
            if (blnRemoved){continue;}

            // remove fireball if it bounced 3 times
            if (!f.isActive()){
                fireballs.remove(i);
                i--;
            }
        }
    }

    public void updateEnemies(){
        for (int i = 0; i < enemies.size(); i++){
            // Checks for collision
            if (player.getPlayerBounds().intersects(enemies.get(i).getBounds())){
                if (player.getVelocityY() > 0 && (int)player.getPrevY() + player.getHeight() <= enemies.get(i).getBounds().y) {
                    enemies.get(i).onStomp(this, i, player);
                }
                else {
                    enemies.get(i).collidesWith(this, player);
                }
            }
            enemies.get(i).move();
        }
    }

    private void obstacleCollision() {
        // Collision check
        for (int i = 0; i < obstacles.size(); i++){
            if (player.getPlayerBounds().intersects(obstacles.get(i).getBounds())){
                Rectangle obstacleRect = obstacles.get(i).getBounds();
                Rectangle playerRect = player.getPlayerBounds();

                // Figure out where the collision is happening
                // Land on top of the obstacle
                if ((int)player.getPrevY() + playerRect.height <= obstacleRect.y && player.getVelocityY() >= 0 ) {
                    player.setPlayerPosition(player.getX(), obstacleRect.y - player.getHeight());
                    player.stopVerticalVel();
                    obstacles.get(i).collidesWith(this, player);
                    break;
                }
                // Hit the bottom of the obstacle
                else if ((int)player.getPrevY() >= obstacleRect.y + obstacleRect.height) {
                    player.setPlayerPosition(player.getX(), obstacleRect.y + obstacleRect.height);
                    player.stopVerticalVel();
                    obstacles.get(i).collidesWith(this, player);
                }
                // Hit obstacle from the left
                else if ((int)player.getPrevX() + playerRect.width <= obstacleRect.x) {
                    player.setPlayerPosition(obstacleRect.x - player.getWidth(), player.getY());
                    player.stopHorizontalVel();
                    obstacles.get(i).collidesWith(this, player);
                }
                // Hit obstacle from the right
                else if ((int)player.getPrevX() >= obstacleRect.x + obstacleRect.width) {
                    player.setPlayerPosition(obstacleRect.x + obstacleRect.width, player.getY());
                    player.stopHorizontalVel();
                }
            }
        }
    }
    public void addCoin(Coin coin){
        coins.add(coin);
    }
    public void addFlower(Flower newflower){
        flower = newflower;
        flower.activateFlower();
    }
    public void removeEnemy(int index){
        enemies.remove(index);
    }

    public void startGame(){

    }
    public void saveGame(){

    }
    public void loadGame(){

    }
    public void gameOver(){

    }
    public void levelOne(){
        obstacles.add(new Platform(300,680));
        obstacles.add(new Platform(700,580));
        obstacles.add(new Block(800,200));
        obstacles.add(new Spikes(1100, 813));
        coins.add(new Coin(300,300));
        coins.add(new Coin(500,300));
        coins.add(new Coin(600,500));
        enemies.add(new Goomba(1450, 813));
        enemies.add(new Koopa(1650, 750));
        obstacles.add(new Pipe(1800, 734));
        enemies.add(new FlowerMonster(1805, 658));
    }
    public void nextLevel(){

    }
    public void increaseScore(){

    }
}
