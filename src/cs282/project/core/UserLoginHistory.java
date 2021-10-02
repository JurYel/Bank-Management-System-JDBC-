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
public class UserLoginHistory {
    private String account_id;
    private Date datetime;
    private String login_time;
    
    public UserLoginHistory(String id,Date dtime){
        super();
        this.account_id = id;
        this.datetime = dtime;
    }
    
    public void setAccountID(String id){
        this.account_id = id;
    }
    
    public void setLoginTime(String time){
        this.login_time = time;
    }
    
    public String getAccountID(){
        return account_id;
    }
    
    public Date getDateTime(){
        return datetime;
    }
    
    public String getLoginTime(){
        return login_time;
    }
    
}
