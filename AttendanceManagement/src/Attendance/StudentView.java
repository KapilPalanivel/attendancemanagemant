package Attendance;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class StudentView {
	Connection con;
	JFrame frame=new JFrame();
	DefaultTableModel model=new DefaultTableModel();
	public void stView(int id) throws SQLException{
		Font ft=new Font("Times New Roman",Font.BOLD,20);
		
		JLabel x=new JLabel("X");
		x.setBounds(965, 10, 100, 20);
		x.setForeground(Color.decode("#37474F"));
		x.setFont(ft);
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});
		
		JLabel min=new JLabel("_");
		min.setBounds(935, 0, 100, 20);
		min.setFont(ft);
		min.setForeground(Color.decode("#37474F"));
		frame.add(min);
		min.addMouseListener(new MouseAdapter() {
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
		welcome.setFont(ft);
		frame.add(welcome);
		
		
		JTable table=new JTable();
		model=(DefaultTableModel)table.getModel();
		model.addColumn("DATE");
		model.addColumn("STATUS");
		table.setFont(ft);
		table.setRowHeight(50);
		JScrollPane scp=new JScrollPane(table);
		scp.setBounds(500, 50, 480, 525);
		table.setFont(ft);
		table.setRowHeight(50);
		frame.add(scp);
		
		
		JLabel totalClass=new JLabel("TOTAL CLASSES: ");
		totalClass.setBounds(25, 180, 250, 20);
		totalClass.setFont(ft);
		totalClass.setForeground(Color.decode("#DEE4E7"));
		frame.add(totalClass);
		
		JLabel ttbox=new JLabel("");
		ttbox.setBounds(60, 230, 250, 20);
		ttbox.setFont(ft);
		ttbox.setForeground(Color.decode("#DEE4E7"));
		frame.add(ttbox);
		
		JLabel ttpre=new JLabel("CLASSES ATTENDED:");
		ttpre.setBounds(25, 280, 250, 20);
		ttpre.setFont(ft);
		ttpre.setForeground(Color.decode("#DEE4E7"));
		frame.add(ttpre);
		
		JLabel ttatt=new JLabel("");
		ttatt.setBounds(60, 330, 250, 20);
		ttatt.setForeground(Color.decode("#DEE4E7"));
		ttatt.setFont(ft);
		frame.add(ttatt);
		
		JLabel ttap=new JLabel("CLASSES MISSED:");
		ttap.setBounds(25, 380, 250, 20);
		ttap.setFont(ft);
		ttap.setForeground(Color.decode("#DEE4E7"));
		frame.add(ttap);
		
		
		JLabel ttaps=new JLabel("");
		ttaps.setBounds(60, 430, 250, 20);
		ttaps.setForeground(Color.decode("#DEE4E7"));
		ttaps.setFont(ft);
		frame.add(ttaps);
		
		JLabel attper=new JLabel("ATTENDANCE PERCENTAGE: ");
		attper.setBounds(25, 480, 350, 40);
		attper.setForeground(Color.decode("#DEE4E7"));
		attper.setFont(ft);
		frame.add(attper);
		
		JLabel perbox=new JLabel("");
		perbox.setBounds(60, 530,250,20);
		perbox.setFont(ft);
		perbox.setForeground(Color.decode("#DEE4E7"));
		frame.add(perbox);
		
		
		
		int arr[]=stat(id);
		ttbox.setText(String.valueOf(arr[0]));
		ttatt.setText(String.valueOf(arr[1]));
		ttaps.setText(String.valueOf(arr[2]));
		perbox.setText(String.valueOf(arr[3])+"%");
		tableUpdate(id);
		
		
		
		
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
		con=DriverManager.getConnection(url,username,pass);
		String query="SELECT name from user WHERE id = "+id;
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);
		rs.next();
		
		return rs.getString("name");
	}
	public void tableUpdate(int id) {
		try {
			ResultSet res=dbSearch(id);
			for(int i=0;res.next();i++)
			{
				model.addRow(new Object[0]);
				model.setValueAt(res.getString("dt"), i, 0);
				model.setValueAt(res.getString("status"), i, 1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int[] stat(int id) throws SQLException{
		String query1="SELECT COUNT(*) as pre FROM attend WHERE stid = "+id+" AND status = 'Present'";
		String query2="SELECT COUNT(*) as abs FROM attend WHERE stid = "+id+" AND status = 'Absent'";
		int[] x=new int[4];
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query1);
		if(rs.next())
		x[1]=rs.getInt("pre");
		rs = st.executeQuery(query2);
		if(rs.next())
		x[2]=rs.getInt("abs");
		x[0]=x[1]+x[2];
		x[3]=(x[1]*100)/x[0];
		
		tableUpdate(id);
		return x;
	}
	public ResultSet dbSearch(int id) throws SQLException{
		Statement st=con.createStatement();
		String query="SELECT * from attend WHERE stid = "+id+" ORDER BY dt desc";
		ResultSet rs=st.executeQuery(query);
		return rs;
	}
}
