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
public class LoanHistory {
    private int history_id;
    private int customer_id;
    private String customer_first;
    private String customer_last;
    private int loan_no;
    private String loan_type;
    private String action;
    private double amount;
    private Date datetime;
    private String str_datetime;
    
    public LoanHistory(String first,String last,String act,double amt,Date dtime){
        super();
        this.customer_first = first;
        this.customer_last = last;
        this.action = act;
        this.amount = amt;
        this.datetime = dtime;
    }
    
    public void setHistoryID(int id){
        this.history_id = id;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public void setStrDateTime(String dtime){
        this.str_datetime = dtime;
    }
    
    public void setLoanNo(int no){
        this.loan_no = no;
    }
    
    public void setLoanType(String type){
        this.loan_type = type;
    }
    
    public int getHistoryID(){
        return history_id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public int getLoanNo(){
        return loan_no;
    }
    
    public String getLoanType(){
        return loan_type;
    }
    
    public String getCustomerFirst(){
        return customer_first;
    }
    
    public String getCustomerLast(){
        return customer_last;
    }
    
    public String getAction(){
        return action;
    }
    
    public double getAmount(){
        return amount;
    }
    
    public Date getDateTime(){
        return datetime;
    }
    
    public String getStrDateTime(){
        return str_datetime;
    }
}
