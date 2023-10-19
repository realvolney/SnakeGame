import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Practice Class to try to get buttons to work
public class GUI extends JFrame {

    private JButton tryAgainButton;
    private JPanel panel;

    public GUI() {
        panel = new JPanel();
        tryAgainButton = new JButton("Try Again");
        panel.add(tryAgainButton);
        setContentPane(panel);
        setTitle("Java is outDated");
        setVisible(true);
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(GUI.this, "Never Give up");

            }
        });
    }

    public static void main(String[] args) {
        new GUI();
    }
}
