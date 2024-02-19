import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CarModels extends JFrame {

    

    // Components for inserting data
    private JLabel titleLabel;
    private JLabel modelNameLabel;
    private JLabel manufactureIdLabel;
    private JLabel yearLabel;
    private JLabel fuelTypeLabel;
    private JLabel engineSizeLabel;
    private JLabel transmissionTypeLabel;
    

    private JTextField modelNameTextField;
    private JTextField manufactureIdField;
    private JTextField yearTextField;
    private JTextField fuelTypeTextField;
    private JTextField engineSizeTextField;
    private JTextField transmissionTypeTextField;
    

    private JButton insertButton,BackButton,updateButton,DelButton;

    // Components for displaying data
    private JTextArea userViewTextArea;
    private JLabel  userViewLabel;

    public CarModels() {
      
    	// Set frame properties
        setTitle("Car Models");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
       
       
        titleLabel = new JLabel("Car Models");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(100, 10, 200, 30);

        modelNameLabel = new JLabel("Model Name:");
        modelNameLabel.setBounds(50, 50, 120, 20);
        
        modelNameTextField = new JTextField();
        modelNameTextField.setBounds(180, 50, 150, 20);

        manufactureIdLabel = new JLabel("Manufacture ID:");
        manufactureIdLabel.setBounds(50, 80, 120, 20);
        
        manufactureIdField = new JTextField();
        manufactureIdField.setBounds(180, 80, 150, 20);

        yearLabel = new JLabel("Year:");
        yearLabel.setBounds(50, 110, 80, 20);
        
        yearTextField = new JTextField();
        yearTextField.setBounds(180, 110, 150, 20);

        fuelTypeLabel = new JLabel("Fuel Type:");
        fuelTypeLabel.setBounds(50, 140, 80, 20);
        

        fuelTypeTextField = new JTextField();
        fuelTypeTextField.setBounds(180, 140, 150, 20);

        engineSizeLabel = new JLabel("Engine Size:");
        engineSizeLabel.setBounds(50, 170, 80, 20);
        
        engineSizeTextField = new JTextField();
        engineSizeTextField.setBounds(180, 170, 150, 20);

        transmissionTypeLabel = new JLabel("Transmission Type:");
        transmissionTypeLabel.setBounds(50, 200, 120, 20);
        
        transmissionTypeTextField = new JTextField();
        transmissionTypeTextField.setBounds(180, 200, 150, 20);


        insertButton = new JButton("Insert");
        insertButton.setBounds(50, 240, 100, 30);
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });
        // Set up back button
        BackButton = new JButton("Back");
        BackButton.setBounds(70, 280, 100, 30);
        BackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goBack();
            }
        });
        
        updateButton = new JButton("Delete");
        updateButton.setBounds(90, 320, 100, 30);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new updatecarmodel().setVisible(true);
            }
        });
        
       DelButton = new JButton("Delete");
        DelButton.setBounds(110, 360, 100, 30);
        DelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new deletecarmodel().setVisible(true);
            }
        });

        // Set up components for displaying manufacturers data
        userViewLabel = new JLabel(" View ( ID : manufacture):");
        userViewLabel.setBounds(50, 400, 250, 20);

        userViewTextArea = new JTextArea();
        userViewTextArea.setBounds(50, 480, 250, 100);
        userViewTextArea.setEditable(false);

       

        // Set up the frame
        
        add(titleLabel);
        add(modelNameLabel);
        add(manufactureIdLabel);
        add(yearLabel);
        add(fuelTypeLabel);
        add(engineSizeLabel);
        add(transmissionTypeLabel);
        add(modelNameTextField);
        add(manufactureIdField);
        add(yearTextField);
        add(fuelTypeTextField);
        add(engineSizeTextField);
        add(transmissionTypeTextField);
        add(insertButton);
        add(BackButton);
        
        add(updateButton);
        add(DelButton);
        
        add(userViewLabel);
        add(userViewTextArea);
        
        loadManufacturers();

        
    }

    private void insertData() {
        try {
            

            // Insert the data into the database
            

       	    Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

       	
       	
       	
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO carmodels (model_name, manufacturer_id, year, fuel_type, engine_size, transmission_type) VALUES (?, ?, ?, ?, ?, ?)");
            
           
            stmt.setString(1, modelNameTextField.getText());
            stmt.setString(2, manufactureIdField.getText());
            stmt.setString(3, yearTextField.getText());
            stmt.setString(4, fuelTypeTextField.getText());
            stmt.setString(5, engineSizeTextField.getText());
            stmt.setString(6, transmissionTypeTextField.getText());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data inserted successfully!");
               
                modelNameTextField.setText("");
                yearTextField.setText("");
                fuelTypeTextField.setText("");
                engineSizeTextField.setText("");
                transmissionTypeTextField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error inserting data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

  

    private void loadManufacturers() {
        try {
        	
        	 Class.forName("com.mysql.cj.jdbc.Driver");
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT manufacturer_id, manufacturer_name FROM carmanufacturers");
            

             StringBuilder userView = new StringBuilder();
             while (rs.next()) {
                 int Id = rs.getInt("manufacturer_id");
                 String manufacturename = rs.getString("manufacturer_name");
                 userView.append(Id).append(" : ").append(manufacturename).append("\n");
             }
             userViewTextArea.setText(userView.toString());
         } catch (SQLException e) {
             e.printStackTrace();
             JOptionPane.showMessageDialog(this, "Error loading information.", "Error", JOptionPane.ERROR_MESSAGE);
         } catch (ClassNotFoundException e) {
 			
 			e.printStackTrace();
 		}
    }

    private void goBack() {
       
      
        new AdminView().setVisible(true);
    }

    public static void main(String[] args) {
         new CarModels();
    }
}
