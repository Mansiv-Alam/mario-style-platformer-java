package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameMenu extends JPanel{

    private Image[] menuBackground = new Image[4];
    private JButton btnStart, btnExit, btnLoadGame, btnCredits;

    public GameMenu(){

        menuBackground[0] = new ImageIcon("src/Resources/MorioSky.png").getImage();
        menuBackground[1] = new ImageIcon("src/Resources/MorioGround.png").getImage();
        menuBackground[2] = new ImageIcon("src/Resources/MorioClouds.png").getImage();

        setLayout(null); // We'll manually position buttons

        // Start button
        btnStart = new JButton(new ImageIcon("src/Resources/Start.png"));
        btnStart.setRolloverIcon(new ImageIcon("src/Resources/StartHover.png"));
        btnStart.setBounds(750, 300, 400, 98);
        btnStart.setBorderPainted(false);
        btnStart.setContentAreaFilled(false);
        btnStart.setFocusPainted(false);
        btnStart.setActionCommand("Start");
        add(btnStart);

        // Exit button
        btnExit = new JButton(new ImageIcon("src/Resources/Exit.png"));
        btnExit.setRolloverIcon(new ImageIcon("src/Resources/ExitHover.png"));
        btnExit.setBounds(750, 600, 400, 98);
        btnExit.setBorderPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setFocusPainted(false);
        btnExit.setActionCommand("Exit");
        add(btnExit);

        // Load Game Button
        btnLoadGame = new JButton(new ImageIcon("src/Resources/LoadGame.png"));
        btnLoadGame.setRolloverIcon(new ImageIcon("src/Resources/LoadGameHover.png"));
        btnLoadGame.setBounds(750, 400, 400, 98);
        btnLoadGame.setBorderPainted(false);
        btnLoadGame.setContentAreaFilled(false);
        btnLoadGame.setFocusPainted(false);
        btnLoadGame.setActionCommand("LoadGame");
        add(btnLoadGame);

        // Credits Button
        btnCredits = new JButton(new ImageIcon("src/Resources/Credits.png"));
        btnCredits.setRolloverIcon(new ImageIcon("src/Resources/CreditsHover.png"));
        btnCredits.setBounds(750, 500, 400, 98);
        btnCredits.setBorderPainted(false);
        btnCredits.setContentAreaFilled(false);
        btnLoadGame.setFocusPainted(false);
        btnCredits.setActionCommand("Credits");
        add(btnCredits);

        ButtonHandler handler = new ButtonHandler();
        btnStart.addActionListener(handler);
        btnExit.addActionListener(handler);
        btnLoadGame.addActionListener(handler);
        btnCredits.addActionListener(handler);
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String strCommand = e.getActionCommand();

            if(strCommand.equals("Start")){
                // Get the current window that holds this menu
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameMenu.this);

                // Remove the menu panel from the window
                window.getContentPane().removeAll();

                // Add the game panel to the window
                GameController game = new GameController();
                window.getContentPane().add(game);
                game.requestFocusInWindow(); // make the panel focus so the keyboard inputs work

                // Refresh the window to show the new content
                window.revalidate();
                window.repaint();
            }
            else if (strCommand.equals("Exit")){
                System.exit(0);
            }
            else if (strCommand.equals("LoadGame")){
                try {
                    Scanner fileIn = new Scanner(new File("SaveData.txt"));
                    int savedScore = fileIn.nextInt();
                    int savedLevel = fileIn.nextInt();

                    // Get the current window that holds this menu
                    JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameMenu.this);

                    // Remove the menu panel from the window
                    window.getContentPane().removeAll();

                    // Add the game panel to the window
                    GameController game = new GameController();
                    window.getContentPane().add(game);
                    game.requestFocusInWindow(); // make the panel focus so the keyboard inputs work
                    game.saveData(savedScore, savedLevel);

                    // Refresh the window to show the new content
                    window.revalidate();
                    window.repaint();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(GameMenu.this, "No save file found.");
                }
            }
            else if(strCommand.equals("Credits")){
                // Get the current window that holds this menu
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(GameMenu.this);

                // Remove the menu panel from the window
                window.getContentPane().removeAll();

                // Add the game panel to the window
                CreditsPanel credits = new CreditsPanel();
                window.getContentPane().add(credits);

                // Refresh the window to show the new content
                window.revalidate();
                window.repaint();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(menuBackground[0], 0, 0, getWidth(), getHeight(), this);
        g.drawImage(menuBackground[1], 0, 863, 1920, 217, this);
        g.drawImage(menuBackground[2], 30, 200, 1748, 320, this);
        // Title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Pt Sans", Font.PLAIN, 48));
        g.drawString("Morio™", 865, 200);
    }
}