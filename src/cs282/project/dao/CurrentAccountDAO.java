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
import cs282.project.core.CurrentAccount;

public class CurrentAccountDAO {
    private Connection myConn;
    
    public CurrentAccountDAO()throws SQLException{
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
    
    public void addCurrentAccount(CurrentAccount acc)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO current_account(account_id,current_balance,date_opened) VALUES(?,?,?)");
            pst.setString(1, acc.getAccountID());
            pst.setDouble(2,acc.getCurrentBalance());
            pst.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void depositCurrentAmount(String id, double amt)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE current_account SET current_balance = ? WHERE account_id = ?");
            pst.setDouble(1,amt);
            pst.setString(2,id);
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void withdrawAmount(String id, double amt)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE current_account SET current_balance = ?  WHERE account_id = ?");
            pst.setDouble(1,amt);
            pst.setString(2,id);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public double getAccountBalance(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        double bal = 0;
        try{
            pst = myConn.prepareStatement("SELECT current_balance FROM current_account WHERE account_id = ?");
            pst.setString(1,id);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                bal = rs.getDouble("current_balance");
            }
            return bal;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return bal;
    }
    
    public ArrayList<CurrentAccount> getCurrentAccounts()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CurrentAccount> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT ca.account_id,city, current_balance, "
                    + "DATE_FORMAT(date_opened,'%M %e, %Y - %l:%i %p') as date_opened "
                    + "FROM current_account as ca, account as acc, branch as br "
                    + "WHERE (ca.account_id,acc.branch_id) = (acc.account_id,br.branch_id)");
            rs = pst.executeQuery();
            
            while(rs.next()){
                CurrentAccount acc = convertRowToCurrentAccount(rs);
                list.add(acc);
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
    
    public ArrayList<CurrentAccount> searchCurrentAccount(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<CurrentAccount> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT ca.account_id,city,current_balance, "
                    + "DATE_FORMAT(date_opened,'%M %e, %Y - %l:%i %p') as date_opened "
                    + "FROM current_account as ca, account as acc, branch as br "
                    + "WHERE (ca.account_id,acc.branch_id) = (acc.account_id,br.branch_id) "
                    + "AND ca.account_id LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                CurrentAccount acc = convertRowToCurrentAccount(rs);
                list.add(acc);
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
    
    private Timestamp convertUtilToTimestamp(java.util.Date uDate){
        Timestamp timestamp = new Timestamp(uDate.getTime());
        return timestamp;
    }
    
    private CurrentAccount convertRowToCurrentAccount(ResultSet rs)throws SQLException{
        String accID = rs.getString("ca.account_id");
        String branch = rs.getString("city");
        double bal = rs.getDouble("current_balance");
        String datetime = rs.getString("date_opened");
        
        CurrentAccount curAcc = new CurrentAccount(accID,branch,bal,null);
        curAcc.setAccountID(accID);
        curAcc.setDateTime(datetime);
        
        return curAcc;
    }
    
    private void close(PreparedStatement pst,ResultSet rs)throws SQLException{
        if(pst != null){
            pst.close();
        }
        if(rs != null){
            rs.close();
        }
    }
}
