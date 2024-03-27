package Attendance;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
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

public class Home {
	public void homeView(int id) throws SQLException {
		JFrame frame = new JFrame();
		Font font = new Font("Times New Roman", Font.BOLD, 20);
		Admin adm = new Admin();

		JLabel x = new JLabel("X");
		x.setBounds(965, 10, 100, 20);
		x.setFont(new Font("Times New Roman", Font.BOLD, 20));
		x.setForeground(Color.decode("#37474F"));
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				System.exit(0);
			}
		});

		JLabel mini = new JLabel("__");
		mini.setBounds(935, 5, 100, 20);
		mini.setForeground(Color.decode("#37474F"));
		frame.add(mini);
		mini.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				frame.setState(Frame.ICONIFIED);
			}
		});

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);

		JLabel welcome = new JLabel("Welcome " + getUser(id) + ",");
		welcome.setForeground(Color.decode("#DEE4E7"));
		welcome.setBounds(10, 50, 250, 20);
		welcome.setFont(font);
		frame.add(welcome);

		JButton student = new JButton("STUDENTS");
		student.setBounds(150, 125, 700, 60);
		student.setFont(font);
		student.setBackground(Color.decode("#DEE4E7"));
		student.setForeground(Color.decode("#37474F"));
		frame.add(welcome);

		JButton students = new JButton("STUDENTS");
		students.setBounds(150, 125, 700, 60);
		students.setForeground(Color.decode("#37474F"));
		students.setBackground(Color.decode("#DEE4E7"));
		students.setFont(font);
		frame.add(students);
		students.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Students s=new Students();
				try {
					s.studentView();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton addattendance = new JButton("ADD ATTENDANCE");
		addattendance.setBounds(150, 250, 400, 60);
		addattendance.setFont(font);
		addattendance.setForeground(Color.decode("#37474F"));
		addattendance.setBackground(Color.decode("#DEE4E7"));
		frame.add(addattendance);
		addattendance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddAttendance ad=new AddAttendance();
				try {
					ad.addView();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton editattendance = new JButton("EDIT ATTENDANCE");
		editattendance.setBounds(600, 250, 250, 60);
		editattendance.setFont(font);
		editattendance.setForeground(Color.decode("#37474F"));
		editattendance.setBackground(Color.decode("#DEE4E7"));
		frame.add(editattendance);
		editattendance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditAttendance ed=new EditAttendance();
				try {
					ed.editView();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		});

		JButton teacher = new JButton("TEACHERS");
		teacher.setBounds(150, 375, 700, 60);
		teacher.setFont(font);
		teacher.setForeground(Color.decode("#37474F"));
		teacher.setBackground(Color.decode("#DEE4E7"));
		frame.add(teacher);
		teacher.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Teachers teach=new Teachers();
				try {
					teach.teachersView();
				} catch (Exception e3) {
					e3.printStackTrace();
				}
			}
		});

		JButton admin = new JButton("ADMIN");
		admin.setBounds(150, 500, 250, 60);
		admin.setFont(font);
		admin.setForeground(Color.decode("#37474F"));
		admin.setBackground(Color.decode("#DEE4E7"));
		frame.add(admin);
		admin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					adm.adminView();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		});

		JButton classes = new JButton("CLASS");
		classes.setBounds(450, 500, 400, 60);
		classes.setFont(font);
		classes.setForeground(Color.decode("#37474F"));
		classes.setBackground(Color.decode("#DEE4E7"));
		frame.add(classes);
		classes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Class classroom = new Class();
				classroom.classView();
			}

		});

		frame.setSize(1000, 600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getContentPane().setBackground(Color.decode("#37474F"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public String getUser(int id) throws SQLException {
		String url = "jdbc:mysql://localhost:3306/attendance";
		String username = "root";
		String pass = "Karthi11";
		String query = "SELECT name FROM user WHERE id = " + id;
		Connection con = DriverManager.getConnection(url, username, pass);
		Statement st = con.createStatement();
		ResultSet re = st.executeQuery(query);
		re.next();

		return re.getString("name");
	}
}
