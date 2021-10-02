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
public class CurrentAccount {
    private String account_id;
    private String branch;
    private double current_balance;
    private Date date_opened;
    private String datetime;
    
    public CurrentAccount(String id,String city,double bal,Date date){
        super();
        this.account_id = id;
        this.branch = city;
        this.current_balance = bal;
        this.date_opened = date;
    }
    
    public void setAccountID(String id){
        this.account_id = id;
    }
    
    public void setDateOpened(Date open){
        this.date_opened = open;
    }
    
    public void setDateTime(String dtime){
        this.datetime = dtime;
    }
    
    public String getAccountID(){
        return account_id;
    }
    
    public String getBranch(){
        return branch;
    }
    
    public double getCurrentBalance(){
        return current_balance;
    }
    
    public Date getDateOpened(){
        return date_opened;
    }
    
    public String getStrDateOpened(){
        return date_opened.toLocaleString();
    }
    
    public String getDateTime(){
        return datetime;
    }
}
