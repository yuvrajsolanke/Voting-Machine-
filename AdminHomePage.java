import javax.swing.*;
import java.awt.event.*;

public class AdminHomePage extends JFrame {
    private JButton loginButton;
    private JButton backButton;


    public AdminHomePage()  {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel adminLabel = new JLabel("Admin Section");
        adminLabel.setBounds(150, 30, 100, 25);
        panel.add(adminLabel);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 200, 40);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AdminLogin();
                dispose();
            }

        });
        backButton = new JButton("Back");
        backButton.setBounds(100, 160, 200, 40);
        panel.add(backButton);
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                new HomePage();
                dispose();
            }
        });

    }
}
