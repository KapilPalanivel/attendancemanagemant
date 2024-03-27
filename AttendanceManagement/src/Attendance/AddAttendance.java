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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class AddAttendance {
    Connection con;
    DefaultTableModel model=new DefaultTableModel();
    public void addView() throws SQLException{
        connect();
        JFrame frame=new JFrame();
        Font text=new Font("Times New Roman",Font.PLAIN,18);
        Font font=new Font("Times New Roman", Font.BOLD, 20);


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
        back.setBounds(10, 10, 100, 20);
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


        JTable table=new JTable(){
            public boolean isCellEditable(int row,int column)
            {
                return false;
            }
        };
        model=(DefaultTableModel)table.getModel();
        model.addColumn("ID");
        model.addColumn("NAME");
        model.addColumn("STATUS");
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        JScrollPane src=new JScrollPane(table);
        src.setBounds(500, 50, 480, 525);
        frame.add(src); 

        JLabel date=new JLabel("DATE:");
        date.setFont(text);
        date.setBounds(25, 60, 75, 20);
        date.setForeground(Color.decode("#DEE4E7"));
        frame.add(date);

        JTextField dtbox=new JTextField();
        dtbox.setFont(text);
        dtbox.setBounds(100, 60, 150, 25);
        dtbox.setBackground(Color.decode("#DEE4E7"));
        dtbox.setForeground(Color.decode("#37474F"));
        String dateInString =new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dtbox.setText(dateInString);
        frame.add(dtbox);


        JLabel classes=new JLabel("CLASS:");
        classes.setFont(text);
        classes.setForeground(Color.decode("#DEE4E7"));
        frame.add(classes);
        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox clss=new JComboBox(classEt());
        clss.setBounds(110, 150, 50, 25);
        frame.add(clss);



        JLabel txt=new JLabel("");
        txt.setFont(text);
        txt.setBounds(125, 525, 350, 20);
        txt.setForeground(Color.red);
        frame.add(txt);

        JButton view=new JButton("VIEW");
        view.setBounds(175, 275, 150, 50);
        view.setFont(font);
        view.setBackground(Color.decode("#DEE4E7"));
        view.setForeground(Color.decode("#37474F"));
        frame.add(view);
        view.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(check(String.valueOf(clss.getSelectedItem()), dtbox.getText()))
                    {
                        txt.setText("Attendance Already Marked!!!");
                    }
                    else{
                        txt.setText("");
                        tblupdt(String.valueOf(clss.getSelectedItem()));
                        System.out.print(String.valueOf(clss.getSelectedItem()));
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            
        });

        JButton ab=new JButton("ABSENT");
        ab.setBounds(75, 365, 150, 50);
        ab.setFont(font);
        ab.setBackground(Color.decode("#DEE4E7"));
        ab.setForeground(Color.decode("#37474F"));
        frame.add(ab);
        ab.setEnabled(false);
        ab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                table.setValueAt("Absent", table.getSelectedRow(), 2);
            }
        });

        JButton pre=new JButton("PRESENT");
        pre.setBounds(275, 365, 150, 50);
        pre.setFont(font);
        pre.setForeground(Color.decode("#37474F"));
        pre.setBackground(Color.decode("#DEE4E7"));
        frame.add(pre);
        pre.setEnabled(false);
        pre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                table.setValueAt("Present", table.getSelectedRow(), 2);
            }
        });

        JButton submit=new JButton("SUBMIT");
        submit.setBounds(175,  450,150, 50);
        submit.setFont(font);
        submit.setForeground(Color.decode("#37474F"));
        submit.setBackground(Color.decode("#DEE4E7"));
        frame.add(submit);
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<table.getRowCount();i++)
                {
                    try {
                        adder(Integer.parseInt(String.valueOf(table.getValueAt(i, 0))), String.valueOf(table.getValueAt(i, 2)), dtbox.getText(), String.valueOf(clss.getSelectedItem()));
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    ab.setEnabled(false);
                    pre.setEnabled(false);
                }
                for(int i=0;i<model.getRowCount();i++)
                {
                    model.removeRow(i);
                    model.setRowCount(0);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                ab.setEnabled(true);
                pre.setEnabled(true);
            }
        });


        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.getContentPane().setBackground(Color.decode("#37474F"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void connect() throws SQLException{
        String url="jdbc:mysql://localhost:3306/attendance";
        String user="root";
        String pass="Karthi11";
        con=DriverManager.getConnection(url, user, pass);
    }

    public ResultSet dbSearch(String classs) throws SQLException{
        String query="SELECT * FROM students where class = '"+classs+"'";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        return rs;
    }
    public String[] classEt() throws SQLException{
        String query="SELECT name from class";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        String[] arr=new String[20];
        int i=0;
        while (rs.next()) {
            arr[i++]=rs.getString("name");
        }
        return arr;
    }
    public void tblupdt(String classes) throws SQLException{
        for(int i=0;i<model.getRowCount();i++)
        {
            model.removeRow(i);
            model.setRowCount(0);
        }
        try {
            ResultSet rs=dbSearch(classes);
            for(int i=0;rs.next();i++)
            {
                model.addRow(new Object[0]);
                model.setValueAt(rs.getInt("id"), i, 0);
                model.setValueAt(rs.getString("name"), i, 1);
                model.setValueAt("Present", i, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void adder(int id,String status,String date,String classes)throws SQLException{
        String add="INSERT INTO attend values("+id+",'"+date+"','"+status+"','"+classes+"')";
        Statement st=con.createStatement();
        st.executeUpdate(add);
    }

    public boolean check(String classes,String date) throws SQLException{
        String query="SELECT * from attend WHERE class = '"+classes+"' AND dt = '"+date+"'";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        if(rs.next())
        return true;
        return false;
    }
}
