import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/23 16:05
 */
public class MainJFrame implements ActionListener, ItemListener {
    private JFrame jf = new JFrame("测试窗口");          // 创建窗口
    private JPanel panelLine1;//创建中间容器（面板容器）
    private JLabel databaseTypeText;
    private JComboBox databaseTypeComboBox;
    private JLabel ipText;
    private JTextField ipEdit;
    private JLabel portText;
    private JTextField portEdit;

    private JPanel panelLine2;
    private JLabel userNameText;
    private JTextField userNameEdit;
    private JLabel pwdText;
    private JTextField pwdEdit;
    private JLabel databaseInstanceText;
    private JTextField databaseInstanceEdit;

    private JPanel panelLine3;
    private JLabel tableNameText;
    private JTextField tableNameEdit;
    private JLabel actionTypeText;
    private JComboBox actionTypeComboBox;


    /**
     * 类型 java field
     */
    private JPanel panelLine4;
    private JPanel panelLine5;
    private ButtonGroup jFieldWordType;//
    private JRadioButton humpWordType;//驼峰，小写，大写
    private JRadioButton lowercaseWordType;//驼峰，小写，大写
    private JRadioButton uppercaseWordType;
    private JCheckBox isNullAnnotation;//为空注解
    private JCheckBox lengthAnnotation;//长度注解
    private JCheckBox dateAnnotation;//日期注解
    private JTextField dateAnnotationType;//注解类型yyyy-MM-dd HH:mm:ss
    private JCheckBox dateIsString;//日期是否是String
    private JCheckBox isRemarksAnnotation;//是否要备注

    /**
     * 查询结果，更新，删除,插入
     */
    private JPanel panelLine6;
    //驼峰，小写，大写
    //是否要备注
    private JLabel prefixText;//前缀
    private JTextField prefixEdit;//前缀


    private JButton creatBtn;
    private JTextField showText;

    public MainJFrame() {
        initJFrame();


        initView();

        // 4. 把 面板容器 作为窗口的内容面板 设置到 窗口
        addPaint();
        // 5. 显示窗口，前面创建的信息都在内存中，通过 jf.setVisible(true) 把内存中的窗口显示在屏幕上。
        jf.setVisible(true);
    }

    private void addPaint() {

        //panelLine6.setVisible(false);
        BoxLayout gridLayout = new BoxLayout(jf.getContentPane(), BoxLayout.Y_AXIS);
        jf.getContentPane().setLayout(gridLayout);
        panelLine1.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
        jf.add(panelLine1);
        panelLine2.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
        jf.add(panelLine2);
        panelLine3.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
        jf.add(panelLine3);
        panelLine4.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
        jf.add(panelLine4);
        panelLine5.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
        jf.add(panelLine5);
        //panelLine6.setLayout(new FlowLayout(FlowLayout.LEFT,12,5));
        //jf.add(panelLine6);
        jf.add(showText);

    }

    private void initView() {
        panelLine1 = new JPanel();
        databaseTypeText = new JLabel("数据库");

        databaseTypeComboBox = new JComboBox(StatusAdapter.getDatabaseTypeStrs());
        ipText = new JLabel("IP地址");
        ipEdit = new JTextField("127.0.0.1");
        ipEdit.setColumns(12);
        portText = new JLabel("端口");
        portEdit = new JTextField("1521");
        portEdit.setColumns(6);
        panelLine1.add(databaseTypeText);
        panelLine1.add(databaseTypeComboBox);
        panelLine1.add(ipText);
        panelLine1.add(ipEdit);
        panelLine1.add(portText);
        panelLine1.add(portEdit);


        panelLine2 = new JPanel();
        userNameText = new JLabel("用户名");
        userNameEdit = new JTextField("test");
        userNameEdit.setColumns(12);
        pwdText = new JLabel("密码");
        pwdEdit = new JTextField("123456");
        pwdEdit.setColumns(12);
        databaseInstanceText = new JLabel("数据库实例名");
        databaseInstanceEdit = new JTextField("1521");
        databaseInstanceEdit.setColumns(6);
        panelLine2.add(userNameText);
        panelLine2.add(userNameEdit);
        panelLine2.add(pwdText);
        panelLine2.add(pwdEdit);
        panelLine2.add(databaseInstanceText);
        panelLine2.add(databaseInstanceEdit);


        panelLine3 = new JPanel();
        tableNameText = new JLabel("表名");
        tableNameEdit = new JTextField("123");
        tableNameEdit.setColumns(24);
        actionTypeText = new JLabel("生成类型");

        actionTypeComboBox = new JComboBox(StatusAdapter.getActionTypeStrs());
        actionTypeComboBox.addItemListener(this);
        panelLine3.add(tableNameText);
        panelLine3.add(tableNameEdit);
        panelLine3.add(actionTypeText);
        panelLine3.add(actionTypeComboBox);

        creatBtn = new JButton("生成代码");
        creatBtn.addActionListener(this);
        panelLine3.add(creatBtn);

        panelLine4 = new JPanel();
        panelLine5 = new JPanel();

        //大写，小写，驼峰的ButtonGroup
        jFieldWordType = new ButtonGroup();
        humpWordType = new JRadioButton("驼峰");
        lowercaseWordType = new JRadioButton("小写");
        uppercaseWordType = new JRadioButton("大写");
        jFieldWordType.add(humpWordType);
        jFieldWordType.add(lowercaseWordType);
        jFieldWordType.add(uppercaseWordType);


        isNullAnnotation = new JCheckBox("非空注解");
        lengthAnnotation = new JCheckBox("长度注解");
        dateAnnotation = new JCheckBox("日期注解");
        dateAnnotationType = new JTextField("yyyy-MM-dd HH:mm:ss");
        dateAnnotationType.setColumns(12);
        dateIsString = new JCheckBox("日期使用String");
        isRemarksAnnotation = new JCheckBox("是否需要备注");
        panelLine4.add(dateAnnotation);
        panelLine4.add(dateAnnotationType);
        panelLine4.add(dateIsString);
        panelLine4.add(humpWordType);
        panelLine4.add(lowercaseWordType);
        panelLine4.add(uppercaseWordType);
        panelLine5.add(isNullAnnotation);
        panelLine5.add(lengthAnnotation);
        panelLine5.add(isRemarksAnnotation);


        panelLine6 = new JPanel();
        prefixText = new JLabel("添加前缀");
        prefixEdit = new JTextField("");
        prefixEdit.setColumns(12);
        panelLine6.add(prefixText);
        panelLine6.add(prefixEdit);
        /*panelLine6.add(humpWordType);
        panelLine6.add(lowercaseWordType);
        panelLine6.add(uppercaseWordType);
        panelLine6.add(isRemarksAnnotation);*/

        showText = new JTextField();
    }

