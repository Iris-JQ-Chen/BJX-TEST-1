package iris.bjx.test1;

import java.sql.*;

public class DBUtils {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/BJX0316";
    private static final String USER = "root";
    private static final String PASSWORD = "cjqloveldf999";

    public DBUtils(){

    }

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public void close(Connection connection){
        try {
            if (connection != null){
                connection.close();
                connection = null;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void close(Statement statement){
        try {
            if (statement != null){
                statement.close();
                statement= null;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void close(PreparedStatement preparedStatement){
        try {
            if (preparedStatement != null){
                preparedStatement.close();
                preparedStatement = null;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void close(ResultSet resultSet){
        try {
            if (resultSet != null){
                resultSet.close();
                resultSet = null;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
