package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditsPanel extends JPanel {

    private Image[] menuBackground = new Image[2];

    public CreditsPanel(){

        menuBackground[0] = new ImageIcon("src/Resources/MorioSky.png").getImage();
        menuBackground[1] = new ImageIcon("src/Resources/MorioGround.png").getImage();

        setLayout(null); // Manually positioned buttons

        // Title label
        JLabel title = new JLabel("Credits");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setBounds(850, 200, 500, 50);
        add(title);

        // Course Code label
        JLabel courseCode = new JLabel("ICS4U");
        courseCode.setForeground(Color.WHITE);
        courseCode.setFont(new Font("Pt Sans", Font.PLAIN, 48));
        courseCode.setBounds(1750, 15, 600, 50);
        add(courseCode);

        // Programmer label
        JLabel leadProgrammer = new JLabel("Lead Programmer: Mansiv Alam");
        leadProgrammer.setForeground(Color.WHITE);
        leadProgrammer.setFont(new Font("Pt Sans", Font.PLAIN, 48));
        leadProgrammer.setBounds(600, 300, 800, 60);
        add(leadProgrammer);

        // Designer label
        JLabel leadDesigner = new JLabel("Lead Designer: Mansiv Alam");
        leadDesigner.setForeground(Color.WHITE);
        leadDesigner.setFont(new Font("Pt Sans", Font.PLAIN, 48));
        leadDesigner.setBounds(600, 400, 700, 60);
        add(leadDesigner);

        // Designer label
        JLabel producer = new JLabel("Producer: Mansiv Alam");
        producer.setForeground(Color.WHITE);
        producer.setFont(new Font("Pt Sans", Font.PLAIN, 48));
        producer.setBounds(600, 500, 600, 60);
        add(producer);

        // Designer label
        JLabel soundDesign = new JLabel("Sound Designer: Mansiv Alam");
        soundDesign.setForeground(Color.WHITE);
        soundDesign.setFont(new Font("Pt Sans", Font.PLAIN, 48));
        soundDesign.setBounds(600, 600, 800, 60);
        add(soundDesign);

        // Back to Home button
        JButton btnBackToHome = new JButton(new ImageIcon("src/Resources/BackToMenu.png"));
        btnBackToHome.setRolloverIcon(new ImageIcon("src/Resources/BackToMenuHover.png"));
        btnBackToHome.setBounds(15, 15, 400, 98);
        btnBackToHome.setBorderPainted(false);
        btnBackToHome.setContentAreaFilled(false);
        btnBackToHome.setFocusPainted(false);
        btnBackToHome.setActionCommand("Back");
        add(btnBackToHome);

        ButtonHandler handler = new ButtonHandler();
        btnBackToHome.addActionListener(handler);
    }
    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e){
            String strCommand = e.getActionCommand();

            if(strCommand.equals("Back")){
                // Get the current window that holds this menu
                JFrame window = (JFrame) SwingUtilities.getWindowAncestor(CreditsPanel.this);

                // Remove the menu panel from the window
                window.getContentPane().removeAll();

                // Add the game panel to the window
                GameMenu game = new GameMenu();
                window.getContentPane().add(game);

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
    }
}
