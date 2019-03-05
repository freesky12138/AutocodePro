package adapter;

import java.text.DecimalFormat;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/29 19:08
 */
public class StatusAdapter {
    private static String[] actionTypeStrs = {"Java class field", "Mybatis Select", "Mybatis Insert", "Mybatis Update", "Mybatis Delete", "JSON", "Mybatis InsertSelective", "ReturnBaseMap"};

    private static String[] databaseTypeStrs = {"Oracle", "Mysql"};

    private static String[] databaseColumType = {"CHAR", "CLOB", "DATE", "DECIMAL", "NUMBER", "FLOAT", "INTEGER", "LONG", "NUMERIC", "REAL", "SMALLINT", "TIME", "TIMESTAMP", "TINYINT", "VARCHAR2",
            "smallint", "tinyint", "int", "bigint", "double", "decimal", "char", "binary", "varchar", "date", "time", "datetime", "TIMESTAMP", "blob", "text"};
    private static String[] databaseColumTypeToMybatis = {"CHAR", "CLOB", "DATE", "DECIMAL", "NUMBER", "FLOAT", "INTEGER", "LONG", "NUMERIC", "REAL", "SMALLINT", "TIME", "TIMESTAMP", "TINYINT", "VARCHAR",
            "SMALLINT", "TINYINT", "INTEGER", "BIGINT", "DOUBLE", "DECIMAL", "VARCHAR", "BINARY", "VARCHAR", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "TIMESTAMP", "Blob", "TEXT"};
    private static String[] databaseColumTypeToJFile = {"String", "Clob", "Date", "BigDecimal", "BigDecimal", "Float", "Integer", "Long", "BigDecimal", "Float", "short", "Time", "String", "byte", "String",
            "Integer", "Integer", "Integer", "Long", "Double", "BigDecimal", "String", "byte[]", "String", "Date", "Time", "Date", "String", "Blob", "String"
    };


    public static String getDatabaseColumType(String str) {

        for (int i = 0; i < databaseColumType.length; i++) {
            if (str.contains(databaseColumType[i])) {
                return databaseColumTypeToJFile[i];
            }
        }
        return str;
    }

    public static String getDatabaseColumToMybatis(String str) {

        for (int i = 0; i < databaseColumType.length; i++) {
            if (str.contains(databaseColumType[i])) {
                return databaseColumTypeToMybatis[i];
            }
        }
        return str;
    }

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
        } else if (str.equals(actionTypeStrs[5])) {
            return 5;
        } else if (str.equals(actionTypeStrs[6])) {
            return 6;
        } else if (str.equals(actionTypeStrs[7])) {
            return 7;
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

    public static String[] getDatabaseColumType() {
        return databaseColumType;
    }

    public static void setDatabaseColumType(String[] databaseColumType) {
        StatusAdapter.databaseColumType = databaseColumType;
    }

    public static String[] getDatabaseColumTypeToJFile() {
        return databaseColumTypeToJFile;
    }

    public static void setDatabaseColumTypeToJFile(String[] databaseColumTypeToJFile) {
        StatusAdapter.databaseColumTypeToJFile = databaseColumTypeToJFile;
    }
}
