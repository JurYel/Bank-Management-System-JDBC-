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
import cs282.project.core.LoanHistory;
import javax.swing.table.AbstractTableModel;
public class UserLoanHistoryTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int LOAN_NO_COL = 0;
    private static final int LOAN_TYPE_COL = 1;
    private static final int ACTION_COL = 2;
    private static final int AMOUNT_COL = 3;
    private static final int DATETIME_COL = 4;
    
    private String[] columnNames = {"Loan No.","Loan Type","Action","Amount","Date/Time"};
    private ArrayList<LoanHistory> loans;
    
    public UserLoanHistoryTableModel(ArrayList<LoanHistory> list){
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
    public Object getValueAt(int row, int col){
        LoanHistory loan = loans.get(row);
        
        switch(col){
            case LOAN_NO_COL:
                return loan.getLoanNo();
            case LOAN_TYPE_COL:
                return loan.getLoanType();
            case ACTION_COL:
                return loan.getAction();
            case AMOUNT_COL:
                return loan.getAmount();
            case DATETIME_COL:
                return loan.getStrDateTime();
            case OBJECT_COL:
                return loan;
            default:
                return loan.getLoanNo();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
}
