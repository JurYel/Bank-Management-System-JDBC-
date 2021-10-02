/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.core;

/**
 *
 * @author Jur Yel
 */
public class Employee {
    private String employee_id;
    private int branch_id;
    private String branchName;
    private String firstName;
    private String lastName;
    private String password;
    
    public Employee(String empID,String branch,String first,String last){
        super();
        this.employee_id = empID;
        this.branchName = branch;
        this.firstName = first;
        this.lastName = last;
    }
    
    public void setEmployeeID(String id){
        this.employee_id = id;
    }
    
    public void setBranchID(int id){
        this.branch_id = id;
    }
    
    public void setBranchName(String name){
        this.branchName = name;
    }
    
    public void setPassword(String pass){
        this.password = pass;
    }
    
    public String getEmployeeID(){
        return employee_id;
    }
    
    public int getBranchID(){
        return branch_id;
    }
    
    public String getBranchName(){
        return branchName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public String getPassword(){
        return password;
    }
}
