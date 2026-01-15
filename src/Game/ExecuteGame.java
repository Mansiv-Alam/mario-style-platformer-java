package Game;

import javax.swing.*;
import java.awt.*;

public class ExecuteGame {
    public static void main(String[] args){
        JFrame window = new JFrame("Morio");
        window.setSize(1920,1080);
        window.setBackground(Color.CYAN);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new GameController());
        window.setVisible(true);
    }
}
