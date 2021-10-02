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
import javax.swing.table.AbstractTableModel;
import cs282.project.core.SavingsAccount;
public class SavingsAccountTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int BRANCH_COL = 0;
    private static final int ACCOUNT_ID_COL = 1;
    private static final int MIN_BALANCE_COL = 2;
    private static final int DATE_OPENED_COL = 3;
    
    private String[] columnNames = {"Branch","Account ID","Minimum Balance","Date Opened"};
    private ArrayList<SavingsAccount> savings;
    
    public SavingsAccountTableModel(ArrayList<SavingsAccount> list){
        this.savings = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return savings.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        SavingsAccount saving = savings.get(row);
        
        switch(col){
            case BRANCH_COL:
                return saving.getBranch();
            case ACCOUNT_ID_COL:
                return saving.getAccountID();
            case MIN_BALANCE_COL:
                return saving.getMinBalance();
            case DATE_OPENED_COL:
                return saving.getDateTime();
            case OBJECT_COL:
                return saving;
            default: 
                return saving.getAccountID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
}
