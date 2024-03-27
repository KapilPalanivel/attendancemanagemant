package Attendance;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Login {
	int usr = 0;

	public void loginView() {
		JFrame frame = new JFrame();
		Font text = new Font("TImes New Roman", Font.PLAIN, 20);
		Home hm = new Home();
		TeacherView tview = new TeacherView();
		StudentView sview = new StudentView();

		JLabel attendance = new JLabel("ATTENDANCE");
		attendance.setForeground(Color.decode("#37474F"));
		attendance.setBounds(100, 275, 400, 50);
		attendance.setFont(new Font("Verdana", Font.BOLD, 50));
		frame.add(attendance);
		JLabel management = new JLabel("MANAGEMENT SYSTEM");
		management.setForeground(Color.decode("#37474F"));
		management.setBounds(280, 310, 400, 50);
		management.setFont(new Font("Verdana", Font.BOLD, 15));
		frame.add(management);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 500, 600);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);

		JLabel x = new JLabel("X");
		x.setForeground(Color.decode("#DEE4E7"));
		x.setBounds(965, 20, 100, 20);
		x.setFont(new Font("Times new Roman", Font.BOLD, 20));
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		JLabel minimize = new JLabel("_");
		minimize.setForeground(Color.decode("#DEE4E7"));
		minimize.setBounds(935, 10, 100, 20);
		minimize.setFont(new Font("TImes new Roman", Font.BOLD, 20));
		frame.add(minimize);
		minimize.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setState(JFrame.ICONIFIED);
			}
		});

		JLabel login = new JLabel("LOGIN");
		login.setForeground(Color.decode("#DEE4E7"));
		login.setBounds(625, 100, 350, 75);
		login.setFont(new Font("Times new Roman", Font.BOLD, 65));
		frame.add(login);

		JLabel user = new JLabel("Username");
		user.setForeground(Color.decode("#DEE4E7"));
		user.setBounds(570, 250, 100, 20);
		user.setFont(text);
		frame.add(user);

		JTextField username = new JTextField();
		username.setBounds(570, 285, 360, 35);
		username.setBackground(Color.decode("#DEE4E7"));
		username.setForeground(Color.decode("#37474F"));
		username.setFont(new Font("TImes new Roman", Font.BOLD, 15));
		frame.add(username);

		JLabel pass = new JLabel("Password");
		pass.setBounds(570, 350, 100, 20);
		pass.setForeground(Color.decode("#DEE4E7"));
		pass.setFont(text);
		frame.add(pass);

		JTextField password = new JTextField();
		password.setBounds(570, 385, 360, 35);
		password.setBackground(Color.decode("#DEE4E7"));
		password.setForeground(Color.decode("#37474F"));
		password.setFont(new Font("TImes new Roman", Font.BOLD, 15));
		frame.add(password);

		JLabel warning = new JLabel();
		warning.setForeground(Color.RED);
		warning.setBounds(635, 450, 250, 20);
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(warning);

		JButton loginButton = new JButton("LOGIN");
		loginButton.setBounds(625, 500, 250, 50);
		loginButton.setFont(new Font("TImes new Roman", Font.BOLD, 20));
		loginButton.setForeground(Color.decode("#37474F"));
		loginButton.setBackground(Color.decode("#DEE4E7"));
		frame.add(loginButton);
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int res = dbCheck(username.getText(), password.getText());
					if (res == 0) {
						warning.setText("NO USER FOUND!");
						username.setText("");
						password.setText("");
					} else if (res == -1) {
						warning.setText("WRONG PASSWORD!");
						password.setText("");
					} else {
						warning.setText("");
						if (res == 1) {
							System.out.println(usr);
							hm.homeView(usr);
						} else if (res == 2) {
							tview.tcView(usr);
						} else if (res == 3) {
							sview.stView(usr);
						}
						frame.dispose();
					}
				} catch (SQLException e1) {
					System.out.print(e1.toString());
				}
			}
		});

		frame.setSize(1000, 600);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getContentPane().setBackground(Color.decode("#37474F"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public int dbCheck(String name, String password) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/attendance";
		String username = "root";
		String pass = "Karthi11";
		System.out.println(name + " " + password);
		String str = "SELECT * FROM user WHERE username = '" + name + "'";
		Connection con = DriverManager.getConnection(url, username, pass);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(str);
		if (rs.next()) {
			if (rs.getString("password").equals(password)) {
				usr = rs.getInt("id");
				return rs.getInt("prio");
			} else {
				return -1;
			}
		} else
			return 0;
	}
}
