import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class RegisterVoter extends JFrame {
    private JTextField nameText;
    private JTextField ageText;
    private JTextField emailText;
    private JTextField addressText;
    private JPasswordField passwordText;
    private JButton registerButton;
    private JLabel statusLabel;

    public RegisterVoter() {
        JPanel panel = new JPanel();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400, 300);
        this.add(panel);

        placeComponents(panel);

        this.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(10, 20, 150, 25);
        panel.add(nameLabel);

        nameText = new JTextField(20);
        nameText.setBounds(150, 20, 200, 25);
        panel.add(nameText);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(10, 60, 150, 25);
        panel.add(ageLabel);

        ageText = new JTextField(20);
        ageText.setBounds(150, 60, 200, 25);
        panel.add(ageText);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(10, 100, 150, 25);
        panel.add(emailLabel);

        emailText = new JTextField(20);
        emailText.setBounds(150, 100, 200, 25);
        panel.add(emailText);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(10, 140, 150, 25);
        panel.add(addressLabel);

        addressText = new JTextField(20);
        addressText.setBounds(150, 140, 200, 25);
        panel.add(addressText);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 180, 150, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField(20);
        passwordText.setBounds(150, 180, 200, 25);
        panel.add(passwordText);

        registerButton = new JButton("Register");
        registerButton.setBounds(150, 220, 200, 25);
        panel.add(registerButton);

        statusLabel = new JLabel("");
        statusLabel.setBounds(10, 250, 300, 25);
        panel.add(statusLabel);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameText.getText();
                int age = Integer.parseInt(ageText.getText());
                String email = emailText.getText();
                String address = addressText.getText();
                String password = new String(passwordText.getPassword());
                if (registerVoter(name, age, email, address, password)) {
                    statusLabel.setText("Voter registered successfully!");
                } else {
                    statusLabel.setText("Error registering voter.");
                }
            }
        });
    }

    private boolean registerVoter(String name, int age, String email, String address, String password) {
        boolean isSuccess = false;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // Register voter in database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/vm", "root", "yuvi");
            String query = "INSERT INTO voters (name, age, email, address, password) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, password);
            int rowsAffected = preparedStatement.executeUpdate();
            isSuccess = rowsAffected > 0;

            // Register voter in Excel file
            if (isSuccess) {
                saveToExcel(name, age, email, address, password);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isSuccess;
    }




    private void saveToExcel(String name, int age, String email, String address, String password) throws IOException {
        String excelFilePath = "voters.xlsx";
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Voters");
	int rowcount;

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Name");
        headerRow.createCell(1).setCellValue("Age");
        headerRow.createCell(2).setCellValue("Email");
        headerRow.createCell(3).setCellValue("Address");
        headerRow.createCell(4).setCellValue("Password");
	rowcount=0;

        // Create data row
        Row dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue(name);
        dataRow.createCell(1).setCellValue(age);
        dataRow.createCell(2).setCellValue(email);
        dataRow.createCell(3).setCellValue(address);
        dataRow.createCell(4).setCellValue(password);

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }

}
