import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    private final int boardWidth;

    private final int boardHeight;

    private final int tileSize = 25;

    private  Tile snakeHead = new Tile(5, 5);

    private List<Tile> snakeBody = new ArrayList<>();

    private final Tile food = new Tile(10, 10);

    private final Random random = new Random();

    private Timer gameLoop;

    private int Xvelocity = 0;

    private int Yvelocity = 0;

    private boolean gameOver;

    private JButton restartButton;



    public SnakeGame( int boardWidth, int boardHeight) {

        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        placeFood();
        createRestartButton();
        add(restartButton);


        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void createRestartButton() {
        restartButton = new JButton("Try Again?");
        restartButton.setBackground(Color.WHITE);
        restartButton.setVisible(false);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(SnakeGame.this,
                        "Continue?","Isn't this game fun??",
                        JOptionPane.YES_NO_CANCEL_OPTION);

                switch (choice) {
                    case JOptionPane.YES_OPTION:
                        restartGame();
                        break;
                    case JOptionPane.NO_OPTION:
                        SnakeGame.this.requestFocus();
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        SnakeGame.this.requestFocus();
                        break;
                    case JOptionPane.CLOSED_OPTION:
                        SnakeGame.this.requestFocus();
                        break;
                    default:
                        SnakeGame.this.requestFocus();
                        break;
                }
                SnakeGame.this.requestFocus();
            }
        });
    }
    public void restartGame() {
        SnakeGame.this.requestFocus();
        restartButton.setVisible(false);
        gameOver = false;
        snakeHead = new Tile(5, 5);
        snakeBody = new ArrayList<>();
        Xvelocity = 0;
        Yvelocity = 0;

        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        placeFood();
        gameLoop = new Timer(100, this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        // Food
        g.setColor(Color.RED);
//        g.fillRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y*tileSize, tileSize, tileSize, true);

        // SnakeHead
        g.setColor((Color.GREEN));
//        g.fillRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x * tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);

        // SnakeBody
        snakeBody.forEach(tile -> {
            g.fill3DRect(tile.x*tileSize, tile.y*tileSize, tileSize, tileSize, true);
        });

        //Score
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        if (gameOver) {

            g.setFont(new Font("Helvetica", Font.PLAIN, 45));
            g.setColor(Color.RED);
            if (snakeBody.isEmpty()) {
                g.drawString("GAME OVER, YOU SUCK", boardWidth / 17, boardHeight / 2);
            }
            else {
                g.drawString("GAME OVER: " + snakeBody.size(), boardWidth / 4, boardHeight / 2);
            }
        }
        else {
            g.setColor(Color.CYAN);
            g.drawString("Score: " + snakeBody.size(), tileSize - 16, tileSize);
        }
    }

    public void placeFood() {
        food.x = random.nextInt(boardWidth / tileSize);
        food.y = random.nextInt(boardHeight / tileSize);
    }

    public void move() {
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
            increaseSpeed();

        }
        moveBody();

        snakeHead.x += Xvelocity;
        snakeHead.y += Yvelocity;

        gameOver = checkGameOver();
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
    private void increaseSpeed() {
        if (!snakeBody.isEmpty()) {
            int delay = gameLoop.getDelay()/100;
            gameLoop.setDelay((gameLoop.getDelay() - delay));
        }
        if (snakeBody.size() % 3 == 0) {
            int delay = gameLoop.getDelay()/60;
            gameLoop.setDelay((gameLoop.getDelay() - delay));
            setRandomBackground(100, 256, 256);
        }
        if (snakeBody.size() % 15 == 0) {
            setBackground(Color.BLACK);
        }
    }
    private void setRandomBackground(int red, int green, int blue) {
        float floatRed = random.nextInt(red);
        float floatGreen = random.nextInt(green);
        float floatBlue = random.nextInt(blue);
        if (red >= 200) {
            red = 0;
        }
        setBackground(Color.getHSBColor(floatRed, floatGreen, floatBlue));
    }

    private boolean checkGameOver() {
        if (snakeHead.x < 0 || snakeHead.x * tileSize > boardWidth ||
                snakeHead.y < 0 || snakeHead.y * tileSize > boardHeight) {
            return true;
        }
        return snakeBody.stream()
                    .anyMatch(tile -> collision(tile, snakeHead));
    }

    public boolean collision(Tile tile1, Tile tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver) {
            restartButton.setVisible(true);
            gameLoop.stop();
        }
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
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

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

