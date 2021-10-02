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
import cs282.project.core.TransactionHistory;

public class TransactionHistoryDAO {
    private Connection myConn;
    
    public TransactionHistoryDAO()throws SQLException{
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
    
    public void recordTransaction(TransactionHistory trans)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO transaction_history (account_id,action,account_type,amount,datetime) VALUES(?,?,?,?,?)");
            pst.setString(1,trans.getAccountID());
            pst.setString(2,trans.getAction());
            pst.setString(3,trans.getAccountType());
            pst.setDouble(4,trans.getAmount());
            pst.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
            
            pst.executeUpdate();
            
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void deleteTransaction(String id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM transaction_history WHERE account_id = ?");
            pst.setString(1,id);
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public ArrayList<TransactionHistory> getAllTransactions()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<TransactionHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT account_id,action,account_type,amount, "
                    + "DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM transaction_history");
            rs = pst.executeQuery();
            
            while(rs.next()){
                TransactionHistory trans = convertRowToHistory(rs);
                list.add(trans);
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
    
    public ArrayList<TransactionHistory> getUserTransactions(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<TransactionHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT account_id, action,account_type,amount, "
                    + "DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM transaction_history "
                    + "WHERE account_id = ? ORDER BY datetime ASC");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                TransactionHistory trans = convertRowToHistory(rs);
                list.add(trans);
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
    
    public ArrayList<TransactionHistory> searchTransaction(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<TransactionHistory> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT account_id,action,account_type,amount "
                    + "DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM transaction_history "
                    + "WHERE CONCAT(action,account_type,amount,datetime) LIKE '%"+search+"%'");
            rs = pst.executeQuery();
            
            while(rs.next()){
                TransactionHistory trans = convertRowToHistory(rs);
                list.add(trans);
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
    
    private TransactionHistory convertRowToHistory(ResultSet rs)throws SQLException{
        String account_id = rs.getString("account_id");
        String action = rs.getString("action");
        String type = rs.getString("account_type");
        double amount = rs.getDouble("amount");
        String datetime = rs.getString("datetime");
        
        TransactionHistory trans = new TransactionHistory(account_id,action,type,amount,null);
        trans.setTransDate(datetime);
        
        return trans;
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
