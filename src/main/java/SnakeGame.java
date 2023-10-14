import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private int boardWidth;

    private int tileSize = 25;

    private int boardHeight;

    private Tile snakeHead;

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

        snakeHead = new Tile(5,5);

        food = new Tile(10,10);
        random = new Random();
        placeFood();

        gameLoop = new Timer(50, this);
        gameLoop.start();

        Xvelocity = 0;
        Yvelocity = 0;
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

        g.setColor(Color.RED);
        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);

        g.setColor((Color.GREEN));
        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public void move() {
        snakeHead.x += Xvelocity;
        snakeHead.y += Yvelocity;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                Xvelocity = 0;
                Yvelocity = -1;
                break;
            case KeyEvent.VK_DOWN:
                Xvelocity = 0;
                Yvelocity = 1;
                break;
            case KeyEvent.VK_LEFT:
                Xvelocity = -1;
                Yvelocity = 0;
                break;
            case KeyEvent.VK_RIGHT:
                Xvelocity = 1;
                Yvelocity = 0;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private class Tile {
        private int x;
        private int y;

        public Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }



}

