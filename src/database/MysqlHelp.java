package database;

import adapter.TableInfoAdapter;
import entity.DataConfig;
import entity.TableInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/30 10:11
 */
public class MysqlHelp {
    private Connection connection;
    private DataConfig dataInfo;

    String sql = "SELECT " +
            "COLUMN_NAME, IS_NULLABLE,COLUMN_COMMENT,DATA_TYPE, CHARACTER_MAXIMUM_LENGTH, NUMERIC_PRECISION  " +
            "FROM INFORMATION_SCHEMA.COLUMNS " +
            "WHERE TABLE_NAME='%s'";

    public MysqlHelp(Connection connection, DataConfig dataInfo) {
        this.connection = connection;
        this.dataInfo = dataInfo;
    }


    public String getJavaFile() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getJavaFile(tableInfos, dataInfo);
    }

    private ArrayList<TableInfo> getTableInfo() throws SQLException {
        Statement stmt = connection.createStatement();
        sql = sql.replaceAll("%s", dataInfo.getTableName());
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<TableInfo> tableInfos = new ArrayList<>();
        while (rs.next()) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setColumnName(rs.getString("COLUMN_NAME"));
            tableInfo.setDataType(rs.getString("DATA_TYPE"));
            tableInfo.setDataLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH") == 0 ? rs.getInt("NUMERIC_PRECISION") : rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
            tableInfo.setNullAble("YES".equals(rs.getString("IS_NULLABLE"))?"Y":"N");
            tableInfo.setComments(rs.getString("COLUMN_COMMENT"));
            tableInfos.add(tableInfo);
        }

        return tableInfos;
    }

    public String getSelectSql() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getSelectSql(tableInfos, dataInfo);
    }

    public String getUpdateSql() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getUpdateSql(tableInfos, dataInfo);
    }


    public String getDeleteSql() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getDeleteSql(tableInfos, dataInfo);
    }

    public String getInsertSql() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getInsertSql(tableInfos, dataInfo);
    }

    public String getInsertSelectiveSql() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getInsertSelectiveSql(tableInfos, dataInfo);
    }

    public String getResultBaseMap() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getResultBaseMap(tableInfos, dataInfo);
    }


    public String getJson() throws SQLException{
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getJson(tableInfos, dataInfo);
    }
}
