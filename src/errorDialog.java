import javax.swing.*;
import java.awt.*;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/29 19:49
 */
public class errorDialog {
    public static void showDialog(Component component,String title,String msg) {
        JOptionPane.showMessageDialog(component, msg, title, JOptionPane.ERROR_MESSAGE);
    }
}
