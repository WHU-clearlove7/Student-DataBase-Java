import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
public class Score {
    Vector<Object> rowData;
    Vector<Object> columnNames;
    //定义数据库需要的全局变量
    PreparedStatement ps=null;
    Connection ct=null;
    ResultSet rs=null;
    static JTable table4=null;
    static JTable table6=null;
    static JTable table7=null;

    public Score(){
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.add("课程号");
        columnNames.add("课程名");
        columnNames.add("成绩");

        rowData = new Vector<Object>();
        //rowData可以存放多行,开始从数据库里取

        try {
            //加载驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");

            ps=ct.prepareStatement("select * from SC");

            rs=ps.executeQuery();

            while(rs.next()){
                //rowData可以存放多行
                Vector<Object> hang=new Vector<Object>();
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
                hang.add(rs.getString(3));
                hang.add(rs.getString(4));
                hang.add(rs.getInt(5));


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
        table4 = new JTable(rowData,columnNames){

            private static final long serialVersionUID = 1525421110274312344L;

            public boolean isCellEditable(int row, int column)
            {return false;}//表格不允许被编辑
        };
        table4.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
        table4.setDefaultRenderer(Object.class, tcr);
    }
    public void addScore() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame Addsc = new JFrame("添加成绩信息");
        Addsc.setSize(250, 180);
        Addsc.setLocation(600, 300);
        JPanel addsc = new JPanel();
        JLabel scsno = new JLabel("学号");
        JLabel sccno = new JLabel("课程号");
        JLabel score = new JLabel("成绩");
        JTextField scsnotext  = new JTextField();
        JTextField sccnotext = new JTextField();
        JTextField scoretext  = new JTextField();

        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        addsc.setLayout(null);
        scsno.setBounds(5,5,70,20);
        scsnotext.setBounds(80,5,120,20);
        sccno.setBounds(5,30,70,20);
        sccnotext.setBounds(80,30,120,20);
        score.setBounds(5,60,70,20);
        scoretext.setBounds(80,60,120,20);
        ok.setBounds(50,100,60,20);
        reset.setBounds(130,100,60,20);

        addsc.add(scsno);
        addsc.add(scsnotext);
        addsc.add(sccno);
        addsc.add(sccnotext);
        addsc.add(score);
        addsc.add(scoretext);
        addsc.add(ok);
        addsc.add(reset);

        Addsc.add(addsc);
        Addsc.setVisible(true);

        Addsc.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String sno = scsnotext.getText();
                    String cno = sccnotext.getText();
                    int score = Integer.valueOf(scoretext.getText()).intValue();
                    int count=0;
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    ps=ct.prepareStatement("select Sname from SelectCourse where Sno='"+sno+"' and Cno='"+cno+"'");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        count++;
                    }
                    if(count==0) {
                        JOptionPane.showMessageDialog(null, "此人没有选此课程！");
                    }
                    if(count!=0) {
                        ps=ct.prepareStatement("insert into [SC]([Sno],[Sname],[Cno],[Cname],[Score]) values('"+sno+"',(select Sname from SelectCourse where Sno='"+sno+"' and Cno='"+cno+"'),'"+cno+"',(select Cname from SelectCourse where Cno='"+cno+"' and Sno='"+sno+"'),"+score+")");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "添加成功！");
                        Addsc.dispose();
                        SystemUI.clickable();
                        refreshScore();
                    }
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
                scsnotext.setText("");
                sccnotext.setText("");
                scoretext.setText("");
            }
        });
    }

    public void updateScore() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame UpdateSC = new JFrame("修改成绩信息");
        UpdateSC.setSize(250, 240);
        UpdateSC.setLocation(600, 300);
        JPanel updatesc = new JPanel();
        JLabel yscsno = new JLabel("要修改的学号");
        JLabel scsno = new JLabel("要改成的学号");
        JLabel ysccno = new JLabel("要修改的课程号");
        JLabel sccno = new JLabel("要改成的课程号");
        JLabel score = new JLabel("成绩");
        JTextField yscsnotext = new JTextField();
        JTextField scsnotext  = new JTextField();
        JTextField ysccnotext = new JTextField();
        JTextField sccnotext = new JTextField();
        JTextField scoretext  = new JTextField();

        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        updatesc.setLayout(null);
        yscsno.setBounds(5,5,100,20);
        yscsnotext.setBounds(110,5,120,20);
        scsno.setBounds(5,30,100,20);
        scsnotext.setBounds(110,30,120,20);
        ysccno.setBounds(5,60,100,20);
        ysccnotext.setBounds(110,60,120,20);
        sccno.setBounds(5,90,100,20);
        sccnotext.setBounds(110,90,120,20);
        score.setBounds(5,120,100,20);
        scoretext.setBounds(110,120,120,20);
        ok.setBounds(50,160,60,20);
        reset.setBounds(120,160,60,20);

        updatesc.add(yscsno);
        updatesc.add(yscsnotext);
        updatesc.add(scsno);
        updatesc.add(scsnotext);
        updatesc.add(ysccno);
        updatesc.add(ysccnotext);
        updatesc.add(sccno);
        updatesc.add(sccnotext);
        updatesc.add(score);
        updatesc.add(scoretext);
        updatesc.add(ok);
        updatesc.add(reset);

        UpdateSC.add(updatesc);
        UpdateSC.setVisible(true);

        UpdateSC.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                try {
                    String ysno=yscsnotext.getText();
                    String ycno=ysccnotext.getText();
                    String sno = scsnotext.getText();
                    String cno = sccnotext.getText();
                    int score = Integer.valueOf(scoretext.getText()).intValue();
                    int count=0;
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    ps=ct.prepareStatement("select Sname from SelectCourse where Sno='"+sno+"' and Cno='"+cno+"'");
                    rs=ps.executeQuery();
                    while(rs.next()){
                        count++;
                    }
                    if(count==0) {
                        JOptionPane.showMessageDialog(null, "此人没有选此课程！");
                    }
                    if(count!=0) {
                        ps=ct.prepareStatement("update SC set Sno ='"+sno+"', Cno ='"+cno+"',Sname=(select Sname from SelectCourse where Sno='"+sno+"'and Cno ='"+cno+"'), Cname =(select Cname from SelectCourse where Cno='"+cno+"'and Sno='"+sno+"'), Score ="+score+" where Sno='"+ysno+"'and Cno='"+ycno+"'");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "修改成功！");
                        UpdateSC.dispose();
                        SystemUI.clickable();
                        refreshScore();
                    }
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
                scsnotext.setText("");
                sccnotext.setText("");
                scoretext.setText("");
            }
        });
    }

    public void deleteScore() {
        // TODO 自动生成的方法存根
        int row = table4.getSelectedRow();
        if ( row >= 0 ) {
            String string[] = new String[4];
            string[0] = (String) table4.getValueAt(row, 0);
            string[1] = (String) table4.getValueAt(row, 1);
            string[2] = (String) table4.getValueAt(row, 2);
            string[3] = (String) table4.getValueAt(row, 3);
            try {
                //加载驱动
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //得到连接
                ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                ps=ct.prepareStatement("delete from SC where Sno='"+string[0]+"' and Sname='"+string[1]+"' and Cno='"+string[2]+"' and Cname='"+string[3]+"'");
                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "删除成功！");
                refreshScore();
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

    public void searchScore() {
        // TODO 自动生成的方法存根
        SystemUI.unclickable();
        JFrame SeaSC = new JFrame("查找选课信息");
        SeaSC.setSize(250, 150);
        SeaSC.setLocation(600, 300);
        JPanel seasc = new JPanel();
        JLabel cno = new JLabel("请输入课程号");
        JTextField cnotext  = new JTextField();
        JLabel sno = new JLabel("请输入学号");
        JTextField snotext  = new JTextField();
        JButton ok = new JButton("确定");
        JButton reset = new JButton("重置");

        seasc.setLayout(null);

        cno.setBounds(5,5,80,20);
        cnotext.setBounds(90,5,120,20);
        sno.setBounds(5,30,80,20);
        snotext.setBounds(90,30,120,20);
        ok.setBounds(50,70,60,20);
        reset.setBounds(130,70,60,20);

        seasc.add(cno);
        seasc.add(cnotext);
        seasc.add(sno);
        seasc.add(snotext);
        seasc.add(ok);
        seasc.add(reset);

        SeaSC.add(seasc);
        SeaSC.setVisible(true);

        SeaSC.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                SystemUI.clickable();
            }
        });
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                columnNames=new Vector<Object>();
                //设置列名
                columnNames.add("学号");
                columnNames.add("姓名");
                columnNames.add("课程号");
                columnNames.add("课程名");
                columnNames.add("成绩");
                String str = cnotext.getText();
                String str1 = snotext.getText();
                rowData = new Vector<Object>();
                int count=0;
                //rowData可以存放多行,开始从数据库里取
                try {
                    //加载驱动
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    //得到连接
                    ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                    if(snotext.getText().trim().equals("")&&!cnotext.getText().trim().equals("")){
                        ps=ct.prepareStatement("select * from SC where cno='"+str+"'");
                        rs=ps.executeQuery();
                    }
                    if(cnotext.getText().trim().equals("")&&!snotext.getText().trim().equals("")){
                        ps=ct.prepareStatement("select * from SC where sno='"+str1+"'");
                        rs=ps.executeQuery();
                    }
                    if(!cnotext.getText().trim().equals("")&&!snotext.getText().trim().equals("")){
                        ps=ct.prepareStatement("select * from SC where sno='"+str1+"' and cno='"+str+"'");
                        rs=ps.executeQuery();
                    }
                    if(cnotext.getText().trim().equals("")&&snotext.getText().trim().equals("")){
                        ps=ct.prepareStatement("select * from SC order by Score desc ");
                        rs=ps.executeQuery();
                        JOptionPane.showMessageDialog(null, "请输入查询信息！");
                    }
                    while(rs.next()){
                        //rowData可以存放多行
                        Vector<Object> hang=new Vector<Object>();
                        hang.add(rs.getString(1));
                        hang.add(rs.getString(2));
                        hang.add(rs.getString(3));
                        hang.add(rs.getString(4));
                        hang.add(rs.getInt(5));
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
                    table4 = new JTable(rowData,columnNames){
                        private static final long serialVersionUID = -518524586936803727L;
                        public boolean isCellEditable(int row, int column)
                        {return false;}//表格不允许被编辑
                    };
                    table4.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
                    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
                    tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
                    table4.setDefaultRenderer(Object.class, tcr);
                    SystemUI.scrollPane4.setViewportView(table4);
                    SeaSC.dispose();
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
                snotext.setText("");
            }
        });
    }

    public void statistical() {
        // TODO 自动生成的方法存根
        DecimalFormat df = new DecimalFormat("0.00%");//及格率变百分数形式
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.add("课程号");
        columnNames.add("课程名");
        columnNames.add("成绩");
        columnNames.add("专业");
        columnNames.add("班级");
        rowData = new Vector<Object>();
        //rowData可以存放多行,开始从数据库里取

        Object[] options = {"班级统计","专业统计"};
        int choice =JOptionPane.showOptionDialog(null,"请选择统计的对象","成绩统计",0,JOptionPane.QUESTION_MESSAGE,null,options,options[1]);
        if(choice==0) {
            SystemUI.unclickable();
            JInternalFrame ClassAnalyse=new JInternalFrame("班级成绩分析",false,true,false,false);
            ClassAnalyse.setVisible(true);
            SystemUI.scrollPane4.setViewportView(ClassAnalyse);
            ClassAnalyse.addInternalFrameListener(new InternalFrameAdapter() {
                public void internalFrameClosed(InternalFrameEvent e) {
                    SystemUI.clickable();// after you close it.
                    refreshScore();
                }
            });
            JScrollPane jspc = new JScrollPane();
            ClassAnalyse.add(jspc, BorderLayout.CENTER);
            JToolBar jtbc=new JToolBar();
            ClassAnalyse.add(jtbc,BorderLayout.NORTH);
            JPanel jpc = new JPanel();
            ClassAnalyse.add(jpc,BorderLayout.SOUTH);
            JLabel jlc1=new JLabel("班级名");
            JLabel jlc2=new JLabel("课程号");
            JButton jbc = new JButton("确定");
            JTextField jtfc1= new JTextField();
            JTextField jtfc2= new JTextField();

            jtbc.add(jlc1);
            jtbc.add(jtfc1);
            jtbc.add(jlc2);
            jtbc.add(jtfc2);
            jtbc.add(jbc);
            jbc.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    clearTable();
                    String str = jtfc1.getText();
                    String str1 = jtfc2.getText();
                    int count = 0;
                    int num = 0;
                    double passrate=0;
                    double average=0;
                    try {
                        //加载驱动
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        //得到连接
                        ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                        if(jtfc1.getText().trim().equals("")||jtfc2.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "请填入完整信息！");
                        }
                        else {
                            ps=ct.prepareStatement("select * from View_1 where Class='"+str+"'and Cno='"+str1+"' order by Score desc");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                //rowData可以存放多行
                                count++;
                                Vector<Object> hang=new Vector<Object>();
                                hang.add(rs.getString(1));
                                hang.add(rs.getString(2));
                                hang.add(rs.getString(3));
                                hang.add(rs.getString(4));
                                hang.add(rs.getInt(5));
                                hang.add(rs.getString(6));
                                hang.add(rs.getString(7));
                                //加入到rowData
                                rowData.add(hang);
                            }
                            ps=ct.prepareStatement("select * from View_1 where Class='"+str+"'and Cno='"+str1+"' and Score>=60");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                num++;
                            }
                            ps=ct.prepareStatement("select AVG(Score) from View_1 where Class='"+str+"'and Cno='"+str1+"'");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                average=rs.getDouble(1);
                            }
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
                    table6 = new JTable(rowData,columnNames){
                        private static final long serialVersionUID = 596141572765495714L;
                        public boolean isCellEditable(int row, int column)
                        {return false;}//表格不允许被编辑
                    };
                    table6.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
                    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
                    tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
                    table6.setDefaultRenderer(Object.class, tcr);
                    jspc.setViewportView(table6);
                    if(count==0) {
                        JOptionPane.showMessageDialog(null, "没有查询到相关消息！");
                    }
                    if (count!=0)
                        passrate=num*1.0/count;
                    String psrt=df.format(passrate);
                    JLabel jlc=new JLabel("本班有'"+count+"'个学生参加考试,平均分为'"+average+"',及格率为'"+psrt+"'");
                    jpc.add(jlc);
                }
            });
        }
        if(choice==1) {
            SystemUI.unclickable();
            JInternalFrame DeptAnalyse = new JInternalFrame("专业成绩分析",false,true,false,false);
            DeptAnalyse.setVisible(true);
            SystemUI.scrollPane4.setViewportView(DeptAnalyse);
            DeptAnalyse.addInternalFrameListener(new InternalFrameAdapter() {
                public void internalFrameClosed(InternalFrameEvent e) {
                    SystemUI.clickable();// after you close it.
                    refreshScore();
                }
            });
            JScrollPane jspd = new JScrollPane();
            DeptAnalyse.add(jspd, BorderLayout.CENTER);
            JToolBar jtbd=new JToolBar();
            DeptAnalyse.add(jtbd,BorderLayout.NORTH);
            JPanel jpd = new JPanel();
            DeptAnalyse.add(jpd,BorderLayout.SOUTH);
            JLabel jld1=new JLabel("专业名");
            JLabel jld2=new JLabel("课程号");
            JButton jbd = new JButton("确定");
            JTextField jtfd1= new JTextField();
            JTextField jtfd2= new JTextField();

            jtbd.add(jld1);
            jtbd.add(jtfd1);
            jtbd.add(jld2);
            jtbd.add(jtfd2);
            jtbd.add(jbd);
            jbd.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    clearTable();
                    String str=jtfd1.getText();
                    String str1=jtfd2.getText();
                    int count = 0;
                    int num = 0;
                    double passrate=0;
                    double average=0;
                    try {
                        //加载驱动
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        //得到连接
                        ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
                        if(jtfd1.getText().trim().equals("")||jtfd2.getText().trim().equals("")) {
                            JOptionPane.showMessageDialog(null, "请填入完整信息！");
                        }
                        else {
                            ps=ct.prepareStatement("select * from View_1 where Sdept='"+str+"'and Cno='"+str1+"' order by Score desc");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                //rowData可以存放多行
                                count++;
                                Vector<Object> hang=new Vector<Object>();
                                hang.add(rs.getString(1));
                                hang.add(rs.getString(2));
                                hang.add(rs.getString(3));
                                hang.add(rs.getString(4));
                                hang.add(rs.getInt(5));
                                hang.add(rs.getString(6));
                                hang.add(rs.getString(7));
                                //加入到rowData
                                rowData.add(hang);
                            }
                            ps=ct.prepareStatement("select * from View_1 where Sdept='"+str+"'and Cno='"+str1+"'and Score>=60");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                num++;
                            }
                            ps=ct.prepareStatement("select AVG(Score) from View_1 where Sdept='"+str+"'and Cno='"+str1+"'");
                            rs=ps.executeQuery();
                            while(rs.next()){
                                average=rs.getDouble(1);
                            }
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
                    table6 = new JTable(rowData,columnNames){

                        private static final long serialVersionUID = -6357053302044595304L;

                        public boolean isCellEditable(int row, int column)
                        {return false;}//表格不允许被编辑
                    };
                    table6.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
                    DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
                    tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
                    table6.setDefaultRenderer(Object.class, tcr);
                    jspd.setViewportView(table6);
                    if(count==0) {
                        JOptionPane.showMessageDialog(null, "没有查询到相关消息！");
                    }
                    if (count!=0)
                        passrate=num*1.0/count;
                    String psrt=df.format(passrate);
                    JLabel jld=new JLabel("本专业有'"+count+"'个学生参加考试,平均分为'"+average+"',及格率为'"+psrt+"'");
                    jpd.add(jld);
                }
            });
        }
    }

    public void refreshScore() {
        // TODO 自动生成的方法存根
        new Score();
        SystemUI.scrollPane4.setViewportView(Score.table4);
    }

    public static synchronized void clearTable() {
        try {
            ((DefaultTableModel)table6.getModel()).getDataVector().clear();
            ((DefaultTableModel)table6.getModel()).fireTableDataChanged();
            table6.updateUI();
        }catch(Exception e){

        }
    }

    public void totalrank() {
        // TODO 自动生成的方法存根
        JFrame jf = new JFrame("总分排名");
        jf.setVisible(true);
        jf.setSize(250, 300);
        JScrollPane js=new JScrollPane();
        jf.add(js,BorderLayout.CENTER);
        columnNames=new Vector<Object>();
        //设置列名
        columnNames.add("名次");
        columnNames.add("学号");
        columnNames.add("总分");
        rowData = new Vector<Object>();
        try {
            //加载驱动
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //得到连接
            ct=DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=S","sa","TZYWWX821722");
            ps=ct.prepareStatement("select SNO,sum(Score) sum_grade from SC group by SC.SNO having count(CNO)>=1 order by sum_grade desc");
            rs=ps.executeQuery();
            int rank=0;
            while(rs.next()){
                //rowData可以存放多行
                rank++;
                Vector<Object> hang=new Vector<Object>();
                hang.add(rank);
                hang.add(rs.getString(1));
                hang.add(rs.getString(2));
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
        table7 = new JTable(rowData,columnNames){
            private static final long serialVersionUID = 3701216243676371199L;
            public boolean isCellEditable(int row, int column)
            {return false;}//表格不允许被编辑
        };
        table7.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//只允许选中一行
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 设置table内容居中
        tcr.setHorizontalAlignment(JLabel.CENTER);// 这句和上句作用一样
        table7.setDefaultRenderer(Object.class, tcr);
        js.setViewportView(table7);
    }
}