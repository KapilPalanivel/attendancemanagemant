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

public class EditAttendance {
    Connection con;
    DefaultTableModel model=new DefaultTableModel();
    public void editView() throws SQLException
    {
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


        JTable table=new JTable(){
            public boolean isCellEditable(int row,int column)
            {
                if(column==3)
                return true;
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

        JLabel dt=new JLabel("DATE:");
        dt.setFont(text);
        dt.setBounds(25, 60, 75, 20);
        dt.setForeground(Color.decode("#DEE4E7"));
        frame.add(dt);

        JTextField dtbox=new JTextField();
        dtbox.setFont(text);
        dtbox.setBounds(100, 60, 150, 25);
        dtbox.setBackground(Color.decode("#DEE4E7"));
        dtbox.setForeground(Color.decode("#37474F"));
        String d=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        dtbox.setText(d);
        frame.add(dtbox);


        JLabel classes=new JLabel("CLASS:");
        classes.setFont(text);
        classes.setBounds(25, 150, 100, 20);
        classes.setForeground(Color.decode("#DEE4E7"));
        frame.add(classes);

        @SuppressWarnings({ "rawtypes", "unchecked" })
        JComboBox clssBox=new JComboBox(classEt());
        clssBox.setBounds(110, 150, 50, 25);
        frame.add(clssBox);


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
                    tblupdt(String.valueOf(clssBox.getSelectedItem()), dtbox.getText());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });



        JButton ab=new JButton("ABSENT");
        ab.setFont(font);
        ab.setBounds(75, 365, 150, 50);
        ab.setBackground(Color.decode("#DEE4E7"));
        ab.setForeground(Color.decode("#37474F"));
        frame.add(ab);
        ab.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                table.setValueAt("Absent", table.getSelectedRow(), 2);
            }
            
        });



        JButton pre=new JButton("PRESENT");
        pre.setFont(font);
        pre.setBounds(275, 365, 150, 50);
        pre.setBackground(Color.decode("#DEE4E7"));
        pre.setForeground(Color.decode("#37474F"));
        frame.add(pre);
        pre.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                table.setValueAt("Present", table.getSelectedRow(), 2);
            }
        });


        JButton submit=new JButton("SUBMIT");
        submit.setFont(font);
        submit.setBackground(Color.decode("#DEE4E7"));
        submit.setForeground(Color.decode("#37474F"));
        submit.setBounds(75, 450, 150, 50);
        frame.add(submit);
        submit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<table.getRowCount();i++)
                {
                    try {
                        edititem(Integer.parseInt(String.valueOf(table.getValueAt(i, 0))),String.valueOf(table.getValueAt(i, 2)), dtbox.getText());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        JButton del=new JButton("DELETE");
        del.setFont(font);
        del.setBackground(Color.decode("#DEE4E7"));
        del.setForeground(Color.decode("#37474F"));
        del.setBounds(275, 450, 150, 50);
        frame.add(del);
        del.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String query="DELETE FROM attend where class = '"+String.valueOf(clssBox.getSelectedItem())+"' AND dt = '"+dtbox.getText()+"'";
                try {
                    Statement st=con.createStatement();
                    st.executeUpdate(query);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                for(int i=0;i<model.getRowCount();i++)
                {
                    model.removeRow(i);
                    model.setRowCount(0);
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

    public void connect() throws SQLException
    {
        String url="jdbc:mysql://localhost:3306/attendance";
        String name="root";
        String pass="Karthi11";
        con=DriverManager.getConnection(url, name, pass);
    }
    public ResultSet dbSearch(String classes,String dt) throws SQLException
    {
        String query="SELECT * from attend,students WHERE attend.stid=students.id AND attend.class = '"+classes+"' AND attend.dt = '"+dt+"'";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        return rs;
    }
    public String[] classEt() throws SQLException{
        String query="SELECT name from class";
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery(query);
        String[] arr=new String[25];
        int i=0;
        while (rs.next()) {
            arr[i++]=rs.getString("name");
        }
        return arr;
    }
    public void tblupdt(String classes,String dt) throws SQLException{
        try {
            for(int i=0;i<model.getRowCount();i++)
            {
                model.removeRow(i);
                model.setRowCount(0);
            }
            ResultSet rs=dbSearch(classes, dt);
            for(int i=0;rs.next();i++)
            {
                model.addRow(new Object[0]);
                model.setValueAt(rs.getInt("stid"), i, 0);
                model.setValueAt(rs.getString("name"), i, 1);
                model.setValueAt(rs.getString("status"), i, 2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void edititem(int id,String status,String dt) throws SQLException{
        String query="UPDATE attend SET status = '"+status+"' WHERE stid = "+id+" AND dt  = '"+dt+"'";
        Statement st=con.createStatement();
        st.executeUpdate(query);
    }
}
