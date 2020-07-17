import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;

public class SystemUI extends JFrame{

    private static final long serialVersionUID = 4197017698513729527L;

    Teacher t = new Teacher();
    Course c = new Course();
    Student st = new Student();
    Score sc = new Score();
    SelectCourse se = new SelectCourse();
    static JTabbedPane tabbedPane;
    static JToolBar toolBar_1;
    static JToolBar toolBar_2;
    static JToolBar toolBar_3;
    static JToolBar toolBar_4;
    static JToolBar toolBar_5;
    static JButton AddTeacher;
    static JButton UpdateTeacher;
    static JButton DeleteTeacher;
    static JButton SearchTeacher;
    static JButton RefreshTeacher;
    static JButton AddStudent;
    static JButton UpdateStudent;
    static JButton DeleteStudent;
    static JButton SearchStudent;
    static JButton RefreshStudent;
    static JButton AddCourse;
    static JButton UpdateCourse;
    static JButton DeleteCourse;
    static JButton SearchCourse;
    static JButton RefreshCourse;
    static JButton AddSelCou;
    static JButton DeleteSelCou;
    static JButton SearchSelCou;
    static JButton RefreshSelCou;
    static JButton AddScore;
    static JButton UpdateScore;
    static JButton DeleteScore;
    static JButton SearchScore;
    static JButton RefreshScore;
    static JButton Statistical;
    static JButton TotalRank;
    static JScrollPane scrollPane1;
    static JScrollPane scrollPane2;
    static JScrollPane scrollPane3;
    static JScrollPane scrollPane4;
    static JScrollPane scrollPane5;
    public SystemUI()
    {

        this.setSize(565, 500);
        this.setLocation(550, 0);
        this.setVisible(true);
        this.setTitle("欢迎来到A教务系统");
        getContentPane().setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        JPanel TeacherInfo = new JPanel();
        JPanel StudentInfo = new JPanel();
        JPanel CourseInfo = new JPanel();
        JPanel ScoreInfo = new JPanel();
        JPanel SelectCourseInfo = new JPanel();
        tabbedPane.add(TeacherInfo,"教师信息");
        TeacherInfo.setLayout(new BorderLayout(0, 0));
        toolBar_1 = new JToolBar();
        TeacherInfo.add(toolBar_1, BorderLayout.NORTH);

        AddTeacher = new JButton("添加教师信息");
        AddTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.addTeacher();
            }
        });
        toolBar_1.add(AddTeacher);

        UpdateTeacher = new JButton("修改教师信息");
        UpdateTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.updateTeacher();
            }
        });
        toolBar_1.add(UpdateTeacher);

        DeleteTeacher = new JButton("删除教师信息");
        DeleteTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.deleteTeacher();
            }
        });
        toolBar_1.add(DeleteTeacher);

        SearchTeacher = new JButton("搜索教师信息");
        SearchTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.searchTeacher();
            }
        });
        toolBar_1.add(SearchTeacher);

        RefreshTeacher = new JButton("刷新");
        RefreshTeacher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                t.refreshTeacher();
            }
        });
        toolBar_1.add(RefreshTeacher);

        scrollPane1 = new JScrollPane();
        TeacherInfo.add(scrollPane1, BorderLayout.CENTER);
        scrollPane1.setViewportView(Teacher.table1);

        tabbedPane.add(StudentInfo,"学生信息");
        StudentInfo.setLayout(new BorderLayout(0, 0));
        toolBar_2 = new JToolBar();
        StudentInfo.add(toolBar_2, BorderLayout.NORTH);

        AddStudent = new JButton("添加学生信息");
        AddStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                st.addStudent();
            }
        });
        toolBar_2.add(AddStudent);

        UpdateStudent = new JButton("修改学生信息");
        UpdateStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                st.updateStudent();
            }
        });
        toolBar_2.add(UpdateStudent);

        DeleteStudent = new JButton("删除学生信息");
        DeleteStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                st.deleteStudent();
            }
        });
        toolBar_2.add(DeleteStudent);

        SearchStudent = new JButton("搜索学生信息");
        SearchStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                st.searchStudent();
            }
        });
        toolBar_2.add(SearchStudent);

        RefreshStudent = new JButton("刷新");
        RefreshStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                st.refreshStudent();
            }
        });
        toolBar_2.add(RefreshStudent);

        scrollPane2 = new JScrollPane();
        StudentInfo.add(scrollPane2, BorderLayout.CENTER);

        scrollPane2.setViewportView(Student.table2);

        tabbedPane.add(CourseInfo,"课程信息");
        CourseInfo.setLayout(new BorderLayout(0, 0));
        toolBar_3 = new JToolBar();
        CourseInfo.add(toolBar_3, BorderLayout.NORTH);

        AddCourse = new JButton("添加课程信息");
        AddCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.addCourse();
            }
        });
        toolBar_3.add(AddCourse);

        UpdateCourse = new JButton("修改课程信息");
        UpdateCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.updateCourse();
            }
        });
        toolBar_3.add(UpdateCourse);

        DeleteCourse = new JButton("删除课程信息");
        DeleteCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.deleteCourse();
            }
        });
        toolBar_3.add(DeleteCourse);

        SearchCourse = new JButton("搜索课程信息");
        SearchCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.searchCourse();
            }
        });
        toolBar_3.add(SearchCourse);

        RefreshCourse = new JButton("刷新");
        RefreshCourse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.refreshCourse();
            }
        });
        toolBar_3.add(RefreshCourse);

        scrollPane3 = new JScrollPane();
        CourseInfo.add(scrollPane3, BorderLayout.CENTER);

        scrollPane3.setViewportView(Course.table3);


        tabbedPane.add(SelectCourseInfo,"选课管理");
        SelectCourseInfo.setLayout(new BorderLayout(0, 0));
        toolBar_5 = new JToolBar();
        SelectCourseInfo.add(toolBar_5, BorderLayout.NORTH);

        AddSelCou = new JButton("添加选课信息");
        AddSelCou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                se.addSelCou();
            }
        });
        toolBar_5.add(AddSelCou);



        DeleteSelCou = new JButton("删除选课信息");
        DeleteSelCou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                se.deleteSelCou();
            }
        });
        toolBar_5.add(DeleteSelCou);

        SearchSelCou = new JButton("搜索选课信息");
        SearchSelCou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                se.searchSelCou();
            }
        });
        toolBar_5.add(SearchSelCou);

        RefreshSelCou = new JButton("刷新");
        RefreshSelCou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                se.refreshSelCou();
            }
        });
        toolBar_5.add(RefreshSelCou);

        scrollPane5 = new JScrollPane();
        SelectCourseInfo.add(scrollPane5, BorderLayout.CENTER);

        scrollPane5.setViewportView(SelectCourse.table5);





        tabbedPane.add(ScoreInfo,"成绩管理");
        ScoreInfo.setLayout(new BorderLayout(0, 0));
        toolBar_4 = new JToolBar();
        ScoreInfo.add(toolBar_4, BorderLayout.NORTH);

        AddScore = new JButton("添加成绩信息");
        AddScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.addScore();
            }
        });
        toolBar_4.add(AddScore);

        UpdateScore = new JButton("修改成绩信息");
        UpdateScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.updateScore();
            }
        });
        toolBar_4.add(UpdateScore);

        DeleteScore = new JButton("删除成绩信息");
        DeleteScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.deleteScore();
            }
        });
        toolBar_4.add(DeleteScore);

        SearchScore = new JButton("搜索成绩信息");
        SearchScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.searchScore();
            }
        });
        toolBar_4.add(SearchScore);

        Statistical = new JButton("统计分析");
        Statistical.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                sc.statistical();
            }
        });
        toolBar_4.add(Statistical);

        TotalRank = new JButton("总分排名");
        TotalRank.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.totalrank();
            }
        });
        toolBar_4.add(TotalRank);

        RefreshScore = new JButton("刷新");
        RefreshScore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sc.refreshScore();
            }
        });
        toolBar_4.add(RefreshScore);

        scrollPane4 = new JScrollPane();
        ScoreInfo.add(scrollPane4, BorderLayout.CENTER);

        scrollPane4.setViewportView(Score.table4);

    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SystemUI frame = new SystemUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void clickable() {
        tabbedPane.setEnabled(true);
        AddTeacher.setEnabled(true);
        DeleteTeacher.setEnabled(true);
        RefreshTeacher.setEnabled(true);
        SearchTeacher.setEnabled(true);
        UpdateTeacher.setEnabled(true);
        AddStudent.setEnabled(true);
        DeleteStudent.setEnabled(true);
        RefreshStudent.setEnabled(true);
        SearchStudent.setEnabled(true);
        UpdateStudent.setEnabled(true);
        AddCourse.setEnabled(true);
        DeleteCourse.setEnabled(true);
        RefreshCourse.setEnabled(true);
        SearchCourse.setEnabled(true);
        UpdateCourse.setEnabled(true);
        AddScore.setEnabled(true);
        DeleteScore.setEnabled(true);
        RefreshScore.setEnabled(true);
        SearchScore.setEnabled(true);
        UpdateScore.setEnabled(true);
        Statistical.setEnabled(true);
        AddSelCou.setEnabled(true);
        DeleteSelCou.setEnabled(true);
        SearchSelCou.setEnabled(true);
        RefreshSelCou.setEnabled(true);
        TotalRank.setEnabled(true);
    }
    public static void unclickable() {
        tabbedPane.setEnabled(false);
        AddTeacher.setEnabled(false);
        DeleteTeacher.setEnabled(false);
        RefreshTeacher.setEnabled(false);
        SearchTeacher.setEnabled(false);
        UpdateTeacher.setEnabled(false);
        AddStudent.setEnabled(false);
        DeleteStudent.setEnabled(false);
        RefreshStudent.setEnabled(false);
        SearchStudent.setEnabled(false);
        UpdateStudent.setEnabled(false);
        AddCourse.setEnabled(false);
        DeleteCourse.setEnabled(false);
        RefreshCourse.setEnabled(false);
        SearchCourse.setEnabled(false);
        UpdateCourse.setEnabled(false);
        AddScore.setEnabled(false);
        DeleteScore.setEnabled(false);
        RefreshScore.setEnabled(false);
        SearchScore.setEnabled(false);
        UpdateScore.setEnabled(false);
        Statistical.setEnabled(false);
        AddSelCou.setEnabled(false);
        DeleteSelCou.setEnabled(false);
        SearchSelCou.setEnabled(false);
        RefreshSelCou.setEnabled(false);
        TotalRank.setEnabled(false);
    }
}