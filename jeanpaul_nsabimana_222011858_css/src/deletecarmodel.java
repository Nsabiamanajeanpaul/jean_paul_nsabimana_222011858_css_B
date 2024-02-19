import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class deletecarmodel extends JFrame {
	private JTextField carmodelidField;
	 

    public deletecarmodel() {
        setTitle("Car models");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        
        
        

     

        JLabel assignmentidLabel = new JLabel("ID:");
        assignmentidLabel.setBounds(20, 110, 80, 25);
        add(assignmentidLabel);

        carmodelidField = new JTextField();
        carmodelidField.setBounds(120, 110, 200, 25);
        add(carmodelidField);

        

        JButton updateButton = new JButton("DELETE Car model");
        updateButton.setBounds(20, 180, 200, 25);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAssignment();
            }
        });
        add(updateButton);

        JButton backButton = new JButton("Back to Admin Dashboard");
        backButton.setBounds(20, 220, 200, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to redirect to AdminDash.java
            	new AdminView().setVisible(true);
                
            }
        });
        add(backButton);
        
     
    }

    private void deleteAssignment() {
    	String assignmentId = carmodelidField.getText();
    	

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

            String sql = "DELETE FROM carmodels  WHERE model_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                preparedStatement.setString(1, assignmentId);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "model DELETED successfully!");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error DELETING model: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new deletecarmodel().setVisible(true);
            }
        });
    }
}

