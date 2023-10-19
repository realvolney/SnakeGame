import javax.swing.*;

public class GameFrame extends JFrame {

    private int boardWidth = 600;
    private int boardHeight = boardWidth;

    public GameFrame(String title) {
        super(title);
        this.setVisible(true);
        this.setSize(boardWidth, boardHeight);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
