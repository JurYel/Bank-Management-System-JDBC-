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
import java.util.Properties;
import java.util.ArrayList;
import java.io.FileInputStream;
import cs282.project.core.Account;
import cs282.project.core.PasswordEncryption;

public class AccountDAO {
    private Connection myConn;
    
    public AccountDAO()throws SQLException{
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
    
    public void registerAccount(Account acc)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO account (account_id,branch_id,customer_id,balance,account_pin) VALUES(?,?,?,?,?)");
            pst.setString(1,acc.getAccountID());
            pst.setInt(2,acc.getBranchID());
            pst.setInt(3,acc.getCustomerID());
            pst.setDouble(4,acc.getBalance());
            pst.setString(5,PasswordEncryption.getHash(acc.getAccountPin().getBytes(), "MD5"));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateAccount(Account acc)throws SQLException{
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            pst = myConn.prepareStatement("UPDATE customer SET customer_firstName = ? , customer_lastName = ? WHERE customer_id = ?");
            pst.setString(1,acc.getFirstName());
            pst.setString(2,acc.getLastName());
            pst.setInt(3, acc.getCustomerID());
            
            pst.executeUpdate();
            
            /* Update Account */
            
            pst = myConn.prepareStatement("UPDATE account SET branch_id = ? WHERE account_id = ?");
            pst.setInt(1,acc.getBranchID());
            pst.setString(2, acc.getAccountID());
            
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
            pst = myConn.prepareStatement("UPDATE account SET balance = ? WHERE account_id = ?");
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
            pst = myConn.prepareStatement("UPDATE account SET balance = ?  WHERE account_id = ?");
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
    
    public void deleteAccount(String id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM account WHERE account_id = ?");
            pst.setString(1, id);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
        
    }
    
    public int getAccountID(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT account_id,customer_firstName,customer_lastName FROM account as acc,customer as cu "
                    + "WHERE acc.customer_id = cu.customer_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String theName = String.format("%s %s",rs.getString("customer_firstName"),rs.getString("customer_lastName"));
                if(theName.equalsIgnoreCase(name)){
                    id = rs.getInt("account_id");
                }
            }
            return id;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return id;
    }
    
    public boolean checkAccountPin(String id,String pin)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT account_pin FROM account WHERE account_id = ?");
            pst.setString(1,id);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("account_pin").equals(PasswordEncryption.getHash(pin.getBytes(),"MD5"))){
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
    
    public boolean checkAccountID(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT account_id FROM account");
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
    
    
    public boolean checkCustomerName(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT customer_firstName,customer_lastName FROM customer");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String cName = String.format("%s %s",rs.getString("customer_firstName"),rs.getString("customer_lastName"));
                if(cName.equalsIgnoreCase(name)){
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
    
    public ArrayList<Account> getAllAccounts()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Account> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT acc.account_id,acc.branch_id,city,acc.customer_id, "
                    + "customer_firstName,customer_lastName,balance,account_pin,DATE_FORMAT(date_opened,'%M %e, %Y')as date_opened "
                    + "FROM account as acc,branch as br, customer as cu,current_account as ca "
                    + "WHERE (acc.branch_id,acc.customer_id,acc.account_id) = (br.branch_id,cu.customer_id,ca.account_id)");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Account account = convertRowToAccount(rs);
                list.add(account);
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
    
    public ArrayList<Account> searchAccount(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Account> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT acc.account_id,acc.branch_id,city,acc.customer_id, "
                    + "customer_firstName,customer_lastName,balance,account_pin,DATE_FORMAT(date_opened,'%M %e, %Y') as date_opened "
                    + "FROM account as acc, branch as br, customer as cu,current_account as ca "
                    + "WHERE (acc.branch_id,acc.customer_id,acc.account_id) = (br.branch_id,cu.customer_id,ca.account_id) "
                    + "AND CONCAT(acc.account_id,customer_firstName,customer_lastName) LIKE '%"+search+"%'");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Account acc = convertRowToAccount(rs);
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
    
    private Account convertRowToAccount(ResultSet rs)throws SQLException{
        String account_id = rs.getString("acc.account_id");
        int branch_Id = rs.getInt("acc.branch_id");
        int customer_id = rs.getInt("acc.customer_id");
        String city = rs.getString("city");
        String first = rs.getString("customer_firstName");
        String last = rs.getString("customer_lastName");
        double balance = rs.getDouble("balance");
        String pin = rs.getString("account_pin");
        String date = rs.getString("date_opened");
        
        Account account = new Account(city,first,last,balance,pin);
        account.setAccountID(account_id);
        account.setBranchID(branch_Id);
        account.setCustomerID(customer_id);
        account.setStrDateOpened(date);
        return account;
    }
    
    private void close(PreparedStatement pst, ResultSet rs)throws SQLException{
        if(pst!=null){
            pst.close();
        }
        if(rs!=null){
            rs.close();
        }
    }
}
