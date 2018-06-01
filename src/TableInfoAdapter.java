import java.util.ArrayList;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/30 13:37
 */
public class TableInfoAdapter {

    public static String getJavaFile(ArrayList<TableInfo> tableInfos, DataInfo dataInfo) {
        String res = "";

        for (TableInfo tableInfo : tableInfos) {
            String rowStr = "";
            //日期是否使用Str
            if (dataInfo.isDateIsString()) {
                StatusAdapter.getDatabaseColumTypeToJFile()[1] = "String";
                StatusAdapter.getDatabaseColumTypeToJFile()[4] = "String";
            } else {
                StatusAdapter.getDatabaseColumTypeToJFile()[1] = "Date";
                StatusAdapter.getDatabaseColumTypeToJFile()[4] = "Date";
            }

            //大写，小写，驼峰
            String columnName = jFieldWordType(tableInfo.getColumnName(), dataInfo);
            //非空
            if (dataInfo.isNullAnnotation() && "N".equals(tableInfo.getNullAble())) {
                rowStr += "@NotNull\n";
            }
            //长度
            if (dataInfo.isLengthAnnotation() && ("VARCHAR2".equals(tableInfo.getDataType()) || "varchar".equals(tableInfo.getDataType()))) {
                rowStr += "@Length(max = %d)\n".replaceAll("%d", tableInfo.getDataLength() + "");
            }
            //日期
            if (dataInfo.isDateAnnotation() && ("DATE".equals(tableInfo.getDataType()) || "datetime".equals(tableInfo.getDataType()))) {
                rowStr += "@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n".replaceAll("yyyy-MM-dd HH:mm:ss", dataInfo.getDateAnnotationType());
            }

            rowStr += "private " + StatusAdapter.getDatabaseColumType(tableInfo.getDataType()) + " " + columnName + ";";
            if (dataInfo.isRemarksAnnotation()) {
                rowStr += "//" + tableInfo.getComments() + " [" + tableInfo.getColumnName() + ", " + tableInfo.getDataType() + ", " + tableInfo.getDataLength() + "]";
            }
            res += rowStr + "\n";
        }
        res += "//共[%s]个字段".replaceAll("%s", tableInfos.size() + "");
        return res;
    }


    public static String getSelectSql(ArrayList<TableInfo> tableInfos, DataInfo dataInfo) {
        String res = "select\n";

        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            String row = "";
            if (!Tool.strIsEmpty(dataInfo.getPrefixEdit())) {
                row = dataInfo.getPrefixEdit() + ".";
            }
            String columnName = tableInfo.getColumnName();
            if (dataInfo.getjFieldWordType() == 0) {
                columnName = columnName.toLowerCase();
                columnName = tableInfo.getColumnName() + " as " + Tool.toHump(columnName);
            } else if (dataInfo.getjFieldWordType() == 1) {
                columnName = columnName.toLowerCase();
            } else {
                columnName = columnName.toUpperCase();
            }
            if (i != tableInfos.size() - 1) {
                columnName += ",";
            }
            row += columnName;

            if (dataInfo.isRemarksAnnotation()) {
                row += "/*" + tableInfo.getComments() + "*/";
            }

            res += row + "\n";
        }

