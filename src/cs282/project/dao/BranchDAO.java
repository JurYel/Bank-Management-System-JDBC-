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
import cs282.project.core.Branch;

public class BranchDAO {
    private Connection myConn;
    
    public BranchDAO()throws SQLException{
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
    
    public void addBranch(Branch branch)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO branch (city,branch_code) VALUES(?,?)");
            pst.setString(1, branch.getCity());
            pst.setString(2,branch.getBranchCode());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateBranch(Branch branch)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int bankID = 0;
        try{
            myConn.setAutoCommit(false);
            
            pst = myConn.prepareStatement("SELECT bank_id FROM bank WHERE branch_id = ?");
            pst.setInt(1, branch.getBranchID());
            rs = pst.executeQuery();
            
            while(rs.next()){
                bankID = rs.getInt("bank_id");
            }
            
            /* Update Bank Name*/
            
            pst = myConn.prepareStatement("UPDATE bank SET bank_name = ? WHERE bank_id  = ?");
            pst.setString(1,branch.getBankName());
            pst.setInt(2,bankID);
            pst.executeUpdate();
            
            /* Update other branch details */
            
            pst = myConn.prepareStatement("UPDATE branch SET city = ? WHERE branch_id = ?");
            pst.setString(1,branch.getCity());
            pst.setInt(2,branch.getBranchID());
            
            pst.executeUpdate();
            
            myConn.commit();
        }
        catch(Exception exc){
            exc.printStackTrace();
            myConn.rollback();
        }
        finally{
            close(pst,rs);
        }
    }
    
    public void deleteBranch(int id)throws SQLException {
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            pst = myConn.prepareStatement("DELETE FROM bank WHERE branch_id = ?");
            pst.setInt(1,id);
            pst.executeUpdate();
            /* Delete from branch afterwards */
            pst = myConn.prepareStatement("DELETE FROM branch WHERE branch_id = ?");
            pst.setInt(1,id);
            pst.executeUpdate();
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
    
    public int getBranchIDByCity(String city)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT branch_id FROM branch WHERE city = ?");
            pst.setString(1,city);
            rs = pst.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("branch_id");
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
    
    public int getBranchIDByCode(String code)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT branch_id FROM branch WHERE branch_code = ?");
            pst.setString(1,code);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("branch_id");
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
    
    public int getBranchByCity(String city)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT branch_id FROM branch WHERE city = ?");
            pst.setString(1,city);
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                id = rs.getInt("branch_id");
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
    
    public boolean checkBranchByCode(String code)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT branch_code FROM branch");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("branch_code").equalsIgnoreCase(code)){
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
    
    public boolean checkBranchBankName(String name)throws SQLException{
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
    
    public boolean checkBranchByCity(String city)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT city FROM branch");
            
            rs = pst.executeQuery();
            while(rs.next()){
                if(rs.getString("city").equalsIgnoreCase(city)){
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
    
    public boolean checkBranchTransactions(int branchID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT branch_id FROM account");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getInt("branch_id") == branchID){
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
    
    public ArrayList<Branch> getAllBranches()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Branch> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT br.branch_id,bank_name,city,branch_code FROM branch as br,bank WHERE br.branch_id = bank.branch_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Branch branch = convertRowToBranch(rs);
                list.add(branch);
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
    
    public ArrayList<Branch> searchBranch(String search)throws SQLException {
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Branch> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT br.branch_id,bank_name,city,branch_code FROM branch as br, bank "
                    + " WHERE CONCAT(city,branch_code) LIKE '%"+search+"%' AND br.branch_id = bank.branch_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Branch branch = convertRowToBranch(rs);
                list.add(branch);
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
    
    private Branch convertRowToBranch(ResultSet rs )throws SQLException{
        int id = rs.getInt("br.branch_id");
        String name = rs.getString("bank_name");
        String city = rs.getString("city");
        String code = rs.getString("branch_code");
        
        Branch branch = new Branch(name,city,code);
        branch.setBranchID(id);
        return branch;
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
