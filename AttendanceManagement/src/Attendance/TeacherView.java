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

public class TeacherView {
	public void tcView(int id) throws SQLException{
		JFrame frame=new JFrame();
		Font f=new Font("Times New Roman",Font.BOLD,20);
		
		
		JLabel x=new JLabel("X");
		x.setBounds(965, 10, 100, 20);
		x.setForeground(Color.decode("#37474F"));
		x.setFont(f);
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});
		
		
		JLabel min=new JLabel("__");
		min.setBounds(935, 5, 100, 20);
		x.setFont(f);
		x.setForeground(Color.decode("#37474F"));
		frame.add(min);
		min.addMouseListener(new MouseAdapter()
		{
			@Override 
			public void mouseClicked(MouseEvent e)
			{
				frame.setState(JFrame.ICONIFIED);
			}
		});
		
		
		JPanel panel=new JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);
		
		JLabel welcome=new JLabel("Welcome "+getUser(id)+",");
		welcome.setBounds(10, 50, 250, 20);
		welcome.setForeground(Color.decode("#DEE4E7"));
		welcome.setFont(f);
		frame.add(welcome);
		
		
		JButton addattendance=new JButton("ADD ATTENDANCE");
		addattendance.setBounds(150, 200, 650, 60);
		addattendance.setFont(f);
		addattendance.setForeground(Color.decode("#37474F"));
		addattendance.setBackground(Color.decode("#DEE4E7"));
		frame.add(addattendance);
		addattendance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AddAttendance addatt=new AddAttendance();
				try {
					addatt.addView();
				}
				catch(Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		
		
		
		JButton editattendance=new JButton("EDIT ATTENDANCE");
		editattendance.setBounds(150, 350, 650, 60);
		editattendance.setForeground(Color.decode("#37474F"));
		editattendance.setBackground(Color.decode("#DEE4E7"));
		editattendance.setFont(f);
		frame.add(editattendance);
		editattendance.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EditAttendance edatt=new EditAttendance();
				try {
					edatt.editView();
				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}
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
	public String getUser(int id) throws SQLException{
		String url="jdbc:mysql://localhost:3306/attendance";
		String username="root";
		String pass="Karthi11";
		Connection con = DriverManager.getConnection(url,username,pass);
		Statement st=con.createStatement();
		String query="SELECT name FROM user WHERE id = "+id;
		ResultSet rs=st.executeQuery(query);
		rs.next();
		
		return rs.getString("name");
	}
}
