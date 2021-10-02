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
public class Bank {
    private int bank_id;
    private int branch_id;
    private String bank_name;
    private String bank_code;
    private String bank_address;
    
    public Bank(String name,String code,String address){
        super();
        this.bank_name = name;
        this.bank_code = code;
        this.bank_address = address;
    }
    
    public void setBankID(int id){
        this.bank_id = id;
    }
    
    public void setBranchID(int id){
        this.branch_id = id;
    }
    
    public int getBankID(){
        return bank_id;
    }
    
    public int getBranchID(){
        return branch_id;
    }
    
    public String getBankName(){
        return bank_name;
    }
    
    public String getBankCode(){
        return bank_code;
    }
    
    public String getBankAddress(){
        return bank_address;
    }
}
