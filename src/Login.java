import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener {
    private static final long serialVersionUID = 5252772035964963789L;
    private JPanel pan = new JPanel();
    private JLabel namelab = new JLabel("用户名");
    private JLabel passlab = new JLabel("密    码");
    private JTextField nametext = new JTextField();
    private JPasswordField passtext = new JPasswordField();
    public JButton denglu = new JButton("登录");
    public JButton chongzhi = new JButton("重置");
    public Login(){
        this.setLocation(550, 250);
        Font font = new Font("宋体",Font.BOLD,12);
        super.setTitle("欢迎登录小白教务系统");
        pan.setLayout(null);
        namelab.setBounds(20,20,60,30);
        nametext.setBounds(90,20,140,30);
        passlab.setBounds(20,60,60,30);
        passtext.setBounds(90,60,140,30);
        denglu.setBounds(30,120,90,20);
        chongzhi.setBounds(140,120,90,20);
        pan.add(namelab);
        pan.add(nametext);
        pan.add(passlab);
        pan.add(passtext);
        pan.add(denglu);
        pan.add(chongzhi);
        passtext.setFont(font);
        chongzhi.setFont(font);
        denglu.addActionListener(this);
        chongzhi.addActionListener(this);
        super.add(pan);
        super.setSize(300,200);
        super.setVisible(true);
        passtext.addKeyListener(new KeyAdapter() {
            public  void keyPressed(KeyEvent e2) {
                if(e2.getKeyChar()==KeyEvent.VK_ENTER) {//如果密码是enter键
                    denglu.doClick();//点击登录按钮
                } }
        });
    }
    public static void main(String []args){

        new Login();
    }

    public void actionPerformed(ActionEvent arg0) {
        if(arg0.getSource()==denglu){
            denglu();
        }else if (arg0.getSource()==chongzhi){
            chongzhi();
        }
    }
    //登录按钮的事件处理函数
    public void denglu(){
        Jdbc d  = new Jdbc();
        String username = nametext.getText();
        char[] password = passtext.getPassword();
        if(d.compare(username, password)){
            JOptionPane.showMessageDialog(null, "登陆成功！");
            dispose();
            new SystemUI();
        }
    }
    //重置按钮触发后的事件处理函数
    public void chongzhi() {
        nametext.setText("");
        passtext.setText("");
    }
}
class Jdbc {
    Connection con = null;
    Statement statement = null;
    ResultSet res = null;
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url  = "jdbc:sqlserver://localhost:1433; DatabaseName=S";
    String name = "sa";
    String passwd = "TZYWWX821722";

    public Jdbc(){
        try{
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url,name,passwd);
            statement = con.createStatement();
        }catch(ClassNotFoundException e){
            System.out.println("对不起，找不到这个Driver");
            e.printStackTrace();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //对比用户名和密码是不匹配
    public boolean compare(String username,char[] password){
        String pwd = String.valueOf(password);
        boolean m = false;
        String sql = "select password from suser where username='"+username+"'";
        try{
            res = statement.executeQuery(sql);
            if(res.next()){
                String pa = res.getString(1);
                //System.out.println(pa+" " +pwd);
                if(pa.equals(pwd)){
                    m = true;
                }else {
                    JOptionPane.showMessageDialog(null, "密码错误！");
                }
            }else {
                JOptionPane.showMessageDialog(null, "用户名不存在！");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return m;
    }
}