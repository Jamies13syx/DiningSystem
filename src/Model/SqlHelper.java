package Model;

import javax.xml.catalog.Catalog;
import java.sql.*;

/**
 * crud and login info
 */

public class SqlHelper {
    Connection ct = null;
    PreparedStatement ps = null;
    ResultSet rs= null;
    String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    String url = "jdbc:sqlserver://127.0.0.1:1433;databaseName=LearningTest";
    String user = "sa";
    String password = "123456";

    public SqlHelper(){
        try{
            Class.forName(driver);
            ct = DriverManager.getConnection(url,user, password);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean operate(String sql, String[] parameters){
        boolean b;
        try{
            ps = ct.prepareStatement(sql);
            if(parameters.length != 0){
                for(int i = 0; i < parameters.length; i++){
                    ps.setString(i + 1, parameters[i]);
                }
            }
            b = (ps.executeUpdate() == 1);

        }
        catch (Exception e){
            e.printStackTrace();
            b = false;
        }
        finally {
            this.closeSource();
        }
        return b;
    }

    public ResultSet query(String sql, String[] parameters){
        try{
            ps = ct.prepareStatement(sql);
            if(parameters.length != 0){
                for(int i = 0; i < parameters.length; i++){
                    ps.setString(i + 1, parameters[i]);
                }
            }
            rs = ps.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return rs;
    }


    public void closeSource(){
        try {
            if(rs != null)
                rs.close();
            if(ps != null)
                ps.close();
            if(ct != null)
                ct.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
