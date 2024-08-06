import javax.swing.*;
import java.awt.event.*;

public class AdminOptions extends JFrame {
    private JButton registerCandidateButton;
    private JButton adminDashboardButton;

    public AdminOptions() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel optionsLabel = new JLabel("Admin Options");
        optionsLabel.setBounds(150, 30, 100, 25);
        panel.add(optionsLabel);

        registerCandidateButton = new JButton("Register Candidate");
        registerCandidateButton.setBounds(100, 100, 200, 40);
        panel.add(registerCandidateButton);

        adminDashboardButton = new JButton("Admin Dashboard");
        adminDashboardButton.setBounds(100, 150, 200, 40);
        panel.add(adminDashboardButton);

        registerCandidateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegisterCandidate();
                dispose();
            }
        });

        adminDashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminDashboard();
                dispose();
            }
        });
    }
}