    /**
     * 初始化JFrame
     */
    private void initJFrame() {
        jf.setSize(800, 700);                       // 设置窗口大小
        jf.setLocationRelativeTo(null);             // 把窗口位置设置到屏幕中心
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // 当点击窗口的关闭按钮时退出程序（没有这一句，程序不会退出）

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        errorDialog.showDialog(jf, "sadf", "asdf");
        if (e.getSource() == creatBtn) {
            if (Tool.strIsEmpty(ipEdit.getText())) {
                errorDialog.showDialog(jf, "警告", "IP不能为空");
            }

            if (Tool.strIsEmpty(portEdit.getText())) {
                errorDialog.showDialog(jf, "警告", "端口不能为空");
            }

            if (Tool.strIsEmpty(userNameEdit.getText())) {
                errorDialog.showDialog(jf, "警告", "用户名不能为空");
            }

            if (Tool.strIsEmpty(pwdEdit.getText())) {
                errorDialog.showDialog(jf, "警告", "密码不能为空");
            }

            if (Tool.strIsEmpty(databaseInstanceEdit.getText())) {
                errorDialog.showDialog(jf, "警告", "实例不能为空");
            }

            if (Tool.strIsEmpty(tableNameEdit.getText())) {
                errorDialog.showDialog(jf, "警告", "表名不能为空");
            }

            DataInfo dataInfo = new DataInfo();
            dataInfo.setDatabaseType(databaseTypeComboBox.getSelectedIndex());
            dataInfo.setIp(ipEdit.getText());
            dataInfo.setPort(portEdit.getText());
            dataInfo.setUserName(userNameEdit.getText());
            dataInfo.setPwd(pwdEdit.getText());
            dataInfo.setDatabaseInstance(databaseInstanceEdit.getText());
            dataInfo.setTableName(tableNameEdit.getText());
            dataInfo.setActionType(actionTypeComboBox.getSelectedIndex());

            while (jFieldWordType.getElements().hasMoreElements()) {
                AbstractButton abstractButton = jFieldWordType.getElements().nextElement();
                if (abstractButton == humpWordType) {
                    dataInfo.setjFieldWordType(0);
                } else if (abstractButton == lowercaseWordType) {
                    dataInfo.setjFieldWordType(1);
                } else {
                    dataInfo.setjFieldWordType(2);
                }
            }
            if (actionTypeComboBox.getSelectedIndex() == 0) {
                dataInfo.setDateAnnotation(dateAnnotation.isSelected());
                dataInfo.setDateAnnotationType(dateAnnotationType.getText());
                dataInfo.setDateIsString(dateIsString.isSelected());
                dataInfo.setNullAnnotation(isNullAnnotation.isSelected());
                dataInfo.setLengthAnnotation(isNullAnnotation.isSelected());
                dataInfo.setRemarksAnnotation(isRemarksAnnotation.isSelected());
            } else {
                dataInfo.setRemarksAnnotation(isRemarksAnnotation.isSelected());
                dataInfo.setPrefixEdit(prefixEdit.getText());
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == actionTypeComboBox) {
            int res = StatusAdapter.getActionTypeStrs(e.getItem().toString());
            if (res == 0) {
                jf.remove(panelLine6);
                panelLine4.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
                jf.add(panelLine4, 3);
                panelLine5.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
                panelLine4.add(humpWordType);
                panelLine4.add(lowercaseWordType);
                panelLine4.add(uppercaseWordType);
                panelLine5.add(isRemarksAnnotation);
                jf.add(panelLine5, 4);
                jf.repaint();
                jf.setVisible(true);


            } else {
                jf.remove(panelLine4);
                jf.remove(panelLine5);
                panelLine6.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
                panelLine6.add(humpWordType);
                panelLine6.add(lowercaseWordType);
                panelLine6.add(uppercaseWordType);
                panelLine6.add(isRemarksAnnotation);
                jf.add(panelLine6, 3);
                jf.repaint();
                jf.setVisible(true);
            }
        }
    }
}
