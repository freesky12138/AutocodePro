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
public class OracleHelp {
    private Connection connection;
    private DataInfo dataInfo;


    String sql = "select utc.column_name as columnName,utc.data_type as dataType,utc.data_length as dataLength,utc.nullable as nullable,ucc.comments as comments" +
            "    from user_tab_columns utc" +
            "    left join user_col_comments ucc on utc.column_name=ucc.column_name and ucc.table_name=utc.table_name" +
            "    where utc.table_name = '%s'";


    public OracleHelp(Connection connection, DataInfo dataInfo) {
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
            tableInfo.setColumnName(rs.getString("columnName"));
            tableInfo.setDataType(rs.getString("dataType"));
            tableInfo.setDataLength(rs.getInt("dataLength"));
            tableInfo.setNullAble(rs.getString("nullable"));
            tableInfo.setComments(rs.getString("comments"));
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

    public String getJson() throws SQLException {
        ArrayList<TableInfo> tableInfos = getTableInfo();

        return TableInfoAdapter.getJson(tableInfos, dataInfo);
    }
}
