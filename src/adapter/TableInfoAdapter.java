package adapter;

import entity.DataConfig;
import entity.TableInfo;
import utils.ToolUtils;

import java.util.ArrayList;

/**
 * @author hyp 1774549483@qq.com
 * @version v1.0
 * @Title:PACKAGE_NAME
 * @description
 * @date 2018/5/30 13:37
 */
public class TableInfoAdapter {

    public static String getJavaFile(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {
        String res = "";

        for (TableInfo tableInfo : tableInfos) {
            String rowStr = "";
            //日期是否使用Str
            if (dataInfo.isDateIsString()) {
                for (int i=0;i<StatusAdapter.getDatabaseColumTypeToJFile().length;i++) {
                    if("Date".equals(StatusAdapter.getDatabaseColumTypeToJFile()[i])){
                        StatusAdapter.getDatabaseColumTypeToJFile()[i]="String";
                    }
                }
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


    public static String getSelectSql(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {
        String res = "select\n";

        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            String row = "";
            if (!ToolUtils.strIsEmpty(dataInfo.getPrefixEdit())) {
                row = dataInfo.getPrefixEdit() + ".";
            }
            String columnName = tableInfo.getColumnName();
            if (dataInfo.getjFieldWordType() == 0) {
               // columnName = columnName.toLowerCase();
                columnName = tableInfo.getColumnName() + " as " + ToolUtils.toHump(columnName);
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
        if (!ToolUtils.strIsEmpty(dataInfo.getPrefixEdit())) {
            res += " " + dataInfo.getPrefixEdit();
        }
        return res;
    }

    private static String jFieldWordType(String columnName, DataConfig dataInfo) {
        if (dataInfo.getjFieldWordType() == 0) {




            columnName = ToolUtils.toHump(columnName);
        } else if (dataInfo.getjFieldWordType() == 1) {
            columnName = columnName.toLowerCase();
        } else {
            columnName = columnName.toUpperCase();
        }
        return columnName;
    }

    public static String getUpdateSql(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {

        String code = "CODE";
        String value = "code";
        String type = "varchar";

        for (TableInfo tableInfo : tableInfos) {
            if(tableInfo.isPk()){
                code = tableInfos.get(0).getColumnName();
                value = jFieldWordType(code.toLowerCase(), dataInfo);

                type = StatusAdapter.getDatabaseColumToMybatis(tableInfos.get(0).getDataType());
            }
        }


        String res = "UPDATE %s\n" +
                "<set>\n";
        res = String.format(res, dataInfo.getTableName());

        for (int i = 0; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            String row = "<if test=\"%s != null\">%s=#{%s,jdbcType=%s},</if>";
            String columnName = jFieldWordType(tableInfo.getColumnName(), dataInfo);

            if (!ToolUtils.strIsEmpty(dataInfo.getPrefixEdit())) {
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

    public static String getDeleteSql(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {
        String code = "CODE";
        String value = "code";

        for (TableInfo tableInfo : tableInfos) {
            if(tableInfo.isPk()){
                code = tableInfos.get(0).getColumnName();

                value=jFieldWordType(code,dataInfo);

                break;
            }
        }
        String res = "<delete id=\"delete\">\n" +
                "\tdelete from %s where %s = #{%s}\n" +
                "</delete>";

        if (!ToolUtils.strIsEmpty(dataInfo.getPrefixEdit())) {
            value = dataInfo.getPrefixEdit() + "." + value;
        }
        res = String.format(res, dataInfo.getTableName(), code, value);
        return res;
    }

    public static String getInsertSql(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {
        String res = "insert into %s(\n";
        String pkName="";
        String pkType="";
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


        for (int i = 0; i < tableInfos.size(); i++) {

            String cowStr = "#{";
            TableInfo tableInfo = tableInfos.get(i);
            if(tableInfo.isPk()){
                res += "seq_" + dataInfo.getTableName().toLowerCase() + ".nextval,\n";
                pkName=jFieldWordType(tableInfo.getColumnName(), dataInfo);
                pkType=StatusAdapter.getDatabaseColumType(tableInfo.getDataType());
            }else {
                String columnName = jFieldWordType(tableInfo.getColumnName(), dataInfo);
                if (!ToolUtils.strIsEmpty(dataInfo.getPrefixEdit())) {
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

        }

        res += ")\n";

        if(dataInfo.isReturnPk()){
            res+=String.format("<selectKey resultType=\"%s\" order=\"AFTER\" keyProperty=\"%s\">\n" +
                    "        SELECT seq_%s.currval AS %s from DUAL\n" +
                    "</selectKey>",pkType,pkName,dataInfo.getTableName().toLowerCase(),pkName);
        }

        return res;
    }

    public static String getInsertSelectiveSql(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {
        String res = "insert into %s";
        res += "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n";
        res = String.format(res, dataInfo.getTableName());

        StringBuilder key = new StringBuilder(res);
        tableInfos.forEach(t -> {
            String columnName = t.getColumnName();
            key.append("<if test=\"" + jFieldWordType(columnName, dataInfo) + "!= null\" >\n" + columnName + ",\n" + "</if>");
        });

        key.append("\n</trim>\n");

        key.append("<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n");

        tableInfos.forEach(t -> {
            String columnName = t.getColumnName();
            key.append("<if test=\"" + jFieldWordType(columnName, dataInfo) + " != null\" >\n" +
                    "        #{" + jFieldWordType(columnName, dataInfo) + ",jdbcType=" + StatusAdapter.getDatabaseColumToMybatis(t.getDataType()) + "},\n" +
                    "      </if>");
        });

        key.append("\n</trim>");

        return key.toString();

    }


    public static String getResultBaseMap(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {

        StringBuilder key = new StringBuilder("<resultMap id=\"BaseResultMap\" type=\"com\">\n");
        tableInfos.forEach(t -> {
            String columnName = t.getColumnName();
            if (columnName.equalsIgnoreCase("id")) {
                key.append("<id column=\"" + columnName + "\" jdbcType=\"INTEGER\" property=\"" + jFieldWordType(columnName, dataInfo) + "\" />\n");
            }
            key.append("\t<result column=\"" + columnName + "\" jdbcType=\"" + StatusAdapter.getDatabaseColumToMybatis(t.getDataType()) + "\" property=\"" + jFieldWordType(columnName, dataInfo) + "\" />\n");
        });
        key.append("</resultMap>");
        return key.toString();

    }

    public static String getJson(ArrayList<TableInfo> tableInfos, DataConfig dataInfo) {
        String res = "{\n";
        for (int i = 1; i < tableInfos.size(); i++) {
            TableInfo tableInfo = tableInfos.get(i);
            String columnName = jFieldWordType(tableInfo.getColumnName(), dataInfo);
            String row;
            if (dataInfo.isRemarksAnnotation()) {
                row = "\t\"" + columnName + "\":" + "\"" + tableInfo.getComments() + "\"";
            } else {
                row = "\t\"" + columnName + "\":" + "\"" + columnName + "\"";
            }

            if (i != tableInfos.size() - 1) {
                row += ",";
            }
            res += row + "\n";
        }
        res += "}";
        return res;
    }
}
