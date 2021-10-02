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
import cs282.project.core.Customer;

public class CustomerDAO {
    private Connection myConn;
    
    public CustomerDAO()throws SQLException{
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
    
    public void addCustomer(Customer customer)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO customer (customer_firstName,customer_lastName,customer_address,contact_number) "
                    + "VALUES(?,?,?,?)");
            pst.setString(1,customer.getCustomerFirstName());
            pst.setString(2,customer.getCustomerLastName());
            pst.setString(3,customer.getCustomerAddress());
            pst.setString(4,customer.getContactNumber());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
        
    }
    
    public void updateCustomer(Customer customer)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE customer SET customer_firstName = ? , customer_lastName = ? , "
                                + "customer_address = ?, contact_number = ? WHERE customer_id = ?");
            pst.setString(1,customer.getCustomerFirstName());
            pst.setString(2,customer.getCustomerLastName());
            pst.setString(3,customer.getCustomerAddress());
            pst.setString(4,customer.getContactNumber());
            pst.setInt(5, customer.getCustomerID());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void deleteCustomer(int id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM customer WHERE customer_id = ?");
            pst.setInt(1, id);
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public int getCustomerID(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
            pst = myConn.prepareStatement("SELECT customer_id,customer_firstName,customer_lastName FROM customer");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String cust = String.format("%s %s",rs.getString("customer_firstName"),rs.getString("customer_lastName"));
                if(cust.equalsIgnoreCase(name)){
                    id = rs.getInt("customer_id");
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
    
    public boolean checkCustomerAccount(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT customer_id FROM account");
            
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getInt("customer_id") == id){
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
    
    public boolean checkNameDifference(int id,String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT customer_firstName,customer_lastName FROM customer WHERE customer_id = ?");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String cust = String.format("%s %s",rs.getString("customer_firstName"),rs.getString("customer_lastName"));
                if(cust.equalsIgnoreCase(name)){
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
    
    public boolean checkIfCustomerExists(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT customer_firstName,customer_lastName FROM customer");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String cust = String.format("%s %s",rs.getString("customer_firstName"),rs.getString("customer_lastName"));
                if(cust.equalsIgnoreCase(name)){
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
    
    public ArrayList<Customer> getAllCustomers()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Customer> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT customer_id, customer_firstName,customer_lastName,customer_address,contact_number "
                    + "FROM customer");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Customer customer = convertRowToCustomer(rs);
                list.add(customer);
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
    
    public ArrayList<Customer> searchCustomer(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Customer> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT customer_id,customer_firstName,customer_lastName,customer_address,contact_number "
                    + "FROM customer WHERE CONCAT(customer_firstName,customer_lastName,customer_address) LIKE '%"+search+"%'");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Customer customer = convertRowToCustomer(rs);
                list.add(customer);
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
    
    private Customer convertRowToCustomer(ResultSet rs)throws SQLException{
        int id = rs.getInt("customer_id");
        String first = rs.getString("customer_firstName");
        String last = rs.getString("customer_lastName");
        String address = rs.getString("customer_address");
        String contact = rs.getString("contact_number");
        
        Customer customer = new Customer(first,last,address,contact);
        customer.setCustomerID(id);
        return customer;
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
