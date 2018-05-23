import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/23 16:05
 */
public class MainJFrame implements ActionListener {
    private JFrame jf = new JFrame("测试窗口");          // 创建窗口
    private JButton btn;
    private JTextField sqlTypeEdit;
    private JTextPane sqlTypeText;
    private JTextPane sqlTypeText2;
    public MainJFrame() {
        initJFrame();


        // 2. 创建中间容器（面板容器）
        JPanel panel = new JPanel();                // 创建面板容器，使用默认的布局管理器

        // 3. 创建一个基本组件（按钮），并添加到 面板容器 中
        btn = new JButton("测试按钮");
        sqlTypeEdit = new JTextField("数据库");
        sqlTypeText=new JTextPane();
        sqlTypeText.setText("数据库");
        sqlTypeText.setEditable(false);

        panel.add(btn);
        panel.add(sqlTypeEdit);
        panel.add(sqlTypeText);
        btn.addActionListener(this);
        sqlTypeText2=new JTextPane();
        sqlTypeText2.setText("123");
        JPanel panel2 = new JPanel();
        panel2.add(sqlTypeText2);

        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        jf.add(panel);
        jf.add(panel2);
        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);
    }

    /**
     * 初始化JFrame
     */
    private void initJFrame() {
        jf.setSize(250, 250);                       // 设置窗口大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）

        jf.setLayout(new FlowLayout());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            System.out.printf("asdg");
        }
    }
}
