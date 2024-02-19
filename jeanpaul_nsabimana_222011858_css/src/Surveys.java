import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Surveys extends JFrame {

    
    private ResultSet resultSet;

    // Components for user and car model information
    private JLabel userInfoLabel;
    private JLabel modelInfoLabel;

    private JTextField userInfoTextField;
    private JTextField modelInfoTextField;

    // Components for survey form
    private JLabel titleLabel;
    private JLabel userLabel;
    private JLabel modelLabel;
    private JLabel ratingLabel;
    private JLabel commentsLabel;
    private JLabel surveyDateLabel;

    private JTextField userTextField;
    private JTextField modelTextField;
    private JTextField ratingTextField;
    private JTextField commentsTextField;
    private JTextField surveyDateTextField;

    private JButton submitButton;
    private JButton feedbackButton;

    public Surveys() {

        userInfoLabel = new JLabel("User Info:");
        userInfoLabel.setBounds(50, 20, 80, 20);

        modelInfoLabel = new JLabel("Car Model Info:");
        modelInfoLabel.setBounds(50, 40, 120, 20);

        userInfoTextField = new JTextField();
        userInfoTextField.setBounds(180, 20, 300, 20);
        userInfoTextField.setEditable(false);

        modelInfoTextField = new JTextField();
        modelInfoTextField.setBounds(180, 40, 300, 20);
        modelInfoTextField.setEditable(false);

        // Initialize components for survey form
        titleLabel = new JLabel("Survey Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(100, 70, 200, 30);

        userLabel = new JLabel("User ID:");
        userLabel.setBounds(50, 110, 80, 20);

        modelLabel = new JLabel("Model ID:");
        modelLabel.setBounds(50, 140, 80, 20);

        ratingLabel = new JLabel("Rating:");
        ratingLabel.setBounds(50, 170, 80, 20);

        commentsLabel = new JLabel("Comments:");
        commentsLabel.setBounds(50, 200, 80, 20);

        surveyDateLabel = new JLabel("Survey Date:");
        surveyDateLabel.setBounds(50, 230, 80, 20);

        userTextField = new JTextField();
        userTextField.setBounds(150, 110, 150, 20);

        modelTextField = new JTextField();
        modelTextField.setBounds(150, 140, 150, 20);

        ratingTextField = new JTextField();
        ratingTextField.setBounds(150, 170, 150, 20);

        commentsTextField = new JTextField();
        commentsTextField.setBounds(150, 200, 150, 20);

        surveyDateTextField = new JTextField();
        surveyDateTextField.setBounds(150, 230, 150, 20);

        submitButton = new JButton("Submit");
        submitButton.setBounds(50, 260, 100, 30);
        submitButton.addActionListener(e -> submitSurvey());

        feedbackButton = new JButton("Feedback");
        feedbackButton.setBounds(160, 260, 100, 30);
        feedbackButton.addActionListener(e -> openFeedbackPage());

        // Set up the frame
        setLayout(null);
        add(userInfoLabel);
        add(modelInfoLabel);
        add(userInfoTextField);
        add(modelInfoTextField);
        add(titleLabel);
        add(userLabel);
        add(modelLabel);
        add(ratingLabel);
        add(commentsLabel);
        add(surveyDateLabel);
        add(userTextField);
        add(modelTextField);
        add(ratingTextField);
        add(commentsTextField);
        add(surveyDateTextField);
        add(submitButton);
        add(feedbackButton);

        // Set up database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Load user and car model information
        loadUserInfo();
        loadModelInfo();

        // Set frame properties
        setTitle("Survey Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadUserInfo() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user_id, username FROM users");


            StringBuilder userInfo = new StringBuilder();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                userInfo.append(userId).append(": ").append(username).append("\n");
            }
            
            userInfoTextField.setText(userInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user information.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadModelInfo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");


            Statement stmt = conn.createStatement();
            String query = "SELECT model_id, model_name FROM carmodels";
            resultSet = stmt.executeQuery(query);

            StringBuilder modelInfo = new StringBuilder();
            while (resultSet.next()) {
                int modelId = resultSet.getInt("model_id");
                String modelName = resultSet.getString("model_name");
                modelInfo.append(modelId).append(": ").append(modelName).append("\n");
            }
            
            modelInfoTextField.setText(modelInfo.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading car model information.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void submitSurvey() {


        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");


        
        
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO surveys (user_id, model_id, rating, comments, survey_date) VALUES (?, ?, ?, ?, ?)");
            
            stmt.setString(1, userTextField.getText());
           stmt.setString(2, modelTextField.getText());
            stmt.setString(3, ratingTextField.getText());
            stmt.setString(4, commentsTextField.getText());
           stmt.setString(5, surveyDateTextField.getText());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Survey submitted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit survey.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
    }


    private void openFeedbackPage() {

        new PlatformFeedback().setVisible(true);

        JOptionPane.showMessageDialog(this, "Redirecting to the Feedback Platform!");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Surveys());
    }
}
