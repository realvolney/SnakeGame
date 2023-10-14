import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.*;
public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private int boardWidth;

    private int tileSize = 25;

    private int boardHeight;

    private Tile snakeHead;

    private List<Tile> snakeBody;

    private Tile food;

    private Random random;

    private Timer gameLoop;

    private int Xvelocity;

    private int Yvelocity;

    public SnakeGame( int boardWidth, int boardHeight) {

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        snakeHead = new Tile(5, 5);

        food = new Tile(10, 10);
        random = new Random();
        placeFood();

        gameLoop = new Timer(100, this);
        gameLoop.start();

        Xvelocity = 0;
        Yvelocity = 0;

        snakeBody = new ArrayList<>();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor((Color.WHITE));
        for (int i =0; i < boardWidth/tileSize; i++) {
            g.drawLine(i*tileSize,0, i*tileSize, boardHeight);
            g.drawLine(0, i*tileSize,boardWidth, i*tileSize);
        }

        // Food
        g.setColor(Color.RED);
        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);

        // SnakeHead
        g.setColor((Color.GREEN));
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);

        // SnakeBody
        snakeBody.forEach(tile -> {
            g.fillRect(tile.x*tileSize, tile.y*tileSize, tileSize, tileSize);
        });

    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }

        moveBody();

        snakeHead.x += Xvelocity;
        snakeHead.y += Yvelocity;
    }

    private void moveBody() {
        ListIterator<Tile> iterator = snakeBody.listIterator(snakeBody.size());
        while(iterator.hasPrevious()) {
            Tile snakePart = iterator.previous();

            if(iterator.previousIndex() == -1) {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else {
                Tile previous = snakeBody.get(iterator.previousIndex());
                snakePart.x = previous.x;
                snakePart.y = previous.y;
            }
        }
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if (Yvelocity != 1) {
                    Xvelocity = 0;
                    Yvelocity = -1;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (Yvelocity !=-1) {
                    Xvelocity = 0;
                    Yvelocity = 1;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (Xvelocity != 1) {
                    Xvelocity = -1;
                    Yvelocity = 0;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (Xvelocity != -1) {
                    Xvelocity = 1;
                    Yvelocity = 0;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Tile class to help build out grid for snake background.
     * Keeps track of 'x' and 'y' coordinate.
     */
    private static class Tile {
        private int x;
        private int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}

