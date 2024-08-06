
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VotingPage extends JFrame {
    private JComboBox<String> candidateList;
    private JButton voteButton;
    private JLabel statusLabel;
    private String voterEmail;

    public VotingPage(String email) {
        this.voterEmail = email;

        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel selectLabel = new JLabel("Select a Candidate:");
        selectLabel.setBounds(10, 20, 150, 25);
        panel.add(selectLabel);

        candidateList = new JComboBox<>();
        candidateList.setBounds(150, 20, 200, 25);
        panel.add(candidateList);
        loadCandidates();

        voteButton = new JButton("Vote");
        voteButton.setBounds(10, 80, 80, 25);
        panel.add(voteButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 110, 300, 25);
        panel.add(statusLabel);

        voteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCandidate = (String) candidateList.getSelectedItem();
                if (selectedCandidate != null && !selectedCandidate.isEmpty()) {
                    castVote(selectedCandidate);
                } else {
                    statusLabel.setText("Please select a candidate.");
                }
            }
        });
    }

    private void loadCandidates() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vm", "root", "yuvi");
            String query = "SELECT name FROM candidates";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                candidateList.addItem(resultSet.getString("name"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void castVote(String candidateName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vm", "root", "yuvi");
            connection.setAutoCommit(false);

            String updateVotes = "UPDATE candidates SET votes = votes + 1 WHERE name = ?";
            PreparedStatement voteStatement = connection.prepareStatement(updateVotes);
            voteStatement.setString(1, candidateName);
            voteStatement.executeUpdate();

            String updateVoter = "UPDATE voters SET voted = true WHERE email = ?";
            PreparedStatement voterStatement = connection.prepareStatement(updateVoter);
            voterStatement.setString(1, voterEmail);
            voterStatement.executeUpdate();

            connection.commit();
           // statusLabel.setText("Vote cast successfully!");
            JOptionPane.showMessageDialog(statusLabel,"Vote cast successfully","voting",JOptionPane.INFORMATION_MESSAGE);

            voteStatement.close();
            voterStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            //statusLabel.setText("Error casting vote.");
            JOptionPane.showMessageDialog(statusLabel,"error casting vote","voting",JOptionPane.ERROR_MESSAGE);
        }
    }
}
