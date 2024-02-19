import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Registration extends JFrame implements ActionListener {

    private JLabel usernameLabel, passwordLabel,roleLabel, titleLabel;
    private JTextField usernameField,roleField;
    private JTextField passwordField;
    private JButton loginButton,registerButton;

    public Registration() {
        setTitle("Car survey system - Login");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 350);
        setLocationRelativeTo(null);

        // Create and position labels
        titleLabel = new JLabel("Register to Car survey system");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setBounds(150, 20, 300, 30);
        add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(100, 80, 100, 25);
        add(usernameLabel);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 120, 100, 25);
        add(passwordLabel);
        
        roleLabel = new JLabel("role:");
        roleLabel.setBounds(100, 160, 100, 25);
        add(roleLabel);

        // Create text fields
        usernameField = new JTextField();
        usernameField.setBounds(210, 80, 150, 25);
        add(usernameField);

        passwordField = new JTextField();
        passwordField.setBounds(210, 120, 150, 25);
        add(passwordField);
        
        roleField = new JTextField();
        roleField.setBounds(210, 160, 150, 25);
        add(roleField);

        // Create button
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 210, 100, 30);
        loginButton.addActionListener(e -> new Login().setVisible(true));
        add(loginButton);

        

        registerButton = new JButton("register");
        registerButton.setBounds(300, 210, 100, 30);
        registerButton.addActionListener(this);
        add(registerButton);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            registerUser();
        }
    }
        private void registerUser() {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleField.getText();

            try {
                // Connect to MySQL database
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nsabimana_jean_paul_css", "222011858", "222011858");

                
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);

                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Registration successful!");
                   
                    
                } else {
                    JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
                }

               
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Driver not found: " + ex.getMessage());
            }
        }


        public static void main(String[] args) {
            Registration form = new Registration();
            form.setVisible(true);
        }
    }
