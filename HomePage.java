import javax.swing.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    private JButton adminButton;
    private JButton voterButton;


    public HomePage() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);

    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome to Online Voting System");
        welcomeLabel.setBounds(100, 30, 200, 25);
        panel.add(welcomeLabel);

        adminButton = new JButton("Admin Section");
        adminButton.setBounds(100, 100, 200, 40);
        panel.add(adminButton);

        voterButton = new JButton("Voter Section");
        voterButton.setBounds(100, 150, 200, 40);
        panel.add(voterButton);

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage();
                dispose();
            }
        });

        voterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VoterHomePage();
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
