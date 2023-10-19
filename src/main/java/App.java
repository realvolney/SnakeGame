import javax.swing.*;

public class App {
    public static void main(String[] args) {

        int boardWidth = 600;
        int boardHeight = 600;

        GameFrame frame = new GameFrame("Snake");

        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);

        frame.pack();
        snakeGame.requestFocus();
    }
}
