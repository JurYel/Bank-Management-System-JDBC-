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
public class Branch {
   private int branch_id;
   private String bank_name;
   private String city;
   private String branch_code;
   
   public Branch(String name,String ct, String code){
       super();
       this.bank_name = name;
       this.city = ct;
       this.branch_code = code;
   }
   
   public void setBranchID(int id){
       this.branch_id = id;
   }
   
   public int getBranchID(){
       return branch_id;
   }
   
   public String getBankName(){
       return bank_name;
   }
   
   public String getCity(){
       return city;
   }
   
   public String getBranchCode(){
       return branch_code;
   }
}
