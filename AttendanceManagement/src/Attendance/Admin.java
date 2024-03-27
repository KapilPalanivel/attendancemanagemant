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

public class Admin {
	DefaultTableModel model = new DefaultTableModel();
	Font text = new Font("Times New Roman", Font.PLAIN, 18);
	Connection con;
	int check;
	JButton edit;
	JButton delete;
	JButton add;

	public void adminView() throws NumberFormatException, SQLException {
		JFrame frame = new JFrame();
		Font font = new Font("Times New Roman", Font.BOLD, 20);

		JLabel x = new JLabel("X");
		x.setForeground(Color.decode("#37474F"));
		x.setBounds(965, 10, 100, 20);
		x.setFont(font);
		frame.add(x);
		x.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		JLabel back = new JLabel("<Back");
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

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 1000, 35);
		panel.setBackground(Color.decode("#DEE4E7"));
		frame.add(panel);

		JTable table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		model = (DefaultTableModel) table.getModel();
		model.addColumn("ID");
		model.addColumn("USERNAME");
		model.addColumn("NAME");
		tblupdt();
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		JScrollPane scr = new JScrollPane(table);
		scr.setBounds(500, 50, 485, 525);
		frame.add(scr);

		JLabel id = new JLabel("ID:");
		id.setFont(text);
		id.setBounds(25, 60, 40, 20);
		id.setForeground(Color.decode("#DEE4E7"));
		frame.add(id);

		JTextField idbox = new JTextField();
		idbox.setBounds(60, 60, 50, 25);
		idbox.setBackground(Color.decode("#DEE4E7"));
		idbox.setFont(text);
		idbox.setForeground(Color.decode("#37474F"));
		idbox.setEditable(false);
		frame.add(idbox);

		JLabel user = new JLabel("USERNAME:");
		user.setFont(text);
		user.setBounds(25, 120, 150, 20);
		user.setForeground(Color.decode("#DEE4E7"));
		frame.add(user);

		JTextField userbox = new JTextField();
		userbox.setBounds(25, 160, 400, 35);
		userbox.setBackground(Color.decode("#DEE4E7"));
		userbox.setFont(text);
		userbox.setForeground(Color.decode("#37474F"));
		userbox.setEditable(false);
		frame.add(userbox);

		JLabel name = new JLabel("NAME:");
		name.setBounds(25, 240, 150, 20);
		name.setFont(text);
		name.setForeground(Color.decode("#DEE4E7"));
		frame.add(name);

		JTextField namebox = new JTextField();
		namebox.setFont(text);
		namebox.setBounds(25, 270, 400, 35);
		namebox.setBackground(Color.decode("#DEE4E7"));
		namebox.setForeground(Color.decode("#37474F"));
		namebox.setEditable(false);
		frame.add(namebox);

		JLabel pass = new JLabel("PASSWORD:");
		pass.setFont(text);
		pass.setBounds(25, 350, 150, 20);
		pass.setForeground(Color.decode("#DEE4E7"));
		frame.add(pass);

		JTextField passbox = new JTextField();
		passbox.setBounds(25, 380, 400, 35);
		passbox.setForeground(Color.decode("#37474F"));
		passbox.setFont(text);
		passbox.setBackground(Color.decode("#DEE4E7"));
		passbox.setEditable(false);
		frame.add(passbox);

		JButton save = new JButton("SAVE");
		save.setBounds(25, 500, 125, 50);
		save.setFont(font);
		save.setBackground(Color.decode("#DEE4E7"));
		save.setForeground(Color.decode("#37474F"));
		save.setEnabled(false);
		frame.add(save);
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (check == 1) {
					try {
						adder(Integer.parseInt(idbox.getText()), userbox.getText(), namebox.getText(),
								passbox.getText());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (check == 2) {
					save.setEnabled(false);
					try {

						if (passbox.getText().equals("")) {
							editer(Integer.parseInt(idbox.getText()), userbox.getText(), namebox.getText());
						} else
							editer(Integer.parseInt(idbox.getText()), userbox.getText(), namebox.getText(),
									passbox.getText());

					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				try {
					idbox.setText(String.valueOf(getId()));
					edit.setEnabled(false);
					delete.setEnabled(false);
					namebox.setText("");
					userbox.setText("");
					passbox.setText("");
					while (model.getRowCount() > 0)
						model.removeRow(0);
					tblupdt();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		});

		edit = new JButton("EDIT");
		edit.setBounds(170, 500, 125, 50);
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
				check = 2;
				userbox.setEditable(true);
				namebox.setEditable(true);
				passbox.setEditable(true);
			}

		});

		add = new JButton("ADD");
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
				passbox.setEditable(true);
				namebox.setEditable(true);
				check = 1;
				try {
					idbox.setText(String.valueOf(getId()));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		delete = new JButton("DELETE");
		delete.setBounds(175, 432, 125, 50);
		delete.setFont(font);
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
				delete.setEnabled(false);
				save.setEnabled(false);
				try {
					deleter(Integer.parseInt(idbox.getText()));
					idbox.setText(String.valueOf(getId()));
					namebox.setText("");
					userbox.setText("");
					passbox.setText("");
					while (model.getRowCount() > 0)
						model.removeRow(0);
					tblupdt();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		});

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				passbox.setText("");
				idbox.setText(String.valueOf(table.getModel().getValueAt(row, 0)));
				userbox.setText(String.valueOf(table.getModel().getValueAt(row, 1)));
				namebox.setText(String.valueOf(table.getModel().getValueAt(row, 2)));
				edit.setEnabled(true);
				userbox.setEditable(false);
				passbox.setEditable(false);
				namebox.setEditable(false);
				save.setEnabled(true);
				delete.setEnabled(true);
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

	public void tblupdt() {
		try {
			ResultSet rs = dbSearch();
			for (int i = 0; rs.next(); i++) {
				model.addRow(new Object[0]);
				model.setValueAt(rs.getInt("id"), i, 0);
				model.setValueAt(rs.getString("username"), i, 1);
				model.setValueAt(rs.getString("name"), i, 2);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet dbSearch() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/attendance";
		String name = "root";
		String pass = "Karthi11";
		String query = "SELECT * FROM user WHERE prio = 1";
		con = DriverManager.getConnection(url, name, pass);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}

	public int getId() throws SQLException {
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery("SELECT MAX(id) from user");
		if (rs.next()) {
			return rs.getInt("MAX(id)") + 1;
		} else {
			return 1;
		}
	}

	public void adder(int id, String user, String name, String password) throws SQLException {
		String query = "INSERT INTO user VALUES(" + id + ",'" + user + "','" + name + "','" + password + "',1)";
		Statement sta = con.createStatement();
		sta.executeUpdate(query);
	}

	public void deleter(int id) throws SQLException {
		String query = "DELETE FROM user WHERE id=" + id;
		Statement st = con.createStatement();
		st.executeUpdate(query);
	}

	public void editer(int id, String user, String name) throws SQLException {
		String qery = "UPDATE user SET username='" + user + "',name='" + name + "'WHERE id=" + id;
		Statement st = con.createStatement();
		st.executeUpdate(qery);
	}

	public void editer(int id, String user, String name, String pass) throws SQLException {
		String qery = "UPDATE user SET username='" + user + "',name='" + name + "',password = '" + pass + "'WHERE id="
				+ id;
		Statement st = con.createStatement();
		st.executeUpdate(qery);
	}
}
