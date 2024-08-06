import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminLogin extends JFrame {
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JLabel statusLabel;

    public AdminLogin() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 200, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 60, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 60, 200, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(150, 100, 80, 25);
        panel.add(loginButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 140, 300, 25);
        panel.add(statusLabel);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (authenticate(username, password)) {
                    new AdminOptions();
                    dispose();
                } else {
                    //statusLabel.setText("Invalid username or password.");
                    JOptionPane.showMessageDialog(statusLabel,"Incorrect username or password","click",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean authenticate(String username, String password) {
        boolean isAuthenticated = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vm", "root", "yuvi");
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAuthenticated;
    }
}
