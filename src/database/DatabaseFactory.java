package database;

import entity.DataConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/30 9:39
 */
public class DatabaseFactory {
    private static Connection oracleConnection;
    private static Connection mySQLConnection;
    private static String mysqlURI = "";
    private static String oracleURI = "";
    private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";


    public static Connection getMySQLConnection(DataConfig dataInfo) throws SQLException {
        if (mySQLConnection == null || getMysqlURI(dataInfo) != mysqlURI) {
            try {
                Class.forName(MYSQL_DRIVER);// 动态加载mysql驱动
                mysqlURI = getMysqlURI(dataInfo);
                mySQLConnection = DriverManager.getConnection(mysqlURI);
                System.out.println("成功加载MySQL驱动程序");
            } catch (SQLException e) {
                throw e;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return mySQLConnection;
    }

    public static Connection getOracleConnection(DataConfig dataInfo) throws SQLException {
        if (oracleConnection == null || getOracleUrl(dataInfo) != oracleURI) {
            try {
                Class.forName(ORACLE_DRIVER);// 动态加载mysql驱动
                oracleURI = getOracleUrl(dataInfo);
                oracleConnection = DriverManager.getConnection(oracleURI, dataInfo.getUserName(), dataInfo.getPwd());
                System.out.println("成功加载Oracle驱动程序");
            } catch (SQLException e) {
               throw e;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return oracleConnection;
    }


    private static String getOracleUrl(DataConfig dataInfo) {
        return "jdbc:oracle:thin:@" + dataInfo.getIp() + ":" + dataInfo.getPort() + ":" + dataInfo.getDatabaseInstance();

    }

    private static String getMysqlURI(DataConfig dataInfo) {
        return "jdbc:mysql://" + dataInfo.getIp() + ":" + dataInfo.getPort() + "/" + dataInfo.getDatabaseInstance() + "?user=" + dataInfo.getUserName() + "&password=" + dataInfo.getPwd() + "&useUnicode=true&characterEncoding=UTF-8";
    }

}
