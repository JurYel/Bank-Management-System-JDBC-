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
import cs282.project.core.SavingsAccount;
import java.util.Date;

public class SavingsAccountDAO {
    private Connection myConn;
    
    public SavingsAccountDAO()throws SQLException{
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
    
    public void addSavingsAccount(SavingsAccount acc)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT savings_account (account_id,min_balance,date_opened,temp_date) VALUES(?,?,?,?)");
            pst.setString(1, acc.getAccountID());
            pst.setDouble(2,acc.getMinBalance());
            pst.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
            pst.setDate(4,new java.sql.Date(System.currentTimeMillis()));
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
        
    }
    
    public void depositSavings(String id, double amt)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE savings_account SET min_balance = ? WHERE account_id = ?");
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
    
    public void withdrawSavings(String id, double amt)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE savings_account SET min_balance = ? WHERE account_id = ?");
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
    
    public void gainInterest()throws SQLException{
        PreparedStatement pst = null;
        Date curDate = new Date();
        try{
            myConn.setAutoCommit(false);
            ArrayList<SavingsAccount> accounts = this.fetchAccounts();
            for(int i = 0;i<accounts.size();i++){
                int temp = (int) accounts.get(i).getTempDate().getTime();
                int current = (int) curDate.getTime();
                
                if(convertMillisecondsToDays(current - temp)/7 >= 1){
                    int weeks = (convertMillisecondsToDays(temp - current)/7);
                    double percentage = (0.03 * weeks);
                    double newBal = (accounts.get(i).getMinBalance() + (accounts.get(i).getMinBalance() * percentage));
                    
                    pst = myConn.prepareStatement("UPDATE savings_account SET min_balance = ?");
                    pst.setDouble(1,newBal);
                    pst.executeUpdate();
                    
                    /* Update temp_date to current date */
                    
                    pst = myConn.prepareStatement("UPDATE savings_account SET temp_date = ?");
                    pst.setDate(1,new java.sql.Date(System.currentTimeMillis()));
                    pst.executeUpdate();
                }
            }
            
            myConn.commit();
        }
        catch(Exception exc){
            exc.printStackTrace();
            myConn.rollback();
        }
        finally{
            close(pst,null);
        }
    }
    
    public double getSavings(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        double bal = 0;
        try{
            pst = myConn.prepareStatement("SELECT min_balance FROM savings_account WHERE account_id = ?");
            pst.setString(1,id);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                bal = rs.getDouble("min_balance");
                
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
    
    public boolean checkSavingsAccountID(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT account_id FROM savings_account");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("account_id").equals(id)){
                    return true;
                }
            }
            return false;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return false;
    }
    
    public ArrayList<SavingsAccount> fetchAccounts()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<SavingsAccount> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT account_id,min_balance,date_opened,temp_date FROM savings_account");
            rs = pst.executeQuery();
            
            while(rs.next()){
                SavingsAccount acc = convertRowToAccount(rs);
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
    
    public ArrayList<SavingsAccount> getSavingsAccounts()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<SavingsAccount> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT sa.account_id, city,min_balance, "
                    + "DATE_FORMAT(date_opened,'%M %e, %Y - %l:%i %p') as date_opened "
                    + "FROM savings_account as sa,account as acc, branch as br "
                    + "WHERE (sa.account_id,acc.branch_id) = (acc.account_id,br.branch_id)");
            rs = pst.executeQuery();
            
            while(rs.next()){
                SavingsAccount acc = convertRowToSavingsAccount(rs);
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
    
    public ArrayList<SavingsAccount> searchSavingsAccount(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<SavingsAccount> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT sa.account_id, city,min_balance, "
                    + "DATE_FORMAT(date_opened,'%M %e, %Y - %l:%i %p') as date_opened "
                    + "FROM savings_account as sa, account as acc, branch as br "
                    + "WHERE (sa.account_id,acc.branch_id) = (acc.account_id,br.branch_id) "
                    + "AND sa.account_id LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                SavingsAccount acc = convertRowToSavingsAccount(rs);
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
    
    private int convertMillisecondsToDays(int ms){
        int days = (int) Math.ceil(ms/(1000*60*60*24))+1;
        return days;
    }
    
    private Timestamp convertUtilToTimestamp(Date uDate){
        Timestamp timestamp = new Timestamp(uDate.getTime());
        return timestamp;
    }
    
    private Date convertTimestampToUtil(Timestamp timestamp){
        Date uDate = new java.util.Date(timestamp.getTime());
        return uDate;
    }
    
    private SavingsAccount convertRowToAccount(ResultSet rs)throws SQLException{
        String accID = rs.getString("account_id");
        double curBal = rs.getDouble("min_balance");
        java.util.Date date_opened = convertTimestampToUtil(rs.getTimestamp("date_opened"));
        java.util.Date datetime = rs.getDate("temp_date");
        
        SavingsAccount svAcc = new SavingsAccount(accID,null,curBal,date_opened);
        svAcc.setTempDate(datetime);
        
        return svAcc;
    }
    
    private SavingsAccount convertRowToSavingsAccount(ResultSet rs)throws SQLException{
        String accID = rs.getString("sa.account_id");
        String branch = rs.getString("city");
        double curBal = rs.getDouble("min_balance");
        String dtime = rs.getString("date_opened");
        
        SavingsAccount svAcc = new SavingsAccount(accID,branch,curBal,null);
        svAcc.setDateTime(dtime);
        
        return svAcc;
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
