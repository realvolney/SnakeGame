import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {

    private int boardWidth = 600;
    private int boardHeight = boardWidth;
    private String title;
    private JButton restartButton;
    public GameFrame(String title) {
        this.setVisible(true);
        this.setSize(boardWidth, boardHeight);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.title = title;


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        restartButton.setVisible(true);
        if (e.getSource() == restartButton) {
            restartButton.setVisible(true);

        }
    }
}
