/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.dao;

/**
 *
 * @author Jur Yel
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import cs282.project.core.UserLoginHistory;
        
public class UserLoginHistoryDAO {
    private Connection myConn;
    
    public UserLoginHistoryDAO()throws SQLException{
        try{
            Properties props = new Properties();
            props.load(new FileInputStream("sql/bankManagement.properties"));
            
            String user = props.getProperty("user");
            String pass = props.getProperty("password");
            String dburl = props.getProperty("dburl");
            
            myConn = DriverManager.getConnection(dburl,user,pass);
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
    }
    
    public void recordLogin(UserLoginHistory user)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO user_loginHistory(account_id,datetime) VALUES(?,?)");
            pst.setString(1,user.getAccountID());
            pst.setTimestamp(2,new Timestamp(System.currentTimeMillis()));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public ArrayList<String> getLastLogin(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<String> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM user_loginHistory "
                    + "WHERE account_id = ? ORDER BY datetime DESC");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String login = rs.getString("datetime");
                list.add(login);
            }
            return list;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    public ArrayList<UserLoginHistory> getAllLoginHistory()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<UserLoginHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT account_id, DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM user_loginHistory ORDER BY datetime DESC");
            rs = pst.executeQuery();
            
            while(rs.next()){
                UserLoginHistory history = convertRowToLoginHistory(rs);
                list.add(history);
            }
            return list;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    public ArrayList<String> getUserLoginHistory(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<String> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM user_loginHistory "
                    + "WHERE account_id = ? ORDER BY datetime DESC");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String datetime = rs.getString("datetime");
                list.add(datetime);
            }
            return list;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return list;
    }
    
    private UserLoginHistory convertRowToLoginHistory(ResultSet rs)throws SQLException{
        String account_id = rs.getString("account_id");
        String datetime = rs.getString("datetime");
        
        UserLoginHistory history = new UserLoginHistory(account_id,null);
        history.setLoginTime(datetime);
        return history;
    }
    
    private void close(PreparedStatement pst, ResultSet rs)throws SQLException{
        if(pst != null){
            pst.close();
        }
        if(rs != null){
            rs.close();
        }
    }
}
