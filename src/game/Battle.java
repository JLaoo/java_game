package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Battle {
    public static final int TILE_SIZE = 50;
    public static final int NUM_TILES_ON_SCREEN_X = 800 / TILE_SIZE;
    public static final int NUM_TILES_ON_SCREEN_Y= 600 / TILE_SIZE;
    private int cameraX = 100;
    private int cameraY = 100;
    private int xMax = 400;
    private int yMax = 300;
    private int numXTiles = 80;
    private int numYTiles = 60;
    HashMap<String, Image> imageMap = new HashMap<>();
    String[][] textMap = new String[numXTiles][numYTiles];
    GameObject[][] objectsMap = new GameObject[numXTiles][numYTiles];
    public Battle() {
        for (String[] arr : textMap) {
            for(int i = 0; i < arr.length; i++) {
                int random = (int)(Math.random() * 2);
                if (random == 0) {
                    arr[i] = "grass";
                } else {
                    arr[i] = "rock";
                }
            }
        }
    }

    public void render(Graphics g) throws IOException {
        int xStart = cameraX / TILE_SIZE;
        int yStart = cameraY / TILE_SIZE;
        int xEnd = Math.min(xStart + NUM_TILES_ON_SCREEN_X, textMap.length);
        int yEnd = Math.min(yStart + NUM_TILES_ON_SCREEN_Y, textMap[0].length);
        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                int shiftedX = (x * TILE_SIZE) - cameraX;
                int shiftedY = (y * TILE_SIZE) - cameraY;
                if(shiftedX >= 0 && shiftedX < 800 && shiftedY >= 0 && shiftedY < 600) {
                    g.drawImage(getImage(textMap[x][y]), shiftedX, shiftedY, null);
                }
            }
        }
    }

    private Image getImage(String tile) throws IOException {
        if (imageMap.containsKey(tile)) {
            return imageMap.get(tile);
        }
        String path = null;
        if (tile.equals("rock")) {
            path = "src/assets/rock.png";
        } else if (tile.equals("grass")) {
            path = "src/assets/grass.png";
        }
        BufferedImage raw = ImageIO.read(new File(path));
        Image resized = raw.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_FAST);
        imageMap.put(tile, resized);
        return resized;
    }

    public void setCameraX(int x) {
        if (x >= 0 && x < xMax) {
            cameraX = x;
        }
    }

    public void setCameraY(int y) {
        if (y >= 0 && y < yMax) {
            cameraY = y;
        }
    }

    public int getCameraX() {
        return cameraX;
    }

    public int getCameraY() {
        return cameraY;
    }
}
