/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.core;

import java.util.Date;

/**
 *
 * @author Jur Yel
 */
public class Account {
    private String account_id;
    private int branch_id;
    private int customer_id;
    private String branchCity;
    private String firstName;
    private String lastName;
    private double balance;
    private String account_pin;
    private Date dateOpened;
    private String strDate;
    
    public Account(String city,String first, String last,double bal,String pin){
        super();
        this.branchCity = city;
        this.firstName = first;
        this.lastName = last;
        this.balance = bal;
        this.account_pin = pin;
    }
    
    public void setAccountID(String id){
        this.account_id = id;
    }
    
    public void setBranchID(int id){
        this.branch_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setDateOpened(Date date){
        this.dateOpened = date;
    }
    
    public void setStrDateOpened(String date){
        this.strDate = date;
    }
    
    public String getAccountID(){
        return account_id;
    }
    
    public int getBranchID(){
        return branch_id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public String getBranchCity(){
        return branchCity;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public String getLastName(){
        return lastName;
    }
    
    public double getBalance(){
        return balance;
    }
    
    public String getAccountPin(){
        return account_pin;
    }
    
    public Date getDateOpened(){
        return dateOpened;
    }
    
    public String getStrDateOpened(){
        return strDate;
    }
}
