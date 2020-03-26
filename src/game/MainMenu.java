package game;

import java.awt.*;

public class MainMenu {
    private int selection = 0;

    public void render(Graphics g) {
        g.setFont(new Font("Impact", Font.ITALIC, 50));
        g.setColor(Color.GRAY);
        if (selection == 0) {
            g.setColor(Color.BLACK);
        }
        g.drawString("Start Game", 30, 150);
        g.setColor(Color.GRAY);
        if (selection == 1) {
            g.setColor(Color.BLACK);
        }
        g.drawString("Load Game", 30, 250);
        g.setColor(Color.GRAY);
        if (selection == 2) {
            g.setColor(Color.BLACK);
        }
        g.drawString("Options", 30, 350);
        g.setColor(Color.GRAY);
        if (selection == 3) {
            g.setColor(Color.BLACK);
        }
        g.drawString("Quit", 30, 450);
    }

    public void selectionUp() {
        if (selection == 0) {
            selection = 3;
        } else {
            selection --;
        }
    }

    public void selectionDown() {
        if (selection == 3) {
            selection = 0;
        } else {
            selection ++;
        }
    }
    public State returnState() {
        if (selection == 0) {
            return State.Battle;
        } else if (selection == 1) {
            return State.LoadScreen;
        } else if (selection == 2) {
            return State.Options;
        } else {
            System.exit(0);
            return null;
        }
    }
}
