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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Students {
    DefaultTableModel model=new DefaultTableModel();
    Connection con;
    int check;
    JButton edit;
    JButton delete;
    JButton add;
    public void studentView() throws SQLException{
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
            public void mouseClicked(MouseEvent e)
            {
                System.exit(0);
            }
        });


        JLabel back=new JLabel("<BACK");
        back.setForeground(Color.decode("#37474F"));
        back.setFont(new Font("Times New Roman", Font.BOLD, 17));
        back.setBounds(18, 10, 100, 20);
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

        JTable table=new JTable(){
            public boolean isCellEditable(int row,int column){
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


        JLabel id=new JLabel("ID:");
        id.setFont(text);
        id.setBounds(25, 60, 40, 20);
        id.setForeground(Color.decode("#DEE4E7"));
        frame.add(id);

        JTextField idbox=new JTextField();
        id.setFont(text);
        idbox.setBounds(60, 60, 50, 25);
        idbox.setBackground(Color.decode("#DEE4E7"));
        idbox.setForeground(Color.decode("#37474F"));
        idbox.setEditable(false);
        idbox.setText(String.valueOf(getId()));
        frame.add(idbox);

        JLabel classes=new JLabel("CLASS :");
        classes.setFont(text);
        classes.setBounds(250, 60, 100, 20);
        classes.setForeground(Color.decode("#DEE4E7"));
        frame.add(classes);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox clsBox=new JComboBox(classEt());
        clsBox.setBounds(350, 60, 50, 25);
        clsBox.setEditable(false);
        frame.add(clsBox);


        JLabel user=new JLabel("USERNAME:");
        user.setFont(text);
        user.setBounds(25, 120, 150, 20);
        user.setForeground(Color.decode("#DEE4E7"));
        frame.add(user);

        JTextField userbox=new JTextField();
        userbox.setBounds(25, 160, 400, 35);
        userbox.setBackground(Color.decode("#DEE4E7"));
        userbox.setFont(text);
        userbox.setForeground(Color.decode("#37474F"));
        userbox.setEditable(false);
        frame.add(userbox);


        JLabel name=new JLabel("NAME: ");
        name.setFont(text);
        name.setForeground(Color.decode("#DEE4E7"));
        name.setBounds(25, 240, 150, 20);
        frame.add(name);

        JTextField namebox=new JTextField();
        namebox.setBounds(25, 270, 400, 35);
        namebox.setForeground(Color.decode("#37474F"));
        namebox.setBackground(Color.decode("#DEE4E7"));
        namebox.setFont(text);
        namebox.setEditable(false);
        frame.add(namebox);


        JLabel pass=new JLabel("PASSWORD: ");
        pass.setFont(text);
        pass.setBounds(25, 350, 150, 20);
        pass.setForeground(Color.decode("#DEE4E7"));
        frame.add(pass);

        JTextField passbox=new JTextField();
        passbox.setFont(text);
        passbox.setForeground(Color.decode("#37474F"));
        passbox.setBackground(Color.decode("#DEE4E7"));
        passbox.setFont(text);
        passbox.setBounds(25, 380, 400, 35);
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
                    try {
                        adder(Integer.parseInt(idbox.getText()), userbox.getText(), namebox.getText(), passbox.getText(), String.valueOf(clsBox.getSelectedItem()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                else if(check==2)
                {
                    save.setEnabled(false);
                    try {
                        if(passbox.getText().equals(""))
                            editer(Integer.parseInt(idbox.getText()), userbox.getText(), namebox.getText(), String.valueOf(clsBox.getSelectedItem()));
                        else
                            editer(Integer.parseInt(idbox.getText()), userbox.getText(), namebox.getText(), passbox.getText(), String.valueOf(clsBox.getSelectedItem()));    
                       } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    idbox.setText(String.valueOf(getId()));
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


        edit =new JButton("EDIT");
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
                userbox.setEditable(true);
                namebox.setEditable(true);
                passbox.setEditable(true);
                clsBox.setEditable(false);
            }
            
        });


        add=new JButton("ADD");
        add.setBounds(325, 500, 125, 50);
        add.setFont(font);
        add.setForeground(Color.decode("#37474F"));
        add.setBackground(Color.decode("#DEE4E7"));
        add.setEnabled(true);
        frame.add(add);
        add.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                add.setEnabled(false);
                save.setEnabled(true);
                delete.setEnabled(false);
                edit.setEnabled(false);
                namebox.setEditable(true);
                passbox.setEditable(true);
                clsBox.setEditable(true);
                userbox.setEditable(true);
                check=1;
                try {
                    idbox.setText(String.valueOf(getId()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            
        });



        delete=new JButton("DELETE");
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
                userbox.setEditable(false);
                clsBox.setEditable(false);
                add.setEnabled(true);
                delete.setEnabled(false);
                edit.setEnabled(false);
                try {
                    deleter(Integer.parseInt(idbox.getText()));
                    idbox.setText(String.valueOf(getId()));
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


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                int row=table.getSelectedRow();
                passbox.setText("");
                idbox.setText(String.valueOf(table.getModel().getValueAt(row, 0)));
                userbox.setText(String.valueOf(table.getModel().getValueAt(row, 1)));
                namebox.setText(String.valueOf(table.getModel().getValueAt(row, 2)));
                edit.setEnabled(true);
                delete.setEnabled(true);
                userbox.setEditable(false);
                namebox.setEditable(false);
                clsBox.setEditable(false);
                passbox.setEditable(false);
                save.setEnabled(false);
                add.setEnabled(true);
            }
        });



        frame.setSize(1000, 600);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.decode("#37474F"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void tblupdt() throws SQLException{
        try {
            ResultSet rs=dbSearch();
            for(int i=0;rs.next();i++)
            {
                model.addRow(new Object[0]);
                model.setValueAt(rs.getInt("id"), i, 0);
                model.setValueAt(rs.getString("username"), i, 1);
                model.setValueAt(rs.getString("name"), i, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ResultSet dbSearch() throws SQLException{
        String str="SELECT user.id,user.username,students.name FROM user,students where user.id=students.id ";
        String url="jdbc:mysql://localhost:3306/attendance";
        String user="root";
        String pass="Karthi11";
        con=DriverManager.getConnection(url, user, pass);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(str);
        return rs;
    }
    public int getId() throws SQLException{
        Statement st=con.createStatement();
        String query="SELECT MAX(id) from user";
        ResultSet rs=st.executeQuery(query);
        rs.next();
        return rs.getInt("MAX(id)")+1;
    }
    public void adder(int id,String user,String name,String pass,String classes) throws SQLException
    {
        String add1="INSERT INTO user VALUES("+id+",'"+user+"','"+name+"','"+pass+"',3)";
        String add2="INSERT INTO students VALUES("+id+",'"+name+"','"+classes+"')";
        Statement st=con.createStatement();
        st.executeUpdate(add1);
        st.executeUpdate(add2);
    }
    public void deleter(int id) throws SQLException{
        String del="DELETE FROM user WHERE id = "+id;
        String del1="DELETE FROM students WHERE id = "+id;
        Statement st=con.createStatement();
        st.executeUpdate(del1);
        st.executeUpdate(del);
    }
    public void editer(int id,String user,String name,String pass,String classes) throws SQLException
    {
        String update="UPDATE user SET username = '"+user+"',name = '"+name+"',password = '"+pass+"' WHERE id ="+id;
        String update2="UPDATE students SET name ='"+name+"',class = '"+classes+"' WHERE id ="+id;
        Statement st=con.createStatement();
        st.executeUpdate(update2);
        st.executeUpdate(update);
    }
    public void editer(int id,String user,String name,String classes) throws SQLException{
        String up="UPDATE user SET username = '"+user+"', name ='"+name+"' WHERE id = "+id;
        String up2="UPDATE students SET name ='"+name+"',classs = '"+classes+"' WHERE id ="+id;
        Statement st=con.createStatement();
        st.executeUpdate(up2);
        st.executeUpdate(up);
    }
    public String[] classEt() throws SQLException{
        String str1="SELECT name from class";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(str1);
        String rt[]=new String[25];
        int i=0;
        while (rs.next()) {
            rt[i++]=rs.getString("name");
        }
        return rt;
    }
}
