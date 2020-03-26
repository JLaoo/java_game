package game;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 800;
    public static final int HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    private Handler handler;
    private MainMenu mainMenu;
    private State state;
    private boolean startBattle = true;
    private Battle battle = null;
    private KeyInput keyInput;

    public Game() {
        handler = new Handler();
        new Window(WIDTH, HEIGHT, "EXAMPLE TITLE", this);
        state = State.MainMenu;
        mainMenu = new MainMenu();
        keyInput = new KeyInput(this, handler, State.MainMenu, mainMenu, null);
        this.addKeyListener(keyInput);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                try {
                    render();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
    }

    private void render() throws IOException {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if (this.state.equals(State.MainMenu)) {
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            mainMenu.render(g);
        } else if (this.state.equals(State.Battle)) {
            if (startBattle) {
                startBattle = false;
                battle = new Battle();
                keyInput.setBattle(battle);
                keyInput.setState(State.Battle);
            }
            g.setColor(Color.CYAN);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            battle.render(g);
        }
        else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            handler.render(g);
        }
        g.dispose();
        bs.show();
    }

    public void setBattle(boolean b) {
        this.startBattle = b;
    }

    public void setState(State state) {
        this.state = state;
    }

    public static void main(String[] args) {
        new Game();
    }
}
