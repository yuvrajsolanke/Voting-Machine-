import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterCandidate extends JFrame {
    private JTextField nameText;
    private JButton registerButton;
    private JLabel statusLabel;
    private JButton backbutton;

    public RegisterCandidate() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 200);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Candidate Name:");
        nameLabel.setBounds(10, 20, 150, 25);
        panel.add(nameLabel);

        nameText = new JTextField(20);
        nameText.setBounds(150, 20, 200, 25);
        panel.add(nameText);

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 60, 200, 25);
        panel.add(registerButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 100, 300, 25);
        panel.add(statusLabel);

        backbutton=new JButton("Back");
        backbutton.setBounds(150,90,200,25);
        panel.add(backbutton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String candidateName = nameText.getText();
                if (registerCandidate(candidateName)) {
                    //  statusLabel.setText("Candidate registered successfully!");
                    JOptionPane.showMessageDialog(nameText, "candidate information registered", "click", JOptionPane.INFORMATION_MESSAGE);
                } else {
                   // statusLabel.setText("Error registering candidate.");
                    JOptionPane.showMessageDialog(statusLabel, "Error registering candidate.", "click", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        backbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminHomePage();
                dispose();
            }
        });
    }

    private boolean registerCandidate(String name) {
        boolean isSuccess = false;
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vm", "root", "yuvi");
            String query = "INSERT INTO candidates (name) VALUES (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            int rowsAffected = preparedStatement.executeUpdate();
            isSuccess = rowsAffected > 0;
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
    public static void main(String[] args) {new RegisterCandidate();}
}
