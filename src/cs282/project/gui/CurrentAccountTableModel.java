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
import cs282.project.core.CurrentAccount;
public class CurrentAccountTableModel extends AbstractTableModel{
    public static final int OBJECT_COL = -1;
    private static final int BRANCH_COL = 0;
    private static final int ACCOUNT_ID_COL = 1;
    private static final int CUR_BALANCE_COL = 2;
    private static final int DATE_OPENED_COL = 3;
    
    private String[] columnNames = {"Branch","Account ID","Current Balance","Date Opened"};
    private ArrayList<CurrentAccount> accounts;
    
    public CurrentAccountTableModel(ArrayList<CurrentAccount> list){
        this.accounts = list;
    }
    
    @Override
    public int getColumnCount(){
        return columnNames.length;
    }
    
    @Override
    public int getRowCount(){
        return accounts.size();
    }
    
    @Override
    public String getColumnName(int col){
        return columnNames[col];
    }
    
    @Override
    public Object getValueAt(int row,int col){
        CurrentAccount account = accounts.get(row);
        
        switch(col){
            case BRANCH_COL:
                return account.getBranch();
            case ACCOUNT_ID_COL:
                return account.getAccountID();
            case CUR_BALANCE_COL:
                return account.getCurrentBalance();
            case DATE_OPENED_COL:
                return account.getDateTime();
            case OBJECT_COL:
                return account;
            default:
                return account.getAccountID();
        }
    }
    
    @Override
    public Class getColumnClass(int c){
        return getValueAt(0,c).getClass();
    }
    
}
