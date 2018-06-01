package entity;

/**
 * Created by Huppert on 2018/5/27.
 */
public class DataConfig {
    private int databaseType;
    private String ip;
    private String port;
    private String userName;
    private String pwd;
    private String databaseInstance;
    private String tableName;
    private int actionType;
    private int jFieldWordType;
    private boolean isNullAnnotation;
    private boolean lengthAnnotation;
    private boolean dateAnnotation;
    private String dateAnnotationType;
    private boolean dateIsString;
    private boolean isRemarksAnnotation;
    private String prefixEdit;

    public int getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(int databaseType) {
        this.databaseType = databaseType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getDatabaseInstance() {
        return databaseInstance;
    }

    public void setDatabaseInstance(String databaseInstance) {
        this.databaseInstance = databaseInstance;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getjFieldWordType() {
        return jFieldWordType;
    }

    public void setjFieldWordType(int jFieldWordType) {
        this.jFieldWordType = jFieldWordType;
    }

    public boolean isNullAnnotation() {
        return isNullAnnotation;
    }

    public void setNullAnnotation(boolean nullAnnotation) {
        isNullAnnotation = nullAnnotation;
    }

    public boolean isLengthAnnotation() {
        return lengthAnnotation;
    }

    public void setLengthAnnotation(boolean lengthAnnotation) {
        this.lengthAnnotation = lengthAnnotation;
    }

    public boolean isDateAnnotation() {
        return dateAnnotation;
    }

    public void setDateAnnotation(boolean dateAnnotation) {
        this.dateAnnotation = dateAnnotation;
    }

    public String getDateAnnotationType() {
        return dateAnnotationType;
    }

    public void setDateAnnotationType(String dateAnnotationType) {
        this.dateAnnotationType = dateAnnotationType;
    }

    public boolean isDateIsString() {
        return dateIsString;
    }

    public void setDateIsString(boolean dateIsString) {
        this.dateIsString = dateIsString;
    }

    public boolean isRemarksAnnotation() {
        return isRemarksAnnotation;
    }

    public void setRemarksAnnotation(boolean remarksAnnotation) {
        isRemarksAnnotation = remarksAnnotation;
    }

    public String getPrefixEdit() {
        return prefixEdit;
    }

    public void setPrefixEdit(String prefixEdit) {
        this.prefixEdit = prefixEdit;
    }
}
