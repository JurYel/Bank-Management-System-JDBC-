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
public class Customer {
    private int customer_id;
    private String customer_first;
    private String customer_last;
    private String customer_address;
    private String contact_number;
    
    public Customer(String first,String last,String address,String contact){
        super();
        this.customer_first = first;
        this.customer_last = last;
        this.customer_address = address;
        this.contact_number = contact;
    }
    
    public void setCustomerID(int id){
        this.customer_id = id;
    }
    
    public int getCustomerID(){
        return customer_id;
    }
    
    public String getCustomerFirstName(){
        return customer_first;
    }
    
    public String getCustomerLastName(){
        return customer_last;
    }
    
    public String getCustomerAddress(){
        return customer_address;
    }
    
    public String getContactNumber(){
        return contact_number;
    }
}
