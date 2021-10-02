/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.dao;

/**
 *
 * @author Jur Yel
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import cs282.project.core.LoanHistory;
import java.sql.Timestamp;

public class LoanHistoryDAO {
   private Connection myConn;
   
   public LoanHistoryDAO()throws SQLException{
       try{
           Properties props = new Properties();
           props.load(new FileInputStream("sql/bankManagement.properties"));
           
           String user = props.getProperty("user");
           String pass = props.getProperty("password");
           String dburl = props.getProperty("dburl");
           
           myConn = DriverManager.getConnection(dburl,user,pass);
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
   }
   
   public void recordAction(LoanHistory history)throws SQLException{
       PreparedStatement pst = null;
       try{
           pst = myConn.prepareStatement("INSERT INTO loan_history(customer_id,loan_no,loan_type,action,amount,datetime) VALUES(?,?,?,?,?,?)");
           pst.setInt(1, history.getCustomerID());
           pst.setInt(2,history.getLoanNo());
           pst.setString(3,history.getLoanType());
           pst.setString(4,history.getAction());
           pst.setDouble(5, history.getAmount());
           pst.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
           
           pst.executeUpdate();
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
       finally{
           close(pst,null);
       }
   }
   
   public ArrayList<String> getLoanType(int no)throws SQLException{
       PreparedStatement pst = null;
       ResultSet rs = null;
       ArrayList<String> list = new ArrayList<>();
       try{
           pst = myConn.prepareStatement("SELECT loan_type FROM loan_history WHERE loan_no = ?");
           pst.setInt(1,no);
           rs = pst.executeQuery();
           
           while(rs.next()){
               String type = rs.getString("loan_type");
               list.add(type);
           }
           return list;
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
       finally{
           close(pst,rs);
       }
       return list;
   }
   
   public ArrayList<String> getLastPaidByLoanNo(int no)throws SQLException{
       PreparedStatement pst = null;
       ResultSet rs = null;
       ArrayList<String> list = new ArrayList<>();
       try{
           pst = myConn.prepareStatement("SELECT DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p')as datetime "
                   + "FROM loan_history WHERE loan_no = ? AND action != 'Loaned' ORDER BY datetime DESC");
           pst.setInt(1,no);
           rs = pst.executeQuery();
           
           while(rs.next()){
               String paid = rs.getString("datetime");
               list.add(paid);
           }
           return list;
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
       finally{
           close(pst,rs);
       }
       return list;
   }
   
   public ArrayList<String> getLastPaid(int custID)throws SQLException{
       PreparedStatement pst = null;
       ResultSet rs = null;
       ArrayList<String> list = new ArrayList<>();
       try{
           pst = myConn.prepareStatement("SELECT DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM loan_history "
                   + "WHERE customer_id = ? AND action != 'Loaned' ORDER BY datetime DESC");
           pst.setInt(1,custID);
           rs = pst.executeQuery();
           
           while(rs.next()){
               String datetime = rs.getString("datetime");
               list.add(datetime);
           }
           return list;
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
       finally{
           close(pst,rs);
       }
       return list;
   }
   
   public ArrayList<LoanHistory> getUserLoans(int customerID)throws SQLException{
       PreparedStatement pst = null;
       ResultSet rs = null;
       ArrayList<LoanHistory> list = new ArrayList<>();
       try{
           pst = myConn.prepareStatement("SELECT history_id,lh.customer_id,customer_firstName,customer_lastName,loan_no,loan_type,action,amount, "
                   + "DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM loan_history as lh, customer as cu "
                   + "WHERE lh.customer_id = cu.customer_id AND lh.customer_id = ? ORDER BY datetime DESC");
           pst.setInt(1,customerID);
           rs = pst.executeQuery();
           
           while(rs.next()){
               LoanHistory loan = convertRowToLoanHistory(rs);
               list.add(loan);
           }
           return list;
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
       finally{
           close(pst,rs);
       }
       return list;
   }
   
   public ArrayList<LoanHistory> getAllRecords()throws SQLException{
       PreparedStatement pst = null;
       ResultSet rs = null;
       ArrayList<LoanHistory> list = new ArrayList<>();
       try{
           pst = myConn.prepareStatement("SELECT history_id,lh.customer_id,customer_firstName,customer_lastName,loan_no,loan_type,action,amount, "
                   + "DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM loan_history as lh, customer as cu "
                   + "WHERE lh.customer_id = cu.customer_id");
           rs = pst.executeQuery();
           
           while(rs.next()){
               LoanHistory history = convertRowToLoanHistory(rs);
               list.add(history);
           }
           return list;
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
       finally{
           close(pst,rs);
       }
       return list;
   }
   
   public ArrayList<LoanHistory> searchRecord(String search)throws SQLException{
       PreparedStatement pst = null;
       ResultSet rs = null;
       ArrayList<LoanHistory> list = new ArrayList<>();
       try{
           pst = myConn.prepareStatement("SELECT history_id,lh.customer_id,customer_firstName,customer_lastName,loan_no,loan_type,action,amount, "
                   + "DATE_FORMAT(datetime,'%M %e, %Y - %l:%i %p') as datetime FROM loan_history as lh, customer as cu "
                   + "WHERE lh.customer_id = cu.customer_id AND CONCAT(customer_firstName,customer_lastName) LIKE '%"+search+"%' ");
           rs = pst.executeQuery();
           
           while(rs.next()){
               LoanHistory history = convertRowToLoanHistory(rs);
               list.add(history);
           }
           return list;
       }
       catch(Exception exc){
           exc.printStackTrace();
       }
       finally{
           close(pst,rs);
       }
       return list;
   }
   
   private java.util.Date convertUtilToTimestamp(Timestamp timestamp){
       java.util.Date uDate = new java.util.Date(timestamp.getTime());
       return uDate;
   }
   
   private LoanHistory convertRowToLoanHistory(ResultSet rs)throws SQLException{
       int history_id = rs.getInt("history_id");
       int customer_id = rs.getInt("lh.customer_id");
       String first = rs.getString("customer_firstName");
       String last = rs.getString("customer_lastName");
       int loan_no = rs.getInt("loan_no");
       String loan_type = rs.getString("loan_type");
       String action = rs.getString("action");
       double amount = rs.getDouble("amount");
       String datetime = rs.getString("datetime");
       
       LoanHistory history = new LoanHistory(first,last,action,amount,null);
       history.setHistoryID(history_id);
       history.setCustomerID(customer_id);
       history.setStrDateTime(datetime);
       history.setLoanNo(loan_no);
       history.setLoanType(loan_type);
       
       return history;
   }
   
   private void close(PreparedStatement pst,ResultSet rs)throws SQLException{
       if(pst!=null){
           pst.close();
       }
       if(rs!=null){
           rs.close();
       }
   }
}
