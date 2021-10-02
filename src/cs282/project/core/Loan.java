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
import java.util.Date;
public class Loan {
    private int loan_id;
    private int branch_id;
    private int customer_id;
    private int loan_no;
    private String city_branch;
    private String customer_firstName;
    private String customer_lastName;
    private double amount;
    private double balance;
    private String loan_type;
    private boolean loan_paid;
    private Date loan_date;
    private Date temp_date;
    private Date end_date;
    private String str_LoanDate;
    
    public Loan(int no,String city,String first,String last,double amt,double bal,String type,Date date){
        super();
        this.loan_no = no;
        this.city_branch = city;
        this.customer_firstName = first;
        this.customer_lastName = last;
        this.amount = amt;
        this.balance = bal;
        this.loan_type = type;
        this.loan_date = date;
    }
    
    public void setLoanID(int id){
        this.loan_id = id;
    }
    
    public void setBranchID(int id){
        this.branch_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setLoanNumber(int no){
        this.loan_no = no;
    }
    
    public void setStrLoanDate(String date){
        this.str_LoanDate = date;
    }
    
    public void setEndDate(Date date){
        this.end_date = date;
    }
    
    public void setLoanPaid(boolean paid){
        this.loan_paid = paid;
    }
    
    public void setTempDate(Date date){
        this.temp_date = date;
    }
    
    public int getLoanID(){
        return loan_id;
    }
    
    public int getBranchID(){
        return branch_id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public int getLoanNumber(){
        return loan_no;
    }
    
    public boolean getLoanPaid(){
        return loan_paid;
    }
    
    public String getCityBranch(){
        return city_branch;
    }
    
    public String getCustomerFirst(){
        return customer_firstName;
    }
    
    public String getCustomerLast(){
        return customer_lastName;
    }
    
    public double getAmount(){
        return amount;
    }
    
    public double getBalance(){
        return balance;
    }
    
    public String getLoanType(){
        return loan_type;
    }
    
    public Date getLoanDate(){
        return loan_date;
    }
    
    public Date getTempDate(){
        return temp_date;
    }
    
    public Date getEndDate(){
        return end_date;
    }
    
    public String getStrLoanDate(){
        return str_LoanDate;
    }
}
