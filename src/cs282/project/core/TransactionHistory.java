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
public class TransactionHistory {
    private String account_id;
    private String action;
    private String account_type;
    private double amount;
    private Date datetime;
    private String trans_date;
    
    public TransactionHistory(String id,String act,String type,double amt, Date dtime){
        super();
        this.account_id = id;
        this.action = act;
        this.account_type = type;
        this.amount = amt;
        this.datetime = dtime;
    }
    
    public void setAccountID(String id){
        this.account_id = id;
    }
    
    public void setTransDate(String date){
        this.trans_date = date;
    }
    
    public String getAccountID(){
        return account_id;
    }
    
    public String getAction(){
        return action;
    }
    
    public String getAccountType(){
        return account_type;
    }
    
    public double getAmount(){
        return amount;
    }
    
    public Date getDateTime(){
        return datetime;
    }
    
    public String getTransDate(){
        return trans_date;
    }
}
