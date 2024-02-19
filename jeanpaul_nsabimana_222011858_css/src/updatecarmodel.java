import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class updatecarmodel extends JFrame {
	private JTextField modelidField;
	 private JTextField modelnameField;
    private JTextField yearField;
  
    private JTextField fuelField;
    private JTextField engineField;
    private JTextField transmissionField;

    public updatecarmodel() {
        setTitle("car models");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        
        JLabel modelidLabel = new JLabel("Id:");
        modelidLabel.setBounds(20, 1, 80, 25);
        add(modelidLabel);

        modelidField = new JTextField();
        modelidField.setBounds(120, 1, 200, 25);
        add( modelidField);
        
        JLabel assignmentLabel = new JLabel("model name:");
        assignmentLabel.setBounds(20, 40, 80, 25);
        add(assignmentLabel);

        modelnameField = new JTextField();
        modelnameField.setBounds(120, 40, 200, 25);
        add( modelnameField);
        
        JLabel yearLabel = new JLabel("year:");
        yearLabel.setBounds(20, 80, 80, 25);
        add(yearLabel);

        yearField = new JTextField();
        yearField.setBounds(120, 80, 200, 25);
        add( yearField);
        
        JLabel fuelLabel = new JLabel("fuel:");
        fuelLabel.setBounds(20, 120, 80, 25);
        add(fuelLabel);

        fuelField = new JTextField();
        fuelField.setBounds(120, 120, 200, 25);
        add( fuelField);

        JLabel engineLabel = new JLabel("engine:");
        engineLabel.setBounds(20, 160, 80, 25);
        add(engineLabel);

        engineField = new JTextField();
        engineField.setBounds(120, 160, 200, 25);
        add(engineField);

     

        JLabel transmissionLabel = new JLabel("transmission:");
        transmissionLabel.setBounds(20, 200, 80, 25);
        add(transmissionLabel);

        transmissionField = new JTextField();
        transmissionField.setBounds(120, 200, 200, 25);
        add(transmissionField);

        

        JButton updateButton = new JButton("update Assignment");
        updateButton.setBounds(20, 240, 150, 25);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAssignment();
            }
        });
        add(updateButton);

        JButton backButton = new JButton("Back to Admin Dashboard");
        backButton.setBounds(20, 280, 200, 25);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	new AdminView().setVisible(true);
                
            }
        });
        add(backButton);
        
     
    }

    private void updateAssignment() {
    	String modelId = modelidField.getText();
    	String modelname =modelnameField.getText();
        String year = yearField.getText();
       
        String fuel =fuelField.getText();
        String engine = engineField.getText();
        String transmission = transmissionField.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

            String sql = "UPDATE carmodels SET model_name = ?,year = ?,fuel_type = ?,engine_size = ?,transmission_type = ? WHERE model_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, modelname);
                preparedStatement.setString(2, year);
                preparedStatement.setString(3, fuel );
                preparedStatement.setString(4, engine);
                preparedStatement.setString(5, transmission);
                preparedStatement.setString(6, modelId);

                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(this, "Car model update successfully!");
            }

            connection.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error updating assignment: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new updatecarmodel().setVisible(true);
            }
        });
    }
}

