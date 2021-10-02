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
public class SavingsAccount {
    private String account_id;
    private String branch;
    private double min_balance;
    private Date date_opened;
    private Date temp_date;
    private String datetime;
    
    public SavingsAccount(String id, String br,double bal, Date date){
        super();
        this.branch = br;
        this.account_id = id;
        this.min_balance = bal;
        this.date_opened = date;
    }
    
    public void setAccountID(String id){
        this.account_id = id;
    }
    
    public void setTempDate(Date temp){
        this.temp_date = temp;
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
    
    public double getMinBalance(){
        return min_balance;
    }
    
    public Date getDateOpened(){
        return date_opened;
    }
    
    public Date getTempDate(){
        return temp_date;
    }
    
    public String getStrDateOpened(){
        return date_opened.toLocaleString();
    }
    
    public String getDateTime(){
        return datetime;
    }
    
}
