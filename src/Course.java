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

public class Course {
    Vector<Object> rowData,columnNames;
    //定义数据库需要的全局变量
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    static JTable table3=null;
    public Course(){
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("课程号");
        columnNames.add("课程名");
        columnNames.add("先修课课程号");
        columnNames.add("学分");
        columnNames.add("类型");
        rowData = new Vector<Object>();
        //rowData可以存放多行,开始从数据库里取

        try {
            //加载驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");

            ps=ct.prepareStatement("select * from Course");

            rs=ps.executeQuery();

            while(rs.next()){
                //rowData可以存放多行
                Vector<Object> hang=new Vector<Object>();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getInt(4));
                hang.add(rs.getString(5));
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
        table3 = new JTable(rowData,columnNames){
            private static final long serialVersionUID = 6843971351064905400L;

            public boolean isCellEditable(int row, int column)
            {return false;}//表格不允许被编辑
        };
        table3.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
        table3.setDefaultRenderer(Object.class, tcr);
    }
    public void addCourse() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame AddCou = new JFrame("添加课程信息");
        AddCou.setSize(250, 250);
        AddCou.setLocation(600, 300);
        JPanel addCou = new JPanel();
        JLabel cno = new JLabel("课程号");
        JLabel cname = new JLabel("课程名");
        JLabel cpno = new JLabel("先修课");
        JLabel credit = new JLabel("学分");
        JLabel cremark = new JLabel("类型");

        JTextField cnotext  = new JTextField();
        JTextField cnametext = new JTextField();
        JTextField cpnotext  = new JTextField();
        JTextField credittext  = new JTextField();
        JTextField cremarktext  = new JTextField();

        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        addCou.setLayout(null);

        cno.setBounds(5,5,70,20);
        cnotext.setBounds(80,5,120,20);
        cname.setBounds(5,30,70,20);
        cnametext.setBounds(80,30,120,20);
        cpno.setBounds(5,60,70,20);
        cpnotext.setBounds(80,60,120,20);
        credit.setBounds(5,90,70,20);
        credittext.setBounds(80,90,120,20);
        cremark.setBounds(5,120,70,20);
        cremarktext.setBounds(80,120,120,20);
        ok.setBounds(50,150,60,20);
        reset.setBounds(130,150,60,20);

        addCou.add(cno);
        addCou.add(cnotext);
        addCou.add(cname);
        addCou.add(cnametext);
        addCou.add(cpno);
        addCou.add(cpnotext);
        addCou.add(credit);
        addCou.add(credittext);
        addCou.add(cremark);
        addCou.add(cremarktext);

        addCou.add(ok);
        addCou.add(reset);

        AddCou.add(addCou);
        AddCou.setVisible(true);

        AddCou.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String no = cnotext.getText();
                    String  name = cnametext.getText();
                    String pno = cpnotext.getText();
                    String remark = cremarktext.getText();
                    int credit = Integer.valueOf(credittext.getText()).intValue();
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    String sql;
                    sql = "insert into [Course]([Cno],[Cname],[Cpno],[Credit],[Remarks]) values('"+no+"','"+name+"','"+pno+"',"+credit+",'"+remark+"')";
                    ps=ct.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "添加成功！");
                    AddCou.dispose();
                    SystemUI.clickable();
                    refreshCourse();

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

