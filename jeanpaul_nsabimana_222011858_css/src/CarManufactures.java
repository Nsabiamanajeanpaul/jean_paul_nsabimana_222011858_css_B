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

public class CarManufactures extends JFrame {

    private Connection connection;
    

    // Components for inserting data
    private JLabel titleLabel;
    private JLabel manufactureNameLabel;
    private JLabel countryLabel;

    private JTextField manufactureNameTextField;
    private JTextField countryTextField;

    private JButton insertButton;

    // Components for displaying data
   // private JTextArea manufacturesTextArea;

    // Components for updating and deleting
    private JButton updateButton;
    private JButton deleteButton;
    
    private JTable manufacturersTable;
    private JScrollPane scrollPane;

    public CarManufactures() {
       
       

        // Set up the frame
       //setLayout(new GridLayout(3, 1));

        // Set up components for inserting data
        titleLabel = new JLabel("Car Manufactures");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(100, 10, 200, 30);
        
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        manufactureNameLabel = new JLabel("Manufacture Name:");
        manufactureNameLabel.setBounds(50, 50, 120, 20);

        

        manufactureNameTextField = new JTextField();
        manufactureNameTextField.setBounds(180, 50, 150, 30);
        
        countryLabel = new JLabel("Country:");
        countryLabel.setBounds(50, 90, 80, 20);

        countryTextField = new JTextField();
        countryTextField.setBounds(180, 90, 150, 30);

        insertButton = new JButton("Insert");
        insertButton.setBounds(50, 120, 100, 30);
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });
        
        /*

        // Set up components for displaying data
        manufacturesTextArea = new JTextArea();
        manufacturesTextArea.setEditable(false);
        manufacturesTextArea.setBounds(50,400,500,300);
        
        JScrollPane manufacturesScrollPane = new JScrollPane(manufacturesTextArea);
        add(new JLabel("Manufactures Data:"));
        add(manufacturesScrollPane);
        
*/

        manufacturersTable = new JTable();
        scrollPane = new JScrollPane(manufacturersTable);
        add(scrollPane, BorderLayout.SOUTH);
        setLayout(new BorderLayout());
        manufacturersTable.setBounds(50,400,500,300);

        // Add components to the frame
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        
        
        
        // Set up components for updating and deleting
        updateButton = new JButton("Update");
        updateButton.setBounds(50, 160, 100, 30);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateData();
            }
        });

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(160, 210, 100, 30);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteData();
            }
        });

        // Set up back button
        JButton backButton = new JButton("Back");
        backButton.setBounds(270, 250, 100, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminView().setVisible(true);
            }
        });

        // Set up the frame
        setLayout(null);
        add(titleLabel);
        add(manufactureNameLabel);
        add(countryLabel);
        add(manufactureNameTextField);
        add(countryTextField);
        add(insertButton);
       // add(manufacturesTextArea);

        add(updateButton);
        add(deleteButton);
        
        
        add(manufacturersTable);
        
        add(backButton);

        // Load manufactures data
        loadManufacturesData();

        // Set frame properties
        setTitle("Car Manufactures");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void insertData() {
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
             Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

        	
        	
        	
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO carmanufacturers (manufacturer_name, country) VALUES (?, ?)");
            
            stmt.setString(1, manufactureNameTextField.getText());
            stmt.setString(2, countryTextField.getText());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data inserted successfully!");
               
                manufactureNameTextField.setText("");
                countryTextField.setText("");
                // Reload manufactures data
                loadManufacturesData();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to insert data.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
    }

    private void loadManufacturesData() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM carmanufacturers");

            // Create table model with columns: ID, Manufacturer Name, Country
            DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Manufacturer Name", "Country"}, 0);

            // Populate table model with data from ResultSet
            while (rs.next()) {
                int manufactureId = rs.getInt("manufacturer_id");
                String manufactureName = rs.getString("manufacturer_name");
                String country = rs.getString("country");
                model.addRow(new Object[]{manufactureId, manufactureName, country});
            }

            // Set table model for JTable
            manufacturersTable.setModel(model);

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading manufacturers data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateData(){
        int selectedRow = manufacturersTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int manufactureId = (int) manufacturersTable.getValueAt(selectedRow, 0);
                String newManufactureName = JOptionPane.showInputDialog(this, "Enter new manufacturer name:");
                String newCountry = JOptionPane.showInputDialog(this, "Enter new country:");
                // Update the database with the new values
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");
                PreparedStatement ps = conn.prepareStatement("UPDATE carmanufacturers SET manufacturer_name=?, country=? WHERE manufacturer_id=?");
                ps.setString(1, newManufactureName);
                ps.setString(2, newCountry);
                ps.setInt(3, manufactureId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Manufacturer updated successfully.");
                    // Update the JTable to reflect changes
                    manufacturersTable.setValueAt(newManufactureName, selectedRow, 1);
                    manufacturersTable.setValueAt(newCountry, selectedRow, 2);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update manufacturer.");
                }
                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating manufacturer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to update.");
        }
    }

    private void deleteData() {
        int selectedRow = manufacturersTable.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int manufactureId = (int) manufacturersTable.getValueAt(selectedRow, 0);
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");
                PreparedStatement ps = conn.prepareStatement("DELETE FROM carmanufacturers WHERE manufacturer_id = ?");
                ps.setInt(1, manufactureId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    DefaultTableModel model = (DefaultTableModel) manufacturersTable.getModel();
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Manufacturer deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete manufacturer.");
                }
                ps.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting manufacturer.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.");
        }
    }

   
   

    public static void main(String[] args) {
        new CarManufactures();
    }
}
