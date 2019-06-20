package utils;

import bean.Page;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBUtil {
    private static String DRIVER_NAME;
    private static String URL;
    private static String USER;
    private static String PWD;
    private static Connection connection;
    private static DBUtil dbUtil;
    /**
     * 单例模式
     */
    private DBUtil() {}

    /**
     * 加载数据库配置文件，初始化配置文件中的数据
     */
    static {
        Properties properties=new Properties();
        try {
            properties.load(DBUtil.class.getClassLoader().getResourceAsStream("confige.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DRIVER_NAME=properties.getProperty("DRIVER_NAME");
        URL=properties.getProperty("URL");
        USER=properties.getProperty("USER");
        PWD=properties.getProperty("PWD");
    }
    public static DBUtil getIstance(){
        if(null==dbUtil)
            return new DBUtil();
        else
            return dbUtil;
    }

    /**
     * 获取数据库连接
     * @return
     */
    public Connection getConnection(){
        try {
            if (null==connection||connection.isClosed()){
                Class.forName(DRIVER_NAME);
                connection = DriverManager.getConnection(URL,USER, PWD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 关闭连接释放资源
     * @param statement
     * @param rs
     */
    public void close(Statement statement, ResultSet rs){
        try {
            if (null!=rs&&!rs.isClosed())
                rs.close();
            if(null!=statement&&!statement.isClosed())
                statement.close();
            if (null!=connection&&!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现单个增删改的方法
     * @param sql
     * @return
     */
    public int upDate(String sql){
        getConnection();
        try {
            Statement statement=connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 实现查询功能
     * @param sql
     * @return
     */
    public ResultSet query(String sql){
        getConnection();
        try {
            Statement statement=connection.createStatement();
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分页查询
     * @param cls
     * @param page
     * @param table
     * @param <T>
     * @return
     */
    public <T> List<T> queryByPage(Class<T> cls,Page page,String table){
        int currentPage = page.getCurrentPage();
        int numPerPage = page.getNumPerPage();
        int fromIndex = (currentPage - 1) * numPerPage;
        String sql = "select * from "+table+" limit " +fromIndex+","+numPerPage+"";
        ResultSet rs =query(sql);
        return queryByRs(rs,cls);
    }

    /**
     * 根据返回结果集得到对象集合
     * @param rs
     * @param cls
     * @param <T>
     * @return
     */
    public <T> List<T> queryByRs(ResultSet rs,Class <T> cls){
        List<T> list=new ArrayList();
        try {
            while(rs.next()){
                T obj=cls.getDeclaredConstructor().newInstance();
                ResultSetMetaData rd=rs.getMetaData();
                for (int i = 0; i < rd.getColumnCount(); i++) {
                    //获取列名
                    String columnName=rd.getColumnLabel(i+1);
                    //组合方法名
                    String methodName="set"+columnName.substring(0, 1).toUpperCase()+columnName.substring(1);
                    //获取列类型
                    int columnType=rd.getColumnType(i+1);
                    Method method=null;
                    switch(columnType) {
                        case Types.VARCHAR:
                        case Types.CHAR:
                            method=cls.getMethod(methodName, String.class);
                            if(method!=null) {
                                method.invoke(obj, rs.getString(columnName));
                            }
                            break;
                        case Types.INTEGER:
                        case Types.SMALLINT:
                            method=cls.getMethod(methodName, int.class);
                            if(method!=null) {
                                method.invoke(obj, rs.getInt(columnName));
                            }
                            break;
                        case Types.BIGINT:
                            method=cls.getMethod(methodName, long.class);
                            if(method!=null) {
                                method.invoke(obj, rs.getLong(columnName));
                            }
                            break;
                        case Types.DATE:
                        case Types.TIMESTAMP:
                            try {
                                method=cls.getMethod(methodName, Date.class);
                                if(method!=null) {
                                    method.invoke(obj, rs.getTimestamp(columnName));
                                }
                            } catch(Exception e) {
                                method=cls.getMethod(methodName, String.class);
                                if(method!=null) {
                                    method.invoke(obj, rs.getString(columnName));
                                }
                            }
                            break;
                        case Types.DECIMAL:
                            method=cls.getMethod(methodName, BigDecimal.class);
                            if(method!=null) {
                                method.invoke(obj, rs.getBigDecimal(columnName));
                            }
                            break;
                        case Types.DOUBLE:
                        case Types.NUMERIC:
                            method=cls.getMethod(methodName, double.class);
                            if(method!=null) {
                                method.invoke(obj, rs.getDouble(columnName));
                            }
                            break;
                        case Types.BIT:
                            method=cls.getMethod(methodName, boolean.class);
                            if(method!=null) {
                                method.invoke(obj, rs.getBoolean(columnName));
                            }
                            break;
                        default:
                            break;
                    }
                }
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询单个数据
     * @param ColumnName
     * @param value
     * @param table
     * @param type
     * @return
     */
    public boolean queryDate(String ColumnName,String value,String table,String type){
        String sql = null;
        if(type.equals("int")||type.equals("double"))
            sql = "select "+ColumnName+" from "+table+" where "+ColumnName+"="+value;
        else
            sql = "select "+ColumnName+" from "+table+" where "+ColumnName+"='"+value+"'";
        ResultSet rs =query(sql);
        try {
            if (rs.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询单个对象
     * 要求实体类的第一个属性为数据库中的主键
     * @param cls
     * @param t
     * @param table
     * @param <T>
     * @return
     */
    public <T> ResultSet isExists(Class <T> cls,T t,String table){
        Field [] fields=cls.getDeclaredFields();
        fields[0].setAccessible(true);
        ResultSet rs = null;
        if(fields[0].getType().toString().equals("int")||fields[0].getType().toString().equals("double")){
            try {
                rs=query("select * from " + table + "where " + fields[0].getName() + "=" + fields[0].get(t));
            } catch (IllegalAccessException e) {
            e.printStackTrace();
            }
        }else{
            try {
                rs=query("select * from " + table + " where " + fields[0].getName() + "='" + fields[0].get(t)+"'");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return rs;
    }
}
