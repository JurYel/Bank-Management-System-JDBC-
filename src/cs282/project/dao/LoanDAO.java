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
import java.util.Properties;
import java.util.ArrayList;
import java.io.FileInputStream;
import cs282.project.core.Loan;
import java.util.Date;

public class LoanDAO {
    private Connection myConn;
    
    public LoanDAO()throws SQLException{
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
    
    public void addLoan(Loan loan)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("INSERT INTO loan (branch_id,customer_id,amount,loan_balance,loan_type,loan_date,temp_date) VALUES(?,?,?,?,?,?,?)");
            pst.setInt(1,loan.getBranchID());
            pst.setInt(2,loan.getCustomerID());
            pst.setDouble(3,loan.getAmount());
            pst.setDouble(4,loan.getBalance());
            pst.setString(5, loan.getLoanType());
            pst.setDate(6,new java.sql.Date(System.currentTimeMillis()));
            pst.setDate(7,new java.sql.Date(System.currentTimeMillis()));
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void updateLoanInfo(Loan loan)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE loan SET loan_type = ? WHERE loan_no = ?");
            
            pst.setString(1,loan.getLoanType());
            pst.setInt(2, loan.getLoanNumber());
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void deleteLoan(int loan_id)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("DELETE FROM loan WHERE loan_no = ?");
            pst.setInt(1,loan_id);
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void reimburseLoan(int loan_no, double newBal)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE loan SET loan_balance = ? WHERE loan_no = ?");
            pst.setDouble(1, newBal);
            pst.setInt(2, loan_no);
            
            pst.executeUpdate();
            
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void fullLoanPayment(int loan_no)throws SQLException{
        PreparedStatement pst = null;
        try{
            pst = myConn.prepareStatement("UPDATE loan SET loan_balance = ?, loan_paid = ? WHERE loan_no = ?");
            pst.setDouble(1, 0);
            pst.setBoolean(2,true);
            pst.setInt(3, loan_no);
            
            pst.executeUpdate();
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,null);
        }
    }
    
