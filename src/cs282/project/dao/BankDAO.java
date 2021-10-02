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
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import cs282.project.core.Bank;

public class BankDAO {
    private Connection myConn;
    
    public BankDAO()throws SQLException{
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
    
    public void registerBank(Bank bank)throws SQLException{
        PreparedStatement pst = null;
        try{
           pst = myConn.prepareStatement("INSERT INTO bank(branch_id,bank_name,bank_address) VALUES(?,?,?)");
           pst.setInt(1,bank.getBranchID());
           pst.setString(2,bank.getBankName());
           pst.setString(3,bank.getBankAddress());
           
           pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateBank(Bank bank)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE bank SET bank_name = ?, bank_address = ? WHERE bank_id = ?");
            pst.setString(1, bank.getBankName());
            pst.setString(2, bank.getBankAddress());
            pst.setInt(3,bank.getBankID());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void expelBank(int id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM bank WHERE bank_id = ?");
            pst.setInt(1,id);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public int getBankID(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT bank_id FROM bank WHERE bank_name = ?");
            pst.setString(1,name);
            rs = pst.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("bank_id");
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
    
    public int getBranchID(int bankID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int branchID = 0;
        try{
            pst = myConn.prepareStatement("SELECT bank.branch_id FROM branch as br, bank WHERE br.branch_id = bank.branch_id AND bank_id = ?");
            pst.setInt(1,bankID);
            rs = pst.executeQuery();
            
            while(rs.next()){
                branchID = rs.getInt("bank.branch_id");
            }
            return branchID;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return branchID;
    }
    
    public boolean checkBankByName(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT bank_name FROM bank");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("bank_name").equalsIgnoreCase(name)){
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
    
    public boolean checkTransactions(int bankID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int branch_id = 0;
        try{
            myConn.setAutoCommit(false);
            pst = myConn.prepareStatement("SELECT bank.branch_id FROM branch as br, bank WHERE (bank.branch_id,bank_id) = (br.branch_id,?)");
            pst.setInt(1,bankID);
            rs = pst.executeQuery();
            
            while(rs.next()){
                branch_id = rs.getInt("bank.branch_id");
            }
            
            /* check transactions */
            
            pst = myConn.prepareStatement("SELECT branch_id FROM account");
            rs = pst.executeQuery();
            myConn.commit();
            while(rs.next()){
                if(rs.getInt("branch_id") == branch_id){
                    return true;
                }
            }
            return false;
        }
        catch(Exception exc){
            exc.printStackTrace();
            myConn.rollback();
        }
        finally{
            close(pst,rs);
        }
        return false;
    }
    
    public ArrayList<Bank> getAllBanks()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Bank> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT bank_id,bank.branch_id,bank_name,bank_address,branch_code "
                    + "FROM bank, branch WHERE branch.branch_id = bank.branch_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Bank bank = convertRowToBank(rs);
                list.add(bank);
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
    
    public ArrayList<Bank> searchBank(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Bank> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT bank_id,bank.branch_id,bank_name,bank_address,branch_code "
                    + "FROM bank, branch WHERE branch.branch_id = bank.branch_id "
                    + "AND CONCAT(bank_name,bank_address,branch_code) LIKE '%"+search+"%'");
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                Bank bank = convertRowToBank(rs);
                list.add(bank);
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
    
    private Bank convertRowToBank(ResultSet rs)throws SQLException{
        int id = rs.getInt("bank_id");
        int branch_id = rs.getInt("bank.branch_id");
        String name = rs.getString("bank_name");
        String code = rs.getString("branch_code");
        String address = rs.getString("bank_address");
        
        Bank bank = new Bank(name,code,address);
        bank.setBankID(id);
        bank.setBranchID(branch_id);
        return bank;
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
