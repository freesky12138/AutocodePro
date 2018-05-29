/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/29 19:08
 */
public class StatusAdapter {
    private static String[] actionTypeStrs = {"Java class field", "Mybatis Set", "Mybatis Insert", "Mybatis Update", "Mybatis Delete"};

    private static String[] databaseTypeStrs = {"Oracle", "Mysql"};

    public static int getActionTypeStrs(String str) {
        if (str.equals(actionTypeStrs[0])) {
            return 0;
        } else if (str.equals(actionTypeStrs[1])) {
            return 1;
        } else if (str.equals(actionTypeStrs[2])) {
            return 2;
        } else if (str.equals(actionTypeStrs[3])) {
            return 3;
        } else if (str.equals(actionTypeStrs[4])) {
            return 4;
        }
        return 0;
    }

    public static String[] getActionTypeStrs() {
        return actionTypeStrs;
    }

    public static void setActionTypeStrs(String[] actionTypeStrs) {
        StatusAdapter.actionTypeStrs = actionTypeStrs;
    }

    public static String[] getDatabaseTypeStrs() {
        return databaseTypeStrs;
    }

    public static void setDatabaseTypeStrs(String[] databaseTypeStrs) {
        StatusAdapter.databaseTypeStrs = databaseTypeStrs;
    }
}