                cnotext.setText("");
                cnametext.setText("");
                cpnotext.setText("");
                credittext.setText("");
                cremarktext.setText("");
            }
        });
    }

    public void updateCourse() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame UpdateCou = new JFrame("修改课程信息");
        UpdateCou.setSize(250, 200);
        UpdateCou.setLocation(600, 300);
        JPanel updatecou = new JPanel();
        JLabel ycno = new JLabel("要修改的课程号");
        JLabel cpno = new JLabel("要改成的先修课");
        JLabel credit = new JLabel("要改成的学分");
        JLabel cremark = new JLabel("要改成的类型");

        JTextField ycnotext = new JTextField();
        JTextField cpnotext  = new JTextField();
        JTextField credittext  = new JTextField();
        JTextField cremarktext  = new JTextField();

        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        updatecou.setLayout(null);
        ycno.setBounds(5,5,100,20);
        ycnotext.setBounds(110,5,120,20);
        cpno.setBounds(5,30,100,20);
        cpnotext.setBounds(110,30,120,20);
        credit.setBounds(5,60,100,20);
        credittext.setBounds(110,60,120,20);
        cremark.setBounds(5,90,100,20);
        cremarktext.setBounds(110,90,120,20);
        ok.setBounds(50,130,60,20);
        reset.setBounds(120,130,60,20);

        updatecou.add(ycno);
        updatecou.add(ycnotext);
        updatecou.add(cpno);
        updatecou.add(cpnotext);
        updatecou.add(credit);
        updatecou.add(credittext);
        updatecou.add(cremark);
        updatecou.add(cremarktext);

        updatecou.add(ok);
        updatecou.add(reset);

        UpdateCou.add(updatecou);
        UpdateCou.setVisible(true);

        UpdateCou.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String yno = ycnotext.getText();
                    String pno = cpnotext.getText();
                    String remark = cremarktext.getText();
                    int credit = Integer.valueOf(credittext.getText()).intValue();
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    String sql;
                    sql = "update Course set Cpno ='"+pno+"', Credit ='"+credit+"', Remarks ='"+remark+"' where Cno='"+yno+"'";
                    ps=ct.prepareStatement(sql);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null, "修改成功！");
                    UpdateCou.dispose();
                    SystemUI.clickable();
                    refreshCourse();
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
                ycnotext.setText("");
                cpnotext.setText("");
                credittext.setText("");
                cremarktext.setText("");
            }
        });
    }

    public void deleteCourse() {
        // TODO 自动生成的方法存根
        int row = table3.getSelectedRow();
        if ( row >= 0 ) {
            String string[] = new String[3];
            string[0] = (String) table3.getValueAt(row, 0);
            string[1] = (String) table3.getValueAt(row, 1);
            try {
                //加载驱动
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //得到连接
                ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                ps=ct.prepareStatement("delete from Course where Cno='"+string[0]+"' and Cname='"+string[1]+"'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "删除成功！");
                refreshCourse();
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

    public void searchCourse() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame SeaCou = new JFrame("查找课程信息");
        SeaCou.setSize(250, 100);
        SeaCou.setLocation(600, 300);
        JPanel seacou = new JPanel();
        JLabel cno = new JLabel("请输入课程号");
        JTextField cnotext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        seacou.setLayout(null);

        cno.setBounds(5,5,80,20);
        cnotext.setBounds(90,5,120,20);
        ok.setBounds(50,30,60,20);
        reset.setBounds(130,30,60,20);

        seacou.add(cno);
        seacou.add(cnotext);
        seacou.add(ok);
        seacou.add(reset);

        SeaCou.add(seacou);
        SeaCou.setVisible(true);

        SeaCou.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                columnNames=new Vector<Object>();
                //设置列名
                columnNames.add("课程号");
                columnNames.add("课程名");
                columnNames.add("先修课课程号");
                columnNames.add("学分");
                columnNames.add("类型");
                String str = cnotext.getText();
                rowData = new Vector<Object>();
                int count=0;
                //rowData可以存放多行,开始从数据库里取
                try {
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    if(!cnotext.getText().trim().equals("")) {
                        ps=ct.prepareStatement("select * from Course where cno='"+str+"'");

                        rs=ps.executeQuery();
                    }
                    else if(cnotext.getText().trim().equals("")) {
                        ps=ct.prepareStatement("select * from Course ");
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
                    table3 = new JTable(rowData,columnNames){

                        private static final long serialVersionUID = -8656035973692724899L;

                        public boolean isCellEditable(int row, int column)
                        {return false;}//表格不允许被编辑
                    };
                    table3.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
                    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
                    tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
                    table3.setDefaultRenderer(Object.class, tcr);
                    SystemUI.scrollPane3.setViewportView(table3);
                    SeaCou.dispose();
                    SystemUI.clickable();}
                if(count==0) {
                    JOptionPane.showMessageDialog(null, "没有查询到相关消息！");
                }
            }
        });
        reset.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0) {
                cnotext.setText("");
            }
        });
    }

    public void refreshCourse() {
        // TODO 自动生成的方法存根
        new Course();
        SystemUI.scrollPane3.setViewportView(Course.table3);
    }

}