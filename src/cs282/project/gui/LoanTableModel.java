/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs282.project.gui;

/**
 *
 * @author Jur Yel
 */
import java.util.ArrayList;
import cs282.project.core.Loan;
import javax.swing.table.AbstractTableModel;
public class LoanTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int LOAN_NO_COL = 0;
    private static final int CITY_COL = 1;
    private static final int FIRST_COL = 2;
    private static final int LAST_COL = 3;
    private static final int AMOUNT_COL = 4;
    private static final int LOAN_TYPE_COL = 5;
    private static final int LOAN_DATE_COL = 6;
    
    private ArrayList<Loan> loans;
    private String[] columnNames = {"Loan No.","Branch","First Name","Last Name","Loan Amount","Loan Type","Loan Date/Time"};
    
    public LoanTableModel(ArrayList<Loan> list){
        this.loans = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return loans.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        Loan loan = loans.get(row);
        
        switch(col){
            case LOAN_NO_COL:
                return loan.getLoanNumber();
            case CITY_COL:
                return loan.getCityBranch();
            case FIRST_COL:
                return loan.getCustomerFirst();
            case LAST_COL:
                return loan.getCustomerLast();
            case AMOUNT_COL:
                return loan.getAmount();
            case LOAN_TYPE_COL:
                return loan.getLoanType();
            case LOAN_DATE_COL:
                return loan.getStrLoanDate();
            case OBJECT_COL:
                return loan;
            default:
                return loan.getLoanNumber();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
}
