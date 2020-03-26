package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    private Handler handler;
    private State state;
    private MainMenu mainMenu;
    private Game game;
    private Battle battle;

    public KeyInput(Game game, Handler handler, State state, MainMenu mainMenu, Battle battle) {
        this.handler = handler;
        this.state = state;
        this.mainMenu = mainMenu;
        this.game = game;
        this.battle = battle;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode(); //ascii
        if (state.equals(State.MainMenu)) {
            if (key == KeyEvent.VK_UP) {
                mainMenu.selectionUp();
            } else if (key == KeyEvent.VK_DOWN) {
                mainMenu.selectionDown();
            } else if (key == KeyEvent.VK_X) {
                game.setState(mainMenu.returnState());
            }
            return;
        } else if (state.equals(State.Battle)) {
            if (key == KeyEvent.VK_UP) {
                battle.setCameraY(battle.getCameraY() - battle.TILE_SIZE);
            } else if (key == KeyEvent.VK_DOWN) {
                battle.setCameraY(battle.getCameraY() + battle.TILE_SIZE);
            } else if (key == KeyEvent.VK_LEFT) {
                battle.setCameraX(battle.getCameraX() - battle.TILE_SIZE);
            } else if (key == KeyEvent.VK_RIGHT) {
                battle.setCameraX(battle.getCameraX() + battle.TILE_SIZE);
            }
        }
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getID() == ID.Player) {
                if(key == KeyEvent.VK_W) {
                    tempObject.setY(-5);
                }
            }
        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setBattle(Battle battle) {
        this.battle = battle;
    }
}
