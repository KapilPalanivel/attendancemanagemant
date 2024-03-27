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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Class {
	DefaultTableModel model=new DefaultTableModel();
	Connection con;
	int check;
	JButton edit;
	JButton add;
	JButton delete;
	/**
	 * 
	 */
	public void classView() {
		JFrame frame=new JFrame();
		Font text=new Font("Times New Roman",Font.PLAIN,18);
		Font font=new Font("Times New Roman",Font.BOLD,20);
		
		
		JLabel x=new JLabel("X");
		x.setBounds(965, 10, 100, 20);
		x.setForeground(Color.decode("#37474F"));
		x.setFont(font);
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);
			}
		});
		
		
		JLabel back=new JLabel(">BACK");
		back.setBounds(18, 10, 100, 20);
		back.setForeground(Color.decode("#37474F"));
		back.setFont(font);
		frame.add(back);
		back.addMouseListener(new MouseAdapter() {
			@Override 
			public void mouseClicked(MouseEvent e)
			{
				frame.dispose();
			}
		});
		
		JPanel panel=new JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);
		
		
		JLabel id=new JLabel("ID:");
		id.setBounds(25, 150, 40, 20);
		id.setForeground(Color.decode("#DEE4E7"));
		id.setFont(text);
		frame.add(id);
		
		JTextField idbox=new JTextField();
		idbox.setBounds(60, 150, 50, 25);
		idbox.setBackground(Color.decode("#DEE4E7"));
		idbox.setFont(font);
		idbox.setForeground(Color.decode("#37474F"));
		idbox.setEditable(false);
		frame.add(idbox);
		
		
		JLabel name=new JLabel("NAME:");
		name.setBounds(25, 240, 150, 20);
		name.setForeground(Color.decode("#DEE4E7"));
		name.setFont(text);
		frame.add(name);
		
		JTextField namebox=new JTextField();
		namebox.setBounds(25, 270, 400, 35);
		namebox.setFont(text);
		namebox.setForeground(Color.decode("#37474F"));
		namebox.setBackground(Color.decode("#DEE4E7"));
		namebox.setEditable(false);
		frame.add(namebox);
		
		
		JButton save=new JButton("SAVE");
		save.setBounds(25, 500, 125, 50);
		save.setFont(font);
		save.setBackground(Color.decode("#DEE4E7"));
		save.setForeground(Color.decode("#37474F"));
		save.setEnabled(false);
		frame.add(save);
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(check==1)
				{
					try {
						adder(Integer.parseInt(idbox.getText()),namebox.getText());
					} catch (NumberFormatException | SQLException e1) {
						e1.printStackTrace();
					}
				}
				else if(check ==2 )
				{
					save.setEnabled(false);
					try {
						editer(Integer.parseInt(idbox.getText()),namebox.getText());
					}
					catch(Exception e2)
					{
						e2.printStackTrace();
					}
				}
				try {
					idbox.setText(String.valueOf(getId()));
					edit.setEnabled(false);
					delete.setEnabled(false);
					name.setText("");
					while(model.getRowCount()>0)
						model.removeRow(0);
					tblupdt();
				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}
			}
			
		});
		
		
		edit=new JButton("EDIT");
		edit.setBounds(175, 500, 125, 50);
		edit.setFont(font);
		edit.setEnabled(false);
		edit.setBackground(Color.decode("#DEE4E7"));
		edit.setForeground(Color.decode("#37474F"));
		frame.add(edit);
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edit.setEnabled(false);
				save.setEnabled(true);
				check=2;
				namebox.setEditable(true);
			}
			
		});
		
		
		add=new JButton("ADD");
		add.setBounds(325, 500, 125, 50);
		add.setFont(font);
		add.setBackground(Color.decode("#DEE4E7"));
		add.setForeground(Color.decode("#37474F"));
		frame.add(add);
		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				add.setEnabled(false);
				delete.setEnabled(false);
				save.setEnabled(true);
				namebox.setEditable(true);
				check=1;
				try {
					idbox.setText(String.valueOf(getId()));
				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}
			}
			
		});
		
		delete =new JButton("DELETE");
		delete.setFont(font);
		delete.setBounds(175, 432, 125, 50);
		delete.setForeground(Color.decode("#37474F"));
		delete.setBackground(Color.decode("#DEE4E7"));
		delete.setEnabled(false);
		frame.add(delete);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				namebox.setEditable(false);
				edit.setEnabled(false);
				add.setEnabled(true);
				try {
					deleter(Integer.parseInt(idbox.getText()));
					idbox.setText(String.valueOf(getId()));
					namebox.setText("");
					while(model.getRowCount()>0)
						model.removeRow(0);
					tblupdt();
				}
				catch(Exception e2)
				{
					e2.printStackTrace();
				}
			}
		});
		
		
		JTable table=new JTable() {
			public boolean isCellEditable(int row,int column)
			{
				return false;
			}
		};
		model=(DefaultTableModel)table.getModel();
		model.addColumn("ID");
		model.addColumn("NAME");
		tblupdt();
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		JScrollPane scrp=new JScrollPane(table);
		scrp.setBounds(500, 50, 480, 525);
		frame.add(scrp);
		
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e1)
			{
				int row=table.getSelectedRow();
				idbox.setText(String.valueOf(table.getModel().getValueAt(row, 0)));
				namebox.setText(String.valueOf(table.getModel().getValueAt(row, 1)));
				edit.setEnabled(true);
				save.setEnabled(false);
				delete.setEnabled(true);
			}
		});
		
		
		frame.setSize(1000,600);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getContentPane().setBackground(Color.decode("#37474F"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void tblupdt() {
		try {
			ResultSet rs=dbSearch();
			for(int i=0;rs.next();i++)
			{
				model.addRow(new Object[0]);
				model.setValueAt(rs.getInt("id"), i, 0);
				model.setValueAt(rs.getString("name"), i, 1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int getId() throws SQLException{
		Statement st=con.createStatement();
		String query="SELECT MAX(id) from class";
		ResultSet rs=st.executeQuery(query);
		if(rs.next())
		return rs.getInt("MAX(id)")+1;
		else
			return 1;
	}
	public ResultSet dbSearch() throws SQLException{
		String url="jdbc:mysql://localhost:3306/attendance";
		String name="root";
		String pass="Karthi11";
		String query="SELECT * FROM class";
		con=DriverManager.getConnection(url, name, pass);
		Statement st=con.createStatement();
		return st.executeQuery(query);
	}
	public void adder(int id,String name) throws SQLException{
		String query="INSERT INTO class VALUES("+id+",'"+name+"')";
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
	public void editer(int id,String name) throws SQLException{
		String query="UPDATE class set name = '"+name+"' WHERE id = "+id;
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
	public void deleter(int id) throws SQLException{
		String query="DELETE FROM class WHERE id = "+id;
		Statement st=con.createStatement();
		st.executeUpdate(query);
	}
}
