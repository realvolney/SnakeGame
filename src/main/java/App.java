import javax.swing.*;

public class App {

    public static void main(String[] args) {

        int boardWidth = 600;
        int boardHeight = boardWidth;



        GameFrame frame = new GameFrame("Snake");


        SnakeGame snakeGame = new SnakeGame(boardWidth, boardHeight);
        frame.add(snakeGame);

        frame.pack();
        snakeGame.requestFocus();
    }

}
