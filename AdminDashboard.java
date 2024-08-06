import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {
    private JTextArea resultArea;

    public AdminDashboard() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.add(panel);
        
        placeComponents(panel);
        
        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(new BorderLayout());
        
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        fetchElectionData();
    }

    private void fetchElectionData() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vm", "root", "yuvi");
            String candidateQuery = "SELECT * FROM candidates";
            PreparedStatement candidateStatement = connection.prepareStatement(candidateQuery);
            ResultSet candidateResultSet = candidateStatement.executeQuery();
            StringBuilder resultText = new StringBuilder("Candidates:\n");
            while (candidateResultSet.next()) {
                int id = candidateResultSet.getInt("id");
                String name = candidateResultSet.getString("name");
                int votes = candidateResultSet.getInt("votes");
                resultText.append("ID: ").append(id).append(", Name: ").append(name).append(", Votes: ").append(votes).append("\n");
            }

            String voterQuery = "SELECT * FROM voters";
            PreparedStatement voterStatement = connection.prepareStatement(voterQuery);
            ResultSet voterResultSet = voterStatement.executeQuery();
            resultText.append("\nVoters:\n");
            while (voterResultSet.next()) {
                int id = voterResultSet.getInt("id");
                String name = voterResultSet.getString("name");
                int age = voterResultSet.getInt("age");
                String email = voterResultSet.getString("email");
                String address = voterResultSet.getString("address");
                boolean voted = voterResultSet.getBoolean("voted");
                resultText.append("ID: ").append(id)
                          .append(", Name: ").append(name)
                          .append(", Age: ").append(age)
                          .append(", Email: ").append(email)
                          .append(", Address: ").append(address)
                          .append(", Voted: ").append(voted).append("\n");
            }
            resultArea.setText(resultText.toString());
            candidateResultSet.close();
            candidateStatement.close();
            voterResultSet.close();
            voterStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
