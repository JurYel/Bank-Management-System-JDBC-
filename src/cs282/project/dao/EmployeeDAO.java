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
import cs282.project.core.Employee;
import cs282.project.core.PasswordEncryption;

public class EmployeeDAO {
    private Connection myConn;
    
    public EmployeeDAO()throws SQLException{
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
    
    public void defRegisterEmployee(Employee emp)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO employee (employee_id,branch_id,emp_firstName,emp_lastName,password) VALUES(?,?,?,?,?)");
            pst.setString(1,emp.getEmployeeID());
            pst.setInt(2,emp.getBranchID());
            pst.setString(3, emp.getFirstName());
            pst.setString(4,emp.getLastName());
            pst.setString(5, PasswordEncryption.getHash(emp.getPassword().getBytes(), "MD5"));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void registerEmployee(Employee emp)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO employee (employee_id,branch_id,emp_firstName,emp_lastName,password) VALUES(?,?,?,?,?)");
            pst.setString(1,emp.getEmployeeID());
            pst.setInt(2,emp.getBranchID());
            pst.setString(3, emp.getFirstName());
            pst.setString(4,emp.getLastName());
            pst.setString(5, PasswordEncryption.getHash(emp.getLastName().toUpperCase().getBytes(), "MD5"));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateEmployee(Employee emp)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE employee SET branch_id = ?, emp_firstName = ? ,  emp_lastName = ? WHERE employee_id = ?");
            pst.setInt(1,emp.getBranchID());
            pst.setString(2,emp.getFirstName());
            pst.setString(3,emp.getLastName());
            pst.setString(4,emp.getEmployeeID());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void fireEmployee(String empID)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM employee WHERE employee_id = ?");
            pst.setString(1,empID);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public String getEmployeeID(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        String id = new String();
        try{
            pst = myConn.prepareStatement("SELECT employee_id,emp_firstName,emp_lastName FROM employee");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String employee = String.format("%s %s",rs.getString("emp_firstName"),rs.getString("emp_lastName"));
                if(employee.equalsIgnoreCase(name)){
                    id = rs.getString("employee_id");
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
    
    public boolean checkEmployeeID(String id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT employee_id FROM employee");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("employee_id").equals(id)){
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
    
    public boolean checkIfNameExists(String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT emp_firstName,emp_lastName FROM employee");
            rs = pst.executeQuery();
            
            while(rs.next()){
                String emp = String.format("%s %s",rs.getString("emp_firstName"),rs.getString("emp_lastName"));
                if(emp.equals(name)){
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
    
    public boolean checkNameDifference(String id,String name)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT emp_firstName,emp_lastName FROM employee WHERE employee_id = ?");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                String emp = String.format("%s %s",rs.getString("emp_firstName"),rs.getString("emp_lastName"));
                if(emp.equalsIgnoreCase(name)){
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
    
    public boolean checkPassword(String id,String pass)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT password FROM employee WHERE employee_id = ?");
            pst.setString(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getString("password").equals(PasswordEncryption.getHash(pass.getBytes(), "MD5"))){
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
    
    public ArrayList<Employee> getAllEmployees()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Employee> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT employee_id,emp.branch_id,city,emp_firstName,emp_lastName "
                    + "FROM employee as emp,branch as br WHERE emp.branch_id = br.branch_id");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Employee emp = convertRowToEmployee(rs);
                list.add(emp);
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
    
    public ArrayList<Employee> searchEmployee(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Employee> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT employee_id,emp.branch_id,city,emp_firstName,emp_lastName "
                    + "FROM employee as emp, branch as br WHERE emp.branch_id = br.branch_id "
                    + "AND CONCAT(city,emp_firstName,emp_lastName) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Employee emp = convertRowToEmployee(rs);
                list.add(emp);
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
    
    private Employee convertRowToEmployee(ResultSet rs)throws SQLException{
        String empID = rs.getString("employee_id");
        int branch_id = rs.getInt("emp.branch_id");
        String branchName = rs.getString("city");
        String first = rs.getString("emp_firstName");
        String last = rs.getString("emp_lastName");
        
        Employee employee = new Employee(empID,branchName,first,last);
        employee.setBranchID(branch_id);
        
        return employee;
    }
    
    private void close(PreparedStatement pst, ResultSet rs )throws SQLException{
        if(pst != null){
            pst.close();
        }
        if(rs!=null){
            rs.close();
        }
    }
}
