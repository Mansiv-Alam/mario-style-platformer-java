package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SettingsPanel extends JPanel {

    private Image[] menuBackground = new Image[3];
    private JButton btnSave;
    private GameController game; // reference to go back to the game

    public SettingsPanel(GameController game){
        this.game = game;

        menuBackground[0] = new ImageIcon("src/Resources/MorioSky.png").getImage();
        menuBackground[1] = new ImageIcon("src/Resources/MorioGround.png").getImage();
        menuBackground[2] = new ImageIcon("src/Resources/MorioClouds.png").getImage();


        setLayout(null); // Manually positioned buttons

        // Save Game Button
        btnSave = new JButton(new ImageIcon("src/Resources/SaveGame.png"));
        btnSave.setRolloverIcon(new ImageIcon("src/Resources/SaveGameHover.png"));
        btnSave.setBounds(750, 300, 400, 98);
        btnSave.setBorderPainted(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setFocusPainted(false);
        btnSave.setActionCommand("Save");
        add(btnSave);

        // Back to Game button
        JButton btnBackToGame = new JButton(new ImageIcon("src/Resources/BackToGame.png"));
        btnBackToGame.setRolloverIcon(new ImageIcon("src/Resources/BackToGameHover.png"));
        btnBackToGame.setBounds(750, 450, 400, 98);
        btnBackToGame.setBorderPainted(false);
        btnBackToGame.setContentAreaFilled(false);
        btnBackToGame.setFocusPainted(false);
        btnBackToGame.setActionCommand("BackToGame");
        add(btnBackToGame);

        // Back to Home button
        JButton btnBackToHome = new JButton(new ImageIcon("src/Resources/BackToMenu.png"));
        btnBackToHome.setRolloverIcon(new ImageIcon("src/Resources/BackToMenuHover.png"));
        btnBackToHome.setBounds(750, 600, 400, 98);
        btnBackToHome.setBorderPainted(false);
        btnBackToHome.setContentAreaFilled(false);
        btnBackToHome.setFocusPainted(false);
        btnBackToHome.setActionCommand("BackToHome");
        add(btnBackToHome);

        ButtonHandler handler = new ButtonHandler(this.game);
        btnSave.addActionListener(handler);
        btnBackToHome.addActionListener(handler);
        btnBackToGame.addActionListener(handler);
    }
    private class ButtonHandler implements ActionListener {
        private GameController game;
        public ButtonHandler (GameController game){
            this.game = game;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String strCommand = e.getActionCommand();

            if(strCommand.equals("Save")){
                try (PrintWriter fileOut = new PrintWriter(new FileWriter("SaveData.txt"))) {
                    // Example: write player position and score
                    fileOut.println(game.returnScore());
                    fileOut.println(game.returnLevel());
                    fileOut.close();
                    JOptionPane.showMessageDialog(SettingsPanel.this, "Game Saved!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(SettingsPanel.this, "Failed to save game.");
                }
            }
            else if (strCommand.equals("BackToGame")){
                // Get the current window that holds this menu
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(SettingsPanel.this);

                // Remove the menu panel from the window
                window.getContentPane().removeAll();

                // Add the game panel to the window
                window.getContentPane().add(this.game);
                this.game.requestFocusInWindow();

                // Refresh the window to show the new content
                window.revalidate();
                window.repaint();
            }
            else if(strCommand.equals("BackToHome")){
                // Get the current window that holds this menu
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(SettingsPanel.this);

                // Remove the menu panel from the window
                window.getContentPane().removeAll();

                // Add the game panel to the window
                GameMenu menu = new GameMenu();
                window.getContentPane().add(menu);

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
        g.drawString("Settings", 860, 250);
    }
}
