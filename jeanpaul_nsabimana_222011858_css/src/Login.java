
import javax.swing.*;

import java.awt.Font;
import java.awt.event.*;
import java.sql.*;


public class Login extends JFrame implements ActionListener {

	    private JLabel usernameLabel, passwordLabel, titleLabel;
	    private JTextField usernameField;
	    private JPasswordField passwordField;
	    private JButton loginButton;

	    public Login() {
	        setTitle("Car survey system - Login");
	        setLayout(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(400, 300);
	        setLocationRelativeTo(null);

	        // Create and position labels
	        titleLabel = new JLabel("Login to Car survey system");
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
	        titleLabel.setBounds(40, 20, 320, 30);
	        add(titleLabel);

	        usernameLabel = new JLabel("Username:");
	        usernameLabel.setBounds(40, 70, 80, 25);
	        add(usernameLabel);

	        passwordLabel = new JLabel("Password:");
	        passwordLabel.setBounds(40, 110, 80, 25);
	        add(passwordLabel);

	        // Create text fields
	        usernameField = new JTextField();
	        usernameField.setBounds(130, 70, 200, 25);
	        add(usernameField);

	        passwordField = new JPasswordField();
	        passwordField.setBounds(130, 110, 200, 25);
	        add(passwordField);

	        // Create button
	        loginButton = new JButton("Login");
	        loginButton.setBounds(150, 170, 100, 30);
	        loginButton.addActionListener(this);
	        add(loginButton);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == loginButton) {
	            loginUser();
	        }
	    }

	    private void loginUser() {
	        String username = usernameField.getText();
	        String password = String.valueOf(passwordField.getPassword());

	        try {
	            
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

	            
	            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
	            stmt.setString(1, username);
	            stmt.setString(2, password);

	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                
	                String role = rs.getString("role");
	                if (role.equalsIgnoreCase("admin")) {
	                    new AdminView().setVisible(true);
	                    
	                    this.dispose();
	                } else {
	                	new Surveys().setVisible(true);
	                    this.dispose();
	                }
	            } else {
	                JOptionPane.showMessageDialog(this, "Invalid username or password.");
	            }

	           
	            rs.close();
	            stmt.close();
	            conn.close();
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
	        } catch (ClassNotFoundException ex) {
	            JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
	        }
	    }

	    public static void main(String[] args) {
	        Login login = new Login();
	        login.setVisible(true);
	    }
	}