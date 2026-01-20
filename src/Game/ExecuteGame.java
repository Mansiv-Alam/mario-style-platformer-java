// Name: Mansiv Alam
// Date: January 20 2026
// Title Morio Culminating Game
// Purpose: To make a mario like game using all units of the ICS4U course, classes, OOP, Abstract data types,

package Game;

import javax.swing.*;
import java.awt.*;

public class ExecuteGame {
    public static void main(String[] args){
        JFrame window = new JFrame("Morio");
        window.setSize(1920,1080);
        //window.setUndecorated(true); for fullscreen
        window.setBackground(Color.CYAN);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new GameMenu());
        window.setVisible(true);
    }
}
