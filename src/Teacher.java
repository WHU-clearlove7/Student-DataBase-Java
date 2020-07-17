import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
public class Teacher {
    Vector<Object> rowData,columnNames;
    static JTable table1=null;
    //定义数据库需要的全局变量
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    public Teacher(){
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("工号");
        columnNames.add("姓名");
        columnNames.add("性别");
        columnNames.add("年龄");
        columnNames.add("专业");
        columnNames.add("工薪");

        rowData = new Vector<Object>();
        //rowData可以存放多行,开始从数据库里取
        try {
            //加载驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");

            ps=ct.prepareStatement("select * from Teacher");

            rs=ps.executeQuery();

            while(rs.next()){
                //rowData可以存放多行
                Vector<Object> hang=new Vector<Object>();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getInt(4));
                hang.add(rs.getString(5));
                hang.add(rs.getInt(6));
                //加入到rowData
                rowData.add(hang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{

            try {
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
                if(ct!=null){
                    ct.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        table1 = new JTable(rowData,columnNames){

            private static final long serialVersionUID = -3229560868878458304L;

            public boolean isCellEditable(int row, int column)
            {return false;}//表格不允许被编辑
        };
        table1.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
        table1.setDefaultRenderer(Object.class, tcr);
    }

    public void addTeacher() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame AddTea = new JFrame("添加教师信息");
        AddTea.setSize(250, 270);
        AddTea.setLocation(600, 300);
        JPanel addtea = new JPanel();
        JLabel tno = new JLabel("工号");
        JLabel tname = new JLabel("姓名");
        JLabel tsex = new JLabel("性别");
        JLabel tage = new JLabel("年龄");
        JLabel tdept = new JLabel("专业");
        JLabel tsalary = new JLabel("工薪");
        JTextField tnotext  = new JTextField();
        JTextField tnametext = new JTextField();
        JTextField tsextext  = new JTextField();
        JTextField tagetext  = new JTextField();
        JTextField tdepttext  = new JTextField();
        JTextField tsalarytext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        addtea.setLayout(null);
        tno.setBounds(5,5,70,20);
        tnotext.setBounds(80,5,120,20);
        tname.setBounds(5,30,70,20);
        tnametext.setBounds(80,30,120,20);
        tsex.setBounds(5,60,70,20);
        tsextext.setBounds(80,60,120,20);
        tage.setBounds(5,90,70,20);
        tagetext.setBounds(80,90,120,20);
        tdept.setBounds(5,120,70,20);
        tdepttext.setBounds(80,120,120,20);
        tsalary.setBounds(5,150,70,20);
        tsalarytext.setBounds(80,150,120,20);
        ok.setBounds(50,190,60,20);
        reset.setBounds(130,190,60,20);
        addtea.add(tno);
        addtea.add(tnotext);
        addtea.add(tname);
        addtea.add(tnametext);
        addtea.add(tsex);
        addtea.add(tsextext);
        addtea.add(tage);
        addtea.add(tagetext);
        addtea.add(tdept);
        addtea.add(tdepttext);
        addtea.add(tsalary);
        addtea.add(tsalarytext);
        addtea.add(ok);
        addtea.add(reset);

        AddTea.add(addtea);
        AddTea.setVisible(true);

        AddTea.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String no = tnotext.getText();
                    String  name = tnametext.getText();
                    String sex = tsextext.getText();
                    String dept = tdepttext.getText();
                    int age = Integer.valueOf(tagetext.getText()).intValue();
                    int salary = Integer.valueOf(tsalarytext.getText()).intValue();
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    ps=ct.prepareStatement("insert into [Teacher]([Tno],[Tname],[Tsex],[Tage],[Tdept],[Tsalary]) values('"+no+"','"+name+"','"+sex+"',"+age+",'"+dept+"',"+salary+")");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "添加成功！");
                    AddTea.dispose();
                    SystemUI.clickable();
                    refreshTeacher();

                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "添加失败！");
                    SystemUI.unclickable();
                } finally{
                    try {
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                        if(ct!=null){
                            ct.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {

                tnotext.setText("");
                tnametext.setText("");
                tsextext.setText("");
                tagetext.setText("");
                tdepttext.setText("");
                tsalarytext.setText("");
            }
        });
    }

    public void updateTeacher() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame UpdateTea = new JFrame("修改教师信息");
        UpdateTea.setSize(250, 290);
        UpdateTea.setLocation(600, 300);
        JPanel updatetea = new JPanel();
        JLabel ytno = new JLabel("要修改的工号");
        JLabel tname = new JLabel("要改成的姓名");
        JLabel tsex = new JLabel("要改成的性别");
        JLabel tage = new JLabel("要改成的年龄");
        JLabel tdept = new JLabel("要改成的专业");
        JLabel tsalary = new JLabel("要改成的工薪");
        JTextField ytnotext = new JTextField();
        JTextField tnametext = new JTextField();
        JTextField tsextext  = new JTextField();
        JTextField tagetext  = new JTextField();
        JTextField tdepttext  = new JTextField();
        JTextField tsalarytext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        updatetea.setLayout(null);

        ytno.setBounds(5,30,100,20);
        ytnotext.setBounds(110,30,120,20);
        tname.setBounds(5,60,100,20);
        tnametext.setBounds(110,60,120,20);
        tsex.setBounds(5,90,100,20);
        tsextext.setBounds(110,90,120,20);
        tage.setBounds(5,120,100,20);
        tagetext.setBounds(110,120,120,20);
        tdept.setBounds(5,150,100,20);
        tdepttext.setBounds(110,150,120,20);
        tsalary.setBounds(5,180,100,20);
        tsalarytext.setBounds(110,180,120,20);
        ok.setBounds(50,210,60,20);
        reset.setBounds(120,210,60,20);

        updatetea.add(ytno);
        updatetea.add(ytnotext);
        updatetea.add(tname);
        updatetea.add(tnametext);
        updatetea.add(tsex);
        updatetea.add(tsextext);
        updatetea.add(tage);
        updatetea.add(tagetext);
        updatetea.add(tdept);
        updatetea.add(tdepttext);
        updatetea.add(tsalary);
        updatetea.add(tsalarytext);
        updatetea.add(ok);
        updatetea.add(reset);

        UpdateTea.add(updatetea);
        UpdateTea.setVisible(true);

        UpdateTea.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String yno = ytnotext.getText();
                    String  name = tnametext.getText();
                    String sex = tsextext.getText();
                    String dept = tdepttext.getText();
                    int age = Integer.valueOf(tagetext.getText()).intValue();
                    int salary = Integer.valueOf(tsalarytext.getText()).intValue();
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    ps=ct.prepareStatement("update Teacher set Tname ='"+name+"',Tsex ='"+sex+"', Tdept ='"+dept+"', Tage ='"+age+"', Tsalary ='"+salary+"' where Tno='"+yno+"'");
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    UpdateTea.dispose();
                    SystemUI.clickable();
                    refreshTeacher();
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "修改失败！");
                    SystemUI.unclickable();
                } finally{
                    try {
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                        if(ct!=null){
                            ct.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ytnotext.setText("");
                tnametext.setText("");
                tsextext.setText("");
                tagetext.setText("");
                tdepttext.setText("");
                tsalarytext.setText("");
            }
        });
    }

    public void deleteTeacher() {
        // TODO 自动生成的方法存根
        int row = table1.getSelectedRow();
        if ( row >= 0 ) {
            String string[] = new String[3];
            string[0] = (String) table1.getValueAt(row, 0);
            string[1] = (String) table1.getValueAt(row, 1);
            try {
                //加载驱动
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //得到连接
                ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                ps=ct.prepareStatement("delete from Teacher where Tno='"+string[0]+"' and Tname='"+string[1]+"'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "删除成功！");
                refreshTeacher();
                row=-1;
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "删除失败！");
            } finally{
                try {
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps!=null){
                        ps.close();
                    }
                    if(ct!=null){
                        ct.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "请选中要删除的行！");
        }
    }

    public void searchTeacher() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame SeaTea = new JFrame("查找教师信息");
        SeaTea.setSize(250, 100);
        SeaTea.setLocation(600, 300);
        JPanel seatea = new JPanel();
        JLabel tno = new JLabel("请输入工号");
        JTextField tnotext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        seatea.setLayout(null);

        tno.setBounds(5,5,70,20);
        tnotext.setBounds(80,5,120,20);
        ok.setBounds(50,30,60,20);
        reset.setBounds(130,30,60,20);

        seatea.add(tno);
        seatea.add(tnotext);
        seatea.add(ok);
        seatea.add(reset);

        SeaTea.add(seatea);
        SeaTea.setVisible(true);

        SeaTea.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                columnNames=new Vector<Object>();
                //设置列名
                columnNames.add("工号");
                columnNames.add("姓名");
                columnNames.add("性别");
                columnNames.add("年龄");
                columnNames.add("专业");
                columnNames.add("工薪");
                String str = tnotext.getText();
                rowData = new Vector<Object>();
                int count=0;
                //rowData可以存放多行,开始从数据库里取
                try {
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    if(!tnotext.getText().trim().equals("")) {
                        ps=ct.prepareStatement("select * from Teacher where tno='"+str+"'");

                        rs=ps.executeQuery();
                    }
                    if(tnotext.getText().trim().equals("")) {
                        ps=ct.prepareStatement("select * from Teacher ");
                        rs=ps.executeQuery();
                        JOptionPane.showMessageDialog(null, "请输入查询信息！");
                    }
                    while(rs.next()){
                        //rowData可以存放多行
                        Vector<Object> hang=new Vector<Object>();
                        hang.add(rs.getString(1));
                        hang.add(rs.getString(2));
                        hang.add(rs.getString(3));
                        hang.add(rs.getInt(4));
                        hang.add(rs.getString(5));
                        hang.add(rs.getInt(6));
                        //加入到rowData
                        rowData.add(hang);
                        count++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "查询失败！");
                } finally{
                    try {
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                        if(ct!=null){
                            ct.close();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(count!=0) {
                    table1 = new JTable(rowData,columnNames){

                        private static final long serialVersionUID = -1545099432791772807L;

                        public boolean isCellEditable(int row, int column)
                        {return false;}//表格不允许被编辑
                    };
                    table1.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
                    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
                    tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
                    table1.setDefaultRenderer(Object.class, tcr);
                    SystemUI.scrollPane1.setViewportView(table1);
                    SeaTea.dispose();
                    SystemUI.clickable();}
                if(count==0) {
                    JOptionPane.showMessageDialog(null, "查无此人！");
                }
            }
        });
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                tnotext.setText("");
            }
        });
    }

    public void refreshTeacher() {
        // TODO 自动生成的方法存根
        new Teacher();
        SystemUI.scrollPane1.setViewportView(Teacher.table1);
    }

}