        res += "from\n" + dataInfo.getTableName();
        if (!Tool.strIsEmpty(dataInfo.getPrefixEdit())) {
            res += " " + dataInfo.getPrefixEdit();
        }
        return res;
    }

    private static String jFieldWordType(String columnName, DataInfo dataInfo) {
        if (dataInfo.getjFieldWordType() == 0) {
            columnName = columnName.toLowerCase();
            columnName = Tool.toHump(columnName);
        } else if (dataInfo.getjFieldWordType() == 1) {
            columnName = columnName.toLowerCase();
        } else {
            columnName = columnName.toUpperCase();
        }
        return columnName;
    }

    public static String getUpdateSql(ArrayList<TableInfo> tableInfos, DataInfo dataInfo) {

        String code = "CODE";
        String value = "code";
        String type = "varchar";
        if (tableInfos.size() != 0) {
            code = tableInfos.get(0).getColumnName();
            value = jFieldWordType(code.toLowerCase(), dataInfo);
            type = tableInfos.get(0).getDataType();
        }

        String res = "UPDATE %s\n" +
                "<set>\n";
        res = String.format(res, dataInfo.getTableName());

        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            String row = "<if test=\"%s != null\">%s=#{%s,jdbcType=%s},</if>";
            String columnName = jFieldWordType(tableInfo.getColumnName(), dataInfo);

            if (!Tool.strIsEmpty(dataInfo.getPrefixEdit())) {
                columnName = dataInfo.getPrefixEdit() + "." + columnName;
            }
            row = String.format(row, columnName, tableInfo.getColumnName(), columnName, StatusAdapter.getDatabaseColumToMybatis(tableInfo.getDataType()));
            res += row + "\n";
        }

        res += String.format("</set>\n" +
                "<where>\n" +
                "\t<if test=\"%s != null \">%s=#{%s,jdbcType=%s}</if>\n" +
                "</where>", value, code, value, type);
        return res;
    }

    public static String getDeleteSql(ArrayList<TableInfo> tableInfos, DataInfo dataInfo) {
        String code = "CODE";
        String value = "code";
        if (tableInfos.size() != 0) {
            code = tableInfos.get(0).getColumnName();
            value = code.toLowerCase();
        }
        String res = "<delete id=\"delete\">\n" +
                "\tdelete from %s where %s = #{%s}\n" +
                "</delete>";

        if (!Tool.strIsEmpty(dataInfo.getPrefixEdit())) {
            value = dataInfo.getPrefixEdit() + "." + value;
        }
        res = String.format(res, dataInfo.getTableName(), code, value);
        return res;
    }

    public static String getInsertSql(ArrayList<TableInfo> tableInfos, DataInfo dataInfo) {
        String res = "insert into %s(\n";
        res = String.format(res, dataInfo.getTableName());
        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            String columnName = tableInfo.getColumnName();

            if (i != tableInfos.size() - 1) {
                columnName += ",";
            }

            if (dataInfo.isRemarksAnnotation()) {
                columnName += "/*" + tableInfo.getComments() + "*/";
            }
            res += columnName + "\n";
        }
        res += ")\nvalues(\n";
        res += "seq_" + dataInfo.getTableName().toLowerCase() + ".nextval,\n";

        for (int i = 1; i < tableInfos.size(); i++) {
            String cowStr = "# {";
            TableInfo tableInfo = tableInfos.get(i);
            String columnName = jFieldWordType(tableInfo.getColumnName(), dataInfo);
            if (!Tool.strIsEmpty(dataInfo.getPrefixEdit())) {
                cowStr += dataInfo.getPrefixEdit() + "." + columnName + ",jdbcType = " + StatusAdapter.getDatabaseColumToMybatis(tableInfo.getDataType());
            } else {
                cowStr += columnName + ",jdbcType = " + StatusAdapter.getDatabaseColumToMybatis(tableInfo.getDataType());
            }

            cowStr += "}";
            if (i != tableInfos.size() - 1) {
                cowStr += ",";
            }
            res += cowStr + "\n";
        }


        res += ")";
        return res;
    }

    public static String getJson(ArrayList<TableInfo> tableInfos, DataInfo dataInfo) {
        String res = "{\n";
        for (int i = 1; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            String columnName = jFieldWordType(tableInfo.getColumnName(), dataInfo);
            String row;
            if(dataInfo.isRemarksAnnotation()){
                row="\t\""+columnName+"\":"+"\""+tableInfo.getComments()+"\"";
            }else {
                row="\t\""+columnName+"\":"+"\""+columnName+"\"";
            }

            if (i != tableInfos.size() - 1) {
                row += ",";
            }
            res+=row+"\n";
        }
        res+="}";
        return res;
    }
}
