package Game;

import Sprites.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameController extends JPanel {
    // Variables
    private int intScore, intCurrentScore;
    private int intLevel = 1;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private ArrayList<Coin> coins = new ArrayList<>();
    private Flower flower;
    private ArrayList<Fireball> fireballs = new ArrayList<>();
    private boolean blnMovingRight, blnMovingLeft;
    private boolean blnGameOver = false;
    private final Image[] BgImages = new Image[4];
    private Image GameOver, Controls;

    // Key Input class
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
        }
    }
    // Mouse Input class
    public class MyMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            Rectangle settingButtonBounds = new Rectangle(1810, 15, 80, 80);
            // Sees if the rectangle contains the coordinates that the mouse is at in order to use the settings button
            if (settingButtonBounds.contains(e.getX(),e.getY())){
                // Get the current window that holds this panel
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameController.this);
                // Create the settings panel and add it
                SettingsPanel settings = new SettingsPanel(GameController.this); // pass window if you want to go back

                // Remove the game panel
                window.getContentPane().removeAll();
                window.getContentPane().add(settings);

                // Refresh
                window.revalidate();
                window.repaint();
            }
            if (blnGameOver){
                Rectangle RetryBounds = new Rectangle(701, 354, 400, 96);
                Rectangle QuitBounds = new Rectangle(701, 521, 400, 96);

                if (QuitBounds.contains(e.getX(),e.getY())){
                    // Get the current window that holds this panel
                    JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameController.this);
                    // Create the settings panel and add it
                    GameMenu menu = new GameMenu();

                    // Remove the game panel
                    window.getContentPane().removeAll();
                    window.getContentPane().add(menu);

                    // Refresh
                    window.revalidate();
                    window.repaint();
                }
                else if (RetryBounds.contains(e.getX(),e.getY())){
                    System.out.println("Output");
                    resetLevel();
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    GameController(){
        player = new Player(25, 822);
        MyKeyListener listener = new MyKeyListener();
        MyMouseListener mouseListener = new MyMouseListener();
        addMouseListener(mouseListener);
        addKeyListener(listener);
        setFocusable(true);
        loadLevel();

        loadBGImages();
    }
    public void loadBGImages(){
        // Gets the image from the source files
        BgImages[0] = new ImageIcon("src/Resources/MorioGround.png").getImage();
        BgImages[1] = new ImageIcon("src/Resources/MorioSky.png").getImage();
        BgImages[2] = new ImageIcon("src/Resources/MorioClouds.png").getImage();
        BgImages[3] = new ImageIcon("src/Resources/Settings.png").getImage();
        GameOver = new ImageIcon("src/Resources/GameOver.png").getImage();
        Controls = new ImageIcon("src/Resources/Controls.png").getImage();
    }

    public void paint(Graphics g) {
        // Background
        g.drawImage(BgImages[1], 0, 0, null);
        g.drawImage(BgImages[0], 0, 863, null);
        g.drawImage(BgImages[2], 30, 200, 1748, 320, null);
        g.drawImage(BgImages[3], 1810, 15, null);

        // GUI
        g.setColor(Color.WHITE);                 // White text
        g.setFont(new Font("Pt Sans", Font.PLAIN, 36)); // Font style and size
        g.drawString("Score: " + intScore, 20, 50);
        g.drawString("Lives: " + player.getLives(), 20, 100);
        if (player.getPlayerState() == 2) {
            g.drawString("Firestate: Active", 240, 50);
        }
        else {
            g.drawString("Firestate: Inactive", 240, 50);
        }

        if (intLevel == 1){
            g.drawImage(Controls, 1100, 50,null);
        }
        if (blnGameOver && intLevel != 4){
            g.drawImage(GameOver, 600, 200, null);
            repaint();
            return;
        }
        else if (blnGameOver){
            g.setFont(new Font("Pt Sans", Font.PLAIN, 48));
            g.drawString("You Won!", 850, 400);
        }

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
        if (player.getLives() == 0){
            blnGameOver = true;
            return;
        }
        player.updatePlayer(blnMovingRight, blnMovingLeft, this);

        updateFireballs();
        updateEnemies();
        obstacleCollision();
        updateCoins();
    }
    public void updateCoins(){
        for (int i = 0; i < coins.size(); i++){
            if (player.getPlayerBounds().intersects(coins.get(i).getBounds())){
                coins.remove(i);
                intCurrentScore += 10;
            }
        }
    }
    public void updateFireballs(){
        // Fireball interactions with obstacles and enemies
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
        for (int i = (enemies.size() - 1); i >= 0; i--){

            // Checks for collision
            if (player.getPlayerBounds().intersects(enemies.get(i).getBounds())){
                if (player.getVelocityY() > 0 && (int)player.getPrevY() + player.getHeight() <= enemies.get(i).getBounds().y) {
                    enemies.get(i).onStomp(this, i, player);
                }
                else {
                    enemies.get(i).collidesWith(this, player);
                }
            }
        }
        for (int i = (enemies.size() - 1); i >= 0; i--){
            Enemy currentEnemy = enemies.get(i);
            boolean blnShellRemoved = false;
            // If all enemies are dead don't continue collision checks
            // For Koopa shells
            for (int j = (enemies.size() - 1); j >= 0; j--){
                // Koopa shells cant destroy itself
                if (i == j) {continue;}
                Enemy other = enemies.get(j);
                // If Koopa Shell touches other enemies (mainly Goombas)
                if (currentEnemy.getBounds().intersects(other.getBounds())){
                    removeEnemy(i);
                    blnShellRemoved = true;
                    break;
                }
            }

            if (blnShellRemoved){continue;}

            for (int j = 0; j < obstacles.size(); j++){
                // If Koopa shell touches obstacles
                if (currentEnemy.getBounds().intersects(obstacles.get(j).getBounds())){
                    removeEnemy(i);
                    blnShellRemoved = true;
                    break;
                }
            }
            if (!blnShellRemoved){
                enemies.get(i).move();
            }
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
                    player.onPlatform();
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
    public void saveData(int intSavedScore,int intSavedLevel){
        this.intScore = intSavedScore;
        this.intLevel = intSavedLevel;
    }
    public void resetLevel(){
        intCurrentScore = 0;
        blnGameOver = false;

        enemies.clear();
        obstacles.clear();
        coins.clear();
        fireballs.clear();
        flower = null;

        loadLevel();
    }
    public void levelOne(){
        obstacles.add(new Platform(800,640));
        obstacles.add(new Block(850,300));
    }
    public void levelTwo(){
        obstacles.add(new Platform(300,680));
        obstacles.add(new Platform(700,580));
        obstacles.add(new Block(800,200));
        obstacles.add(new Spikes(200, 813));
        coins.add(new Coin(300,300, false));
        coins.add(new Coin(500,300, false));
        coins.add(new Coin(600,500, false));
        enemies.add(new Goomba(1350, 813));
        enemies.add(new Koopa(1600, 773));
        obstacles.add(new Pipe(950, 734));
        enemies.add(new FlowerMonster(955, 658));
    }
    public void levelThree(){
        enemies.add(new Goomba(260, 813));
        obstacles.add(new Platform(450,680));
        obstacles.add(new Platform(200,380));
        obstacles.add(new Block(250,100));
        coins.add(new Coin(300,300, false));
        coins.add(new Coin(500,400, false));
        obstacles.add(new Pipe(710, 734));
        enemies.add(new FlowerMonster(715, 658));
        obstacles.add(new Platform(850,550));
        obstacles.add(new Platform(1025,550));
        obstacles.add(new Spikes(983, 505));
        coins.add(new Coin(1020,305, false));
        obstacles.add(new Spikes(1043, 505));
        enemies.add(new Koopa(1400, 773));
        enemies.add(new Goomba(1700,813));
    }
    public void nextLevel(){
        intLevel++;
        intScore += intCurrentScore;

        enemies.clear();
        obstacles.clear();
        coins.clear();
        fireballs.clear();
        flower = null;

        loadLevel();
    }
    public void increaseScore(){
        intCurrentScore += 20; // More score for blocks and Enemy deaths
    }
    public void loadLevel(){
        player = new Player(25, 822); // reset position + lives
        if (intLevel == 1){
            levelOne();
        }
        else if (intLevel == 2){
            levelTwo();
        }
        else if (intLevel == 3){
            levelThree();
        }
        else if (intLevel == 4){
            blnGameOver = true;
        }
    }
    public int returnScore(){
        return this.intScore;
    }
    public int returnLevel(){
        return this.intLevel;
    }
}
