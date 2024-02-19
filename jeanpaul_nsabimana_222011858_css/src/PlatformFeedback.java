import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

public class PlatformFeedback extends JFrame {

   

    // Components for feedback form
    private JLabel titleLabel;
    private JLabel userLabel;
    private JLabel commentsLabel;
    private JLabel dateLabel;

    private JTextField userTextField;
    private JTextArea commentsTextArea;
    private JTextField dateTextField;

    private JButton submitButton;

    // Components for user view
    private JLabel userViewLabel;
    private JTextArea userViewTextArea;

    public PlatformFeedback() {


        titleLabel = new JLabel("Feedback Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(100, 10, 200, 30);

        userLabel = new JLabel("User ID:");
        userLabel.setBounds(50, 50, 80, 20);

        commentsLabel = new JLabel("Comments:");
        commentsLabel.setBounds(50, 80, 80, 20);

        dateLabel = new JLabel("Date:");
        dateLabel.setBounds(50, 200, 80, 20);

        userTextField = new JTextField();
        userTextField.setBounds(150, 50, 150, 20);

        commentsTextArea = new JTextArea();
        commentsTextArea.setBounds(150, 80, 150, 100);

        dateTextField = new JTextField();
        dateTextField.setBounds(150, 200, 150, 20);

        submitButton = new JButton("Submit");
        submitButton.setBounds(50, 230, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });

        // Components for user view
        userViewLabel = new JLabel("User View (User ID : Username):");
        userViewLabel.setBounds(50, 270, 250, 20);

        userViewTextArea = new JTextArea();
        userViewTextArea.setBounds(50, 300, 250, 100);
        userViewTextArea.setEditable(false);

        // Set up the frame
        setLayout(null);
        add(titleLabel);
        add(userLabel);
        add(commentsLabel);
        add(dateLabel);
        add(userTextField);
        add(commentsTextArea);
        add(dateTextField);
        add(submitButton);

        add(userViewLabel);
        add(userViewTextArea);

        loadUserView();
        


    

        // Set frame properties
        setTitle("Feedback Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void submitFeedback() {
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");


           
       
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO platformfeedback (user_id, feedback_comments, feedback_date) VALUES (?, ?, ?)");
          
            stmt.setString(1, userTextField.getText());
            stmt.setString(2, commentsTextArea.getText());
           stmt.setString(3, dateTextField.getText());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Feedback submitted successfully!");

                userTextField.setText("");
                commentsTextArea.setText("");
                dateTextField.setText("");

                loadUserView();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to submit feedback.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
    }

    private void loadUserView() {
        try {
        	

       	 Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT user_id, username FROM users");
            

            StringBuilder userView = new StringBuilder();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String username = rs.getString("username");
                userView.append(userId).append(" : ").append(username).append("\n");
            }
            userViewTextArea.setText(userView.toString());
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading user information.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        new PlatformFeedback();
    }
}
