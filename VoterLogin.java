

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class VoterLogin extends JFrame {
    private JTextField userText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JLabel success;

    public VoterLogin() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 150);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userLabel = new JLabel("Email");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100, 50, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        success = new JLabel("");
        success.setBounds(10, 110, 300, 25);
        panel.add(success);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = userText.getText();
                String password = new String(passwordText.getPassword());
                if (validateLogin(email, password)) {
                   // success.setText("Login successful!");
                    JOptionPane.showMessageDialog(success, "Login successful", "click", JOptionPane.INFORMATION_MESSAGE);
                    new VotingPage(email);
                    dispose(); // Close the login frame
                } else {
                    //success.setText("Login failed.");
                    JOptionPane.showMessageDialog(success,"Login Failed","click",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean validateLogin(String email, String password) {
        boolean isValid = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vm", "root", "yuvi");
            String query = "SELECT * FROM voters WHERE email=? AND password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            isValid = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public static void main(String[] args) {
        new VoterLogin();
    }
}
