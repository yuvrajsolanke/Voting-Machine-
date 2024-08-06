import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VoterHomePage extends JFrame {
    private JButton loginButton;
    private JButton registerButton;

    public VoterHomePage() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel voterLabel = new JLabel("Voter Section");
        voterLabel.setBounds(150, 30, 100, 25);
        panel.add(voterLabel);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 200, 40);
        panel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(100, 150, 200, 40);
        panel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VoterLogin();
                dispose();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterVoter();
                dispose();
            }
        });
    }
}