    public void shortTermInterest()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            myConn.setAutoCommit(false);
            ArrayList<Loan> STLoans = this.getShortTermLoans();
            Date curDate = new Date();
            for(int i = 0;i<STLoans.size();i++){
                Date temp_date = STLoans.get(i).getTempDate();
                int curMS = (int)curDate.getTime();
                int tempMS = (int)temp_date.getTime();
                
                if((convertMillisecondsToDays(curMS - tempMS) / 30) >= 1){
                    int mos = (convertMillisecondsToDays(tempMS - curMS)/30);
                    double prct = 0.1 * mos;
                    double loaned = (STLoans.get(i).getAmount() + (STLoans.get(i).getAmount() * prct));
                    
                    pst = myConn.prepareStatement("UPDATE loan SET amount = ?, loan_balance = ? WHERE loan_no ?");
                    pst.setDouble(1,loaned);
                    pst.setDouble(2,loaned);
                    pst.setInt(3,STLoans.get(i).getLoanNumber());
                    pst.executeUpdate();
                    
                    /* Update temp_date to current date */
                    
                    pst = myConn.prepareStatement("UPDATE loan SET temp_date = ? WHERE loan_no = ?");
                    pst.setDate(1,new java.sql.Date(System.currentTimeMillis()));
                    pst.setInt(2, STLoans.get(i).getLoanNumber());
                    pst.executeUpdate();
                }
            }
            myConn.commit();
        }
        catch(Exception exc){
            exc.printStackTrace();
            myConn.rollback();
        }
        finally{
            close(pst,rs);
        }
    }
    
    public void longTermInterest()throws SQLException{
        PreparedStatement pst = null;
        try{
            myConn.setAutoCommit(false);
            ArrayList<Loan> LTLoans = this.getLongTermLoans();
            Date curDate = new Date();
            
            for(int i = 0;i<LTLoans.size();i++){
                Date temp_date = LTLoans.get(i).getTempDate();
                int curMS = (int)curDate.getTime();
                int tempMS = (int)temp_date.getTime();
                
                if((convertMillisecondsToDays(curMS - tempMS)/30) >= 1){
                    int mos = (convertMillisecondsToDays(curMS - tempMS)/30);
                    double prct = 0.24 * mos;
                    double newLoaned = (LTLoans.get(i).getAmount() + (LTLoans.get(i).getAmount() * prct));
                    
                    pst = myConn.prepareStatement("UPDATE loan SET amount = ?, loan_balance = ? WHERE loan_no = ?");
                    pst.setDouble(1,newLoaned);
                    pst.setDouble(2,newLoaned);
                    pst.setInt(3, LTLoans.get(i).getLoanNumber());
                    pst.executeUpdate();
                    
                    /* Update temp_date to current date */
                    
                    pst = myConn.prepareStatement("UPDATE loan SET temp_date = ? WHERE loan_no = ?");
                    pst.setDate(1,new java.sql.Date(System.currentTimeMillis()));
                    pst.setInt(2, LTLoans.get(i).getLoanNumber());
                    pst.executeUpdate();
                }
            }
            myConn.commit();
        }
        catch(Exception exc){
            exc.printStackTrace();
            myConn.rollback();
        }
        finally{
            close(pst,null);
        }
    }
    
    public int getLoanID(int custID)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int loan_id = 0;
        try{
            pst = myConn.prepareStatement("SELECT loan_no FROM loan WHERE customer_id = ? AND loan_paid = false");
            pst.setInt(1,custID);
            rs = pst.executeQuery();
            
            while(rs.next()){
                loan_id = rs.getInt("loan_no");
            }
            return loan_id;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return loan_id;
    }
    
    public double getLoanAmount(int no)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        double amount = 0;
        try{
            pst = myConn.prepareStatement("SELECT amount FROM loan WHERE loan_no = ?");
            pst.setInt(1,no);
            rs = pst.executeQuery();
            
            while(rs.next()){
                amount = rs.getDouble("amount");
            }
            return amount;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return amount;
    }
    
    public double getLoanBalance(int no)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        double bal = 0;
        try{
            pst = myConn.prepareStatement("SELECT loan_balance FROM loan WHERE loan_no = ?");
            pst.setInt(1,no);
            rs = pst.executeQuery();
            
            while(rs.next()){
                bal = rs.getDouble("loan_balance");
            }
            return bal;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return bal;
    }
    
    public ArrayList<Loan> getLongTermLoans()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Loan> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT loan_no,amount,loan_type,loan_balance,loan_paid,temp_date,end_date FROM loan "
                    + "WHERE (loan_type,loan_paid) = ('Long-term',false)");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Loan loan = convertRowToSTLoan(rs);
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
    
    public ArrayList<Loan> getShortTermLoans()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Loan> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT loan_no,amount,loan_type,loan_balance,loan_paid,temp_date,end_date FROM loan "
                    + "WHERE (loan_type,loan_paid) = ('Short-term',false)");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Loan loan = convertRowToSTLoan(rs);
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
    
    public ArrayList<Loan> getUserLoanDetails(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Loan> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT loan.branch_id,loan.customer_id,loan_no,city, "
                    + "customer_firstName,customer_lastName,amount,loan_balance,loan_type, "
                    + "DATE_FORMAT(loan_date,'%M %e, %Y') as loan_date FROM loan,branch as br,customer as cu "
                    + "WHERE (loan.branch_id,loan.customer_id) = (br.branch_id,cu.customer_id) "
                    + "AND loan.customer_id = ? ");
            pst.setInt(1,id);
            rs = pst.executeQuery();
            
            while(rs.next()){
                Loan loan = convertRowToLoan(rs);
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
    
    public boolean checkIfLoanPaid(int no)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT loan_paid FROM loan WHERE loan_no = ?");
            pst.setInt(1,no);
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getBoolean("loan_paid") == true){
                    return true;
                }
            }
            return false;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return false;
    }
    
    public boolean checkUserHasLoan(int id)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT customer_id,loan_paid FROM loan");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getInt("customer_id") == id){
                    if(!(rs.getBoolean("loan_paid"))){
                        return true;
                    }
                }
            }
            return false;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return false;
    }
    
    public boolean checkLoanNo(int no)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            pst = myConn.prepareStatement("SELECT loan_no FROM loan");
            rs = pst.executeQuery();
            
            while(rs.next()){
                if(rs.getInt("loan_no") == no){
                    return true;
                }
            }
            return false;
        }
        catch(Exception exc){
            exc.printStackTrace();
        }
        finally{
            close(pst,rs);
        }
        return false;
    }
    
    public ArrayList<Loan> getAllLoans()throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Loan> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT loan.branch_id,loan.customer_id,loan_no,city, "
                    + "customer_firstName,customer_lastName,amount,loan_balance,loan_type, "
                    + "DATE_FORMAT(loan_date,'%M %e, %Y') as loan_date FROM loan,branch as br,customer as cu "
                    + "WHERE (loan.branch_id,loan.customer_id) = (br.branch_id,cu.customer_id)");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Loan loan = convertRowToLoan(rs);
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
    
    public ArrayList<Loan> searchLoan(String search)throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<Loan> list = new ArrayList<>();
        try{
            pst = myConn.prepareStatement("SELECT loan.branch_id,loan.customer_id,loan_no,city, "
                    + "customer_firstName,customer_lastName,amount,loan_balance,loan_type, "
                    + "DATE_FORMAT(loan_date,'%M %e, %Y') as loan_date FROM loan,branch as br,customer as cu "
                    + "WHERE (loan.branch_id,loan.customer_id) = (br.branch_id,cu.customer_id) "
                    + "AND CONCAT(loan_no,customer_firstName,customer_lastName,loan_type,loan_date,city) LIKE '%"+search+"%' ");
            rs = pst.executeQuery();
            
            while(rs.next()){
                Loan loan = convertRowToLoan(rs);
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
    
    private java.sql.Date convertUtilToSQL(Date uDate){
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
    
    private int convertMillisecondsToDays(int ms){
        int days = (int)Math.ceil(ms/(1000*60*60*24))+1;
        return days;
    }
    
    private Loan convertRowToSTLoan(ResultSet rs)throws SQLException{
        int loan_no = rs.getInt("loan_no");
        String type = rs.getString("loan_type");
        double amount = rs.getDouble("amount");
        double balance = rs.getDouble("loan_balance");
        boolean paid = rs.getBoolean("loan_paid");
        Date temp_date = rs.getDate("temp_date");
        Date end_date = rs.getDate("end_date");
        
        Loan loan = new Loan(loan_no,null,null,null,amount,balance,type,null);
        loan.setLoanPaid(paid);
        loan.setTempDate(temp_date);
        loan.setEndDate(end_date);
        
        return loan;
    }
    
    private Loan convertRowToLoan(ResultSet rs)throws SQLException{
        int branch_id = rs.getInt("loan.branch_id");
        int customer_id = rs.getInt("loan.customer_id");
        int loan_no = rs.getInt("loan_no");
        String city = rs.getString("city");
        String first = rs.getString("customer_firstName");
        String last = rs.getString("customer_lastName");
        double amount = rs.getDouble("amount");
        double balance = rs.getDouble("loan_balance");
        String type = rs.getString("loan_type");
        String loan_date = rs.getString("loan_date");
        
        Loan loan = new Loan(loan_no,city,first,last,amount,balance,type,null);
        loan.setBranchID(branch_id);
        loan.setCustomerID(customer_id);
        loan.setStrLoanDate(loan_date);
        
        return loan;
        
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
