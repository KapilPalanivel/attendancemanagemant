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
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;




public class Teachers {
	DefaultTableModel model=new DefaultTableModel();
	Connection con;
	int check;
	JButton edit;
	JButton delete;
	JButton add;
	public void teachersView() throws SQLException{
		Font text=new Font("Times New Roman",Font.PLAIN,18);
		Font font=new Font("Times New Roman",Font.BOLD,20);
		
		JFrame frame=new JFrame();
		
		
		JLabel x=new JLabel("X");
		x.setForeground(Color.decode("#37474F"));
		x.setBounds(965, 10, 100, 20);
		x.setFont(font);
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e1)
			{
				System.exit(0);
			}
		});
		
		
		JLabel back=new JLabel("<BACK");
		back.setBounds(18, 10, 100, 20);
		back.setForeground(Color.decode("#37474F"));
		back.setFont(font);
		frame.add(back);
		back.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();
			}
		});
		
		
		JPanel panel=new JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);
		
		JTable table = new JTable() {
			public boolean isCellEditable(int row,int column) {
				return false;
			}
		};
		model=(DefaultTableModel)table.getModel();
		model.addColumn("ID");
		model.addColumn("USERNAME");
		model.addColumn("NAME");
		tblupdt();
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		JScrollPane src=new JScrollPane(table);
		src.setBounds(500, 50, 480, 525);
		frame.add(src);


		JLabel id=new JLabel("ID");
		id.setFont(text);
		id.setBounds(25, 60, 40, 20);
		id.setForeground(Color.decode("#DEE4E7"));
		frame.add(id);
		JTextField idBox=new JTextField();
		idBox.setBounds(60, 60, 50, 25);
		idBox.setBackground(Color.decode("#DEE4E7"));
		idBox.setFont(text);
		idBox.setForeground(Color.decode("#37474F"));
		idBox.setEditable(false);
		idBox.setText(String.valueOf(getId()));
		frame.add(idBox);


		JLabel user=new JLabel("USERNAME");
		user.setForeground(Color.decode("#DEE4E7"));
		user.setFont(text);
		user.setBounds(25, 120, 150, 20);
		frame.add(user);

		JTextField userbox=new JTextField();
		userbox.setFont(text);
		userbox.setForeground(Color.decode("#37474F"));
		userbox.setBackground(Color.decode("#DEE4E7"));
		userbox.setBounds(25, 160, 400, 35);
		userbox.setEditable(false);
		frame.add(userbox);


		JLabel name=new JLabel("NAME");
		name.setFont(text);
		name.setForeground(Color.decode("#DEE4E7"));
		name.setBounds(25, 240, 150, 20);
		frame.add(name);

		JTextField namebox=new JTextField();
		namebox.setFont(text);
		namebox.setBounds(25, 270, 400, 35);
		namebox.setForeground(Color.decode("#37474F"));
		namebox.setBackground(Color.decode("#DEE4E7"));
		namebox.setEditable(false);
		frame.add(namebox);


		JLabel pass=new JLabel("PASSWORD");
		pass.setBounds(25, 350, 150, 20);
		pass.setForeground(Color.decode("#DEE4E7"));
		pass.setFont(text);
		frame.add(pass);

		JTextField passbox=new JTextField();
		passbox.setFont(text);
		passbox.setBounds(25, 380, 400, 35);
		passbox.setForeground(Color.decode("#37474F"));
		passbox.setBackground(Color.decode("#DEE4E7"));
		passbox.setEditable(false);
		frame.add(passbox);


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
					try{
						adder(Integer.parseInt(idBox.getText()), userbox.getText(), namebox.getText(), passbox.getText());
					}
					catch(Exception e2)
					{
						e2.printStackTrace();
					}
				}
				else if(check==2)
				{
					save.setEnabled(false);
					try {
						if(passbox.getText().equals(""))
						editter(Integer.parseInt(idBox.getText()), userbox.getText(), namebox.getText());
						else
						editter(Integer.parseInt(idBox.getText()), userbox.getText(), namebox.getText(),passbox.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				try {
					idBox.setText(String.valueOf(getId()));
					edit.setEnabled(false);
					delete.setEnabled(false);
					namebox.setText("");
					userbox.setText("");
					passbox.setText("");
					while (model.getRowCount()>0) {
						model.removeRow(0);
					}
					tblupdt();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});


		edit=new JButton("EDIT");
		edit.setBounds(175, 500, 125, 50);
		edit.setFont(font);
		edit.setForeground(Color.decode("#37474F"));
		edit.setBackground(Color.decode("#DEE4E7"));
		edit.setEnabled(false);
		frame.add(edit);
		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				edit.setEnabled(false);
				save.setEnabled(true);
				check=2;
				userbox.setEditable(true);
				namebox.setEditable(true);
				passbox.setEditable(true);
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
				save.setEnabled(true);
				delete.setEnabled(false);
				userbox.setEditable(true);
				namebox.setEditable(true);
				passbox.setEditable(true);
				check=1;
				try {
					idBox.setText(String.valueOf(getId()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});

		delete = new JButton("DELETE");
		delete.setFont(font);
		delete.setBounds(175, 432, 125, 50);
		delete.setBackground(Color.decode("#DEE4E7"));
		delete.setForeground(Color.decode("#37474F"));
		delete.setEnabled(false);
		frame.add(delete);
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				userbox.setEditable(false);
				namebox.setEditable(false);
				passbox.setEditable(false);
				edit.setEnabled(false);
				add.setEnabled(true);
				try {
					deleter(Integer.parseInt(idBox.getText()));
					idBox.setText(String.valueOf(getId()));
					namebox.setText("");
					passbox.setText("");
					userbox.setText("");
					while(model.getRowCount()>0)
					model.removeRow(0);

					tblupdt();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});


		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e)
			{
				int row=table.getSelectedRow();
				passbox.setText("");
				idBox.setText(String.valueOf(table.getModel().getValueAt(row, 0)));
				userbox.setText(String.valueOf(table.getModel().getValueAt(row, 1)));
				namebox.setText(String.valueOf(table.getModel().getValueAt(row, 2)));
				edit.setEnabled(true);
				delete.setEnabled(true);
				save.setEnabled(false);
				userbox.setEditable(false);
				passbox.setEditable(false);
				namebox.setEditable(false);
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

	public void tblupdt(){
		try{
			ResultSet rs=dbSearch();
			for(int i=0;rs.next();i++)
			{
				model.addRow(new Object[0]);
				model.setValueAt(rs.getInt("id"), i, 0);
				model.setValueAt(rs.getString("username"), i, 1);
				model.setValueAt(rs.getString("name"), i, 2);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public int getId() throws SQLException{
		Statement st=con.createStatement();
		String query="SELECT MAX(id) from user";
		ResultSet rs=st.executeQuery(query);
		if(rs.next())
		{
			return rs.getInt("MAX(id)")+1;
		}
		else 
		return 1;
	}
	public ResultSet dbSearch() throws SQLException{
		String query="SELECT user.id,user.username,teachers.name from user,teachers where user.id=teachers.id";
		String url="jdbc:mysql://localhost:3306/attendance";
		String username="root";
		String pass="Karthi11";
		con = DriverManager.getConnection(url, username, pass);
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery(query);
		return rs;
	}

	public void adder(int id,String user,String name,String password) throws SQLException{
		String adding1="INSERT INTO user VALUES("+id+",'"+user+"','"+name+"','"+password+"',2)";
		String add2="insert into teachers values("+id+",'"+name+"')";
		Statement st=con.createStatement();
		st.executeUpdate(adding1);
		st.executeUpdate(add2);
	}

	public void deleter(int id) throws SQLException{
		String query1 = "DELETE FROM teachers where id="+id;
		String query2="DELETE FROM user where id = "+id;
		Statement st=con.createStatement();
		st.executeUpdate(query2);
		st.executeUpdate(query1);
	}

	public void editter(int id,String user,String name,String pass) throws SQLException{
		String up1="UPDATE user SET username= '"+user+"',name = '"+name+"', password = '"+pass+"' WHERE id = "+id;
		String up2="UPDATE teachers SET name = '"+name+"' WHERE id = "+id;
		Statement st=con.createStatement();
		st.executeUpdate(up1);
		st.executeUpdate(up2);
	}
	
	public void editter(int id,String user,String name) throws SQLException{
		String up1="UPDATE user SET username = '"+user+"',name = '"+name+"' WHERE id = "+id;
		String up2="UPDATE teachers SET name = '"+name+"' WHERE id = "+id;
		Statement statement=con.createStatement();
		statement.executeUpdate(up1);
		statement.executeUpdate(up2);
	}

}
