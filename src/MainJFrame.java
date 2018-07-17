import adapter.StatusAdapter;
import database.DatabaseFactory;
import database.MysqlHelp;
import database.OracleHelp;
import entity.DataConfig;
import file.ConfigFileDao;
import utils.ToolUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/23 16:05
 */
public class MainJFrame implements ActionListener, ItemListener, MouseListener {
    private JFrame jf = new JFrame("haha point(https://github.com/freesky12138/AutocodePro/)");          // 创建窗口
    private JMenuBar jMenuBar;
    private JMenu config;
    private JMenuItem loadConfig;
    private JMenuItem savaConfig;
    private JMenuItem initConfig;
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
    private JPasswordField pwdEdit;
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
    private JTextArea showText;
    private JScrollPane js;


    //读取保存配置
    private ConfigFileDao configFileDao = new ConfigFileDao();

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
        js.setPreferredSize(new Dimension(400, 700));
        jf.add(js);

        jf.setJMenuBar(jMenuBar);
    }

    private void initView() {
        jMenuBar = new JMenuBar();
        config = new JMenu("配置");
        loadConfig = new JMenuItem("加载配置");
        savaConfig = new JMenuItem("保存配置");
        initConfig = new JMenuItem("还原配置");
        initConfig.addMouseListener(this);
        savaConfig.addMouseListener(this);
        loadConfig.addMouseListener(this);
        jMenuBar.add(config);
        config.add(loadConfig);
        config.add(savaConfig);
        config.add(initConfig);


        panelLine1 = new JPanel();
        databaseTypeText = new JLabel("数据库");

        databaseTypeComboBox = new JComboBox(StatusAdapter.getDatabaseTypeStrs());
        databaseTypeComboBox.setSelectedIndex(1);
        ipText = new JLabel("IP地址");
        ipEdit = new JTextField("118.24.120.211");
        ipEdit.setColumns(12);
        portText = new JLabel("端口");
        portEdit = new JTextField("3306");
        portEdit.setColumns(6);
        panelLine1.add(databaseTypeText);
        panelLine1.add(databaseTypeComboBox);
        panelLine1.add(ipText);
        panelLine1.add(ipEdit);
        panelLine1.add(portText);
        panelLine1.add(portEdit);


        panelLine2 = new JPanel();
        userNameText = new JLabel("用户名");
        userNameEdit = new JTextField("root");
        userNameEdit.setColumns(12);
        pwdText = new JLabel("密码");
        pwdEdit = new JPasswordField("W9EhiSonxh2E");
        pwdEdit.setColumns(12);
        databaseInstanceText = new JLabel("数据库实例名");
        databaseInstanceEdit = new JTextField("db_tutor");
        databaseInstanceEdit.setColumns(6);
        panelLine2.add(userNameText);
        panelLine2.add(userNameEdit);
        panelLine2.add(pwdText);
        panelLine2.add(pwdEdit);
        panelLine2.add(databaseInstanceText);
        panelLine2.add(databaseInstanceEdit);


        panelLine3 = new JPanel();
        tableNameText = new JLabel("表名");
        tableNameEdit = new JTextField("m_user");
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
        jFieldWordType.setSelected(uppercaseWordType.getModel(), true);


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

        showText = new JTextArea();
        showText.setMargin(new Insets(10, 10, 10, 10));
        showText.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN, 14));
        js = new JScrollPane(showText);
        js.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        js.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        DataConfig dataInfo = null;
        try {
            dataInfo = configFileDao.loadDataInfo();
            setViewConfig(dataInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        if (e.getSource() == creatBtn) {
            if (ToolUtils.strIsEmpty(ipEdit.getText())) {
                ErrorDialog.showDialog(jf, "警告", "IP不能为空");
            }

            if (ToolUtils.strIsEmpty(portEdit.getText())) {
                ErrorDialog.showDialog(jf, "警告", "端口不能为空");
            }

            if (ToolUtils.strIsEmpty(userNameEdit.getText())) {
                ErrorDialog.showDialog(jf, "警告", "用户名不能为空");
            }

            if (ToolUtils.strIsEmpty(pwdEdit.getText())) {
                ErrorDialog.showDialog(jf, "警告", "密码不能为空");
            }

            if (ToolUtils.strIsEmpty(databaseInstanceEdit.getText())) {
                ErrorDialog.showDialog(jf, "警告", "实例不能为空");
            }

            if (ToolUtils.strIsEmpty(tableNameEdit.getText())) {
                ErrorDialog.showDialog(jf, "警告", "表名不能为空");
            }

            DataConfig dataInfo = new DataConfig();
            dataInfo.setDatabaseType(databaseTypeComboBox.getSelectedIndex());
            dataInfo.setIp(ipEdit.getText());
            dataInfo.setPort(portEdit.getText());
            dataInfo.setUserName(userNameEdit.getText());
            dataInfo.setPwd(pwdEdit.getText());
            dataInfo.setDatabaseInstance(databaseInstanceEdit.getText());
            dataInfo.setTableName(tableNameEdit.getText());
            dataInfo.setActionType(actionTypeComboBox.getSelectedIndex());

            if (humpWordType.isSelected()) {
                dataInfo.setjFieldWordType(0);
            } else if (lowercaseWordType.isSelected()) {
                dataInfo.setjFieldWordType(1);
            } else {
                dataInfo.setjFieldWordType(2);
            }
            if (actionTypeComboBox.getSelectedIndex() == 0) {
                dataInfo.setDateAnnotation(dateAnnotation.isSelected());
                dataInfo.setDateAnnotationType(dateAnnotationType.getText());
                dataInfo.setDateIsString(dateIsString.isSelected());
                dataInfo.setNullAnnotation(isNullAnnotation.isSelected());
                dataInfo.setLengthAnnotation(lengthAnnotation.isSelected());
                dataInfo.setRemarksAnnotation(isRemarksAnnotation.isSelected());
            } else {
                dataInfo.setRemarksAnnotation(isRemarksAnnotation.isSelected());
                dataInfo.setPrefixEdit(prefixEdit.getText());
            }
            try {
                if (dataInfo.getDatabaseType() == 0) {
                    if (dataInfo.getActionType() == 0) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getJavaFile());
                    } else if (dataInfo.getActionType() == 1) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getSelectSql());
                    } else if (dataInfo.getActionType() == 2) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getInsertSql());
                    } else if (dataInfo.getActionType() == 3) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getUpdateSql());
                    } else if (dataInfo.getActionType() == 4) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getDeleteSql());
                    } else if (dataInfo.getActionType() == 5) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getJson());
                    } else if (dataInfo.getActionType() == 6) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getInsertSelectiveSql());
                    } else if (dataInfo.getActionType() == 7) {
                        showText.setText(new OracleHelp(DatabaseFactory.getOracleConnection(dataInfo), dataInfo).getResultBaseMap());
                    }
                } else {
                    if (dataInfo.getActionType() == 0) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getJavaFile());
                    } else if (dataInfo.getActionType() == 1) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getSelectSql());
                    } else if (dataInfo.getActionType() == 2) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getInsertSql());
                    } else if (dataInfo.getActionType() == 3) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getUpdateSql());
                    } else if (dataInfo.getActionType() == 4) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getDeleteSql());
                    } else if (dataInfo.getActionType() == 5) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getJson());
                    } else if (dataInfo.getActionType() == 6) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getInsertSelectiveSql());
                    } else if (dataInfo.getActionType() == 7) {
                        showText.setText(new MysqlHelp(DatabaseFactory.getMySQLConnection(dataInfo), dataInfo).getResultBaseMap());
                    }
                }
            } catch (SQLException e1) {
                ErrorDialog.showDialog(jf, "警告", "数据库连接信息错误");
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
                if (res == 5) {
                    panelLine6.remove(prefixEdit);
                    panelLine6.remove(prefixText);
                } else {
                    panelLine6.add(prefixEdit);
                    panelLine6.add(prefixText);
                }
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

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    private void setViewConfig(DataConfig dataInfo) {
        if (dataInfo != null) {
            databaseTypeComboBox.setSelectedIndex(dataInfo.getDatabaseType());
            ipEdit.setText(dataInfo.getIp());
            portEdit.setText(dataInfo.getPort());
            userNameEdit.setText(dataInfo.getUserName());
            pwdEdit.setText(dataInfo.getPwd());
            databaseInstanceEdit.setText(dataInfo.getDatabaseInstance());
            tableNameEdit.setText(dataInfo.getTableName());
        }
    }

    private DataConfig getViewConifg() {
        DataConfig dataInfo = new DataConfig();
        dataInfo.setDatabaseType(databaseTypeComboBox.getSelectedIndex());
        dataInfo.setIp(ipEdit.getText());
        dataInfo.setPort(portEdit.getText());
        dataInfo.setUserName(userNameEdit.getText());
        dataInfo.setPwd(pwdEdit.getText());
        dataInfo.setDatabaseInstance(databaseInstanceEdit.getText());
        dataInfo.setTableName(tableNameEdit.getText());
        return dataInfo;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        try {
            if (e.getSource() == loadConfig) {
                DataConfig dataInfo = configFileDao.loadDataInfo();
                setViewConfig(dataInfo);
            } else if (e.getSource() == savaConfig) {
                DataConfig dataInfo = getViewConifg();
                configFileDao.saveDataInfo(dataInfo);
            } else if (e.getSource() == initConfig) {
                DataConfig dataInfo = new DataConfig();
                dataInfo.setDatabaseType(1);
                dataInfo.setIp("118.24.120.211");
                dataInfo.setPort("3306");
                dataInfo.setUserName("root");
                dataInfo.setPwd("W9EhiSonxh2E");
                dataInfo.setDatabaseInstance("db_tutor");
                dataInfo.setTableName("m_user");
                setViewConfig(dataInfo);
                configFileDao.saveDataInfo(dataInfo);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            ErrorDialog.showDialog(jf, "警告", "读写配置文件出错");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
