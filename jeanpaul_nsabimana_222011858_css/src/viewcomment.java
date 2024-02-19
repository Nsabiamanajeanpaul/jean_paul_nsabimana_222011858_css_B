import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class viewcomment extends JFrame implements ActionListener {

    private JTable surveysTable;
    private JButton carmanufactsButton, carmodelsButton,delButton;

    public viewcomment() {
        setTitle("Admin Dashboard");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);


        String[] columnNames = {"feedback_id", "user_id", "comment", "date"};


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM platformfeedback");


            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            while (rs.next()) {
                Object[] row = {rs.getInt("feedback_id"),
                        rs.getString("user_id"),
                        rs.getString("feedback_comments"),
                        rs.getString("feedback_date"),
                        
                        };
                model.addRow(row);
            }


            surveysTable = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(surveysTable);
            add(scrollPane, BorderLayout.CENTER);


            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
        }

        // Create buttons
        JPanel buttonPanel = new JPanel();
        carmanufactsButton = new JButton("car manufactures");
        carmanufactsButton.addActionListener(this);
        buttonPanel.add(carmanufactsButton);

        carmodelsButton = new JButton("car models");
        carmodelsButton.addActionListener(this);
        buttonPanel.add(carmodelsButton);

        
        delButton = new JButton("DELETE selected data");
        buttonPanel.add(delButton);
        delButton.addActionListener(this);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == carmanufactsButton) {

             new CarManufactures().setVisible(true);

        } else if (e.getSource() == carmodelsButton) {

             new CarModels().setVisible(true);

        } else if (e.getSource() == delButton) {
        	
        	 int selectedRow = surveysTable.getSelectedRow();
             if (selectedRow == -1) {
                 JOptionPane.showMessageDialog(this, "Please select a row to delete.");
                 return;
             }
             
             int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
             if (confirm == JOptionPane.YES_OPTION) {
                 try {
                	 
                	 Class.forName("com.mysql.cj.jdbc.Driver");
                     Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");
                	 
                     int idToDelete = (int) surveysTable.getValueAt(selectedRow, 0);
                     PreparedStatement ps = conn.prepareStatement("DELETE FROM platformfeedback WHERE feedback_id = ?");
                     ps.setInt(1, idToDelete);
                     int rowsAffected = ps.executeUpdate();
                     if (rowsAffected > 0) {
                         JOptionPane.showMessageDialog(this, "Row deleted successfully.");
                         DefaultTableModel model = (DefaultTableModel) surveysTable.getModel();
                         model.removeRow(selectedRow);
                     } else {
                         JOptionPane.showMessageDialog(this, "Failed to delete row.");
                     }
                     ps.close();
                 } catch (Exception e1) {
                     e1.printStackTrace();
                 }
             }
         }
        	
        	
        }
    

    public static void main(String[] args) {
        viewcomment adminDash = new viewcomment();
        adminDash.setVisible(true);
    }

}

